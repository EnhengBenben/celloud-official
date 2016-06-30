package com.celloud.service;

import com.celloud.model.mysql.Invoice;

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
    public int applyInvoice(Invoice invoice, String[] ids);
}
