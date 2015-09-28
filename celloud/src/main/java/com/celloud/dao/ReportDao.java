package com.celloud.dao;

import com.celloud.dao.impl.ReportDaoImpl;
import com.celloud.sdo.Report;
import com.google.inject.ImplementedBy;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-24下午2:48:17
 * @version Revision: 1.0
 */
@ImplementedBy(ReportDaoImpl.class)
public interface ReportDao {
    /**
     * 新增项目报告
     * 
     * @param pro
     * @return 主键
     */
    public Long insertProReport(Report report);
}
