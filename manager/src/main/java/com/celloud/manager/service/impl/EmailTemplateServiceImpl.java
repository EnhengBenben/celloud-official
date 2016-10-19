package com.celloud.manager.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.manager.constants.DataState;
import com.celloud.manager.mapper.EmailTemplateMapper;
import com.celloud.manager.model.EmailTemplate;
import com.celloud.manager.page.Page;
import com.celloud.manager.page.PageList;
import com.celloud.manager.service.EmailTemplateService;



@Service("emailTemplateService")
public class EmailTemplateServiceImpl implements EmailTemplateService {

	@Resource
	private EmailTemplateMapper emailTemplateMapper;

    @Override
    public PageList<EmailTemplate> getEmailTemplates(Page page) {
        List<EmailTemplate> list = emailTemplateMapper.getEmailTemplates(page,
                DataState.ACTIVE);
        return new PageList<>(page, list);
    }

    @Override
    public EmailTemplate findById(Integer id) {
        return emailTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer addEmailTemplate(EmailTemplate emailTemplate) {
        emailTemplate.setCreateDate(new Date());
        emailTemplate.setState(DataState.ACTIVE);
        return emailTemplateMapper.insertSelective(emailTemplate);
    }

    @Override
    public Integer updateEmailTemplate(EmailTemplate emailTemplate) {
        return emailTemplateMapper.updateByPrimaryKeySelective(emailTemplate);
    }

    @Override
    public EmailTemplate getTemplateByMethod(String method, Integer id) {
        return emailTemplateMapper.getTemplateByMethod(method, id,
                DataState.ACTIVE);
    }

    @Override
    public List<EmailTemplate> getAll() {
        return emailTemplateMapper.getAll(DataState.ACTIVE);
    }

    @Override
    public EmailTemplate getTemplate(String method) {
        return emailTemplateMapper.getTemplate(method, DataState.ACTIVE);
    }

    @Override
    public int deleteTemplate(Integer id) {
        return emailTemplateMapper.deleteByPrimaryKey(id, DataState.DEELTED);
    }
}
