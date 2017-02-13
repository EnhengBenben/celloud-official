package com.celloud.constants;

import java.util.Arrays;
import java.util.List;

/**
 * APP运行所需dataKeyList文件类型分类
 * 
 * @author leamo
 * @date 2016年1月13日 下午2:52:39
 */
public class AppDataListType {

	/**
	 * spark
	 */
	public static final List<Integer> SPARK = Arrays.asList(86, 92, 93, 99, 100, 101, 118, 133, 134, 135, 136, 137);

	/**
	 * fastq
	 */
    public static final List<Integer> FASTQ_PATH = Arrays.asList(110, 111, 112,
            114, 123, 126, 127, 128, 145);

	/**
	 * 两对fastq文件 ...(A/B)_(R1/R2).fastq
	 */
	public static final List<Integer> AB_FASTQ_PATH = Arrays.asList(131);
	/**
	 * XLS配对规则
	 */
	public static final List<Integer> XLS_PATH = Arrays.asList(140);

	/**
	 * split
	 */
	public static final List<Integer> SPLIT = Arrays.asList(113);

	/**
	 * path
	 */
    public static final List<Integer> ONLY_PATH = Arrays.asList(1, 73, 82, 84,
            89, 90, 105, 106, 107, 108, 117, 118, 133, 134, 135, 136, 137, 144);

	/**
	 * path and name
	 */
	public static final List<Integer> PATH_AND_NAME = Arrays.asList(11, 80, 85, 87, 88, 91, 94, 104, 109, 116, 119, 120,
			121, 122, 124, 125, 129, 130);

	/**
	 * 调用封装的API运行
	 */
	public static final List<Integer> API_RUN = Arrays.asList(113, 118);
	/**
	 * 调用封装的API运行
	 */
	public static final List<Integer> MQ_RUN = Arrays.asList(123);
	/**
	 * JAVA直接将输入文件做为结果文件处理
	 */
	public static final List<Integer> JAVA_RUN = Arrays.asList(140);
	/**
	 * FSOCG需要排除的id
	 */
	public static final List<String> FSOCG_noId = Arrays.asList("HMBS.ENCTRL.E8E9", "TBP.ENCTRL.E3E4",
			"LRP1.ENCTRL.E57E58", "MRPL13.ENCTRL.E1.E2", "JUN.ENCTRL.E1");

}
