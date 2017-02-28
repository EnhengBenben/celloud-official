package com.celloud.task;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.model.mysql.DataFile;

/**
 * 给split文件分组
 * 
 * @author <a href="mailto:sunwendong@celloud.cn">sun8wd</a>
 * @date 2017年2月28日下午12:08:21
 * @version Revision: 1.0
 */
public class SplitDataGroup implements DataGroup {
	private static Logger logger = LoggerFactory.getLogger(FastqDataGroup.class);
	private static List<String> supportedApps = Arrays.asList("split");

	@Override
	public List<String> supportedApps() {
		return supportedApps;
	}

	@Override
	public Map<String, String> group(List<DataFile> dataFiles) {
		Map<String, String> datas = new HashMap<>();
		if (dataFiles == null) {
			logger.warn("文件列表不存在！");
			return datas;
		}
		DataFile r1 = null, r2 = null, txt = null;
		for (DataFile file : dataFiles) {
			r1 = (r1 == null && file.getFileName().contains("R1")) ? file : r1;
			r2 = (r2 == null && file.getFileName().contains("R2")) ? file : r2;
			txt = (txt == null && !file.getFileName().contains("R1") && !file.getFileName().contains("R2")) ? file
					: txt;
		}
		if (r1 == null || r2 == null || txt == null) {
			logger.warn("文件数量小于3个，不能运行：r1={},r2={},txt={}", r1 == null ? "" : r1.getFileName(),
					r2 == null ? "" : r2.getFileName(), txt == null ? "" : txt.getFileName());
			return datas;
		}
		if (!(r1.getUserId().equals(r2.getUserId()) && r1.getUserId().equals(txt.getUserId()))) {
			logger.warn("split文件分属于不同的用户，不能运行：{}:{},{}:{},{}:{}", r1.getUserId(), r1.getFileName(), r2.getUserId(),
					r2.getFileName(), txt.getUserId(), txt.getFileName());
			return datas;
		}
		if (!(r1.getBatch().equals(r2.getBatch()) && r1.getBatch().equals(txt.getBatch()))) {
			logger.warn("split文件分属于不同的批次，不能运行：{}:{},{}:{},{}:{}", r1.getBatch(), r1.getFileName(), r2.getBatch(),
					r2.getFileName(), txt.getBatch(), txt.getFileName());
			return datas;
		}
		if (!r1.getFileName().substring(0, r1.getFileName().lastIndexOf("R1"))
				.equals(r2.getFileName().substring(0, r1.getFileName().lastIndexOf("R2")))
				|| !r1.getFileName().substring(0, r1.getFileName().lastIndexOf("R1"))
						.endsWith(txt.getFileName().substring(0, txt.getFileName().lastIndexOf(".")))) {
			logger.warn("文件名不匹配：r1={},r2={},txt={}", r1.getFileName(), r2.getFileName(), txt.getFileName());
		}
		datas.put("r1", r1.getDataKey());
		datas.put("r2", r2.getDataKey());
		datas.put("txt", txt.getDataKey());
		return datas;
	}

}
