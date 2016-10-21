package com.celloud.mapper;

import com.celloud.model.mysql.WechatAutoReply;

public interface WechatAutoReplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WechatAutoReply record);

    int insertSelective(WechatAutoReply record);

    WechatAutoReply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WechatAutoReply record);

    int updateByPrimaryKeyWithBLOBs(WechatAutoReply record);

    int updateByPrimaryKey(WechatAutoReply record);

	/**
	 * 关键字检索
	 * 
	 * @param keywords
	 * @return
	 * @author lin
	 * @date 2016年10月21日下午1:31:37
	 */
	WechatAutoReply selectByKeywords(String keywords);
}