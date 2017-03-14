package com.celloud.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.celloud.constants.AppDataListType;
import com.celloud.constants.CommandKey;
import com.celloud.constants.DataState;
import com.celloud.constants.FileFormat;
import com.celloud.constants.IconConstants;
import com.celloud.constants.SparkPro;
import com.celloud.constants.TaskPeriod;
import com.celloud.model.mysql.App;
import com.celloud.model.mysql.ComputeCluster;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Project;
import com.celloud.model.mysql.Report;
import com.celloud.model.mysql.Sample;
import com.celloud.model.mysql.Task;
import com.celloud.service.AppService;
import com.celloud.service.ComputeClusterService;
import com.celloud.service.DataService;
import com.celloud.service.DirectReportService;
import com.celloud.service.ExpensesService;
import com.celloud.service.ProjectService;
import com.celloud.service.ReportService;
import com.celloud.service.RunService;
import com.celloud.service.SampleService;
import com.celloud.service.TaskService;
import com.celloud.task.DataGroup;
import com.celloud.task.DataGroupFactory;
import com.celloud.utils.AppSubmitUtil;
import com.celloud.utils.DataKeyListToFile;
import com.celloud.utils.DataUtil;
import com.celloud.utils.DateUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.Response;
import com.celloud.utils.UploadPathUtils;

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
	@Resource
	private SampleService sampleService;
	@Resource
	private DirectReportService directReportService;
	@Resource
	private ComputeClusterService computeClusterService;

	/**
	 * 参考接口{@link com.celloud.task.DataGroup}
	 * 进行改造，在所有的app全部通过消息队列的方式投递时，将这两个东西合二为一
	 * 
	 * @param appId
	 * @param dataList
	 * @return
	 */
	@Override
	public Map<String, Object> getDataListFile(Integer appId, List<DataFile> dataList) {
		Map<String, Object> dataFilePathMap = new HashMap<>();
		if (AppDataListType.PROJECT.contains(appId)) {
			dataFilePathMap = DataKeyListToFile.projectContainName(dataList);
		} else if (AppDataListType.FASTQ_PATH.contains(appId)) {
			dataFilePathMap = DataKeyListToFile.onlyFastqPath(dataList);
		} else if (AppDataListType.ONLY_PATH.contains(appId)) {
			dataFilePathMap = DataKeyListToFile.onlyPath(dataList);
		} else if (AppDataListType.PATH_AND_NAME.contains(appId)) {
			dataFilePathMap = DataKeyListToFile.containName(dataList);
		} else if (AppDataListType.SPLIT.contains(appId)) {
			dataFilePathMap = DataKeyListToFile.toSplit(dataList);
		} else if (AppDataListType.AB_FASTQ_PATH.contains(appId)) {
			dataFilePathMap = DataKeyListToFile.abFastqPath(dataList);
		} else if (AppDataListType.XLS_PATH.contains(appId)) {
			dataFilePathMap = DataKeyListToFile.pair(dataList, "DNA.xls", "RNA.xls");
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
	public boolean runCheckIsWait(App app) {
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
	public Response run(Integer userId, String dataIds) {
		// 1. 检索数据详情
		List<DataFile> dataList = dataService.findDatasById(dataIds);
		// 2. 数据分组
		Map<Integer, List<DataFile>> map = dataGroup(dataList);
		// 3. 校验余额
		List<Integer> appIdList = new ArrayList<>();
		for (Integer appId : map.keySet()) {
			appIdList.add(appId);
		}
		if (!appService.checkPriceToRun(appIdList, userId)) {
			String result = "余额不足，请充值后再运行";
			logger.info("用户" + userId + result);
			return new Response(result);
		}
		// 4. 运行
		StringBuffer result = new StringBuffer();
		for (Entry<Integer, List<DataFile>> entry : map.entrySet()) {
			String back = runSingle(userId, entry.getKey(), entry.getValue());
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

	@SuppressWarnings("unchecked")
	@Override
	public String runSingle(Integer userId, Integer appId, List<DataFile> dataList) {
		// 1. 创建 dataListFile
		String result = null;
		Map<String, Object> dataFilePathMap = getDataListFile(appId, dataList);
		String dataReportNum = dataFilePathMap.get(DataKeyListToFile.DATA_REPORT_NUM).toString();
		dataFilePathMap.remove(DataKeyListToFile.DATA_REPORT_NUM);
		if (dataFilePathMap.get("canRunDataList") != null) {
			dataList = (List<DataFile>) dataFilePathMap.get("canRunDataList");
			dataFilePathMap.remove("canRunDataList");
		}
		if (dataList == null || dataList.size() == 0) {
			result = "所选数据未成功匹配";
			logger.info("用户{}{}", userId, result);
			return result;
		}

		// 2. 创建项目
		Integer projectId = createProject(userId, dataList, Integer.valueOf(dataReportNum));
		if (projectId == null) {
			result = "项目创建失败";
			logger.info("用户{}{}", userId, result);
			return result;
		}

		// 3. 创建报告
		boolean reportState = createReport(userId, appId, projectId, dataList);
		if (!reportState) {
			result = appId + "报告创建失败";
			logger.info("用户{}{}", userId, result);
			return result;
		}

		// 4. 投递
		App app = appService.selectByPrimaryKey(appId);
		Map<String, String> datas = null;
		DataGroup group = DataGroupFactory.get(app.getCode());
		if (group != null) {
			datas = group.group(dataList);
		}
		String appPath = SparkPro.TOOLSPATH + userId + "/" + appId + "/";
		if (!FileTools.checkPath(appPath)) {
			new File(appPath).mkdirs();
		}
        if (dataList.get(0).getOssPath() != null && appId == 123) {
			appPath = UploadPathUtils.getObjectKeyByPath(UploadPathUtils.getOutPathInOSS(userId, appId));
		}
		for (Entry<String, Object> entry : dataFilePathMap.entrySet()) {
			String dataKey = entry.getKey();
			String dataListFile = entry.getValue().toString();
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
			task.setDatalist(dataListFile);
			task.setResult(appPath);
			task.setSampleId(dataService.getSampleIdByDataKey(dataKey));
			taskService.updateTask(task);
			Integer taskId = task.getTaskId();
			Integer[] dataIds = new Integer[dataList.size()];
			for (int i = 0; i < dataIds.length; i++) {
				dataIds[i] = dataList.get(i).getFileId();
			}
			taskService.saveTaskDataRelat(taskId, dataIds);
			if (AppDataListType.MQ_RUN.contains(appId)) {
				if (datas != null && !datas.isEmpty()) {
					ComputeCluster cluster = computeClusterService.selectByAppId(appId);
					AppSubmitUtil.mq(cluster.getTopic(), app.getCode(), taskId, userId, datas);
					taskService.updateToRunning(taskId);
				}
			} else if (runCheckIsWait(app)) {
				if (AppDataListType.JAVA_RUN.contains(appId)) {
					directReportService.fsocg(userId, appId, dataListFile, appPath, projectId);
				} else if (AppDataListType.API_RUN.contains(appId)) {
					AppSubmitUtil.http(appId, dataListFile, appPath, projectId);
				} else {
					AppSubmitUtil.ssh("sge", command, false);
					logger.info("任务{}运行命令：{}", taskId, command);
				}
				taskService.updateToRunning(taskId);
			} else {
				logger.info("数据{}排队运行{}", dataKey, app.getAppName());
			}
		}
		// 保存消费记录
		expenseService.saveRunExpenses(projectId, appId, userId, dataList);
		return result;
	}

	@Override
	public void runNext(Integer appId) {
		Task t = taskService.findFirstTask(appId);
		if (t != null) {
			if (AppDataListType.API_RUN.contains(appId)) {
				AppSubmitUtil.http(appId, t.getDatalist(), t.getResult(), t.getProjectId());
			} else {
				AppSubmitUtil.ssh("sge", t.getCommand(), false);
			}
			taskService.updateToRunning(t.getTaskId());
		}
	}

	@Override
	public String bsiCheckRun(String batch, Integer dataId, String dataKey, String originalName, Integer userId,
			Integer fileFormat) {
		logger.info("判断是否数据{}上传完即刻运行", originalName);
		Integer appId;
		String pubName = "";
		String storageName = null;
		if (fileFormat == FileFormat.FQ) {
			appId = IconConstants.APP_ID_SPLIT;
			Boolean isR1 = false;
			if (originalName.contains("R1")) {
				pubName = originalName.substring(0, originalName.lastIndexOf("R1"));
				storageName = StringUtils.splitByWholeSeparator(originalName, "_")[0];
				isR1 = true;
			} else if (originalName.contains("R2")) {
				pubName = originalName.substring(0, originalName.lastIndexOf("R2"));
				storageName = StringUtils.splitByWholeSeparator(originalName, "_")[0];
			}
			logger.info("文库编号为{}", storageName);
			Pattern p = Pattern.compile("\\_|\\%");
			Matcher m = p.matcher(pubName);
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				String rep = "\\\\" + m.group(0);
				m.appendReplacement(sb, rep);
			}
			m.appendTail(sb);
			List<DataFile> dataList = dataService.getDataByBatchAndFileName(userId, batch, sb.toString());
			boolean hasR1 = false;
			boolean hasR2 = false;
			for (DataFile d : dataList) {
				String name_tmp = d.getFileName();
				if (name_tmp.contains("R1")) {
					hasR1 = true;
				} else if (name_tmp.contains("R2")) {
					hasR2 = true;
				}
			}
			Task task = new Task();
			task.setUserId(userId);
			task.setDataKey(dataKey);
			task.setPeriod(TaskPeriod.UPLOADING);
			task.setParams(pubName);
			task.setAppId(appId);
			taskService.addOrUpdateUploadTaskByParam(task, isR1);
			if (hasR1 && hasR2) {
				logger.info("完全上传BSI所需的配对文件");
				List<Sample> sampleList = sampleService.getSamplesByStorageName(storageName);
				if (sampleList != null && sampleList.size() > 0) {
					logger.info("文库{}下样本列表长度{}", storageName, sampleList.size());
					DataFile data = new DataFile();
					data.setFileName(pubName + ".txt");
					data.setUserId(userId);
					data.setCreateDate(new Date());
					data.setFileFormat(FileFormat.NONE);
					data.setState(DataState.ACTIVE);
					data.setBatch(batch);
					Integer id = dataService.addDataInfo(data);
					String index_dataKey = DataUtil.getNewDataKey(id);
					data.setFileId(id);
					data.setDataKey(index_dataKey);
					String path = new StringBuffer().append(PropertiesUtil.bigFilePath).append(userId).append("/")
							.append(DateUtil.getDateToString("yyyyMMdd")).append("/").append(index_dataKey + ".txt")
							.toString();
					StringBuffer slist = new StringBuffer();
					for (Sample s : sampleList) {
						slist.append(s.getExperSampleName()).append(":")
								.append(s.getSindex().contains(":")
										? StringUtils.splitByWholeSeparator(s.getSindex(), ":")[1] : s.getSindex())
								.append("\n");
					}
					FileTools.appendWrite(path, slist.toString());
					data.setPath(path);
					data.setSize(FileUtils.sizeOf(new File(path)));
					dataService.updateByPrimaryKeySelective(data);
					dataList.add(data);
					runSingle(userId, appId, dataList);
					logger.info("判断BSI运行成功");
					dataService.delete(String.valueOf(id));
				}
			}
		} else if (fileFormat == FileFormat.YASUO) {
			appId = IconConstants.APP_ID_BSI;
			List<DataFile> dataList = new ArrayList<>();
			DataFile data = dataService.getDataById(dataId);
			dataList.add(data);
			runSingle(userId, appId, dataList);
		}
		return "1";
	}

	@Override
	public void rockyCheckRun(Integer appId, DataFile data) {
		String originalName = data.getFileName();
		String pubName = "";
		if (data.getFileFormat() == FileFormat.FQ || data.getFileFormat() == FileFormat.YASUO) {
			Boolean isR1 = false;
			if (originalName.contains("R1")) {
				pubName = originalName.substring(0, originalName.lastIndexOf("R1"));
				isR1 = true;
			} else if (originalName.contains("R2")) {
				pubName = originalName.substring(0, originalName.lastIndexOf("R2"));
			}
			Pattern p = Pattern.compile("\\_|\\%");
			Matcher m = p.matcher(pubName);
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				String rep = "\\\\" + m.group(0);
				m.appendReplacement(sb, rep);
			}
			m.appendTail(sb);
			List<DataFile> dlist = dataService.getDataByBatchAndFileName(data.getUserId(), data.getBatch(),
					sb.toString());
			boolean hasR1 = false;
			boolean hasR2 = false;
			Map<String, DataFile> datas = new HashMap<String, DataFile>();
			for (DataFile d : dlist) {
				String name_tmp = d.getFileName();
				if (name_tmp.contains("R1") && data.getFileFormat().intValue() == d.getFileFormat().intValue()) {
					hasR1 = true;
				} else if (name_tmp.contains("R2") && data.getFileFormat().intValue() == d.getFileFormat().intValue()) {
					hasR2 = true;
				}
				// 排除同一个文件多次上传的问题
				datas.put(d.getMd5(), d);
			}
			Task task = new Task();
			task.setUserId(data.getUserId());
			task.setDataKey(data.getDataKey());
			task.setPeriod(TaskPeriod.UPLOADING);// TODO 小心处理这个状态，将关系到数据的统计
			task.setParams(pubName);
			task.setAppId(IconConstants.APP_ID_ROCKY);
			taskService.addOrUpdateUploadTaskByParam(task, isR1);
			if (hasR1 && hasR2) {
				logger.info("数据{}上传完可以运行", originalName);
				runSingle(data.getUserId(), appId, new ArrayList<>(datas.values()));
			}
		} else {
			logger.info("数据{}上传完不可以运行", originalName);
		}
	}
}
