package com.celloud.action;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.Constants;
import com.celloud.model.PrivateKey;
import com.celloud.model.PublicKey;
import com.celloud.model.mysql.User;
import com.celloud.service.UserService;
import com.celloud.utils.MD5Util;
import com.celloud.utils.RSAUtil;
import com.celloud.wechat.WechatUtils;

@Controller
@RequestMapping("api/wechat")
public class WeChatAction {
	Logger log = LoggerFactory.getLogger(WeChatAction.class);

	@Resource
	private UserService us;
    @Resource
    private WechatUtils wechatUtils;

	@RequestMapping(value = "getState", method = RequestMethod.GET)
    public ModelAndView getState(String state, String code) {
        ModelAndView mv = new ModelAndView();
		String openId = wechatUtils.getOpenId(code);
		String msg = null;
		if ("out".equals(state)) {//关注后通过自动回复的链接进来，需要跳转登录页面
			mv.setViewName("wechat/bind");
			int isBind = us.checkWechatBind(openId, null);
			if (isBind > 0) {
				msg = "您的微信号已绑定平台账号，不可重复绑定，如有疑问请登录平台后在“问题反馈”中联系我们。";
				mv.addObject("info", msg).addObject("isSuccess", "true");
				return mv;
			}
			Session session = SecurityUtils.getSubject().getSession();
			session.setAttribute(Constants.SESSION_WECHAT_OPENID, openId);
			PublicKey publicKey = generatePublicKey(session);
			mv.addObject("publicKey", publicKey).addObject("isSuccess", "false");
		} else {
			//TODO
		}
        return mv;
	}

	@RequestMapping(value = "toUnBind", method = RequestMethod.GET)
	public ModelAndView toUnBind(String state, String code) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wechat/unbind");
		String openId = wechatUtils.getOpenId(code);
		int isBind = us.checkWechatBind(openId, null);
		if (isBind == 0) {
			String msg = "您的微信号尚未绑定平台账号，不可解除绑定，如有疑问请登录平台后在“问题反馈”中联系我们。";
			mv.addObject("info", msg).addObject("isSuccess", "true");
			return mv;
		}
		User user = us.getUserByOpenId(openId);
		Session session = SecurityUtils.getSubject().getSession();
		session.setAttribute(Constants.SESSION_WECHAT_OPENID, openId);
		PublicKey publicKey = generatePublicKey(session);
		mv.addObject("publicKey", publicKey).addObject("isSuccess", "false").addObject("username", user.getUsername());
		return mv;
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ModelAndView login(User user) {
		log.info("用户微信登陆：" + user.getUsername());
		Session session = SecurityUtils.getSubject().getSession();
		PrivateKey privateKey = (PrivateKey) session.getAttribute(Constants.SESSION_RSA_PRIVATEKEY);
		ModelAndView mv = new ModelAndView("wechat/bind").addObject("user", user);
		String password = user.getPassword();
        password = RSAUtil.decryptStringByJs(privateKey, password);
		password = password == null ? "" : MD5Util.getMD5(password);
		user.setPassword(password);
		user = us.login(user);
		if (user == null) {
			String msg = "用户名或密码错误，请重新登录！";
			mv.addObject("publicKey", generatePublicKey(session)).addObject("info", msg).addObject("isSuccess",
					"false");
			return mv;
		}
		Object openID = session.getAttribute(Constants.SESSION_WECHAT_OPENID);
		if (openID == null) {
			String msg = "绑定失败，请重新点击绑定链接后再次绑定账号！";
			mv.addObject("publicKey", generatePublicKey(session)).addObject("info", msg).addObject("isSuccess",
					"false");
			return mv;
		}
		String openId = openID.toString();
		int isBind = us.checkWechatBind(openId, user.getUserId());
        String msg = "";
		if (isBind == 0) {
            us.insertUserWechatInfo(user.getUserId(), openId, null);
            msg = "您的CelLoud账号与微信号绑定成功！";
            log.info("用户({})登录成功！", user.getUsername());
        } else {
			msg = "您的平台账号已绑定微信号，不可重复绑定，如有疑问请登录平台后在“问题反馈”中联系我们。";
        }
		session.removeAttribute(Constants.SESSION_RSA_PRIVATEKEY);
		session.removeAttribute(Constants.SESSION_WECHAT_OPENID);
		mv.addObject("info", msg).addObject("isSuccess", "true");
		return mv;
	}

	@RequestMapping(value = "unBind", method = RequestMethod.POST)
	public ModelAndView unBind(User user) {
		Session session = SecurityUtils.getSubject().getSession();
		PrivateKey privateKey = (PrivateKey) session.getAttribute(Constants.SESSION_RSA_PRIVATEKEY);
		ModelAndView mv = new ModelAndView("wechat/unbind").addObject("user", user);
		String password = user.getPassword();
		password = RSAUtil.decryptStringByJs(privateKey, password);
		password = password == null ? "" : MD5Util.getMD5(password);
		String openId = session.getAttribute(Constants.SESSION_WECHAT_OPENID).toString();
		int num = us.checkWechatUnBind(openId, password);
		if (num == 0) {
			String msg = "密码错误，请重新输入！";
			mv.addObject("publicKey", generatePublicKey(session)).addObject("info", msg).addObject("isSuccess", "false")
					.addObject("username", user.getUsername());
			return mv;
		}
		us.wechatUnBind(openId, password);
		String msg = "您的CelLoud账号与微信号解绑成功！";
		session.removeAttribute(Constants.SESSION_RSA_PRIVATEKEY);
		session.removeAttribute(Constants.SESSION_WECHAT_OPENID);
		mv.addObject("info", msg).addObject("isSuccess", "true");
		return mv;
	}

	/**
	 * 生成一个rsa公钥私钥对，将私钥存储到session，返回公钥
	 * 
	 * @param session
	 * @return
	 */
	private PublicKey generatePublicKey(Session session) {
		KeyPair keyPair = RSAUtil.generateKeyPair();
		RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
		PrivateKey privateKey = new PrivateKey(rsaPrivateKey.getModulus(), rsaPrivateKey.getPrivateExponent());
		session.setAttribute(Constants.SESSION_RSA_PRIVATEKEY, privateKey);
		PublicKey publicKey = new PublicKey();
		publicKey.setModulus(rsaPublicKey.getModulus().toString(16));
		publicKey.setExponent(rsaPublicKey.getPublicExponent().toString(16));
		return publicKey;
	}
}
