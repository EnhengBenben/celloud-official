package com.celloud.constants;

import java.util.HashMap;

import org.apache.commons.lang.text.StrSubstitutor;

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

	/**
	 * 构造替换command命令参数的map
	 * 
	 * @param dataList
	 * @param resultPath
	 * @param projectId
	 * @return
	 * @author lin
	 * @date 2016年9月21日下午4:04:22
	 */
	public static HashMap<String, String> getMap(String dataList, String resultPath, Integer projectId) {
		HashMap<String, String> map = new HashMap<>();
		map.put(DATALIST, dataList);
		map.put(RESULTPATH, resultPath);
		map.put(PROJECTID, String.valueOf(projectId));
		return map;
	}

	/**
	 * 构造运行命令
	 * 
	 * @param dataList
	 * @param resultPath
	 * @param projectId
	 * @param command
	 * @return
	 * @author lin
	 * @date 2016年9月21日下午4:04:58
	 */
	public static String getCommand(String dataList, String resultPath, Integer projectId, String command) {
		HashMap<String, String> map = getMap(dataList, resultPath, projectId);
		StrSubstitutor sub = new StrSubstitutor(map);
		return sub.replace(command);
	}

}
