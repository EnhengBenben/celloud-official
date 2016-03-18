package com.celloud.backstage.mapper;

import org.apache.ibatis.annotations.Param;

import com.celloud.backstage.model.Project;

public interface ProjectMapper {
    int deleteByPrimaryKey(Integer projectId);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Integer projectId);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
    /**
     * 数据清理 硬删除tb_project
     *
     * @param userId
     * @return
     * @author han
     * @date 2016年2月22日 下午4:56:35
     */
    public int deleteProjectByUserId(@Param("userId")Integer userId);
    
    /**
     * 数据清理 硬删除tb_project_share
     *
     * @param userId
     * @return
     * @author han
     * @date 2016年2月22日 下午4:56:35
     */
    public int deleteProjectShareByFrom(@Param("userId")Integer userId);
}