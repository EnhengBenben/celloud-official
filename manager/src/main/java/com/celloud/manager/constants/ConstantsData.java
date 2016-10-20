package com.celloud.manager.constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.celloud.manager.model.User;


/**
 * 系统级常量及参数配置
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月22日 下午4:17:37
 */
public class ConstantsData {

    private static Logger logger=LoggerFactory.getLogger(ConstantsData.class);
    private static Properties systemProperties;
    /**
     * 判断当前session有无用户登录
     * 
     * @return
     */
    public static boolean isLoggedIn() {
        return getLoginUser() != null;
    }

    /**
     * 获取当前session已登录的用户对象，如无用户登录，则为null
     * 
     * @return
     */
    public static User getLoginUser() {
        Object user = getSession().getAttribute(Constants.SESSION_LOGIN_USER);
        return user == null ? null : (User) user;
    }

    /**
     * 获取当前登录用户的用户名
     * 
     * @return
     */
    public static String getLoginUserName() {
        User user = getLoginUser();
        return user == null ? null : user.getUsername();
    }

    /**
     * 获取当前登录用户的id
     * 
     * @return
     */
    public static Integer getLoginUserId() {
        User user = getLoginUser();
        return user == null ? null : user.getUserId();
    }

    /**
     * 获取当前登录用户的email
     * 
     * @return
     */
    public static String getLoginEmail() {
        User user = getLoginUser();
        return user == null ? null : user.getEmail();
    }

    /**
     * 获取当前登录用户的role
     * 
     * @return
     */
    public static Integer getLoginCompanyId() {
        User user = getLoginUser();
        Integer companyId = null;
        if (user == null) {
            companyId = -1;
        } else {
            Integer role = user.getRole();
            if (UserRole.ADMINISTRATOR.equals(role)) {// 超级管理员
                companyId = null;
            }
            if (UserRole.BIG_CUSTOMER.equals(role)) {// 大客户
                companyId = user.getCompanyId();
            }
        }
        return companyId;
    }

    /**
     * 获取当前的request对象
     * @return
     */
    public static HttpServletRequest getRequset() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
    /**
     * 获取当前的response对象
     * @return
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }
    /**
     * 获取当前的session对象
     * @return
     */
    public static HttpSession getSession() {
        return getRequset().getSession();
    }
    public static Properties loadProperties(String filepath) {
        String path = (ConstantsData.class.getClassLoader().getResource("") + filepath).replace("file:", "");
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(path)));
        } catch (IOException e) {
            logger.error("加载properties文件失败：{}", filepath, e);
        }
        return properties;
    }
    
    public static void loadSystemProperties() {
        systemProperties = loadProperties(Constants.SYSTEM_PROPERTIES_FILE);
    }
    public static Object getContextUrl() {
        if(systemProperties==null){
            loadSystemProperties();
        }
        return systemProperties.getProperty("context_url");
    }
}
