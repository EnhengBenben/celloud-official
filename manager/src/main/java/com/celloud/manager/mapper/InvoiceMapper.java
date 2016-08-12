package com.celloud.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.manager.model.Invoice;
import com.celloud.manager.page.Page;

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
    List<Invoice> pageQuery(Page page, @Param("userId") Integer userId);

    /**
     * @author MQ
     * @date 2016年6月30日下午3:25:49
     * @description 根据id查找invoice
     * @param id
     *
     */
    Invoice findById(Integer id);
}