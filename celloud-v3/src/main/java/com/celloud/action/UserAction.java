package com.celloud.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.model.ActionLog;
import com.celloud.model.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.ActionLogService;
import com.celloud.service.UserService;
import com.celloud.utils.MD5Util;
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
        return Response.SAVE_SUCESS.setData(user);
    }

    @RequestMapping("updatePassword")
    @ResponseBody
    public Response updatePassword(String oldPassword, String newPassword) {
        User user = ConstantsData.getLoginUser();
        user.setPassword(MD5Util.getMD5(oldPassword));
        if (userService.login(user) == null) {
            return WRONG_PASSWORD;
        }
        int result = userService.updatePassword(user.getUserId(), MD5Util.getMD5(newPassword));
        return result > 0 ? Response.SAVE_SUCESS : UPDATE_PASSWORD_FAIL;
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
}
