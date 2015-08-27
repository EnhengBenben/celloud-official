package com.nova.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.nova.action.BaseAction;
import com.nova.sdo.Behavior;

import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;

public class BehaviorUtil extends BaseAction {
    private static final long serialVersionUID = 1L;

    public static Behavior getBehavior(HttpServletRequest request) {
	Behavior behavior = null;
	InputStream is = BehaviorUtil.class.getClassLoader()
		.getResourceAsStream("ua.ini");
	UASparser p;
	try {
	    p = new UASparser(is);
	    String userAgent = request.getHeader("user-agent");
	    UserAgentInfo uai = p.parse(userAgent);
	    String browser = uai.getUaFamily();
	    String os = uai.getOsFamily();
	    String ip = getIp(request);
	    behavior = new Behavior();
	    behavior.setIp(ip);
	    behavior.setBrowser(browser);
	    behavior.setOs(os);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return behavior;
    }

    private static String getIp(HttpServletRequest request) {
	String ip = request.getHeader("x-forwarded-for");
	if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
	    ip = request.getHeader("Proxy-Client-IP");
	}
	if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
	    ip = request.getHeader("WL-Proxy-Client-IP");
	}
	if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
	    ip = request.getHeader("HTTP_CLIENT_IP");
	}
	if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
	    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	}
	if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
	    ip = request.getRemoteAddr();
	}
	return ip;
    }
}
