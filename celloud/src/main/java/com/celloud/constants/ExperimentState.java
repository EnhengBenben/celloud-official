package com.celloud.constants;

/**
 * 实验流程状态
 * 
 * @author lin
 * @date 2016年3月28日 下午4:17:19
 */
public class ExperimentState {
	/**
	 * 正常开启
	 */
	public static final int OPEN = 0;
	/**
	 * 异常关闭
	 */
	public static final int EXCEPTIONCLOSED = 1;
	/**
	 * 正常关闭
	 */
	public static final int NORMALCLOSED = 2;

	/**
	 * 可以绑定数据的实验阶段
	 */
	public static final int RELAT_STEP = 5;
	/**
	 * 报告产生后的实验阶段
	 */
	public static final int REPORT_STEP = 6;

	/**
	 * 未绑定数据的file_id
	 */
	public static final int UN_RELAT_FILEID = 0;
}
