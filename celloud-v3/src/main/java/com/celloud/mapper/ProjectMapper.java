package com.celloud.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.Project;

public interface ProjectMapper {
    int deleteByPrimaryKey(Integer projectId);

    /**
     * 软删除项目
     * 
     * @param projectId
     *            ：项目ID
     * @param state
     *            ：删除状态
     * @return
     * @date 2016-1-8 下午2:27:43
     */
    Integer deleteByState(@Param("projectId") Integer projectId,
            @Param("state") Integer state);

    int insert(Project record);

    int insertSelective(Project record);

    /**
     * 新增项目数据关系
     * 
     * @param dataId
     * @param project
     * @return
     * @author leamo
     * @date 2016-1-10 下午4:43:57
     */
    Integer insertDataProjectRelat(@Param("dataId") Integer dataId,
            @Param("projectId") Integer projectId);

    Project selectByPrimaryKey(Integer projectId);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);

    /**
     * 删除共享来的项目
     * 
     * @param userId
     *            ：被共享人
     * @param projectId
     *            ：被共享的项目
     * @return
     * @date 2016-1-7 下午4:55:49
     */
    public Integer deleteShareToMe(@Param("userId") Integer userId,
            @Param("projectId") Integer projectId);

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
    public Integer deleteShareFromMe(@Param("userId") Integer userId,
            @Param("projectId") Integer projectId);

    /**
     * 修改项目的共享人数
     * 
     * @param projectId
     *            ：要减少的项目ID
     * @param num
     *            ：num 为 null，则共享人数减1，否则即置为指定的值
     * @return
     * @date 2016-1-7 下午4:56:21
     */
    public Integer updateShareNum(@Param("projectId") Integer projectId,
            @Param("num") Integer num);

    /**
     * 共享项目
     * 
     * @param from
     * @param projectId
     * @param to
     * @return
     * @date 2016-1-7 下午6:52:36
     */
    public Integer addShare(@Param("shareFrom") Integer from,
            @Param("projectId") Integer projectId, @Param("shareTo") Integer to);

    /**
     * 根据项目编号获取userId，APP信息
     * 
     * @param projectId
     * @return
     * @author leamo
     * @date 2016年1月15日 上午10:41:53
     */
    Map<String, Object> findProjectInfoById(Integer projectId);
    
	/**
	 * 获取项目共享给了哪些用户
	 * 
	 * @param userId：项目共享人（share_from）
	 * @param projectId：要检索的项目
	 * @return
	 * @author lin
	 * @date 2016年1月25日下午3:12:38
	 */
	public List<Map<String, Object>> getShareTo(@Param("userId") Integer userId, @Param("projectId") Integer projectId);
}