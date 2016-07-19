package com.celloud.backstage.service;

import java.util.List;

import com.celloud.backstage.model.EmailTemplate;

public interface EmailTemplateService {

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
}