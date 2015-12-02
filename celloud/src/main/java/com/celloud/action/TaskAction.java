package com.celloud.action;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.celloud.sdo.App;
import com.celloud.sdo.Data;
import com.celloud.sdo.Project;
import com.celloud.sdo.Task;
import com.celloud.sdo.User;
import com.celloud.service.DataService;
import com.celloud.service.RunOverService;
import com.celloud.service.TaskService;
import com.google.inject.Inject;
import com.nova.action.BaseAction;
import com.nova.constants.AppNameIDConstant;
import com.nova.constants.DataState;
import com.nova.constants.FileFormat;
import com.nova.constants.SparkPro;
import com.nova.email.EmailProjectEnd;
import com.nova.service.IReportService;
import com.nova.utils.Base64Util;
import com.nova.utils.DataUtil;
import com.nova.utils.DateUtil;
import com.nova.utils.FileTools;
import com.nova.utils.PerlUtils;
import com.nova.utils.PropertiesUtil;
import com.nova.utils.RemoteRequests;
import com.nova.utils.XmlUtil;

/**
 * 任务应用
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-11-3下午5:18:22
 * @version Revision: 1.0
 */
@ParentPackage("celloud-default")
@Action("task")
@Results({ @Result(name = "success", type = "json", params = { "root",
        "conditionInt" }) })
public class TaskAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(DataAction.class);
    @Inject
    private TaskService taskService;
    @Inject
    private DataService dataService;
    @Inject
    private IReportService reportService;
    private Task task;
    private Integer conditionInt;
    private Integer projectId;
    /** 文件名称 eg: filename1,filename2 */
    private String dataNames;

    /**
     * 修改任务状态并执行排队任务
     * 
     * @return
     */
    public String updateTaskState() {
        taskService.updateToDone(task.getTaskId());
        log.info("任务" + task.getTaskId() + "执行完毕");
        task = taskService.getFirstTask(task.getAppId());
        if (task != null) {
            StringBuffer remotePath = new StringBuffer();
            Long taskId = task.getTaskId();
            remotePath.append(PropertiesUtil.toolsOutPath)
                    .append("Procedure!runApp?userId=")
                    .append(task.getUserId()).append("&appId=")
                    .append(task.getAppId()).append("&dataKey=")
                    .append(task.getDataKey()).append("&taskId=")
                    .append(taskId).append("&command=")
                    .append(Base64Util.encrypt(task.getCommand())).append("&")
                    .append(task.getParams());
            RemoteRequests rr = new RemoteRequests();
            rr.run(remotePath.toString());
            log.info("任务" + task.getTaskId() + "开始完成投递");
            taskService.updateToRunning(taskId);
        }
        conditionInt = 1;
        return SUCCESS;
    }

    /**
     * 任务运行结束
     * perl端调用：http://www.celloud.cn/task!runOver?projectId=1&dataNames=data1
     * .fastq,data2.fastq
     * 
     * @return
     */
    public String runOver() {
        log.info("任务运行结束，proId:" + projectId + ",运行数据dataKey：" + dataNames);
        String[] dataArr = dataNames.split(",");
        StringBuffer dataKeys = new StringBuffer();
        String dataKey = "";
        for (int i = 0; i < dataArr.length; i++) {
            String dname = dataArr[i];
            if (i == 0) {
                dataKey = dname;
            }
            dataKeys.append(dname);
            if (i < dataArr.length - 1) {
                dataKeys.append(",");
            }
        }
        // 1. 数据库检索
        Map<String, Object> map = taskService
                .getTaskInfoByProId((long) projectId);
        Project pro = (Project) map.get("project");
        App app = (App) map.get("app");
        User user = (User) map.get("user");
        Integer userId = user.getUserId();
        Long appId = app.getSoftwareId();
        List<Data> dataList = dataService
                .getDataByDataKeys(dataKeys.toString());
        // 2. 利用 python将数据报告插入 mongodb
        StringBuffer command = new StringBuffer();
        command.append("python ").append(SparkPro.TASKOVERPY).append(" ")
                .append(SparkPro.TOOLSPATH).append(" ").append(userId)
                .append(" ").append(appId).append(" ").append(dataKeys)
                .append(" ").append(projectId);
        PerlUtils.executeGadgetsPerl(command.toString());
        // 3. 创建项目结果文件
        StringBuffer basePath = new StringBuffer();
        basePath.append(SparkPro.TOOLSPATH).append(userId).append("/")
                .append(appId).append("/");
        StringBuffer projectFile = new StringBuffer();
        projectFile.append(basePath).append(projectId).append("/")
                .append(projectId).append(".txt");
        StringBuffer reportPath = new StringBuffer();
        reportPath.append(basePath).append(dataKey).append("/");
        // 4. 通过反射调用相应app的处理方法，传参格式如下：
        // String reportPath, String appName, String appTitle,String
        // projectFile,String projectId, List<Data> dataList
        RunOverService ros = new RunOverService();
        try {
            ros.getClass()
                    .getMethod(
                            app.getMethod(),
                            new Class[] { String.class, String.class,
                                    String.class, String.class, String.class,
                                    List.class, Integer.class })
                    .invoke(ros, reportPath.toString(), dataKey,
                            app.getTitle(), projectFile.toString(),
                            projectId.toString(), dataList, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 5. 通过读取xml文件来生成项目报告
        String xml = null;
        if (new File(projectFile.toString()).exists()) {
            xml = XmlUtil.writeXML(projectFile.toString());
        }
        // 6. 项目报告插入mysql并修改项目运行状态
        reportService.updateReportStateByProSoftId(userId, projectId,
                Integer.valueOf(appId.toString()), 3, xml);
        if (appId.toString().equals(AppNameIDConstant.split)) {
            String inPath = reportPath + "result/split/";
            String outPath = PropertiesUtil.fileFinal;
            HashSet<String> resultFiles = FileTools.getFiles(inPath);
            Iterator<String> rFile = resultFiles.iterator();
            Long size = null;
            for (int i = 0; i < dataList.size(); i++) {
                dataNames += dataList.get(i).getFileName();
            }
            while (rFile.hasNext()) {
                String fstr = rFile.next();
                if (!fstr.equals("...tar.gz") && !fstr.equals("..tar.gz")) {
                    String extName = fstr
                            .substring(fstr.lastIndexOf(".tar.gz"));
                    String resourcePath = inPath + fstr;
                    size = new File(resourcePath).length();
                    com.celloud.sdo.Data data = new com.celloud.sdo.Data();
                    data.setUserId(userId);
                    data.setFileName(fstr);
                    data.setState(DataState.DEELTED);
                    int dataId = dataService.addData(data);
                    dataKey = DataUtil.getNewDataKey(dataId);
                    String filePath = outPath + dataKey + extName;
                    boolean state = PerlUtils.excuteCopyPerl(resourcePath,
                            filePath);
                    if (state) {
                        data.setFileId((long) dataId);
                        data.setDataKey(dataKey);
                        data.setAnotherName("split:" + dataNames);
                        data.setSize(size);
                        data.setPath(filePath);
                        data.setFileFormat(FileFormat.FQ);
                        data.setState(DataState.ACTIVE);
                        dataService.updateData(data);
                    }
                }
            }
        }
        // TODO 7.发送邮件
        String param = "fileName=" + null + "&userId=" + userId + "&appId="
                + appId + "&dataKey=" + dataKeys + "&projectId=" + projectId
                + "&sampleList=" + null;
        EmailProjectEnd.sendEndEmail(pro.getProjectName(), "" + appId,
                DateUtil.nowCarefulTimeToString(pro.getCreateDate()),
                user.getEmail(), param, true);

        // 8.结束任务并开始执行等待任务
        task = taskService.getTaskDataAppPro(dataKey, appId, (long) projectId);
        if (task != null) {
            this.updateTaskState();
        }
        return SUCCESS;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Integer getConditionInt() {
        return conditionInt;
    }

    public void setConditionInt(Integer conditionInt) {
        this.conditionInt = conditionInt;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getDataNames() {
        return dataNames;
    }

    public void setDataNames(String dataNames) {
        this.dataNames = dataNames;
    }
}
