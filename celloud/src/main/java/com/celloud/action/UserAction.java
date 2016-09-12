package com.celloud.action;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.alimail.AliEmail;
import com.celloud.alimail.AliEmailUtils;
import com.celloud.alimail.AliSubstitution;
import com.celloud.constants.CompanyConstants;
import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.message.category.MessageCategoryCode;
import com.celloud.message.category.MessageCategoryUtils;
import com.celloud.model.mysql.ActionLog;
import com.celloud.model.mysql.Company;
import com.celloud.model.mysql.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.sendcloud.EmailParams;
import com.celloud.sendcloud.EmailType;
import com.celloud.service.ActionLogService;
import com.celloud.service.CompanyService;
import com.celloud.service.UserService;
import com.celloud.utils.DateUtil;
import com.celloud.utils.MD5Util;
import com.celloud.utils.ResetPwdUtils;
import com.celloud.utils.Response;
import com.celloud.wechat.ParamFormat;
import com.celloud.wechat.ParamFormat.Param;
import com.celloud.wechat.WechatParams;
import com.celloud.wechat.WechatUtils;

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
	private AliEmailUtils emailUtils;
	@Resource
	private WechatUtils wechatUtils;
	@Resource
	private MessageCategoryUtils mcu;
    @Resource
    private CompanyService companyService;
	private static final Response EMAIL_IN_USE = new Response("202", "邮箱已存在");
	private static final Response UPDATE_BASEINFO_FAIL = new Response("修改用户信息失败");
	private static final Response UPDATE_PASSWORD_FAIL = new Response("修改用户密码失败");
	private static final Response WRONG_PASSWORD = new Response("203", "原始密码错误");
    private Logger logger = LoggerFactory.getLogger(UserAction.class);

    /**
     * 
     * @author MQ
     * @date 2016年8月30日下午3:37:41
     * @description 更新公司logo
     * @param company
     * @return
     *
     */
    @RequestMapping("setCompanyIcon")
    @ResponseBody
    public int setCompanyIcon(Company company) {
        company.setCompanyId(ConstantsData.getLoginCompanyId());
        return companyService.updateCompanyIcon(company);
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月30日下午3:07:40
     * @description 获取公司的icon名称
     * @return
     *
     */
    @RequestMapping("getCompanyIcon")
    @ResponseBody
    public Company getCompanyIcon() {
        Company company = companyService.selectByPrimaryKey(ConstantsData.getLoginCompanyId());
        if (StringUtils.isBlank(company.getCompanyIcon())) {
            company.setCompanyIcon("");
        }
        return company;
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月30日下午3:00:21
     * @description 获取已保存的公司logo
     * @param file
     *
     */
    @RequestMapping(value = "icon")
    public ResponseEntity<byte[]> attach(String file) throws IOException {
        String path = CompanyConstants.getCompanyIconPath() + File.separator + file;
        File targetFile = new File(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), headers, HttpStatus.OK);
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月30日下午3:00:06
     * @description 获取已上传未保存的公司logo
     * @param file
     *
     */
    @RequestMapping(value = "icon/temp")
    public ResponseEntity<byte[]> attachTemp(String file) throws IOException {
        String path = CompanyConstants.getCompanyIconTempPath() + File.separator + file;
        File targetFile = new File(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), headers, HttpStatus.OK);
    }

	/**
     * 
     * @author MQ
     * @date 2016年8月30日下午2:13:07
     * @description
     * @param file
     * @param fileName
     * @return
     *
     */
    @RequestMapping(value = "uploadCompanyIcon")
    @ResponseBody
    public String attach(@RequestParam("file") CommonsMultipartFile file, String fileName) {
        String type = fileName.substring(fileName.lastIndexOf("."));
        File targetFile = new File(CompanyConstants.getCompanyIconTempPath(), new ObjectId().toString() + type);
        targetFile.getParentFile().mkdirs();
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            logger.error("用户上传公司logo失败：{}", fileName, e);
        }
        return targetFile.getName();
    }

    /**
     * 跳转到账号管理菜单，并初始化数据
     * 
     * @return
     */
	@RequestMapping("info")
    @ResponseBody
    public User info() {
		int userId = ConstantsData.getLoginUserId();
        return userService.selectUserById(userId);
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
		Param params = ParamFormat.param()
				.set(WechatParams.PWD_UPDATE.first.name(), "您好，" + user.getUsername() + "：", "#222222")
				.set(WechatParams.PWD_UPDATE.productName.name(), "平台账号", null)
				.set(WechatParams.PWD_UPDATE.time.name(), DateUtil.getDateToString(DateUtil.YMDHMS), null);
		mcu.sendMessage(user.getUserId(), MessageCategoryCode.UPDATEPWD, null, params, null);
		return result > 0 ? Response.SAVE_SUCCESS : UPDATE_PASSWORD_FAIL;
	}

	/**
	 * 获取用户操作日志
	 * 
	 * @return
	 */
	@RequestMapping("logInfo")
    @ResponseBody
    public PageList<ActionLog> logInfo(Page page) {
		if (page == null) {
			page = new Page();
		}
        return logService.findLogs(ConstantsData.getLoginUserId(), page);
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

		String url = ResetPwdUtils.updateEmailPath.replaceAll("resetEmailUsername", user.getUsername())
				.replaceAll("resetcode", randomCode);

		AliEmail aliEmail = AliEmail.template(EmailType.CONFIRM_OLD_EMAIL)
				.substitutionVars(AliSubstitution.sub().set(EmailParams.CONFIRM_OLD_EMAIL.url.name(), url));
		emailUtils.simpleSend(aliEmail, email);
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
	 * 获取当前用户的账户余额
	 * 
	 * @return
	 */
	@RequestMapping("balances")
	@ResponseBody
	public BigDecimal getBalances() {
		User user = userService.selectUserById(ConstantsData.getLoginUserId());
		return user.getBalances();
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
		userService.insertFindPwdInfo(user.getUserId(), randomCode);

		String url = ResetPwdUtils.toActiveEmailPath.replaceAll("username", user.getUsername())
				.replaceAll("resetcode", randomCode).replaceAll("newemail", email);
		AliEmail aliEmail = AliEmail.template(EmailType.CONFIRM_NEW_EMAIL)
				.substitutionVars(AliSubstitution.sub().set(EmailParams.CONFIRM_NEW_EMAIL.url.name(), url));
		emailUtils.simpleSend(aliEmail, email);
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
		userService.updateUserEmail(user);
		return mv.addObject("info", "邮箱修改成功");
	}
}
