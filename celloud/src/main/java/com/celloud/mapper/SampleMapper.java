package com.celloud.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.Sample;
import com.celloud.page.Page;

public interface SampleMapper {
    int deleteByPrimaryKey(Integer sampleId);

    int insertSelective(Sample record);

    Sample selectByPrimaryKey(Integer sampleId);

    int updateByPrimaryKeySelective(Sample record);

    int updateAddTypeById(@Param("sampleIds") List<Integer> sampleIds,
            @Param("isAdd") Integer isAdd,
            @Param("orderId") Integer orderId);

    List<Sample> selectAllByUser(@Param("userId") Integer userId,
            @Param("isAdd") Integer isAdd, @Param("state") Integer state,
            @Param("experState") Integer experState);

    List<Map<String, String>> listSampleAndPatient(@Param("userId") Integer userId, @Param("orderId") Integer orderId,
            @Param("isAdd") Integer isAdd,
            @Param("state") Integer state);

    List<Map<String, Object>> listSample(@Param("userId") Integer userid, @Param("isAdd") Integer isAdd,
            @Param("state") Integer state);

    Sample selectByName(@Param("userId") Integer userId,
            @Param("sampleName") String sampleName,
            @Param("state") Integer state);

    Sample selectByNameAndNotIn(@Param("userId") Integer userId, @Param("sample") Sample sample,
            @Param("state") Integer state);

    int deleteList(@Param("sampleIds") List<Integer> sampleIds);

    List<Sample> getSamples(Page page, @Param("userId") Integer userId,
            @Param("experState") Integer experState,
            @Param("state") Integer state);

    Sample getByNameExperState(@Param("orderNo") String orderNo,
            @Param("sampleName") String sampleName,
            @Param("experState") Integer experState,
            @Param("state") Integer state, @Param("isAdd") Integer isAdd);

    int addSampleTagRelat(@Param("sampleId") Integer sampleId,
            @Param("tagId") Integer tagId);

    List<Sample> getSamplesByOrder(@Param("userId") Integer userId,
            @Param("orderId") Integer orderId, @Param("state") Integer state);

    Sample getSampleByNameAndOrderNo(@Param("sampleName") String sampleName,
            @Param("orderNo") String orderNo,
            @Param("state") Integer state);

    Sample getByExperNameExperState(
            @Param("experSampleName") String experSampleName,
            @Param("experState") Integer experState,
            @Param("state") Integer state, @Param("isAdd") Integer isAdd);

    List<Sample> getSamplesByStorageName(
            @Param("storageName") String storageName,
            @Param("state") Integer state);

    Sample getSampleByExperName(
            @Param("experSampleName") String experSampleName,
            @Param("state") Integer state);

    List<Sample> getSamplesExperState(Page page,
            @Param("userId") Integer userId,
            @Param("sampleName") String sampleName,
            @Param("state") Integer state);

	/**
	 * 
	 * @author miaoqi
	 * @description 根据id集合查找样本集合
	 * @date 2017年1月12日
	 *
	 * @param ids
	 * @return
	 */
	List<Sample> selectByIds(@Param("ids") List<Integer> ids);

    List<Sample> getSamplesByStorageId(@Param("ssid") Integer storageId, @Param("state") Integer state);

    /**
     * 
     * @description 根据样本id查询样本和病人信息
     * @author miaoqi
     * @date 2017年2月14日 下午2:13:29
     * @param userId
     * @param sampleId
     * @param notadd
     * @param active
     * @return
     */
    Map<String, String> getSampleAndPatient(@Param("userId") Integer userId, @Param("sampleId") Integer sampleId,
            @Param("isAdd") Integer isAdd,
            @Param("state") Integer state);

    /**
     * 
     * @description 根据sampleId和tagId从关系表中查找主键
     * @author miaoqi
     * @date 2017年2月16日 上午11:31:36
     * @param sampleId
     * @param tagId
     * @return
     */
    Integer getKeyBySampleIdAndTagId(@Param("sampleId") Integer sampleId, @Param("tagId") Integer tagId);

    /**
     * 
     * @description 根据主键更新sampleId和tagId的关系
     * @author miaoqi
     * @date 2017年2月16日 上午11:35:42
     * @param id
     * @param sampleId
     * @param tagId
     * @return
     */
    Integer updateSampleIdAndTagIdByKey(@Param("id") Integer id, @Param("sampleId") Integer sampleId,
            @Param("tagId") Integer tagId);
}