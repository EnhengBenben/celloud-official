package com.celloud.service;

import com.celloud.sdo.Project;
import com.celloud.service.impl.ProjectServiceImpl;
import com.google.inject.ImplementedBy;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-23下午3:56:45
 * @version Revision: 1.0
 */
@ImplementedBy(ProjectServiceImpl.class)
public interface ProjectService {
    /**
     * 新增项目
     * 
     * @param pro
     * @return 主键
     */
    public Long insertProject(Project pro);
}
