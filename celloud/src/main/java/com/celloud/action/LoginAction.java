package com.celloud.action;

import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.alidayu.AliDayuUtils;
import com.celloud.alidayu.AlidayuConfig;
import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.message.category.MessageCategoryCode;
import com.celloud.message.category.MessageCategoryUtils;
import com.celloud.model.LoginCaptcha;
import com.celloud.model.PrivateKey;
import com.celloud.model.PublicKey;
import com.celloud.model.mysql.App;
import com.celloud.model.mysql.User;
import com.celloud.service.ActionLogService;
import com.celloud.service.AppService;
import com.celloud.service.RSAKeyService;
import com.celloud.service.UserService;
import com.celloud.utils.ActionLog;
import com.celloud.utils.DataUtil;
import com.celloud.utils.DateUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.MD5Util;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.RSAUtil;
import com.celloud.utils.UserAgentUtil;
import com.celloud.wechat.ParamFormat;
import com.celloud.wechat.ParamFormat.Param;
import com.celloud.wechat.constant.WechatParams;

/**
 * 登录action
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月22日 下午1:32:22
 */
@Controller
public class LoginAction {
	Logger logger = LoggerFactory.getLogger(LoginAction.class);
	@Resource
	private UserService userService;
	@Resource
	private RSAKeyService rsaKeyService;
	@Resource
	private ActionLogService logService;
	@Resource
	private MessageCategoryUtils mcu;
	@Resource
	private AppService appService;

	@ActionLog(value = "发送登录验证码", button = "发送验证码")
	@RequestMapping("sendLoginCapcha.html")
	@ResponseBody
	public String sendCapcha(String cellphone) {
		String captcha = DataUtil.getCapchaRandom();
		String result = AliDayuUtils.sendCaptcha(cellphone, captcha);
		// 验证码已发送
		if (!result.equals("error")) {
			// 存储验证码，计算过期时间
			LoginCaptcha loginCaptcha = new LoginCaptcha();
			loginCaptcha.setCaptcha(captcha);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MINUTE, AlidayuConfig.captcha_expire_time);
			loginCaptcha.setExpireDate(calendar.getTime());
			// TODO 临时存文件
			File f = new File(PropertiesUtil.outputPath + cellphone);
			if (f.exists()) {
				try {
					FileUtils.forceDelete(new File(PropertiesUtil.outputPath + cellphone));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileTools.createFile(PropertiesUtil.outputPath + cellphone + "/" + captcha);
			return "succuss";
		}
		return "error";
	}

	@ActionLog(value = "C端用户登录", button = "登录")
	@RequestMapping(value = "clientLogin.html", method = RequestMethod.POST)
	public ModelAndView clientLogin(String cellphone, String captcha, Model model) {
		File f = new File(PropertiesUtil.outputPath + cellphone + "/" + captcha);
		ModelAndView mv = new ModelAndView("client");
		if (f.exists() && new Date().getTime() - f.lastModified() < 1000 * 60 * 5) {
			Integer result = userService.checkAddClientUser(cellphone);
			if (result != 0) {
				Subject subject = SecurityUtils.getSubject();
				User user = userService.findByUsernameOrEmail("cel_" + cellphone.substring(3, cellphone.length()));
				UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword(), false);
				try {
					subject.login(token);
				} catch (IncorrectCredentialsException | UnknownAccountException e) {
					mv.addObject("info", "用户登录失败，用户名密码错误");
					logger.info("用户登录失败，用户名密码错误：phone={},captcha={}", cellphone, captcha);
				} catch (Exception e) {
					mv.addObject("info", "用户登录失败，未知异常");
					logger.info("用户登录失败，未知异常：phone={},captcha={}", cellphone, captcha);
				}
				if (subject.isAuthenticated()) {
                    mv.setViewName("loading");
					try {
						FileUtils.forceDelete(new File(PropertiesUtil.outputPath + cellphone));
					} catch (IOException e) {
						e.printStackTrace();
					}
					return mv;
				}
			}
		}else{
			mv.addObject("info", "验证码错误，请重新输入！");
		}
		return mv;
	}

	/**
	 * 跳转到登录页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ActionLog(value = "跳转到登录页面", button = "登录")
	@RequestMapping("login")
	public ModelAndView login() {
		ConstantsData.getAnotherNamePerlPath(null);
		ModelAndView mv = new ModelAndView("login");
		User user = new User();
		Subject subject = SecurityUtils.getSubject();
		Object isRem = subject.getSession(true).getAttribute("isRemembered");
		boolean isRemembered = isRem != null ? ((boolean) isRem) : subject.isRemembered();
		PublicKey key = generatePublicKey(subject.getSession());
		if (isRemembered) {
			String username = String.valueOf(subject.getPrincipal());
			User temp = userService.findByUsernameOrEmail(username);
			if (temp != null) {
				user.setUsername(temp.getUsername());
				String password = RSAUtil.encryptedString(key.getModulus(), key.getExponent(), temp.getPassword());
				user.setPassword(password);
			} else {
				logger.info("用户使用记住密码登录，但根据用户名(" + username + ")未找到用户");
				isRemembered = false;
			}
		}
		return mv.addObject("checked", isRemembered).addObject("user", user).addObject("publicKey", key)
				.addObject("showKaptchaCode", getFailedlogins() >= 3);
	}

	/**
	 * 用户登录验证
	 * 
	 * @param model
	 * @param user
	 * @param kaptchaCode
	 * @param publicKey
	 * @param checked
	 * @param request
	 * @param response
	 * @return
	 */
	@ActionLog(value = "用户登录", button = "登录")
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ModelAndView login(User user, String kaptchaCode, String newPassword, boolean checked) {
		logger.info("用户正在登陆：" + user.getUsername());
		ConstantsData.getAnotherNamePerlPath(null);
		Subject subject = SecurityUtils.getSubject();
		String password = user.getPassword();
		user.setPassword("");
		Session session = subject.getSession();
		PrivateKey privateKey = (PrivateKey) session.getAttribute(Constants.SESSION_RSA_PRIVATEKEY);
		ModelAndView mv = new ModelAndView("login").addObject("user", user).addObject("checked", subject.isRemembered())
				.addObject("publicKey", generatePublicKey(session))
				.addObject("showKaptchaCode", getFailedlogins() >= 3);
		if (!checkKaptcha(kaptchaCode, session)) {
			return mv.addObject("info", "验证码错误，请重新登录！");
		}
		if (newPassword == null || newPassword.trim().length() <= 0) {
			password = RSAUtil.decryptString(privateKey, password);
		} else {
			password = RSAUtil.decryptStringByJs(privateKey, newPassword);
			password = password == null ? "" : MD5Util.getMD5(password);
		}
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), password, checked);
		try {
			subject.login(token);
		} catch (IncorrectCredentialsException | UnknownAccountException e) {
			String msg = "用户名或密码错误，请重新登录！";
			addFailedlogins();
			logger.warn("用户（{}）登录失败，用户名或密码错误！", user.getUsername());
			return mv.addObject("info", msg).addObject("showKaptchaCode", getFailedlogins() >= 3);
		} catch (Exception e) {
			logger.error("登录失败！", e);
			return mv.addObject("info", "登录失败！");
		}
		if (!subject.isAuthenticated()) {
			return mv.addObject("info", "登录失败！");
		}
		User loginUser = ConstantsData.getLoginUser();
		logger.info("用户({})登录成功！", loginUser.getUsername());
		logService.log("用户登录", "用户" + loginUser.getUsername() + "登录成功");
		session.removeAttribute(Constants.SESSION_RSA_PRIVATEKEY);
		session.removeAttribute(Constants.SESSION_FAILED_LOGIN_TIME);
		session.setAttribute("isRemembered", checked);
		Integer userId = loginUser.getUserId();
		// 获取用户所属的大客户，决定是否有统计菜单
		Integer companyId = userService.getCompanyIdByUserId(userId);
		session.setAttribute("companyId", companyId);
		mv.setViewName("loading");
		String openId = userService.getOpenIdByUser(userId);
		session.setAttribute(Constants.SESSION_WECHAT_OPENID, openId);
		// 初始化消息中心
		session.setAttribute(Constants.MESSAGE_CATEGORY, null);
		// 发送登录消息
		String first = "您好，您的帐号" + ConstantsData.getLoginUserName() + " 被登录";
		String now = DateUtil.getDateToString(DateUtil.YMDHMS);
		String ip = UserAgentUtil.getIp(ConstantsData.getRequset());
		String reason = "备注：如本次登录不是您本人授权，说明您的帐号存在安全隐患！为减少您的损失，请立即修改密码。";
		Param params = ParamFormat.param().set(WechatParams.LOGIN.first.name(), first, "#222222")
				.set(WechatParams.LOGIN.time.name(), now, null).set(WechatParams.LOGIN.ip.name(), ip, null)
				.set(WechatParams.LOGIN.reason.name(), reason, "#222222");
		mcu.sendMessage(userId, MessageCategoryCode.LOGIN, null, params, null);
		// 获取当前用户所有的app
		List<App> appList = appService.getMyAppList(userId);
		// 该用户appId不为空, 判断是否包含bsi与rocky
		boolean bsi = false;
		boolean rocky = false;
		if (appList != null && appList.size() > 0) {
			for (App app : appList) {
				// 获取该app的appId
				Integer appId = app.getAppId();
				// bsi
				if (appId == 118) {
					bsi = true;
				} else if (appId == 123) {
					rocky = true;
				}
			}
		}
		if (!bsi && rocky) {
			mv.addObject("route", "#/product/rocky/upload");
        } else if (bsi && !rocky) {
            mv.addObject("route", "#/product/bsi/report");
		}
		return mv;
	}

	/**
	 * 获取用户登录的账号密码错误次数
	 * 
	 * @return
	 */
	public int getFailedlogins() {
		Object failedlogins = SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_FAILED_LOGIN_TIME);
		int time = 0;
		try {
			time = Integer.parseInt((String) failedlogins);
		} catch (Exception e) {
		}
		return time;
	}

	/**
	 * 增加用户登录的账号密码错误次数
	 */
	public void addFailedlogins() {
		int time = getFailedlogins();
		SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_FAILED_LOGIN_TIME, (time + 1) + "");
	}

	/**
	 * 校验验证码，在用户错误三次及以上时，需要校验验证码
	 * 
	 * @param kaptcha
	 * @param session
	 * @return
	 */
	public boolean checkKaptcha(String kaptcha, Session session) {
		int time = getFailedlogins();
		if (time < 3) {
			return true;
		}
		String kaptchaExpected = (String) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		// 验证码错误，直接返回到登录页面
		if (kaptchaExpected == null || !kaptchaExpected.equalsIgnoreCase(kaptcha)) {
			logger.info("用户登陆验证码错误：param : {} \t session : {}", kaptcha, kaptchaExpected);
			return false;
		}
		return true;
	}

	/**
	 * 用户退出操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ActionLog(value = "用户退出", button = "退出")
	@RequestMapping("logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, String flag) {
		HttpSession session = request.getSession();
		User user = ConstantsData.getLoginUser();
		session.removeAttribute(Constants.SESSION_LOGIN_USER);
		Enumeration<String> names = session.getAttributeNames();
		while (names.hasMoreElements()) {
			session.removeAttribute(names.nextElement());
		}
		SecurityUtils.getSubject().logout();
		if ((user != null && user.getRole().equals(3)) || "client".equals(flag)) {
			return "redirect:client.html";
		}
		logger.info("用户({})主动退出", user == null ? "null..." : user.getUsername());
		return "redirect:login";
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
