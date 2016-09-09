package com.celloud.service;

public interface BoxApiService {
	public void updatefile(String objectKey, Integer fileId, Integer tagId, String batch, Integer needSplit);

	/**
	 * 判断是否上传完即刻运行
	 * 
	 * @param tagId
	 * @param batch
	 * @param dataId
	 * @param needSplit
	 * @param newName
	 * @param folderByDay
	 * @param originalName
	 * @param userId
	 * @param fileFormat
	 * @return
	 * @author leamo
	 * @date 2016年5月10日 下午3:41:08
	 */
	public String bsierCheckRun(String batch, Integer dataId, String dataKey, Integer needSplit, String originalName,
			Integer userId, Integer fileFormat);

	public String getAnotherName(String filePath, String fileDataKey, String perlPath, String outPath);

	public void updateUploadState(Integer fileId, String objectKey, int state);
}
