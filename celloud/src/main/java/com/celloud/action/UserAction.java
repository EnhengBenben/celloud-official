package com.celloud.action;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.celloud.alidayu.AlidayuConfig;
import com.celloud.alimail.AliEmail;
import com.celloud.alimail.AliEmailUtils;
import com.celloud.alimail.AliSubstitution;
import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.IconConstants;
import com.celloud.constants.UserSecRole;
import com.celloud.message.category.MessageCategoryCode;
import com.celloud.message.category.MessageCategoryUtils;
import com.celloud.model.mongo.UserCaptcha;
import com.celloud.model.mysql.ActionLog;
import com.celloud.model.mysql.Company;
import com.celloud.model.mysql.SecRole;
import com.celloud.model.mysql.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.sendcloud.EmailParams;
import com.celloud.sendcloud.EmailType;
import com.celloud.service.ActionLogService;
import com.celloud.service.CompanyService;
import com.celloud.service.CustomerService;
import com.celloud.service.SecRoleService;
import com.celloud.service.UserService;
import com.celloud.utils.DataUtil;
import com.celloud.utils.DateUtil;
import com.celloud.utils.MD5Util;
import com.celloud.utils.ResetPwdUtils;
import com.celloud.utils.Response;
import com.celloud.wechat.ParamFormat;
import com.celloud.wechat.ParamFormat.Param;
import com.celloud.wechat.WechatUtils;
import com.celloud.wechat.constant.WechatParams;

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
	private SecRoleService secRoleService;
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
    @Autowired
    private CustomerService customerService;
	private static final Response EMAIL_IN_USE = new Response("202", "邮箱已存在");
	private static final Response UPDATE_BASEINFO_FAIL = new Response("修改用户信息失败");
	private static final Response UPDATE_PASSWORD_FAIL = new Response("修改用户密码失败");
	private static final Response WRONG_PASSWORD = new Response("203", "原始密码错误");
	private Logger logger = LoggerFactory.getLogger(UserAction.class);

	@RequestMapping(value = "toAddRole", method = RequestMethod.GET)
	@ResponseBody
	public List<SecRole> toAddRole(Integer userId) {
		Integer authFrom = ConstantsData.getLoginUserId();
		List<SecRole> all = userService.getRolesByUserId(authFrom);
		for (int i = 0; i < all.size(); i++) {
			if (all.get(i).getId().equals(UserSecRole.PLATFORM)) {
				all.remove(i);
			}
		}
		List<SecRole> have = userService.getRolesByUserId(userId);
		all.removeAll(have);
		return all;
	}

	@RequestMapping(value = "addRole", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> addRole(Integer userId, Integer[] roles) {
		secRoleService.insertUserRoles(userId, roles, ConstantsData.getLoginUserId());
		return ResponseEntity.ok(new Response("200", "追加成功"));
	}

	@RequestMapping(value = "toRemoveRole", method = RequestMethod.GET)
	@ResponseBody
	public List<SecRole> toRemoveRole(Integer userId) {
		Integer authFrom = ConstantsData.getLoginUserId();
		return userService.getRoles(userId, authFrom);
	}

	@RequestMapping(value = "removeRole", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> removeRole(Integer userId, Integer[] roles) {
		secRoleService.deleteByAuthFrom(userId, roles, ConstantsData.getLoginUserId());
		return ResponseEntity.ok(new Response("200", "删除成功"));
	}

    /**
     * 
     * @description 平台用户认证手机发送验证码
     * @author miaoqi
     * @date 2016年11月29日上午10:54:40
     *
     * @param cellphone
     * @return
     */
    @RequestMapping(value = "sendCaptcha", method = RequestMethod.POST)
    public ResponseEntity<Void> sendCapcha(String cellphone) {
        Integer role = ConstantsData.getLoginUser().getRole();
        if (role.intValue() == 5) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        logger.info("用户 {} 申请验证手机号码 {}", ConstantsData.getLoginUserId(), cellphone);
        if (StringUtils.isEmpty(cellphone)) {
            logger.info("手机号码 {} 格式有误", cellphone);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        // 校验手机号
        String regex = "^\\d{11}$";
        Pattern patter = Pattern.compile(regex);
        Matcher matcher = patter.matcher(cellphone);
        if (!matcher.find()) {
            // 手机号错误
            logger.info("手机号码 {} 格式有误", cellphone);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        // 获取验证码
        String captcha = DataUtil.getCapchaRandom();
        // 新增或更新验证码
        Boolean flag = customerService.addOrUpdateUserCaptcha(cellphone, captcha);
        if (flag) {
            logger.info("验证码 {} 发送成功", captcha);
            // 200 发送成功
            return ResponseEntity.ok().build();
        }
        logger.info("验证码 {} 发送失败", captcha);
        // 500 发送失败
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 
     * @description 认证手机号码
     * @author miaoqi
     * @date 2016年11月29日下午12:54:50
     *
     * @param captcha
     * @return
     */
    @RequestMapping(value = "authenticationCellphone", method = RequestMethod.POST)
    public ResponseEntity<Response> authenticationCellphone(String cellphone, String captcha) {
        Integer role = ConstantsData.getLoginUser().getRole();
        if (role.intValue() == 5) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        // 参数校验
        logger.info("用户 {} 进行手机号认证, 手机号 = {}, 验证码 = {}", ConstantsData.getLoginUserId(), cellphone, captcha);
        if (StringUtils.isEmpty(cellphone)) {
            logger.info("手机号码格式有误");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("400", "手机号码格式有误"));
        }
        if (StringUtils.isEmpty(captcha)) {
            logger.info("验证码格式有误");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("400", "验证码格式有误"));
        }
        // 1. 根据手机号从mongo中查询用户的验证码信息
        UserCaptcha userCaptcha = customerService.getUserCaptchaByCellphone(cellphone);
        if (userCaptcha != null) {
            // 2. 获取创建时间
            DateTime createDate = new DateTime(userCaptcha.getCreateDate());
            // 3. 创建时间 + 1分钟 大于当前时间, 代表没有过期, 并且验证码相等
            if (createDate.plusMinutes(AlidayuConfig.captcha_expire_time).isAfterNow()) {
                if (userCaptcha.getCaptcha().equals(captcha)) {
                    // 4. 根据当前用户id查询
                    User mongoUser = userService.queryFromMongo(ConstantsData.getLoginUserId());
                    User sqlUser = ConstantsData.getLoginUser();
                    if (mongoUser != null) { // 更新该用户
                        mongoUser.setCellphone(cellphone);
                        userService.updateToMongo(mongoUser);
                    } else { // 插入该用户
                        sqlUser.setCellphone(cellphone);
                        userService.saveToMongo(sqlUser);
                    }
                    // 5. 更新mysql数据库
                    sqlUser = new User();
                    sqlUser.setUserId(ConstantsData.getLoginUserId());
                    sqlUser.setCellphone(cellphone);
                    userService.updateBySelective(sqlUser);
                    customerService.removeUserCaptchaByCellphone(cellphone);
                    logger.info("用户 {} 更新手机号成功", ConstantsData.getLoginUserId());
                    return ResponseEntity.ok(new Response("200", "更新成功"));
                } else {
                    logger.info("用户 {} 验证码有误", ConstantsData.getLoginUserId());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("500", "验证码错误"));
                }
            }
        }
        logger.info("用户 {} 尚未获取验证码或验证码已超时", ConstantsData.getLoginUserId());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("500", "您尚未获取验证码或验证码已超时"));
    }

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
	 * 查询用户的所有角色
	 * 
	 * @return
	 * @author lin
	 * @date 2016年12月20日下午4:21:06
	 */
	@RequestMapping("findRoles")
	@ResponseBody
	public List<SecRole> findRoles() {
		Integer userId = ConstantsData.getLoginUserId();
		return userService.getRolesByUserId(userId);
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
		String path = IconConstants.getCompanyPath(file);
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
		String path = IconConstants.getTempPath(file);
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
		File targetFile = new File(IconConstants.getTempPath(new ObjectId().toString() + type));
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
        logger.info("用户{}修改用户基本信息", ConstantsData.getLoginUserName());
         
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
		return Response.SUCCESS_SAVE(user);
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
        if (user.getRole().intValue() == 5) {
            return new Response("5", "角色错误");
        }
		user.setPassword(MD5Util.getMD5(oldPassword));
		if (userService.login(user) == null) {
			return WRONG_PASSWORD;
		}
		int result = userService.updatePassword(user.getUserId(), newPassword);
		Param params = ParamFormat.param()
				.set(WechatParams.PWD_UPDATE.first.name(), "您好，" + user.getUsername() + "：", "#222222")
				.set(WechatParams.PWD_UPDATE.productName.name(), "平台账号", null)
				.set(WechatParams.PWD_UPDATE.time.name(), DateUtil.getDateToString(DateUtil.YMDHMS), null);
		mcu.sendMessage(user.getUserId(), MessageCategoryCode.UPDATEPWD, null, params, null, null);
		return result > 0 ? Response.SUCCESS_SAVE() : UPDATE_PASSWORD_FAIL;
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
        Integer role = ConstantsData.getLoginUser().getRole();
        if(role.intValue() == 5){
            return -1;
        }
		if (StringUtils.isBlank(email)) {
			return 1;// error
		}
		String randomCode = MD5Util.getMD5(String.valueOf(new Date().getTime()));
		User user = ConstantsData.getLoginUser();
		userService.insertFindPwdInfo(user.getUserId(), randomCode);

		String url = ResetPwdUtils.updateEmailPath.replaceAll("resetEmailUsername", user.getUsername())
				.replaceAll("resetcode", randomCode);

		AliEmail aliEmail = AliEmail.template(EmailType.CONFIRM_OLD_EMAIL)
				.substitutionVars(AliSubstitution.sub()
						.set(EmailParams.CONFIRM_OLD_EMAIL.home.name(), ConstantsData.getContextUrl())
						.set(EmailParams.CONFIRM_OLD_EMAIL.url.name(), url));
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
     * 
     * @description 医院管理员添加用户, 所有信息与管理员保持一致
     * @author miaoqi
     * @date 2016年10月28日下午1:44:14
     *
     * @param emailArray
     */
    @RequestMapping("/sendRegistEmail")
	public ResponseEntity<Map<String, String>> sendEmail(String email, String kaptcha, Integer[] apps,
			Integer[] roles) {
        logger.info("医院管理员 {} 发送注册邮件 email = {}, kaptchat = {}", ConstantsData.getLoginUserId(), email, kaptcha);
        Boolean flag = true;
        Map<String, String> errorMap = new HashMap<String, String>();
        // 校验email是否合法
        if (checkEmail(email, null) == 0) { // 邮箱已被使用
            logger.info("邮箱已被占用 email = {}", email);
            flag = false;
            errorMap.put("emailError", "邮箱已被使用");
        }
        // 校验验证码
        Subject subject = SecurityUtils.getSubject();
        // 获取Shiro的session
        Session session = subject.getSession();
        if (!checkKaptcha(kaptcha, session)) {
            flag = false;
            errorMap.put("kaptchaError", "验证码错误");
        }
        // 邮箱和验证码均合法
        if (flag) {
			flag = userService.sendRegisterEmail(email, apps, roles);
            if (flag) {
                logger.info("注册邮件发送成功 email = {}", email);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            logger.info("注册邮件发送失败 email = {}", email);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        }

    }

    /**
     * 发送注册手机验证码
     * 
     * @param email
     * @param captcha
     * @param apps
     * @param roles
     * @return
     * @author leamo
     * @date 2017年2月13日 下午3:34:03
     */
    @RequestMapping("/sendCellphoneCaptcha")
    public ResponseEntity<Map<String, String>> sendCellphoneCaptcha(String cellphone, String captcha, String truename,
            Integer[] apps, Integer[] roles) {
        logger.info("医院管理员 {} 发送注册短信 cellphone = {}, captcha = {}", ConstantsData.getLoginUserId(), cellphone,
                captcha);
        Boolean flag = true;
        Map<String, String> errorMap = new HashMap<String, String>();
        // 校验手机号是否已注册
        if (userService.isCellphoneInUse(cellphone)) {
            logger.info("电话号码已被占用 cellphone = {}", cellphone);
            flag = false;
            errorMap.put("cellphoneError", "电话号码已被使用");
        }
        // 校验验证码
        Subject subject = SecurityUtils.getSubject();
        // 获取Shiro的session
        Session session = subject.getSession();
        if (!checkKaptcha(captcha, session)) {
            flag = false;
            errorMap.put("kaptchaError", "验证码错误");
        }
        // 邮箱和验证码均合法
        if (flag) {
            if (userService.sendRegisterSms(cellphone, truename, apps, roles)) {
                logger.info("注册短信发送成功 cellphone = {}", cellphone);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            logger.info("注册短信发送失败 cellphone = {}", cellphone);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
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
				.substitutionVars(AliSubstitution.sub()
						.set(EmailParams.CONFIRM_NEW_EMAIL.home.name(), ConstantsData.getContextUrl())
						.set(EmailParams.CONFIRM_NEW_EMAIL.url.name(), url));
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

    public boolean checkKaptcha(String kaptcha, Session session) {
        String kaptchaExpected = (String) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if (kaptchaExpected == null || !kaptchaExpected.equalsIgnoreCase(kaptcha)) {
            logger.info("医院管理员新增用户验证码错误：param : {} \t session : {}", kaptcha, kaptchaExpected);
            return false;
        }
        return true;
    }

	@RequestMapping(value = "getLoginUser", method = RequestMethod.GET)
	@ResponseBody
	public User getLoginUser() {
		return ConstantsData.getLoginUser();
	}
}
