package com.celloud.listener;

import java.io.File;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.celloud.model.BoxFile;
import com.celloud.service.DataService;
import com.celloud.service.RunService;
import com.celloud.utils.CheckFileTypeUtil;

@Component
public class FileDownloadedListener implements ApplicationListener<FileDownloadedEvent> {
	private static Logger logger = LoggerFactory.getLogger(FileDownloadedListener.class);
	@Resource
	private DataService service;
	@Resource
	private RunService runService;

	@Override
	public void onApplicationEvent(FileDownloadedEvent event) {
		BoxFile boxFile = (BoxFile) event.getSource();
		String anotherName = service.getAnotherName("", boxFile.getPath(), "");
		CheckFileTypeUtil util = new CheckFileTypeUtil();
		File file = new File(boxFile.getPath());
		int fileFormat = util.checkFileType(file.getName(), file.getParentFile().getAbsolutePath());
		int result = service.updateFileInfo(boxFile.getFileId(), boxFile.getObjectKey(), boxFile.getPath(),
				boxFile.getPath(), fileFormat, boxFile.getMd5(), anotherName, boxFile.getFileId());
		logger.info("文件更新状态{}:{}", result > 0 ? "成功" : "失败", boxFile.getPath());
		if (boxFile.getTagId() != null && boxFile.getTagId().intValue() == 1) {
			// TODO 保险起见，这里还应该校验用户是否已经添加app
			String checkRunresult = runService.bsiCheckRun(boxFile.getBatch(), boxFile.getFileId(),
					boxFile.getDataKey(), boxFile.getNeedSplit(), boxFile.getFileName(), boxFile.getUserId(),
					fileFormat);
			logger.debug("bsi check run result: {}", checkRunresult);
		}
	}

}
