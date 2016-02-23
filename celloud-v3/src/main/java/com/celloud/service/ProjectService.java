package com.celloud.service;

import java.util.List;
import java.util.Map;

import com.celloud.model.mysql.Project;

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

    /**
     * 新增报告
     * 
     * @param project
     * @return
     * @author leamo
     * @date 2016-1-10 下午4:07:16
     */
    public Integer insertProject(Project project);

    /**
     * 批量运行新增报告
     * 
     * @param project
     * @param appIdArr
     *            需要建项目的APP
     * @param dataIdArr
     *            需要建项目的数据
     * @return {appId: projectId}
     * @author leamo
     * @date 2016年1月14日 下午1:12:51
     */
    public Map<Integer, Integer> insertMultipleProject(Project project,
            String[] appIdArr, String[] dataIdArr);

    public Map<String, Object> findProjectInfoById(Integer projectId);
    
    /**
     * 获取项目共享给了哪些用户
     * 
     * @param userId：项目共享人（share_from）
     * @param projectId：要检索的项目
     * @return
     * @author lin
     * @date 2016年1月25日下午3:10:31
     */
	public List<Map<String, Object>> getShareTo(Integer userId, Integer projectId);
	
	/**
	 * 单查项目
	 * 
	 * @param projectId
	 * @return
	 * @author lin
	 * @date 2016年1月26日下午7:09:46
	 */
	public Project selectByPrimaryKey(Integer projectId);
}
