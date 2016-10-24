package com.celloud.wechat;

import java.util.Date;
import java.util.Map;

import com.celloud.utils.XmlUtil;
import com.celloud.wechat.constant.WechatMessage;

public class MessageUtils {
	public static String getReply(Map<String, String> map, String replyContext) {
		String from = map.get(WechatMessage.text.FromUserName);//openId
		String to = map.get(WechatMessage.text.ToUserName);//开发者
		map.put(WechatMessage.text.FromUserName, to);
		map.put(WechatMessage.text.ToUserName, from);
		map.put(WechatMessage.text.CreateTime, String.valueOf(new Date().getTime()));
		map.put(WechatMessage.text.Content, replyContext);
		return XmlUtil.mapToXML(map, "xml").asXML();
	}
}
