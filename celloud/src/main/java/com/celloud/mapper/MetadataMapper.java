package com.celloud.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.Metadata;

public interface MetadataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Metadata record);

    int insertSelective(Metadata record);

    Metadata selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Metadata record);

    int updateByPrimaryKey(Metadata record);

	public List<Metadata> getMetadata(@Param("appId") Integer appId, @Param("flag") Integer flag,
			@Param("state") Integer state);

    /**
     * 
     * @description 根据tagId和flag查询元数据返回给Select2
     * @author miaoqi
     * @date 2017年2月13日 下午4:23:02
     * @param tagId
     * @param flagId
     * @return
     */
    List<Map<String, String>> getMetadataToSelectByTagIdAndFlag(@Param("tagId") Integer tagId,
            @Param("flag") Integer flag);

    /**
     * 
     * @description name为key,seq为value获取map
     * @author miaoqi
     * @date 2017年2月23日 下午4:19:19
     * @return
     */
    @MapKey("name")
    Map<String, Map<String, String>> getNameSeqMap(@Param("flag") Integer flag);

}