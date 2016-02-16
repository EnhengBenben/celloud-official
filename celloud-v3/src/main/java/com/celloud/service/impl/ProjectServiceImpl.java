package com.celloud.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.DataState;
import com.celloud.mapper.ProjectMapper;
import com.celloud.mapper.ReportMapper;
import com.celloud.model.Project;
import com.celloud.service.ProjectService;

@Service("projectServiceImpl")
public class ProjectServiceImpl implements ProjectService {
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private ReportMapper reportMapper;

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
        Integer pdel = projectMapper.deleteByState(projectId,
                DataState.DEELTED);
        reportMapper.deleteByState(projectId, DataState.DEELTED);
        return pdel;
    }

    @Override
    public Integer insertProject(Project project) {
        project.setProjectId(null);
        project.setCreateDate(new Date());
        return projectMapper.insertSelective(project);
    }

    @Override
    public Map<Integer, Integer> insertMultipleProject(Project project,
            String[] appIdArr, String[] dataIdArr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (String appId : appIdArr) {
            project.setProjectId(null);
            project.setCreateDate(new Date());
            projectMapper.insertSelective(project);
            Integer projectId = project.getProjectId();
            for (String dataId : dataIdArr) {
                projectMapper.insertDataProjectRelat(Integer.valueOf(dataId),
                        projectId);
            }
            map.put(Integer.valueOf(appId), projectId);
        }
        return map;
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
