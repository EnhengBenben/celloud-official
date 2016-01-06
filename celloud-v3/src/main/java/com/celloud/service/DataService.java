package com.celloud.service;

import java.util.List;
import java.util.Map;

import com.celloud.model.DataFile;
import com.celloud.page.Page;
import com.celloud.page.PageList;

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
    public Integer countData(Integer userId);

    /**
     * (重构)统计帐号下的文件大小
     * 
     * @param userId
     * @return
     */
    public Long sumData(Integer userId);

    /**
     * (重构)按照时间段统计用户数据
     * 
     * @param userId
     * @param time
     * @return
     */
    public List<Map<String, String>> countData(Integer userId, Integer time);

    /**
     * (重构)按照时间段统计帐号下的文件大小
     *
     * @param userId
     * @param time
     * @return
     * @date 2015年12月30日 下午4:03:12
     */
    public List<Map<String, String>> sumData(Integer userId, Integer time);

    /**
     * (重构)添加上传文件信息
     * 
     * @param data
     * @return
     */
    public int addDataInfo(DataFile data);

    /**
     * 修改数据信息
     * 
     * @param data
     * @return
     */
    public int updateDataInfoByFileId(DataFile data);

    /**
     * 数据分页列表
     * 
     * @param page
     * @param userId
     * @return
     */
    PageList<DataFile> dataAllList(Page page, Integer userId);

    /**
     * 按条件检索数据列表
     * 
     * @param page
     * @param userId
     * @param condition
     * @param sort
     * @param sortDateType
     * @param sortNameType
     * @return
     */
    PageList<DataFile> dataLists(Page page, Integer userId, String condition,
            int sort, String sortDateType, String sortNameType);
}
