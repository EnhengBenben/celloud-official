package com.nova.email;

import com.nova.email.EmailService;
import com.nova.constants.AppNameIDConstant;
import com.nova.utils.DateUtil;
import com.nova.utils.Encrypt;
import com.nova.utils.TemplateUtil;

public class EmailProjectEnd {
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
			rmsg = TemplateUtil.readTemplate(3);
			rmsg = rmsg.replaceAll("#projectName", name);
		} else {
			rmsg = TemplateUtil.readTemplate(4);
			rmsg = rmsg.replaceAll("#dataName", name);
		}
		rmsg = rmsg.replaceAll("#app", AppNameIDConstant.map.get(appId));
		rmsg = rmsg.replaceAll("#start", start);
		rmsg = rmsg.replaceAll("#end", DateUtil.nowCarefulTimeToString());
		rmsg = rmsg.replaceAll("#param", Encrypt.encrypt(param));
		EmailService.send(email, rmsg, true);
	}
}
