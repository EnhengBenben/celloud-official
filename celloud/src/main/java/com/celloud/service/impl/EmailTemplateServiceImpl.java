package com.celloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.DataState;
import com.celloud.mapper.EmailTemplateMapper;
import com.celloud.model.mysql.EmailTemplate;
import com.celloud.service.EmailTemplateService;

@Service("emailTemplateService")
public class EmailTemplateServiceImpl implements EmailTemplateService {

	@Resource
	private EmailTemplateMapper emailTemplateMapper;

	@Override
	public List<EmailTemplate> getAll() {
		return emailTemplateMapper.getAll(DataState.ACTIVE);
	}

	@Override
    public EmailTemplate getTemplate(String method) {
		return emailTemplateMapper.getTemplate(method, DataState.ACTIVE);
	}

}
