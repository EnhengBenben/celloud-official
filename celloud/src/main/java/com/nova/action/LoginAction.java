package com.nova.action;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.google.inject.Inject;
import com.nova.sdo.Behavior;
import com.nova.sdo.PublicKey;
import com.nova.sdo.User;
import com.nova.service.IBehaviorService;
import com.nova.service.IUserService;
import com.nova.utils.BehaviorUtil;
import com.nova.utils.IPUtil;
import com.nova.utils.MD5Util;
import com.nova.utils.RSAUtil;
import com.opensymphony.xwork2.ModelDriven;

@ParentPackage("celloud-default")
@Action("login")
@Results({ @Result(name = "success", location = "/pages/celloud.jsp"),
	@Result(name = "input", location = "/index.jsp"),
	@Result(name = "toLogin", location = "/index.jsp"),
	@Result(name = "logout", type = "redirect", location = "toLogin"),
	@Result(name = "init", type = "json", params = { "root", "publicKey" }) })
public class LoginAction extends BaseAction implements ModelDriven<User> {
    Logger log = Logger.getLogger(LoginAction.class);
    private static final long serialVersionUID = 1L;
    @Inject
    private IUserService userService;
    @Inject
    private IBehaviorService behaviorService;
    private User user;
    private boolean checked;
    private String id;
    private PublicKey publicKey;

    /**
     * 客户端登陆方法
     * 
     * @return
     */
    public String clientLogin() {
	if (id != null) {
	    User u = new User();
	    u.setLoginUuid(id);
	    user = userService.login(u);
	}
	// if (!ifNoticeTitle()) {
	// super.session.put("noticeTitleFlag", "1");
	// } else {
	// super.session.put("noticeTitleFlag", "0");
	// }
	if (user != null) {
	    super.session.put("userName", user.getUsername());
	    super.session.put("userId", user.getUserId());
	    super.session.put("userRole", user.getRole());
	    super.session.put("userNav", user.getNavigation());
	    super.session.put("deptId", user.getDeptId());
	    super.session.put("email", user.getEmail());
	    super.session.put("companyId", user.getCompanyId());

	    // 记录登录信息
	    new Runnable() {
		public void run() {
		    HttpServletRequest request = ServletActionContext
			    .getRequest();
		    Behavior behavior = BehaviorUtil.getBehavior(request);
		    behavior.setUserName(user.getUsername());
		    String address = IPUtil.getAddreeByIp(behavior.getIp(),
			    ServletActionContext.getServletContext()
				    .getRealPath("/"));
		    behavior.setAddress(address);
		    behaviorService.logLoginInfo(behavior);
		}
	    }.run();
	    return SUCCESS;
	} else {
	    return "logout";
	}
    }

    // XXX 时间紧迫，可优化
    public String login() {
	// if (!ifNoticeTitle()) {
	// super.session.put("noticeTitleFlag", "1");
	// } else {
	// super.session.put("noticeTitleFlag", "0");
	// }
	HashMap<String, String> rsa = RSAUtil.map;
	publicKey = new PublicKey();
	publicKey.setModulus(rsa.get("pubmo"));
	publicKey.setExponent(rsa.get("pubex"));
	String userName = user.getUsername();
	if (checked) {
	    Cookie nameCookie = new Cookie("username", user.getUsername());
	    Cookie passwdcookie = new Cookie("password", user.getPassword());
	    nameCookie.setMaxAge(60 * 60 * 24 * 7);
	    passwdcookie.setMaxAge(60 * 60 * 24 * 7);
	    response.addCookie(nameCookie);
	    response.addCookie(passwdcookie);
	    if (!"".equals(request.getParameter("info"))) {
		String kaptchaExpected = (String) session
			.get(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (kaptchaExpected == null
			|| !kaptchaExpected.equalsIgnoreCase(user
				.getKaptchaCode())) {
		    request.setAttribute("info", "验证码错误，请重新输入！");
		    request.setAttribute("password", "");
		    return INPUT;
		}
	    }
	} else {
	    // 未选择记住密码则清空cookie
	    Cookie nameCookie = new Cookie("username", null);
	    Cookie passwdcookie = new Cookie("password", null);
	    nameCookie.setMaxAge(0);
	    passwdcookie.setMaxAge(0);
	    response.addCookie(nameCookie);
	    response.addCookie(passwdcookie);
	    String kaptchaExpected = (String) session
		    .get(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
	    if (kaptchaExpected == null) {
		request.setAttribute("info", "验证码错误，请重新输入！");
		return INPUT;
	    } else {
		boolean flag = (kaptchaExpected.equalsIgnoreCase(user
			.getKaptchaCode()));
		if (!flag) {
		    request.setAttribute("info", "验证码错误，请重新登录！");
		    request.setAttribute("username", userName);
		    request.setAttribute("password", "");
		    return INPUT;
		}
	    }
	}
	String pwd = RSAUtil.decryptStringByJs(user.getPassword());
	if (pwd != null) {
	    user.setPassword(MD5Util.getMD5(pwd));
	}
	user = userService.login(user);
	if (user != null) {
	    super.session.put("userName", user.getUsername());
	    super.session.put("userId", user.getUserId());
	    super.session.put("userRole", user.getRole());
	    super.session.put("userNav", user.getNavigation());
	    super.session.put("deptId", user.getDeptId());
	    super.session.put("email", user.getEmail());
	    super.session.put("companyId", user.getCompanyId());
	    // 记录登录信息
	    new Runnable() {
		public void run() {
		    HttpServletRequest request = ServletActionContext
			    .getRequest();
		    Behavior behavior = BehaviorUtil.getBehavior(request);
		    behavior.setUserName(user.getUsername());
		    String address = IPUtil.getAddreeByIp(behavior.getIp(),
			    ServletActionContext.getServletContext()
				    .getRealPath("/"));
		    behavior.setAddress(address);
		    behaviorService.logLoginInfo(behavior);
		}
	    }.run();

	    return SUCCESS;
	} else {
	    request.setAttribute("info", "用户名或密码错误，请重新登录！");
	    request.setAttribute("username", userName);
	    request.setAttribute("password", "");
	    return INPUT;
	}
    }

    public String toLogin() {
	HashMap<String, String> rsa = RSAUtil.map;
	publicKey = new PublicKey();
	publicKey.setModulus(rsa.get("pubmo"));
	publicKey.setExponent(rsa.get("pubex"));
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
	    for (int i = 0; i < cookies.length; i++) {
		Cookie c = cookies[i];
		if (c.getName().equalsIgnoreCase("username")) {
		    user.setUsername(c.getValue());
		} else if (c.getName().equalsIgnoreCase("password")) {
		    user.setPassword(c.getValue());
		    this.checked = true;
		}
	    }
	} else {
	    this.checked = false;
	}
	return "toLogin";
    }

    public String logout() {
	String userName = String.valueOf(super.session.get("userName"));
	log.info("用户" + userName + "退出登录");
	super.session.clear();

	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
	    for (int i = 0; i < cookies.length; i++) {
		String value = cookies[i].getName();
		if ("username".equals(value) || "password".equals(value)) {
		    cookies[i].setMaxAge(0);
		    response.addCookie(cookies[i]);
		}
	    }
	}
	return "logout";
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    @Override
    public User getModel() {
	if (user == null) {
	    user = new User();
	}
	return user;
    }

    public boolean isChecked() {
	return checked;
    }

    public void setChecked(boolean checked) {
	this.checked = checked;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public PublicKey getPublicKey() {
	return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
	this.publicKey = publicKey;
    }

}