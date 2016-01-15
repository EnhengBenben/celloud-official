package com.celloud.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.model.CmpReport;
import com.celloud.model.DataFile;
import com.celloud.model.HBV;
import com.celloud.model.MIB;
import com.celloud.model.Pgs;
import com.celloud.model.Split;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.DataService;
import com.celloud.service.ReportService;
import com.celloud.utils.HttpURLUtils;
import com.celloud.utils.PropertiesUtil;

@RequestMapping(value = "/report")
@Controller
public class ReportAction {
    @Resource
    private ReportService reportService;
    @Resource
    private DataService dataService;

    /**
     * 获取报告模块列表
     * 
     * @param page
     * @param size
     * @param condition
     * @param start
     * @param end
     * @param appId
     * @return
     * @date 2016-1-10 下午10:36:57
     */
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
     * 从 Tools 端获取数据报告
     * 
     * @param dataKey
     * @param url
     * @return
     * @date 2016-1-10 下午11:37:40
     */
    @ResponseBody
    @RequestMapping("getReportFromTools")
    public String getReportFromTools(String dataKey, String url) {
        url = PropertiesUtil.toolsPath + url;
        DataFile data = dataService.getDataByKey(dataKey);
        String anotherName = data.getAnotherName();
        if (!StringUtils.isEmpty(anotherName)) {
            url += "&anotherName=" + anotherName;
        }
        return HttpURLUtils.getHTTPResult(url);
    }

    /**
     * 用于 ModelAndView 加载共有参数
     * 
     * @param path
     * @return
     * @author lin
     * @date 2016-1-11 上午10:39:13
     */
    private ModelAndView getModelAndView(String path) {
        ModelAndView mv = new ModelAndView(path);
        mv.addObject("toolsPath", PropertiesUtil.toolsPath);
        mv.addObject("uploadPath", PropertiesUtil.toolsOutPath + "upload/");
        return mv;
    }

    /**
     * 获取 CMP 报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-10 下午10:57:24
     */
    @RequestMapping("getCMPReport")
    public ModelAndView getCMPReport(String dataKey, Integer projectId,
            Integer appId) {
        CmpReport cmpReport = reportService.getCMPReport(dataKey, projectId,
                appId);
        ModelAndView mv = getModelAndView("report/report_data_cmp");
        return mv.addObject("cmpReport", cmpReport);
    }

    /**
     * 获取 GDD 报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-10 下午10:57:13
     */
    @RequestMapping("getGDDReport")
    public ModelAndView getGDDReport(String dataKey, Integer projectId,
            Integer appId) {
        CmpReport cmpReport = reportService.getCMPReport(dataKey, projectId,
                appId);
        ModelAndView mv = getModelAndView("report/report_data_gdd");
        return mv.addObject("cmpReport", cmpReport);
    }

    /**
     * 获取 Split 数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-10 下午10:44:45
     */
    @RequestMapping("getSplitReport")
    public ModelAndView getSplitReport(String dataKey, Integer projectId,
            Integer appId) {
        Split split = reportService.getSplitReport(dataKey, projectId, appId);
        ModelAndView mv = getModelAndView("report/report_data_split");
        return mv.addObject("split", split);
    }

    /**
     * 获取 MIB 的数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-10 下午10:40:40
     */
    @RequestMapping("getMIBReport")
    public ModelAndView getMIBReport(String dataKey, Integer projectId,
            Integer appId) {
        MIB mib = reportService.getMIBReport(dataKey, projectId, appId);
        ModelAndView mv = getModelAndView("report/report_data_mib");
        return mv.addObject("mib", mib);
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
        ModelAndView mv = getModelAndView("report/report_data_hbv");
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
        ModelAndView mv = getModelAndView("report/report_data_pgs");
        return mv.addObject("pgs", pgs);
    }
}
