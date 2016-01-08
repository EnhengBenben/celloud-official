package com.celloud.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.ProjectMapper;
import com.celloud.service.ProjectService;

@Service("projectServiceImpl")
public class ProjectServiceImpl implements ProjectService {
    @Resource
    private ProjectMapper projectMapper;

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

}
