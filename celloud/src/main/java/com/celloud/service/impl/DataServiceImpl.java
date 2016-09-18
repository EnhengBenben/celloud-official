package com.celloud.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.celloud.constants.AppDataListType;
import com.celloud.constants.CommandKey;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.DataState;
import com.celloud.constants.ReportPeriod;
import com.celloud.constants.ReportType;
import com.celloud.constants.SparkPro;
import com.celloud.constants.TaskPeriod;
import com.celloud.mapper.DataFileMapper;
import com.celloud.model.mysql.App;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Project;
import com.celloud.model.mysql.Report;
import com.celloud.model.mysql.Task;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.AppService;
import com.celloud.service.DataService;
import com.celloud.service.ExpensesService;
import com.celloud.service.ProjectService;
import com.celloud.service.ReportService;
import com.celloud.service.TaskService;
import com.celloud.utils.DataKeyListToFile;
import com.celloud.utils.FileTools;
import com.celloud.utils.HttpURLUtils;

/**
 * 数据管理服务实现类
 * 
 * @author han
 * @date 2015年12月23日 下午6:20:22
 */
@Service("dataService")
public class DataServiceImpl implements DataService {
	private static Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);
	@Resource
	DataFileMapper dataFileMapper;
	@Resource
	private DataService dataService;
	@Resource
	private AppService appService;
	@Resource
	private ProjectService projectService;
	@Resource
	private ReportService reportService;
	@Resource
	private TaskService taskService;
	@Resource
	ExpensesService expenseService;

	@Override
	public Integer countData(Integer userId) {
		return dataFileMapper.countData(userId, DataState.ACTIVE);
	}

	@Override
	public Long sumData(Integer userId) {
		return dataFileMapper.sumData(userId, DataState.ACTIVE);
	}

	@Override
	public List<Map<String, String>> countData(Integer userId, String time) {
		return dataFileMapper.countDataByTime(userId, time, DataState.ACTIVE);
	}

	@Override
	public int addDataInfo(DataFile data) {
		dataFileMapper.addDataInfo(data);
		return data.getFileId();
	}

	@Override
	public int updateDataInfoByFileId(DataFile data) {
		return dataFileMapper.updateDataInfoByFileId(data);
	}

	@Override
	public int updateDataInfoByFileIdAndTagId(DataFile data, Integer tagId) {
		dataFileMapper.insertFileTagRelat(data.getFileId(), tagId);
		return dataFileMapper.updateDataInfoByFileId(data);
	}

	@Override
	public List<Map<String, String>> sumData(Integer userId, String time) {
		return dataFileMapper.sumDataByTime(userId, time, DataState.ACTIVE);
	}

	@Override
	public PageList<DataFile> dataAllList(Page page, Integer userId) {
		List<DataFile> lists = dataFileMapper.findAllDataLists(page, userId, DataState.ACTIVE, ReportType.DATA,
				ReportPeriod.COMPLETE);
		return new PageList<>(page, lists);
	}

	@Override
	public PageList<DataFile> dataListByAppId(Page page, Integer userId, Integer appId, String condition, Integer sort,
			String sortDate, String sortName, String sortBatch) {
		List<DataFile> lists = dataFileMapper.findDataListsByAppId(page, userId, DataState.ACTIVE, appId, condition,
				sort, sortDate, sortName, sortBatch);
		return new PageList<>(page, lists);
	}

	@Override
	public PageList<DataFile> dataLists(Page page, Integer userId, String condition, int sort, String sortDateType,
			String sortNameType) {
		List<DataFile> lists = dataFileMapper.findDataLists(page, userId, condition, sort, sortDateType, sortNameType,
				DataState.ACTIVE, ReportType.DATA, ReportPeriod.COMPLETE);
		return new PageList<>(page, lists);
	}

	@Override
	public PageList<DataFile> dataLists(Page page, Integer userId, String condition, int sort, String sortDate,
			String sortBatch, String sortName) {
		List<DataFile> lists = dataFileMapper.findDataListsSortMore(page, userId, condition, sort, sortDate, sortBatch,
				sortName, DataState.ACTIVE, ReportType.DATA, ReportPeriod.COMPLETE);
		return new PageList<>(page, lists);
	}

	@Override
	public Integer getFormatByIds(String dataIds) {
		Map<String, Object> map = dataFileMapper.findFormatByIds(dataIds);
		Integer result = null;
		if (map.get("formatNum") != null && (Long) map.get("formatNum") > 1) {
			result = -1;
		} else {
			result = (Integer) map.get("fileFormat");
		}
		return result;
	}

	@Override
	public List<Integer> findRunningAppData(String dataIds, Integer appId) {
		return dataFileMapper.findRunningAppData(dataIds, appId, DataState.ACTIVE, ReportPeriod.COMPLETE);
	}

	@Override
	public String queryFileSize(String dataIds) {
		return dataFileMapper.queryFileSize(dataIds);
	}

	@Override
	public List<DataFile> findDatasById(String dataIds) {
		// String[] dataIdArr = dataIds.split(",");
		return dataFileMapper.findDatasById(dataIds);
	}

	@Override
	public Integer dataRunning(String appIds) {
		return dataFileMapper.queryDataRunning(appIds, ReportPeriod.NOT_RUN, DataState.ACTIVE, ReportType.DATA);
	}

	@Override
	public Integer delete(String dataIds) {
		String[] dataIdArr = dataIds.split(",");
		int index = 0;
		for (String dataId : dataIdArr) {
			DataFile data = new DataFile();
			data.setFileId(Integer.parseInt(dataId));
			data.setState(DataState.DEELTED);
			index += dataFileMapper.updateByPrimaryKeySelective(data);
		}
		return index;
	}

	@Override
	public DataFile getDataById(Integer dataId) {
		return dataFileMapper.selectByPrimaryKey(dataId);
	}

	@Override
	public List<Map<String, String>> getStrainList(Integer userId) {
		List<Map<String, String>> mlist = new ArrayList<>();
		List<String> list = dataFileMapper.queryStrainList(userId);
		for (String s : list) {
			Map<String, String> map = new HashMap<>();
			map.put("id", s);
			map.put("text", s);
			mlist.add(map);
		}
		return mlist;
	}

	@Override
	public Integer updateDataByIds(String dataIds, DataFile data) {
		return dataFileMapper.updateDataByIds(dataIds, data.getStrain(), data.getSample(), new Date(),
				data.getAnotherName(), data.getDataTags());
	}

	@Override
	public Integer updateDatas(List<DataFile> dataList) {
		Integer index = 0;
		for (DataFile d : dataList) {
			index += dataFileMapper.updateByPrimaryKeySelective(d);
		}
		return index;
	}

	@Override
	public Map<String, String> countUserRunFileNum(Integer userId) {
		return dataFileMapper.countFileNumByUserId(userId);
	}

	@Override
	public List<DataFile> getDatasInProject(Integer projectId) {
		return dataFileMapper.getDatasInProject(projectId);
	}

	@Override
	public List<Map<String, String>> countDataFile(Integer userId) {
		return null;
	}

	@Override
	public DataFile getDataByKey(String dataKey) {
		return dataFileMapper.selectByDataKey(dataKey);
	}

	@Override
	public List<DataFile> selectDataByKeys(String dataKeys) {
		return dataFileMapper.selectByDataKeys(dataKeys);
	}

	@Override
	public int updateByPrimaryKeySelective(DataFile record) {
		return dataFileMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateDataAndTag(DataFile record) {
		// 1.清除历史标签
		dataFileMapper.deleteDataTag(record.getFileId());
		// 2.插入新的标签
		dataFileMapper.insertDataTag(record);
		// 3.修改数据信息
		return dataFileMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<DataFile> getDataByAnotherName(Integer userId, String anotherName) {
		return dataFileMapper.getDataByAnotherName(userId, anotherName, DataState.ACTIVE);
	}

	@Override
	public List<DataFile> getDataByBatchAndFileName(Integer userId, String batch, String fileName) {
		return dataFileMapper.getDataByBatchAndFileName(userId, batch, fileName, DataState.ACTIVE);
	}

	@Override
	public List<String> getBatchList(Integer userId) {
		return dataFileMapper.getBatchList(userId, DataState.ACTIVE);
	}

	@Override
	public PageList<DataFile> filterRockyList(Page page, String sample, String condition, String sidx, String sord) {
		List<DataFile> lists = dataFileMapper.filterRockyList(page, ConstantsData.getLoginUserId(), DataState.ACTIVE,
				ReportType.DATA, ReportPeriod.COMPLETE, sample, condition, sidx, sord);
		return new PageList<>(page, lists);
	}

	@Override
	public String run(Integer userId, String dataIds, String appIds) {
		String result = "";
		logger.info("用户{}使用数据{}运行APP{}", userId, dataIds, appIds);
		String[] dataIdArr = dataIds.split(",");
		String[] appIdArrs = appIds.split(",");
		List<Integer> appIdList = new ArrayList<>();
		for (String s : appIdArrs) {
			appIdList.add(Integer.parseInt(s));
		}
		if (!appService.checkPriceToRun(appIdList, userId)) {
			logger.info("{}的余额不足，提醒充值后再运行", userId);
			return "1";
		}

		// 公共项目信息
		Project project = new Project();
		String proName = new Date().getTime() + "";
		project.setUserId(userId);
		project.setProjectName(proName);
		project.setDataNum(dataIdArr.length);
		project.setDataSize(dataService.queryFileSize(dataIds));

		// 公共报告信息
		Report report = new Report();
		report.setUserId(userId);
		List<DataFile> dataList = dataService.findDatasById(dataIds);
		// 构建运行所需dataListFile文件路径
		Map<String, String> dataFilePathMap = new HashMap<>();// 针对按数据投递APP
		String dataFilePath = "";// 针对按项目投递APP

		List<Integer> list_tmp = new ArrayList<>(appIdList);
		list_tmp.retainAll(AppDataListType.FASTQ_PATH);
		if (list_tmp.size() > 0) {
			dataFilePathMap = DataKeyListToFile.onlyFastqPath(dataList);
			project.setDataNum(Integer.parseInt(dataFilePathMap.get("dataReportNum")));
			dataFilePathMap.remove("dataReportNum");
		} else {
			list_tmp = new ArrayList<>(appIdList);
			list_tmp.retainAll(AppDataListType.ONLY_PATH);
			if (list_tmp.size() > 0) {
				dataFilePath = DataKeyListToFile.onlyPath(dataList);
			} else {
				list_tmp = new ArrayList<>(appIdList);
				list_tmp.retainAll(AppDataListType.PATH_AND_NAME);
				if (list_tmp.size() > 0) {
					dataFilePath = DataKeyListToFile.containName(dataList);
				} else {
					list_tmp = new ArrayList<>(appIdList);
					list_tmp.retainAll(AppDataListType.SPLIT);
					if (list_tmp.size() > 0) {
						dataFilePathMap = DataKeyListToFile.toSplit(dataList);
						project.setDataNum(1);
					}
				}
			}
		}
		// 批量创建项目
		Map<Integer, Integer> appProMap = projectService.insertMultipleProject(project, appIdList, dataIdArr);
		if (appProMap == null) {
			result = "项目创建失败";
			logger.info("{}{}", userId, result);
			return result;
		}

		// 批量创建报告
		List<Integer> failAppIdList = reportService.insertMultipleProReport(report, appProMap, dataIdArr);
		if (failAppIdList.size() > 0) {
			result = appService.findAppNamesByIds(failAppIdList.toString()) + "创建报告失败";
			logger.info("{}{}", userId, result);
			return result;
		}

		// 运行APP详细信息
		List<App> appList = appService.findAppsByIds(appIds);
		String bp = SparkPro.TOOLSPATH + userId + "/";
		for (App app : appList) {
			Integer appId = app.getAppId();
			Integer proId = appProMap.get(appId);
			String appPath = bp + appId + "/";
			if (!FileTools.checkPath(appPath)) {
				new File(appPath).mkdirs();
			}
			if (AppDataListType.FASTQ_PATH.contains(appId) || AppDataListType.SPLIT.contains(appId)) {
				int runningNum;
				Boolean iswait;
				if (AppDataListType.SPARK.contains(appId)) {
					runningNum = taskService.findRunningNumByAppId(AppDataListType.SPARK);
					iswait = runningNum < SparkPro.MAXTASK;
					logger.info("APP{}任务投递到spark", appId);
				} else {
					runningNum = taskService.findRunningNumByAppId(appId);
					iswait = runningNum < app.getMaxTask() || app.getMaxTask() == 0;
				}
				for (Entry<String, String> entry : dataFilePathMap.entrySet()) {
					String dataKey = entry.getKey();
					String dataListFile = entry.getValue();
					Map<String, String> map = CommandKey.getMap(dataListFile, appPath, proId);
					StrSubstitutor sub = new StrSubstitutor(map);
					String command = sub.replace(app.getCommand());
					Task task = taskService.findTaskByDataKeyAndApp(dataKey, appId);
					if (task == null) {
						task = new Task();
						task.setProjectId(proId);
						task.setUserId(userId);
						task.setAppId(appId);
						task.setDataKey(dataKey);
						task.setCommand(command);
						taskService.create(task);
					} else {
						task.setProjectId(proId);
						task.setCommand(command);
						task.setPeriod(TaskPeriod.WAITTING);
						taskService.updateTask(task);
					}
					Integer taskId = task.getTaskId();
					if (iswait) {
						logger.info("任务{}运行命令：{}", taskId, command);
						List<NameValuePair> params = new ArrayList<>();
						params.add(new BasicNameValuePair("list", dataListFile));
						params.add(new BasicNameValuePair("exposePath", appPath));
						params.add(new BasicNameValuePair("projectID", String.valueOf(proId)));
						//TODO path
						HttpURLUtils.httpPostRequest("http://192.168.22.32:8080/API/service/split", params);
						taskService.updateToRunning(taskId);
					} else {
						logger.info("数据{}排队运行{}", dataKey, app.getAppName());
					}
				}
				// 保存消费记录
				expenseService.saveRunExpenses(proId, appId, userId, dataList);
			} else {
				// SGE
				logger.info("celloud 直接向 SGE 投递任务");
				Map<String, String> map = CommandKey.getMap(dataFilePath, appPath, proId);
				StrSubstitutor sub = new StrSubstitutor(map);
				String command = sub.replace(app.getCommand());
				logger.info("运行命令:{}", command);
				List<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("list", dataFilePath));
				params.add(new BasicNameValuePair("exposePath", appPath));
				params.add(new BasicNameValuePair("projectID", String.valueOf(proId)));
				//TODO path
				HttpURLUtils.httpPostRequest("http://192.168.22.32:8080/API/service/split", params);
				// 保存消费记录
				expenseService.saveProRunExpenses(proId, dataList);
			}
		}

		return result;
	}
}
