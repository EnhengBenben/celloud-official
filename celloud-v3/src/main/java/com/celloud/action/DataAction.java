package com.celloud.action;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.ConstantsData;
import com.celloud.constants.GlobalQueue;
import com.celloud.constants.Mod;
import com.celloud.constants.SparkPro;
import com.celloud.model.App;
import com.celloud.model.DataFile;
import com.celloud.model.DataFileEditForm;
import com.celloud.model.Project;
import com.celloud.model.Report;
import com.celloud.model.Task;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.AppService;
import com.celloud.service.DataService;
import com.celloud.service.ProjectService;
import com.celloud.service.ReportService;
import com.celloud.service.TaskService;
import com.celloud.utils.Base64Util;
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
    private static String dataPath = PropertiesUtil.bigFilePath;
    private static String datalist = PropertiesUtil.datalist;

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
    @RequestMapping("dataList.action")
    public ModelAndView dataList(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size, String condition,
            @RequestParam(defaultValue = "0") int sort,
            @RequestParam(defaultValue = "desc") String sortDateType,
            @RequestParam(defaultValue = "asc") String sortNameType) {
        ModelAndView mv = new ModelAndView("data/data_list");
        Page pager = new Page(page, size);
        PageList<DataFile> dataList = dataService.dataLists(pager,
                ConstantsData.getLoginUserId(), condition, sort, sortDateType,
                sortNameType);
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
    @RequestMapping("getFormatByDataIds.action")
    @ResponseBody
    public Integer getFormatByDataIds(String dataIds) {
        Integer result = dataService.getFormatByIds(dataIds);
        logger.info("用户{}获取{}数据类型", ConstantsData.getLoginUserName(), dataIds);
        return result;
    }

    /**
     * 根据数据类型获取可运行的app
     * 
     * @param formatId
     * @return
     */
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
    @RequestMapping("checkDataRunningApp.action")
    @ResponseBody
    public List<Integer> checkDataRunningApp(String dataIds, Integer appId) {
        List<Integer> dataIdList = dataService.findRunningAppData(dataIds,
                appId);
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
    @RequestMapping("delete.action")
    @ResponseBody
    public Response delete(String dataIds) {
        int result = dataService.delete(dataIds);
        logger.info("用户{}删除数据{}{}", ConstantsData.getLoginUserName(), dataIds,
                result);
        return result > 0 ? Response.DELETE_SUCESS : DELETE_DATA_FAIL;
    }

    /**
     * 修改数据信息页面
     * 
     * @param dataId
     * @return
     * @author leamo
     * @date 2016-1-10 下午10:04:24
     */
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
    @RequestMapping("batchEditDataByIds.action")
    @ResponseBody
    public Integer batchEditDataByIds(String dataIds, DataFile data) {
        Integer result = dataService.updateDataByIds(dataIds, data);
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
    @RequestMapping("run.action")
    @ResponseBody
    public String run(String dataIds, String appIds) {
        logger.info("用户{}使用数据{}运行APP{}", ConstantsData.getLoginUserName(),
                dataIds, appIds);
        String failApp = "";
        Integer userId = ConstantsData.getLoginUserId();
        String email = ConstantsData.getLoginEmail();
        String[] appIdArr = appIds.split(",");
        String[] dataIdArr = dataIds.split(",");
        String proName = new Date().getTime() + "";
        String dataSize = dataService.queryFileSize(dataIds);

        Project project = new Project();
        project.setUserId(userId);
        project.setProjectName(proName);
        project.setDataNum(dataIdArr.length);
        project.setDataSize(dataSize);

        Report report = new Report();
        report.setUserId(userId);
        StringBuffer dataResult = new StringBuffer();
        List<DataFile> dataList = dataService.findDatasById(dataIds);
        for (DataFile d : dataList) {
            dataResult.append(getDataResult(d));
        }
        for (String appIdStr : appIdArr) {
            Integer appId = Integer.parseInt(appIdStr);
            App app = appService.findAppById(appId);
            String appName = app.getAppName();
            project.setProjectId(null);
            // 创建项目
            projectService.insertProject(project);
            Integer proId = project.getProjectId();
            if (proId == null) {
                failApp += appName + ",";
                logger.error("创建项目失败");
                continue;
            }
            // 项目添加数据
            Integer flag = dataService.insertDataProjectRelat(dataIdArr, proId);
            logger.info("用户{}创建项目{}与数据{}关系", ConstantsData.getLoginUserName(),
                    proId, dataIds, flag);
            if (flag < 1) {
                failApp += appName + ",";
                logger.error("创建项目数据关系失败");
                continue;
            }
            // 添加项目报告
            report.setProjectId(proId);
            report.setAppId(appId);
            reportService.insertProReport(report);
            Integer reportId = report.getReportId();
            if (reportId == null) {
                failApp += appName + ",";
                logger.error("创建项目报告失败");
                continue;
            } else {
                logger.info("用户{}创建项目报告{}成功", ConstantsData.getLoginUserName(),
                        reportId);
                report.setReportId(null);
                // 添加数据报告
                Integer _drstate = reportService.insertDataReport(report,
                        dataIdArr);
                if (_drstate > 0) {
                    logger.info("用户{}创建数据报告成功",
                            ConstantsData.getLoginUserName());
                }
            }
            // 判断运行文件顺序是否需要重组
            if (appId == 113) {
                String dataDetails = FileTools
                        .dataListSortNoEnd(dataResult.toString());
                dataResult = new StringBuffer();
                dataResult.append(dataDetails);
            }
            logger.info("用户" + userId + "开始运行" + appId);
            String dataKeyList = dataResult.toString();
            // TODO
            String appPath = basePath + userId + "/" + appId + "/";
            if (!FileTools.checkPath(appPath)) {
                new File(appPath).mkdirs();
            }
            if (SparkPro.apps.contains(appId)) {// 判断是否需要进队列
                String select = SparkPro.apps.toString().substring(1,
                        SparkPro.apps.toString().length() - 1);
                int running = dataService.dataRunning(select);
                logger.info("页面运行任务，此时正在运行的任务数：{}", running);
                if (SparkPro.NODES >= running) {
                    logger.info("资源满足需求，投递任务");
                    submit(appPath, proId + "", dataKeyList, appName,
                            app.getCommand());
                } else {
                    logger.info("资源不满足需求，进入队列等待");
                    String command = appPath + "--" + proId + "--" + dataKeyList
                            + "--" + appName + "--" + appId;
                    GlobalQueue.offer(command);
                }
            } else if (appId == 110 || appId == 111 || appId == 112
                    || appId == 114) {
                sortDataList(dataList);
                Iterator<DataFile> chk_it = dataList.iterator();
                while (chk_it.hasNext()) {
                    String dataListFile = datalist + new Date().getTime() + "_"
                            + new Double(Math.random() * 1000).intValue()
                            + ".txt";
                    FileTools.createFile(dataListFile);
                    DataFile _data = sortFastqFile(chk_it, dataListFile);
                    String dataKey = _data.getDataKey();
                    int runningNum = taskService.getRunningNumByAppId(appId);
                    Task task = new Task();
                    task.setProjectId(proId);
                    task.setUserId(userId);
                    task.setAppId(appId);
                    task.setDataKey(dataKey);
                    StringBuffer command = new StringBuffer(
                            "nohup perl /share/biosoft/perl/PGS_MG/bin/moniter_qsub_url-v1.pl nohup perl ");
                    command.append(app.getCommand()).append(" ")
                            .append(dataListFile).append(" ").append(appPath)
                            .append(" ProjectID").append(proId).append(" &>")
                            .append(appPath).append("/").append(proId)
                            .append("/log ");
                    task.setCommand(command.toString());
                    StringBuffer params = new StringBuffer();
                    params.append("appName=").append(appName)
                            .append("&projectName=").append(proName)
                            .append("&email=").append(email)
                            .append("&fileName=").append(_data.getFileName())
                            .append("&projectId=").append(proId);
                    task.setParams(params.toString());
                    Integer taskId = taskService.create(task);
                    // TODO
                    if (runningNum < 4) {
                        StringBuffer remotePath = new StringBuffer();
                        remotePath.append(PropertiesUtil.toolsPath)
                                .append("Procedure!runApp?userId=")
                                .append(userId).append("&appId=").append(appId)
                                .append("&dataKey=").append(dataKey)
                                .append("&taskId=").append(taskId)
                                .append("&command=")
                                .append(Base64Util.encrypt(command.toString()))
                                .append("&").append(params);
                        RemoteRequests rr = new RemoteRequests();
                        rr.run(remotePath.toString());
                        taskService.updateToRunning(taskId);
                    } else {
                        logger.info("数据" + dataKey + "排队运行" + app.getAppName());
                    }
                }
            } else {
                if (SparkPro.SGEAPPS.contains(appId)) {
                    // TODO 所有向Tools端投递任务的流程都向这里集中
                    // 最终判断删除，非spark就是SGE
                    logger.info("celloud 直接向 SGE 投递任务");
                    String dataListFile = DataKeyListToFile
                            .containName(dataKeyList);
                    String command = app.getCommand() + " " + dataListFile + " "
                            + appPath + " ProjectID" + proId + " >" + appPath
                            + "ProjectID" + proId + ".log &";
                    logger.info("运行命令：" + command);
                    SSHUtil ssh = new SSHUtil(sgeHost, sgeUserName, sgePwd);
                    ssh.sshSubmit(command, false);
                } else {
                    String newPath = PropertiesUtil.toolsPath
                            + "Procedure!runApp?userId=" + userId + "&appId="
                            + appId + "&appName=" + appName + "&projectName="
                            + proName + "&email=" + email + "&dataKeyList="
                            + dataResult.toString() + "&projectId=" + proId;
                    RemoteRequests rr = new RemoteRequests();
                    rr.run(newPath);
                }
            }
        }
        if (failApp != "") {
            logger.info("用户{}使用数据{}运投递APP{}失败",
                    ConstantsData.getLoginUserName(), dataIds, appIds);
        }
        return failApp;
    }

    private void submit(String basePath, String projectId, String dataKeyList,
            String appName, String perl) {
        // 创建要运行的文件列表文件
        String dataListFile = DataKeyListToFile.toSpark(projectId, dataKeyList);
        // TODO
        String command = "nohup perl  /share/biosoft/perl/wangzhen/PGS/bin/moniter_qsub_url.pl perl "
                + " " + perl + " " + dataListFile + " " + basePath
                + " ProjectID" + projectId + " >" + basePath + "ProjectID"
                + projectId + ".log &";
        logger.info("运行命令：" + command);
        SSHUtil ssh = new SSHUtil(sparkhost, sparkuserName, sparkpwd);
        ssh.sshSubmit(command, false);
    }

    /**
     * 运行所需信息
     * 
     * @param d
     * @return
     * @author leamo
     * @date 2016-1-10 下午8:44:38
     */
    private StringBuffer getDataResult(DataFile d) {
        StringBuffer sb = new StringBuffer();
        String filename = d.getFileName();
        String datakey = d.getDataKey();
        String anotherName = d.getAnotherName();
        String ext = FileTools.getExtName(filename);
        sb.append(datakey).append(",").append(datakey).append(ext).append(",")
                .append(filename).append(",")
                .append(StringUtils.isEmpty(anotherName) ? null : anotherName)
                .append(";");
        return sb;
    }

    private void sortDataList(List<DataFile> dataList) {
        Collections.sort(dataList, new Comparator<DataFile>() {
            @Override
            public int compare(DataFile d1, DataFile d2) {
                return d1.getFileName().compareTo(d2.getFileName());
            }
        });
    }

    private DataFile sortFastqFile(Iterator<DataFile> dataFileIt, String file) {
        StringBuffer dataResult = new StringBuffer();
        DataFile d = dataFileIt.next();
        String datakey = d.getDataKey();
        String _fname = d.getFileName();
        String ext = FileTools.getExtName(_fname);
        if (_fname.contains("R1") || _fname.contains("R2")) {
            String s1 = _fname.substring(0, _fname.lastIndexOf("R1"));
            String s2 = _fname.substring(_fname.lastIndexOf("R1") + 2,
                    _fname.length());
            DataFile d1 = dataFileIt.next();
            String _fname2 = d1.getFileName();
            if (_fname2.contains(s1 + "R2") && _fname2
                    .substring(_fname2.lastIndexOf("R2") + 2, _fname2.length())
                    .equals(s2)) {
                dataResult.append(dataPath).append(datakey).append(ext)
                        .append("\t").append(dataPath).append(d1.getDataKey())
                        .append(ext).append("\t");
                _fname += "+" + d1.getFileName();
            }
        } else {
            dataResult.append(dataPath).append(datakey).append(ext);
        }
        FileTools.appendWrite(file, dataResult.toString());
        return d;
    }
}
