package com.celloud.box.listener;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.celloud.box.event.SplitRunOverEvent;
import com.celloud.box.model.DataFile;
import com.celloud.box.model.SplitFile;
import com.celloud.box.service.BoxService;
import com.celloud.box.utils.UploadPath;

/**
 * split队列监听器，监听split运行结束的事件，某个文件运行split结束后，触发一下个文件的split运行
 * 
 * @author <a href="mailto:sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年10月25日上午11:27:54
 * @version Revision: 1.0
 */
@Component
public class SplitRunOverListener implements ApplicationListener<SplitRunOverEvent> {
	@Resource
	private BoxService boxService;
	private static Logger logger = LoggerFactory.getLogger(SplitRunOverListener.class);

	@Async
	@Override
	public void onApplicationEvent(SplitRunOverEvent event) {
		String path = (String) event.getSource();
		SplitFile splitFile = SplitFile.load(path);
		splitFile.setRunning(Boolean.FALSE);
		splitFile.toFile();
		DataFile r1 = setSplited(splitFile.getR1Path());
		boxService.finish(r1);
		DataFile r2 = setSplited(splitFile.getR2Path());
		boxService.finish(r2);
		DataFile txt = setSplited(splitFile.getTxtPath());
		boxService.finish(txt);
		String temp = r1.getFilename().replaceAll(r1.getExt(), "");
		String resultPath = UploadPath.getSplitOutputPath(splitFile.getUserId(), splitFile.getBatch(),
				splitFile.getName()) + File.separatorChar + temp + File.separatorChar + "result" + File.separatorChar
				+ "split" + File.separatorChar;
		File resultFile = new File(resultPath);
		File[] results = resultFile.listFiles();
		if (results == null || results.length == 0) {
			logger.error("split运行结束，但是没有找到结果文件：{}\n{}", resultPath, splitFile.toJSON());
			return;
		}
		String folder = UploadPath.getUploadingPath(splitFile.getUserId());
		for (File result : results) {
			String uniqueName = UploadPath.getUniqueName(splitFile.getUserId(), result.getName(), result.lastModified(),
					result.length());
			String randomName = UploadPath.getRandomName(uniqueName);
			File newPath = new File(folder + randomName);
			try {
				FileUtils.moveFile(result, newPath);
			} catch (IOException e) {
				logger.error("移动文件失败：{}", result.getAbsolutePath(), e);
				continue;
			}
			boxService.splitRunOver(splitFile.getUserId(), newPath.getName(), splitFile.getName(), r1.getTagId(),
					splitFile.getBatch(), null, newPath);
		}
		// TODO clean...
	}

	public DataFile setSplited(String path) {
		DataFile temp = DataFile.load(path + ".json");
		temp.setSplited(Boolean.TRUE);
		temp.serialize();
		return temp;
	}

}
