package com.celloud.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.bson.types.ObjectId;

import com.alibaba.fastjson.JSONObject;
import com.celloud.sdo.App;
import com.celloud.sdo.Company;
import com.celloud.sdo.Data;
import com.celloud.sdo.Dept;
import com.celloud.sdo.Project;
import com.celloud.sdo.Report;
import com.celloud.sdo.User;
import com.celloud.service.AppService;
import com.celloud.service.DataService;
import com.celloud.service.ProjectService;
import com.celloud.service.ReportService;
import com.celloud.service.UserService;
import com.google.inject.Inject;
import com.mongo.sdo.Split;
import com.nova.action.BaseAction;
import com.nova.constants.DataUpload;
import com.nova.constants.FileFormat;
import com.nova.constants.Mod;
import com.nova.constants.SparkPro;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.portpool.PortPool;
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
    private com.mongo.service.ReportService mReportService;
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
    private static String sparkuserName = machines.get("spark").get(
            Mod.USERNAME);

    // TODO 需要投递到spark集群的app
    private static final List<String> apps = Arrays.asList("92");
    // 初始化perl命令路径
    private static Map<String, String> perlMap = new HashMap<>();
    static {
        SQLUtils sql = new SQLUtils();
        List<App> list = sql.getAllSoftware();
        for (App software : list) {
            perlMap.put("" + software.getSoftwareId(), software.getCommand());
        }
    }

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
            if (apps.contains(appId)) {// 判断是否需要进队列
                String select = apps.toString().substring(1,
                        apps.toString().length() - 1);
                int running = idataService.dataRunning(select);
                log.info("页面运行任务，此时正在运行的任务数：" + running);
                // TODO
                String appPath = basePath + userId + "/" + appId + "/";
                if (SparkPro.NODES >= running) {
                    log.info("资源满足需求，投递任务");
                    submit(appPath, proId + "", dataKeyList, appName,
                            perlMap.get(appId));
                } else {
                    log.info("资源不满足需求，进入队列等待");
                    String command = appPath + "--" + proId + "--"
                            + dataKeyList + "--" + appName + "--" + appId;
                    GlobalQueue.offer(command);
                }
            } else {
                String newPath = PropertiesUtil.toolsOutPath
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
        return "info";
    }

    private void submit(String basePath, String projectId, String dataKeyList,
            String appName, String perl) {
        // 创建要运行的文件列表文件
        String dataListFile = dealDataKeyListContainFileName(projectId,
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
     * 将 dataKeyList 封装成 dataListFile 文件，包含文件名
     * 
     * @param dataKeyList
     * @return
     */
    private String dealDataKeyListContainFileName(String projectId,
            String dataKeyList) {
        StringBuffer sb = new StringBuffer();
        String dataListFile = datalist + new Date().getTime() + "_"
                + new Double(Math.random() * 1000).intValue() + ".txt";
        FileTools.createFile(dataListFile);
        String dataArray[] = dataKeyList.split(";");
        List<String> ports = PortPool.getPorts(dataArray.length, projectId);
        for (int i = 0; i < dataArray.length; i++) {
            String[] dataDetail = dataArray[i].split(",");
            sb.append(dataPath + getArray(dataDetail, 1) + "\t"
                    + getArray(dataDetail, 2) + "\t" + ports.get(i) + "\n");
        }
        FileTools.appendWrite(dataListFile, sb.toString());
        return dataListFile;
    }

    /**
     * 将数据报告分离出来的数据(split流程运行结果)拷贝到数据目录并保存
     * 
     * @return
     */
    public String saveSplitReportData() {
        userId = (Integer) super.session.get("userId");
        String inPath = PropertiesUtil.reportPath + condition;
        String outPath = PropertiesUtil.fileFinal;
        List<String> dataKeyList = getRadomDataKey(conditionInt);
        HashSet<String> resultFiles = FileTools.getFiles(inPath);
        Iterator<String> rFile = resultFiles.iterator();
        int num = 0;
        Long size = null;
        String dataKey = "";
        conditionInt = 0;
        result = "";
        Split split = new Split();
        split.setId(new ObjectId(dataIds));
        split.setUpload(DataUpload.DOING);
        split.setSplitDataIds(result);
        mReportService.editSplit(split);
        while (rFile.hasNext()) {
            String fstr = rFile.next();
            if (!fstr.equals("...tar.gz") && !fstr.equals("..tar.gz")) {
                String extName = fstr.substring(fstr.lastIndexOf(".tar.gz"));
                String resourcePath = inPath + fstr;
                size = new File(resourcePath).length();
                dataKey = FileTools.listIsNull(dataKeyList, num);
                String filePath = outPath + dataKey + extName;
                boolean state = PerlUtils
                        .excuteCopyPerl(resourcePath, filePath);
                if (state) {
                    data = new Data();
                    data.setUserId(userId);
                    data.setFileName(fstr);
                    data.setDataKey(dataKey);
                    data.setAnotherName("来自APP:split");
                    data.setSize(size);
                    data.setPath(filePath);
                    data.setFileFormat(FileFormat.FQ);
                    result += dataService.addData(data) + ",";
                }
                num++;
            }
        }
        split.setUpload(DataUpload.DONE);
        split.setSplitDataIds(result);
        mReportService.editSplit(split);
        return "info";
    }

    private List<String> getRadomDataKey(int num) {
        List<String> resultList = new ArrayList<>();
        List<String> dataKeyList = dataService.getAllDataKey();
        for (int i = 0; i < num; i++) {
            String dataKey = DataUtil.getNewDataKey();
            while (dataKeyList.contains(dataKey)) {
                dataKey = DataUtil.getNewDataKey();
            }
            dataKeyList.add(dataKey);
            resultList.add(dataKey);
        }
        return resultList;
    }

    /**
     * 取数组指定位置的值
     * 
     * @param n
     *            ：数组
     * @param num
     *            ：位置
     * @return：存在则返回值，否则返回null
     */
    private static String getArray(String[] n, int num) {
        return n == null ? null : (n.length > num ? n[num] : null);
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
