package com.celloud.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.celloud.sdo.App;
import com.celloud.sdo.Classify;
import com.celloud.sdo.Screen;
import com.celloud.service.AppService;
import com.google.inject.Inject;
import com.nova.action.BaseAction;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-15下午4:48:30
 * @version Revision: 1.0
 */
@ParentPackage("celloud-default")
@Action("app3")
@Results({
        @Result(name = "toAppByFormat", type = "json", params = { "root",
                "appList" }),
        @Result(name = "appClassify", location = "../../pages/software/appClassify.jsp"),
        @Result(name = "appListPage", location = "../../pages/software/appList.jsp"),
        @Result(name = "toAppDetail", location = "../../pages/software/appDetail.jsp"),
        @Result(name = "toMyAppPage", location = "../../pages/software/myApps.jsp") })
public class AppAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(AppAction.class);
    @Inject
    private AppService appService;
    private List<App> appList;
    private String condition;
    private Integer conditionInt;
    private Integer paramId;
    private Map<String, List<Classify>> classifyMap;
    private App app;
    private List<Screen> screenList;

    public String getAppByFormat() {
        appList = appService.getAppsByFormat(Integer.parseInt(condition));
        return "toAppByFormat";
    }

    public String getAppClassify() {
        log.info("用户" + super.session.get("userName") + "查看应用市场");
        classifyMap = appService.getDoubleClassify(conditionInt);
        return "appClassify";
    }

    public String getAppListPage() {
        Integer companyId = (Integer) super.session.get("companyId");
        appList = appService.getAppByClassify(paramId, conditionInt, companyId);
        return "appListPage";
    }

    public String getAppById() {
        log.info("用户" + super.session.get("userName") + "查看APP" + paramId
                + "详细信息");
        app = appService.getAppById(paramId);
        screenList = appService.getScreenByAppId(paramId);
        return "toAppDetail";
    }

    public String getMyApp() {
        Integer userId = (Integer) super.session.get("userId");
        log.info("用户" + super.session.get("userName") + "查看已添加的APP");
        // TODO 测试
        userId = 33;
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

    public Integer getConditionInt() {
        return conditionInt;
    }

    public void setConditionInt(Integer conditionInt) {
        this.conditionInt = conditionInt;
    }

    public Map<String, List<Classify>> getClassifyMap() {
        return classifyMap;
    }

    public void setClassifyMap(Map<String, List<Classify>> classifyMap) {
        this.classifyMap = classifyMap;
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

}
