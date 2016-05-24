package com.celloud.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.celloud.constants.CommandKey;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.DataState;
import com.celloud.constants.ExperimentState;
import com.celloud.constants.FileFormat;
import com.celloud.constants.GlobalQueue;
import com.celloud.constants.Mod;
import com.celloud.constants.PortPool;
import com.celloud.constants.ReportType;
import com.celloud.constants.SparkPro;
import com.celloud.model.mongo.TaskQueue;
import com.celloud.model.mysql.App;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Experiment;
import com.celloud.model.mysql.Project;
import com.celloud.model.mysql.Report;
import com.celloud.model.mysql.Task;
import com.celloud.service.AppService;
import com.celloud.service.DataService;
import com.celloud.service.ExpensesService;
import com.celloud.service.ExperimentService;
import com.celloud.service.ProjectService;
import com.celloud.service.ReportService;
import com.celloud.service.SecRoleService;
import com.celloud.service.TaskService;
import com.celloud.utils.ActionLog;
import com.celloud.utils.DataKeyListToFile;
import com.celloud.utils.DataUtil;
import com.celloud.utils.DateUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.MD5Util;
import com.celloud.utils.PerlUtils;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.RunOverUtil;
import com.celloud.utils.SSHUtil;
import com.celloud.utils.XmlUtil;

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
    private AppService appService;
    @Resource
    private ExpensesService expencesService;
    @Resource
    private ExperimentService expService;
    @Resource
    private SecRoleService secService;

    private static Map<String, Map<String, String>> machines = ConstantsData
            .getMachines();
    private static String sgeHost = machines.get("158").get(Mod.HOST);
    private static String sgePwd = machines.get("158").get(Mod.PWD);;
    private static String sgeUserName = machines.get("158").get(Mod.USERNAME);
    private static String sparkhost = machines.get("spark").get(Mod.HOST);
    private static String sparkpwd = machines.get("spark").get(Mod.PWD);
    private static String sparkuserName = machines.get("spark")
            .get(Mod.USERNAME);

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
        String[] dataArr = dataNames.split(",");
        String dataKey = FileTools.getArray(dataArr, 0);
        // 1. 数据库检索
        Map<String, Object> map = taskService
                .findTaskInfoByProId(Integer.parseInt(projectId));
        if (map == null) {
            logger.info("获取项目信息错误" + map);
            return "run error";
        }
        Integer userId = (Integer) map.get("userId");
        Integer appId = (Integer) map.get("appId");
        String title = (String) map.get("title");
        String method = (String) map.get("method");
        List<DataFile> dataList = dataService.selectDataByKeys(dataNames);
        // 2. 利用 python将数据报告插入 mongodb
        StringBuffer command = new StringBuffer();
        command.append("python ").append(SparkPro.TASKOVERPY).append(" ")
                .append(SparkPro.TOOLSPATH).append(" ").append(userId)
                .append(" ").append(appId).append(" ").append(dataNames)
                .append(" ").append(projectId);
        PerlUtils.excutePerl(command.toString());
        // 3. 创建项目结果文件
        StringBuffer basePath = new StringBuffer();
        basePath.append(SparkPro.TOOLSPATH).append(userId).append("/")
                .append(appId).append("/");

        StringBuffer projectFileBf = new StringBuffer();
        projectFileBf.append(basePath).append(projectId).append("/")
                .append(projectId).append(".txt");
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
                    new Class[] { String.class, String.class, String.class,
                            String.class, String.class, List.class })
                    .invoke(ros, reportPath.toString(), dataKey, title,
                            projectFile, projectId, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 5. 通过读取xml文件来生成项目报告
        String xml = null;
        if (new File(projectFile.toString()).exists()) {
            xml = XmlUtil.writeXML(projectFile.toString());
        }

        if (appId == 113) {
            String batch = "";
            String pubName = "";
            for (DataFile d_tmp : dataList) {
                String filename = d_tmp.getFileName();
                if (filename.endsWith(".txt") || filename.endsWith(".lis")) {
                    pubName = filename.substring(0, filename.lastIndexOf("."));
                }
                batch = d_tmp.getBatch();
            }
            String inPath = reportPath + "result/split/";
            HashSet<String> resultFiles = FileTools.getFiles(inPath);
            if (resultFiles != null) {
                Iterator<String> rFile = resultFiles.iterator();
                Long size = null;
                Set<String> secs = secService.findRolesByUserId(userId);
                while (rFile.hasNext()) {
                    String fstr = rFile.next();
                    if (!fstr.equals("...tar.gz") && !fstr.equals("..tar.gz")) {
                        String extName = fstr
                                .substring(fstr.lastIndexOf(".tar.gz"));
                        String resourcePath = inPath + fstr;
                        size = new File(resourcePath).length();
                        DataFile data = new DataFile();
                        data.setUserId(userId);
                        data.setFileName(fstr);
                        data.setState(DataState.DEELTED);
                        int dataId = dataService.addDataInfo(data);
                        String new_dataKey = DataUtil.getNewDataKey(dataId);
                        String filePath = PropertiesUtil.bigFilePath + userId
                                + File.separatorChar
                                + DateUtil.getDateToString("yyyyMMdd")
                                + File.separatorChar + new_dataKey + extName;
                        boolean state = FileTools.nioTransferCopy(
                                new File(resourcePath), new File(filePath));
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
                            dataService.updateDataInfoByFileId(data);
                            if (secs.contains("bsier")) {
                                toRunSplitData(userId, data);
                            }
                        }
                    }
                }
            }
        }
        // 6.结束任务修改项目报告状态
        Task task = taskService.updateToDone(appId, Integer.parseInt(projectId),
                dataKey, dataNames, xml);
        if (task != null) {
            logger.info("任务{}执行完毕", task.getTaskId());
            task = taskService.findFirstTask(appId);
            if (task != null) {
                String toRunCommand = task.getCommand();
                logger.info("运行命令：{}", toRunCommand);
                SSHUtil ssh = new SSHUtil(sgeHost, sgeUserName, sgePwd);
                ssh.sshSubmit(toRunCommand.toString(), false);
                taskService.updateToRunning(task.getTaskId());
            }
        }
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
    public String projectRunOver(String projectId) {
        logger.info("项目运行结束，id:{}", projectId);
        if (StringUtils.isEmpty(projectId)) {
            logger.info("任务运行结束信息不全");
            return "run error";
        }
        // 1. 利用 python 生成数据 pdf，并将数据报告插入 mongodb
        String command = "python " + SparkPro.PYTHONPATH + " "
                + SparkPro.TOOLSPATH + " " + projectId;
        PerlUtils.excutePerl(command);
        // 2. 数据库检索
        int proId = Integer.parseInt(projectId);
        Map<String, Object> map = projectService.findProjectInfoById(proId);
        Integer userId = (Integer) map.get("userId");
        Integer appId = (Integer) map.get("appId");
        String appName = (String) map.get("appName");
        String title = (String) map.get("title");
        String method = (String) map.get("method");

        List<DataFile> dataList = dataService.getDatasInProject(proId);

        // 3. 创建项目结果文件
        StringBuffer basePath = new StringBuffer();
        basePath.append(SparkPro.TOOLSPATH).append(userId).append("/")
                .append(appId).append("/");
        StringBuffer projectFileBf = new StringBuffer();
        projectFileBf.append(basePath).append(projectId).append("/")
                .append(projectId).append(".txt");
        String projectFile = projectFileBf.toString();
        FileTools.createFile(projectFile);
        // 4. 通过反射调用相应app的处理方法，传参格式如下：
        // String appPath, String appName, String appTitle,String
        // projectFile,String projectId, List<DataFile> proDataList
        RunOverUtil rou = new RunOverUtil();
        try {
            rou.getClass().getMethod(method,
                    new Class[] { String.class, String.class, String.class,
                            String.class, String.class, List.class })
                    .invoke(rou, basePath.toString(), appName, title,
                            projectFile, projectId, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 5. 通过读取xml文件来生成项目报告
        String xml = null;
        if (new File(projectFile.toString()).exists()) {
            xml = XmlUtil.writeXML(projectFile);
        }
        // 6. 项目报告插入mysql并修改项目运行状态
        reportService.reportCompeleteByProId(proId, xml);
        if (appId == ExperimentState.MDA_MR || appId == ExperimentState.SurePlex
                || appId == ExperimentState.gDNA_MR) {
            for (DataFile dataFile : dataList) {
                String anotherName = dataFile.getAnotherName() == null ? ""
                        : dataFile.getAnotherName();
                int dataId = dataFile.getFileId();
                List<Experiment> expList = expService.getRelatList(userId,
                        anotherName, dataFile.getDataKey());
                if (expList != null && expList.size() == 1) {
                    Report report = reportService.getReport(userId, appId,
                            Integer.valueOf(projectId), dataId,
                            ReportType.DATA);
                    Experiment exp = expList.get(0);
                    Integer am = exp.getAmplificationMethod();
					if (am == null || am.equals(0)) {
						continue;
					}
                    if (ExperimentState.map.get(am).equals(appId)) {
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
        runQueue(projectId);
        return "run over";
    }

    /**
     * 运行队列里的命令
     */
    @ActionLog(value = "运行结束，释放端口，执行正在排队的命令", button = "运行结束")
    public void runQueue(String projectId) {
        logger.info("{}运行结束，释放端口", projectId);
        PortPool.setPort(projectId);
        while (true) {
            if (GlobalQueue.isEmpty()) {
                logger.info("任务队列为空");
                break;
            }
            String proId = GlobalQueue.peek();
            if (proId != null) {
                TaskQueue tq = reportService
                        .getTaskQueue(Integer.parseInt(proId));
                List<DataFile> list = tq.getDataList();
                int need = list.size();
                logger.info("需要节点 {}可使用节点 {}", need, PortPool.getSize());
                if (PortPool.getSize() < need) {
                    logger.info("节点不满足需要，暂缓投递任务");
                    break;
                }
                String _dataFilePath = DataKeyListToFile.toSpark(proId, list);
                Map<String, String> map = CommandKey.getMap(_dataFilePath,
                        tq.getPath(), Integer.valueOf(proId));
                StrSubstitutor sub = new StrSubstitutor(map);
                String command = sub.replace(tq.getCommand());
                logger.info("资源满足需求，投递任务！运行命令：" + command);
                SSHUtil ssh = new SSHUtil(sparkhost, sparkuserName, sparkpwd);
                ssh.sshSubmit(command, false);
                GlobalQueue.poll();
            }
        }
    }

    @ActionLog(value = "调用Tools端运行结束方法", button = "运行结束")
    @RequestMapping("toolsRunOver")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String toolsRunOver(Integer userId, Integer appId, Integer projectId,
            Integer period, String context) {
        Integer result = reportService.updateReportStateToTools(userId, appId,
                projectId, period, context);
        return String.valueOf(result);
    }

    @ActionLog(value = "bsi运行split分数据", button = "运行split分数据")
    private String toRunSplitData(Integer userId,
            DataFile data) {
        logger.info("bsi自动运行split分数据");
        String result;
        // XXX 运行完split只能运行bsi
        Integer appId = 118;
        Integer dataId = data.getFileId();
        // 公共项目信息
        Project project = new Project();
        String proName = new Date().getTime() + "";
        project.setUserId(userId);
        project.setProjectName(proName);
        project.setDataNum(1);
        project.setDataSize(data.getSize().toString());

        // 公共报告信息
        Report report = new Report();
        report.setUserId(userId);

        // 构建运行所需dataListFile文件路径

        List<DataFile> dataList = new ArrayList<>();
        dataList.add(data);
        Map<String, String> dataFilePathMap = DataKeyListToFile
                .onlyFastqPath(dataList);
        project.setDataNum(
                Integer.parseInt(dataFilePathMap.get("dataReportNum")));
        dataFilePathMap.remove("dataReportNum");
        // 批量创建项目
        Integer proId = projectService.insertProject(project, dataId);
        if (proId == null) {
            result = "血流项目创建失败";
            return result;
        }
        // 批量创建报告
        Integer reportId = reportService.insertProReport(report, dataId);
        if (reportId == 0) {
            result = "血流项目创建报告失败";
            return result;
        }

        // 运行APP详细信息
        App app = appService.findAppById(appId);
        String bp = SparkPro.TOOLSPATH + userId + "/";
        String appPath = bp + appId + "/";
        if (!FileTools.checkPath(appPath)) {
            new File(appPath).mkdirs();
        }
        for (Entry<String, String> entry : dataFilePathMap.entrySet()) {
            String dataKey = entry.getKey();
            String dataListFile = entry.getValue();
            int runningNum = taskService.findRunningNumByAppId(appId);
            Task task = new Task();
            task.setProjectId(proId);
            task.setUserId(userId);
            task.setAppId(appId);
            task.setDataKey(dataKey);
            Map<String, String> map = CommandKey.getMap(dataListFile, appPath,
                    proId);
            StrSubstitutor sub = new StrSubstitutor(map);
            String command = sub.replace(app.getCommand());
            task.setCommand(command);
            taskService.create(task);
            Integer taskId = task.getTaskId();
            if (runningNum < app.getMaxTask() || app.getMaxTask() == 0) {
                logger.info("任务{}运行命令：{}", taskId, command);
                SSHUtil ssh = new SSHUtil(sgeHost, sgeUserName, sgePwd);
                ssh.sshSubmit(command, false);
                taskService.updateToRunning(taskId);
            } else {
                logger.info("数据{}排队运行{}", dataKey, app.getAppName());
            }
        }
        return "begin run:bsi";
    }
}
