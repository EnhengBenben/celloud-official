package com.celloud.utils;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.celloud.constants.AppDataListType;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.Mod;
import com.celloud.constants.SparkPro;
import com.celloud.model.mysql.App;
import com.celloud.model.mysql.Task;
import com.celloud.service.AppService;
import com.celloud.service.TaskService;

public class TaskUtils {
	private static Map<String, Map<String, String>> machines = ConstantsData.getMachines();
	private static String sparkhost = machines.get("spark").get(Mod.HOST);
	private static String sparkpwd = machines.get("spark").get(Mod.PWD);
	private static String sparkuserName = machines.get("spark").get(Mod.USERNAME);
	private static String sgeHost = machines.get("158").get(Mod.HOST);
	private static String sgePwd = machines.get("158").get(Mod.PWD);;
	private static String sgeUserName = machines.get("158").get(Mod.USERNAME);

	@Autowired
	private TaskService taskService;
	@Autowired
	private AppService appService;

	@Async
	public void killTask(Integer userId, Integer appId, Integer projectId) {
		String param = SparkPro.TOOLSPATH + userId + "/" + appId + " ProjectID" + projectId;
		String command = null;
		SSHUtil ssh = null;
		if (AppDataListType.SPARK.contains(appId)) {
			command = SparkPro.SPARKKILL + " " + param;
			ssh = new SSHUtil(sparkhost, sparkuserName, sparkpwd);
			ssh.sshSubmit(command, false);
		} else {
			command = SparkPro.SGEKILL + " " + param;
			ssh = new SSHUtil(sgeHost, sgeUserName, sgePwd);
			ssh.sshSubmit(command, false);
		}
		if (AppDataListType.FASTQ_PATH.contains(appId) || AppDataListType.SPLIT.contains(appId)) {
			taskService.deleteTask(projectId);
			Task task = taskService.findFirstTask(appId);
			if (task != null) {
				int runningNum = taskService.findRunningNumByAppId(appId);
				App app = appService.selectByPrimaryKey(appId);
				if (runningNum < app.getMaxTask() || app.getMaxTask() == 0) {
					if (AppDataListType.SPARK.contains(appId)) {
						ssh = new SSHUtil(sparkhost, sparkuserName, sparkpwd);
					} else {
						ssh = new SSHUtil(sgeHost, sgeUserName, sgePwd);
					}
					ssh.sshSubmit(task.getCommand(), false);
					taskService.updateToRunning(task.getTaskId());
				}
			}
		}
	}
}
