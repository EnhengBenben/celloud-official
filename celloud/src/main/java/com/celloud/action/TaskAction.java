package com.celloud.action;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.celloud.alimail.AliEmail;
import com.celloud.alimail.AliSubstitution;
import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.DataState;
import com.celloud.constants.ExperimentState;
import com.celloud.constants.FileFormat;
import com.celloud.constants.NoticeConstants;
import com.celloud.constants.ReportType;
import com.celloud.constants.SparkPro;
import com.celloud.message.MessageUtils;
import com.celloud.message.category.MessageCategoryCode;
import com.celloud.message.category.MessageCategoryUtils;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Experiment;
import com.celloud.model.mysql.Project;
import com.celloud.model.mysql.Report;
import com.celloud.model.mysql.Task;
import com.celloud.sendcloud.EmailParams;
import com.celloud.sendcloud.EmailType;
import com.celloud.service.DataService;
import com.celloud.service.ExperimentService;
import com.celloud.service.ProjectService;
import com.celloud.service.ReportService;
import com.celloud.service.RunService;
import com.celloud.service.TaskService;
import com.celloud.utils.ActionLog;
import com.celloud.utils.DataUtil;
import com.celloud.utils.DateUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.MD5Util;
import com.celloud.utils.PerlUtils;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.RunOverUtil;
import com.celloud.utils.XmlUtil;
import com.celloud.wechat.ParamFormat;
import com.celloud.wechat.ParamFormat.Param;
import com.celloud.wechat.constant.WechatParams;

/**
 * 投递任务管理
 * 
 * @author leamo
 * @date 2016年1月14日 下午5:05:52
 */
@Controller
@RequestMapping("api/task")
public class TaskAction {
	Logger logger = LoggerFactory.getLogger(TaskAction.class);
	@Resource
	private TaskService taskService;
	@Resource
	private DataService dataService;
	@Resource
	private ProjectService projectService;
	@Resource
	private ReportService reportService;
	@Resource
	private ExperimentService expService;
	@Resource
	private MessageCategoryUtils mcu;
	@Resource
	private RunService runService;

	/**
	 * 任务运行结束
	 * perl端调用：http://www.celloud.cn/task/taskRunOver.html?projectId=1&dataNames
	 * =data1 .fastq,data2.fastq
	 * 
	 * @return
	 * @author leamo
	 * @date 2016年1月14日 下午5:09:27
	 */
	@ActionLog(value = "任务运行结束，修改项目报告、保存数据报告到mongo、修改报告运行状态、排队运行下一个任务", button = "运行结束")
	@RequestMapping("taskRunOver")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String taskRunOver(String projectId, String dataNames) {
		logger.info("任务运行结束，proId:{},运行数据dataKey：{}", projectId, dataNames);
		if (StringUtils.isEmpty(projectId) || StringUtils.isEmpty(dataNames)) {
			logger.info("任务运行结束信息不全");
			return "run error";
		}
		int proId = Integer.parseInt(projectId);
		// 1. 数据库检索
		Map<String, Object> map = taskService.findTaskInfoByProId(proId);
		if (map == null) {
			logger.info("获取项目信息错误" + map);
			return "run error";
		}
		Integer appId = (Integer) map.get("appId");
		Project project = projectService.selectByPrimaryKey(proId);
		if (project.getState() == 1) {
			logger.info("用户删除的项目回调并尝试运行下一个，projectID：" + projectId);
			runService.runNext(appId);
			return "run error";
		}
		Integer userId = (Integer) map.get("userId");
		String appName = (String) map.get("appName");
		String username = (String) map.get("username");
		String title = (String) map.get("title");
		String method = (String) map.get("method");
		String[] dataArr = dataNames.split(",");
		String dataKey = FileTools.getArray(dataArr, 0);
		List<DataFile> dataList = dataService.selectDataByKeys(dataNames);
		// 2. 利用 python将数据报告插入 mongodb
		StringBuffer command = new StringBuffer();
		command.append("python ").append(SparkPro.TASKOVERPY).append(" ")
				.append(dataList.get(0).getOssPath() == null ? SparkPro.TOOLSPATH
						: ConstantsData.getOfsPath() + "output")
				.append(" ").append(userId).append(" ").append(appId).append(" ").append(dataNames).append(" ")
				.append(projectId);
		PerlUtils.excutePerl(command.toString());
		// 3. 创建项目结果文件
		StringBuffer basePath = new StringBuffer();
		basePath.append(SparkPro.TOOLSPATH).append(userId).append("/").append(appId).append("/");

		StringBuffer projectFileBf = new StringBuffer();
		projectFileBf.append(basePath).append(projectId).append("/").append(projectId).append(".txt");
		String projectFile = projectFileBf.toString();
		FileTools.createFile(projectFile);
		StringBuffer reportPath = new StringBuffer();
		reportPath.append(basePath).append(dataKey).append("/");
		// 4. 通过反射调用相应app的处理方法，传参格式如下：
		// String reportPath, String appName, String appTitle,String
		// projectFile,String projectId, List<DataFile> dataList
		RunOverUtil ros = new RunOverUtil();
		try {
			ros.getClass().getMethod(method,
					new Class[] { String.class, String.class, String.class, String.class, String.class, List.class })
					.invoke(ros, reportPath.toString(), dataKey, title, projectFile, projectId, dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 5. 通过读取xml文件来生成项目报告
		String xml = null;
		if (new File(projectFile.toString()).exists()) {
			xml = XmlUtil.writeXML(projectFile.toString());
		}

		String pubName = "";
		String batch = "";
		String fname = "";
		Integer tagId = 0;
		for (DataFile d_tmp : dataList) {
			String filename = d_tmp.getFileName();
			fname = d_tmp.getFileName();
			if (filename.endsWith(".txt") || filename.endsWith(".lis")) {
				pubName = filename.substring(0, filename.lastIndexOf("."));
			} else {
				batch = d_tmp.getBatch();
				tagId = d_tmp.getTagId();
			}
		}
		if (appId == 113) {
			String inPath = reportPath + "result/split/";
			HashSet<String> resultFiles = FileTools.getFiles(inPath);
			if (resultFiles != null) {
				Iterator<String> rFile = resultFiles.iterator();
				Long size = null;
				while (rFile.hasNext()) {
					String fstr = rFile.next();
					if (!fstr.equals("...tar.gz") && !fstr.equals("..tar.gz")) {
						String extName = fstr.substring(fstr.lastIndexOf(".tar.gz"));
						String resourcePath = inPath + fstr;
						size = new File(resourcePath).length();
						DataFile data = new DataFile();
						data.setUserId(userId);
						data.setFileName(fstr);
						data.setState(DataState.DEELTED);
						int dataId = dataService.addDataInfo(data);
						String new_dataKey = DataUtil.getNewDataKey(dataId);
						String folderByDay = PropertiesUtil.bigFilePath + userId + File.separator
								+ DateUtil.getDateToString("yyyyMMdd");
						File pf = new File(folderByDay);
						if (!pf.exists()) {
							pf.mkdirs();
						}
						String filePath = folderByDay + File.separatorChar + new_dataKey + extName;
						boolean state = FileTools.nioTransferCopy(new File(resourcePath), new File(filePath));
						if (state) {
							data.setFileId(dataId);
							data.setDataKey(new_dataKey);
							data.setAnotherName(pubName);
							data.setSize(size);
							data.setPath(filePath);
							data.setFileFormat(FileFormat.FQ);
							data.setState(DataState.ACTIVE);
							data.setBatch(batch);
							data.setMd5(MD5Util.getFileMD5(filePath));
							dataService.updateDataInfoByFileIdAndTagId(data, tagId);
							// TODO 需要去掉写死的自动运行
							Integer bsiApp = Constants.bsiTags.get(tagId);
							if (bsiApp != null) {
								logger.info("bsi自动运行split分数据");
								runService.runSingle(userId, bsiApp, Arrays.asList(data));
							}
						}
					}
				}
			}
		}
		// 6.结束任务修改项目报告状态
		Task task = taskService.updateToDone(appId, Integer.parseInt(projectId), dataKey, dataNames, xml);
		String startDate = null;
		String endDate = null;
		if (task != null) {
			logger.info("任务{}执行完毕", task.getTaskId());
			// 修改data的isRun状态
			dataList.forEach(dataFile -> {
				dataFile.setIsRun(0);
				dataService.updateByPrimaryKeySelective(dataFile);
			});
			runService.runNext(appId);
			// 构造邮件内容
			startDate = DateUtil.getDateToString(task.getStartDate(), DateUtil.YMDHMS);
			endDate = DateUtil.getDateToString(task.getEndDate(), DateUtil.YMDHMS);
		}
		String tipsName = pubName.equals("") ? fname : pubName;
		// 构造桌面消息
		MessageUtils mu = MessageUtils.get().on(Constants.MESSAGE_USER_CHANNEL)
				.send(NoticeConstants.createMessage("task", "运行完成", "文件【" + tipsName + "】运行应用【" + appName + "】完成"));
		AliEmail aliEmail = AliEmail.template(EmailType.RUN_OVER)
				.substitutionVars(AliSubstitution.sub().set(EmailParams.RUN_OVER.userName.name(), username)
						.set(EmailParams.RUN_OVER.home.name(), ConstantsData.getContextUrl())
						.set(EmailParams.RUN_OVER.projectName.name(), tipsName)
						.set(EmailParams.RUN_OVER.app.name(), appName).set(EmailParams.RUN_OVER.start.name(), startDate)
						.set(EmailParams.RUN_OVER.end.name(), endDate));
		// 构造微信消息
		Param params = ParamFormat.param()
				.set(WechatParams.RUN_OVER.first.name(), "您好，您的数据" + tipsName + " 运行结束", "#222222")
				.set(WechatParams.RUN_OVER.keyword1.name(), appName, "#222222")
				.set(WechatParams.RUN_OVER.keyword2.name(), startDate, "#222222")
				.set(WechatParams.RUN_OVER.keyword3.name(), endDate, "#222222");
		String wechatUrl = null;
		if (appId.equals(123)) {// 华木兰要追加跳转页面
			params.set(WechatParams.RUN_OVER.remark.name(), "点击下方详情查看报告", "#222222");
			wechatUrl = ConstantsData.getContextUrl() + "wechat_rocky.html?projectId=" + projectId + "&dataKey="
					+ dataKey + "&appId=" + appId;
			logger.info("华木兰微信提醒wechatUrl：" + wechatUrl);
		}
		mcu.sendMessage(userId, MessageCategoryCode.REPORT, aliEmail, params, mu, wechatUrl);
		return "run over";
	}

	/**
	 * 项目运行结束，由python进行全部的后续处理
	 * perl端调用：http://www.celloud.cn/task/pythonRunOver.html?projectId=
	 * 
	 * @return
	 */
	@ActionLog(value = "项目运行结束，python进行全部后续处理", button = "运行结束")
	@RequestMapping("pythonRunOver")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String pythonRunOver(String projectId) {
		String command = SparkPro.PYTHONRUNOVER + " " + projectId;
		PerlUtils.excutePerl(command);
		return "run over";
	}

	/**
	 * 项目运行结束之后
	 * perl端调用：http://www.celloud.cn/task/projectRunOver.html?projectId=
	 * 
	 * @return
	 */
	@ActionLog(value = "项目运行结束，插入项目报告、保存数据报告到mongo、修改报告运行状态", button = "运行结束")
	@RequestMapping("projectRunOver")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String projectRunOver(String projectId, String dataKey) {
		logger.info("项目运行结束，id:{},{}", projectId, dataKey);
		if (StringUtils.isEmpty(projectId) || StringUtils.isEmpty(dataKey)) {
			logger.info("任务运行结束信息不全");
			return "run error";
		}
		int proId = Integer.parseInt(projectId);
		Project project = projectService.selectByPrimaryKey(proId);
		if (project.getState() == 1) {
			logger.info("用户删除的项目回调，projectID：" + projectId);
			return "run error";
		}
		// 1. 利用 python 生成数据 pdf，并将数据报告插入 mongodb
		String command = SparkPro.PYTHONPATH + " " + SparkPro.TOOLSPATH + " " + projectId + " " + dataKey;
		PerlUtils.excutePerl(command);
		// 2. 数据库检索
		Map<String, Object> map = projectService.findProjectInfoById(proId);
		Integer userId = (Integer) map.get("userId");
		Integer appId = (Integer) map.get("appId");
		String appName = (String) map.get("appName");
		String username = (String) map.get("username");
		String title = (String) map.get("title");
		String method = (String) map.get("method");
		String projectName = (String) map.get("projectName");

		List<DataFile> dataList = dataService.getDatasInProject(proId);

		// 3. 创建项目结果文件
		StringBuffer basePath = new StringBuffer();
		basePath.append(SparkPro.TOOLSPATH).append(userId).append("/").append(appId).append("/");
		String projectFile = basePath.toString() + projectId + "/" + projectId + ".txt";
		if (!new File(projectFile).exists()) {
			FileTools.appendWrite(projectFile, title);
		}
		FileLock filelock = FileTools.getFileLock(new File(projectFile));
		// 4. 通过反射调用相应app的处理方法，传参格式如下：
		// String appPath, String appName, String appTitle,String
		// projectFile,String projectId, List<DataFile> proDataList,String
		// dataKey
		RunOverUtil rou = new RunOverUtil();
		try {
			rou.getClass()
					.getMethod(method,
							new Class[] { String.class, String.class, String.class, String.class, String.class,
									List.class, String.class, })
					.invoke(rou, basePath.toString(), appName, title, projectFile, projectId, dataList, dataKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 5. 通过读取xml文件来生成项目报告
		String xml = null;
		if (new File(projectFile.toString()).exists()) {
			xml = XmlUtil.writeXML(projectFile);
		}
		try {
			filelock.release();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 6. 项目报告插入mysql并修改项目运行状态
		reportService.reportCompeleteByProId(proId, dataKey, xml);
		if (ExperimentState.apps.contains(appId)) {
			for (DataFile dataFile : dataList) {
				String anotherName = dataFile.getAnotherName() == null ? "" : dataFile.getAnotherName();
				int dataId = dataFile.getFileId();
				List<Experiment> expList = expService.getRelatList(userId, anotherName, dataFile.getDataKey());
				if (expList != null && expList.size() == 1) {
					Report report = reportService.getReport(userId, appId, Integer.valueOf(projectId), dataId,
							ReportType.DATA);
					Experiment exp = expList.get(0);
					Integer am = exp.getAmplificationMethod();
					if (am == null || am.equals(0)) {
						continue;
					}
					Integer sample = exp.getSampleType();
					if (sample == null || sample.equals(0)) {
						continue;
					}
					Integer sequenator = exp.getSequenator();
					if (sequenator == null || sequenator.equals(0)) {
						continue;
					}
					Integer expAPPId = expService.getApp(sample, am, sequenator);
					if (expAPPId != null && expAPPId.equals(appId)) {
						exp.setReportId(report.getReportId());
						exp.setReportDate(report.getEndDate());
						exp.setStep(ExperimentState.REPORT_STEP);
						expService.updateByPrimaryKeySelective(exp);
						logger.info("用户{}数据{}自动绑定报告成功", userId, dataId);
					}
				} else {
					logger.error("用户{}未能检索到与{}匹配的实验流程", userId, dataId);
				}
			}
		}
		DataFile dataFile = dataService.getDataByKey(dataKey);
		dataFile.setIsRun(1);
		dataService.updateByPrimaryKeySelective(dataFile);
		// 构造桌面消息
		MessageUtils mu = MessageUtils.get().on(Constants.MESSAGE_USER_CHANNEL).send(NoticeConstants
				.createMessage("task", "运行完成", "项目【" + projectName + "】下数据【" + dataKey + "】运行【" + appName + "】完成。"));
		// 构造邮件内容
		Report report = reportService.getReportByProjectId(Integer.valueOf(projectId));
		String startDate = DateUtil.getDateToString(report.getCreateDate(), DateUtil.YMDHMS);
		String endDate = report.getEndDate() == null ? null
				: DateUtil.getDateToString(report.getEndDate(), DateUtil.YMDHMS);
		AliEmail aliEmail = AliEmail.template(EmailType.RUN_OVER).substitutionVars(AliSubstitution.sub()
				.set(EmailParams.RUN_OVER.home.name(), ConstantsData.getContextUrl())
				.set(EmailParams.RUN_OVER.userName.name(), username).set(EmailParams.RUN_OVER.data.name(), dataKey)
				.set(EmailParams.RUN_OVER.projectName.name(), projectName).set(EmailParams.RUN_OVER.app.name(), appName)
				.set(EmailParams.RUN_OVER.start.name(), startDate).set(EmailParams.RUN_OVER.end.name(), endDate));
		// 构造微信发送消息
		Param params = ParamFormat.param()
				.set(WechatParams.RUN_OVER.first.name(), "您好，您的项目" + projectName + " 下数据" + dataKey + "运行结束", "#222222")
				.set(WechatParams.RUN_OVER.keyword1.name(), appName, null)
				.set(WechatParams.RUN_OVER.keyword2.name(), startDate, null)
				.set(WechatParams.RUN_OVER.keyword3.name(), endDate, "#222222");
		mcu.sendMessage(userId, MessageCategoryCode.REPORT, aliEmail, params, mu, null);
		return "run over";
	}

}
