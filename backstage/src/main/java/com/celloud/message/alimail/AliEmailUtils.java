package com.celloud.message.alimail;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.celloud.backstage.utils.CustomStringUtils;
import com.celloud.backstage.utils.UserAgentUtil;

public class AliEmailUtils {
	private static Logger logger = LoggerFactory.getLogger(AliEmailUtils.class);
	private String apiUser;
	private String apiKey;
	private String username;
	private String emailName;
	private String errorsMailTo;
	private String errorTitle;

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

	/**
	 * 将错误日志组织成邮件正文并发送
	 * 
	 * @param request
	 * @param exception
	 */
	public void sendError(HttpServletRequest request, Exception exception) {
		if (errorsMailTo == null) {
			logger.warn("系统出现异常，正在发送异常信息邮件，但是没有找到邮件接收者！");
			return;
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		StringBuffer buffer = new StringBuffer("");
		buffer.append("<h3>异常地址：</h3>");
		buffer.append(request.getRequestURL());
		if (request.getQueryString() != null) {
			buffer.append("?" + request.getQueryString());
		}
		buffer.append("<h3>用户信息：</h3>");
		buffer.append(UserAgentUtil.getActionLog(request).toResume());
		buffer.append("<h3>异常信息：</h3>");
		buffer.append(exception.toString());
		buffer.append("<h3>异常描述：</h3>");
		buffer.append("<pre>");
		buffer.append(sw.toString());
		buffer.append("</pre>");
		String content = buffer.toString();
		pw.close();
		simpleSend(errorTitle, content, errorsMailTo);
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

	public String getErrorsMailTo() {
		return errorsMailTo;
	}

	public void setErrorsMailTo(String errorsMailTo) {
		this.errorsMailTo = errorsMailTo;
	}

	public String getErrorTitle() {
		return errorTitle;
	}

	public void setErrorTitle(String errorTitle) {
		this.errorTitle = errorTitle;
	}

}
