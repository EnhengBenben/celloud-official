package com.celloud.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.WechatAutoReplyMapper;
import com.celloud.model.mysql.WechatAutoReply;
import com.celloud.service.WechatAutoReplyService;

@Service("wechatAutoReplyService")
public class WechatAutoReplyServiceImpl implements WechatAutoReplyService {

	@Resource
	WechatAutoReplyMapper autoReply;

	@Override
	public WechatAutoReply selectByKeywords(String keywords) {
		return autoReply.selectByKeywords(keywords);
	}

}
