package com.celloud.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.celloud.sdo.Data;
import com.celloud.service.DataService;
import com.google.inject.Inject;
import com.nova.action.BaseAction;
import com.nova.pager.Page;
import com.nova.pager.PageList;

/**
 * v3.0数据管理
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-14上午10:22:21
 * @version Revision: 1.0
 */
@ParentPackage("celloud-default")
@Action("data3")
@Results({
	@Result(name = "success", type = "json", params = { "root",
		"conditionInt" }),
	@Result(name = "toDataManage", location = "../../pages/data/myData.jsp"),
	@Result(name = "info", type = "json", params = { "root", "result" }),
	@Result(name = "getSoftList", type = "redirect", location = "software!getSoftByFormat", params = {
		"condition", "${condition}" }),
	@Result(name = "checkDataRunningSoft", type = "json", params = {
		"root", "intList" }),
	@Result(name = "toMoreInfo", location = "../../pages/data/moreDataInfo.jsp") })
public class DataAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(DataAction.class);
    @Inject
    private DataService dataService;
    private PageList<Data> dataPageList;
    private Data data;
    private List<Integer> intList;
    private List<Map<String, String>> mapList;
    private Integer userId;
    private Page page = new Page(50, 0);
    private String sortByName;
    private String sortByDate;
    private String condition;
    private Integer conditionInt;
    private String result;

    public String getAllData() {
	userId = (Integer) super.session.get("userId");
	log.info("用户" + super.session.get("userName") + "访问数据管理页面");
	dataPageList = dataService.getAllData(page, userId);
	return "toDataManage";
    }

    public String getDataByCondition() {
	userId = (Integer) super.session.get("userId");
	log.info("用户" + super.session.get("userName") + "根据条件搜索数据");
	dataPageList = dataService.getDataByCondition(page, userId,
		conditionInt, sortByName, sortByDate, condition);
	return "toDataManage";
    }

    public String getSoftListByFormat() {
	log.info("用户" + super.session.get("userName") + "获取数据可运行的APP");
	Map<String, Integer> formatMap = dataService
		.getFormatNumByIds(condition);
	if (formatMap.get("formatNum") != null
		&& formatMap.get("formatNum") > 1) {
	    result = "所选数据格式不统一！";
	    return "info";
	} else {
	    condition = String.valueOf(formatMap.get("format"));
	    return "getSoftList";
	}
    }

    public String checkDataRunningSoft() {
	log.info("验证用户" + super.session.get("userName") + "为数据" + condition
		+ "选择APP" + conditionInt);
	intList = dataService.getRunningDataBySoft(condition, conditionInt);
	return "checkDataRunningSoft";
    }

    public String deleteData() {
	log.info("用户" + super.session.get("userName") + "删除数据" + condition);
	conditionInt = dataService.deleteDataByIds(condition);
	return "success";
    }

    public String getMoreData() {
	log.info("用户" + super.session.get("userName") + "获取数据" + conditionInt
		+ "别名、物种、样本类型等信息");
	userId = (Integer) super.session.get("userId");
	data = dataService.getDataAndStrain(userId, conditionInt);
	return "toMoreInfo";
    }

    public PageList<Data> getDataPageList() {
	return dataPageList;
    }

    public void setDataPageList(PageList<Data> dataPageList) {
	this.dataPageList = dataPageList;
    }

    public int getUserId() {
	return userId;
    }

    public void setUserId(int userId) {
	this.userId = userId;
    }

    public Page getPage() {
	return page;
    }

    public void setPage(Page page) {
	this.page = page;
    }

    public String getSortByName() {
	return sortByName;
    }

    public void setSortByName(String sortByName) {
	this.sortByName = sortByName;
    }

    public String getSortByDate() {
	return sortByDate;
    }

    public void setSortByDate(String sortByDate) {
	this.sortByDate = sortByDate;
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

    public String getResult() {
	return result;
    }

    public void setResult(String result) {
	this.result = result;
    }

    public List<Integer> getIntList() {
	return intList;
    }

    public void setIntList(List<Integer> intList) {
	this.intList = intList;
    }

    public Data getData() {
	return data;
    }

    public void setData(Data data) {
	this.data = data;
    }

    public List<Map<String, String>> getMapList() {
	return mapList;
    }

    public void setMapList(List<Map<String, String>> mapList) {
	this.mapList = mapList;
    }

}
