package com.celloud.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.DataState;
import com.celloud.constants.ReportPeriod;
import com.celloud.mapper.ProjectMapper;
import com.celloud.mapper.ReportMapper;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Project;
import com.celloud.model.mysql.Report;
import com.celloud.service.AppService;
import com.celloud.service.ProjectService;
import com.celloud.service.ReportService;
import com.celloud.service.TaskService;
import com.celloud.utils.TaskUtils;

@Service("projectServiceImpl")
public class ProjectServiceImpl implements ProjectService {

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
	@Resource
	private TaskUtils taskUtils;

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
			taskUtils.killTask(report.getUserId(), report.getAppId(), projectId);
		}
		Integer pdel = projectMapper.deleteByState(projectId, DataState.DEELTED);
        reportMapper.deleteByState(projectId, DataState.DEELTED);
        return pdel;
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
