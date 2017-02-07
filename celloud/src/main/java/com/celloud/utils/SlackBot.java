package com.celloud.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.constants.ConstantsData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class SlackBot {
	private static Logger logger = LoggerFactory.getLogger(SlackBot.class);
	private static String fallback = "[CelLoud]异常报告";
	private static String pretext = "[CelLoud]异常报告";
	private static String color = "danger";
	private static boolean isClose = true;
	private static String webhook;
	static {
		Properties prop = ConstantsData.loadProperties("slack.properties");
		fallback = prop.getProperty("fallback") != null ? prop.getProperty("fallback") : fallback;
		pretext = prop.getProperty("pretext") != null ? prop.getProperty("pretext") : fallback;
		color = prop.getProperty("color") != null ? prop.getProperty("color") : fallback;
		webhook = prop.getProperty("webhook");
		String close = prop.getProperty("close");
		isClose = close != null && close.toLowerCase().equals("true");
	}

	public static void error(HttpServletRequest request, Exception exception) {
		if (isClose) {
			logger.debug("Slack notice is closed!");
			return;
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		Attachment attach = new Attachment();
		attach.setFallback(fallback);
		attach.setPretext(pretext);
		attach.setColor(color);
		List<AttachField> fields = new ArrayList<>();
		String url = request.getMethod() + "   " + request.getRequestURL();
		if (request.getQueryString() != null) {
			url = url + "?" + request.getQueryString();
		}
		fields.add(new AttachField("异常地址", url));
		com.celloud.model.mysql.ActionLog log = UserAgentUtil.getActionLog(request);
		String userInfo = "user:  "
				+ (log.getUserId() == null ? "无登录用户" : (log.getUserId() + " , " + log.getUsername()));
		userInfo = userInfo + "\nenviron:  " + log.getOs() + " " + log.getOsVersion() + " , " + log.getBrowser() + " "
				+ log.getBrowserVersion();
		userInfo = userInfo + "\nlocal:  " + log.getIp() + " , " + log.getAddress();
		fields.add(new AttachField("用户信息", userInfo));
		fields.add(new AttachField("异常信息", exception.toString()));
		fields.add(new AttachField("异常描述", sw.toString(), true));
		attach.setFields(fields);
		sw = new StringWriter();
		try {
			new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE).writeValue(sw,
					new AttachPayload(attach));
		} catch (IOException e) {
			logger.error("转换slack的payload成json格式失败！", e);
		}
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("payload", sw.toString()));
		HttpURLUtils.httpPostRequest(webhook, params);
	}

	static class Payload {
		private String text;
		private String channel;
		private String username;
		private String icon_url;
		private String icon_emoji;

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getChannel() {
			return channel;
		}

		public void setChannel(String channel) {
			this.channel = channel;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getIcon_url() {
			return icon_url;
		}

		public void setIcon_url(String icon_url) {
			this.icon_url = icon_url;
		}

		public String getIcon_emoji() {
			return icon_emoji;
		}

		public void setIcon_emoji(String icon_emoji) {
			this.icon_emoji = icon_emoji;
		}

	}

	static class AttachPayload {
		private List<Attachment> attachments;

		public AttachPayload() {
		}

		public AttachPayload(List<Attachment> attachments) {
			super();
			this.attachments = attachments;
		}

		public AttachPayload(Attachment attachment) {
			super();
			this.attachments = new ArrayList<>();
			this.attachments.add(attachment);
		}

		public List<Attachment> getAttachments() {
			return attachments;
		}

		public void setAttachments(List<Attachment> attachments) {
			this.attachments = attachments;
		}

	}

	static class Attachment {
		private String fallback;
		private String text;
		private String pretext;
		private String color = "#36a64f";
		private List<AttachField> fields;

		public String getFallback() {
			return fallback;
		}

		public void setFallback(String fallback) {
			this.fallback = fallback;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getPretext() {
			return pretext;
		}

		public void setPretext(String pretext) {
			this.pretext = pretext;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public List<AttachField> getFields() {
			return fields;
		}

		public void setFields(List<AttachField> fields) {
			this.fields = fields;
		}

	}

	static class AttachField {
		private String title;
		private String value;
		private boolean shorted;

		public AttachField() {
		}

		public AttachField(String title, String value) {
			super();
			this.title = title;
			this.value = value;
		}

		public AttachField(String title, String value, boolean shorted) {
			super();
			this.title = title;
			this.value = value;
			this.shorted = shorted;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public boolean isShort() {
			return shorted;
		}

		public void setShort(boolean isShort) {
			this.shorted = isShort;
		}

	}
}
