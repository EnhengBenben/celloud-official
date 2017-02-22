package com.celloud.utils;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import com.celloud.model.mysql.ActionLog;

public class UploadLogUtils {
	private static String path = "/share/data/celloud/logs/upload.log";

	public static void start(HttpServletRequest request, String name) {
		try {
			ActionLog log = UserAgentUtil.getActionLog(request);
			FileUtils.forceMkdir(new File(path).getParentFile());
			FileUtils.write(new File(path),
					log.getUsername() + "," + log.getIp() + "," + name + ",start," + System.currentTimeMillis() + "\n",
					true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void stop(HttpServletRequest request, String name) {
		try {
			ActionLog log = UserAgentUtil.getActionLog(request);
			FileUtils.forceMkdir(new File(path).getParentFile());
			FileUtils.write(new File(path),
					log.getUsername() + "," + log.getIp() + "," + name + ",stop," + System.currentTimeMillis() + "\n",
					true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
