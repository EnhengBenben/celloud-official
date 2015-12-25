package com.celloud.constants;

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
}
