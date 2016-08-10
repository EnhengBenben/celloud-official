package com.celloud.manager.service;

import java.util.List;

import com.celloud.manager.model.EmailTemplate;
import com.celloud.manager.page.Page;
import com.celloud.manager.page.PageList;

public interface EmailTemplateService {

    /**
     * 获取所有的邮件模板
     * 
     * @return
     * @author leamo
     * @date 2016年7月21日 上午11:25:32
     */
    PageList<EmailTemplate> getEmailTemplates(Page page);

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
    EmailTemplate getTemplateByMethod(String method, Integer id);
    
    /**
     * 获取所有的邮件模板
     * 
     * @return
     * @author lin
     * @date 2016年7月7日下午3:23:22
     */
    List<EmailTemplate> getAll();

    /**
     * 根据调用方法获取模板
     * 
     * @param method
     * @return
     * @author lin
     * @date 2016年7月7日下午3:45:22
     */
    EmailTemplate getTemplate(String method);

    /**
     * 删除模板
     * 
     * @param id
     * @return
     * @author leamo
     * @date 2016年7月22日 上午10:16:52
     */
    int deleteTemplate(Integer id);
}