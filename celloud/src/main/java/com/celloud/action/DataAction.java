package com.celloud.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.alibaba.fastjson.JSONObject;
import com.celloud.sdo.App;
import com.celloud.sdo.Company;
import com.celloud.sdo.Data;
import com.celloud.sdo.Dept;
import com.celloud.sdo.Project;
import com.celloud.sdo.Report;
import com.celloud.sdo.Task;
import com.celloud.sdo.User;
import com.celloud.service.AppService;
import com.celloud.service.DataService;
import com.celloud.service.ProjectService;
import com.celloud.service.ReportService;
import com.celloud.service.TaskService;
import com.celloud.service.UserService;
import com.celloud.utils.DataKeyListToFile;
import com.google.inject.Inject;
import com.nova.action.BaseAction;
import com.nova.constants.DataState;
import com.nova.constants.FileFormat;
import com.nova.constants.Mod;
import com.nova.constants.SparkPro;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.queue.GlobalQueue;
import com.nova.service.IDataService;
import com.nova.utils.Base64Util;
import com.nova.utils.DataUtil;
import com.nova.utils.FileTools;
import com.nova.utils.PerlUtils;
import com.nova.utils.PropertiesUtil;
import com.nova.utils.RemoteRequests;
import com.nova.utils.SQLUtils;
import com.nova.utils.SSHUtil;
import com.nova.utils.XmlUtil;

/**
 * v3.0数据管理
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-14上午10:22:21
 * @version Revision: 1.0
 */
@ParentPackage("celloud-default")
@Action("data3")
@Results({
        @Result(name = "success", type = "json", params = { "root",
                "conditionInt" }),
        @Result(name = "info", type = "json", params = { "root", "result" }),
        @Result(name = "checkDataRunningSoft", type = "json", params = {
                "root", "intList" }),
        @Result(name = "mapList", type = "json", params = { "root", "mapList" }),
        @Result(name = "getSoftList", type = "redirect", location = "app3!getAppByFormat", params = {
                "condition", "${condition}" }),
        @Result(name = "toDataManage", location = "../../pages/data/myData.jsp"),
        @Result(name = "toMoreInfo", location = "../../pages/data/moreDataInfo.jsp"),
        @Result(name = "toUpdateDatas", location = "../../pages/data/updateDatas.jsp") })
public class DataAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(DataAction.class);
    @Inject
    private DataService dataService;
    @Inject
    private ProjectService proService;
    @Inject
    private ReportService reportService;
    @Inject
    private UserService userService;
    @Inject
    private AppService appService;
    @Inject
    private IDataService idataService;
    @Inject
    private TaskService taskService;
    private PageList<Data> dataPageList;
    private List<Integer> intList;
    private List<Data> dataList;
    private List<Map<String, String>> mapList;
    private Data data;
    private Integer userId;
    private Page page = new Page(50, 0);
    private String sortByName;
    private String sortByDate;
    private String condition;
    private Integer conditionInt;
    private Long fileId;
    private String dataIds;
    private String result;

    private static String basePath = SparkPro.TOOLSPATH;
    private static String dataPath = PropertiesUtil.bigFilePath;
    private static String datalist = PropertiesUtil.datalist;

    private static Map<String, Map<String, String>> machines = XmlUtil.machines;
    private static String sparkhost = machines.get("spark").get(Mod.HOST);
    private static String sparkpwd = machines.get("spark").get(Mod.PWD);
    private static String sparkuserName = machines.get("spark").get(Mod.USERNAME);
    private static String sgeHost = machines.get("158").get(Mod.HOST);
    private static String sgePwd = machines.get("158").get(Mod.PWD);;
    private static String sgeUserName = machines.get("158").get(Mod.USERNAME);
    

    // 初始化perl命令路径
    private static Map<Long, App> appMap = SQLUtils.appMap;

    public String getAllData() {
        userId = (Integer) super.session.get("userId");
        log.info("用户" + super.session.get("userName") + "访问数据管理页面");
        dataPageList = dataService.getAllData(page, userId);
        return "toDataManage";
    }

    public String getDataByCondition() {
        userId = (Integer) super.session.get("userId");
        log.info("用户" + super.session.get("userName") + "根据条件搜索数据");
        dataPageList = dataService.getDataByCondition(page, userId,
                conditionInt, sortByName, sortByDate, condition);
        return "toDataManage";
    }

    public String getAppListByFormat() {
        log.info("用户" + super.session.get("userName") + "获取数据可运行的APP");
        Map<String, Integer> formatMap = dataService.getFormatNumByIds(dataIds);
        if (formatMap.get("formatNum") != null
                && formatMap.get("formatNum") > 1) {
            result = "所选数据格式不统一！";
            return "info";
        } else {
            condition = String.valueOf(formatMap.get("format"));
            return "getSoftList";
        }
    }

    public String checkDataRunningSoft() {
        log.info("验证用户" + super.session.get("userName") + "为数据" + dataIds
                + "选择APP" + conditionInt);
        intList = dataService.getRunningDataBySoft(dataIds, conditionInt);
        return "checkDataRunningSoft";
    }

    public String deleteData() {
        log.info("用户" + super.session.get("userName") + "删除数据" + dataIds);
        conditionInt = dataService.deleteDataByIds(dataIds);
        return "success";
    }

    public String getMoreData() {
        log.info("用户" + super.session.get("userName") + "获取数据" + fileId
                + "别名、物种、样本类型等信息");
        userId = (Integer) super.session.get("userId");
        data = dataService.getDataAndStrain(userId, fileId);
        return "toMoreInfo";
    }

    public String getStrainList() {
        log.info("用户" + super.session.get("userName") + "获取物种信息列表");
        userId = (Integer) super.session.get("userId");
        mapList = dataService.getStrainList(userId);
        return "mapList";
    }

    public String toUpdateDatas() {
        log.info("用户" + super.session.get("userName") + "打开批量逐个编辑多个数据页面");
        dataList = dataService.getDatasByIds(dataIds);
        return "toUpdateDatas";
    }

    public String updateDataByIds() {
        log.info("用户" + super.session.get("userName") + "修改数据" + dataIds);
        conditionInt = dataService.updateData(dataIds, data);
        return "success";
    }

    public String updateManyDatas() {
        log.info("用户" + super.session.get("userName") + "批量修改数据");
        conditionInt = dataService.updateDatas(dataList);
        return "success";
    }

    public String run() {
        log.info("用户" + super.session.get("userName") + "准备运行数据");
        userId = (Integer) super.session.get("userId");
        String[] appIds = condition.split(",");
        String[] dataIdArr = dataIds.split(",");
        String proName = new Date().getTime() + "";
        String fileSize = dataService.getDataSize(dataIds);

        Project project = new Project();
        project.setUserId(userId);
        project.setProjectName(proName);
        project.setFileNum(dataIdArr.length);
        project.setFileSize(fileSize);

        Report report = new Report();
        report.setUserId(userId);
        // 5.根据 appIds 获取 datakeys
        StringBuffer dataResult = new StringBuffer();
        dataList = dataService.getDatasByIds(dataIds);
        for (Data d : dataList) {
            String filename = d.getFileName();
            String datakey = d.getDataKey();
            String anotherName = d.getAnotherName();
            String ext = FileTools.getExtName(filename);
            dataResult
                    .append(datakey)
                    .append(",")
                    .append(datakey)
                    .append(ext)
                    .append(",")
                    .append(filename)
                    .append(",")
                    .append(StringUtils.isEmpty(anotherName) ? null
                            : anotherName).append(";");
        }
        Map<String, Object> userMap = userService.getUserAllInfo(userId);
        Company com = (Company) userMap.get("company");
        User user = (User) userMap.get("user");
        Dept dept = (Dept) userMap.get("dept");
        String email = user.getEmail();
        result = "";
        for (String appId : appIds) {
            Long appId_l = Long.parseLong(appId);
            String appName = appService.getAppNameById(Long.parseLong(appId));
            // 创建项目
            Long proId = proService.insertProject(project);
            log.info("用户" + super.session.get("userName") + "创建项目" + proId);

            if (proId == null) {
                result += appName + "  ";
                log.error("创建项目失败");
                continue;
            }
            // 项目添加数据
            Integer flag = dataService.addDataToPro(dataIdArr, proId);
            log.info("用户" + super.session.get("userName") + "创建项目" + proId
                    + "与数据" + dataIds + "关系" + flag);
            if (flag < 1) {
                result += appName + "  ";
                log.error("创建项目数据关系失败");
                continue;
            }
            // 添加项目报告
            report.setProjectId(proId);
            report.setSoftwareId(Long.parseLong(appId));
            Long reportId = reportService.insertProReport(report);
            if (reportId == null) {
                result += appName + "  ";
                log.error("创建项目报告失败");
                continue;
            }
            Map<String, List<Data>> map = new HashMap<String, List<Data>>();
            if (Integer.parseInt(appId) == 110
                    || Integer.parseInt(appId) == 111
                    || Integer.parseInt(appId) == 112) {
                String dataDetails = FileTools.dataListSort(dataResult
                        .toString());
                String dataArray[] = dataDetails.split(";");
                for (int i = 0; i < dataArray.length; i = i + 2) {
                    String[] dataDetail = dataArray[i].split(",");
                    String[] dataDetail1 = dataArray[i + 1].split(",");
                    List<Data> dataList = dataService
                            .getDataByDataKeys(FileTools
                                    .getArray(dataDetail, 0)
                                    + ","
                                    + FileTools.getArray(dataDetail1, 0));
                    map.put(FileTools.getArray(dataDetail, 0), dataList);
                }
            } else if (Integer.parseInt(appId) == 113) {
                String dataDetails = FileTools.dataListSortNoEnd(dataResult
                        .toString());
                String dataArray[] = dataDetails.split(";");
                dataResult = new StringBuffer();
                dataResult.append(dataDetails);
                for (int i = 0; i < dataArray.length; i = i + 3) {
                    String[] dataDetail = dataArray[i].split(",");
                    String[] dataDetail1 = dataArray[i + 1].split(",");
                    String[] dataDetail2 = dataArray[i + 2].split(",");
                    List<Data> dataList = dataService
                            .getDataByDataKeys(FileTools
                                    .getArray(dataDetail, 0)
                                    + ","
                                    + FileTools.getArray(dataDetail1, 0)
                                    + ","
                                    + FileTools.getArray(dataDetail2, 0));
                    map.put(FileTools.getArray(dataDetail, 0), dataList);
                }
            }
            log.info("用户" + super.session.get("userName") + "开始运行" + appName);
            String dataKeyList = dataResult.toString();
            // TODO
            String appPath = basePath + userId + "/" + appId + "/";
            if (!FileTools.checkPath(appPath)) {
                new File(appPath).mkdirs();
            }
            if (SparkPro.apps.contains(appId)) {// 判断是否需要进队列
                String select = SparkPro.apps.toString().substring(1,
                        SparkPro.apps.toString().length() - 1);
                int running = idataService.dataRunning(select);
                log.info("页面运行任务，此时正在运行的任务数：" + running);
                if (SparkPro.NODES >= running) {
                    log.info("资源满足需求，投递任务");
                    submit(appPath, proId + "", dataKeyList, appName, appMap
                            .get(appId_l).getCommand());
                } else {
                    log.info("资源不满足需求，进入队列等待");
                    String command = appPath + "--" + proId + "--"
                            + dataKeyList + "--" + appName + "--" + appId;
                    GlobalQueue.offer(command);
                }
            } else if (Integer.parseInt(appId) == 114) {
                Collections.sort(dataList, new Comparator<Data>() {
                    @Override
                    public int compare(Data d1, Data d2) {
                        return d1.getFileName().compareTo(d2.getFileName());
                    }
                });
                Iterator<Data> chk_it = dataList.iterator();
                while (chk_it.hasNext()) {
                    dataResult = new StringBuffer();
                    List<Data> _dlist = new ArrayList<>();
                    String dataListFile = datalist + new Date().getTime() + "_"
                            + new Double(Math.random() * 1000).intValue()
                            + ".txt";
                    FileTools.createFile(dataListFile);
                    Data d = chk_it.next();
                    String datakey = d.getDataKey();
                    String _fname = d.getFileName();
                    map = new HashMap<String, List<Data>>();
                    String ext = FileTools.getExtName(_fname);
                    if (_fname.contains("R1") || _fname.contains("R2")) {
                        String s1 = _fname.substring(0,
                                _fname.lastIndexOf("R1"));
                        String s2 = _fname.substring(
                                _fname.lastIndexOf("R1") + 2, _fname.length());
                        Data d1 = chk_it.next();
                        String _fname2 = d1.getFileName();
                        if (_fname2.contains(s1 + "R2")
                                && _fname2.substring(
                                        _fname2.lastIndexOf("R2") + 2,
                                        _fname2.length()).equals(s2)) {
                            dataResult.append(dataPath).append(datakey)
                                    .append(ext).append("\t").append(dataPath)
                                    .append(d1.getDataKey()).append(ext)
                                    .append("\t");
                            _dlist.add(d);
                            _dlist.add(d1);
                            map.put(datakey, _dlist);
                            _fname += "+" + d1.getFileName();
                        }
                    } else {
                        dataResult.append(dataPath).append(datakey).append(ext);
                        _dlist.add(d);
                        map.put(datakey, _dlist);
                    }
                    FileTools.appendWrite(dataListFile, dataResult.toString());
                    int runningNum = taskService.getRunningNumByAppId(appId_l);
                    Task task = new Task();
                    task.setProjectId(proId);
                    task.setUserId(Long.valueOf(userId));
                    task.setAppId(appId_l);
                    task.setDataKey(datakey);
                    StringBuffer command = new StringBuffer(
                            "nohup perl /share/biosoft/perl/PGS_MG/bin/moniter_qsub_url-v1.pl nohup perl ");
                    command.append(appMap.get(appId_l).getCommand())
                            .append(" ").append(dataListFile).append(" ")
                            .append(appPath).append(" ProjectID").append(proId)
                            .append(" &>").append(appPath).append("/")
                            .append(proId).append("/log ");
                    task.setCommand(command.toString());
                    StringBuffer params = new StringBuffer();
                    params.append("appName=")
                            .append(appName)
                            .append("&projectName=")
                            .append(proName)
                            .append("&email=")
                            .append(email)
                            .append("&fileName=")
                            .append(_fname)
                            .append("&projectId=")
                            .append(proId)
                            .append("&dataInfos=")
                            .append(Base64Util.encrypt(JSONObject
                                    .toJSONString(map)))
                            .append("&company=")
                            .append(Base64Util.encrypt(JSONObject
                                    .toJSONString(com)))
                            .append("&user=")
                            .append(Base64Util.encrypt(JSONObject
                                    .toJSONString(user)))
                            .append("&dept=")
                            .append(Base64Util.encrypt(JSONObject
                                    .toJSONString(dept)));
                    task.setParams(params.toString());
                    Long taskId = taskService.create(task);
                    // TODO
                    if (runningNum < 4) {
                        StringBuffer remotePath = new StringBuffer();
                        remotePath.append(PropertiesUtil.toolsOutPath)
                                .append("Procedure!runApp?userId=")
                                .append(userId).append("&appId=").append(appId)
                                .append("&dataKey=").append(datakey)
                                .append("&taskId=").append(taskId)
                                .append("&command=")
                                .append(Base64Util.encrypt(command.toString()))
                                .append("&").append(params);
                        RemoteRequests rr = new RemoteRequests();
                        rr.run(remotePath.toString());
                        taskService.updateToRunning(taskId);
                    } else {
                        log.info("数据" + datakey + "排队运行" + appName);
                    }
                }
            } else {
                if (SparkPro.SGEAPPS.contains(appId)) {
                    // TODO 所有向Tools端投递任务的流程都向这里集中
                    // 最终判断删除，非spark就是SGE
                    log.info("celloud 直接向 SGE 投递任务");
                    // TODO 统一命令形式
                    String command = null;
                    if (appId.equals("116")) {
                        String dataListFile = DataKeyListToFile
                                .containName(dataKeyList);
                        command = appMap.get(Long.parseLong(appId))
                                .getCommand()
                                + " "
                                + dataListFile
                                + " "
                                + appPath
                                + " ProjectID"
                                + proId
                                + " >"
                                + appPath + "ProjectID" + proId + ".log &";
                    } else {
                        String dataListFile = DataKeyListToFile.onlyPath(dataKeyList);
                        command = appMap.get(Long.parseLong(appId))
                                .getCommand()
                                + " "
                                + dataListFile
                                + " "
                                + appPath
                                + " ProjectID"
                                + proId
                                + " &>"
                                + appPath + "ProjectID" + proId + ".log";
                    }
                    log.info("运行命令：" + command);
                    SSHUtil ssh = new SSHUtil(sgeHost, sgeUserName, sgePwd);
                    ssh.sshSubmit(command, false);
                } else {
                    String newPath = PropertiesUtil.toolsPath
                            + "Procedure!runApp?userId=" + userId + "&appId="
                            + appId + "&appName=" + appName + "&projectName="
                            + proName + "&email=" + email + "&dataKeyList="
                            + dataResult.toString() + "&projectId=" + proId
                            + "&dataInfos="
                            + Base64Util.encrypt(JSONObject.toJSONString(map))
                            + "&company="
                            + Base64Util.encrypt(JSONObject.toJSONString(com))
                            + "&user="
                            + Base64Util.encrypt(JSONObject.toJSONString(user))
                            + "&dept="
                            + Base64Util.encrypt(JSONObject.toJSONString(dept));
                    RemoteRequests rr = new RemoteRequests();
                    rr.run(newPath);
                }
            }
        }
        return "info";
    }

    private void submit(String basePath, String projectId, String dataKeyList,
            String appName, String perl) {
        // 创建要运行的文件列表文件
        String dataListFile = DataKeyListToFile.toSpark(projectId,
                dataKeyList);
        // TODO
        String command = "nohup perl  /share/biosoft/perl/wangzhen/PGS/bin/moniter_qsub_url.pl perl "
                + " "
                + perl
                + " "
                + dataListFile
                + " "
                + basePath
                + " ProjectID"
                + projectId
                + " >"
                + basePath
                + "ProjectID"
                + projectId + ".log &";
        log.info("运行命令：" + command);
        SSHUtil ssh = new SSHUtil(sparkhost, sparkuserName, sparkpwd);
        ssh.sshSubmit(command, false);
    }

    /**
     * 将数据报告分离出来的数据(split流程运行结果)拷贝到数据目录并保存
     * 
     * @return
     */
    public String saveSplitReportData() {
        String inPath = PropertiesUtil.reportPath + condition;
        String outPath = PropertiesUtil.fileFinal;
        HashSet<String> resultFiles = FileTools.getFiles(inPath);
        Iterator<String> rFile = resultFiles.iterator();
        Long size = null;
        String dataKey = "";
        conditionInt = 0;
        result = "";
        while (rFile.hasNext()) {
            String fstr = rFile.next();
            if (!fstr.equals("...tar.gz") && !fstr.equals("..tar.gz")) {
                String extName = fstr.substring(fstr.lastIndexOf(".tar.gz"));
                String resourcePath = inPath + fstr;
                size = new File(resourcePath).length();
                data = new Data();
                data.setUserId(userId);
                data.setFileName(fstr);
                data.setState(DataState.DEELTED);
                int dataId = dataService.addData(data);
                dataKey = DataUtil.getNewDataKey(dataId);
                String filePath = outPath + dataKey + extName;
                boolean state = PerlUtils
                        .excuteCopyPerl(resourcePath, filePath);
                if (state) {
                    data.setFileId((long) dataId);
                    data.setDataKey(dataKey);
                    data.setAnotherName("split:" + dataIds);
                    data.setSize(size);
                    data.setPath(filePath);
                    data.setFileFormat(FileFormat.FQ);
                    data.setState(DataState.ACTIVE);
                    result += dataService.updateData(data) + ",";
                }
            }
        }
        return "info";
    }

    public PageList<Data> getDataPageList() {
        return dataPageList;
    }

    public void setDataPageList(PageList<Data> dataPageList) {
        this.dataPageList = dataPageList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getSortByName() {
        return sortByName;
    }

    public void setSortByName(String sortByName) {
        this.sortByName = sortByName;
    }

    public String getSortByDate() {
        return sortByDate;
    }

    public void setSortByDate(String sortByDate) {
        this.sortByDate = sortByDate;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getConditionInt() {
        return conditionInt;
    }

    public void setConditionInt(Integer conditionInt) {
        this.conditionInt = conditionInt;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Integer> getIntList() {
        return intList;
    }

    public void setIntList(List<Integer> intList) {
        this.intList = intList;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<Map<String, String>> getMapList() {
        return mapList;
    }

    public void setMapList(List<Map<String, String>> mapList) {
        this.mapList = mapList;
    }

    public String getDataIds() {
        return dataIds;
    }

    public void setDataIds(String dataIds) {
        this.dataIds = dataIds;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

}
