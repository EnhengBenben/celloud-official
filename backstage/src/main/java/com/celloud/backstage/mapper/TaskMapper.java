package com.celloud.backstage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.backstage.model.Task;
import com.celloud.backstage.page.Page;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer taskId);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer taskId);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKeyWithBLOBs(Task record);

    int updateByPrimaryKey(Task record);
    
    
    List<Map<String,String>> getRunningTimeByPage(Page page,@Param("keyword") String keyword);

    Map<String, String> getQueuingTime();
    
    int getTotalRecordCount();
    
    int getCountByState(int state);
    
    int getFailCount();

    /**
     * 
     * @author MQ
     * @date 2016年8月3日上午10:41:36
     * @description 获取上一周用户及登录次数top10
     *
     */
    public List<Map<String, Object>> getLastWeekUserLogin(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月3日上午11:35:24
     * @description 获取上一周app运行次数top10
     *
     */
    public List<Map<String, Object>> getLastWeekAppRun(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月3日上午11:36:14
     * @description 获取上一周用户及数据大小top10
     *
     */
    public List<Map<String, Object>> getLastWeekDataSize(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月4日下午5:55:35
     * @description 获取活跃用户数量
     *
     */
    public Integer getActiveUserCount(@Param("testAccountIds") String testAccountIds);


    /**
     * 
     * @author MQ
     * @date 2016年7月13日上午9:36:56
     * @description 获取历史周登录统计
     * @param companyId
     * @param testAccountIds
     * @return
     *
     */
    public List<Map<String, Object>> getHistoryWeekUserLogin(@Param("companyId") Integer companyId,
            @Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年7月13日上午9:36:56
     * @description 获取历史周活跃用户
     * @param companyId
     * @param testAccountIds
     * @return
     *
     */
    public List<Map<String, Object>> getHistoryWeekActiveUser(@Param("companyId") Integer companyId,
            @Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年7月13日上午10:09:30
     * @description 获取历史周App运行次数
     * @param companyId
     * @param testAccountIds
     * @return
     *
     */
    public List<Map<String, Object>> getHistoryWeekAppRun(@Param("companyId") Integer companyId,
            @Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年7月13日上午11:16:05
     * @description 获取历史周App活跃数
     * @param companyId
     * @param testAccountIds
     * @return
     *
     */
    public List<Map<String, Object>> getHistoryWeekAppActive(@Param("companyId") Integer companyId,
            @Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年7月13日下午1:00:50
     * @description 获取历史周数据大小
     * @param companyId
     * @param testAccountIds
     * @return
     *
     */
    public List<Map<String, Object>> getHistoryWeekDataSize(@Param("companyId") Integer companyId,
            @Param("testAccountIds") String testAccountIds);
}