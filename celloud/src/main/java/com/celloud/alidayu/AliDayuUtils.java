package com.celloud.alidayu;

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
    public static String sendCaptcha(String mobile, String info) {
        IClientProfile profile = DefaultProfile.getProfile(
                AlidayuConfig.region_id, AlidayuConfig.access_key,
                AlidayuConfig.secret);
        try {
            DefaultProfile.addEndpoint(AlidayuConfig.region_id,
                    AlidayuConfig.region_id, AlidayuConfig.product,
                    AlidayuConfig.domain);
            IAcsClient client = new DefaultAcsClient(profile);
            SingleSendSmsRequest request = new SingleSendSmsRequest();
            request.setSignName("华点云");
            request.setTemplateCode(AlidayuConfig.captcha_template_code);
            request.setParamString("{'number':'" + info + "'}");
            request.setRecNum(mobile);
            SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
            return httpResponse.getRequestId();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return "error";
    }
}
