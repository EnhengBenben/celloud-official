package com.celloud.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.DataFile;
import com.celloud.page.Page;

public interface DataFileMapper {
	int deleteByPrimaryKey(Integer fileId);

	int insert(DataFile record);

	int insertSelective(DataFile record);

	DataFile selectByPrimaryKey(Integer fileId);

	/**
	 * 根据 datakey 获取数据
	 * 
	 * @param dataKey
	 * @return
	 * @author lin
	 * @date 2016-1-10 下午11:56:02
	 */
	public DataFile selectByDataKey(@Param("dataKey") String dataKey);

	/**
	 * 根据dataKey批量获取数据
	 * 
	 * @param dataKeys
	 * @return
	 * @author leamo
	 * @date 2016年1月14日 下午6:13:00
	 */
	List<DataFile> selectByDataKeys(@Param("dataKey") String dataKeys);

	int updateByPrimaryKeySelective(DataFile record);

	int updateByPrimaryKeyWithBLOBs(DataFile record);

	int updateByPrimaryKey(DataFile record);

	int deleteDataTag(Integer fileId);

	int insertDataTag(DataFile dataFile);

	/**
	 * 校验数据是否已存在了tag
	 * 
	 * @param fileId
	 * @param tagId
	 * @return
	 */
	public int selectTagRelat(@Param("fileId") Integer fileId, @Param("tagId") Integer tagId);

	Integer insertFileTagRelat(@Param("fileId") Integer fileId, @Param("tagId") Integer tagId);

	/**
	 * 检索某个项目下的所有数据
	 * 
	 * @param projectId
	 * @return
	 * @date 2016-1-9 上午3:06:47
	 */
	public List<DataFile> getDatasInProject(@Param("projectId") Integer projectId);

    /**
     * 检索某个项目下的所有数据
     * 
     * @param projectId
     * @return
     * @date 2016-1-9 上午3:06:47
     */
    public List<Map<String, Object>> getDatasMapInProject(@Param("projectId") Integer projectId);

	/**
	 * 统计帐号下的文件数量
	 * 
	 * @param userId
	 * @param state
	 * @return
	 * @author han
	 * @date 2015年12月31日 上午10:27:24
	 */
	public Integer countData(@Param("userId") Integer userId, @Param("state") Integer state);

	/**
	 * 统计帐号下的文件大小
	 * 
	 * @param userId
	 * @param state
	 * @return
	 * @author han
	 * @date 2015年12月31日 上午10:27:40
	 */
	public Long sumData(@Param("userId") Integer userId, @Param("state") Integer state);

	/**
	 * 按照时间段统计用户数据
	 * 
	 * @param userId
	 * @param time
	 * @param state
	 * @return
	 * @author han
	 * @date 2015年12月31日 上午10:27:50
	 */
	public List<Map<String, String>> countDataByTime(@Param("userId") Integer userId, @Param("time") String time,
			@Param("state") Integer state);

	/**
	 * 按照时间段统计帐号下的文件大小
	 * 
	 * @param userId
	 * @param time
	 * @param state
	 * @return
	 * @author han
	 * @date 2015年12月31日 上午10:28:02
	 */
	public List<Map<String, String>> sumDataByTime(@Param("userId") Integer userId, @Param("time") String time,
			@Param("state") Integer state);

	/**
	 * 添加上传文件信息
	 * 
	 * @param data
	 * @return
	 * @author han
	 * @date 2015年12月31日 上午10:28:28
	 */
	public int addDataInfo(DataFile data);

	/**
	 * 修改数据信息
	 * 
	 * @param data
	 * @return
	 * @author han
	 * @date 2015年12月31日 上午10:28:37
	 */
	public int updateDataInfoByFileId(DataFile data);

	/**
	 * 所有数据分页列表
	 * 
	 * @param page
	 * @param userId
	 * @param state
	 * @param reportType
	 * @param period
	 * @return
	 */
	List<DataFile> findAllDataLists(Page page, @Param("userId") Integer userId, @Param("state") Integer state,
			@Param("reportType") Integer reportType, @Param("period") Integer period);

	/**
	 * 所有数据分页列表
	 * 
	 * @param page
	 * @param userId
	 * @param state
	 * @param reportType
	 * @param period
	 * @return
	 */
	List<DataFile> findDataListsByAppId(Page page, @Param("userId") Integer userId, @Param("state") Integer state,
			@Param("appId") Integer appId, @Param("condition") String condition, @Param("sort") Integer sort,
			@Param("sortDate") String sortDate, @Param("sortName") String sortName,
			@Param("sortBatch") String sortBatch);

	/**
	 * 按条件检索数据列表
	 * 
	 * @param page
	 * @param userId
	 * @param condition
	 * @param sort
	 * @param sortDateType
	 * @param sortNameType
	 * @param state
	 * @param reportType
	 * @param period
	 * @return
	 */
	List<DataFile> findDataLists(Page page, @Param("userId") Integer userId, @Param("condition") String condition,
			@Param("sort") Integer sort, @Param("sortDateType") String sortDateType,
			@Param("sortNameType") String sortNameType, @Param("state") Integer state,
            @Param("reportType") Integer reportType, @Param("period") Integer period,
            @Param("sortAnotherName") String sortAnotherName,
            @Param("sortRun") String sortRun);

	/**
	 * 按条件检索数据列表
	 * 
	 * @param page
	 * @param userId
	 * @param condition
	 * @param sort
	 * @param sortDateType
	 * @param sortNameType
	 * @param state
	 * @param reportType
	 * @param period
	 * @return
	 */
	List<DataFile> findDataListsSortMore(Page page, @Param("userId") Integer userId,
			@Param("condition") String condition, @Param("sort") Integer sort, @Param("sortDate") String sortDate,
			@Param("sortBatch") String sortBatch, @Param("sortName") String sortName, @Param("state") Integer state,
			@Param("reportType") Integer reportType, @Param("period") Integer period);

	/**
	 * 根据数据id获取数据文件类型及各类型数量
	 * 
	 * @param dataIds
	 * @return
	 */
	Map<String, Object> findFormatByIds(@Param("dataIds") String dataIds);

	/**
	 * 查询正在运行制定APP的数据id
	 * 
	 * @param dataIds
	 * @param appId
	 * @return
	 */
	List<Integer> findRunningAppData(@Param("dataIds") String dataIds, @Param("appId") Integer appId,
			@Param("state") Integer state, @Param("period") Integer period);

	/**
	 * 获取数据大小
	 * 
	 * @param dataIds
	 * @return
	 */
	String queryFileSize(@Param("dataIds") String dataIds);

	/**
	 * 根据数据编号查询数据详细信息
	 * 
	 * @param dataIds
	 * @return
	 */
	List<DataFile> findDatasById(@Param("dataIds") String dataIds);

	/**
	 * 批量修改数据
	 * 
	 * @param dataId
	 * @param data
	 * @return
	 * @author leamo
	 * @date 2016-1-10 下午10:59:06
	 */
	Integer updateDataByIds(@Param("dataIds") String dataIds, @Param("strain") String strain,
			@Param("sample") String sample, @Param("updateDate") Date updateDate,
			@Param("anotherName") String anotherName, @Param("dataTags") String dataTags);

	/**
	 * app正在运行运行个数
	 * 
	 * @param appIds
	 * @return
	 * @author leamo
	 * @date 2016-1-10 下午6:41:05
	 */
	Integer queryDataRunning(@Param("appIds") String appIds, @Param("period") Integer period,
			@Param("state") Integer state, @Param("flag") Integer flag);

	/**
	 * 获取用户输入的物种列表
	 * 
	 * @param userId
	 * @return
	 * @author leamo
	 * @date 2016-1-10 下午10:17:19
	 */
	List<String> queryStrainList(Integer userId);

	/**
	 * 根据用户编码查询用户已运行与未运行的文件数量
	 * 
	 * @param userId
	 * @return {"fileNum":"","runNum":""}上传的文件数量，有多少文件未运行
	 */
	public Map<String, String> countFileNumByUserId(@Param("userId") Integer userId);

	/**
	 * 根据用户编号,统计各周的数据
	 * 
	 * @param userId
	 * @return
	 */
	public List<Map<String, String>> countDataFileWeek(@Param("userId") Integer userId);

	/**
	 * 根据别名检索数据
	 * 
	 * @param userId
	 * @param anotherName
	 * @return
	 * @author lin
	 * @date 2016年4月6日下午4:22:30
	 */
	List<DataFile> getDataByAnotherName(@Param("userId") Integer userId, @Param("anotherName") String anotherName,
			@Param("state") int state);

	/**
	 * 获取同一批次的成对数据
	 * 
	 * @param batch
	 * @param fileName
	 * @return
	 * @author leamo
	 * @date 2016年5月10日 下午2:08:49
	 */
	List<DataFile> getDataByBatchAndFileName(@Param("userId") Integer userId, @Param("batch") String batch,
			@Param("fileName") String fileName, @Param("state") int state);

	/**
	 * 获取上传批次列表
	 * 
	 * @param userId
	 * @return
	 * @author leamo
	 * @date 2016年6月13日 下午3:19:51
	 */
	List<String> getBatchList(@Param("userId") Integer userId, @Param("state") int state);

	List<String> getBsiBatchList(@Param("userId") Integer userId, @Param("state") int state,
	        @Param("appId") Integer appId);

	List<Map<String, Object>> filterRockyList(Page page, @Param("userId") Integer userId, @Param("state") Integer state,
			@Param("reportType") Integer reportType, @Param("period") Integer period, @Param("sample") String sample,
			@Param("condition") String condition, @Param("sidx") String sidx, @Param("sord") String sord);

	/**
	 * 
	 * @description 从TbTask表中获取运行结束的数据报告
	 * @author miaoqi
	 * @date 2016年10月8日下午4:51:01
	 *
	 * @param projectId
	 *            项目id
	 * @return 项目下数据报告对应的文件集合
	 *
	 */
    List<Map<String, Object>> getDataFileFromTbTask(@Param("projectId") Integer projectId);

	/**
	 * 
	 * @description 查询某个userId下某个appId的文件的md5出现次数大于1的file_id和md5值
	 * @author miaoqi
	 * @date 2016年10月19日下午1:47:38
	 *
	 * @param userId
	 * @param appId
	 * @return
	 */
	@MapKey("fileId")
	Map<Integer, Map<String, String>> getMd5FileIdMap(@Param("userId") Integer userId, @Param("appId") Integer appId);

    /**
     * 插入数据、样本关系
     * 
     * @param fileId
     * @param sampleId
     * @return
     * @author leamo
     * @date 2016年11月29日 上午10:18:39
     */
    Integer addFileSampleRelat(@Param("fileId") Integer fileId,
            @Param("sampleId") Integer sampleId);

    /**
     * 获取样本id
     * 
     * @param dataKey
     * @return
     * @author leamo
     * @date 2016年11月29日 上午10:43:44
     */
    Long getSampleIdByDataKey(@Param("dataKey") String dataKey);

    Integer getFileSampleCount(@Param("fileId") Integer fileId, @Param("sampleId") Integer sampleId);
}