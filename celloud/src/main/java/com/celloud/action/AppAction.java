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
@Results({ @Result(name = "toAppByFormat", type = "json", params = { "root",
                "appList" }),
        @Result(name = "toAppHome", location = "../../pages/software/appList.jsp") })
public class AppAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(AppAction.class);
    @Inject
    private AppService appService;
    private List<App> appList;
    private String condition;
    private Integer conditionInt;
    private Map<String, List<Classify>> classifyMap;

    public String getAppByFormat() {
        appList = appService.getAppsByFormat(Integer.parseInt(condition));
        return "toAppByFormat";
    }

    public String toAppHome() {
        Integer companyId = (Integer) super.session.get("companyId");
        log.info("用户" + super.session.get("userName") + "查看应用市场");
        classifyMap = appService.getDoubleClassify(conditionInt);
        appList = appService.getAppByClassify(conditionInt, companyId);
        return "toAppHome";
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

}
