package com.celloud.wechat;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.utils.HttpURLUtils;

/**
 * 微信操作工具类
 * 
 * @author lin
 * @date 2016年6月27日 上午10:17:51
 */
public class WechatUtils {
	Logger logger = LoggerFactory.getLogger(WechatUtils.class);

	//properties
	private String appId;
	private String appSecret;
	private String loginHtml;

	//wechat url
    private String oAuth2Url;
    private String oAuth2TokenUrl;
    private String tokenUrl;
    private String templateUrl;

	/**
	 * 获取生成二维码所需要的url
	 * 
	 * @param checkCode
	 * @return
	 * @author lin
	 * @date 2016年6月27日上午10:26:42
	 */
	public String getQRUrl(String checkCode) {
		//由于授权操作安全等级较高，所以在发起授权请求时，微信会对授权链接做正则强匹配校验，如果链接的参数顺序不对，授权页面将无法正常访问
		String url = oAuth2Url + "appid=" + appId + "&redirect_uri=" + loginHtml
				+ "&response_type=code&scope=snsapi_userinfo&state=" + checkCode + "#wechat_redirect";
		return url;
	}

	/**
	 * 获取登录token
	 * 
	 * @return
	 * @author lin
	 * @date 2016年6月27日上午10:37:03
	 */
	public String getToken() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        if (session.getAttribute("WebchatToken") == null) {
            String url = tokenUrl + "appid=" + appId + "&secret=" + appSecret;
            String result = HttpURLUtils.httpGetRequest(url);
            JSONObject t = new JSONObject(result);
            if (t.has("access_token")) {
                session.setAttribute("WebchatToken",
                        t.getString("access_token"));

            } else {
                logger.error(result);
            }
        }
        return session.getAttribute("WebchatToken").toString();
	}

	/**
	 * 根据用户授权code获取token
	 * 
	 * @param code
	 * @return
	 * @author lin
	 * @date 2016年6月27日上午10:44:15
	 */
	public String getToken(String code) {
		String url = oAuth2TokenUrl + "appid=" + appId + "&secret=" + appSecret
				+ "&code=" + code + "&grant_type=authorization_code";
		String result = HttpURLUtils.httpGetRequest(url);
		JSONObject t = new JSONObject(result);
        if (t.has("access_token")) {
            return t.getString("access_token");
		}
		logger.error(result);
		return null;
	}

	/**
     * 根据用户授权code获取openid
     * 
     * @param code
     * @return
     * @author leamo
     * @date 2016年6月29日 下午11:14:31
     */
    public String getOpenId(String code) {
        String url = oAuth2TokenUrl + "appid=" + appId + "&secret=" + appSecret
                + "&code=" + code + "&grant_type=authorization_code";
        String result = HttpURLUtils.httpGetRequest(url);
        JSONObject t = new JSONObject(result);
        if (t.has("openid")) {
            return t.getString("openid");
        }
        logger.error(result);
        return null;
    }

    /**
     * 获取用户信息
     * 
     * @param openId
     * @return
     * @author lin
     * @date 2016年6月27日上午10:49:17
     */
	public String getUserInfo(String openId) {
		//TODO 需要验证token
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + getToken() + "&openid=" + openId
				+ "&lang=zh_CN";
		String result = HttpURLUtils.httpGetRequest(url);
		System.out.println(result);
		return null;
	}

    /**
     * 消息推送
     * 
     * @param map
     * @param access_token
     * @return
     * @author leamo
     * @date 2016年6月30日 下午4:13:04
     */
    public String pushMessage(Map<String, Object> map) {
        String url = templateUrl + getToken();
        JSONObject json = new JSONObject(map);
        return HttpURLUtils.httpPostRequest(url, json.toString());
    }

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public void setLoginHtml(String loginHtml) {
		this.loginHtml = loginHtml;
	}

    public void setoAuth2Url(String oAuth2Url) {
        this.oAuth2Url = oAuth2Url;
    }

    public void setoAuth2TokenUrl(String oAuth2TokenUrl) {
        this.oAuth2TokenUrl = oAuth2TokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

}
