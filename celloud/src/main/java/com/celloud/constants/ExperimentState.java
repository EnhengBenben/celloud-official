package com.celloud.constants;

import java.util.HashMap;

/**
 * 实验流程状态
 * 
 * @author lin
 * @date 2016年3月28日 下午4:17:19
 */
public class ExperimentState {
	// state
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
	// step
	/**
	 * 可以绑定数据的实验阶段
	 */
	public static final int RELAT_STEP = 5;
	/**
	 * 报告产生后的实验阶段
	 */
	public static final int REPORT_STEP = 6;
	// method
	public static final Integer MDA = 11;
	public static final int Sureplex = 12;
	public static final int gDNA = 13;
	// app
	public static final int MDA_MR = 88;
	public static final int SurePlex = 94;
	public static final int gDNA_MR = 87;
	public static final HashMap<Integer, Integer> map = new HashMap<Integer, Integer>() {
		private static final long serialVersionUID = 1L;

		{
			put(MDA, MDA_MR);
			put(Sureplex, SurePlex);
			put(gDNA, gDNA_MR);
		}
	};

	/**
	 * 未绑定数据的file_id
	 */
	public static final int UN_RELAT_FILEID = 0;

}
