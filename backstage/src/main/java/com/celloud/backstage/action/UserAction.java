package com.celloud.backstage.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.backstage.constants.ConstantsData;
import com.celloud.backstage.model.App;
import com.celloud.backstage.model.Company;
import com.celloud.backstage.model.Dept;
import com.celloud.backstage.model.SecRole;
import com.celloud.backstage.model.User;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.AppService;
import com.celloud.backstage.service.CompanyService;
import com.celloud.backstage.service.DeptService;
import com.celloud.backstage.service.SecRoleService;
import com.celloud.backstage.service.UserService;
import com.celloud.backstage.utils.Base64Util;
import com.celloud.backstage.utils.MD5Util;

/**
 * 
 *
 * @author han
 * @date 2016年1月27日 下午4:11:05
 */
@Controller
public class UserAction {
    Logger logger=LoggerFactory.getLogger(UserAction.class);
    @Resource
    private UserService userService;
    @Resource
    private CompanyService companyService;
    @Resource
    private AppService appService;
    @Resource
    private DeptService deptService;
	@Resource
	private SecRoleService secRoleService;
    
    
    @RequestMapping("user/userList")
    public ModelAndView getUserByPage(@RequestParam(defaultValue = "1") int currentPage,
             @RequestParam(defaultValue = "10") int size,@RequestParam("searchFiled") String searchFiled,@RequestParam("keyword") String keyword){
         ModelAndView mv=new ModelAndView("user/user_main");
         Page page = new Page(currentPage, size);
         if(!"username".equals(searchFiled)&&!"email".equals(searchFiled)){
             searchFiled="username";
         }
         PageList<User> pageList=userService.getUserByPage(page,searchFiled,keyword!=null?keyword.trim():keyword);
         mv.addObject("pageList",pageList);
         mv.addObject("searchFiled",searchFiled);
         mv.addObject("keyword",keyword);
         return mv;
     }
    @RequestMapping("user/toSendEmail")
    public ModelAndView toSendEmail(){
        ModelAndView mv=new ModelAndView("user/user_sendEmail");
        List<Company> companyList=companyService.getAllCompany();
		List<Company> companyAppList = companyService.getAllCompanyHaveApp();
        List<App> appPublicList=appService.getAppListPulbicAdded();
		List<SecRole> roleList = secRoleService.selectAll();
        mv.addObject("companyList", companyList);
		mv.addObject("companyAppList", companyAppList);
        mv.addObject("publicApp", appPublicList);
		mv.addObject("roleList", roleList);
        return mv;
    }
    
    @ResponseBody
    @RequestMapping("user/getAppList")
    public List<App> getAppList(@RequestParam("companyId") int companyId){
       return appService.getAppListByCompany(companyId);
    }
    @ResponseBody
    @RequestMapping("user/getDept")
    public List<Dept> getDeptByCompanyId(@RequestParam("companyId") int companyId){
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
            int result = userService.isEmailInUse(email,null) ? 1 : 0;
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
    public void sendEmail(@RequestParam("emailArray") String[] emailArray,@RequestParam("deptId") Integer deptId,
            @RequestParam("companyId") Integer companyId,@RequestParam("appCompanyId") Integer appCompanyId,
            @RequestParam("appIdArray") Integer[] appIdArray,@RequestParam("role") Integer role) {
        userService.sendRegisterEmail(emailArray, deptId, companyId, appCompanyId, appIdArray,role);
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
        ModelAndView mv=new ModelAndView("user/user_add");
        String param = Base64Util.decrypt(email);
        System.out.println(param);
        logger.info("注册邮件{}",param);
        String p[] = param.split("/");
        if (p.length != 6) {
            mv.addObject("flag", false);
        } else {
            String code=p[1];
            User user = new User();
            user.setEmail(p[0]);
            user.setDeptId(Integer.parseInt(p[2]));
            user.setCompanyId(Integer.parseInt(p[3]));
            user.setRole(Integer.parseInt(p[5]));
            mv.addObject("appCompanyId",Integer.parseInt(p[4]));//大客户Id
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
    public boolean addUser(User user,@RequestParam("code") String md5code,@RequestParam("appCompanyId") Integer appCompanyId) {
        logger.info("新增用户");
        return userService.addUser(user,md5code,appCompanyId);
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
}
