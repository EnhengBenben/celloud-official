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
    public List<Map<String, Object>> getLastWeekUserLoginTop(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月3日上午11:35:24
     * @description 获取上一周app运行次数top10
     *
     */
    public List<Map<String, Object>> getLastWeekAppRunTop(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月3日上午11:36:14
     * @description 获取上一周用户及数据大小文件数量top10
     *
     */
    public List<Map<String, Object>> getLastWeekDataSizeTop(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月8日下午3:25:35
     * @description 获取全部用户的登录次数top10
     * @param testAccountIds
     *            测试账号
     * @return 总量用户登录次数top10
     *
     */
    public List<Map<String, Object>> getUserLoginTop(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月8日下午3:24:40
     * @description 获取全部用户的app运行次数top10
     * @param testAccountIds
     *            测试账号
     * @return 总量App运行次数top10
     *
     */
    public List<Map<String, Object>> getAppRunTop(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月8日下午3:24:11
     * @description 获取总量上传数据的top10
     * @param testAccountIds
     *            测试账号
     * @return 总量上传数据大小top10
     *
     */
    public List<Map<String, Object>> getDataSizeTop(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月4日下午5:55:35
     * @description 获取截止到本周周一的活跃用户数量
     *
     */
    public Integer getActiveUserCount(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月5日下午2:04:05
     * @description 获取截止到上一周周一的活跃用户数量
     *
     */
    public Integer getLastActiveUserCount(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月8日上午9:51:46
     * @description 获取截止到本周周一的App总量
     *
     */
    public Integer getAppCount();

    /**
     * 
     * @author MQ
     * @date 2016年8月8日上午9:56:15
     * @description 获取截止到上一周周一的App总量
     *
     */
    public Integer getLastAppCount();

    /**
     * 
     * @author MQ
     * @date 2016年8月8日上午10:45:40
     * @description 获取文件大小和数量
     *
     */
    public Map<String, Object> getFileSizeAndCount(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月8日下午2:33:58
     * @description 根据flag标志位获取上一周报告数量
     * @param flag
     *            0:数据报告 1:项目报告
     * @return 报告数量
     *
     */
    public Integer getWeekReportCountByFlag(@Param("flag") int flag);

    /**
     * 
     * @author MQ
     * @date 2016年8月8日下午2:49:36
     * @description 获取上一周的客户端用户登录次数
     * @return 客户端用户登录次数
     *
     */
    public Integer getWeekClientLoginCount(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月9日下午2:00:16
     * @description 获取一周内每天的用户的登录数量
     * @param testAccountIds
     *            测试账号id
     * @return 日期-登录数的map
     *
     */
    public List<Map<String, Integer>> findLoginCountByDay(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月9日下午5:19:20
     * @description 获取一周内每个用户的登录数量
     * @param testAccountIds
     *            测试账号
     * @return 用户-登录数的map
     *
     */
    public List<Map<String, Integer>> findLoginCountByUsername(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月10日上午9:26:34
     * @description 获取一周内每天的app运行数量
     * @param testAccountIds
     *            测试账号id
     * @return 日期-app运行数的map
     *
     */
    public List<Map<String, Integer>> findAppRunCountByDay(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月10日上午9:55:17
     * @description 获取一周内每个app的运行数量
     * @param testAccountIds
     *            测试账号id
     * @return app-app运行次数的map
     *
     */
    public List<Map<String, Integer>> findAppRunCount(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月10日上午10:24:18
     * @description 获取一周内每个用户的上传数据大小
     * @param testAccountIds
     *            测试账号id
     * @return 用户名-数据大小的map
     *
     */
    public List<Map<String, Object>> findFileSizeByUsername(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月10日上午10:56:43
     * @description 获取一周内每个用户的上传数据数量
     * @param testAccountIds
     *            测试账号id
     * @return 用户名-数据数量的map
     *
     */
    public List<Map<String, Object>> findFileCountByUsername(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月10日上午11:03:56
     * @description 获取一周内每天上传数大小
     * @param testAccountIds
     *            测试账号id
     * @return 日期-数据大小的map
     *
     */
    public List<Map<String, Object>> findFileSizeByDay(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月10日上午11:26:30
     * @description 获取一周内客户端上传数据数量
     * @param testAccountIds
     *            测试账号id
     * @return 用户名-数据数量的map
     *
     */
    public List<Map<String, Object>> findFileCountByClient(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月10日下午1:57:13
     * @description 获取一周内每个用户运行每个app的次数
     * @param testAccountIds
     *            测试账号id
     * @return 用户名^app-运行次数的map
     *
     */
    public List<Map<String, Object>> findAppRunCountByUsername(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月10日下午2:53:19
     * @description 获取一周内每个操作系统下每个浏览器的使用次数
     * @param testAccountIds
     *            测试账号id
     * @return 操作系统^浏览器-使用次数的map
     *
     */
    public List<Map<String, Object>> findOsBrowser(@Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月10日下午4:27:51
     * @description 查询当前在线医院数量
     * @return 在线医院数量
     *
     */
    public Integer findCompanyCount();

    /**
     * 
     * @author MQ
     * @date 2016年8月10日下午6:45:11
     * @description 获取上一周登录次数
     * @param companyId
     *            医院编号
     * @return
     *
     */
    public Map<String, Object> getLastWeekUserLogin(Integer companyId, @Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月10日下午6:44:50
     * @description 获取上一周活跃用户
     * @param companyId
     *            医院编号
     * @return
     *
     */
    public Map<String, Object> getLastWeekActiveUser(Integer companyId, @Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月10日下午6:44:20
     * @description 获取上一周App运行次数
     * @param companyId
     *            医院编号
     * @return
     *
     */
    public Map<String, Object> getLastWeekAppRun(Integer companyId, @Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月10日下午6:44:04
     * @description 获取上一周App活跃数
     * @param companyId
     *            医院编号
     * @return
     *
     */
    public Map<String, Object> getLastWeekAppActive(Integer companyId, @Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年8月10日下午6:43:08
     * @description 获取上一周数据大小
     * @param companyId
     *            医院编号
     * @return
     *
     */
    public Map<String, Object> getLastWeekDataSize(Integer companyId, @Param("testAccountIds") String testAccountIds);
}