package com.celloud.task;

import java.util.HashSet;
import java.util.Set;

/**
 * 数据分组工厂类，用来根据appCode找到对应的数据分组实现类
 * 
 * @author <a href="mailto:sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年12月21日上午10:57:11
 * @version Revision: 1.0
 */
public class DataGroupFactory {
	private static Set<DataGroup> groups = new HashSet<>();
	static {
		// 将所有的实现类都声明到工厂中
		groups.add(new FastqDataGroup());
	}

	public static DataGroup get(String appCode) {
		DataGroup result = null;
		for (DataGroup group : groups) {
			if (group.supportedApps().contains(appCode)) {
				result = group;
				break;
			}
		}
		return result;
	}
}
