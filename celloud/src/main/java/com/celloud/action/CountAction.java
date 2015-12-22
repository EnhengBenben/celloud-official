package com.celloud.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.celloud.constants.TimeState;
import com.celloud.service.AppService;
import com.celloud.service.DataService;
import com.celloud.service.ReportService;
import com.google.inject.Inject;
import com.nova.action.BaseAction;

@ParentPackage("celloud-default")
@Action("count3")
@Results({
        @Result(name = "userCount", location = "../../pages/count/userCount.jsp"),
        @Result(name = "fileMonthCount", type = "json", params = { "root",
                "monthData" }),
        @Result(name = "fileDayCount", type = "json", params = { "root",
                "dayData" }) })
public class CountAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(CountAction.class);
    @Inject
    private DataService dataService;
    @Inject
    private ReportService reportService;
    @Inject
    private AppService appService;
    private Integer userId;
    private Long sumData;
    private Integer countData;
    private Integer countReport;
    private Integer countApp;
    private List<Map<String, String>> monthData;
    private List<Map<String, String>> dayData;

    /**
     * 控制台统计
     * 
     * @return
     */
    public String loginCount() {
        userId = (Integer) super.session.get("userId");
        countData = dataService.countData(userId);
        sumData = dataService.sumData(userId);
        countReport = reportService.countReport(userId);
        countApp = appService.getMyAppList(userId).size();
        return "userCount";
    }

    /**
     * 按照月份统计文件
     * 
     * @return
     */
    public String fileMonthCount() {
        userId = (Integer) super.session.get("userId");
        monthData = dataService.countData(userId, TimeState.MONTH);
        return "fileMonthCount";
    }

    /**
     * 按照天统计文件
     * 
     * @return
     */
    public String fileDayCount() {
        userId = (Integer) super.session.get("userId");
        dayData = dataService.countData(userId, TimeState.DAY);
        return "fileDayCount";
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getSumData() {
        return sumData;
    }

    public void setSumData(Long sumData) {
        this.sumData = sumData;
    }

    public Integer getCountData() {
        return countData;
    }

    public void setCountData(Integer countData) {
        this.countData = countData;
    }

    public Integer getCountReport() {
        return countReport;
    }

    public void setCountReport(Integer countReport) {
        this.countReport = countReport;
    }

    public Integer getCountApp() {
        return countApp;
    }

    public void setCountApp(Integer countApp) {
        this.countApp = countApp;
    }

    public List<Map<String, String>> getMonthData() {
        return monthData;
    }

    public void setMonthData(List<Map<String, String>> monthData) {
        this.monthData = monthData;
    }

    public List<Map<String, String>> getDayData() {
        return dayData;
    }

    public void setDayData(List<Map<String, String>> dayData) {
        this.dayData = dayData;
    }

}
