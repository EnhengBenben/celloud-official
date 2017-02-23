package com.celloud.alidayu;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.celloud.constants.ConstantsData;
import com.celloud.model.mongo.UserCaptcha;

/**
 * 阿里大于配置类
 * 
 * @author leamo
 * @date 2016年10月31日 下午3:12:22
 */
public class AlidayuConfig {
    private static final String ALIDAYU_PROPS = "alidayu.properties";
    private static Properties props = ConstantsData
            .loadProperties(ALIDAYU_PROPS);

    public static String region_id = props.getProperty("regionId");
    public static String access_key = props.getProperty("accessKeyId");
    public static String secret = props.getProperty("secret");
    public static String product = props.getProperty("product");
    public static String domain = props.getProperty("domain");
    public static String sign_name = props.getProperty("signName");
    public static String captcha_template_code = props.getProperty("captchaTemplateCode");
    public static String register_captcha_template_code = props.getProperty("registerCaptchaTemplateCode");
    /** 验证码过期时间(min) */
    public static int captcha_expire_time = 5;
    /** 注册验证码过期时间(h) */
    public static int register_captcha_expire_time = 24;
    public static Map<String, UserCaptcha> userCapchaMap;

    static {
        userCapchaMap = new ConcurrentHashMap<>();
    }
}
