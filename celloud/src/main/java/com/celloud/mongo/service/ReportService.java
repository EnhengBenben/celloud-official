/**  */
package com.celloud.mongo.service;

import java.util.Map;

import com.celloud.mongo.sdo.CmpFilling;
import com.celloud.mongo.sdo.CmpReport;
import com.google.inject.ImplementedBy;

/**
 * 报告服务接口
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-13下午4:33:19
 * @version Revision: 1.0
 */
@ImplementedBy(ReportServiceImpl.class)
public interface ReportService {
	/**
	 * 新增CMP运行报告内容
	 * 
	 * @param cmpReport
	 */
	public void saveCmpReport(CmpReport cmpReport);

	/**
	 * 新增cmp用户填写部分内容
	 * 
	 * @param cmpFill
	 */
	public void saveCmpFilling(CmpFilling cmpFill);

	/**
	 * 获取CMP报告内容
	 * 
	 * @param reportId
	 *            mysql中的报告id
	 * @return
	 */
	public Map<String, Object> getOneWholeCmpReport(Object reportId,
			Object fillId);

	/**
	 * 删除已有的CMP
	 * 
	 * @param id
	 */
	public void deleteCmpFilling(Object id);
}
