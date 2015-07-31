/**  */
package com.celloud.mongo.service;

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
 Integer userId);

	/**
	 * 获取CMP简要信息内容
	 * 
	 * @param dataKey
	 * @param userId
	 * @return
	 */
	public CmpReport getSimpleCmp(String dataKey, Integer userId);
}
