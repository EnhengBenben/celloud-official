package com.celloud.service;

import java.util.List;
import java.util.Map;

import com.celloud.model.DataFile;
import com.celloud.page.Page;
import com.celloud.page.PageList;

/**
 * 数据管理服务接口
 * 
 * @author han
 * @date 2015年12月23日 下午5:58:14
 */
public interface DataService {
    /**
     * 统计帐号下的文件数量
     * 
     * @param userId
     * @return
     */
    public Integer countData(Integer userId);

    /**
     * 统计帐号下的文件大小
     * 
     * @param userId
     * @return
     */
    public Long sumData(Integer userId);

    /**
     * 按照时间段统计用户数据
     * 
     * @param userId
     * @param time
     * @return
     */
    public List<Map<String, String>> countData(Integer userId, String time);

    /**
     * 按照时间段统计帐号下的文件大小
     * 
     * @param userId
     * @param time
     * @return
     * @date 2015年12月30日 下午4:03:12
     */
    public List<Map<String, String>> sumData(Integer userId, String time);

    /**
     * 添加上传文件信息
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

    /**
     * 根据数据编号获取数据类型
     * 
     * @param dataIds
     * @return -1：大于一种数据类型
     */
    Integer getFormatByIds(String dataIds);

    /**
     * 查询正在运行制定APP的数据id
     * 
     * @param dataIds
     * @param appId
     * @return
     */
    List<Integer> findRunningAppData(String dataIds, Integer appId);

    /**
     * 获取数据大小
     * 
     * @param dataIds
     * @return
     */
    String queryFileSize(String dataIds);

    /**
     * 根据数据编号查询数据详细信息
     * 
     * @param dataIds
     * @return
     */
    List<DataFile> findDatasById(String dataIds);

    /**
     * 新增项目数据关系
     * 
     * @param dataId
     * @param project
     * @return
     * @author leamo
     * @date 2016-1-10 下午4:43:57
     */
    Integer insertDataProjectRelat(String[] dataIdArr, Integer projectId);

    /**
     * app正在运行运行个数
     * 
     * @param appIds
     * @return
     * @author leamo
     * @date 2016-1-10 下午6:41:05
     */
    Integer dataRunning(String appIds);

    /**
     * 删除数据
     * 
     * @param data
     * @return
     * @author leamo
     * @date 2016-1-10 下午9:51:18
     */
    Integer delete(String dataIds);

    /**
     * 根据数据id获取数据详细
     * 
     * @param dataId
     * @return
     * @author leamo
     * @date 2016-1-10 下午10:02:17
     */
    DataFile getDataById(Integer dataId);

    /**
     * 获取物种信息
     * 
     * @param userId
     * @return
     * @author leamo
     * @date 2016-1-10 下午10:14:09
     */
    List<Map<String, String>> getStrainList(Integer userId);

    /**
     * 批量修改数据
     * 
     * @param dataId
     * @param data
     * @return
     * @author leamo
     * @date 2016-1-10 下午10:59:06
     */
    Integer updateDataByIds(String dataIds, DataFile data);

    /**
     * one by one修改数据
     * 
     * @param dataList
     * @return
     * @author leamo
     * @date 2016-1-10 下午11:11:35
     */
    Integer updateDatas(List<DataFile> dataList);

    /**
     * 查询用户的文件数量与运行的文件数量
     * 
     * @param userId
     * @return
     */
    public Map<String, String> countUserRunFileNum(Integer userId);

    /**
     * 检索某个项目下的所有数据
     * 
     * @param projectId
     * @return
     * @date 2016-1-9 上午3:05:40
     */
    public List<DataFile> getDatasInProject(Integer projectId);

    /**
     * 根据用户编号,统计各周的数据
     * 
     * @param userId
     * @return
     */
    public List<Map<String, String>> countDataFile(Integer userId);

    /**
     * 根据 DataKey 获取数据
     * 
     * @param dataKey
     * @return
     * @author lin
     * @date 2016-1-10 下午11:54:31
     */
    public DataFile getDataByKey(String dataKey);

}
