package com.celloud.wechat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.celloud.wechat.constant.WechatConstants;

public class WechatToken {
	private static Map<String, String> wechatToken = new HashMap<>();

	public static String getToken() {
		String time = wechatToken.get(WechatConstants.EXPIREDATE);
		if (time == null)
			return null;
		if (new Date().getTime() < Long.valueOf(time)) {
			WechatToken.refreshToken();
			return wechatToken.get(WechatConstants.TOKEN);
		}
		return null;
	}

	public static void setToken(String token) {
		wechatToken.put(WechatConstants.TOKEN, token);
		refreshToken();
	}

	private static void refreshToken() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, WechatConstants.VALIDITY);
		wechatToken.put(WechatConstants.EXPIREDATE, "" + calendar.getTime().getTime());
	}
}
