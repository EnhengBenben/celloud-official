package com.celloud.mail;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.constants.FeedbackConstants;
import com.celloud.model.mysql.Feedback;
import com.celloud.model.mysql.FeedbackAttachment;
import com.celloud.utils.HttpURLUtils;
import com.celloud.utils.UserAgentUtil;

/**
 * 邮箱工具类
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年1月21日 下午1:32:23
 */
public class EmailUtils {
    private static Logger logger = LoggerFactory.getLogger(EmailUtils.class);
	@Resource
	private EmailService emailService;
    private String errorMailTo;
    private String[] errorMailTos;
    private String errorTitle;
    private String feedbackMailTo;
    private String[] feedbackMailTos;
    private String feedbackTitle;
	private String apiUser;
	private String apiKey;
	private String url;
	private String username;
	private String emailName;

	/**
	 * 调用SendCloud模板发送邮件
	 * 
	 * @param templateName
	 * @param map:Map<email,TreeMap<param,value>>
	 * @return
	 * @author lin
	 * @date 2016年6月16日下午2:45:51
	 */
	public String sendWithTemplate(String templateName, Map<String, TreeMap<String, String>> map) {
		String to = paramTransfer(map);
		List<NameValuePair> param = getParams(templateName, to);
		return HttpURLUtils.httpPostRequest(url, param);
	}

	/**
	 * 封装发送所需属性
	 * 
	 * @param templateName
	 * @param to
	 * @return
	 * @author lin
	 * @date 2016年6月16日下午2:51:10
	 */
	private List<NameValuePair> getParams(String templateName, String to) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("api_user", apiUser));
		params.add(new BasicNameValuePair("api_key", apiKey));
		params.add(new BasicNameValuePair("substitution_vars", to));
		params.add(new BasicNameValuePair("template_invoke_name", templateName));
		params.add(new BasicNameValuePair("from", username));
		params.add(new BasicNameValuePair("fromname", emailName));
		params.add(new BasicNameValuePair("resp_email_id", "true"));
		return params;
	}

	/**
	 * 将参数封装成API要求的格式
	 * 
	 * @param map
	 * @return
	 * @author lin
	 * @date 2016年6月16日下午2:50:40
	 */
	private String paramTransfer(Map<String, TreeMap<String, String>> map) {
		Map<String, List<String>> sub = new HashMap<>();
		List<String> to = new ArrayList<>();
		for (Entry<String, TreeMap<String, String>> entry : map.entrySet()) {
			to.add(entry.getKey());
			for (Entry<String, String> attribute : entry.getValue().entrySet()) {
				List<String> name = sub.get(attribute.getKey());
				if (name == null) {
					name = new ArrayList<>();
				}
				name.add(attribute.getValue());
				sub.put(attribute.getKey(), name);
			}
		}
		Map<String, Object> result = new HashMap<>();
		result.put("to", to);
		result.put("sub", sub);
		JSONObject jsonResult = new JSONObject(result);
		return jsonResult.toString();
	}

    public void sendWithTitle(String title, String content, String... to) {
        Email email = new Email().addTo(to).setSubject(title).setContent(content);
		emailService.send(email);
    }

    public void send(String content, String... to) {
        Email email = new Email().addTo(to).setContent(content);
		emailService.send(email);
    }

    /**
     * 将错误日志组织成邮件正文并发送
     * 
     * @param request
     * @param exception
     */
    public void sendError(HttpServletRequest request, Exception exception) {
        if (this.errorMailTos == null) {
            logger.warn("系统出现异常，正在发送异常信息邮件，但是没有找到邮件接收者！");
            return;
        }
		String content = getError(request, exception);
        Email email = new Email().addTo(this.errorMailTos).setSubject(errorTitle).setContent(content);
		emailService.send(email);
    }

	/**
	 * 将错误日志组织成邮件正文
	 * 
	 * @param request
	 * @param exception
	 * @return
	 * @author lin
	 * @date 2016年6月21日下午3:48:25
	 */
	public String getError(HttpServletRequest request, Exception exception) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		StringBuffer buffer = new StringBuffer("");
		buffer.append("<h3>异常地址：</h3>");
		buffer.append(request.getMethod() + "   ");
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
		// TODO 使用volecity模板渲染邮件内容
		String content = buffer.toString();
		pw.close();
		return content;
	}

    /**
     * 将送问题反馈组织成邮件正文并发送
     * 
     * @param feedback
     */
    public void sendFeedback(Feedback feedback, List<FeedbackAttachment> attachments) {
        if (this.feedbackMailTos == null) {
            logger.warn("用户提交了工单，正在发送工单信息邮件，但是没有找到邮件接收者！");
            return;
        }
        Email email = new Email().addModel("feedback", feedback).addTo(this.feedbackMailTos).setSubject(feedbackTitle)
                .setTemplate("feedback.vm");
        if (attachments != null && attachments.size() > 0) {
            for (FeedbackAttachment fa : attachments) {
                String file = FeedbackConstants.getAttachment(fa.getFilePath());
                email.attach(file, null);
            }
        }
		emailService.send(email);
    }

    public String getErrorMailTo() {
        return errorMailTo;
    }

    public void setErrorMailTo(String errorMailTo) {
        this.errorMailTo = errorMailTo;
        this.errorMailTos = this.getMails(this.errorMailTo);
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public void setErrorTitle(String errorTitle) {
        this.errorTitle = errorTitle;
    }

    public String getFeedbackMailTo() {
        return feedbackMailTo;
    }

    public void setFeedbackMailTo(String feedbackMailTo) {
        this.feedbackMailTo = feedbackMailTo;
        this.feedbackMailTos = this.getMails(this.feedbackMailTo);
    }

    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    private String[] getMails(String mails) {
        String[] result = null;
        if (mails != null && !mails.trim().equals("")) {
            result = mails.split(",");
            List<String> list = new ArrayList<>(new HashSet<>(Arrays.asList(result)));
            result = list.toArray(new String[list.size()]);
        }
        return result;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
