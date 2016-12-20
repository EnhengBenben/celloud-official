package com.celloud.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.AppPermission;
import com.celloud.constants.ClassifyFloor;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.IconConstants;
import com.celloud.model.mysql.App;
import com.celloud.model.mysql.Classify;
import com.celloud.model.mysql.Screen;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.AppService;
import com.celloud.service.ClassifyService;
import com.celloud.service.ScreenService;
import com.celloud.utils.ActionLog;

/**
 * 应用市场
 *
 * @author han
 * @date 2016年1月5日 下午4:01:20
 */
@Controller
@RequestMapping("app")
public class AppAction {
    Logger log = LoggerFactory.getLogger(AppAction.class);
    @Resource
    private AppService appService;
    @Resource
    private ClassifyService classifyService;
    @Resource
    private ScreenService screenService;

	@ActionLog(value = "获取用户被授权的所有APP", button = "医院管理员新增")
	@ResponseBody
	@RequestMapping("getRightAppList")
	public List<App> getRightAppList() {
		return appService.getRightAppList(ConstantsData.getLoginUserId());
	}

    @ActionLog(value = "获取用户已经运行过数据的APP列表（项目报告页面检索框用）", button = "报告管理")
    @ResponseBody
    @RequestMapping("getRanAPP")
	public PageList<Map<String, String>> getRanAPP() {
		PageList<Map<String, String>> pageList = new PageList<>();
		List<Map<String, String>> list = appService.getRanAPP(ConstantsData.getLoginUserId());
		pageList.setDatas(list);
		return pageList;
    }

    @ActionLog(value = "打开应用市场首页", button = "应用市场")
    @RequestMapping("toAppStore")
    @ResponseBody
    public List<Classify> toAppStore() {
        log.info("用户{}查看应用市场", ConstantsData.getLoginUserName());
        /** 一级分类列表 */
        return classifyService.getClassify(ClassifyFloor.root);
    }

    @ActionLog(value = "APP首页查看指定一级分类的子分类", button = "APP一级分类按钮")
    @RequestMapping("toSclassifyApp_bak")
    public ModelAndView toSclassifyApp_bak(Integer paramId) {
        log.info("{}在APP首页查看{}的子分类", ConstantsData.getLoginUserName(), paramId);
        ModelAndView mv = new ModelAndView("app/app_classify");
        Integer userId = ConstantsData.getLoginUserId();
        List<Classify> sclassifys = null;
        if (paramId == ClassifyFloor.js) {
            /** 小软件 */
            Classify clas = classifyService.getClassifyById(paramId);
            sclassifys = new ArrayList<>();
            sclassifys.add(clas);
        } else {
            /** 第一个一级分类的子分类 */
            sclassifys = classifyService.getClassify(paramId);
        }
        /** 二级分类下的app */
        Map<Integer, List<App>> classifyAppMap = new HashMap<>();
        for (Classify c : sclassifys) {
            Integer cid = c.getClassifyId();
            List<App> appList = appService.getAppByClassify(cid, userId);
            classifyAppMap.put(cid, appList);
        }
        mv.addObject("sclassifys", sclassifys);
        mv.addObject("classifyAppMap", classifyAppMap);
        return mv;
    }

    @ActionLog(value = "APP首页查看指定一级分类的子分类", button = "APP一级分类按钮")
    @RequestMapping("toSclassifyApp")
    @ResponseBody
    public Map<String, Object> toSclassifyApp(Integer paramId) {
        log.info("{}在APP首页查看{}的子分类", ConstantsData.getLoginUserName(), paramId);
        Map<String, Object> map = new HashMap<String, Object>();
        Integer userId = ConstantsData.getLoginUserId();
        List<Classify> sclassifys = null;
        if (paramId == ClassifyFloor.js) {
            /** 小软件 */
            Classify clas = classifyService.getClassifyById(paramId);
            sclassifys = new ArrayList<>();
            sclassifys.add(clas);
        } else {
            /** 第一个一级分类的子分类 */
            sclassifys = classifyService.getClassify(paramId);
        }
        /** 二级分类下的app */
        Map<Integer, List<App>> classifyAppMap = new HashMap<>();
        for (Classify c : sclassifys) {
            Integer cid = c.getClassifyId();
            List<App> appList = appService.getAppByClassify(cid, userId);
            classifyAppMap.put(cid, appList);
        }
        map.put("sclassifys", sclassifys);
        map.put("classifyAppMap", classifyAppMap);
        return map;
    }

    @ActionLog(value = "查看分类指定分类下的所有APP列表页面", button = "APP获取更多")
    @RequestMapping("toMoreAppList")
    public ModelAndView toMoreAppList(Integer classifyId, Integer classifyPid, String condition, String type,
            Integer classifyFloor, @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page pager = new Page(page, size);
        log.info("{}查看分类{}-{}下的APP", ConstantsData.getLoginUserName(), classifyPid, classifyId);
        ModelAndView mv = new ModelAndView("app/app_list");
        Integer userId = ConstantsData.getLoginUserId();
        List<Classify> pclassifys = classifyService.getClassify(ClassifyFloor.root);
        Integer cid = classifyId;
        Integer floor = classifyFloor;
        if (classifyId == 0) {
            cid = classifyPid;
        }
        if (classifyPid != ClassifyFloor.js && classifyId != ClassifyFloor.js) {
            List<Classify> sclassifys = classifyService.getClassify(classifyPid);
            mv.addObject("sclassifys", sclassifys);
        } else {
            floor = 1;
            classifyPid = ClassifyFloor.js;
        }
        PageList<App> appPageList = appService.getAppPageListByClassify(cid, floor,userId, condition, type, pager);
        mv.addObject("pclassifys", pclassifys);
        mv.addObject("appPageList", appPageList);
        mv.addObject("classifyId", classifyId);
        mv.addObject("classifyPid", classifyPid);
        mv.addObject("classifyFloor", classifyFloor);
        mv.addObject("condition",condition);
        mv.addObject("type",type);
        return mv;
    }

    @ActionLog(value = "查看指定APP详细信息", button = "APP详细")
    @RequestMapping("appDetail_bak")
    public ModelAndView getAppById_bak(Integer paramId) {
        log.info("用户{}查看APP{}详细信息", ConstantsData.getLoginUserName(), paramId);
        ModelAndView mv = new ModelAndView("app/app_detail");
        Integer userId = ConstantsData.getLoginUserId();
        App app = appService.getAppById(paramId, userId);
        List<Screen> screenList = screenService.getScreenByAppId(paramId);
        mv.addObject("app", app);
        mv.addObject("screenList", screenList);
        return mv;
    }

    @ActionLog(value = "查看指定APP详细信息", button = "APP详细")
    @RequestMapping("appDetail")
    @ResponseBody
    public Map<String, Object> getAppById(Integer paramId) {
        log.info("用户{}查看APP{}详细信息", ConstantsData.getLoginUserName(), paramId);
        Map<String, Object> map = new HashMap<String, Object>();
        Integer userId = ConstantsData.getLoginUserId();
        App app = appService.getAppById(paramId, userId);
        List<Screen> screenList = screenService.getScreenByAppId(paramId);
        map.put("app", app);
        map.put("screenList", screenList);
        return map;
    }

    @ActionLog(value = "查看用户已添加的APP", button = "应用市场")
    @RequestMapping("getMyApp_bak")
    public ModelAndView getMyApp_bak() {
        log.info("用户{}查看已添加的APP", ConstantsData.getLoginUserName());
        ModelAndView mv = new ModelAndView("app/app_added");
        Integer userId = ConstantsData.getLoginUserId();
        List<App> appList = appService.getMyAppList(userId);
        mv.addObject("appList", appList);
        return mv;
    }

    @ActionLog(value = "查看用户已添加的APP", button = "应用市场")
    @RequestMapping("myApps")
    @ResponseBody
    public List<App> getMyApp() {
        log.info("用户{}查看已添加的APP", ConstantsData.getLoginUserName());
        Integer userId = ConstantsData.getLoginUserId();
        List<App> appList = appService.getMyAppList(userId);
        return appList;
    }

	/**
	 * 获取用户的产品
	 * 
	 * @return
	 * @author lin
	 * @date 2016年9月9日下午1:53:11
	 */
	@RequestMapping("getProduct")
	@ResponseBody
	public Map<String, Object> getProduct() {
		Integer userId = ConstantsData.getLoginUserId();
		Map<String, Object> map = new HashMap<>();
		List<App> appList = appService.getMyAppList(userId);
        Boolean hasPrivate = false;
		for (App app : appList) {
			if (app.getAppId().equals(118)) {
				map.put("app" + app.getAppId(), app.getAppId());
                map.put("onlyBSI", true);
            } else if (app.getAppId().equals(123)) {
				map.put("app" + app.getAppId(), app.getAppId());
                hasPrivate = true;
            } else {
                if (app.getAttribute() == AppPermission.PRIVATE) {
                    hasPrivate = true;
                }
			}
		}
        if (hasPrivate) {
            map.put("onlyBSI", false);
        }
		return map;
	}

    @ActionLog(value = "用户添加APP，即允许在数据管理运行中显示", button = "添加APP")
    @ResponseBody
    @RequestMapping("addApp")
    public Object userAddApp(Integer paramId, HttpServletResponse response) {
        log.info("用户{}添加APP{}", ConstantsData.getLoginUserName(), paramId);
        Integer userId = ConstantsData.getLoginUserId();
        return appService.userAddApp(userId, paramId);
    }

    @ActionLog(value = "用户取消添加APP，即不允许在数据管理运行中显示", button = "删除APP")
    @ResponseBody
    @RequestMapping("removeApp")
    public Object userRemoveApp(Integer paramId, HttpServletResponse response) {
        log.info("用户{}取消添加APP{}", ConstantsData.getLoginUserName(), paramId);
        Integer userId = ConstantsData.getLoginUserId();
        return appService.userRemoveApp(userId, paramId);
    }
    
    /**
     * 获取已保存app图标
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> appImage(String file) throws IOException {
		String path = IconConstants.getAppPath(file);
        File targetFile = new File(path);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
    }
    
    /**
     * 获取已保存app截图
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "screen", method = RequestMethod.GET)
    public ResponseEntity<byte[]> appScreen(String file) throws IOException {
		String path = IconConstants.getScreenPath(file);
        File targetFile = new File(path);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
    }
}
