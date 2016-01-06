package com.celloud.action;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.Constants;
import com.celloud.model.User;
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
            HttpSession session,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE + "") Integer size,
            String condition, String start, String end, Integer appId) {
        long s = new Date().getTime();
        System.out.println("------"+page);
        System.out.println(size);
        System.out.println(condition);
        System.out.println(start);
        System.out.println(end);
        System.out.println(appId);
//        Constants.SESSION_LOGIN_USER
        User user = (User) session.getAttribute(Constants.SESSION_LOGIN_USER);
        System.out.println(user==null);
        System.out.println(user.getUserId());
        ModelAndView mv = new ModelAndView("report/report_list");
        Page pager = new Page(page, size);
        PageList<Map<String, Object>> pageList = reportService
                .getReportPageList(user.getUserId(), pager, condition, start, end, appId);
        System.out.println(pageList.getDatas().size());
        // System.out.println(pageList.getDatas().get(0).get("project_id"));
        Map<String, Object> map = pageList.getDatas().get(0);
        for (Entry<String, Object> i : map.entrySet()) {
            System.out.println("key:" + i.getKey());
            System.out.println("val:" + i.getValue());
            System.out.println("--");
        }
        long e = new Date().getTime();
        System.out.println(e - s);
        return mv.addObject("pageList", pageList);
    }
}
