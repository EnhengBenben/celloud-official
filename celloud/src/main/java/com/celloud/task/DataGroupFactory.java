package com.celloud.task;

import java.util.Map;

import com.celloud.utils.SpringTool;

/**
 * 数据分组工厂类，用来根据appCode找到对应的数据分组实现类
 * 
 * @author <a href="mailto:sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年12月21日上午10:57:11
 * @version Revision: 1.0
 */
public class DataGroupFactory {

	public static DataGroup get(String appCode) {
		Map<String, DataGroup> groups = SpringTool.getBeans(DataGroup.class);
		return groups.values().stream().filter(g -> g.supportedApps().contains(appCode)).findFirst().orElse(null);
	}
}
