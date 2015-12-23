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
import com.celloud.sdo.Software;
import com.celloud.sdo.User;
import com.celloud.service.SoftwareService;
import com.google.inject.Inject;

@ParentPackage("json-default")
@Action("app")
@Results({
		@Result(name = "appList", location = "../../pages/appList.jsp"),
		@Result(name = "oneApp", location = "../../pages/appOne.jsp"),
		@Result(name = "appActivity", location = "../../pages/appActivity.jsp"),
		@Result(name = "success", type = "json", params = { "root", "fileName" }),
		@Result(name = "appListJson", type = "json", params = { "root", "appList" }),
		@Result(name = "appRunMonth", type = "json", params = { "root", "appList" }),
		@Result(name = "appRunWeek", type = "json", params = { "root", "appList" }),
		@Result(name = "appRunTop", type = "json", params = { "root", "appList" }),
		})

public class AppAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Inject
	private SoftwareService appService;
	private List<Software> appList;
	private Integer compId;
	private Software app;
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
		return "appRunTop";
	}
	/**
	 *  获取App的运行信息
	 * @return JSON
	 */
	public String getAppListByBigUserJson() {
		compId = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		appList = appService.getAppListByBigUser(compId,role);
		return "appListJson";
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
		appList = appService.getAppRunTimeInWeek(cmpId,user.getUserId(), startDate, endDate,role,softwareId);
		return "appRunWeek";
	}
	/**
	 * 统计各app在时间段内月运行次数
	 * @return
	 */
	public String getAppRunMonth(){
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		Integer cmpId = (Integer) getCid();
		log.info(softwareId);
		appList = appService.getAppRunTimeInMonth(cmpId,user.getUserId(), startDate, endDate,role,softwareId);
		return "appRunMonth";
	}
	
	public String getAppById() {
		app = appService.getAppById(app.getSoftwareId());
		return "oneApp";
	}

	public List<Software> getAppList() {
		return appList;
	}

	public void setAppList(List<Software> appList) {
		this.appList = appList;
	}

	public Software getApp() {
		return app;
	}

	public void setApp(Software app) {
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
