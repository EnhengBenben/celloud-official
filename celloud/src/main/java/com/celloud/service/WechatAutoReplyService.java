package com.celloud.service;

import com.celloud.model.mysql.WechatAutoReply;

public interface WechatAutoReplyService {

	WechatAutoReply selectByKeywords(String keywords);

}
