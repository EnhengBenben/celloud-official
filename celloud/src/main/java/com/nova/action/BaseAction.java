package com.nova.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements SessionAware,
		ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 1L;
	protected Map<String, Object> session;
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	@Override
	public void addActionError(String anErrorMessage) {
		if (anErrorMessage.startsWith("the request was rejected because its size")) {  
			return;
//            //这些只是将原信息中的文件大小提取出来。  
//		  Matcher m = Pattern.compile("\\d+").matcher(anErrorMessage);  
//		  String s1 = "";  
//		  if (m.find())   s1 = m.group();  
//		  String s2 = "";  
//		  if (m.find())   s2 = m.group();  
//		  super.addActionError("你上传的文件（" + s1 + "）超过允许的大小（" + s2 + "）");  
		} else {//不是则不管它  
		  super.addActionError(anErrorMessage);  
		}  
	}
}
