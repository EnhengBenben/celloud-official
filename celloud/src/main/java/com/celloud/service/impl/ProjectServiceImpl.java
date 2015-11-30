package com.celloud.service.impl;

import com.celloud.dao.ProjectDao;
import com.celloud.sdo.Project;
import com.celloud.service.ProjectService;
import com.google.inject.Inject;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-23下午3:57:10
 * @version Revision: 1.0
 */
public class ProjectServiceImpl implements ProjectService {
    @Inject
    ProjectDao proDao;

    @Override
    public Long insertProject(Project pro) {
        return proDao.insertProject(pro);
    }
}
