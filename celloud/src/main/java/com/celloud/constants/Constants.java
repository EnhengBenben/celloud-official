package com.celloud.constants;

import java.util.HashMap;
import java.util.Map;

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
	 * 消息中心设置
	 */
	public static final String MESSAGE_CATEGORY = "message_category";
	/**
	 * 已登录用户的角色
	 */
	public static final String SESSION_LOGIN_USER_ROLES = "loginUsersRolesInSession";
	/**
	 * 已登录用户的权限
	 */
	public static final String SESSION_LOGIN_USER_PERMISSIONS = "loginUsersPermissionsInSession";
	/**
	 * session中当前登录用户私钥
	 */
	public static final String SESSION_RSA_PRIVATEKEY = "rsaPrivateKeyInSession";
	/**
	 * 微信openID
	 */
	public static final String SESSION_WECHAT_OPENID = "wechatOpenId";
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
	 * 微信二维码过期时间（min）
	 */
	public static final int WECHAT_EXPIRE_TIME = 5;
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
	/**
	 * 系统配置文件
	 */
	public static final String SYSTEM_PROPERTIES_FILE = "system.properties";
	/**
	 * 生信流程api配置文件
	 */
	public static final String BIOINFO_SERVICES_PROPERTIES_FILE = "bioinfo_services.properties";
	/**
	 * kafka中，给所有用户发送消息时消息的key
	 */
	public static final String MESSAGE_ALLUSER_KEY = "__message-alluser-key-in-kafka__";
	/**
	 * kafka中，给用户发送消息的topic
	 */
	public static final String MESSAGE_USER_TOPIC = "user-message";

	public static final String MESSAGE_USER_CHANNEL = "userMessage";

	public static final String FILEPATH_PROPERTIES_FILE = "file_path.properties";
	/**
	 * bsi系列app的tagId和appId对应关系
	 */
	public static final Map<Integer, Integer> bsiTags = new HashMap<Integer, Integer>() {
		private static final long serialVersionUID = 1L;

		{
			put(1, 118);
			put(40, 133);
			put(41, 134);
			put(42, 135);
			put(43, 136);
			put(44, 137);
		}
	};

}
