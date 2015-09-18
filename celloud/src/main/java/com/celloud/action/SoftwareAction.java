package com.celloud.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.celloud.sdo.Software;
import com.celloud.service.SoftwareService;
import com.google.inject.Inject;
import com.nova.action.BaseAction;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-15下午4:48:30
 * @version Revision: 1.0
 */
@ParentPackage("celloud-default")
@Action("software")
@Results({ @Result(name = "toSoftByFormat", type = "json", params = { "root",
	"appList" }) })
public class SoftwareAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    @Inject
    private SoftwareService appService;
    private List<Software> appList;
    private String condition;

    public String getSoftByFormat() {
	appList = appService.getAppsByFormat(Integer.parseInt(condition));
	return "toSoftByFormat";
    }

    public List<Software> getAppList() {
	return appList;
    }

    public void setAppList(List<Software> appList) {
	this.appList = appList;
    }

    public String getCondition() {
	return condition;
    }

    public void setCondition(String condition) {
	this.condition = condition;
    }

}
