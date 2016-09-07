package com.celloud.box.service;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.celloud.box.utils.UploadPath;

@Service
public class BoxServiceImpl implements BoxService {
	@Resource
	private OSSService ossService;

	@Async
	@Override
	public void upload2oss(Integer userId, String dataKey, String ext, File f) {
		// TODO 修改生成objectKey的规则
		ossService.upload(UploadPath.getObjectKey(userId, dataKey, ext), f);
	}

}
