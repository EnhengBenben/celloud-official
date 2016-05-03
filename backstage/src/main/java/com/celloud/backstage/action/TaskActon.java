package com.celloud.backstage.action;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.TaskService;

/**
 * 
 *
 * @author han
 * @date 2016年1月27日 下午4:11:05
 */
@RequestMapping("/task")
@Controller
public class TaskActon {
    Logger logger = LoggerFactory.getLogger(TaskActon.class);
    @Resource
    private TaskService taskService;

    @RequestMapping("/getQueuingTime")
    public ModelAndView getQueuingTime() {
        ModelAndView mv = new ModelAndView("task/task_queuing_time");
        Map<String, String> map = taskService.getQueuingTime();
        mv.addObject("map", map);
        return mv;
    }

    @RequestMapping("/getRunningTime")
    public ModelAndView getRunningTimeByPage(@RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int size, @RequestParam("keyword") String keyword) {
        ModelAndView mv = new ModelAndView("task/task_running_time");
        Page page = new Page(currentPage, size);
        PageList<Map<String, String>> pageList = taskService.getRunningTimeByPage(page,
                keyword != null ? keyword.trim() : keyword);
        mv.addObject("pageList", pageList);
        mv.addObject("keyword", keyword);
        return mv;
    }

    @RequestMapping("/getQuantityStatistics")
    public ModelAndView getQuantityStatistics() {
        ModelAndView mv = new ModelAndView("task/task_quantity_statistics");
        Map<String, Integer> map = taskService.getQuantityStatistics();
        mv.addObject("map", map);
        return mv;
    }
}
