package com.celloud.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.celloud.constants.ClassifyFloor;
import com.celloud.sdo.App;
import com.celloud.sdo.Classify;
import com.celloud.sdo.Screen;
import com.celloud.service.AppService;
import com.google.inject.Inject;
import com.nova.action.BaseAction;
import com.nova.pager.Page;
import com.nova.pager.PageList;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-15下午4:48:30
 * @version Revision: 1.0
 */
@ParentPackage("celloud-default")
@Action("app3")
@Results({
        @Result(name = "success", type = "json", params = { "root", "resultInt" }),
        @Result(name = "toAppByFormat", type = "json", params = { "root",
                "appList" }),
        @Result(name = "toAppStore", location = "../../pages/software/appStore.jsp"),
        @Result(name = "toSclassifyApp", location = "../../pages/software/appClassify.jsp"),
        @Result(name = "toMoreAppList", location = "../../pages/software/appList.jsp"),
        @Result(name = "toAppDetail", location = "../../pages/software/appDetail.jsp"),
        @Result(name = "toMyAppPage", location = "../../pages/software/myApps.jsp") })
public class AppAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(AppAction.class);
    @Inject
    private AppService appService;
    private String type;
    private String condition;
    private Integer paramId;
    private Integer resultInt;
    /** app父级分类id */
    private Integer classifyPid;
    /** app子分类id */
    private Integer classifyId;
    /** app分类等级 */
    private Integer classifyFloor;
    private App app;
    private Page page;
    private List<App> appList;
    private List<Screen> screenList;
    private List<Classify> pclassifys;
    private List<Classify> sclassifys;
    private Map<Integer, List<App>> classifyAppMap;
    private PageList<App> appPageList;

    public String getAppByFormat() {
        Integer userId = (Integer) super.session.get("userId");
        appList = appService.getAppsByFormat(Integer.parseInt(condition),
                userId);
        return "toAppByFormat";
    }

    public String toAppStore() {
        log.info("用户" + super.session.get("userName") + "查看应用市场");
        /** 一级分类列表 */
        pclassifys = appService.getClassify(ClassifyFloor.root);
        return "toAppStore";
    }

    public String toSclassifyApp() {
        log.info(super.session.get("userName") + "在APP首页查看" + paramId + "的子分类");
        Integer companyId = (Integer) super.session.get("companyId");
        if (paramId == ClassifyFloor.js) {
            /** 小软件 */
            Classify clas = appService.getClassifyById(paramId);
            sclassifys = new ArrayList<>();
            sclassifys.add(clas);
        } else {
            /** 第一个一级分类的子分类 */
            sclassifys = appService.getClassify(paramId);
        }
        /** 二级分类下的app */
        classifyAppMap = new HashMap<>();
        for (Classify c : sclassifys) {
            Integer cid = c.getClassifyId();
            appList = appService.getAppByClassify(cid, companyId);
            classifyAppMap.put(cid, appList);
        }
        return "toSclassifyApp";
    }

    public String toMoreAppList() {
        log.info(super.session.get("userName") + "查看分类" + classifyPid + "-"
                + classifyId + "下的APP");
        Integer companyId = (Integer) super.session.get("companyId");
        pclassifys = appService.getClassify(ClassifyFloor.root);
        Integer cid = classifyId;
        Integer floor = classifyFloor;
        if (classifyId == 0) {
            cid = classifyPid;
        }
        if (classifyPid != ClassifyFloor.js && classifyId != ClassifyFloor.js) {
            sclassifys = appService.getClassify(classifyPid);
        } else {
            floor = 1;
            classifyPid = ClassifyFloor.js;
        }
        appPageList = appService.getAppPageListByClassify(cid, floor,
                companyId, condition, type, page);
        return "toMoreAppList";
    }

    public String getAppById() {
        Integer userId = (Integer) super.session.get("userId");
        log.info("用户" + super.session.get("userName") + "查看APP" + paramId
                + "详细信息");
        app = appService.getAppById(paramId, userId);
        screenList = appService.getScreenByAppId(paramId);
        return "toAppDetail";
    }

    public String getMyApp() {
        Integer userId = (Integer) super.session.get("userId");
        log.info("用户" + super.session.get("userName") + "查看已添加的APP");
        appList = appService.getMyAppList(userId);
        return "toMyAppPage";
    }

    public String userAddApp() {
        Integer userId = (Integer) super.session.get("userId");
        log.info("用户" + super.session.get("userName") + "添加APP" + paramId);
        resultInt = appService.userAddApp(userId, paramId);
        if (resultInt > 0) {
            return getMyAppList();
        } else {
            return SUCCESS;
        }
    }

    public String userRemoveApp() {
        Integer userId = (Integer) super.session.get("userId");
        log.info("用户" + super.session.get("userName") + "取消添加APP" + paramId);
        resultInt = appService.userRemoveApp(userId, paramId);
        if (resultInt > 0) {
            return getMyAppList();
        } else {
            return SUCCESS;
        }
    }

    public String getMyAppList() {
        Integer userId = (Integer) super.session.get("userId");
        appList = appService.getMyAppList(userId);
        return "toMyAppPage";
    }

    public List<App> getAppList() {
        return appList;
    }

    public void setAppList(List<App> appList) {
        this.appList = appList;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getParamId() {
        return paramId;
    }

    public void setParamId(Integer paramId) {
        this.paramId = paramId;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public List<Screen> getScreenList() {
        return screenList;
    }

    public void setScreenList(List<Screen> screenList) {
        this.screenList = screenList;
    }

    public Integer getResultInt() {
        return resultInt;
    }

    public void setResultInt(Integer resultInt) {
        this.resultInt = resultInt;
    }

    public List<Classify> getPclassifys() {
        return pclassifys;
    }

    public void setPclassifys(List<Classify> pclassifys) {
        this.pclassifys = pclassifys;
    }

    public List<Classify> getSclassifys() {
        return sclassifys;
    }

    public void setSclassifys(List<Classify> sclassifys) {
        this.sclassifys = sclassifys;
    }

    public Map<Integer, List<App>> getClassifyAppMap() {
        return classifyAppMap;
    }

    public void setClassifyAppMap(Map<Integer, List<App>> classifyAppMap) {
        this.classifyAppMap = classifyAppMap;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public PageList<App> getAppPageList() {
        return appPageList;
    }

    public void setAppPageList(PageList<App> appPageList) {
        this.appPageList = appPageList;
    }

    public Integer getClassifyPid() {
        return classifyPid;
    }

    public void setClassifyPid(Integer classifyPid) {
        this.classifyPid = classifyPid;
    }

    public Integer getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }

    public Integer getClassifyFloor() {
        return classifyFloor;
    }

    public void setClassifyFloor(Integer classifyFloor) {
        this.classifyFloor = classifyFloor;
    }

}
