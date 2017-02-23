package com.celloud.action;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.alimail.AliEmail;
import com.celloud.alimail.AliEmailUtils;
import com.celloud.alimail.AliSubstitution;
import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.model.mysql.Client;
import com.celloud.model.mysql.User;
import com.celloud.sendcloud.EmailParams;
import com.celloud.sendcloud.EmailType;
import com.celloud.service.AppService;
import com.celloud.service.ClientService;
import com.celloud.service.UserService;
import com.celloud.utils.MD5Util;
import com.celloud.utils.ResetPwdUtils;
import com.celloud.utils.UserAgentUtil;

/**
 * 首页相关的action
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月23日 下午1:40:56
 */
@Controller
public class HomeAction {
	private static final Logger logger = LoggerFactory.getLogger(HomeAction.class);
	@Resource
	private UserService userService;
	@Resource
	private AliEmailUtils emailUtils;
	@Resource
	private ClientService clientService;
	@Resource
	private AppService appService;

	/**
	 * 用户重置密码--跳转到重置密码页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "resetPassword/{username}/{randomCode}.html", method = RequestMethod.GET)
	public ModelAndView resetPassword(HttpSession session, @PathVariable String username,
			@PathVariable String randomCode) {
		// TODO 添加rsa加密
		ModelAndView mv = new ModelAndView("user/user_pwd_reset");
		User user = userService.getUserByFindPwd(username, randomCode);
		if (user == null) {
			return mv.addObject("info", "找回密码的链接错误或已过期");
		}
		logger.info("用户正在找回密码：userId={},username={},randomCode={}", user.getUserId(), username, randomCode);
		session.setAttribute(Constants.RESET_PASSWORD_USER_ID, user.getUserId());
		return mv.addObject("user", user).addObject("randomCode", randomCode);
	}

	@RequestMapping(value = "resetEmail/{username}/{randomCode}.html", method = RequestMethod.GET)
	public ModelAndView resetEmail(HttpSession session, @PathVariable String username,
			@PathVariable String randomCode) {
		ModelAndView mv = new ModelAndView("user/user_email_reset");
		User user = userService.getUserByFindPwd(username, randomCode);
		if (user == null) {
			return mv.addObject("info", "修改邮箱的链接错误或已过期");
		}
		logger.info("用户正在修改邮箱：userId={},username={},randomCode={}", user.getUserId(), username, randomCode);
		return mv.addObject("user", user).addObject("randomCode", randomCode);
	}

	/**
	 * 用户重置密码--保存
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "resetPassword.html", method = RequestMethod.POST)
	public ModelAndView resetPassword(String username, String password, String randomCode, HttpServletRequest request) {
		HttpSession session = request.getSession();
		// TODO 添加rsa加密
		logger.info("【重置密码】username=" + username);
		logger.info("【重置密码】randomCode=" + randomCode);
		ModelAndView mv = new ModelAndView("user/user_pwd_reset");
		String userId = String.valueOf(session.getAttribute(Constants.RESET_PASSWORD_USER_ID));
		logger.info("【重置密码】userId=" + userId);
		if (userId == null || userId.equalsIgnoreCase("null")) {
			logger.warn("用户进行了非法请求：重置密码时未检查到session中的userId.{},{},{}", username, randomCode,
					UserAgentUtil.getUrl(request));
			return mv.addObject("info", "请求不合法").addObject("forbidden", "forbidden");
		}
		User user = userService.getUserByFindPwd(username, randomCode);
		if (user == null || !userId.equals(String.valueOf(user.getUserId()))) {
			if (user == null) {
				logger.warn("用户进行了非法请求：重置密码时未检查到要修改的用户{},{}.{}", username, randomCode, UserAgentUtil.getUrl(request));
			} else {
				logger.warn("用户进行了非法请求：重置密码时检测到的userId不正确{}={},{},{}.{}", userId, String.valueOf(user.getUserId()),
						username, randomCode, UserAgentUtil.getUrl(request));
			}
			return mv.addObject("info", "请求不合法").addObject("forbidden", "forbidden");
		}
		session.removeAttribute(Constants.RESET_PASSWORD_USER_ID);
		userService.updatePassword(user.getUserId(), password);
		userService.cleanFindPwd(user.getUserId(), new Date());
		return mv.addObject("info", "密码重置成功");
	}

	/**
	 * 用户找回密码
	 * 
	 * @param email
	 * @param kaptchaCode
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "findPassword.html", method = RequestMethod.POST)
	public ModelAndView findPassword(String email, String kaptchaCode, HttpSession session) {
		ModelAndView mv = new ModelAndView("user/user_pwd_find").addObject("sucess", "fail");
		String kaptchaExpected = String
				.valueOf(session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY));
		// 干掉session中的验证码，避免用户刷新页面重复提交
		session.removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (kaptchaExpected == null || !kaptchaExpected.equalsIgnoreCase(kaptchaCode)) {
			return mv.addObject("info", "验证码错误，请重新输入！");
		}
		User user = userService.getUserByEmail(email);
		if (user == null) {
			return mv.addObject("info", "邮箱不存在，请重新输入！");
		}
		String randomCode = MD5Util.getMD5(String.valueOf(new Date().getTime()));
		userService.insertFindPwdInfo(user.getUserId(), randomCode);
		String url = ResetPwdUtils.celloudPath.replaceAll("resetpwduname", user.getUsername())
				.replaceAll("resetpwdcode", randomCode);
		AliEmail aliEmail = AliEmail.template(EmailType.PWD_FIND)
				.substitutionVars(
						AliSubstitution.sub().set(EmailParams.PWD_FIND.home.name(), ConstantsData.getContextUrl())
								.set(EmailParams.PWD_FIND.url.name(), url));
		emailUtils.simpleSend(aliEmail, email);

		email = email.substring(0, 1) + "***" + email.substring(email.lastIndexOf("@"));
		String emailAddress = "http://mail." + email.substring(email.lastIndexOf("@") + 1);
		return mv.addObject("success", "ok").addObject("email", email).addObject("emailAddress", emailAddress);
	}

	@RequestMapping(value = "findPassword.html", method = RequestMethod.GET)
	public String findPassword() {
		return "redirect:forgot.html";
	}

	@RequestMapping("service.html")
	public String service() {
		return "service";
	}

	@RequestMapping("feedback.html")
	public String feedBack() {
		return "feedback";
	}

	@RequestMapping("feedback_for_phone.html")
	public String feedBackForPhone() {
		return "feedback_for_phone";
	}

	@RequestMapping("home.html")
	public String home() {
		return "home";
	}

    // TODO测试
    @RequestMapping("index.html")
    public String indexHtml() {
        return "home";
    }

	@RequestMapping("index")
	public String index(HttpServletRequest request) {
		// XXX 百菌探报证结束后删除
        if (ConstantsData.getLoginUserId() == 126) {
			return "bsi/baozheng/index";
		}
		// 获取防盗链信息
		String referer = request.getHeader("referer");
        if (referer != null && referer.contains("customer")) {
			return "redirect:clientindex";
		}
		return "index";
	}

	@RequestMapping("bsi")
	public String bsi() {
		return "bsi/index";
	}

	@RequestMapping("rocky")
	public String rocky() {
		return "rocky/index";
	}

	@RequestMapping("clientindex")
	public String clientIndex() {
		return "client/index";
	}

	@RequestMapping("download.html")
	public ModelAndView download() {
		ModelAndView mv = new ModelAndView("download");
		Client client = clientService.getLast();
		mv.addObject("client", client);
		return mv;
	}

	@RequestMapping("join_us.html")
	public String joinUs() {
		return "join_us";
	}

	@RequestMapping("forgot.html")
	public String forgot() {
		return "user/user_pwd_find";
	}

	@RequestMapping("browser.html")
	public String browser() {
		return "browser";
	}

	@RequestMapping("home_phone.html")
	public String homePhone() {
		return "home_phone";
	}

	@RequestMapping("about_us.html")
	public String aboutUs() {
		return "about_us";
	}

	@RequestMapping("sample_order.html")
	public String sampleOrder() {
		return "experiment_scan/sampling_order";
	}

    @RequestMapping("sampleInfoOrder")
    public String sampleInfoOrder() {
        return "experiment_scan/sampling_info_order";
    }

	@RequestMapping("sessionTimeOut.html")
	public String sessionTimeOut() {
		return "user/user_timeout";
	}

	@RequestMapping("client.html")
	public String client(String info, Model model) {
		model.addAttribute("info", info);
		return "client";
	}

    @RequestMapping("wechat_rocky.html")
    public String wechatRocky(String info) {
        return "wechat/rocky_report";
    }

    /**
     * 手机注册用户重置密码
     * 
     * @param username
     * @param password
     * @param randomCode
     * @param request
     * @return
     * @author leamo
     * @date 2017年2月20日 下午11:26:01
     */
    @RequestMapping(value = "resetCellphonePwd.html", method = RequestMethod.POST)
    public String resetCellphonePwd(HttpServletRequest request, String username, String password, Model model) {
        boolean result = userService.addCellphoneUser(username, password);
        if (result) {
            model.addAttribute("info", "密码重置成功，请用新密码登录");
            return "redirect:login";
        }
        model.addAttribute("info", "密码重置失败，请重试！");
        HttpSession session = request.getSession();
        session.removeAttribute("isCellphoneRegister");
        return "user/user_pwd_cellphone";
    }
}
