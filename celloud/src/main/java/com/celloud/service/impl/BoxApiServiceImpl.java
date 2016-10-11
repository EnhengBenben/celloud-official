package com.celloud.service.impl;

import java.io.File;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.celloud.model.mysql.DataFile;
import com.celloud.service.BoxApiService;
import com.celloud.service.DataService;
import com.celloud.service.RunService;
import com.celloud.utils.CheckFileTypeUtil;
import com.celloud.utils.MD5Util;
import com.celloud.utils.OSSUtils;

@Service
public class BoxApiServiceImpl implements BoxApiService {
	private static Logger logger = LoggerFactory.getLogger(BoxApiServiceImpl.class);
	@Resource
	private DataService dataService;
	@Resource
	private RunService runService;

	CheckFileTypeUtil checkFileType = new CheckFileTypeUtil();

	@Async
	@Override
	public void finishfile(String objectKey, Integer fileId, Integer tagId, String batch, Integer needSplit,
			String newName, String folderByDay) {
		DataFile file = dataService.getDataById(fileId);
		Integer userId = file.getUserId();
		boolean isDownloaded = false;
		String path = folderByDay + File.separator + newName;
		for (int i = 0; i < 3; i++) {
			String md5 = OSSUtils.download(objectKey, path);
			if (file.getMd5().equals(md5)) {
				isDownloaded = true;
				break;
			} else {
				logger.info("从oss下载文件错误，进行第{}次重试：{}", i + 1, objectKey);
			}
		}
		if (!isDownloaded) {
			dataService.updateUploadState(fileId, objectKey, 3, path);
			return;
		}
		int fileFormat = checkFileType.checkFileType(newName, folderByDay);
		// TODO fileFormat 是空
		String md5 = MD5Util.getFileMD5(path);
		dataService.updateFileInfo(fileId, file.getDataKey(), path, batch, fileFormat, md5, null, null);
		if (tagId != null && tagId.intValue() == 1) {
			// TODO 保险起见，这里还应该校验用户是否已经添加app
			String result = runService.bsiCheckRun(batch, fileId, file.getDataKey(), needSplit, file.getFileName(),
					userId, fileFormat);
			logger.debug("bsi check run result: {}", result);
		}
	}

}
