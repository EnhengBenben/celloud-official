package com.celloud.message.alimail;

import java.util.Map;

import org.apache.commons.lang.text.StrSubstitutor;

import com.celloud.backstage.model.EmailTemplate;

/**
 * 阿里云邮件构造方法
 * <p>
 * AliEmail.template(EmailType.*) .substitutionVars(Substitution.sub().set(EmailParams.*.*.name(), "A value").set(EmailParams.*.*.name(), "B value"))
 * 
 * @author lin
 * @date 2016年7月7日 下午2:58:46
 */
public class AliEmail {
	public static Map<String, EmailTemplate> templates;
	private EmailTemplate template;
	private AliSubstitution.Sub sub;

	public AliEmail(EmailTemplate template) {
		this.template = template;
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
		return new AliEmail(templates.get(method));
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
		this.sub = sub;
		return this;
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
		return ss.replace(this.template.getContext());
	}

	public EmailTemplate getTemplate() {
		return template;
	}
}
