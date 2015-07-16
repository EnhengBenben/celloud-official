/**  */
package com.celloud.mongo.dao;

import com.celloud.mongo.sdo.CmpFilling;
import com.celloud.mongo.sdo.CmpReport;

/**
 * 操作Report接口
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-9下午4:23:35
 * @version Revision: 1.0
 */
public interface ReportDAO {
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
	public CmpReport getOneCmpReport(Object id);

	/**
	 * 获取CMP用户填写内容
	 * 
	 * @param id
	 * @return
	 */
	public CmpFilling getOneCmpFilling(Object id);

	/**
	 * 删除已有的CMP
	 * 
	 * @param id
	 */
	public void deleteCmpFilling(Object id);
}
