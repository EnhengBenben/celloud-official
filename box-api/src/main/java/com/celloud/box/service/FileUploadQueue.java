package com.celloud.box.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Vector;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import com.celloud.box.config.BoxConfig;
import com.celloud.box.event.FileAddedEvent;
import com.celloud.box.event.FileUploadErrorEvent;
import com.celloud.box.event.FileUploadedEvent;
import com.celloud.box.model.DataFile;
import com.celloud.box.utils.OSSProgressListener;

@Component
public class FileUploadQueue {
	private static Logger logger = LoggerFactory.getLogger(FileUploadQueue.class);
	@Autowired
	private ApplicationContext applicationContext;
	@Resource
	private OSSService service;
	@Resource
	private BoxConfig config;
	private static Queue<String> queue = new LinkedList<String>();
	private static Vector<String> uploading = new Vector<>();

	public boolean add(File file) {
		return file != null && file.exists() && add(file.getAbsolutePath());
	}

	public boolean add(String file) {
		boolean result = false;
		synchronized (this) {
			if (!queue.contains(file)) {
				result = queue.offer(file);
				applicationContext.publishEvent(new FileAddedEvent(file));
			}
		}
		return result;
	}

	public void upload() {
		String file = null;
		synchronized (this) {
			if (uploading.size() >= config.getMaxUploading()) {
				this.printAll();
				return;
			}
			file = queue.poll();
			if (file == null) {
				this.printAll();
				return;
			}
			uploading.add(file);
			this.printAll();
		}
		DataFile dataFile = DataFile.load(file + ".json");
		boolean result = false;
		if (dataFile != null) {
			String location = null;
			for (int i = 0; i <= config.getMaxRetry(); i++) {
				location = service.upload(dataFile.getObjectKey(), new File(file),
						new OSSProgressListener(dataFile.getUserId(), dataFile.getFilename(), dataFile.getDataKey()));
				if (location != null) {
					result = true;
					break;
				}
			}
		} else {
			result = true;
		}
		uploading.remove(file);// 线程安全，不需要加锁
		ApplicationEvent event = result ? new FileUploadedEvent(file) : new FileUploadErrorEvent(file);
		applicationContext.publishEvent(event);
	}

	public List<String> getAll() {
		return new ArrayList<String>(queue);
	}

	public void printAll() {

		logger.info("##################uploading##################");
		for (String file : uploading) {
			logger.info(file);
		}
		if (uploading.size() <= 0) {
			logger.info("没有需要上传的文件......");
		}
		if (queue.size() > 0) {
			logger.info("################## waiting ##################");
			Iterator<String> it = queue.iterator();
			while (it.hasNext()) {
				logger.info(it.next());
			}
		}
		logger.info("#############################################");
	}
}
