package com.celloud.service;

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
     * 新增任务
     * 
     * @param task
     * @return
     */
    public Integer create(Task task);

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
    public Task updateToDone(Integer appId, Integer projectId, String dataKey,
            String dataKeys, String context);

    /**
     * 指定app正在运行的任务数
     * 
     * @param appId
     * @return
     */
    public Integer findRunningNumByAppId(Integer appId);

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
    public Task findTaskDataAppPro(String dataKey, Integer appId,
            Integer projectId);

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
    public PageList<Task> findTasksByUser(Page page, Integer userId);

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
    public PageList<Task> findTasksByUserCondition(Page page, Integer userId,
            String condition, Integer sort, String sortDate, String sortBatch,
            String sortName, String sortPeriod);

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
}
