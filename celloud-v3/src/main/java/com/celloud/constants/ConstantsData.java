package com.celloud.constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.celloud.model.User;

/**
 * 系统级常量及参数配置
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月22日 下午4:17:37
 */
public class ConstantsData {
    private static Logger logger = LoggerFactory.getLogger(ConstantsData.class);
    public static Map<String, Map<String, String>> machines = null;

    /**
     * 获取所有机器列表(如果内存中已存在，则直接使用内存中的；如果内存中不存在，则加载配置文件)
     * 
     * @return
     */
    public static Map<String, Map<String, String>> getMachines() {
        return getMachines(false);
    }

    /**
     * 获取所有机器列表
     * 
     * @param reload
     *            是否重新加载配置文件
     * @return
     */
    public static Map<String, Map<String, String>> getMachines(boolean reload) {
        if (!reload && machines != null) {
            return machines;
        }
        logger.debug(reload ? "重新" : "第一次" + "加载machines的配置文件");
        machines = new HashMap<String, Map<String, String>>();
        InputStream is = ConstantsData.class.getClassLoader().getResourceAsStream("machine.xml");
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(is);
            Element root = doc.getRootElement(); // 获取根节点
            // 获取根节点下的所有子节点machine
            Iterator<?> machine = root.elementIterator("machine");
            // 遍历machine节点
            while (machine.hasNext()) {
                Element every = (Element) machine.next();
                // 获取machine下的各个属性值
                Map<String, String> map = new HashMap<>();
                map.put(Mod.HOST, every.elementTextTrim(Mod.HOST));
                map.put(Mod.PORT, every.elementTextTrim(Mod.PORT));
                map.put(Mod.USERNAME, every.elementTextTrim(Mod.USERNAME));
                map.put(Mod.PWD, every.elementTextTrim(Mod.PWD));
                machines.put(every.elementTextTrim(Mod.NAME), map);
            }
        } catch (Exception e) {
            logger.error("加载machines出错！", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("关闭输入流出错！", e);
                }
            }
        }
        return machines;
    }

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
     * 获取当前的request对象
     * 
     * @return
     */
    public static HttpServletRequest getRequset() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取当前的response对象
     * 
     * @return
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取当前的session对象
     * 
     * @return
     */
    public static HttpSession getSession() {
        return getRequset().getSession();
    }
}
