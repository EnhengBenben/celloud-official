package com.nova.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.google.inject.Inject;
import com.nova.email.EmailService;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.Behavior;
import com.nova.sdo.Company;
import com.nova.sdo.Dept;
import com.nova.sdo.User;
import com.nova.service.IBehaviorService;
import com.nova.service.ICompanyService;
import com.nova.service.IDeptService;
import com.nova.service.IUserService;
import com.nova.utils.Base64Util;
import com.nova.utils.MD5Util;
import com.nova.utils.ResetPwdUtils;

@ParentPackage("celloud-default")
@Action("user")
@Results({
    @Result(name = "validateUserEmail", type = "json", params = { "root", "result" }),
	@Result(name = "checkEmail", type = "json", params = { "root", "email" }),
	@Result(name = "resetPassword", location = "../../pages/user/resetPassword.jsp"),
	@Result(name = "goToForgetPwd", location = "../../pages/user/forgetPwd.jsp"),
	@Result(name = "getUserLogInfo", location = "../../pages/user/logInfoList.jsp"),
	@Result(name = "success", type = "json", params = { "root", "flag" }) })
public class UserAction extends BaseAction {
    Logger log = Logger.getLogger(UserAction.class);
    private static final long serialVersionUID = 1L;
    @Inject
    private IUserService userService;
    @Inject
    private IBehaviorService behaviorService;
    @Inject
    private ICompanyService ics;
    @Inject
    private IDeptService ids;
    private String username;
    private int userId;
    private String password;
    private boolean flag;
    private int result;
    private User user;
    private List<User> userList;
    private Map<String, List<Map<String, String>>> userMap;
    private List<Map<String, String>> userMapList;
    private Map<String, List<Map<String, String>>> userMapNotSharedPro;
    private int projectId;
    private int role;
    private Page page;
    private int notify;
    // 用户信息的分页列表
    private PageList<User> userPageList;
    private String email;
    private PageList<Behavior> behaviorList;
    private String[] emailArray;
    private List<Company> comList;
    private List<Dept> deptList;
    private int deptId;
    private String context;

    /**
     * 系统层级的群发邮件
     * 
     * @return
     */
    public String sendEmailToUser() {
	for (String emailTo : emailArray) {
	    EmailService.send(emailTo, context, true);
	}
	flag = true;
	return SUCCESS;
    }

    /**
     * 后台采用发邮件添加用户时检验邮箱是否已经添加
     * 
     * @return
     */
    public String checkEmail() {
	StringBuffer sb = new StringBuffer();
	for (String email : emailArray) {
	    result = userService.checkUserEmail(email) ? 1 : 0;
	    sb.append(result).append(",");
	}
	sb.delete(sb.length() - 1, sb.length());
	email = sb.toString();
	return "checkEmail";
    }

    /**
     * 用户自主注册时校验邮箱
     * 
     * @return
     */
    public String checkEmailOne() {
	flag = userService.checkUserEmail(email);
	return SUCCESS;
    }

    /**
     * 发送添加用户邮件
     * 
     * @return
     */
    public void sendEmail() {
	for (String email : emailArray) {
	    String randomCode = MD5Util.getMD5(String.valueOf(new Date()
		    .getTime()));
	    userService.insertFindPwdInfo(email, randomCode);
	    String param = Base64Util.encrypt(email + "/" + randomCode + "/"
		    + deptId + "/" + user.getCompanyId());
	    String context = ResetPwdUtils.userContent.replaceAll("url",
		    ResetPwdUtils.userPath.replaceAll("path", param));
	    EmailService.send(email, context, ResetPwdUtils.userTitle);
	}
    }

    /**
     * 修改不再提示
     * 
     * @return
     */
    public String updateNotify() {
	userId = (Integer) session.get("userId");
	flag = userService.updateNotify(userId, notify);
	return "updateNotify";
    }

    /**
     * 检查管理员页面是否超时
     * 
     * @return
     */
    public String checkAdminSessionTimeOut() {
	username = (String) super.session.get("userName");
	return SUCCESS;
    }

    /**
     * 根据用户编号删除用户
     * 
     * @return
     */
    public String deleteUserByUserId() {
	result = userService.deleteUserByUserId(userId);
	return "deleteUserByUserId";
    }

    /**
     * 获取用户个人信息页面
     * 
     * @return
     */
    public String openPersonalInfo() {
	return "openPersonalInfo";
    }

    /**
     * 获取登录日志信息
     * 
     * @return
     */
    public String getUserLogInfo() {
	behaviorList = behaviorService.getUserLogInfo(page);
	return "getUserLogInfo";
    }

    /**
     * 根据当前登录的用户编号获取用户邮箱
     * 
     * @return
     */
    public String getEmailBySessionUserId() {
	userId = (Integer) session.get("userId");
	email = userService.getEmailBySessionUserId(userId);
	return SUCCESS;
    }

    /**
     * 用户注册
     * 
     * @return
     */
    public String registUser() {
	result = userService.register(user);
	return SUCCESS;
    }

    /**
     * 验证码验证
     * 
     * @return
     */
    public String checkKapcha() {
	String kaptchaExpected = (String) session
		.get(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
	flag = (kaptchaExpected.equalsIgnoreCase(kaptchaCode));
	return SUCCESS;
    }

    /**
     * 用户邮箱验重
     * 
     * @return
     */
    public String checkUserEmail() {
	flag = userService.checkUserEmail(user.getEmail());
	return SUCCESS;
    }

    public String checkUserEmailByUserId() {
        System.out.println("____ß");
        flag = userService.checkUserEmailByUserId(
                (Integer) super.session.get("userId"), user.getEmail());
        return SUCCESS;
    }

    /**
     * 用户名验重
     * 
     * @return
     */
    public String checkUsername() {
	log.info("校验用户名是否重复：" + username);
	flag = userService.checkUsername(username);
	return SUCCESS;
    }

    public String checkUsernameByUserId() {
	flag = userService.checkUsernameByUserId(user.getUserId(),
		user.getUsername());
	return SUCCESS;
    }

    /**
     * 个人信息维护
     * 
     * @return
     */
    public String updateUserBaseInfo() {
	username = (String) session.get("userName");
	user.setUsername(username);
	result = userService.updateUserByUserName(user);
	return SUCCESS;
    }

    /**
     * 跳转到找回密码页面
     * 
     * @return
     */
    public String goToForgetPwd() {
	return "goToForgetPwd";
    }

    /**
     * 跳转用户注册页面
     * 
     * @return
     */
    public String register() {
	String param = Base64Util.decrypt(email);
	System.out.println(param);
	String p[] = param.split("/");
	if (p.length != 4) {
	    flag = false;
	} else {
	    code = p[1];
	    user = new User();
	    user.setEmail(p[0]);
	    user.setDeptId(Integer.parseInt(p[2]));
	    user.setCompanyId(Integer.parseInt(p[3]));
	    flag = userService.getValidate(p[0], code);
	}
	return "register";
    }

    /**
     * 获取当前登陆人信息
     * 
     * @return
     */
    public String getUserInfo() {
	userId = (Integer) session.get("userId");
	user = userService.getUserById(userId);
	return SUCCESS;
    }

    /**
     * 验证用户输入的密码是否正确
     * 
     * @return
     */
    public String validatePwd() {
	userId = (Integer) session.get("userId");
	flag = userService.checkPwd(userId, password);
	return SUCCESS;
    }

    /**
     * 修改密码
     * 
     * @return
     */
    public String updateUserPwd() {
	userId = (Integer) session.get("userId");
	String result = userService.changePassword(userId, password);
	if (result.equals("success")) {
	    flag = true;
	    super.session.put("userPwd", password);
	} else {
	    flag = false;
	}
	return SUCCESS;
    }

    /**
     * 验证用户邮箱是否存在
     * 
     * @return
     */
    private String kaptchaCode;

    public String validateUserEmail() {
	String kaptchaExpected = (String) session
		.get(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
	if (!kaptchaCode.equalsIgnoreCase(kaptchaExpected)) {
	    result = 1;// 验证码不正确
	} else {
	    username = userService.getUserNameByEmail(user.getEmail());
	    userId = userService.getUserIdByName(username);
	    if (null != username) {
		result = 0;// 邮箱验证通过
		// 1、随机生成验证码
		String randomCode = MD5Util.getMD5(String.valueOf(new Date()
			.getTime()));
		userService.insertFindPwdInfo(userId, randomCode);
		// 2、发送邮件
		EmailService.send(
			user.getEmail(),
			ResetPwdUtils.content.replaceAll("username", username)
				.replaceAll(
					"url",
					ResetPwdUtils.celloudPath.replaceAll(
						"resetpwduname", username)
						.replaceAll("resetpwdcode",
							randomCode)),
			ResetPwdUtils.title,true);
	    } else {
		result = 2;// 邮箱验证没通过
	    }
	}
	return SUCCESS;
    }

    /**
     * 密码重置，由用户点击邮箱链接触发
     * 
     * @return
     */
    // code获取随机验证码
    private String code;

    public String resetPassword() {
	Date dateNow = new Date();
	userId = userService.getUserIdByName(username);
	Date expireDate = userService.getExpireDateByUserIdCode(userId, code);
	if (null == expireDate) {// 没有找到记录，用户可能修改了链接
	    result = 0;// 链接错误
	} else {
	    long diff = dateNow.getTime() - expireDate.getTime();
	    if (diff > 0) {// 链接超时
		result = 0;
	    } else {
		result = 1;// 链接验证通过
	    }
	}
	return "resetPassword";
    }

    /**
     * 密码重置，用户密码找回
     * 
     * @return
     */
    public String updatePwdByUserName() {
	userId = userService.getUserIdByName(username);
	String result = userService.changePassword(userId, user.getPassword());
	if (result.equals("success")) {
	    this.result = 0;// 修改成功
	    // 修改链接的失效日期
	    userService.updateExpireDateByUserId(userId);
	} else {
	    this.result = 1;// 修改失败
	}
	return SUCCESS;
    }

    /**
     * 获取全部用户分页列表
     * 
     * @return
     */
    public String getAllUserPageList() {
	userPageList = userService.getAllUserPageList(page, username);
	return "getUserPageList";
    }

    public String addUser() {
	log.info("新增用户");
	int addNum = userService.addUser(user);
	if (addNum > 0) {
	    flag = true;
	    userService.deleteValidate(user.getEmail());
	} else {
	    flag = false;
	}
	return "addUser";
    }

    public String updateUser() {
	int updateNum = userService.updateUser(user);
	if (updateNum > 0) {
	    flag = true;
	} else {
	    flag = false;
	}
	return "updateUser";
    }

    public String getUserIdByName() {
	userId = userService.getUserIdByName(username);
	return "getUserIdByName";
    }

    /**
     * 根据用户编号获取用户信息
     * 
     * @return
     */
    public String getUserById() {
	user = userService.getUserById(userId);
	comList = ics.getAllCompany();
	Dept dept = ids.getDept(user.getDeptId());
	if (dept != null) {
	    deptList = ids.getDeptAll(dept.getCompanyId());
	}
	return "getUserById";
    }

    /**
     * 获取用户角色
     * 
     * @return
     */
    public String getUserRole() {
	role = (Integer) super.session.get("userRole");
	return "getUserRole";
    }

    public String getAllUserList() {
	userList = userService.getAllUserList();
	return "getAllUserList";
    }

    /**
     * 返回所有用户列表
     * 
     * @return
     */
    public String getAllUser() {
	userList = userService.getAllUserList();
	userMapList = new ArrayList<Map<String, String>>();
	for (User user : userList) {
	    Map<String, String> userMap = new HashMap<String, String>();
	    userMap.put("id", user.getUserId() + "");
	    userMap.put("text", user.getUsername());
	    userMapList.add(userMap);
	}
	return "getAllUser";
    }

    /**
     * 获取非登录用户的用户列表
     * 
     * @return
     */
    public String getUsersMap() {
	Integer userId = (Integer) super.session.get("userId");
	userList = userService.getUserList(userId);
	List<Map<String, String>> userMapList = new ArrayList<Map<String, String>>();
	for (User user : userList) {
	    Map<String, String> userMap = new HashMap<String, String>();
	    userMap.put("id", user.getUserId() + "");
	    userMap.put("text", user.getUsername());
	    userMapList.add(userMap);
	}
	userMap = new HashMap<String, List<Map<String, String>>>();
	userMap.put("tags", userMapList);
	return SUCCESS;
    }

    /**
     * 获取已经被共享的用户列表
     * 
     * @return
     */
    public String getUsersMapSharedPro() {
	Integer userId = (Integer) super.session.get("userId");
	userList = userService.getUserListSharedPro(userId, projectId);
	userMapList = new ArrayList<Map<String, String>>();
	for (User user : userList) {
	    Map<String, String> userMap = new HashMap<String, String>();
	    userMap.put("id", user.getUserId() + "");
	    userMap.put("text", user.getUsername());
	    userMapList.add(userMap);
	}
	return "usersMapSharedPro";
    }

    /**
     * 验证密码有效性
     * 
     * @return
     */
    public String checkPwd() {
	Integer userId = (Integer) super.session.get("userId");
	flag = userService.checkPwd(userId, password);
	return SUCCESS;
    }

    // 修改密码
    public String updatePwd() {
	flag = false;
	Integer userId = (Integer) super.session.get("userId");
	String result = userService.changePassword(userId, password);
	if (result.equals("success")) {
	    flag = true;
	}
	return SUCCESS;
    }

    // 密码重置
    public String userResetPwd() {
	flag = userService.resetPwd(user.getUserId());
	return "userResetPwd";
    }

    // 更新主题
    public String updateTheme() {
	flag = userService.updateTheme(user.getTheme(),
		(Integer) super.session.get("userId"));
	return SUCCESS;
    }

    /**********************************
     * 最新方法列表
     * 
     * @return
     */
    /**
     * 管理员登录
     * 
     * @return
     */
    public String adminLogin() {
	user = userService.login(user);
	if (user != null) {
	    super.session.put("userId", user.getUserId());
	    super.session.put("userName", user.getUsername());
	    super.session.put("userRole", user.getRole());
	    super.session.put("companyId", user.getCompanyId());
	    flag = true;
	} else {
	    flag = false;
	}
	return "adminLogin";
    }

    /**
     * 管理员退出
     * 
     * @return
     */
    public String logout() {
	super.session.clear();
	flag = true;
	return SUCCESS;
    }

    private int adminRole;

    public String getAdminiRole() {
	Object obj = super.session.get("userRole");
	if (null != obj) {
	    adminRole = (Integer) super.session.get("userRole");
	}
	return SUCCESS;
    }

    public int getAdminRole() {
	return adminRole;
    }

    public void setAdminRole(int adminRole) {
	this.adminRole = adminRole;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public boolean isFlag() {
	return flag;
    }

    public void setFlag(boolean flag) {
	this.flag = flag;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public void setUserList(List<User> userList) {
	this.userList = userList;
    }

    public List<User> getUserList() {
	return userList;
    }

    public Map<String, List<Map<String, String>>> getUserMap() {
	return userMap;
    }

    public void setUserMap(Map<String, List<Map<String, String>>> userMap) {
	this.userMap = userMap;
    }

    public List<Map<String, String>> getUserMapList() {
	return userMapList;
    }

    public void setUserMapList(List<Map<String, String>> userMapList) {
	this.userMapList = userMapList;
    }

    public Map<String, List<Map<String, String>>> getUserMapNotSharedPro() {
	return userMapNotSharedPro;
    }

    public void setUserMapNotSharedPro(
	    Map<String, List<Map<String, String>>> userMapNotSharedPro) {
	this.userMapNotSharedPro = userMapNotSharedPro;
    }

    public int getRole() {
	return role;
    }

    public void setRole(int role) {
	this.role = role;
    }

    public int getProjectId() {
	return projectId;
    }

    public void setProjectId(int projectId) {
	this.projectId = projectId;
    }

    public Page getPage() {
	return page;
    }

    public void setPage(Page page) {
	this.page = page;
    }

    public void setUserPageList(PageList<User> userPageList) {
	this.userPageList = userPageList;
    }

    public PageList<User> getUserPageList() {
	return userPageList;
    }

    public int getUserId() {
	return userId;
    }

    public void setUserId(int userId) {
	this.userId = userId;
    }

    public String getKaptchaCode() {
	return kaptchaCode;
    }

    public void setKaptchaCode(String kaptchaCode) {
	this.kaptchaCode = kaptchaCode;
    }

    public int getResult() {
	return result;
    }

    public void setResult(int result) {
	this.result = result;
    }

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public PageList<Behavior> getBehaviorList() {
	return behaviorList;
    }

    public void setBehaviorList(PageList<Behavior> behaviorList) {
	this.behaviorList = behaviorList;
    }

    public int getNotify() {
	return notify;
    }

    public void setNotify(int notify) {
	this.notify = notify;
    }

    public String[] getEmailArray() {
	return emailArray;
    }

    public void setEmailArray(String[] emailArray) {
	this.emailArray = emailArray;
    }

    public List<Company> getComList() {
	return comList;
    }

    public void setComList(List<Company> comList) {
	this.comList = comList;
    }

    public List<Dept> getDeptList() {
	return deptList;
    }

    public void setDeptList(List<Dept> deptList) {
	this.deptList = deptList;
    }

    public int getDeptId() {
	return deptId;
    }

    public void setDeptId(int deptId) {
	this.deptId = deptId;
    }

    public String getContext() {
	return context;
    }

    public void setContext(String context) {
	this.context = context;
    }
}