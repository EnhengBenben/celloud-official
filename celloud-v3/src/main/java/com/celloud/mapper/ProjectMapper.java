package com.celloud.mapper;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.Project;

public interface ProjectMapper {
    int deleteByPrimaryKey(Integer projectId);

    int insert(Project record);

    int insertSelective(Project record);

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
}