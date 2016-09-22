package com.celloud.utils;

import com.celloud.constants.Constants;

/**
 * 用户头像工具类
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年1月7日 下午4:23:24
 */
public class AvatarUtil {
	/**
	 * 根据名字获取用户头像
	 * 
	 * @param avatar
	 * @return
	 */
	public static String getAvatar(String avatar) {
		if (avatar == null || avatar.trim().equals("")) {
			return getDefaultAvatar();
		}
		String path = "images/avatar/";
		if (avatar.startsWith(path)) {
			return avatar;
		}
		return "images/avatar/" + avatar + ".png";
	}

	/**
	 * 获取默认的用户头像
	 * 
	 * @return
	 */
	public static String getDefaultAvatar() {
		return "images/avatar/" + Constants.DELAULT_AVATAR + ".png";
	}

}
