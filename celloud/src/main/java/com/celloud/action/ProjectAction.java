package com.celloud.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.celloud.constants.AppDataListType;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.Mod;
import com.celloud.constants.ReportPeriod;
import com.celloud.constants.SparkPro;
import com.celloud.model.mysql.App;
import com.celloud.model.mysql.Project;
import com.celloud.model.mysql.Report;
import com.celloud.model.mysql.Task;
import com.celloud.model.mysql.User;
import com.celloud.service.AppService;
import com.celloud.service.ProjectService;
import com.celloud.service.ReportService;
import com.celloud.service.TaskService;
import com.celloud.service.UserService;
import com.celloud.utils.ActionLog;
import com.celloud.utils.SSHUtil;

/**
 * 项目操作类
 * 
 * @author lin
 * @date 2016-1-7 下午2:54:28
 */
@RequestMapping(value = "/project")
@Controller
public class ProjectAction {
	 Logger logger = LoggerFactory.getLogger(ProjectAction.class);
    @Resource
    private ProjectService projectService;
    @Resource
    private UserService userService;
    @Resource
    private ReportService reportService;
    @Resource
    private TaskService taskService;
    @Resource
    private AppService appService;
    
    private static Map<String, Map<String, String>> machines = ConstantsData
            .getMachines();
    private static String sparkhost = machines.get("spark").get(Mod.HOST);
    private static String sparkpwd = machines.get("spark").get(Mod.PWD);
    private static String sparkuserName = machines.get("spark")
            .get(Mod.USERNAME);
    private static String sgeHost = machines.get("158").get(Mod.HOST);
    private static String sgePwd = machines.get("158").get(Mod.PWD);;
    private static String sgeUserName = machines.get("158").get(Mod.USERNAME);

    /**
     * 修改项目
     * 
     * @param project
     * @return
     * @date 2016-1-8 下午1:43:28
     */
    @ActionLog(value = "修改项目信息（项目名）", button = "修改项目")
    @RequestMapping("update")
    @ResponseBody
    public Integer update(Project project) {
        return projectService.update(project);
    }

	/**
	 * 删除项目
	 * 
	 * @param projectId
	 * @return
	 * @date 2016-1-8 下午3:24:30
	 */
	@ActionLog(value = "项目软删除", button = "删除项目")
	@RequestMapping("deleteByState")
	@ResponseBody
	public Integer deleteByState(Integer projectId) {
		Report report = reportService.getReportByProjectId(projectId);
		if (report != null && report.getPeriod() != ReportPeriod.COMPLETE) {
			Integer intAppId = report.getAppId();
			String appId = String.valueOf(intAppId);
			String param = SparkPro.TOOLSPATH + report.getUserId() + "/" + appId + " ProjectID" + projectId;
			String command = null;
			SSHUtil ssh = null;
			if (SparkPro.apps.contains(appId)) {
				command = SparkPro.SPARKKILL + " " + param;
				ssh = new SSHUtil(sparkhost, sparkuserName, sparkpwd);
				ssh.sshSubmit(command, false);
				TaskAction ta = new TaskAction();
				ta.runQueue(String.valueOf(projectId));
			} else {
				command = SparkPro.SGEKILL + " " + param;
				ssh = new SSHUtil(sgeHost, sgeUserName, sgePwd);
				ssh.sshSubmit(command, false);
				if (AppDataListType.FASTQ_PATH.contains(appId) || AppDataListType.SPLIT.contains(appId)) {
					taskService.deleteTask(projectId);
					Task task = taskService.findFirstTask(intAppId);
					if (task != null) {
						int runningNum = taskService.findRunningNumByAppId(intAppId);
						App app = appService.selectByPrimaryKey(intAppId);
						if (runningNum < app.getMaxTask() || app.getMaxTask() == 0) {
							ssh = new SSHUtil(sgeHost, sgeUserName, sgePwd);
							ssh.sshSubmit(task.getCommand(), false);
							taskService.updateToRunning(task.getTaskId());
						}
					}
				}
			}
		}
		return projectService.deleteByState(projectId);
	}

    /**
     * 共享项目
     * 
     * @param userNames
     *            ：要共享的用户名
     * @param projectId
     *            ：要共享的项目
     * @return
     * @date 2016-1-7 下午7:00:10
     */
    @ActionLog(value = "将项目共享给其他用户", button = "共享项目")
    @RequestMapping(value = "shareProject", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String share(String userNames, Integer projectId) {
        String error = "";
        String userIds = "";
        if (StringUtils.isNotEmpty(userNames)) {
            String username[] = userNames.split(",");
            for (String uname : username) {
                User user = userService.getUserByName(uname);
                if (user == null) {
                    error += uname + ",";
                } else {
                    userIds += "" + user.getUserId() + ",";
                }
            }
            if (StringUtils.isNotEmpty(error)) {
                error = error.substring(0, error.length() - 1);
                error = "用户" + error + "不存在！";
                return error;
            }
        }
        Integer userId = ConstantsData.getLoginUserId();
        projectService.deleteShareFromMe(userId, projectId);
        if (StringUtils.isNotEmpty(userIds)) {
            projectService.addShare(userId, projectId, userIds);
        }
        return error;
    }

    /**
     * 删除共享来的项目
     * 
     * @param projectId
     * @return
     * @date 2016-1-7 下午5:01:59
     */
    @ActionLog(value = "删除一条共享来的项目记录，项目本身不删除", button = "删除共享项目")
    @RequestMapping("deleteShare")
    @ResponseBody
    public boolean deleteShare(Integer projectId) {
        Integer userId = ConstantsData.getLoginUserId();
        // 删除一条共享记录，修改一条项目记录
        Integer num = projectService.deleteShareToMe(userId, projectId);
        return num == 2;
    }
    
	/**
	 * 查询项目共享给了哪些用户
	 * 
	 * @param projectId
	 * @return
	 * @author lin
	 * @date 2016年1月25日下午3:24:00
	 */
    @ActionLog(value = "查询项目共享给了哪些用户", button = "共享项目")
	@RequestMapping("getShareTo")
	@ResponseBody
	public List<Map<String, Object>> getShareTo(Integer projectId) {
		Integer userId = ConstantsData.getLoginUserId();
		return projectService.getShareTo(userId, projectId);
	}
}
