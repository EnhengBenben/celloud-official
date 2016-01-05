package com.celloud.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.DataFile;

public interface DataFileMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(DataFile record);

    int insertSelective(DataFile record);

    DataFile selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(DataFile record);

    int updateByPrimaryKeyWithBLOBs(DataFile record);

    int updateByPrimaryKey(DataFile record);

    /**
     * (重构)统计帐号下的文件数量
     *
     * @param userId
     * @param state
     * @return
     * @author han
     * @date 2015年12月31日 上午10:27:24
     */
    public Integer countData(@Param("userId") Integer userId, @Param("state") Integer state);

    /**
     * (重构)统计帐号下的文件大小
     *
     * @param userId
     * @param state
     * @return
     * @author han
     * @date 2015年12月31日 上午10:27:40
     */
    public Long sumData(@Param("userId") Integer userId, @Param("state") Integer state);

    /**
     * (重构)按照时间段统计用户数据
     *
     * @param userId
     * @param time
     * @param state
     * @return
     * @author han
     * @date 2015年12月31日 上午10:27:50
     */
    public List<Map<String, String>> countDataByTime(@Param("userId") Integer userId, @Param("time") Integer time,
            @Param("state") Integer state);

    /**
     * (重构)按照时间段统计帐号下的文件大小
     *
     * @param userId
     * @param time
     * @param state
     * @return
     * @author han
     * @date 2015年12月31日 上午10:28:02
     */
    public List<Map<String, String>> sumDataByTime(@Param("userId") Integer userId, @Param("time") Integer time,
            @Param("state") Integer state);

    /**
     * (重构)添加上传文件信息
     *
     * @param data
     * @return
     * @author han
     * @date 2015年12月31日 上午10:28:28
     */
    public int addDataInfo(DataFile data);

    /**
     * (重构)修改数据信息
     *
     * @param data
     * @return
     * @author han
     * @date 2015年12月31日 上午10:28:37
     */
    public int updateDataInfoByFileId(DataFile data);
}