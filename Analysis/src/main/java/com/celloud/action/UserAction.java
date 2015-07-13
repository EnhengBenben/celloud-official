package com.celloud.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.celloud.sdo.PublicKey;
import com.celloud.sdo.User;
import com.celloud.service.UserService;
import com.celloud.utils.MD5Util;
import com.celloud.utils.RSAUtil;
import com.google.inject.Inject;

@ParentPackage("json-default")
@Action("user")
@Results({ @Result(name = "success", location = "../../index.jsp"),
		@Result(name = "input", location = "../../login.jsp"),
		@Result(name = "init", type = "json", params = { "root", "publicKey" }),
		@Result(name = "output", type = "json", params = { "root", "list" })
})
public class UserAction extends BaseAction {
	Logger log = Logger.getLogger(UserAction.class);
	private static final long serialVersionUID = 1L;
	@Inject
	private UserService userService;
	private User user;
	private PublicKey publicKey;
	private List<Map<String, Object>> list;

	public String login() {
		log.info("用户" + user.getUsername() + "准备登录");
		String userName = user.getUsername();
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
		String pwd = RSAUtil.decryptStringByJs(user.getPassword());
		if (pwd != null) {
			user.setPassword(MD5Util.getMD5(pwd));
		}
		user = userService.login(user);
		if (user != null) {
			if (user.getRole() > 1) {
				super.session.put("userName", user.getUsername());
				super.session.put("userId", user.getUserId());
				super.session.put("userRole", user.getRole());
				super.session.put("deptId", user.getDeptId());
				super.session.put("companyId", user.getCompany_id());
				super.session.put("email", user.getEmail());
				return SUCCESS;
			} else {
				request.setAttribute("info", "该用户没有操作权限！");
				request.setAttribute("username", userName);
				request.setAttribute("password", "");
				return INPUT;
			}
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
		return "init";
	}

	public String logout() {
		String userName = String.valueOf(super.session.get("userName"));
		log.info("用户" + userName + "退出登录");
		super.session.clear();
		return "input";
	}
	
	/**
	 * 获取大客户的所有客户信息
	 * @return
	 */
	public String getBigUsersUser(){
		Integer companyId = (Integer) getCid();
		list = userService.getBigUsersUser(companyId);
		return "output";
	}
	
	private Object getCid() {
		Object cid = super.session.get("companyId");
		log.info("获取companyId:" + cid);
		if (cid == null) {
			log.error("后台session超时或者非法访问");
		}
		return cid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
}
