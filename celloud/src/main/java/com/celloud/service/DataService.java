package com.celloud.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.celloud.model.mysql.DataFile;
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
	 * 修改数据信息
	 * 
	 * @param data
	 * @return
	 */
	public int updateDataInfoByFileIdAndTagId(DataFile data, Integer tagId);

	/**
	 * 数据分页列表
	 * 
	 * @param page
	 * @param userId
	 * @return
	 */
	PageList<DataFile> dataAllList(Page page, Integer userId);

	/**
	 * 产品中的数据分页列表
	 * 
	 * @param page
	 * @param userId
	 * @param appId
	 * @return
	 */
	PageList<DataFile> dataListByAppId(Page page, Integer userId, Integer appId, String condition, Integer sort,
			String sortDate, String sortName, String sortBatch);

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
	PageList<DataFile> dataLists(Page page, Integer userId, String condition, int sort, String sortDateType,
            String sortNameType, String sortAnotherName, String sortRun);

	/**
	 * 按条件检索数据列表
	 * 
	 * @param page
	 * @param userId
	 * @param condition
	 * @param sort
	 * @param sortDate
	 * @param sortBatch
	 * @param sortName
	 * @return
	 */
	PageList<DataFile> dataLists(Page page, Integer userId, String condition, int sort, String sortDate,
			String sortBatch, String sortName);

	/**
	 * 根据别名检索数据
	 * 
	 * @param userId
	 * @param anotherName
	 * @return
	 * @author lin
	 * @date 2016年4月6日下午4:19:41
	 */
	List<DataFile> getDataByAnotherName(Integer userId, String anotherName);

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
     * 检索某个项目下的所有数据
     * 
     * @param projectId
     * @return
     * @date 2016-1-9 上午3:05:40
     */
    public List<Map<String, Object>> getDatasMapInProject(Integer projectId);

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

	/**
	 * 根据dataKey批量获取数据
	 * 
	 * @param dataKey
	 * @return
	 * @author leamo
	 * @date 2016年1月14日 下午6:15:30
	 */
	public List<DataFile> selectDataByKeys(String dataKeys);

	/**
	 * 根据主键修改非空字段
	 * 
	 * @param record
	 * @return
	 * @author lin
	 * @date 2016年2月1日上午12:26:33
	 */
	public int updateByPrimaryKeySelective(DataFile record);

	/**
	 * 修改数据信息及其产品标签
	 * 
	 * @param record
	 * @return
	 * @author lin
	 * @date 2016年8月23日下午2:30:17
	 */
	public int updateDataAndTag(DataFile record);

	/**
	 * 获取同一批次的成对数据
	 * 
	 * @param batch
	 * @param fileName
	 * @return
	 * @author leamo
	 * @date 2016年5月10日 下午2:08:49
	 */
	public List<DataFile> getDataByBatchAndFileName(Integer userId, String batch, String fileName);

	/**
	 * 获取上传批次列表
	 * 
	 * @param userId
	 * @return
	 * @author leamo
	 * @date 2016年6月13日 下午3:19:51
	 */
	public List<String> getBatchList(Integer userId);

    /**
     * 
     * @description 根据appId获取批次列表
     * @author miaoqi
     * @date 2017年3月7日 下午6:10:24
     * @param userId
     * @param appId
     * @return
     */
    public List<String> getBatchListByAppId(Integer userId, Integer appId);

	public List<String> getBsiBatchList(Integer userId, Integer appId);

	/**
	 * 查询乳腺癌产品的数据列表
	 * 
	 * @param pager
	 * @param sample
	 * @param condition
	 * @param sidx
	 * @param sord
	 * @return
	 */
	public PageList<Map<String, Object>> filterRockyList(Page pager, String sample, String condition, String sidx,
            String sord, Integer appId);

	public String getAnotherName(HttpServletRequest request, String filePath, String fileDataKey);

	public String getAnotherName(String perlPath, String filePath, String outPath);

	public int updateFileInfo(Integer dataId, String dataKey, String filePath, String batch, Integer fileFormat,
			String md5, String anotherName, Integer tagId);

	public void updateUploadState(Integer fileId, String objectKey, int state);

	public Integer addFileInfo(Integer userId, String fileName);

	/**
	 * 
	 * @author miaoqi
	 * @date 2016年10月8日下午4:44:19
	 * @description 从TbTask中获取运行结束的数据报告
	 *
	 */
    public List<Map<String, Object>> getDataFileFromTbTask(Integer projectId);

	/**
	 * 创建文件
	 * @param userId
	 * @param objectKey
	 * @return
	 */
	public Integer addAndRunFile(Integer userId, String objectKey);

    /**
     * 获取样本id
     * 
     * @param dataKey
     * @return
     * @author leamo
     * @date 2016年11月29日 上午10:43:44
     */
    Integer getSampleIdByDataKey(String dataKey);

    /**
     * 
     * @description 根据dataKey获取batch
     * @author miaoqi
     * @date 2017年2月24日 下午6:15:51
     * @param dataKey
     * @return
     */
    String getBatchByDataKey(String dataKey);
}
