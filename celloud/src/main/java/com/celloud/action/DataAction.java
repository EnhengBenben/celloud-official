package com.celloud.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.AppDataListType;
import com.celloud.constants.CommandKey;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.GlobalQueue;
import com.celloud.constants.Mod;
import com.celloud.constants.SparkPro;
import com.celloud.model.DataFileEditForm;
import com.celloud.model.mongo.TaskQueue;
import com.celloud.model.mysql.App;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Project;
import com.celloud.model.mysql.Report;
import com.celloud.model.mysql.Task;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.AppService;
import com.celloud.service.DataService;
import com.celloud.service.ProjectService;
import com.celloud.service.ReportService;
import com.celloud.service.TaskService;
import com.celloud.utils.ActionLog;
import com.celloud.utils.DataKeyListToFile;
import com.celloud.utils.FileTools;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.RemoteRequests;
import com.celloud.utils.Response;
import com.celloud.utils.SSHUtil;

/**
 * 数据管理
 * 
 * @author liuqx
 * @date 2015-12-30 下午4:08:06
 */
@Controller
@RequestMapping("data")
public class DataAction {

    Logger logger = LoggerFactory.getLogger(DataAction.class);
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

    private static String basePath = SparkPro.TOOLSPATH;

    private static Map<String, Map<String, String>> machines = ConstantsData
            .getMachines();
    private static String sparkhost = machines.get("spark").get(Mod.HOST);
    private static String sparkpwd = machines.get("spark").get(Mod.PWD);
    private static String sparkuserName = machines.get("spark")
            .get(Mod.USERNAME);
    private static String sgeHost = machines.get("158").get(Mod.HOST);
    private static String sgePwd = machines.get("158").get(Mod.PWD);;
    private static String sgeUserName = machines.get("158").get(Mod.USERNAME);
    private static final Response DELETE_DATA_FAIL = new Response("删除数据失败");

    /**
     * 检索某个项目下的所有数据
     * 
     * @param projectId
     * @return
     * @date 2016-1-9 上午3:43:01
     */
    @ActionLog(value = "检索某个项目下的所有数据（用于数据报告页面右侧菜单）", button = "数据报告")
    @RequestMapping("getDatasInProject")
    @ResponseBody
    public List<DataFile> getDatasInProject(Integer projectId) {
        return dataService.getDatasInProject(projectId);
    }

    /**
     * 获取全部数据列表
     * 
     * @param session
     * @param page
     * @param size
     * @return
     */
    @ActionLog(value = "打开数据管理页面", button = "数据管理")
    @RequestMapping("dataAllList.action")
    public ModelAndView dataAllList(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        ModelAndView mv = new ModelAndView("data/data_list");
        Page pager = new Page(page, size);
        PageList<DataFile> dataList = dataService.dataAllList(pager,
                ConstantsData.getLoginUserId());
        mv.addObject("dataList", dataList);
        logger.info("用户{}打开数据管理", ConstantsData.getLoginUserName());
        return mv;
    }

    /**
     * 根据条件获取数据列表
     * 
     * @param session
     * @param page
     *            当前页
     * @param size
     *            每页行数
     * @param condition
     *            检索条件
     * @param sort
     *            排序字段 0:create_date 1:file_name
     * @param sortType
     *            排序类型
     * @return
     */
    @ActionLog(value = "条件检索数据列表", button = "数据管理搜索/分页")
    @RequestMapping("dataList.action")
    public ModelAndView dataList(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size, String condition,
            @RequestParam(defaultValue = "0") int sort,
            @RequestParam(defaultValue = "desc") String sortDateType,
            @RequestParam(defaultValue = "asc") String sortNameType) {
        Pattern p = Pattern.compile("\\_|\\%|\\'|\"");
        Matcher m = p.matcher(condition);
        StringBuffer con_sb = new StringBuffer();
        while (m.find()) {
            String rep = "\\\\" + m.group(0);
            m.appendReplacement(con_sb, rep);
        }
        m.appendTail(con_sb);
        ModelAndView mv = new ModelAndView("data/data_list");
        Page pager = new Page(page, size);
        PageList<DataFile> dataList = dataService.dataLists(pager,
                ConstantsData.getLoginUserId(), con_sb.toString(), sort,
                sortDateType, sortNameType);
        mv.addObject("dataList", dataList);
        logger.info("用户{}根据条件检索数据列表", ConstantsData.getLoginUserName());
        return mv;
    }

    /**
     * 根据数据编号获取数据类型
     * 
     * @param dataIds
     * @return -1:所选类型大于一种
     */
    @ActionLog(value = "获取数据类型", button = "运行")
    @RequestMapping("getFormatByDataIds.action")
    @ResponseBody
    public Integer getFormatByDataIds(String dataIds) {
        Integer result = 0;
        if (dataIds == null || dataIds.equals(""))
            return result;
        result = dataService.getFormatByIds(dataIds);
        logger.info("用户{}获取{}数据类型", ConstantsData.getLoginUserName(), dataIds);
        return result;
    }

    /**
     * 根据数据类型获取可运行的app
     * 
     * @param formatId
     * @return
     */
    @ActionLog(value = "获取可运行的app", button = "运行")
    @RequestMapping("getRunApp.action")
    @ResponseBody
    public List<App> getRunApp(
            @RequestParam(defaultValue = "0") Integer formatId) {
        List<App> apps = appService
                .findAppsByFormat(ConstantsData.getLoginUserId(), formatId);
        logger.info("用户{}获取可运行数据类型{}的app", ConstantsData.getLoginUserName(),
                formatId);
        return apps;
    }

    /**
     * 验证正在运行某APP的数据
     * 
     * @param dataIds
     * @param appId
     * @return
     */
    @ActionLog(value = "验证数据是否正在运行APP", button = "运行")
    @RequestMapping("checkDataRunningApp.action")
    @ResponseBody
    public List<Integer> checkDataRunningApp(String dataIds, Integer appId) {
        List<Integer> dataIdList = new ArrayList<>();
        if (dataIds == null || dataIds.equals(""))
            return dataIdList;
        dataIdList = dataService.findRunningAppData(dataIds, appId);
        logger.info("用户{}验证数据{}是否正在运行APP{}", ConstantsData.getLoginUserName(),
                dataIds, appId);
        return dataIdList;
    }

    /**
     * 删除数据
     * 
     * @param dataIds
     * @return
     * @author leamo
     * @date 2016-1-10 下午9:49:10
     */
    @ActionLog(value = "删除所选数据", button = "数据删除")
    @RequestMapping("delete.action")
    @ResponseBody
    public Response delete(String dataIds) {
        Integer result = 0;
        if (dataIds != null && !dataIds.equals(""))
            result = dataService.delete(dataIds);
        logger.info("用户{}删除数据{}{}", ConstantsData.getLoginUserName(), dataIds,
                result);
        return result > 0 ? Response.DELETE_SUCCESS : DELETE_DATA_FAIL;
    }

    /**
     * 修改数据信息页面
     * 
     * @param dataId
     * @return
     * @author leamo
     * @date 2016-1-10 下午10:04:24
     */
    @ActionLog(value = "打开分别修改数据列表Modal", button = "数据编辑")
    @RequestMapping("toEachEditDatas.action")
    public ModelAndView toEachEditDatas(String dataIds) {
        ModelAndView mv = new ModelAndView("data/data_all_update");
        List<DataFile> dataList = dataService.findDatasById(dataIds);
        mv.addObject("dataList", dataList);
        logger.info("用户{}打开分别修改数据列表Modal", ConstantsData.getLoginUserName(),
                dataIds);
        return mv;
    }

    /**
     * 获取物种列表
     * 
     * @return
     * @author leamo
     * @date 2016-1-10 下午10:21:13
     */
    @ActionLog(value = "获取物种列表", button = "数据编辑")
    @RequestMapping("getStrainList.action")
    @ResponseBody
    public List<Map<String, String>> getStrainList() {
        List<Map<String, String>> mapList = dataService
                .getStrainList(ConstantsData.getLoginUserId());
        return mapList;
    }

    /**
     * 批量修改数据
     * 
     * @param dataIds
     * @param data
     * @return
     * @author leamo
     * @date 2016-1-10 下午11:04:34
     */
    @ActionLog(value = "批量保存修改数据", button = "保存批量编辑")
    @RequestMapping("batchEditDataByIds.action")
    @ResponseBody
    public Integer batchEditDataByIds(String dataIds, DataFile data) {
        Integer result = 0;
        if (dataIds != null && !dataIds.equals(""))
            result = dataService.updateDataByIds(dataIds, data);
        logger.info("用户{}批量修改数据{}", ConstantsData.getLoginUserName(), dataIds);
        return result;
    }

    /**
     * 批量修改数据
     * 
     * @param dataIds
     * @param data
     * @return
     * @author leamo
     * @date 2016-1-10 下午11:04:34
     */
    @ActionLog(value = "分别保存所修改的数据", button = "保存单个编辑")
    @RequestMapping("eachEditDataByIds.action")
    @ResponseBody
    public Integer eachEditDataByIds(DataFileEditForm dataFileEditForm) {
        Integer result = dataService
                .updateDatas(dataFileEditForm.getDataList());
        logger.info("用户{}分别修改{}个数据", ConstantsData.getLoginUserName(), result);
        return result;
    }

    /**
     * 数据运行
     * 
     * @param dataIds
     * @param appIds
     * @return
     */
    @ActionLog(value = "开始运行方法，调用perl，保存任务信息", button = "开始运行")
    @RequestMapping("run")
    @ResponseBody
    public String run(String dataIds, String appIds) {
        Integer userId = ConstantsData.getLoginUserId();
        String userName = ConstantsData.getLoginUserName();
        String result = "";
        logger.info("用户{}使用数据{}运行APP{}", userName, dataIds, appIds);
        String[] appIdArr = appIds.split(",");
        String[] dataIdArr = dataIds.split(",");

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

        List<String> appIdList = new ArrayList<>(Arrays.asList(appIdArr));
        List<String> list_tmp = new ArrayList<>(appIdList);
        list_tmp.retainAll(AppDataListType.FASTQ_PATH);
        if (list_tmp.size() > 0) {
            dataFilePathMap = DataKeyListToFile.onlyFastqPath(dataList);
            project.setDataNum(
                    Integer.parseInt(dataFilePathMap.get("dataReportNum")));
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
        Map<Integer, Integer> appProMap = projectService
                .insertMultipleProject(project, appIdArr, dataIdArr);
        if (appProMap == null) {
            result = "项目创建失败";
            logger.info("{}{}", userName, result);
            return result;
        }

        // 批量创建报告
        List<Integer> failAppIdList = reportService
                .insertMultipleProReport(report, appProMap, dataIdArr);
        if (failAppIdList.size() > 0) {
            result = appService.findAppNamesByIds(failAppIdList.toString())
                    + "创建报告失败";
            logger.info("{}{}", userName, result);
            return result;
        }

        // TODO 向tools端传参 优化tools投递后删除
        StringBuffer dataResult = new StringBuffer();
        for (DataFile d : dataList) {
            dataResult.append(getDataResult(d));
        }

        // 运行APP详细信息
        List<App> appList = appService.findAppsByIds(appIds);
        String bp = basePath + userId + "/";
        for (App app : appList) {
            Integer appId = app.getAppId();
            String appName = app.getAppName();
            Integer proId = appProMap.get(appId);
            String appPath = bp + appId + "/";
            if (!FileTools.checkPath(appPath)) {
                new File(appPath).mkdirs();
            }
            if (AppDataListType.SPARK.contains(String.valueOf(appId))) {// 判断是否需要进队列
                String select = SparkPro.apps.toString().substring(1,
                        SparkPro.apps.toString().length() - 1);
                int running = dataService.dataRunning(select);
                logger.info("spark 正在运行的任务数：{}", running);
                if (SparkPro.NODES >= running) {
                    String _dataFilePath = DataKeyListToFile
                            .toSpark(proId.toString(), dataList);
                    Map<String, String> map = CommandKey.getMap(_dataFilePath,
                            appPath, proId);
                    StrSubstitutor sub = new StrSubstitutor(map);
                    String command = sub.replace(app.getCommand());
                    logger.info("资源满足需求，投递任务！运行命令：" + command);
                    SSHUtil ssh = new SSHUtil(sparkhost, sparkuserName,
                            sparkpwd);
                    ssh.sshSubmit(command, false);
                } else {
                    TaskQueue tq = new TaskQueue();
                    tq.setAppId(0);
                    tq.setDataKey("");
                    tq.setProjectId(proId);
                    tq.setDataList(dataList);
                    tq.setPath(appPath);
                    tq.setCommand(app.getCommand());
                    reportService.saveTask(tq);
                    logger.info("资源不满足需求，进入队列等待");
                    GlobalQueue.offer(proId.toString());
                }
            } else if (AppDataListType.FASTQ_PATH
                    .contains(String.valueOf(appId))
                    || AppDataListType.SPLIT.contains(String.valueOf(appId))) {
                for (Entry<String, String> entry : dataFilePathMap.entrySet()) {
                    String dataKey = entry.getKey();
                    String dataListFile = entry.getValue();
                    int runningNum = taskService.findRunningNumByAppId(appId);
                    Task task = new Task();
                    task.setProjectId(proId);
                    task.setUserId(userId);
                    task.setAppId(appId);
                    task.setDataKey(dataKey);
                    Map<String, String> map = CommandKey.getMap(dataListFile,
                            appPath, proId);
                    StrSubstitutor sub = new StrSubstitutor(map);
                    String command = sub.replace(app.getCommand());
                    task.setCommand(command);
                    taskService.create(task);
                    Integer taskId = task.getTaskId();
                    if (runningNum < app.getMaxTask()
                            || app.getMaxTask() == 0) {
                        logger.info("任务{}运行命令：{}", taskId, command);
                        SSHUtil ssh = new SSHUtil(sgeHost, sgeUserName, sgePwd);
                        ssh.sshSubmit(command, false);
                        taskService.updateToRunning(taskId);
                    } else {
                        logger.info("数据{}排队运行{}", dataKey, app.getAppName());
                    }
                }
            } else {
				if (SparkPro.SGEAPPS.contains(String.valueOf(appId))) {
					// TODO 所有向Tools端投递任务的流程都向这里集中
					// 最终判断删除，非spark就是SGE
					logger.info("celloud 直接向 SGE 投递任务");
					Map<String, String> map = CommandKey.getMap(dataFilePath, appPath, proId);
					StrSubstitutor sub = new StrSubstitutor(map);
					String command = sub.replace(app.getCommand());
					logger.info("运行命令:{}", command);
					SSHUtil ssh = new SSHUtil(sgeHost, sgeUserName, sgePwd);
					ssh.sshSubmit(command, false);
				} else {
					//TODO 还有 _16S 流程需要从Toold端删除
					String newPath = PropertiesUtil.toolsPath + "Procedure!runApp?userId=" + userId + "&appId=" + appId
							+ "&appName=" + appName + "&projectName=" + proName + "&dataKeyList="
							+ dataResult.toString() + "&projectId=" + proId;
					RemoteRequests rr = new RemoteRequests();
					rr.run(newPath);
				}
            }
        }
        return result;
    }

    /**
     * 运行所需信息
     * 
     * @param d
     * @return
     * @author leamo
     * @date 2016-1-10 下午8:44:38
     */
    // TODO 待删
    private StringBuffer getDataResult(DataFile d) {
        StringBuffer sb = new StringBuffer();
        String filename = d.getFileName();
        String datakey = d.getDataKey();
        String anotherName = d.getAnotherName();
        String ext = FileTools.getExtName(filename);
        sb.append(datakey).append(",").append(datakey).append(ext).append(",")
                .append(filename).append(",")
                // TODO 这个三目有必要么？
                .append(StringUtils.isEmpty(anotherName) ? null : anotherName)
                .append(";");
        return sb;
    }

    @ActionLog(value = "获取所有数据任务列表", button = "我的报告")
    @RequestMapping("taskAllList")
    public ModelAndView taskAllList(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        ModelAndView mv = new ModelAndView("bsi/report_list");
        Page pager = new Page(page, size);
        PageList<Task> pageList = taskService.findTasksByUser(pager,
                ConstantsData.getLoginUserId());
        mv.addObject("pageList", pageList);
        logger.info("血流用户{}查看我的报告列表", ConstantsData.getLoginUserName());
        return mv;
    }

    /**
     * 根据条件获取数据列表
     * 
     * @param session
     * @param page
     *            当前页
     * @param size
     *            每页行数
     * @param condition
     *            检索条件
     * @param sort
     *            排序字段 0:create_date 1:file_name
     * @param sortType
     *            排序类型
     * @return
     */
    @ActionLog(value = "条件检数据索数据列表", button = "数据管理搜索/分页")
    @RequestMapping("taskList")
    public ModelAndView taskList(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size, String condition,
            @RequestParam(defaultValue = "0") int sort,
            @RequestParam(defaultValue = "desc") String sortDate,
            @RequestParam(defaultValue = "asc") String sortPeriod) {
        Pattern p = Pattern.compile("\\_|\\%|\\'|\"");
        Matcher m = p.matcher(condition);
        StringBuffer con_sb = new StringBuffer();
        while (m.find()) {
            String rep = "\\\\" + m.group(0);
            m.appendReplacement(con_sb, rep);
        }
        m.appendTail(con_sb);
        ModelAndView mv = new ModelAndView("bsi/report_list");
        Page pager = new Page(page, size);
        PageList<Task> pageList = taskService.findTasksByUserCondition(pager,
                ConstantsData.getLoginUserId(), condition, sort, sortDate,
                sortPeriod);
        mv.addObject("pageList", pageList);
        logger.info("用户{}根据条件检索数据列表", ConstantsData.getLoginUserName());
        return mv;
    }
}
