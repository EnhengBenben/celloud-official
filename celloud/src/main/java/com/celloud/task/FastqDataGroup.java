package com.celloud.task;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.model.mysql.DataFile;

public class FastqDataGroup implements DataGroup {
	private static Logger logger = LoggerFactory.getLogger(FastqDataGroup.class);
	// TODO 每个app都要在这里列举，不利于扩展，可以给app另外一个字段标识
	private static List<String> supportedApps = Arrays.asList("CMP", "CMP_199", "GDD", "MIB", "华木兰", "AccuSeqα",
			"AccuSeqα199", "AccuSeqΩ");

	@Override
	public Map<String, String> group(List<DataFile> dataFiles) {
		Map<String, String> datas = new HashMap<>();
		if (dataFiles == null || dataFiles.size() < 2) {
			logger.warn("文件数量不正确，不能运行：{}", dataFiles == null ? "null" : dataFiles.size());
			return datas;
		}
		String r1 = null, r2 = null;
		DataFile f1 = dataFiles.get(0);
		DataFile f2 = dataFiles.get(1);
		if (f1.getUserId().intValue() != f2.getUserId().intValue()) {
			logger.warn("文件分别属于不同的用户，不能运行：dataKey={},userId={}；dataKey={},userId={}", f1.getDataKey(), f1.getUserId(),
					f2.getDataKey(), f2.getUserId());
			return datas;
		}
		// TODO tag
		if (!f1.getBatch().equals(f2.getBatch())) {
			logger.warn("文件分别属于不同的批次，不能运行：dataKey={},batch={}；dataKey={},batch={}", f1.getDataKey(), f1.getBatch(),
					f2.getDataKey(), f2.getBatch());
			return datas;
		}
		if (!f1.getFileName().replaceAll("R1", "R").replaceAll("R2", "R")
				.equals(f2.getFileName().replaceAll("R2", "R").replaceAll("R1", "R"))) {
			logger.warn("文件名不匹配：{}\t{}", f1.getFileName(), f2.getFileName());
		}
		if (f1.getFileName().contains("R1")) {
			r1 = f1.getDataKey();
			r2 = f2.getDataKey();
		} else {
			r1 = f2.getDataKey();
			r2 = f1.getDataKey();
		}
		datas.put("r1", r1);
		datas.put("r2", r2);
		return datas;
	}

	@Override
	public List<String> supportedApps() {
		return supportedApps;
	}

}
