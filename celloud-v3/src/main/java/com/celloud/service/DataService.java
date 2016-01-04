package com.celloud.service;

import java.util.List;
import java.util.Map;

import com.celloud.model.DataFile;

/**
 * 数据管理服务接口重构
 *
 * @author han
 * @date 2015年12月23日 下午5:58:14
 */
public interface DataService {
    /**
     * (重构)统计帐号下的文件数量
     * 
     * @param userId
     * @return
     */
    Integer countData(Integer userId);

    /**
     * (重构)统计帐号下的文件大小
     * 
     * @param userId
     * @return
     */
    Long sumData(Integer userId);

    /**
     * (重构)按照时间段统计用户数据
     * 
     * @param userId
     * @param time
     * @return
     */
    List<Map<String, String>> countData(Integer userId, Integer time);
    
    /**
     *(重构)按照时间段统计帐号下的文件大小
     *
     * @param userId
     * @param time
     * @return
     * @date 2015年12月30日 下午4:03:12
     */
    List<Map<String, String>> sumData(Integer userId, Integer time);

    /**
     * (重构)添加上传文件信息
     * 
     * @param data
     * @return
     */
    int addDataInfo(DataFile data);

    /**
     * 修改数据信息
     * 
     * @param data
     * @return
     */
    int updateDataInfoByFileId(DataFile data);
}
