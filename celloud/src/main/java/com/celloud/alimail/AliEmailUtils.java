package com.celloud.alimail;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.celloud.utils.CustomStringUtils;

public class AliEmailUtils {
	private String apiUser;
	private String apiKey;
	private String username;
	private String emailName;

	/**
	 * 模板发送
	 * 
	 * @param aliEmail
	 * @param to
	 * @return
	 * @author lin
	 * @date 2016年7月7日下午3:58:46
	 */
	public String simpleSend(AliEmail aliEmail, String... to) {
		return simpleSend(aliEmail.getTemplate().getTitle(), aliEmail.getContext(), to);
	}

	/**
	 * 阿里云邮件简单发送方法
	 * 
	 * @param title：标题
	 * @param context：正文
	 * @param to：收件人
	 * @return
	 * @author lin
	 * @date 2016年7月7日下午3:04:07
	 */
	public String simpleSend(String title, String context, String... to) {
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", apiUser, apiKey);
		IAcsClient client = new DefaultAcsClient(profile);
		SingleSendMailRequest request = new SingleSendMailRequest();
		String result = null;
		try {
			request.setAccountName(username);
			request.setFromAlias(emailName);
			request.setAddressType(1);
			request.setReplyToAddress(true);
			request.setToAddress(CustomStringUtils.join(to, ","));
			request.setSubject(title);
			request.setHtmlBody(context);
			SingleSendMailResponse httpResponse = client.getAcsResponse(request);
			result = httpResponse.toString();
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void setApiUser(String apiUser) {
		this.apiUser = apiUser;
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
