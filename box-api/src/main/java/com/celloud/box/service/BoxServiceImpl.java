package com.celloud.box.service;

import java.io.File;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.celloud.box.utils.OSSUtils;
import com.celloud.box.utils.UploadPath;

@Service
public class BoxServiceImpl implements BoxService {
	@Async
	@Override
	public void upload2oss(Integer userId, String dataKey, String ext, File f) {
		String location = OSSUtils.upload(UploadPath.getObjectKey(userId, f.getName(), ""), f);
	}

}
