package com.celloud.service;

import com.celloud.sdo.Report;
import com.celloud.service.impl.ReportServiceImpl;
import com.google.inject.ImplementedBy;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-24下午3:05:53
 * @version Revision: 1.0
 */
@ImplementedBy(ReportServiceImpl.class)
public interface ReportService {
    /**
     * 新增项目报告
     * 
     * @param pro
     * @return 主键
     */
    public Long insertProReport(Report report);
}
