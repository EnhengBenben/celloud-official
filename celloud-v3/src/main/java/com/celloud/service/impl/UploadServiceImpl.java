package com.celloud.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.UploadMapper;
import com.celloud.model.Upload;
import com.celloud.service.UploadService;

@Service("uploadService")
public class UploadServiceImpl implements UploadService{
	
	@Resource
	private UploadMapper uploadMapper;

	@Override
	public int uploadUpdate(Integer userId, String dataKey, long num, long position) {
		Upload record = new Upload();
		record.setUserId(userId);
		record.setDataKey(dataKey);
		record.setNum((int)num);
		record.setPosition((double)position);
		return uploadMapper.uploadUpdate(record);
	}

	@Override
	public int uploadInsert(Integer userId, String dataKey, long num, long position) {
		Upload record = new Upload();
		record.setUserId(userId);
		record.setDataKey(dataKey);
		record.setNum((int)num);
		record.setPosition((double)position);
		record.setCreateDate(new Date());
		return uploadMapper.insertSelective(record);
	}

	@Override
	public List<Upload> getUploadList(Integer userId, String dataKey) {
		return uploadMapper.getUploadList(userId, dataKey);
	}

}
