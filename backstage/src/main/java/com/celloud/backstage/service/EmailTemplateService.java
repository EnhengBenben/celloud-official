package com.celloud.backstage.service;

import com.celloud.backstage.model.EmailTemplate;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;

public interface EmailTemplateService {

    /**
     * 获取所有的邮件模板
     * 
     * @return
     * @author leamo
     * @date 2016年7月21日 上午11:25:32
     */
    PageList<EmailTemplate> getAll(Page page);

    /**
     * 根据id获取模板信息
     * 
     * @param id
     * @return
     * @author leamo
     * @date 2016年7月21日 下午1:16:23
     */
    EmailTemplate findById(Integer id);

    /**
     * 新增模板
     * 
     * @param emailTemplate
     * @return
     * @author leamo
     * @date 2016年7月21日 下午2:15:32
     */
    Integer addEmailTemplate(EmailTemplate emailTemplate);

    /**
     * 修改模板
     * 
     * @param emailTemplate
     * @return
     * @author leamo
     * @date 2016年7月21日 下午2:15:32
     */
    Integer updateEmailTemplate(EmailTemplate emailTemplate);

    /**
     * 根据调用方法获取模板
     * 
     * @param method
     * @return
     * @author leamo
     * @date 2016年7月21日 下午2:15:32
     */
    EmailTemplate getTemplate(String method, Integer id);
}