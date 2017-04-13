package com.celloud.task;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.celloud.model.mysql.DataFile;

/**
 * 给fastq文件分组
 * 
 * @author <a href="mailto:sunwendong@celloud.cn">sun8wd</a>
 * @date 2017年3月1日下午3:52:10
 * @version Revision: 1.0
 */
@Component
public class FastqDataGroup implements DataGroup {
	private static Logger logger = LoggerFactory.getLogger(FastqDataGroup.class);
	// TODO 每个app都要在这里列举，不利于扩展，可以给app另外一个字段标识
	private static List<String> supportedApps = Arrays.asList("CMP", "CMP_199", "GDD", "MIB", "华木兰", "AccuSeqα",
            "AccuSeqα199", "AccuSeqΩ", "华木兰-白金");

	@Override
	public Map<String, String> group(List<DataFile> dataFiles) {
		Map<String, String> datas = new HashMap<>();
		if (dataFiles == null) {
			logger.warn("文件列表不存在！");
			return datas;
		}
		DataFile r1 = null, r2 = null;
		for (DataFile file : dataFiles) {
			r1 = (r1 == null && file.getFileName().contains("R1")) ? file : r1;
			r2 = (r2 == null && file.getFileName().contains("R2")) ? file : r2;
		}
		if (r1 == null || r2 == null) {
			logger.warn("文件数量不正确，不能运行：r1={},r2={}", r1 == null ? "" : r1.getFileName(),
					r2 == null ? "" : r2.getFileName());
			return datas;
		}
		if (r1.getUserId().intValue() != r2.getUserId().intValue()) {
			logger.warn("文件分别属于不同的用户，不能运行：{}:{},{}:{}", r1.getUserId(), r1.getFileName(), r2.getUserId(),
					r2.getFileName());
			return datas;
		}
		// TODO tag
		if (!r1.getBatch().equals(r2.getBatch())) {
			logger.warn("文件分别属于不同的批次，不能运行：{}:{}；{}:{}", r1.getBatch(), r1.getFileName(), r2.getBatch(),
					r2.getFileName());
			return datas;
		}
		if (!r1.getFileName().substring(0, r1.getFileName().lastIndexOf("R1"))
				.equals(r2.getFileName().substring(0, r2.getFileName().lastIndexOf("R2")))) {
			logger.warn("文件名不匹配：{}\t{}", r1.getFileName(), r2.getFileName());
		}
		datas.put("r1", r1.getDataKey());
		datas.put("r2", r2.getDataKey());
		return datas;
	}

	@Override
	public List<String> supportedApps() {
		return supportedApps;
	}

}
