package com.celloud.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.NoticeConstants;
import com.celloud.model.mysql.Notice;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.NoticeService;

@Controller
@RequestMapping("notice")
public class NoticeAction {
    @Resource
    private NoticeService noticeService;

    @RequestMapping("lastUnread/{type}")
    public String lastUnread(@PathVariable String type, Model model) {
        if (NoticeConstants.TYPE_MESSAGE.equals(type)) {
            PageList<Notice> pageList = noticeService.findLastUnreadMessage();
            model.addAttribute("pageList", pageList);
        } else {
            type = NoticeConstants.TYPE_NOTICE;
        }
        model.addAttribute("type", type);
        return "notice/lastUnread";
    }

    @RequestMapping("list/{type}")
    public ModelAndView list(@PathVariable String type, Page page) {
        ModelAndView mv = new ModelAndView("notice/list");
        if (NoticeConstants.TYPE_MESSAGE.equals(type)) {
            PageList<Notice> pageList = noticeService.findLastMessage(page);
            mv.addObject("messageList", pageList);
        } else {
            type = NoticeConstants.TYPE_NOTICE;
        }
        mv.addObject("type", type);
        return mv;
    }
}
