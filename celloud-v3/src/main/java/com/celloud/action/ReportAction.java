package com.celloud.action;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
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
        long s = new Date().getTime();
        System.out.println(condition);
        System.out.println(start);
        System.out.println(end);
        System.out.println(appId);
        Integer userId = ConstantsData.getLoginUserId();
        ModelAndView mv = new ModelAndView("report/report_list");
        Page pager = new Page(page, size);
        PageList<Map<String, Object>> pageList = reportService
                .getReportPageList(userId, pager, condition, start, end, appId);
        long e = new Date().getTime();
        System.out.println(e - s);
        return mv.addObject("pageList", pageList);
    }
}
