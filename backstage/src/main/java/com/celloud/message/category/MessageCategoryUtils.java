package com.celloud.message.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.celloud.backstage.model.MessageCategory;
import com.celloud.backstage.model.User;
import com.celloud.backstage.service.MessageCategoryService;
import com.celloud.backstage.service.UserService;
import com.celloud.message.alimail.AliEmail;
import com.celloud.message.alimail.AliEmailUtils;
import com.celloud.message.web.MessageUtils;
import com.celloud.message.wechat.ParamFormat;
import com.celloud.message.wechat.ParamFormat.Param;
import com.celloud.message.wechat.WechatType;
import com.celloud.message.wechat.WechatUtils;

@Service("messageCategoryUtils")
public class MessageCategoryUtils {
	@Resource
	private MessageCategoryService mcs;
	@Resource
	private WechatUtils wechatUtils;
	@Resource
	private UserService userService;
	@Resource
	private AliEmailUtils emailUtils;

	/**
	 * 登录时初始化用户消息设置
	 * 
	 * @param userId
	 * @return
	 * @author lin
	 * @date 2016年7月13日下午3:02:59
	 */
	public Map<String, MessageCategory> initSetting(Integer userId) {
		List<MessageCategory> userMessage = mcs.getBeanByUserId(userId);
		Map<String, MessageCategory> result = new HashMap<>();
		if (userMessage != null && userMessage.size() > 0) {
			for (MessageCategory mc : userMessage) {
				result.put(mc.getCode(), mc);
			}
		}
		return result;
	}

	/**
	 * 
	 * 
	 * @param code
	 * @param email
	 * @param wechat
	 * @param window
	 * @author lin
	 * @date 2016年7月18日上午10:10:06
	 */
	@Async
	public void sendMessage(String code, AliEmail email, Param wechat, MessageUtils window) {
		List<User> list = userService.getAllUserList();
		for (User user : list) {
			Map<String, MessageCategory> map = initSetting(user.getUserId());
			if (map.get(code).getEmail().equals(MessageCategoryState.SEND) && email != null) {
				String to = user.getEmail();
				emailUtils.simpleSend(email, to);
			}
			if (map.get(code).getWechat().equals(MessageCategoryState.SEND) && wechat != null) {
				String openId = userService.getOpenIdByUser(user.getUserId());
				if (StringUtils.isNotEmpty(openId)) {
					if (MessageCategoryCode.REPORT.equals(code)) {
						wechatUtils.pushMessage(ParamFormat.paramAll().template(WechatType.RUN_OVER).openId(openId)
								.url(null).data(wechat).get());
					} else if (MessageCategoryCode.LOGIN.equals(code)) {
						wechatUtils.pushMessage(ParamFormat.paramAll().template(WechatType.LOGIN).openId(openId)
								.url(null).data(wechat).get());
					} else if (MessageCategoryCode.UPDATEPWD.equals(code)) {
						wechatUtils.pushMessage(ParamFormat.paramAll().template(WechatType.PWD_UPDATE).openId(openId)
								.url(null).data(wechat).get());
					} else if (MessageCategoryCode.SHARE.equals(code)) {
						//TODO need template
						System.out.println("共享报告微信提醒还需要做");
						//wechatUtils.pushMessage(ParamFormat.paramAll().template(WechatType.SHARE).openId(openId).url(null)
						//	.data(wechat).get());
					} else if (MessageCategoryCode.BALANCES.equals(code)) {
						wechatUtils.pushMessage(ParamFormat.paramAll().template(WechatType.BALANCE_CHANGE)
								.openId(openId).url(null).data(wechat).get());
					}
				}
			}
			if (map.get(code).getWindow().equals(MessageCategoryState.SEND) && window != null) {
				window.to(user.getUsername());
			}
		}
	}
}
