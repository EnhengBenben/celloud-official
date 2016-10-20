package com.celloud.manager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.manager.model.App;
import com.celloud.manager.page.Page;

public interface AppMapper {
    int deleteByPrimaryKey(Integer appId);

    int insert(App record);

    int insertSelective(App record);

    App selectByPrimaryKey(Integer appId);

    int updateByPrimaryKeySelective(App record);

    int updateByPrimaryKeyWithBLOBs(App record);

    int updateByPrimaryKey(App record);

    /**
     * 统计大客户下的app数量
     *
     * @param companyId
     * @param offLine
     * @return
     * @author han
     * @date 2016年3月10日 下午2:10:51
     */
    public int countApp(@Param("companyId") Integer companyId, @Param("offLine") int offLine);

    /**
     * 统计大客户下的app运行次数
     *
     * @param companyId
     * @param offLine
     * @param flag
     * @param period
     * @return
     * @author han
     * @date 2016年3月11日 下午3:12:16
     */
    public List<Map<String, Integer>> countAppRunNum(@Param("companyId") Integer companyId,
            @Param("offLine") int offLine, @Param("flag") Integer flag, @Param("period") int period,
            @Param("testAccountIds") String testAccountIds);

    /**
     * 统计大客户下的用户运行 app次数
     *
     * @param companyId
     * @param offLine
     * @param flag
     * @param period
     * @return
     * @author han
     * @date 2016年3月11日 下午3:22:33
     */
    public List<Map<String, Integer>> countAppRunNumByUser(@Param("companyId") Integer companyId,
            @Param("offLine") int offLine, @Param("flag") Integer flag, @Param("period") int period,
            @Param("testAccountIds") String testAccountIds);

    /**
     * 统计大客户下的app
     *
     * @param companyId
     * @param offLine
     * @return
     * @author han
     * @date 2016年3月18日 下午2:44:02
     */
    public List<App> getAppOfBigCustomer(@Param("companyId") Integer companyId, @Param("offLine") int offLine);

    /**
     * 大客户的APP价格列表
     * 
     * @param companyId
     * @param offLine
     * @param priceType
     * @return
     * @author leamo
     * @date 2016年3月18日 下午4:47:11
     */
    public List<App> appPriceList(@Param("companyId") Integer companyId, @Param("offLine") int offLine,
            @Param("priceType") Byte priceType);

    /**
     * 按大客户统计app运行次数
     *
     * @param offLine
     * @param flag
     * @param period
     * @param testAccountIds
     * @return
     * @author han
     * @date 2016年3月21日 下午2:04:36
     */
    public List<Map<String, Integer>> countAppRunNumByBigCustomer(@Param("offLine") int offLine,
            @Param("flag") Integer flag, @Param("period") int period, @Param("testAccountIds") String testAccountIds);

    /**
     * 获取app列表
     *
     * @param companyId
     *            大客户编号
     * @param attribute
     *            软件可添加权限 0-public（所有人可添加） 1-private（软件所有公司的用户可添加）
     * @return
     * @author han
     * @date 2016年1月29日 下午2:08:29
     */

    List<App> getAppListByCompany(@Param("companyId") int companyId, @Param("attribute") Integer attribute,
            @Param("offLine") Integer offLine);

    /**
     * 根据attribute获取非工具类appList
     *
     * @param attribute
     * @param runType
     *            运行方式 0-直接运行 1-需添加然后运行 2-可直 接运行可添加
     * @return
     * @author han
     * @date 2016年1月29日 下午2:12:11
     */

    List<App> getAppListAdded(@Param("attribute") Integer attribute, @Param("offLine") Integer offLine);

    /**
     * 分页查询app列表
     *
     * @param page
     * @return
     * @author han
     * @date 2016年2月23日 下午2:04:18
     */
    public List<App> getAppListByPage(Page page);

    public int updateAppOffline(@Param("offLine") Integer offLine, @Param("appId") Integer appId);

    public int insertApp(App app);

    public App getAppById(@Param("appId") Integer appId);

    public int insertAppFileFormatBatch(@Param("appId") Integer appId, @Param("formatIds") Integer[] formatIds);

    public int insertAppClassifyBatch(@Param("appId") Integer appId, @Param("calssifyIds") Integer[] calssifyIds);

    public int appNameExist(@Param("appId") Integer appId, @Param("appName") String appName);

    public int deleteAppFileFormat(@Param("appId") Integer appId);

    public int deleteAppClassify(@Param("appId") Integer appId);

    public int updateApp(App app);

    public void deleteAppRightByAppIdsAndUserId(@Param("apps") List<App> apps, @Param("userId") Integer userId);

    /**
     * 
     * @author MQ
     * @date 2016年7月21日下午3:46:52
     * @description 根据appId从关系表中查找用户id
     *
     */
    public List<Integer> findUserIdsByAppId(@Param("appId") Integer appId);

    /**
     * 
     * @author MQ
     * @date 2016年7月21日下午4:08:56
     * @description 根据appId删除用户和app的关系
     *
     */
    public int deleteUserAppRight(@Param("appId") Integer appId, @Param("testAccountIds") String testAccountIds);

    /**
     * 
     * @author MQ
     * @date 2016年7月21日下午4:12:45
     * @description 建立用户和app的关系
     *
     */
    public int insertUserAppRight(@Param("appId") Integer appId, @Param("userIds") Integer[] userIds);

}