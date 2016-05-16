package com.celloud.manager.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.manager.constants.ConstantsData;
import com.celloud.manager.model.App;
import com.celloud.manager.model.Company;
import com.celloud.manager.model.Dept;
import com.celloud.manager.model.User;
import com.celloud.manager.page.Page;
import com.celloud.manager.page.PageList;
import com.celloud.manager.service.AppService;
import com.celloud.manager.service.CompanyService;
import com.celloud.manager.service.DeptService;
import com.celloud.manager.service.UserService;
import com.celloud.manager.utils.Base64Util;
import com.celloud.manager.utils.MD5Util;

/**
 * 
 *
 * @author han
 * @date 2016年1月27日 下午4:11:05
 */
@Controller
public class UserAction {
    Logger logger = LoggerFactory.getLogger(UserAction.class);

    @Resource
    private UserService userService;
    @Resource
    private CompanyService companyService;
    @Resource
    private AppService appService;
    @Resource
    private DeptService deptService;

    @RequestMapping("user/userList")
    public ModelAndView getUserByPage(@RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int size, @RequestParam("searchFiled") String searchFiled,
            @RequestParam("keyword") String keyword) {
        ModelAndView mv = new ModelAndView("user/user_main");
        Page page = new Page(currentPage, size);
        if (!"username".equals(searchFiled) && !"email".equals(searchFiled)) {
            searchFiled = "username";
        }
        Integer companyId = ConstantsData.getLoginUser().getCompanyId();
        PageList<User> pageList = userService.getUserByPage(companyId, page, searchFiled,
                keyword != null ? keyword.trim() : keyword);
        mv.addObject("pageList", pageList);
        mv.addObject("searchFiled", searchFiled);
        mv.addObject("keyword", keyword);
        return mv;
    }

    @RequestMapping("user/toSendEmail")
    public ModelAndView toSendEmail() {
        ModelAndView mv = new ModelAndView("user/user_sendEmail");
        Integer appCompanyId = ConstantsData.getLoginUser().getCompanyId();
        List<App> appList = appService.getAppListByCompany(appCompanyId);
        mv.addObject("appList", appList);
        mv.addObject("appCompanyId", appCompanyId);
        return mv;
    }

    @ResponseBody
    @RequestMapping("user/getAppList")
    public List<App> getAppList(@RequestParam("companyId") int companyId) {
        return appService.getAppListByCompany(companyId);
    }

    @ResponseBody
    @RequestMapping("user/getDept")
    public List<Dept> getDeptByCompanyId(@RequestParam("companyId") int companyId) {
        return deptService.getDeptByCompanyId(companyId);
    }

    /**
     * 后台采用发邮件添加用户时检验邮箱是否已经添加
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("user/checkEmail")
    public String checkEmail(@RequestParam("emailArray") String[] emailArray) {
        StringBuffer sb = new StringBuffer();
        for (String email : emailArray) {
            int result = userService.isEmailInUse(email, null) ? 1 : 0;
            sb.append(result).append(",");
        }
        sb.delete(sb.length() - 1, sb.length());
        String email = sb.toString();
        return email;
    }

    /**
     * 发送添加用户邮件
     *
     * @param emailArray
     * @param deptId
     * @param companyId
     * @author han
     * @date 2016年1月28日 下午1:56:30
     */
    @ResponseBody
    @RequestMapping("user/sendEmail")
    public void sendEmail(@RequestParam("emailArray") String[] emailArray, @RequestParam("deptId") String deptId,
            @RequestParam("companyId") String companyId, @RequestParam("appCompanyId") Integer appCompanyId,
            @RequestParam("appIdArray") Integer[] appIdArray, @RequestParam("role") Integer role) {
        // companyId和deptId是字符串代表新增的医院和部门
        Integer sendCompanyId = null;
        Integer sendDeptId = null;
        try {
            sendCompanyId = Integer.parseInt(companyId);
        } catch (Exception e) {
            Company company = new Company();
            company.setCompanyName(companyId);
            company.setCreateDate(new Date());
            companyService.addCompany(company);
            sendCompanyId = company.getCompanyId();
        }
        try {
            sendDeptId = Integer.parseInt(deptId);
        } catch (Exception e) {
            Dept dept = new Dept();
            dept.setDeptName(deptId);
            dept.setCompanyId(sendCompanyId);
            deptService.addDept(dept);
            sendDeptId = dept.getDeptId();
        }
        userService.sendRegisterEmail(emailArray, sendDeptId, sendCompanyId, appCompanyId, appIdArray, role);
    }

    /**
     * 跳转用户注册页面
     *
     * @return
     * @author han
     * @date 2016年1月28日 下午1:56:41
     */
    @RequestMapping("addUser/{email}")
    public ModelAndView register(@PathVariable String email) {
        ModelAndView mv = new ModelAndView("user/user_add");
        System.out.println("email:" + email);
        String param = Base64Util.decrypt(email);
        System.out.println(param);
        logger.info("注册邮件{}", param);
        String p[] = param.split("/");
        if (p.length != 6) {
            mv.addObject("flag", false);
        } else {
            String code = p[1];
            User user = new User();
            user.setEmail(p[0]);
            user.setDeptId(Integer.parseInt(p[2]));
            user.setCompanyId(Integer.parseInt(p[3]));
            user.setRole(Integer.parseInt(p[5]));
            mv.addObject("appCompanyId", Integer.parseInt(p[4]));// 大客户Id
            mv.addObject("code", code);
            mv.addObject("user", user);
            mv.addObject("flag", userService.getValidate(p[0], code));
        }
        return mv;
    }

    /**
     * 用户名验重
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("addUser/checkUsername")
    public boolean checkUsername(@RequestParam("username") String username) {
        logger.info("校验用户名是否重复：{}", username);
        return userService.isUsernameInUse(username, null);
    }

    /**
     * 用户自主注册时校验邮箱
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("addUser/checkEmailOne")
    public boolean checkEmailOne(@RequestParam("email") String email) {
        return userService.isEmailInUse(email, null);
    }

    @ResponseBody
    @RequestMapping("addUser/addUser")
    public boolean addUser(User user, @RequestParam("code") String md5code,
            @RequestParam("appCompanyId") Integer appCompanyId) {
        logger.info("新增用户");
        return userService.addUser(user, md5code, appCompanyId);
    }

    /**
     * 用户修改密码
     * 
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping("user/updatePassword")
    @ResponseBody
    public int updatePassword(String oldPassword, String newPassword) {
        User user = ConstantsData.getLoginUser();
        user.setPassword(MD5Util.getMD5(oldPassword));
        if (userService.login(user) == null) {
            return 203;
        }
        int result = userService.updatePassword(user.getUserId(), newPassword);
        return result;
    }

    @RequestMapping("user/getDeptByCompanyId")
    @ResponseBody
    public List<Map<String, String>> getDeptByCompanyId(Integer companyId) {
        if (companyId == null) {
            List<Map<String, String>> deptList = new ArrayList<Map<String, String>>();
            Map<String, String> m1 = new HashMap<String, String>();
            m1.put("id", "生殖中心");
            m1.put("text", "生殖中心");
            Map<String, String> m2 = new HashMap<String, String>();
            m2.put("id", "检验科");
            m2.put("text", "检验科");
            Map<String, String> m3 = new HashMap<String, String>();
            m3.put("id", "技术部");
            m3.put("text", "技术部");
            deptList.add(m1);
            deptList.add(m2);
            deptList.add(m3);
            return deptList;
        } else {
            return deptService.getDeptToSelectByCompanyId(companyId);
        }
    }
}
