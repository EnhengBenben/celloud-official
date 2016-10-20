package com.celloud.service;

import com.celloud.model.mysql.Invoice;
import com.celloud.page.Page;
import com.celloud.page.PageList;

/**
 * @author MQ
 * @date 2016年6月29日下午6:03:29
 * @description 发票服务接口
 *
 */
public interface InvoiceService {

    /**
     * @author MQ
     * @date 2016年6月29日下午6:07:10
     * @description 申请发票
     * @param invoice
     * 
     */
    public int applyInvoice(Integer userId, String username, Invoice invoice, Integer[] ids);

    /**
     * @author MQ
     * @date 2016年6月30日下午1:25:49
     * @description 分页查询发票
     * @return
     *
     */
    public PageList<Invoice> getInvoiceList(Page page, Integer userId);

    /**
     * @author MQ
     * @date 2016年6月30日下午3:30:20
     * @description 根据id查询发票信息
     * @param id
     *
     */
    public Invoice getInvoiceDetail(Integer id);
}
