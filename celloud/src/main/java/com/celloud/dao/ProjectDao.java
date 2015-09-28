package com.celloud.dao;

import com.celloud.dao.impl.ProjectDaoImpl;
import com.celloud.sdo.Project;
import com.google.inject.ImplementedBy;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-21上午11:16:04
 * @version Revision: 1.0
 */
@ImplementedBy(ProjectDaoImpl.class)
public interface ProjectDao {
    /**
     * 新增项目
     * 
     * @param pro
     * @return 主键
     */
    public Long insertProject(Project pro);
}
