package com.celloud.constants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.celloud.model.mysql.OSSConfig;
import com.celloud.model.mysql.User;
import com.celloud.utils.LocalIpAddressUtil;

/**
 * 系统级常量及参数配置
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月22日 下午4:17:37
 */
public class ConstantsData {
	private static Logger logger = LoggerFactory.getLogger(ConstantsData.class);
	public static Map<String, Map<String, String>> machines = null;
	private static Properties systemProperties;
	private static Properties filePathProperties;
	private static Properties bioinfoServices;
	private static String anotherNamePerlPath;
	private static OSSConfig ossConfig;
	private static String clusterId;

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
		Object user = getShioSession().getAttribute(Constants.SESSION_LOGIN_USER);
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
	 * 获取当前登录用户所属医院
	 * 
	 * @return
	 */
	public static Integer getLoginCompanyId() {
		User user = getLoginUser();
		return user == null ? null : user.getCompanyId();
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

	public static Session getShioSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Properties loadProperties(String filepath) {
		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = ConstantsData.class.getClassLoader().getResourceAsStream(filepath);
			properties.load(inputStream);
		} catch (IOException e) {
			logger.error("加载properties文件失败：{}", filepath, e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("关闭properties文件读取流失败： {}", filepath, e);
				}
			}
		}
		return properties;
	}

	public static void loadBioinfoServices() {
		bioinfoServices = loadProperties(Constants.BIOINFO_SERVICES_PROPERTIES_FILE);
	}

	public static String getBioinfoService(String app) {
		if (bioinfoServices == null) {
			loadBioinfoServices();
		}
		return bioinfoServices == null ? null : bioinfoServices.getProperty(app);
	}

	public synchronized static Properties getSystemProperties() {
		if (systemProperties == null) {
			systemProperties = loadProperties(Constants.SYSTEM_PROPERTIES_FILE);
		}
		return systemProperties;
	}

	public static String getContextUrl() {
		return getSystemProperties().getProperty("context_url");
	}

	private synchronized static Properties getFilePathProperties() {
		if (filePathProperties == null) {
			return loadProperties(Constants.FILEPATH_PROPERTIES_FILE);
		}
		return filePathProperties;
	}

	public static String getOfsPath() {
		String ofsPath = getFilePathProperties().getProperty("ossfsPath");
		if (ofsPath != null && !ofsPath.endsWith(File.separatorChar + "")) {
			ofsPath = ofsPath + File.separatorChar;
		}
		return ofsPath;
	}

	public static String getLocalIp() {
		String localAddr = getRequset().getRemoteAddr();
		if ("0:0:0:0:0:0:0:1".equals(localAddr)) {
			localAddr = "127.0.0.1";
		}
		return localAddr;
	}

	public static Map<String, String> significances() {
		Map<String, String> significances = new HashMap<>();
		significances.put("Pathogenic", "致病相关变异");
		significances.put("Likely pathogenic", "可能致病变异");
		significances.put("Uncertain significance", "意义不明确变异");
		significances.put("Likely benign", "可能良性变异");
		significances.put("Benign", "良性变异");
		return significances;
	}

	public static String getAnotherNamePerlPath(HttpServletRequest request) {
		if (anotherNamePerlPath != null) {
			return anotherNamePerlPath;
		}
		request = request == null ? getRequset() : request;
		if (request == null) {
			return null;
		}
		// sc.getRealPath("/resources") + "/plugins/getAliases.pl"
		anotherNamePerlPath = request.getServletContext().getRealPath("/resources") + "/plugins/getAliases.pl";
		return anotherNamePerlPath;
	}

	public static OSSConfig getOSSConfig() {
		return ossConfig;
	}

	public synchronized static void setOSSConfig(OSSConfig config) {
		ossConfig = config;
	}

	public static String getClusterId(Integer port) {
		if (clusterId == null) {
			clusterId = LocalIpAddressUtil.getLocalIps().stream().sorted().reduce((x, y) -> x + "," + y).orElse("1");
		}
		return clusterId + (port == null ? "" : port + "");
	}
}
