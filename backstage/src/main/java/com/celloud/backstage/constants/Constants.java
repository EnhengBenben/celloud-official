package com.celloud.backstage.constants;

/**
 * 系统静态变量
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月23日 上午10:03:04
 */
public class Constants {
    /**
     * session中当前已登录用户
     */
    public static final String SESSION_LOGIN_USER = "loginUserInSession";
    /**
     * session中记录的账号密码错误次数
     */
    public static final String SESSION_FAILED_LOGIN_TIME = "failedLoginTimeInSession";
    /**
     * session中当前登录用户私钥
     */
    public static final String SESSION_RSA_PRIVATEKEY = "rsaPrivateKeyInSession";
    /**
     * cookie中的用户名
     */
    public static final String COOKIE_USERNAME = "username";
    /**
     * cookie中的密码
     */
    public static final String COOKIE_PASSWORD = "password";
    /**
     * cookie中的modulus
     */
    public static final String COOKIE_MODULUS = "modulus";

    /**
     * cookie的默认有效时间(天为单位)
     */
    public static final int COOKIE_MAX_AGE_DAY = 7;

    /**
     * 用户找回密码链接过期时长(小时为单位)
     */
    public static final int FIND_PASSWORD_EXPIRE_TIME = 1;
    /**
     * 用户找回密码功能，根据链接重置密码时，需要校验session中的userid是否与要修改的用户名是否对应
     */
    public static final String RESET_PASSWORD_USER_ID = "resetPasswordUserId";
    /**
     * 分页中，默认每页显示条数
     */
    public static final int DEFAULT_PAGE_SIZE = 10;
    /**
     * 默认的用户头像
     */
    public static final String DELAULT_AVATAR = "01";
    public static final String SYSTEM_PROPERTIES_FILE = "system.properties";
}
