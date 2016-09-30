package com.celloud.service;

public interface BoxApiService {
	public void finishfile(String objectKey, Integer fileId, Integer tagId, String batch, Integer needSplit,
			String newName, String folderByDay);
}
