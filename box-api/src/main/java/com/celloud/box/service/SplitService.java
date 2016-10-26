package com.celloud.box.service;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.celloud.box.config.BoxConfig;
import com.celloud.box.model.SplitFile;
import com.celloud.box.utils.CommandUtils;

@Component
public class SplitService {
	private static Logger logger = LoggerFactory.getLogger(SplitService.class);
	@Resource
	private BoxConfig config;

	public boolean run(SplitFile file) {
		if (!file.check()) {
			return false;
		}
		StringBuffer command = new StringBuffer(config.getSplitCommand());
		try {
			FileUtils.write(new File(file.getListPath()),
					file.getR1Path() + "\t" + file.getR2Path() + "\t" + file.getTxtPath());

		} catch (IOException e) {
			logger.error("运行split失败，写入list文件时出错:\n{}", file.toJSON(), e);
			return false;
		}
		File out = new File(file.getOutPath());
//		try {
//			if (out.exists()) {
//				FileUtils.deleteDirectory(out);
//			}
//		} catch (IOException e) {
//			logger.error("运行split失败，输出目录已存在，删除时出错:\n{}", file.toJSON(), e);
//		}
		out.mkdirs();
		command.append(" ").append(file.getListPath()).append(" ").append(file.getOutPath());
		boolean result = CommandUtils.excute(command.toString());
		if (!result) {
			logger.error("运行split失败，运行命令出错：{}\n{}", command.toString(), file.toJSON());
			return false;
		}
		return true;
	}
}
