package com.celloud.mapper;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.Price;

public interface PriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Price record);

    int insertSelective(Price record);

    Price selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Price record);

    int updateByPrimaryKey(Price record);

    /**
     * 根据联合外键获取价格信息
     * 
     * @param itemId
     * @param flag
     * @return
     * @author leamo
     * @date 2016年2月23日 下午7:14:22
     */
    Price selectByItemId(@Param("itemId") Integer itemId,
            @Param("flag") Byte flag);
}