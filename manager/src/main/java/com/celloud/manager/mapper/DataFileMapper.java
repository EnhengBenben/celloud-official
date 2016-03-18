package com.celloud.manager.mapper;

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
    public int countDataFile(@Param("companyId")Integer companyId,@Param("state")int state);
    
    /**
     * 大客户下数据大小统计
     *
     * @param companyId
     * @param state
     * @return
     * @author han
     * @date 2016年3月10日 下午3:11:16
     */
    public long countDataFileSize(@Param("companyId")Integer companyId,@Param("state")int state);
}