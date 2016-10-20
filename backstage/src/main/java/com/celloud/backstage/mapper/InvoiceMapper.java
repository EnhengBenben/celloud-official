package com.celloud.backstage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.backstage.model.Invoice;
import com.celloud.backstage.page.Page;

public interface InvoiceMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Invoice record);

    int insertSelective(Invoice record);

    Invoice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Invoice record);

    int updateByPrimaryKey(Invoice record);

    /**
     * @author MQ
     * @date 2016年6月30日下午1:55:53
     * @description 插入一条记录并设置主键
     * @param invoice
     *
     */
    int insertAndSetId(Invoice invoice);

    /**
     * @author MQ
     * @date 2016年6月30日下午1:42:40
     * @description 分页查询方法
     * @param userId
     *
     */
    List<Map<String, String>> pageQuery(Page page, @Param("userId") Integer userId, @Param("keyword") String keyword);

    /**
     * @author MQ
     * @date 2016年6月30日下午3:25:49
     * @description 根据id查找invoice
     * @param id
     *
     */
    Map<String, String> findById(Integer id);
}