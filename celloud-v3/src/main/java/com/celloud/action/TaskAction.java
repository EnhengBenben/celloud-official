package com.celloud.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.celloud.constants.ConstantsData;
import com.celloud.constants.DataState;
import com.celloud.constants.FileFormat;
import com.celloud.constants.GlobalQueue;
import com.celloud.constants.Mod;
import com.celloud.constants.PortPool;
import com.celloud.constants.SparkPro;
import com.celloud.model.DataFile;
import com.celloud.model.Task;
import com.celloud.service.AppService;
import com.celloud.service.DataService;
import com.celloud.service.ProjectService;
import com.celloud.service.ReportService;
import com.celloud.service.TaskService;
import com.celloud.utils.DataUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.PerlUtils;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.RunOverService;
import com.celloud.utils.SSHUtil;
import com.celloud.utils.XmlUtil;

/**
 * 投递任务管理
 * 
 * @author leamo
 * @date 2016年1月14日 下午5:05:52
 */
@Controller
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
     * perl端调用：http://www.celloud.cn/task!runOver?projectId=1&dataNames=data1
     * .fastq,data2.fastq
     * 
     * @return
     * @author leamo
     * @date 2016年1月14日 下午5:09:27
     */
    @RequestMapping("task!runOver")
    public String runOver(String projectId, String dataNames) {
        logger.info("任务运行结束，proId:{},运行数据dataKey：{}", projectId, dataNames);
        String[] dataArr = dataNames.split(",");
        List<String> dataKeyList = new ArrayList<>();
        String dataKey = "";
        for (int i = 0; i < dataArr.length; i++) {
            String dname = dataArr[i];
            if (i == 0) {
                dataKey = dname;
            }
            dataKeyList.add(dname);
        }

        // 1. 数据库检索
        Map<String, Object> map = taskService
                .findTaskInfoByProId(Integer.parseInt(projectId));
        Integer userId = (Integer) map.get("userId");
        Integer appId = (Integer) map.get("appId");
        String title = (String) map.get("title");
        String method = (String) map.get("method");
        List<DataFile> dataList = dataService
                .selectDataByKeys(dataKeyList.toString());

        // 2. 利用 python将数据报告插入 mongodb
        StringBuffer command = new StringBuffer();
        command.append("python ").append(SparkPro.TASKOVERPY).append(" ")
                .append(SparkPro.TOOLSPATH).append(" ").append(userId)
                .append(" ").append(appId).append(" ")
                .append(dataKeyList.toString()).append(" ").append(projectId);
        PerlUtils.excutePerl(command.toString());
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
        // projectFile,String projectId, List<DataFile> dataList
        RunOverService ros = new RunOverService();
        try {
            ros.getClass().getMethod(method,
                    new Class[] { String.class, String.class, String.class,
                            String.class, String.class, List.class })
                    .invoke(ros, reportPath.toString(), dataKey, title,
                            projectFile.toString(), projectId, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 5. 通过读取xml文件来生成项目报告
        String xml = null;
        if (new File(projectFile.toString()).exists()) {
            xml = XmlUtil.writeXML(projectFile.toString());
        }
        // 6.结束任务修改项目报告状态
        Task task = taskService.updateToDone(appId, Integer.parseInt(projectId),
                dataKey, xml);
        if (task != null) {
            logger.info("任务{}执行完毕", task.getTaskId());
            task = taskService.findFirstTask(appId);
            if (task != null) {
                logger.info("运行命令：{}", command);
                SSHUtil ssh = new SSHUtil(sgeHost, sgeUserName, sgePwd);
                ssh.sshSubmit(command.toString(), false);
                taskService.updateToRunning(task.getTaskId());
            }
        }

        if (appId == 113) {
            String inPath = reportPath + "result/split/";
            String outPath = PropertiesUtil.bigFilePath;
            HashSet<String> resultFiles = FileTools.getFiles(inPath);
            Iterator<String> rFile = resultFiles.iterator();
            Long size = null;
            while (rFile.hasNext()) {
                String fstr = rFile.next();
                if (!fstr.equals("...tar.gz") && !fstr.equals("..tar.gz")) {
                    String extName = fstr
                            .substring(fstr.lastIndexOf(".tar.gz"));
                    String resourcePath = inPath + fstr;
                    size = new File(resourcePath).length();
                    DataFile DataFile = new DataFile();
                    DataFile.setUserId(userId);
                    DataFile.setFileName(fstr);
                    DataFile.setState(DataState.DEELTED);
                    int dataId = dataService.addDataInfo(DataFile);
                    String new_dataKey = DataUtil.getNewDataKey(dataId);
                    String filePath = outPath + new_dataKey + extName;
                    boolean state = PerlUtils.excuteCopyFile(resourcePath,
                            filePath);
                    if (state) {
                        DataFile.setFileId(dataId);
                        DataFile.setDataKey(new_dataKey);
                        DataFile.setAnotherName("split:" + dataKey);
                        DataFile.setSize(size);
                        DataFile.setPath(filePath);
                        DataFile.setFileFormat(FileFormat.FQ);
                        DataFile.setState(DataState.ACTIVE);
                        dataService.updateDataInfoByFileId(DataFile);
                    }
                }
            }
        }
        return "运行完成";
    }

    /**
     * 项目运行结束之后
     * 
     * @return
     */
    @RequestMapping("project!projectRunOver")
    public String projectRunOver(String projectId) {
        logger.info("项目运行结束，id:{}", projectId);
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
        // projectFile,String projectId, List<Data> proDataList
        RunOverService ros = new RunOverService();
        try {
            // TODO 方法名称和title类型应该从数据库获取
            ros.getClass().getMethod(method,
                    new Class[] { String.class, String.class, String.class,
                            String.class, String.class, List.class })
                    .invoke(ros, basePath, appName, title, projectFile,
                            projectId, dataList);
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

        runQueue(projectId);
        return "run over";
    }

    /**
     * 运行队列里的命令
     */
    private void runQueue(String projectId) {
        logger.info("{}运行结束，释放端口", projectId);
        PortPool.setPort(projectId);
        while (true) {
            if (GlobalQueue.isEmpty()) {
                logger.info("任务队列为空");
                break;
            }
            String command = GlobalQueue.peek();
            if (command != null) {
                String[] infos = command.split("--");
                int need = infos[2].split(";").length;
                logger.info("需要节点 {}可使用节点 ", need, PortPool.getSize());
                if (PortPool.getSize() < need) {
                    logger.info("节点不满足需要，暂缓投递任务");
                    break;
                }
                logger.info("满足需要，投递任务");
                submit(infos[0], infos[1], infos[2], infos[3], appService
                        .findAppById(Integer.parseInt(infos[4])).getCommand());
                GlobalQueue.poll();
            }
        }
    }

    private void submit(String basePath, String projectId, String dataListFile,
            String appName, String perl) {
        // TODO
        String command = "nohup perl  /share/biosoft/perl/wangzhen/PGS/bin/moniter_qsub_url.pl perl "
                + " " + perl + " " + dataListFile + " " + basePath
                + " ProjectID" + projectId + " >" + basePath + "ProjectID"
                + projectId + ".log &";
        logger.info("运行命令：{}", command);
        SSHUtil ssh = new SSHUtil(sparkhost, sparkuserName, sparkpwd);
        ssh.sshSubmit(command, false);
    }

}
