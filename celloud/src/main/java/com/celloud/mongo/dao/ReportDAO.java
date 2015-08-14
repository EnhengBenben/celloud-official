/**  */
package com.celloud.mongo.dao;

import com.celloud.mongo.sdo.CmpFilling;
import com.celloud.mongo.sdo.CmpReport;
import com.google.inject.ImplementedBy;

/**
 * 操作Report接口
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-9下午4:23:35
 * @version Revision: 1.0
 */
@ImplementedBy(ReportDAOImpl.class)
public interface ReportDAO {
	/**
	 * 新增CMP运行报告内容
	 * 
	 * @param cmpReport
	 */
	public void saveCmpReport(CmpReport cmpReport);

	/**
	 * 修改cmp用户填写部分内容
	 * 
	 * @param cmpFill
	 */
	public void editCmpFilling(Object id, CmpFilling cmpFill);

	/**
	 * 获取CMP报告内容
	 * 
	 * @param reportId
	 *            mysql中的报告id
	 * @return
	 */
	public CmpReport getCmpReport(String dataKey, Integer proId);

	/**
	 * 获取CMP简要信息内容
	 * 
	 * @param dataKey
	 * @param userId
	 * @return
	 */
	public CmpReport getSimpleCmp(String dataKey, Integer proId);
}
