package com.celloud.alidayu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;

/**
 * 阿里大于工具类
 * 
 * @author leamo
 * @date 2016年10月31日 下午3:09:30
 */
public class AliDayuUtils {
	private static Logger logger = LoggerFactory.getLogger(AliDayuUtils.class);

    /**
     * 发送验证码公共方法
     * 
     * @param mobile
     * @param captcha
     * @param templateCode
     * @return
     * @author leamo
     * @date 2017年2月13日 下午5:08:59
     */
    public static String send(String mobile, String captcha, String templateCode) {
        IClientProfile profile = DefaultProfile.getProfile(AlidayuConfig.region_id, AlidayuConfig.access_key,
                AlidayuConfig.secret);
        try {
            DefaultProfile.addEndpoint(AlidayuConfig.region_id, AlidayuConfig.region_id, AlidayuConfig.product,
                    AlidayuConfig.domain);
            IAcsClient client = new DefaultAcsClient(profile);
            SingleSendSmsRequest request = new SingleSendSmsRequest();
            request.setSignName("华点云");
            request.setTemplateCode(templateCode);
            request.setParamString("{'number':'" + captcha + "'}");
            request.setRecNum(mobile);
            SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
            return httpResponse.getRequestId();
        } catch (ServerException e) {
            logger.error("验证码发送失败！" + e);
        } catch (ClientException e) {
            logger.error("验证码发送失败！" + e);
        }
        return "error";
    }

    /**
     * 登录验证码
     * 
     * @param mobile
     * @param info
     * @return
     * @author leamo
     * @date 2016年10月31日 下午5:02:15
     */
    public static String sendCaptcha(String mobile, String captcha) {
        logger.info("准备向手机{}发送登录验证码{}", mobile, captcha);
        return send(mobile, captcha, AlidayuConfig.captcha_template_code);
    }

    /**
     * 注册验证码
     * 
     * @param mobile
     * @param info
     * @return
     * @author leamo
     * @date 2017年2月13日 下午5:02:09
     */
    public static String sendRegisterCaptcha(String mobile, String captcha) {
        logger.info("准备向手机{}发送注册验证码{}", mobile, captcha);
        return send(mobile, captcha, AlidayuConfig.register_captcha_template_code);
    }
}
