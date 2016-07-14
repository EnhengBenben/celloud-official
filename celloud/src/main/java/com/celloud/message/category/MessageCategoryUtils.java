package com.celloud.message.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.model.mysql.MessageCategory;
import com.celloud.utils.DateUtil;
import com.celloud.utils.UserAgentUtil;
import com.celloud.wechat.ParamFormat;
import com.celloud.wechat.WechatParams;
import com.celloud.wechat.WechatType;
import com.celloud.wechat.WechatUtils;

@Service
public class MessageCategoryUtils {
	@Resource
	private WechatUtils wechatUtils;
	/**
	 * 登录时初始化用户消息设置
	 * 
	 * @param userId
	 * @return
	 * @author lin
	 * @date 2016年7月13日下午3:02:59
	 */
	public Map<String, MessageCategory> initSetting(List<MessageCategory> userMessage,
			List<MessageCategory> allMessage) {
		Map<String, MessageCategory> result = new HashMap<>();
		if (userMessage != null && userMessage.size() > 0) {
			for (MessageCategory mc : userMessage) {
				result.put(mc.getCode(), mc);
			}
		} else if (allMessage != null && allMessage.size() > 0) {
			for (MessageCategory mc : allMessage) {
				result.put(mc.getCode(), mc);
			}
		} else {
			return null;
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
	public void sendMessage(String code, String params) {
		HttpSession session = ConstantsData.getSession();
		@SuppressWarnings("unchecked")
		Map<String, MessageCategory> map = (Map<String, MessageCategory>) session
				.getAttribute(Constants.MESSAGE_CATEGORY);
		if (map.get(code).getEmail().equals(MessageCategoryState.SEND)) {

		}
		if (map.get(code).getWechat().equals(MessageCategoryState.SEND)) {
			Object openId = session.getAttribute(Constants.SESSION_WECHAT_OPENID);
			if (openId != null && StringUtils.isNotEmpty(openId.toString())) {
				wechatUtils.pushMessage(
						ParamFormat.paramAll().template(WechatType.LOGIN).openId(openId.toString()).url(null)
						.data(ParamFormat.param()
										.set(WechatParams.LOGIN.first.name(),
												"您好，您的帐号" + session.getAttribute(ConstantsData.getLoginUserName())
														+ " 被登录",
										"#222222")
								.set(WechatParams.LOGIN.time.name(), DateUtil.getDateToString("yyyy-MM-dd hh:mm:ss"),
										null)
								.set(WechatParams.LOGIN.ip.name(), UserAgentUtil.getIp(ConstantsData.getRequset()),
										null)
								.set(WechatParams.LOGIN.reason.name(), "备注：如本次登录不是您本人授权，说明您的帐号存在安全隐患！为减少您的损失，请立即修改密码。",
										"#222222"))
						.get());
			}
		}
		if (map.get(code).getWindow().equals(MessageCategoryState.SEND)) {

		}

	}

}
