package com.celloud.utils;

import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;

/**
 * 用户头像工具类
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年1月7日 下午4:23:24
 */
public class AvatarUtil {
    public static String getAvatar(String avatar) {
        if (avatar == null || avatar.trim().equals("")) {
            return getDefaultAvatar();
        }
        String path = ConstantsData.getRequset().getContextPath() + "/images/avatar/";
        if (avatar.startsWith(path)) {
            return avatar;
        }
        if (ConstantsData.getSession().getServletContext().getRealPath("avatar/" + avatar) == null) {
            return getDefaultAvatar();
        }
        return ConstantsData.getRequset().getContextPath() + "/images/avatar/" + avatar;
    }

    public static String getDefaultAvatar() {
        return ConstantsData.getRequset().getContextPath() + "/images/avatar/" + Constants.DELAULT_AVATAR;
    }

    public static String encrypt() {
        return null;
    }
}
