package com.celloud.box.service;

import java.io.File;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.celloud.box.model.Newfile;
import com.celloud.box.utils.MD5Util;
import com.celloud.box.utils.ThreadUtil;
import com.celloud.box.utils.UploadPath;

@Service
public class BoxServiceImpl implements BoxService {
	private static Logger logger = LoggerFactory.getLogger(BoxServiceImpl.class);
	@Resource
	private OSSService ossService;
	@Resource
	private ApiService apiService;

	@Async
	@Override
	public void finish(Integer userId, String name, Integer tagId, String batch, Integer needSplit, File file) {
		String md5 = MD5Util.getFileMD5(file);
		// 通知celloud有新文件上传
		Newfile newfile = null;
		for (int i = 0; i < 3; i++) {// 失败需重试
			newfile = apiService.newfile(userId, name, file.length(), md5, tagId, batch);
			if (newfile != null) {// 成功则退出
				break;
			}
			ThreadUtil.sleep(5000);// 5秒之后再重试
		}
		if (newfile == null) {
			// TODO 这里需要处理
			return;
		}
		// 将文件上传到oss
		String objectKey = UploadPath.getObjectKey(userId, newfile.getDataKey(), newfile.getExt());
		ossService.upload(objectKey, file);
		// 通知celloud文件已经上传到oss
		for (int i = 0; i < 3; i++) {// 失败需重试
			boolean result = apiService.updatefile(objectKey, newfile.getFileId(), tagId, batch, needSplit);
			if (result) {// 成功则退出
				break;
			}
			ThreadUtil.sleep(5000);// 5秒之后再重试
		}
		logger.info("文件已上传到celloud");
	}

}
