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
	 * 向spark投递
	 */
	public static final List<Integer> SPARK = Arrays.asList(86, 92, 93, 99, 100, 101, 118);

	/**
	 * 投递fastq文件
	 */
	public static final List<Integer> FASTQ_PATH = Arrays.asList(110, 111, 112, 114, 118, 123);

	/**
	 * 向split投递
	 */
	public static final List<Integer> SPLIT = Arrays.asList(113);

	/**
	 * 需要路径
	 */
	public static final List<Integer> ONLY_PATH = Arrays.asList(1, 73, 82, 84, 89, 90, 105, 106, 107, 108, 117);

	/**
	 * 需要路径和文件名
	 */
	public static final List<Integer> PATH_AND_NAME = Arrays.asList(11, 80, 85, 87, 88, 91, 94, 104, 109, 116, 119, 120,
			121, 122, 124, 125);

}
