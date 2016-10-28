package com.celloud.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.manager.model.Price;

public interface PriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Price record);

    int insertSelective(Price record);

    Price selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Price record);

    int updateByItemId(Price record);

    int updateExpireDate(Price record);

    /**
     * 获取产品的历史价格列表
     * 
     * @param itemId
     * @param itemType
     * @return
     * @author leamo
     * @date 2016年3月21日 下午3:11:36
     */
    List<Price> getPriceList(@Param("itemId") Integer itemId,
            @Param("itemType") Byte itemType);
}