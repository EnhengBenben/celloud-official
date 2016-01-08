package com.celloud.action;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.model.HBV;
import com.celloud.model.Pgs;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.ReportService;

@RequestMapping(value = "/report")
@Controller
public class ReportAction {
    @Resource
    private ReportService reportService;

    @RequestMapping("getReportPageList")
    public ModelAndView reportPages(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE + "") Integer size,
            String condition, String start, String end, Integer appId) {
        Integer userId = ConstantsData.getLoginUserId();
        ModelAndView mv = new ModelAndView("report/report_list");
        Page pager = new Page(page, size);
        PageList<Map<String, Object>> pageList = reportService
                .getReportPageList(userId, pager, condition, start, end, appId);
        return mv.addObject("pageList", pageList);
    }

    /**
     * 获取HBV的数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-8 下午4:49:31
     */
    @RequestMapping("getHBVReport")
    public ModelAndView getHBVReport(String dataKey, Integer projectId,
            Integer appId) {
        HBV hbv = reportService.getHBVReport(dataKey, projectId, appId);
        ModelAndView mv = new ModelAndView("report/report_data_hbv");
        return mv.addObject("hbv", hbv);
    }

    /**
     * 获取PGS的数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-9 上午2:57:38
     */
    @RequestMapping("getPgsReport")
    public ModelAndView getPgsReport(String dataKey, Integer projectId,
            Integer appId) {
        Pgs pgs = reportService.getPgsReport(dataKey, projectId, appId);
        ModelAndView mv = new ModelAndView("report/report_data_pgs");
        return mv.addObject("pgs", pgs);
    }
}
