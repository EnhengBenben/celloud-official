package com.celloud.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.Constants;
import com.celloud.model.DataFile;
import com.celloud.model.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.DataService;

/**
 * 数据管理
 * 
 * @author liuqx
 * @date 2015-12-30 下午4:08:06
 */
@Controller
@RequestMapping("data")
public class DataAction {
    Logger logger = LoggerFactory.getLogger(DataAction.class);
    @Resource
    private DataService dataService;

    /**
     * 获取全部数据列表
     * 
     * @param session
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("dataAllList.action")
    public ModelAndView dataAllList(HttpSession session,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        ModelAndView mv = new ModelAndView("data/data_list");
        Page pager = new Page(page, size);
        PageList<DataFile> dataList = dataService.dataAllList(pager,
                getSessionUserId(session));
        mv.addObject("dataList", dataList);
        return mv;
    }

    /**
     * 根据条件获取数据列表
     * 
     * @param session
     * @param page
     *            当前页
     * @param size
     *            每页行数
     * @param condition
     *            检索条件
     * @param sort
     *            排序字段 0:create_date 1:file_name
     * @param sortType
     *            排序类型
     * @return
     */
    @RequestMapping("dataList.action")
    public ModelAndView dataList(
            HttpSession session,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size, String condition,
            @RequestParam(defaultValue = "0") int sort,
            @RequestParam(defaultValue = "desc") String sortDateType,
            @RequestParam(defaultValue = "asc") String sortNameType) {
        ModelAndView mv = new ModelAndView("data/data_list");
        Page pager = new Page(page, size);
        PageList<DataFile> dataList = dataService.dataLists(pager,
                getSessionUserId(session), condition, sort, sortDateType,
                sortNameType);
        mv.addObject("dataList", dataList);
        return mv;
    }

    public Integer getSessionUserId(HttpSession session) {
        User user = (User) session.getAttribute(Constants.SESSION_LOGIN_USER);
        return user != null ? user.getUserId() : null;
    }
}
