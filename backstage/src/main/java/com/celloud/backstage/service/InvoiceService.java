package com.celloud.backstage.service;

import java.util.Map;

import com.celloud.backstage.model.Invoice;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;

/**
 * @author MQ
 * @date 2016年6月29日下午6:03:29
 * @description 发票服务接口
 *
 */
public interface InvoiceService {

    /**
     * @author MQ
     * @date 2016年6月30日下午1:25:49
     * @description 分页查询发票
     * @return
     *
     */
    public PageList<Map<String, String>> getInvoiceListByKeyword(Page page, Integer userId, String keyword);

    /**
     * @author MQ
     * @date 2016年6月30日下午3:30:20
     * @description 根据id查询发票信息
     * @param id
     *
     */
    public Map<String, String> getInvoiceDetail(Integer id);

    /**
     * @author MQ
     * @date 2016年7月3日下午10:59:38
     * @description 邮寄发票
     * @return
     *
     */
    public int postInvoice(Invoice invoice, String postCompany, String postNumber, String email);
}
