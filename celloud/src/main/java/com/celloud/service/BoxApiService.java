package com.celloud.service;

import java.util.concurrent.Future;

import com.celloud.model.BoxFile;

public interface BoxApiService {
	public Future<Boolean> downloadFromOSS(BoxFile file);

	public Boolean downloadFromOSS(String objectKey, String path, String md5);
}
