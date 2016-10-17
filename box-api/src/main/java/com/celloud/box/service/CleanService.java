package com.celloud.box.service;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.celloud.box.config.BoxConfig;
import com.celloud.box.utils.DateUtils;
import com.celloud.box.utils.UploadPath;

@Component
public class CleanService {
	private static Logger logger = LoggerFactory.getLogger(CleanService.class);
	@Resource
	private BoxConfig config;

	@Scheduled(cron = "0 0 0/2 * * ? ") // 每5秒执行一次
	public void clean() {
		logger.info("cleanning...");
		File uploading = new File(UploadPath.getUploadingPath());
		if (!uploading.exists()) {
			return;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 0 - config.getRetentionDays());
		File[] toBeDeleted = uploading.listFiles();
		for (File file : toBeDeleted) {
			Date date = DateUtils.parse(file.getName(), "yyyyMMdd");
			if (date == null || calendar.getTime().after(date)) {
				try {
					FileUtils.forceDelete(file);
					logger.info("deleted：{}", file.getAbsolutePath());
				} catch (IOException e) { 
					logger.error("文件删除失败:{}", file.getAbsolutePath(), e);
				}

			}
		}
	}
}
