package com.celloud.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.celloud.sdo.DataFile;
import com.celloud.sdo.Entry;
import com.celloud.sdo.LoginLog;
import com.celloud.sdo.PublicKey;
import com.celloud.sdo.App;
import com.celloud.sdo.User;
import com.celloud.service.UserService;
import com.celloud.utils.MD5Util;
import com.celloud.utils.RSAUtil;
import com.google.inject.Inject;

@ParentPackage("json-default")
@Action("user")
@Results({ @Result(name = "success", location = "../../index.jsp"),
		@Result(name = "input", location = "../../login.jsp"),
		@Result(name = "userList", location = "../../pages/userList.jsp"),
		@Result(name = "oneUser", location = "../../pages/userOne.jsp"),
		@Result(name = "toUserActivity", location = "../../pages/userActivity.jsp"),
		@Result(name = "init", type = "json", params = { "root", "publicKey" }),
		@Result(name = "output", type = "json", params = { "root", "list" }),

		@Result(name = "userListJson", type = "json", params = { "root", "userList" }),

		@Result(name = "EntryList", type = "json", params = { "root", "entryList" }),

		/****/
		@Result(name = "DataList", type = "json", params = { "root", "dataList" }),
		@Result(name = "SoftwareList", type = "json", params = { "root", "softList" }),
		/** 用户登陆排序 */
		@Result(name = "LoginList", type = "json", params = { "root", "loginList" }),

})
public class UserAction extends BaseAction {
	Logger log = Logger.getLogger(UserAction.class);
	private static final long serialVersionUID = 1L;
	@Inject
	private UserService userService;
	private User user;
	private PublicKey publicKey;
	private List<Map<String, Object>> list;
	private List<User> userList;
	private List<LoginLog> loginList;
	private List<App> softList;
	private List<DataFile> dataList;
	private List<Entry> entryList;
	private Date startDate;
	private Date endDate;
	private String orderType; // 1文件数量,2数据大小
	private int topN = 10;
	private String groupType; // 按周、月分组、
	private List<Integer> userIds;

	public String login() {
		String userName = user.getUsername();
		String kaptchaExpected = (String) session.get(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (kaptchaExpected == null) {
			request.setAttribute("info", "验证码错误，请重新输入！");
			return INPUT;
		} else {
			boolean flag = (kaptchaExpected.equalsIgnoreCase(user.getKaptchaCode()));
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
		log.info(user);
		/***
		 * zuo role枚举 0:用户 1:大客户 2:超级管理员 3:系统管理员
		 */
		if (user != null) {
			if (user.getRole() >= 1) {
				super.session.put("userName", user.getUsername());
				super.session.put("userId", user.getUser_id());
				super.session.put(User.USER_ROLE, user.getRole());
				super.session.put("deptId", user.getDeptId());
				super.session.put("email", user.getEmail());
				super.session.put("companyId", user.getCompany_id());
				log.info(user.getUsername() + "用户登录成功");

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

	public String userLoginTop() {
		loginList = userService.getLoginTop(groupType, topN, startDate, endDate);
		return "LoginList";
	}

	public String userDataTop() {
		dataList = userService.getUserDataTop(groupType, topN, startDate, endDate);
		return "DataList";
	}

	public String toUserActivity() {
		Integer cmpId = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		userList = userService.getUserByCompany(cmpId, role);
		log.info(userList.size());
		return "toUserActivity";
	}

	/**
	 * 用户活跃度统计、用户登陆次数按周统计
	 * 
	 * @return
	 */
	public String getLoginSortWeek() {
		Integer cmpId = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		loginList = userService.getLoginUserSortWeek(cmpId, role, userIds, startDate, endDate);
		return "LoginList";
	}

	/**
	 * 用户活跃度、用户登陆次数按月统计
	 * 
	 * @return
	 */
	public String getLoginSortMonth() {
		Integer cmpId = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		loginList = userService.getLoginUserSortMonth(cmpId, role, userIds, startDate, endDate);
		return "LoginList";
	}

	/**
	 * 活跃度统计用户文件数量、大小按周统计
	 * 
	 * @return
	 */
	public String activityFileWeek() {
		Integer cmpId = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		dataList = userService.getFileInWeekDate(cmpId, role, userIds, startDate, endDate);
		return "DataList";
	}

	/**
	 * 活跃度统计用户文件数量、大小按月统计
	 * 
	 * @return
	 */
	public String activityFileMonth() {
		Integer cmpId = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		dataList = userService.getFileMonthInDate(cmpId, role, userIds, startDate, endDate);
		return "DataList";
	}

	/**
	 * 活跃度统计 用户运行app按周统计
	 * 
	 * @return
	 */
	public String appRunInWeek() {
		Integer cmpId = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		softList = userService.getAppRunInWeek(cmpId, role, userIds, startDate, endDate);
		return "SoftwareList";
	}

	/**
	 * 活跃度统计 用户运行app次数按月统计
	 * 
	 * @return
	 */
	public String appRunInMonth() {
		Integer cmpId = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		softList = userService.getAppRunInMonth(cmpId, role, userIds, startDate, endDate);
		return "SoftwareList";
	}

	public String uploadFileMonth() {

		dataList = userService.getUploadFileMonth(user.getUser_id());
		return "DataList";
	}

	public String uploadFileWeek() {
		dataList = userService.getUploadFileWeek(user.getUser_id());
		return "DataList";
	}

	/**
	 * 查询单个用户运行各App次数
	 * 
	 * @return
	 */
	public String userRunApp() {
		softList = userService.getAppRunTimesByUId(user.getUser_id());
		return "SoftwareList";
	}

	/**
	 * 按月统计各月App运行情况
	 * 
	 * @return
	 */
	public String userRunAppInMonth() {
		entryList = userService.getAppRunEachMonthByUId(user.getUser_id());
		return "EntryList";
	}

	/**
	 * 按周统计各月App运行情况
	 * 
	 * @return
	 */
	public String userRunAppInWeek() {
		entryList = userService.getAppRunEachWeekByUId(user.getUser_id());
		return "EntryList";
	}

	public List<Integer> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}

	/**
	 * 查询用户日志
	 * 
	 * @return
	 */
	public String getLoginLog() {
		loginList = userService.getLogById(user.getUsername());
		return "LoginList";
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
	 * 
	 * @return
	 */
	public String getBigUsersUser() {
		Integer companyId = (Integer) getCid();
		list = userService.getBigUsersUser(companyId);
		return "output";
	}

	/**
	 * 获取大客户下用户的数据信息
	 * 
	 * @return
	 */
	public String userList() {
		Integer companyId = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		userList = userService.getUserListByBigCom(companyId, role, "fileNum");
		return "userList";
	}

	/**
	 * 获取大客户下用户的数据信息
	 * 
	 * @return JSON
	 */
	public String getUserListByBigUserJson() {
		Integer companyId = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		userList = userService.getUserListByBigCom(companyId, role, orderType);
		return "userListJson";
	}

	public String getUserById() {
		user = userService.getUserById(user.getUser_id());
		loginList = userService.getLogById(user.getUsername());
		return "oneUser";
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

	public int getTopN() {
		return topN;
	}

	public void setTopN(int topN) {
		this.topN = topN;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<LoginLog> getLoginList() {
		return loginList;
	}

	public void setLoginList(List<LoginLog> loginList) {
		this.loginList = loginList;
	}

	public List<App> getSoftList() {
		return softList;
	}

	public void setSoftList(List<App> softList) {
		this.softList = softList;
	}

	public List<DataFile> getDataList() {
		return dataList;
	}

	public void setDataList(List<DataFile> dataList) {
		this.dataList = dataList;
	}

	public List<Entry> getEntryList() {
		return entryList;
	}

	public void setEntryList(List<Entry> entryList) {
		this.entryList = entryList;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		SimpleDateFormat sdf = null;
		if (startDate != null && startDate.length() == 7)// yyyy-MM
			sdf = new SimpleDateFormat("yyyy-MM");
		else if (startDate != null & startDate.length() == 10)
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.startDate = sdf.parse(startDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void setEndDate(String endDate) {
		SimpleDateFormat sdf = null;
		if (endDate != null && endDate.length() == 7)// yyyy-MM
			sdf = new SimpleDateFormat("yyyy-MM");
		else if (endDate != null & endDate.length() == 10)
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.endDate = sdf.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

}
