package com.celloud.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.model.mysql.Notice;
import com.celloud.page.PageList;
import com.celloud.service.NoticeService;

@Controller
@RequestMapping("notice")
public class NoticeAction {
    @Resource
    private NoticeService noticeService;

    @RequestMapping("lastUnread")
    public ModelAndView lastUnread() {
        ModelAndView mv = new ModelAndView("notice/lastUnread");
        PageList<Notice> pageList = noticeService.findLastUnreadNotice();
        mv.addObject("pageList", pageList);
        return mv;
    }
}
