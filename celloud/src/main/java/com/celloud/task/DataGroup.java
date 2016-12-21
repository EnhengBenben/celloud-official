package com.celloud.task;

import java.util.List;
import java.util.Map;

import com.celloud.model.mysql.DataFile;

/**
 * 数据分组接口，用来给同一个样本的数据进行标记，（如华木兰数据，标记出r1和r2来）
 * 
 * @author <a href="mailto:sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年12月21日上午10:55:27
 * @version Revision: 1.0
 */
public interface DataGroup {
	/**
	 * 数据分组，给同一个样本的数据打标记（如华木兰数据，标记出r1和r2来）
	 * 
	 * @param dataFiles
	 * @return
	 */
	public Map<String, String> group(List<DataFile> dataFiles);

	/**
	 * 列举每个数据分组的实现类支持的app，使用appCode<br>
	 * TODO 每个app都需要列举，不利于扩展，增加新的app需要在这里改代码。<br>
	 * 可以给app增加字段，标识app使用的分组实现类是哪个(给实现类起个名字)。
	 */
	public List<String> supportedApps();
}
