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
     * 根据商品编号和类型获取价格信息
     * 
     * @param itemId
     * @param itemType
     * @return
     * @author leamo
     * @date 2016年3月4日 下午3:34:57
     */
    Price selectByItemId(@Param("itemId") Integer itemId,
            @Param("itemType") Byte itemType);
}