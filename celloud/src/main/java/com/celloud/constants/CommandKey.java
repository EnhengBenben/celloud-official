package com.celloud.constants;

import java.util.HashMap;

/**
 * perl命令替换过程中要替换的值
 * 
 * @author lin
 * @date 2016年1月14日 下午7:39:38
 */
public class CommandKey {

	/**
	 * datalist
	 */
	public static final String DATALIST = "list";

	/**
	 * resultPath
	 */
	public static final String RESULTPATH = "path";

	/**
	 * projectId
	 */
	public static final String PROJECTID = "projectId";

	public static HashMap<String, String> getMap(String dataList, String resultPath, Integer projectId) {
		HashMap<String, String> map = new HashMap<>();
		map.put(DATALIST, dataList);
		map.put(RESULTPATH, resultPath);
		map.put(PROJECTID, String.valueOf(projectId));
		return map;
	}

}
