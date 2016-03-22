package com.celloud.manager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.manager.model.App;

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
    public int countApp(@Param("companyId") Integer companyId,
            @Param("offLine") int offLine);

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
    public List<Map<String, Integer>> countAppRunNum(
            @Param("companyId") Integer companyId,
            @Param("offLine") int offLine, @Param("flag") Integer flag,
            @Param("period") int period,
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
    public List<Map<String, Integer>> countAppRunNumByUser(
            @Param("companyId") Integer companyId,
            @Param("offLine") int offLine, @Param("flag") Integer flag,
            @Param("period") int period,
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
    public List<App> getAppOfBigCustomer(@Param("companyId") Integer companyId,
            @Param("offLine") int offLine);

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
    public List<App> appPriceList(@Param("companyId") Integer companyId,
            @Param("offLine") int offLine, @Param("priceType") Byte priceType);

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
    public List<Map<String, Integer>> countAppRunNumByBigCustomer(
            @Param("offLine") int offLine, @Param("flag") Integer flag,
            @Param("period") int period,
            @Param("testAccountIds") String testAccountIds);
}