package com.celloud.alimail;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.stereotype.Component;

import com.celloud.model.mysql.EmailTemplate;
import com.celloud.service.EmailTemplateService;

/**
 * 阿里云邮件构造方法
 * <p>
 * AliEmail.template(EmailType.*) .substitutionVars(Substitution.sub().set(EmailParams.*.*.name(), "A value").set(EmailParams.*.*.name(), "B value"))
 * 
 * @author lin
 * @date 2016年7月7日 下午2:58:46
 */
@Component
public class AliEmail {
	@Resource
	private EmailTemplateService templateService;
	private static AliEmail aliEmail;
	private EmailTemplate template;
	private AliSubstitution.Sub sub;

	@PostConstruct
	public void init() {
		AliEmail.setAliEmail(this);
		aliEmail.templateService = templateService;
	}

	/**
	 * 基础邮件模板
	 * 
	 * @param method：模板方法名
	 * @return
	 * @author lin
	 * @date 2016年7月7日下午3:01:33
	 */
	public static AliEmail template(String method) {
		EmailTemplate template = aliEmail.templateService.getTemplate(method);
		aliEmail.setTemplate(template);
		return aliEmail;
	}

	/**
	 * 需要替换的参数：key - value
	 * 
	 * @param sub
	 * @return
	 * @author lin
	 * @date 2016年7月7日下午3:01:52
	 */
	public AliEmail substitutionVars(AliSubstitution.Sub sub) {
		aliEmail.sub = sub;
		return aliEmail;
	}

	/**
	 * 返回替换完毕的邮件内容
	 * 
	 * @return
	 * @author lin
	 * @date 2016年7月7日下午3:02:21
	 */
	public String getContext() {
		StrSubstitutor ss = new StrSubstitutor(sub.get());
		return ss.replace(aliEmail.template.getContext());
	}

	public EmailTemplate getTemplate() {
		return template;
	}

	public void setTemplate(EmailTemplate template) {
		aliEmail.template = template;
	}

	public static void setAliEmail(AliEmail aliEmail) {
		AliEmail.aliEmail = aliEmail;
	}
}
