package com.celloud.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.celloud.constants.AppDataListType;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.DataState;
import com.celloud.constants.Mod;
import com.celloud.constants.ReportPeriod;
import com.celloud.constants.SparkPro;
import com.celloud.mapper.ProjectMapper;
import com.celloud.mapper.ReportMapper;
import com.celloud.model.mysql.App;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Project;
import com.celloud.model.mysql.Report;
import com.celloud.model.mysql.Task;
import com.celloud.service.AppService;
import com.celloud.service.ProjectService;
import com.celloud.service.ReportService;
import com.celloud.service.TaskService;
import com.celloud.utils.SSHUtil;

@Service("projectServiceImpl")
public class ProjectServiceImpl implements ProjectService {

	private static Map<String, Map<String, String>> machines = ConstantsData.getMachines();
	private static String sparkhost = machines.get("spark").get(Mod.HOST);
	private static String sparkpwd = machines.get("spark").get(Mod.PWD);
	private static String sparkuserName = machines.get("spark").get(Mod.USERNAME);
	private static String sgeHost = machines.get("158").get(Mod.HOST);
	private static String sgePwd = machines.get("158").get(Mod.PWD);;
	private static String sgeUserName = machines.get("158").get(Mod.USERNAME);

    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private ReportMapper reportMapper;
	@Resource
	private ReportService reportService;
	@Resource
	private TaskService taskService;
	@Resource
	private AppService appService;

    @Override
    public Integer deleteShareToMe(Integer userId, Integer projectId) {
        Integer a = projectMapper.deleteShareToMe(userId, projectId);
        Integer b = projectMapper.updateShareNum(projectId, null);
        return a + b;
    }

    @Override
    public Integer deleteShareFromMe(Integer userId, Integer projectId) {
        Integer a = projectMapper.deleteShareFromMe(userId, projectId);
        Integer b = projectMapper.updateShareNum(projectId, 0);
        return a + b;
    }

    @Override
    public Integer addShare(Integer userId, Integer projectId, String userIds) {
        int count = 0;
        for (String to : userIds.split(",")) {
            int a = projectMapper.addShare(userId, projectId,
                    Integer.valueOf(to));
            count += a;
        }
        projectMapper.updateShareNum(projectId, count);
        return count;
    }

    @Override
    public Integer update(Project project) {
        return projectMapper.updateByPrimaryKeySelective(project);
    }

    @Override
    public Integer deleteByState(Integer projectId) {
		Report report = reportService.getReportByProjectId(projectId);
		if (report != null && report.getPeriod() != ReportPeriod.COMPLETE) {
			killTask(report.getUserId(), report.getAppId(), projectId);
		}
		Integer pdel = projectMapper.deleteByState(projectId, DataState.DEELTED);
        reportMapper.deleteByState(projectId, DataState.DEELTED);
        return pdel;
    }

	@Async
	private void killTask(Integer userId, Integer appId, Integer projectId) {
		String param = SparkPro.TOOLSPATH + userId + "/" + appId + " ProjectID" + projectId;
		String command = null;
		SSHUtil ssh = null;
		if (AppDataListType.SPARK.contains(appId)) {
			command = SparkPro.SPARKKILL + " " + param;
			ssh = new SSHUtil(sparkhost, sparkuserName, sparkpwd);
			ssh.sshSubmit(command, false);
		} else {
			command = SparkPro.SGEKILL + " " + param;
			ssh = new SSHUtil(sgeHost, sgeUserName, sgePwd);
			ssh.sshSubmit(command, false);
		}
		if (AppDataListType.FASTQ_PATH.contains(appId) || AppDataListType.SPLIT.contains(appId)) {
			taskService.deleteTask(projectId);
			Task task = taskService.findFirstTask(appId);
			if (task != null) {
				int runningNum = taskService.findRunningNumByAppId(appId);
				App app = appService.selectByPrimaryKey(appId);
				if (runningNum < app.getMaxTask() || app.getMaxTask() == 0) {
					if (AppDataListType.SPARK.contains(appId)) {
						ssh = new SSHUtil(sparkhost, sparkuserName, sparkpwd);
					} else {
						ssh = new SSHUtil(sgeHost, sgeUserName, sgePwd);
					}
					ssh.sshSubmit(task.getCommand(), false);
					taskService.updateToRunning(task.getTaskId());
				}
			}
		}
	}

    @Override
    public Integer insertProject(Project project, Integer dataId) {
        project.setProjectId(null);
        project.setCreateDate(new Date());
        projectMapper.insertSelective(project);
        Integer projectId = project.getProjectId();
        projectMapper.insertDataProjectRelat(dataId, projectId);
        return projectId;
    }

    @Override
    public Map<Integer, Integer> insertMultipleProject(Project project,
            List<Integer> appIds, String[] dataIdArr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer appId : appIds) {
            project.setProjectId(null);
            project.setCreateDate(new Date());
            projectMapper.insertSelective(project);
            Integer projectId = project.getProjectId();
            for (String dataId : dataIdArr) {
                projectMapper.insertDataProjectRelat(Integer.valueOf(dataId),
                        projectId);
            }
            map.put(appId, projectId);
        }
        return map;
    }

	@Override
	public Integer insertProject(Project project, List<DataFile> datalist) {
		project.setProjectId(null);
		project.setCreateDate(new Date());
		projectMapper.insertSelective(project);
		Integer projectId = project.getProjectId();
		for (DataFile data : datalist) {
			projectMapper.insertDataProjectRelat(data.getFileId(), projectId);
		}
		return projectId;
	}

    @Override
    public Map<String, Object> findProjectInfoById(Integer projectId) {
        return projectMapper.findProjectInfoById(projectId);
    }

	@Override
	public List<Map<String, Object>> getShareTo(Integer userId, Integer projectId) {
		return projectMapper.getShareTo(userId, projectId);
	}

	@Override
	public Project selectByPrimaryKey(Integer projectId) {
		return projectMapper.selectByPrimaryKey(projectId);
	}

}
