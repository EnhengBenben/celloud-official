/**  */
package com.celloud.mongo.service;

import com.celloud.mongo.sdo.CmpFilling;
import com.celloud.mongo.sdo.CmpReport;

/**
 * 报告服务接口
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-13下午4:33:19
 * @version Revision: 1.0
 */
public interface ReportService {
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
	 * @param dataKey
	 * @param userId
	 * @return
	 */
	public CmpReport getCmpReport(String dataKey,
			String userId);
}
