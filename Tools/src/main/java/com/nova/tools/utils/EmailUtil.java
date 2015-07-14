package com.nova.tools.utils;

import com.nova.email.EmailService;
import com.nova.tools.constant.AppNameIDConstant;

public class EmailUtil {
	/**
	 * @param name
	 *            :projectName 或者 dataName
	 * @param appId
	 * @param start
	 * @param email
	 * @param param
	 * @param isProject
	 *            ：true 是项目；false 是数据
	 */
	public static void sendEndEmail(String name, String appId, String start,
			String email, String param, boolean isProject) {
		String rmsg = null;
		if (isProject) {
			rmsg = TemplateUtils.readTemplate(3);
			rmsg = rmsg.replaceAll("#projectName", name);
		} else {
			rmsg = TemplateUtils.readTemplate(4);
			rmsg = rmsg.replaceAll("#dataName", name);
		}
		rmsg = rmsg.replaceAll("#app", AppNameIDConstant.map.get(appId));
		rmsg = rmsg.replaceAll("#start", start);
		rmsg = rmsg.replaceAll("#end", DateUtil.formatNowDate());
		rmsg = rmsg.replaceAll("#param", Encrypt.encrypt(param));
		EmailService.send(email, rmsg, true);
	}
}
