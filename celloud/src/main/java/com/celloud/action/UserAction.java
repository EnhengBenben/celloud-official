package com.celloud.action;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.mail.EmailUtils;
import com.celloud.model.mysql.ActionLog;
import com.celloud.model.mysql.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.ActionLogService;
import com.celloud.service.UserService;
import com.celloud.utils.MD5Util;
import com.celloud.utils.ResetPwdUtils;
import com.celloud.utils.Response;

/**
 * 用户管理
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年1月6日 上午10:09:28
 */
@Controller
@RequestMapping("user")
public class UserAction {
    @Resource
    private UserService userService;
    @Resource
    private ActionLogService logService;
    @Resource
    private EmailUtils emailUtils;
    private static final Response EMAIL_IN_USE = new Response("202", "邮箱已存在");
    private static final Response UPDATE_BASEINFO_FAIL = new Response("修改用户信息失败");
    private static final Response UPDATE_PASSWORD_FAIL = new Response("修改用户密码失败");
    private static final Response WRONG_PASSWORD = new Response("203", "原始密码错误");

    /**
     * 跳转到账号管理菜单，并初始化数据
     * 
     * @return
     */
    @RequestMapping("info")
    public ModelAndView info() {
        int userId = ConstantsData.getLoginUserId();
        User user = userService.selectUserById(userId);
        return new ModelAndView("user/user_main").addObject("user", user);
    }

    /**
     * 修改用户基本信息
     * 
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("updateInfo")
    @ResponseBody
    public Response updateInfo(User user, HttpServletRequest request) {
        // 其他待修改字段可以在这里添加，不允许修改的字段一定要过滤掉
        user.setUserId(ConstantsData.getLoginUserId());
        if (userService.isEmailInUse(user.getEmail(), user.getUserId())) {
            return EMAIL_IN_USE;
        }
        int result = userService.updateUserInfo(user);
        if (result <= 0) {
            return UPDATE_BASEINFO_FAIL;
        }
        user = userService.selectUserById(user.getUserId());
        request.getSession().setAttribute(Constants.SESSION_LOGIN_USER, user);
        return Response.SAVE_SUCCESS.setData(user);
    }

    /**
     * 用户修改密码
     * 
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping("updatePassword")
    @ResponseBody
    public Response updatePassword(String oldPassword, String newPassword) {
        User user = ConstantsData.getLoginUser();
        user.setPassword(MD5Util.getMD5(oldPassword));
        if (userService.login(user) == null) {
            return WRONG_PASSWORD;
        }
        int result = userService.updatePassword(user.getUserId(), newPassword);
        return result > 0 ? Response.SAVE_SUCCESS : UPDATE_PASSWORD_FAIL;
    }

    /**
     * 获取用户操作日志
     * 
     * @return
     */
    @RequestMapping("logInfo")
    public ModelAndView logInfo(Page page) {
        if (page == null) {
            page = new Page();
        }
        PageList<ActionLog> pageList = logService.findLogs(ConstantsData.getLoginUserId(), page);
        return new ModelAndView("user/user_log_list").addObject("pageList", pageList);
    }
    
	/**
	 * 修改邮箱时向旧邮箱发送邮件
	 * 
	 * @param email
	 * @return
	 * @author lin
	 * @date 2016年4月18日下午4:05:38
	 */
	@RequestMapping("sendOldEmail")
	@ResponseBody
	public Integer sendOldEmail(String email) {
		if (StringUtils.isBlank(email)) {
			return 1;// error
		}
		String randomCode = MD5Util.getMD5(String.valueOf(new Date().getTime()));
		User user = ConstantsData.getLoginUser();
		userService.insertFindPwdInfo(user.getUserId(), randomCode);
		emailUtils.sendWithTitle(ResetPwdUtils.updateEmailTitle,
				ResetPwdUtils.updateEmailContent.replaceAll("url", ResetPwdUtils.updateEmailPath
						.replaceAll("resetEmailUsername", user.getUsername()).replaceAll("resetcode", randomCode)),
				email);
		return 0;
	}

	/**
	 * 校验邮箱是否存在
	 * 
	 * @param email
	 * @param userId
	 * @return
	 * @author lin
	 * @date 2016年4月18日下午4:59:40
	 */
	@RequestMapping("email/checkEmail")
	@ResponseBody
	public Integer checkEmail(String email, Integer userId) {
		if (userService.isEmailInUse(email, userId)) {
			return 0;
		} else {
			return 1;
		}
	}
	
	/**
	 * 修改邮箱时向新邮箱发送邮件
	 * 
	 * @param email
	 * @return
	 * @author lin
	 * @date 2016年4月18日下午4:05:38
	 */
	@RequestMapping("email/sendNewEmail")
	@ResponseBody
	public Integer sendNewEmail(Integer userId, String username, String randomCode, String oldEmail, String email) {
		User user = userService.getUserByFindPwd(username, randomCode);
		if (user == null || StringUtils.isBlank(email)) {
			return 1;// error
		}
		userService.cleanFindPwd(user.getUserId(), new Date());
		randomCode = MD5Util.getMD5(String.valueOf(new Date().getTime()));
		user = ConstantsData.getLoginUser();
		userService.insertFindPwdInfo(user.getUserId(), randomCode);
		emailUtils
				.sendWithTitle(ResetPwdUtils.toActiveEmailTitle,
						ResetPwdUtils.toActiveEmailContent
								.replaceAll("url",
										ResetPwdUtils.toActiveEmailPath.replaceAll("username", user.getUsername())
												.replaceAll("resetcode", randomCode).replaceAll("newemail", email)),
						email);
		return 0;
	}

	/**
	 * 激活邮箱
	 * 
	 * @param username
	 * @param email
	 * @param randomCode
	 * @return
	 * @author lin
	 * @date 2016年4月18日下午5:58:06
	 */
	@RequestMapping(value = "email/activeEmail/{username}/{email}/{randomCode}.html", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView activeEmail(@PathVariable String username, @PathVariable String email,
			@PathVariable String randomCode) {
		User user = userService.getUserByFindPwd(username, randomCode);
		ModelAndView mv = new ModelAndView("user/user_email_reset");
		if (user == null || StringUtils.isBlank(email)) {
			return mv.addObject("info", "请求不合法").addObject("forbidden", "forbidden");
		}
		userService.cleanFindPwd(user.getUserId(), new Date());
		user.setEmail(email);
		userService.updateUserInfo(user);
		return mv.addObject("info", "邮箱修改成功");
	}
}
