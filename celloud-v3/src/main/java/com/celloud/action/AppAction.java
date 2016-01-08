package com.celloud.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.ClassifyFloor;
import com.celloud.constants.ConstantsData;
import com.celloud.model.App;
import com.celloud.model.Classify;
import com.celloud.model.Screen;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.AppService;
import com.celloud.service.ClassifyService;
import com.celloud.service.ScreenService;

/**
 * 应用市场
 *
 * @author han
 * @date 2016年1月5日 下午4:01:20
 */
@Controller
@RequestMapping("app")
public class AppAction {
    private static final long serialVersionUID = 1L;
    Logger log = LoggerFactory.getLogger(AppAction.class);
    @Resource
    private AppService appService;
    @Resource
    private ClassifyService classifyService;
    @Resource
    private ScreenService screenService;


    @RequestMapping("toAppStore")
    public ModelAndView toAppStore() {
        log.info("用户{}查看应用市场", ConstantsData.getLoginUserName());
        ModelAndView mv = new ModelAndView("app/app_main");
        /** 一级分类列表 */
        List<Classify> pclassifys = classifyService.getClassify(ClassifyFloor.root);
        mv.addObject("pclassifys", pclassifys);
        return mv;
    }

    @RequestMapping("toSclassifyApp")
    public ModelAndView toSclassifyApp(Integer paramId) {
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
        PageList<App> appPageList = appService.getAppPageListByClassify(cid, floor,
 userId, condition, type, pager);
        mv.addObject("pclassifys", pclassifys);
        mv.addObject("appPageList", appPageList);
        mv.addObject("classifyId", classifyId);
        mv.addObject("classifyPid", classifyPid);
        mv.addObject("classifyFloor", classifyFloor);
        return mv;
    }

    @RequestMapping("appDetail")
    public ModelAndView getAppById(Integer paramId) {
        log.info("用户{}查看APP{}详细信息", ConstantsData.getLoginUserName(), paramId);
        ModelAndView mv = new ModelAndView("app/app_detail");
        Integer userId = ConstantsData.getLoginUserId();
        App app = appService.getAppById(paramId, userId);
        List<Screen> screenList = screenService.getScreenByAppId(paramId);
        mv.addObject("app", app);
        mv.addObject("screenList", screenList);
        return mv;
    }

    @RequestMapping("myApps")
    public ModelAndView getMyApp() {
        log.info("用户{}查看已添加的APP", ConstantsData.getLoginUserName());
        ModelAndView mv = new ModelAndView("app/app_added");
        Integer userId = ConstantsData.getLoginUserId();
        List<App> appList = appService.getMyAppList(userId);
        mv.addObject("appList", appList);
        return mv;
    }

    @ResponseBody
    @RequestMapping("addApp")
    public Object userAddApp(Integer paramId, HttpServletResponse response) {
        log.info("用户{}添加APP{}", ConstantsData.getLoginUserName(), paramId);
        Integer userId = ConstantsData.getLoginUserId();
        Integer resultInt = appService.userAddApp(userId, paramId);
        if (resultInt > 0) {
            try {
                response.sendRedirect("myApps");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        } else {
            return resultInt;
        }
    }

    @ResponseBody
    @RequestMapping("removeApp")
    public Object userRemoveApp(Integer paramId, HttpServletResponse response) {
        log.info("用户{}取消添加APP{}", ConstantsData.getLoginUserName(), paramId);
        Integer userId = ConstantsData.getLoginUserId();
        Integer resultInt = appService.userRemoveApp(userId, paramId);
        if (resultInt > 0) {
            try {
                response.sendRedirect("myApps");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        } else {
            return resultInt;
        }
    }

}
