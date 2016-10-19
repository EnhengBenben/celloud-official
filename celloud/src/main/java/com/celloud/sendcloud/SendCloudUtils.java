package com.celloud.sendcloud;

import com.celloud.sendcloud.mail.Email;
import com.celloud.sendcloud.mail.Result;

public class SendCloudUtils {
	private String apiUser;
	private String apiKey;
	private String username;
	private String emailName;

	/**
	 * SendCloud模板发送
	 * 
	 * @param email:
	 *            <p>
	 *            Email.template("user_register")
	 *            <p>
	 *            .substitutionVars(Substitution.sub().set("url", "http://www.iplaybest.com"))
	 *            <p>
	 *            .attachment(new File("path"))
	 *            <p>
	 *            .to("576681692@qq.com");
	 *            <p>
	 * @return
	 * @author lin
	 * @date 2016年6月21日下午2:49:45
	 */
	public Result sendTemplate(Email<?> email) {
		SendCloud webapi = SendCloud.createWebApi(apiUser, apiKey);
		email.from(username).fromName(emailName);
		return webapi.mail().send(email);
	}

	/**
	 * SendCloud普通发送
	 * <p>
	 * 不和模板匹配的内容通常会被拦截，不建议使用
	 * 
	 * @param email:
	 *            <p>
	 *            Email.general()
	 *            <p>
	 *            .html(context)
	 *            <p>
	 *            .subject("mail title")
	 *            <p>
	 *            .attachment(new File("path"))
	 *            <p>
	 *            .to("576681692@qq.com");
	 *            <p>
	 * @return
	 * @author lin
	 * @date 2016年6月21日下午3:40:30
	 */
	@Deprecated
	public Result sendNormal(Email<?> email) {
		SendCloud webapi = SendCloud.createWebApi(apiUser, apiKey);
		email.from(username).fromName(emailName);
		return webapi.mail().send(email);
	}

	public String getApiUser() {
		return apiUser;
	}

	public void setApiUser(String apiUser) {
		this.apiUser = apiUser;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmailName() {
		return emailName;
	}

	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}

}
