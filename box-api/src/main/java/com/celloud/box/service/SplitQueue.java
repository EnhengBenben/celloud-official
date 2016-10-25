package com.celloud.box.service;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.celloud.box.config.BoxConfig;
import com.celloud.box.event.SplitAddedEvent;
import com.celloud.box.event.SplitRunOverEvent;
import com.celloud.box.model.SplitFile;
import com.celloud.box.utils.UploadPath;

@Component
public class SplitQueue {
	@Resource
	private SplitService service;
	@Resource
	private BoxConfig config;
	@Resource
	private ApplicationContext context;
	private static Queue<String> queue = new LinkedList<String>();
	private static Vector<String> running = new Vector<>();

	public boolean add(SplitFile file) {
		String path = UploadPath.getSplitCheckingPath(file.getUserId(), file.getBatch(), file.getName());
		boolean result = false;
		synchronized (this) {
			if (!queue.contains(path)) {
				result = queue.offer(path);
				context.publishEvent(new SplitAddedEvent(path));
			}
		}
		return result;
	}

	public void split() {
		String file = null;
		synchronized (this) {
			if (running.size() >= config.getMaxSplitting()) {
				this.printAll();
				return;
			}
			file = queue.poll();
			if (file == null) {
				this.printAll();
				return;
			}
			running.add(file);
			this.printAll();
		}
		SplitFile splitFile = SplitFile.load(file);
		splitFile.setRunning(Boolean.TRUE);
		splitFile.toFile();
		boolean result = service.run(splitFile);
		context.publishEvent(result ? new SplitRunOverEvent(file) : new SplitRunOverEvent(file));
	}

	public void printAll() {
	}
}
