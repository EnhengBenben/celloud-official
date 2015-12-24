/**  */
package com.celloud.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.celloud.sdo.App;
import com.celloud.sdo.User;
import com.celloud.service.AppService;
import com.google.inject.Inject;

@ParentPackage("json-default")
@Action("app")
@Results({
		@Result(name = "appList", location = "../../pages/appList.jsp"),
		@Result(name = "oneApp", location = "../../pages/appOne.jsp"),
		@Result(name = "appActivity", location = "../../pages/appActivity.jsp"),
		
		@Result(name = "success", type = "json", params = { "root", "fileName" }),
		@Result(name = "AppList", type = "json", params = { "root", "appList" }),

		})

public class AppAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Inject
	private AppService appService;
	private List<App> appList;
	private Integer compId;
	private App app;
	private User user;
	private Date startDate;
	private Date endDate;
	private String softwareId;
	private int topN=10;
	private String type;//分组月、周
	
	/**
	 * 获取App的运行信息
	 * @return
	 */
	public String getAppListByBigUser() {
		Integer cmpId = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		appList = appService.getAppListByBigUser(cmpId,role);
		return "appList";
	}
	public String appRunTop(){
		topN=10;
	    appList = appService.getAppRunTop(type, topN, startDate, endDate);
		return "AppList";
	}
	/**
	 *  获取App的运行信息
	 * @return JSON
	 */
	public String getAppListByBigUserJson() {
		compId = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		appList = appService.getAppListByBigUser(compId,role);
		return "AppList";
	}
	public String toAppActivity(){
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		Integer cmpId = (Integer) getCid();
		appList = appService.getAppByCompanyId(cmpId, role);
		return "appActivity";
	}
	/**
	 * 统计各app在时间段内周运行次数
	 * @return
	 */
	public String getAppRunWeek(){
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		Integer cmpId = (Integer) getCid();
		log.info(softwareId);
		appList = appService.getAppRunTimeInWeek(cmpId,user.getUser_id(), startDate, endDate,role,softwareId);
		return "AppList";
	}
	/**
	 * 统计各app在时间段内月运行次数
	 * @return
	 */
	public String getAppRunMonth(){
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		Integer cmpId = (Integer) getCid();
		log.info(softwareId);
		appList = appService.getAppRunTimeInMonth(cmpId,user.getUser_id(), startDate, endDate,role,softwareId);
		return "AppList";
	}
	
	public String getAppById() {
		app = appService.getAppById(app.getApp_id());
		return "oneApp";
	}

	public List<App> getAppList() {
		return appList;
	}

	public void setAppList(List<App> appList) {
		this.appList = appList;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public int getTopN() {
		return topN;
	}
	public void setTopN(int topN) {
		this.topN = topN;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		SimpleDateFormat sdf = null;
		if(startDate!=null && startDate.length()==7)//yyyy-MM
			sdf = new SimpleDateFormat("yyyy-MM");
		else if(startDate!=null & startDate.length()==10)
			sdf= new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.startDate = sdf.parse(startDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		SimpleDateFormat sdf = null;
		if(endDate!=null && endDate.length()==7)//yyyy-MM
			sdf = new SimpleDateFormat("yyyy-MM");
		else if(endDate!=null & endDate.length()==10)
			sdf= new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.endDate = sdf.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public String getSoftwareId() {
		return softwareId;
	}
	public void setSoftwareId(String softwareId) {
		this.softwareId = softwareId;
	}
	
}
