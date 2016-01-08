package com.celloud.service;

import com.celloud.model.Project;

/**
 *  项目操作service
 * 
 * @author lin
 * @date 2016-1-7 下午3:59:50
 */
public interface ProjectService {

    /**
     * 项目软删除
     * 
     * @param projectId
     * @return
     * @date 2016-1-8 下午2:31:36
     */
    public Integer deleteByState(Integer projectId);

    /**
     * 修改项目
     * 
     * @param project
     * @return
     * @date 2016-1-8 下午1:40:04
     */
    public Integer update(Project project);

    /**
     * 删除共享来的项目
     * 
     * @param userId
     *            ：被共享人
     * @param projectId
     *            ：被共享的项目
     * @date 2016-1-7 下午4:01:48
     */
    public Integer deleteShareToMe(Integer userId, Integer projectId);

    /**
     * 删除共享出去的项目
     * 
     * @param userId
     *            ：共享人
     * @param projectId
     *            ：被共享的项目
     * @return
     * @date 2016-1-7 下午5:29:09
     */
    public Integer deleteShareFromMe(Integer userId, Integer projectId);

    /**
     * 共享项目
     * 
     * @param userId
     * @param projectId
     * @param userIds
     * @return
     * @date 2016-1-7 下午6:51:11
     */
    public Integer addShare(Integer userId, Integer projectId, String userIds);
}
