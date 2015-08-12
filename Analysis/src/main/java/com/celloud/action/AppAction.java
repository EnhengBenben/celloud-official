/**  */
package com.celloud.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.celloud.sdo.Software;
import com.celloud.service.SoftwareService;
import com.google.inject.Inject;

@ParentPackage("json-default")
@Action("app")
@Results({
		@Result(name = "appList", location = "../../pages/appList.jsp"),
		@Result(name = "success", type = "json", params = { "root", "fileName" }) })
public class AppAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Inject
	private SoftwareService appService;
	private List<Software> appList;
	private Integer compId;

	public String getAppListByBigUser() {
		compId = (Integer) getCid();
		appList = appService.getAppListByBigUser(compId);
		return "appList";
	}

	public List<Software> getAppList() {
		return appList;
	}

	public void setAppList(List<Software> appList) {
		this.appList = appList;
	}

}
