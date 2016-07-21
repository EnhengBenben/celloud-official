package com.celloud.backstage.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.backstage.constants.DataState;
import com.celloud.backstage.mapper.EmailTemplateMapper;
import com.celloud.backstage.model.EmailTemplate;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.EmailTemplateService;


@Service("emailTemplateService")
public class EmailTemplateServiceImpl implements EmailTemplateService {

	@Resource
	private EmailTemplateMapper emailTemplateMapper;

    @Override
    public PageList<EmailTemplate> getAll(Page page) {
        List<EmailTemplate> list = emailTemplateMapper.getAll(page,
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
    public EmailTemplate getTemplate(String method, Integer id) {
        return emailTemplateMapper.getTemplate(method, id, DataState.ACTIVE);
    }
}
