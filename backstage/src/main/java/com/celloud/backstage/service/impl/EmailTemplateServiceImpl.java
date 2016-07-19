package com.celloud.backstage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.backstage.constants.DataState;
import com.celloud.backstage.mapper.EmailTemplateMapper;
import com.celloud.backstage.model.EmailTemplate;
import com.celloud.backstage.service.EmailTemplateService;

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
