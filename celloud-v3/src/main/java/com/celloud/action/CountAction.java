package com.celloud.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.Constants;
import com.celloud.constants.TimeState;
import com.celloud.model.User;
import com.celloud.service.AppService;
import com.celloud.service.DataService;
import com.celloud.service.ReportService;

/**
 * 总览相关action
 *
 * @author han
 * @date 2015年12月25日 下午3:34:05
 */
@Controller
@RequestMapping("count")
public class CountAction {
    private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(CountAction.class);
    @Resource
    private DataService dataService;
    @Resource
    private ReportService reportService;
    @Resource
    private AppService appService;

    /**
     * 控制台统计
     * 
     * @return
     */
    @RequestMapping("loginCount.action")
    public ModelAndView loginCount(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("count/count_user");
        Integer userId = getLoginUserId(request);
        Integer countData = dataService.countData(userId);
        Long sumData = dataService.sumData(userId);
        Integer countReport = reportService.countReport(userId);
        Integer countApp = appService.countMyApp(userId);
        mv.addObject("countData", countData);
        mv.addObject("sumData", sumData);
        mv.addObject("countReport", countReport);
        mv.addObject("countApp", countApp);
        return mv;
    }

    /**
     * 按照月份统计文件
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("fileMonthCount.action")
    public List<Map<String, String>> fileMonthCount(HttpServletRequest request, HttpServletResponse response) {
        Integer userId = getLoginUserId(request);
        List<Map<String, String>> monthData = dataService.countData(userId, TimeState.MONTH);
        return monthData;
    }
    
    /**
     * 按照月份统计文件大小
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("fileSizeMonthCount.action")
    public List<Map<String, String>> fileSizeMonthCount(HttpServletRequest request, HttpServletResponse response) {
        Integer userId = getLoginUserId(request);
        List<Map<String, String>> monthData = dataService.sumData(userId, TimeState.MONTH);
        return monthData;
    }

    /**
     * 按照天统计文件
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("fileDayCount.action")
    public List<Map<String, String>> fileDayCount(HttpServletRequest request, HttpServletResponse response) {
        Integer userId = getLoginUserId(request);
        List<Map<String, String>> dayData = dataService.countData(userId, TimeState.DAY);
        return dayData;
    }
    
    /**
     * 按照天统计文件大小
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("fileSizeDayCount.action")
    public List<Map<String, String>> fileSizeDayCount(HttpServletRequest request, HttpServletResponse response) {
        Integer userId = getLoginUserId(request);
        List<Map<String, String>> dayData = dataService.sumData(userId, TimeState.DAY);
        return dayData;
    }
    
    /**
     * 按照月份统计报告
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("reportMonthCount.action")
    public List<Map<String, String>> reportMonthCount(HttpServletRequest request, HttpServletResponse response) {
        Integer userId = getLoginUserId(request);
        List<Map<String, String>> dayData = reportService.countReport(userId, TimeState.MONTH);
        return dayData;
    }
    
    /**
     * 按照天统计报告
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("reportDayCount.action")
    public List<Map<String, String>> reportDayCount(HttpServletRequest request, HttpServletResponse response) {
        Integer userId = getLoginUserId(request);
        List<Map<String, String>> dayData = reportService.countReport(userId, TimeState.DAY);
        return dayData;
    }
    
    /**
     * 按照月份统计报告
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("appMonthCount.action")
    public List<Map<String, String>> appMonthCount(HttpServletRequest request, HttpServletResponse response) {
        Integer userId = getLoginUserId(request);
        List<Map<String, String>> dayData = appService.countMyApp(userId, TimeState.MONTH);
        return dayData;
    }
    
    /**
     * 按照天统计报告
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("appDayCount.action")
    public List<Map<String, String>> appDayCount(HttpServletRequest request, HttpServletResponse response) {
        Integer userId = getLoginUserId(request);
        List<Map<String, String>> dayData = appService.countMyApp(userId, TimeState.DAY);
        return dayData;
    }

    private Integer getLoginUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_LOGIN_USER);
        return user != null ? user.getUserId() : null;
    }

}
