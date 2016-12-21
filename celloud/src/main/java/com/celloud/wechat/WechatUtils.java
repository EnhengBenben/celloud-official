package com.celloud.wechat;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
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
	private String urlToken;

	//wechat url
	private String oAuth2TokenUrl;
	private String tokenUrl;
	private String templateUrl;
	private String createMenu;

	private String qrcodeCreate;
	private String qrcodeShow;

	/**
	 * 初始化自定义菜单
	 * 
	 * @return
	 * @author lin
	 * @date 2016年10月19日下午4:11:58
	 */
	public String initMenu() {
		String menu = "{'button':[{'name':'我要','sub_button':[{'type':'view','name':'绑定账户','url':'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc9447d96940b620c&redirect_uri=https://www.celloud.cn/api/wechat/toBind/&response_type=code&scope=snsapi_userinfo&state=out#wechat_redirect'},{'type':'view','name':'解除绑定','url':'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc9447d96940b620c&redirect_uri=https://www.celloud.cn/api/wechat/toUnBind/&response_type=code&scope=snsapi_userinfo&state=out#wechat_redirect'}]},{'type':'view','name':'意见反馈','url':'https://www.celloud.cn/feedback_for_phone.html'},{'type':'view','name':'进入官网','url':'https://www.celloud.cn'}]}";
		String url = createMenu + getToken();
		logger.info("init wechat menu:" + url);
		JSONObject json = new JSONObject(menu);
		return HttpURLUtils.httpPostRequest(url, json.toString());

	}

	/**
	 * 校验url是否有效
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 * @author lin
	 * @date 2016年10月24日下午1:43:53
	 */
	public boolean checkUrl(String signature, String timestamp, String nonce) {
		List<String> param = new ArrayList<>();
		param.add(urlToken);
		param.add(timestamp);
		param.add(nonce);
		//1）将token、timestamp、nonce三个参数进行字典序排序
		Collections.sort(param);
		//2）将三个参数字符串拼接成一个字符串进行sha1加密
		String params = "";
		for (String s : param) {
			params += s;
		}
		String mySignature = DigestUtils.sha1Hex(params);
		//3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		return mySignature.equals(signature);
	}

	/**
	 * 获取微信临时二维码
	 * 
	 * @param time
	 *            超时时间，单位秒
	 * @return
	 * @author lin
	 * @date 2016年10月26日下午2:56:28
	 */
	public String getTempQRUrl(int time) {
		String url = qrcodeCreate + getToken();
		Map<String, Object> sceneId = new HashMap<>();
		sceneId.put("scene_id", getSceneId());
		Map<String, Object> scene = new HashMap<>();
		scene.put("scene", sceneId);
		Map<String, Object> map = new HashMap<>();
		map.put("expire_seconds", time);
		map.put("action_name", "QR_SCENE");
		map.put("action_info", scene);
		JSONObject json = new JSONObject(map);
		String result = HttpURLUtils.httpPostRequest(url, json.toString());
		JSONObject resultJson = new JSONObject(result);
		if (resultJson.has("ticket")) {
			return qrcodeShow + resultJson.get("ticket");
		}
		return null;
	}

	/**
	 * 获取不为0的场景值ID<br>
	 * 临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
	 * 
	 * @return
	 * @author lin
	 * @date 2016年10月26日下午2:07:38
	 */
	private static int getSceneId() {
		SecureRandom sr = new SecureRandom();
		int flag = 0;
		while (flag == 0) {
			flag = sr.nextInt(10000);
		}
		return flag;
	}

	/**
	 * 获取登录token
	 * 
	 * @return
	 * @author lin
	 * @date 2016年6月27日上午10:37:03
	 */
	public String getToken() {
		String token = WechatToken.getToken();
		if (token == null) {
			String url = tokenUrl + "appid=" + appId + "&secret=" + appSecret;
			String result = HttpURLUtils.httpGetRequest(url);
			JSONObject t = new JSONObject(result);
			if (t.has("access_token")) {
				token = t.getString("access_token");
				WechatToken.setToken(token);
			} else {
				logger.error(result);
			}
		}
		return token;
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
		String url = oAuth2TokenUrl + "appid=" + appId + "&secret=" + appSecret + "&code=" + code
				+ "&grant_type=authorization_code";
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
		String url = oAuth2TokenUrl + "appid=" + appId + "&secret=" + appSecret + "&code=" + code
				+ "&grant_type=authorization_code";
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

	public void setoAuth2TokenUrl(String oAuth2TokenUrl) {
		this.oAuth2TokenUrl = oAuth2TokenUrl;
	}

	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}

	public void setTemplateUrl(String templateUrl) {
		this.templateUrl = templateUrl;
	}

	public void setUrlToken(String urlToken) {
		this.urlToken = urlToken;
	}

	public void setCreateMenu(String createMenu) {
		this.createMenu = createMenu;
	}

	public void setQrcodeCreate(String qrcodeCreate) {
		this.qrcodeCreate = qrcodeCreate;
	}

	public void setQrcodeShow(String qrcodeShow) {
		this.qrcodeShow = qrcodeShow;
	}

}
