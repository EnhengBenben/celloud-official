package com.celloud.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.celloud.alimail.AliEmail;
import com.celloud.model.mysql.EmailTemplate;
import com.celloud.service.EmailTemplateService;

@Service
public class EmailTemplateListener implements ApplicationListener<ContextRefreshedEvent> {
	Logger logger = LoggerFactory.getLogger(EmailTemplateListener.class);
	@Resource
	private EmailTemplateService ets;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			List<EmailTemplate> list = ets.getAll();
			Map<String, EmailTemplate> templates = new HashMap<>();
			for (EmailTemplate emailTemplate : list) {
				templates.put(emailTemplate.getMethod(), emailTemplate);
			}
			AliEmail.templates = templates;
			logger.info("邮件模板初始化完毕");
		}
	}
}
