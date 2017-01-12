package com.celloud.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.Task;
import com.celloud.page.Page;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer taskId);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer taskId);

    /**
     * 根据项目id 和 数据编号修改任务运行状态
     * 
     * @param projectId
     * @param dataKey
     * @param period
     * @param startDate
     * @param endDate
     * @return
     * @author leamo
     * @date 2016年1月14日 下午7:59:40
     */
    int updatePeriodByProAndData(@Param("projectId") Integer projectId,
            @Param("dataKey") String dataKey, @Param("period") Integer period,
            @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKeyWithBLOBs(Task record);

    /**
     * 根据projectId删除任务
     * 
     * @param projectId
     * @return
     * @author lin
     * @date 2016年5月16日下午5:01:03
     */
    public Integer deleteTask(@Param("projectId") Integer projectId,
            @Param("state") Integer state,
            @Param("deleteDate") Date deleteDate);

    int updateByPrimaryKey(Task record);

    /**
     * 获取等待运行APP时间最长的任务
     * 
     * @param appId
     * @return
     * @author leamo
     * @date 2016-1-10 下午8:15:21
     */
    Task findFirstTaskByAppId(@Param("appId") Integer appId,
            @Param("period") Integer period, @Param("state") Integer state);

    /**
     * 获取APP正在运行的任务数
     * 
     * @param appId
     * @return
     * @author leamo
     * @date 2016-1-10 下午8:23:32
     */
    Integer findAppRunningNum(@Param("appId") Integer appId,
            @Param("period") Integer period, @Param("state") Integer state);

    /**
     * 获取多个APP正在运行的任务数
     * 
     * @param appIds
     * @param period
     * @param state
     * @return
     * @author leamo
     * @date 2016年6月17日 上午10:53:56
     */
    Integer findAppsRunningNum(@Param("appIds") List<Integer> appIds,
            @Param("period") Integer period, @Param("state") Integer state);

    /**
     * 根据项目编号获取任务信息
     * 
     * @param projectId
     * @return
     * @author leamo
     * @date 2016年1月14日 下午7:11:18
     */
    Map<String, Object> findTaskInfoByProId(Integer projectId);

    /**
     * 根据报告、数据获取任务信息
     * 
     * @param projectId
     * @param dataKey
     * @return
     * @author leamo
     * @date 2016年1月16日 下午4:44:17
     */
    Task findTaskByProData(@Param("projectId") Integer projectId,
            @Param("dataKey") String dataKey);

    /**
     * 查询用户数据任务列表
     * 
     * @param userId
     * @return
     * @author leamo
     * @date 2016年4月21日 下午2:32:22
     */
    List<Task> findTasksByUser(Page page, @Param("userId") Integer userId,
            @Param("state") Integer state, @Param("appId") Integer appId);

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
    // XXX 此方法对应的XML中的 “union all”部分为临时解决 “样本采集”用户看报告的问题，做完上传数据与样本绑定之后立即删除
    List<Task> findTasksByUserCondition(Page page,
            @Param("userId") Integer userId,
            @Param("condition") String condition, @Param("sort") Integer sort,
            @Param("sortDate") String sortDate,
            @Param("sortBatch") String sortBatch,
            @Param("sortName") String sortName,
            @Param("sortPeriod") String sortPeriod,
            @Param("state") Integer state, @Param("batch") String batch,
            @Param("period") String period,
            @Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("appId") Integer appId,
            @Param("sampleName") String sampleName);

    /**
     * 检索某批次下的运行完的任务列表
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
    List<Task> findTasksByBatch(Page page, @Param("userId") Integer userId,
            @Param("appId") Integer appId, @Param("period") Integer period,
            @Param("state") Integer state, @Param("batch") String batch);

    /**
     * 检索某批次下的运行完的任务列表没有userId限制
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
    List<Task> findTasksByBatchNoUserId(Page page, @Param("userId") Integer userId, @Param("appId") Integer appId,
            @Param("period") Integer period, @Param("state") Integer state, @Param("batch") String batch);

    /**
     * 按照运行状态和参数查找任务
     * 
     * @param userId
     * @param state
     * @param period
     * @param params
     * @return
     * @author leamo
     * @date 2016年5月16日 下午3:27:39
     */
    Task findTaskByParamsAndPeriod(@Param("userId") Integer userId,
            @Param("state") Integer state, @Param("period") Integer period,
            @Param("params") String params);

    /**
     * 
     * @description 按照运行状态, 参数, 批次查找任务
     * @author miaoqi
     * @date 2016年11月21日上午11:02:19
     *
     * @param userId
     * @param state
     * @param period
     * @param params
     * @return
     */
    Task findTaskByParamsAndPeriodAndBatch(@Param("userId") Integer userId, @Param("state") Integer state,
            @Param("period") Integer period, @Param("params") String params, @Param("batch") String batch);

    /**
     * 根据数据编号获取任务信息
     * 
     * @param state
     * @param dataKey
     * @return
     * @author leamo
     * @date 2016年5月16日 下午4:03:13
     */
    Task findTaskByDataKeyAndApp(@Param("state") Integer state,
            @Param("dataKey") String dataKey, @Param("appId") Integer appId,
            @Param("period") Integer period);

    /**
     * 根据数据编号、appId、projectId获取任务信息
     * 
     * @param state
     * @param dataKey
     * @param appId
     * @param projectId
     * @return
     * @author leamo
     * @date 2016年5月26日 上午11:51:16
     */
    Task findTaskDataAppPro(@Param("state") Integer state,
            @Param("dataKey") String dataKey, @Param("appId") Integer appId,
            @Param("projectId") Integer projectId);

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
    List<Map<String, Object>> findTaskPeriodNum(@Param("state") Integer state,
            @Param("appId") Integer appId, @Param("userId") Integer userId);

    /**
     * 获取乳腺癌产品的报告
     * 
     * @param pager
     * @param loginUserId
     * @param active
     * @param appIdRocky
     * @return
     */
    List<Task> findRockyTasks(Page pager, @Param("userId") Integer userId,
            @Param("state") Integer state, @Param("appId") Integer appId,
            @Param("sample") String sample,
            @Param("condition") String condition, @Param("sidx") String sidx,
            @Param("sord") String sord,
            @Param("batches") ArrayList<String> batches,
            @Param("periods") ArrayList<Integer> periods,
            @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    List<Task> findTaskAndDataInfo(Page pager, @Param("userId") Integer userId,
            @Param("state") Integer state, @Param("condition") String condition,
            @Param("tagId") Integer tagId, @Param("batch") String batch,
            @Param("period") Integer period,
            @Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("sord") String sord);

    List<String> findDataKeys(@Param("userId") Integer userId,
            @Param("state") Integer state, @Param("appId") Integer appId,
            @Param("proId") Integer proId, @Param("params") String params);

    List<Task> findAllByBatch(@Param("userId") Integer userId, @Param("appId") Integer appId,
            @Param("period") Integer period, @Param("state") Integer state, @Param("batch") String batch);

	Task selectByProjectId(@Param("projectId") Integer projectId);

}