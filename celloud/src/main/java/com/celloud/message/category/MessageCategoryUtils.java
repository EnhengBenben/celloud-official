package com.celloud.message.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.celloud.alimail.AliEmail;
import com.celloud.alimail.AliEmailUtils;
import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.message.MessageUtils;
import com.celloud.model.mysql.MessageCategory;
import com.celloud.model.mysql.User;
import com.celloud.service.MessageCategoryService;
import com.celloud.service.UserService;
import com.celloud.wechat.ParamFormat;
import com.celloud.wechat.ParamFormat.Param;
import com.celloud.wechat.WechatUtils;
import com.celloud.wechat.constant.WechatType;

@Service
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
	 * @param code:MessageCategoryCode.
	 * @param params
	 * @author lin
	 * @date 2016年7月13日下午4:56:18
	 */
	public void sendMessage(String code, AliEmail email, Param wechat, MessageUtils window, String wechatUrl) {
		Session session = ConstantsData.getShioSession();
		Object obj = session.getAttribute(Constants.MESSAGE_CATEGORY);
		@SuppressWarnings("unchecked")
		Map<String, MessageCategory> map = obj == null ? null : (Map<String, MessageCategory>) obj;
		if (map == null) {
			map = initSetting(ConstantsData.getLoginUserId());
			session.setAttribute(Constants.MESSAGE_CATEGORY, map);
		}
		if (map.get(code).getEmail().equals(MessageCategoryState.SEND) && email != null) {
			//暂时不需要邮件提醒
		}
		if (map.get(code).getWechat().equals(MessageCategoryState.SEND) && wechat != null) {
			Object wechatOpenId = session.getAttribute(Constants.SESSION_WECHAT_OPENID);
			String openId = wechatOpenId == null ? null : wechatOpenId.toString();
			if (StringUtils.isNotEmpty(openId)) {
				if (MessageCategoryCode.LOGIN.equals(code)) {
					wechatUtils.pushMessage(ParamFormat.paramAll().template(WechatType.LOGIN).openId(openId)
							.url(wechatUrl).data(wechat).get());
				} else if (MessageCategoryCode.UPDATEPWD.equals(code)) {
					wechatUtils.pushMessage(ParamFormat.paramAll().template(WechatType.PWD_UPDATE).openId(openId)
							.url(wechatUrl).data(wechat).get());
				}
			}
		}
		if (map.get(code).getWindow().equals(MessageCategoryState.SEND) && window != null) {
			//暂时不需要桌面提醒
		}

	}

	/**
	 * 
	 * 
	 * @param userId
	 * @param code
	 * @param email
	 * @param wechat
	 * @param window
	 * @author lin
	 * @date 2016年7月18日上午10:10:06
	 */
	@Async
	public void sendMessage(Integer userId, String code, AliEmail email, Param wechat, MessageUtils window,
			String wechatUrl) {
		Map<String, MessageCategory> map = initSetting(userId);
		User user = userService.selectUserByIdNotIcon(userId);
		if (map.get(code).getEmail().equals(MessageCategoryState.SEND) && email != null) {
			String to = user.getEmail();
			emailUtils.simpleSend(email, to);
		}
		if (map.get(code).getWechat().equals(MessageCategoryState.SEND) && wechat != null) {
			String openId = userService.getOpenIdByUser(userId);
			if (StringUtils.isNotEmpty(openId)) {
				if (MessageCategoryCode.REPORT.equals(code)) {
					wechatUtils.pushMessage(ParamFormat.paramAll().template(WechatType.RUN_OVER).openId(openId)
							.url(wechatUrl).data(wechat).get());
				} else if (MessageCategoryCode.LOGIN.equals(code)) {
					wechatUtils
							.pushMessage(ParamFormat.paramAll().template(WechatType.LOGIN).openId(openId).url(wechatUrl)
							.data(wechat).get());
				} else if (MessageCategoryCode.UPDATEPWD.equals(code)) {
					wechatUtils.pushMessage(ParamFormat.paramAll().template(WechatType.PWD_UPDATE).openId(openId)
							.url(wechatUrl).data(wechat).get());
				} else if (MessageCategoryCode.SHARE.equals(code)) {
					//TODO need template
					System.out.println("共享报告微信提醒还需要做");
					//wechatUtils.pushMessage(ParamFormat.paramAll().template(WechatType.SHARE).openId(openId).url(wechatUrl)
					//	.data(wechat).get());
				} else if (MessageCategoryCode.BALANCES.equals(code)) {
					wechatUtils.pushMessage(ParamFormat.paramAll().template(WechatType.BALANCE_CHANGE).openId(openId)
							.url(wechatUrl).data(wechat).get());
				}
			}
		}
		if (map.get(code).getWindow().equals(MessageCategoryState.SEND) && window != null) {
			window.to(user.getUsername());
		}

	}

}
