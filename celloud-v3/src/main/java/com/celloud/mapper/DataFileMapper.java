package com.celloud.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.DataFile;
import com.celloud.page.Page;

public interface DataFileMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(DataFile record);

    int insertSelective(DataFile record);

    DataFile selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(DataFile record);

    int updateByPrimaryKeyWithBLOBs(DataFile record);

    int updateByPrimaryKey(DataFile record);

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
    public List<Map<String, String>> countDataByTime(@Param("userId") Integer userId, @Param("time") Integer time,
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
    public List<Map<String, String>> sumDataByTime(@Param("userId") Integer userId, @Param("time") Integer time,
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
    List<DataFile> findAllDataLists(Page page, @Param("userId") Integer userId,
            @Param("state") Integer state,
            @Param("reportType") Integer reportType,
            @Param("period") Integer period);

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
    List<DataFile> findDataLists(Page page, @Param("userId") Integer userId,
            @Param("condition") String condition, @Param("sort") Integer sort,
            @Param("sortDateType") String sortDateType,
            @Param("sortNameType") String sortNameType,
            @Param("state") Integer state,
            @Param("reportType") Integer reportType,
            @Param("period") Integer period);
    /**
	 * 根据用户编码查询用户已运行与未运行的文件数量
	 * 
	 * @param userId
	 * @return {"fileNum":"","runNum":""}上传的文件数量，有多少文件未运行
	 */
	public Map<String, String> countFileNumByUserId(@Param("userId") Integer userId);
}