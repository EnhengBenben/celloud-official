/**  */
package com.celloud.mongo.service;

import java.util.List;

import com.celloud.mongo.sdo.CmpFilling;
import com.celloud.mongo.sdo.CmpReport;
import com.celloud.mongo.sdo.Pgs;
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
	public CmpReport getCmpReport(String dataKey, Integer userId);

	/**
	 * 获取CMP简要信息内容
	 * 
	 * @param dataKey
	 * @param userId
	 * @return
	 */
	public CmpReport getSimpleCmp(String dataKey, Integer userId);

	/**
	 * 获取pgs报告内容
	 * 
	 * @param dataKey
	 * @param proId
	 * @return
	 */
	public Pgs getPgsReport(String dataKey, Integer proId, Integer appId);

	/**
	 * 获取pgs统计报告内容
	 * 
	 * @param userId
	 * @return
	 */
	public abstract List<Pgs> getPgsList(Integer userId);
}
