package com.celloud.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.celloud.model.mysql.Task;
import com.celloud.page.Page;
import com.celloud.page.PageList;

/**
 * 运行任务服务类
 * 
 * @author leamo
 * @date 2016-1-10 下午8:10:18
 */
public interface TaskService {

	/**
	 * 
	 * @author miaoqi
	 * @description 根据项目id获取task
	 * @date 2017年1月12日
	 *
	 * @param projectId
	 * @return
	 */
	Task findTaskByProjectid(Integer projectId);

	/**
	 * 获取等待运行APP时间最长的任务
	 * 
	 * @param appId
	 * @return
	 */
	public Task findFirstTask(Integer appId);

	/**
	 * 任务修改为正在运行
	 * 
	 * @param taskId
	 * @return
	 */
	public Integer updateToRunning(Integer taskId);

	/**
	 * 任务运行结束，获取APP正在排队的任务
	 * 
	 * @param appId
	 * @param projectId
	 *            需改状态项目
	 * @param dataKey
	 *            需改状态数据
	 * @param context
	 *            项目报告内容
	 * @return
	 * @author leamo
	 * @date 2016年1月14日 下午7:43:46
	 */
	public Task updateToDone(Integer appId, Integer projectId, String dataKey, String dataKeys, String context);

	/**
	 * 指定app正在运行的任务数
	 * 
	 * @param appId
	 * @return
	 */
	public Integer findRunningNumByAppId(Integer appId);

	/**
	 * 指定app正在运行的任务数
	 * 
	 * @param appId
	 * @return
	 */
	public Integer findRunningNumByAppId(List<Integer> appId);

	/**
	 * 根据proId获取报告信息、任务编号、app信息、数据个数
	 * 
	 * @param proId
	 * @return
	 */
	public Map<String, Object> findTaskInfoByProId(Integer projectId);

	/**
	 * 根据dataKey appId proId获取任务信息
	 * 
	 * @param proId
	 * @param appId
	 * @param dataKey
	 * @return
	 */
	public Task findTaskDataAppPro(String dataKey, Integer appId, Integer projectId);

	/**
	 * 根据proId删除未运行或正在运行的任务
	 * 
	 * @param proId
	 * @return
	 */
	public Integer deleteTask(Integer projectId);

	/**
	 * 查询用户数据任务列表
	 * 
	 * @param userId
	 * @return
	 * @author leamo
	 * @date 2016年4月21日 下午2:32:22
	 */
	public PageList<Task> findTasksByUser(Page page, Integer userId, Integer appId);

	/**
	 * 按条件检索数据列表
	 * 
	 * @param page
	 * @param userId
	 * @param condition
	 * @param sort
	 * @param sortDateType
	 * @param sortNameType
	 * @param state
	 * @param reportType
	 * @return
	 */
	public PageList<Task> findTasksByUserCondition(Page page, Integer userId, String condition, Integer sort,
			String sortDate, String sortBatch, String sortName, String sortPeriod, String batch, String period,
			String beginDate, String endDate, Integer appId, String sampleName);

	/**
	 * 获取统一批次下的报告列表
	 * 
	 * @param page
	 * @param userId
	 * @param appId
	 * @param condition
	 * @return
	 * @author leamo
	 * @date 2016年6月1日 下午5:38:50
	 */
	public PageList<Task> findTasksByBatch(Page page, Integer userId, Integer appId, String batch);

	/**
	 * 数据报告上一个/下一个
	 * 
	 * @param page
	 * @param userId
	 * @param condition
	 * @param sort
	 * @param sortDate
	 * @param sortBatch
	 * @param sortName
	 * @param sortPeriod
	 * @return
	 * @author leamo
	 * @date 2016年5月25日 下午1:56:54
	 */
	public PageList<Task> findNextOrPrevTasks(Page page, Integer userId, String condition, Integer sort,
			String sortDate, String sortBatch, String sortName, String sortPeriod, Boolean isPrev, Integer totalPage,
			String batch, String period, String beginDate, String endDate, Integer appId, String sampleName);

	/**
	 * 新增或修改上传任务数据
	 * 
	 * @param userId
	 * @param period
	 * @param params
	 * @return
	 * @author leamo
	 * @date 2016年5月16日 下午3:33:18
	 */
	public Integer addOrUpdateUploadTaskByParam(Task task, Boolean isUpdate);

	/**
	 * 新增或修改上传任务数据筛选标签
	 * 
	 * @param userId
	 * @param period
	 * @param params
	 * @return
	 * @author leamo
	 * @date 2016年5月16日 下午3:33:18
	 */
	public Integer addOrUpdateUploadTaskByParamAndBatch(Task task, Boolean isUpdate, String batch);

	/**
	 * 根据数据编号获取任务信息
	 * 
	 * @param state
	 * @param dataKey
	 * @return
	 * @author leamo
	 * @date 2016年5月16日 下午4:03:13
	 */
	public Task findTaskByDataKeyAndApp(String dataKey, Integer appId);

	/**
	 * 修改任务
	 * 
	 * @param task
	 * @return
	 * @author leamo
	 * @date 2016年5月16日 下午4:12:17
	 */
	public Integer updateTask(Task task);

	/**
	 * 统计用户报告任务种类数量
	 * 
	 * @param state
	 * @param appId
	 * @param userId
	 * @return
	 * @author leamo
	 * @date 2016年6月13日 上午10:20:15
	 */
	public Map<String, Object> findTaskPeriodNum(Integer appId, Integer userId);

	/**
	 * 获取乳腺癌的报告
	 * 
	 * @param pager
	 */
	public PageList<Task> findRockyTasks(Page pager, String sample, String condition, String sidx, String sord,
			ArrayList<String> batches, ArrayList<Integer> state, Date beginDate, Date endDate);

	/**
	 * 获取所有报告任务列表
	 * 
	 * @param pager
	 * @param userId
	 * @param condition
	 * @param batch
	 * @param period
	 * @param beginDate
	 * @param endDate
	 * @param sord
	 * @return
	 * @author leamo
	 * @date 2016年8月29日 下午3:17:25
	 */
	public PageList<Task> findAllTasks(Page pager, Integer userId, String condition, Integer tagId, String batch,
			Integer period, String beginDate, String endDate, String sord);

	/**
	 * 
	 * @description 根据标签查询全部task
	 * @author miaoqi
	 * @date 2016年11月17日上午11:20:44
	 *
	 * @param batch
	 * @param loginUserId
	 * @param appId
	 * @return
	 */
	public List<Task> findAllByBatch(String batch, Integer loginUserId, Integer appId);

	PageList<Task> findTasksByBatchNoUserId(Page page, Integer userId, Integer appId, String batch);

	public int saveTaskDataRelat(Integer taskId, Integer... dataIds);

    /**
     * 
     * @description
     * @author miaoqi 查询同一批次下的所有dataKey
     * @date 2017年2月27日 上午11:18:51
     * @param userId
     * @param appId
     * @param batch
     * @return
     */
    List<String> findDataKeysByBatchNoSample(Integer userId, Integer appId, String batch);

    /**
     * 
     * @description 分页获取同一批次列表, 不包含样本信息
     * @author miaoqi
     * @date 2017年2月27日 下午1:21:51
     * @param page
     * @param userId
     * @param appId
     * @param batch
     * @return
     */
    public PageList<Task> findTasksByBatchNoSample(Page page, Integer userId, Integer appId, String batch);
}
