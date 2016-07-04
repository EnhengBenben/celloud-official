package com.celloud.action;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
		if ("out".equals(state)) {//关注后通过自动回复的链接进来，需要跳转登录页面
            mv.setViewName("wechat");
			Subject subject = SecurityUtils.getSubject();
			PublicKey publicKey = generatePublicKey(subject.getSession());
            mv.addObject("publicKey", publicKey).addObject("isSuccess",
                    "false");
		} else {
			//state 就是 MD5，需要校验MD5是否合法
			//合法则直接绑定

		}
        mv.addObject("code", code);
        return mv;
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView login(User user, String code) {
		log.info("用户微信登陆：" + user.getUsername());
		Session session = SecurityUtils.getSubject().getSession();
		PrivateKey privateKey = (PrivateKey) session.getAttribute(Constants.SESSION_RSA_PRIVATEKEY);
		ModelAndView mv = new ModelAndView("wechat").addObject("user", user);
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
        String openId = wechatUtils.getOpenId(code);
        us.insertUserWechatInfo(user.getUserId(), openId, null);
        String msg = "您的CelLoud账号与微信号绑定成功！";
		log.info("用户({})登录成功！", user.getUsername());
		session.removeAttribute(Constants.SESSION_RSA_PRIVATEKEY);
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
