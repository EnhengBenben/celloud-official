package com.celloud.constants;

public class BoxUploadState {
	/**
	 * 文件已上传到盒子
	 */
	public static final Integer IN_BOX = 0;
	/**
	 * 文件已经上传到OSS
	 */
	public static final Integer IN_OSS = 1;
	/**
	 * 文件上传到OSS时失败
	 */
	public static final Integer FAIL_TO_OSS = 2;
	/**
	 * 文件已经成功下载到celloud平台端
	 */
	public static final Integer ON_CELLOUD = 3;
	/**
	 * 文件下载到celloud平台端失败
	 */
	public static final Integer FAIL_TO_CELLOUD = 4;
}
