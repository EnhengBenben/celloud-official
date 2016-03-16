package com.celloud.manager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.manager.model.DataFile;

public interface DataFileMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(DataFile record);

    int insertSelective(DataFile record);

    DataFile selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(DataFile record);

    int updateByPrimaryKeyWithBLOBs(DataFile record);

    int updateByPrimaryKey(DataFile record);
    
    /**
     * 大客户下数据个数统计
     *
     * @param companyId
     * @param state
     * @return
     * @author han
     * @date 2016年3月10日 下午3:06:36
     */
    public int countDataFile(@Param("companyId")Integer companyId,@Param("state")int state,@Param("testAccountIds")String testAccountIds);
    
    /**
     * 大客户下数据大小统计
     *
     * @param companyId
     * @param state
     * @return
     * @author han
     * @date 2016年3月10日 下午3:11:16
     */
    public long countDataFileSize(@Param("companyId")Integer companyId,@Param("state")int state,@Param("testAccountIds")String testAccountIds);
    
    /**
     * 按月份统计大客户数据
     *
     * @param companyId
     * @param state
     * @param testAccountIds
     * @return
     * @author han
     * @date 2016年3月14日 下午6:23:29
     */
    public List<Map<String,Object>> countDataFileByMonth(@Param("companyId")Integer companyId,@Param("state")int state,@Param("testAccountIds")String testAccountIds,@Param("order")String order);
    
    /**
     * 按用户统计大客户数据
     *
     * @param companyId
     * @param state
     * @param testAccountIds
     * @return
     * @author han
     * @date 2016年3月15日 下午3:45:13
     */
    public List<Map<String,Object>> countDataFileByUser(@Param("companyId")Integer companyId,@Param("state")int state,@Param("testAccountIds")String testAccountIds);
    /**
     * 按医院统计数据个数
     *
     * @param companyId
     * @param state
     * @param testAccountIds
     * @return
     * @author han
     * @date 2016年3月16日 上午10:33:17
     */
    public List<Map<String,Object>> countDataFileByCompany(@Param("companyId")Integer companyId,@Param("state")int state,@Param("testAccountIds")String testAccountIds);
    
    
    /**
     * 大客户统计
     *
     * @param state
     * @param testAccountIds
     * @return
     * @author han
     * @date 2016年3月16日 上午11:03:17
     */
    public List<Map<String,Object>> countBigCustomerDataFile(@Param("state")int state,@Param("testAccountIds")String testAccountIds);

}