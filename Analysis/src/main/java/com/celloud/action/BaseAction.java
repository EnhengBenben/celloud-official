package com.celloud.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(BaseAction.class);
	protected Map<String, Object> session;
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	public Object getCid() {
		Object cid = session.get("companyId");
		if (cid == null) {
			log.error("后台session超时或者非法访问");
		}
		return cid;
	}

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
		} else {// 不是则不管它
			super.addActionError(anErrorMessage);
		}
	}
}
