package com.celloud.task;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.model.mysql.DataFile;

/**
 * 给单文件的app运行文件分组
 * 
 * @author <a href="mailto:sunwendong@celloud.cn">sun8wd</a>
 * @date 2017年2月28日下午12:08:21
 * @version Revision: 1.0
 */
public class OnlyPathGroup implements DataGroup {
	private static Logger logger = LoggerFactory.getLogger(FastqDataGroup.class);
	private static List<String> supportedApps = Arrays.asList("BSI");

	@Override
	public Map<String, String> group(List<DataFile> dataFiles) {
		Map<String, String> datas = new HashMap<>();
		if (dataFiles == null || dataFiles.isEmpty()) {
			logger.warn("文件列表不存在！");
			return datas;
		}
		datas.put("data", dataFiles.get(0).getDataKey());
		return datas;
	}

	@Override
	public List<String> supportedApps() {
		return supportedApps;
	}

}
