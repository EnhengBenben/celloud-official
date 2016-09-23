package com.celloud.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.celloud.constants.AppDataListType;
import com.celloud.constants.CommandKey;
import com.celloud.constants.SparkPro;
import com.celloud.model.mysql.App;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Project;
import com.celloud.model.mysql.Report;
import com.celloud.model.mysql.Task;
import com.celloud.service.AppService;
import com.celloud.service.DataService;
import com.celloud.service.ExpensesService;
import com.celloud.service.ProjectService;
import com.celloud.service.ReportService;
import com.celloud.service.RunService;
import com.celloud.service.TaskService;
import com.celloud.utils.AppSubmitUtil;
import com.celloud.utils.DataKeyListToFile;
import com.celloud.utils.FileTools;
import com.celloud.utils.Response;

@Service("runService")
public class RunServiceImpl implements RunService {
	Logger logger = LoggerFactory.getLogger(RunServiceImpl.class);

	@Resource
	private ReportService reportService;
	@Resource
	private ProjectService projectService;
	@Resource
	private TaskService taskService;
	@Resource
	private AppService appService;
	@Resource
	private ExpensesService expenseService;
	@Resource
	private DataService dataService;

	@Override
	public Map<String, String> getDataListFile(Integer appId, List<DataFile> dataList) {
		Map<String, String> dataFilePathMap = new HashMap<>();
		if (AppDataListType.FASTQ_PATH.contains(appId)) {
			dataFilePathMap = DataKeyListToFile.onlyFastqPath(dataList);
		} else if (AppDataListType.ONLY_PATH.contains(appId)) {
			dataFilePathMap = DataKeyListToFile.onlyPath(dataList);
		} else if (AppDataListType.PATH_AND_NAME.contains(appId)) {
			dataFilePathMap = DataKeyListToFile.containName(dataList);
		} else if (AppDataListType.SPLIT.contains(appId)) {
			dataFilePathMap = DataKeyListToFile.toSplit(dataList);
		}
		return dataFilePathMap;
	}

	@Override
	public Map<Integer, List<DataFile>> dataGroup(List<DataFile> dataList) {
		Map<Integer, List<DataFile>> map = new HashMap<>();
		for (DataFile dataFile : dataList) {
			Integer appId = dataFile.getAppId();
			List<DataFile> list = null;
			if (map.containsKey(appId)) {
				list = map.get(appId);
			} else {
				list = new ArrayList<>();
			}
			list.add(dataFile);
			map.put(appId, list);
		}
		return map;
	}

	@Override
	public Integer createProject(Integer userId, List<DataFile> dataList, Integer dataReportNum) {
		Project project = new Project();
		String proName = new Date().getTime() + "";
		project.setUserId(userId);
		project.setProjectName(proName);
		Long size = 0l;
		for (DataFile dataFile : dataList) {
			size += dataFile.getSize();
		}
		project.setDataSize(String.valueOf(size));
		project.setDataNum(dataReportNum);
		return projectService.insertProject(project, dataList);
	}

	@Override
	public boolean createReport(Integer userId, Integer appId, Integer projectId, List<DataFile> dataList) {
		Report report = new Report();
		report.setUserId(userId);
		report.setAppId(appId);
		report.setProjectId(projectId);
		return reportService.insertProReport(report, dataList);
	}

	@Override
	public boolean isWait(App app) {
		boolean iswait = false;
		int runningNum = 0;
		Integer appId = app.getAppId();
		if (AppDataListType.SPARK.contains(appId)) {
			runningNum = taskService.findRunningNumByAppId(AppDataListType.SPARK);
			iswait = runningNum < SparkPro.MAXTASK;
		} else {
			runningNum = taskService.findRunningNumByAppId(appId);
			iswait = runningNum < app.getMaxTask() || app.getMaxTask() == 0;
		}
		return iswait;
	}

	@Override
	public Response run(Integer userId, String userName, String dataIds) {
		//1. 检索数据详情
		List<DataFile> dataList = dataService.findDatasById(dataIds);
		//2. 数据分组
		Map<Integer, List<DataFile>> map = dataGroup(dataList);
		//3. 校验余额
		List<Integer> appIdList = new ArrayList<>();
		for (Integer appId : map.keySet()) {
			appIdList.add(appId);
		}
		if (!appService.checkPriceToRun(appIdList, userId)) {
			String result = "余额不足，请充值后再运行";
			logger.info(userName + result);
			return new Response(result);
		}
		//4. 运行
		StringBuffer result = new StringBuffer();
		for (Entry<Integer, List<DataFile>> entry : map.entrySet()) {
			String back = runSingle(userId, userName, entry.getKey(), entry.getValue());
			if (back != null) {
				result.append(back).append(";");
			}
		}
		String error = result.toString();
		if (error.length() != 0) {
			return new Response(error);
		}
		return Response.SUCCESS();
	}

	@Override
	public String runSingle(Integer userId, String userName, Integer appId, List<DataFile> dataList) {
		//1. 创建 dataListFile
		Map<String, String> dataFilePathMap = getDataListFile(appId, dataList);
		String dataReportNum = dataFilePathMap.get(DataKeyListToFile.DATA_REPORT_NUM);
		dataFilePathMap.remove(DataKeyListToFile.DATA_REPORT_NUM);

		//2. 创建项目
		String result = null;
		Integer projectId = createProject(userId, dataList, Integer.valueOf(dataReportNum));
		if (projectId == null) {
			result = "项目创建失败";
			logger.info("{}{}", userName, result);
			return result;
		}

		//3. 创建报告
		boolean reportState = createReport(userId, appId, projectId, dataList);
		if (!reportState) {
			result = appId + "报告创建失败";
			logger.info("{}{}", userName, result);
			return result;
		}

		//4. 投递
		App app = appService.selectByPrimaryKey(appId);
		String appPath = SparkPro.TOOLSPATH + userId + "/" + appId + "/";
		if (!FileTools.checkPath(appPath)) {
			new File(appPath).mkdirs();
		}
		Boolean iswait = isWait(app);
		for (Entry<String, String> entry : dataFilePathMap.entrySet()) {
			String dataKey = entry.getKey();
			String dataListFile = entry.getValue();
			String command = CommandKey.getCommand(dataListFile, appPath, projectId, app.getCommand());
			Task task = taskService.findTaskByDataKeyAndApp(dataKey, appId);
			if (task == null) {
				task = new Task();
				task.setUserId(userId);
				task.setAppId(appId);
				task.setDataKey(dataKey);
			}
			task.setProjectId(projectId);
			task.setCommand(command);
			taskService.updateTask(task);
			Integer taskId = task.getTaskId();
			if (iswait) {
				if (AppDataListType.API_RUN.contains(appId)) {
					AppSubmitUtil.http(appId, dataListFile, appPath, projectId);
				} else {
					AppSubmitUtil.ssh("sge", command, false);
				}
				logger.info("任务{}运行命令：{}", taskId, command);
				taskService.updateToRunning(taskId);
			} else {
				logger.info("数据{}排队运行{}", dataKey, app.getAppName());
			}
		}
		// 保存消费记录
		expenseService.saveRunExpenses(projectId, appId, userId, dataList);
		return result;
	}

}
