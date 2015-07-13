package com.nova.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.google.inject.Inject;
import com.nova.sdo.App;
import com.nova.service.IAppService;

@ParentPackage("celloud-default")
@Action("app")
@Results({
		@Result(name = "success", type = "json", params = { "root", "appList" }),
		@Result(name = "desk", type = "json", params = { "root", "flag" }),
		@Result(name = "delete", type = "json", params = { "root", "flag" }) })
public class AppAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Inject
	private IAppService appService;
	private List<App> list;
	private List<List<App>> appList;
	private Integer userId;
	private Integer appId;
	private Integer deskNo;
	private Integer oldDeskNo;
	private boolean flag;

	// 获取用户的桌面APP
	public String getApp() {
		appList = appService.getApp(userId);
		return SUCCESS;
	}

	// 桌面软件托动后 保存
	public String updateDesk() {
		// 后台桌号和前台统一
		flag = appService.updateDesk(userId, deskNo + 1, oldDeskNo + 1, appId);
		return "desk";
	}

	// 从桌面上删除app
	public String delete() {
		flag = appService.deleteAppFromDesk(userId, deskNo + 1, appId);
		return "delete";
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<App> getList() {
		return list;
	}

	public void setList(List<App> list) {
		this.list = list;
	}

	public List<List<App>> getAppList() {
		return appList;
	}

	public void setAppList(List<List<App>> appList) {
		this.appList = appList;
	}

	public Integer getDeskNo() {
		return deskNo;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public void setDeskNo(Integer deskNo) {
		this.deskNo = deskNo;
	}

	public Integer getOldDeskNo() {
		return oldDeskNo;
	}

	public void setOldDeskNo(Integer oldDeskNo) {
		this.oldDeskNo = oldDeskNo;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
