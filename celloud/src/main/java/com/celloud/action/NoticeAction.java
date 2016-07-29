package com.celloud.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.NoticeConstants;
import com.celloud.model.mysql.Notice;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.NoticeService;
import com.celloud.utils.ActionLog;
import com.celloud.utils.Response;

@Controller
@RequestMapping("notice")
public class NoticeAction {
	@Resource
	private NoticeService noticeService;

	@ActionLog(value = "获取未读消息")
	@RequestMapping("lastUnread/{type}")
	public String lastUnread(@PathVariable String type, Model model) {
		if (NoticeConstants.TYPE_MESSAGE.equals(type)) {
			PageList<Notice> pageList = noticeService.findLastUnreadMessage();
			model.addAttribute("messageList", pageList);
		} else {
			PageList<Notice> pageList = noticeService.findLastUnreadNotice();
			model.addAttribute("noticeList", pageList);
			type = NoticeConstants.TYPE_NOTICE;
		}
		model.addAttribute("type", type);
		return "notice/lastUnread";
	}

	@ActionLog(value = "获取消息", button = "查看所有")
	@RequestMapping("list/{type}")
	public ModelAndView list(@PathVariable String type, Page page) {
		ModelAndView mv = new ModelAndView("notice/list");
		if (NoticeConstants.TYPE_MESSAGE.equals(type)) {
			PageList<Notice> pageList = noticeService.findLastMessage(page);
			mv.addObject("messageList", pageList);
		} else {
			page.setPageSize(5);
			PageList<Notice> pageList = noticeService.findLastNotice(page);
			mv.addObject("noticeList", pageList);
			type = NoticeConstants.TYPE_NOTICE;
		}
		mv.addObject("type", type);
		return mv;
	}

	@ActionLog(value = "获取一个消息")
	@RequestMapping("detail")
	public String detail(Integer noticeId, Model model) {
		Notice notice = noticeService.getNoticeById(noticeId);
		model.addAttribute("notice", notice);
		return "notice/detail";
	}

	@ActionLog(value = "删除消息", button = "删除")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Response delete(Integer[] noticeIds) {
		noticeService.deleteMessages(noticeIds);
		return Response.DELETE_SUCCESS;
	}

	@ActionLog(value = "读消息", button = "已读")
	@RequestMapping(value = "read", method = RequestMethod.POST)
	@ResponseBody
	public Response read(Integer[] noticeIds) {
		noticeService.readMessage(noticeIds);
		return Response.UPDATE_SUCCESS;
	}

	@ActionLog(value = "获所有消息", button = "全部置为已读")
	@RequestMapping(value = "readAll/{type}", method = RequestMethod.POST)
	@ResponseBody
	public Response readAll(@PathVariable @RequestParam(required = true) String type) {
		if (NoticeConstants.TYPE_MESSAGE.equals(type)) {
			noticeService.readAllMessage();
		} else {
			noticeService.readAllNotice();
		}
		return Response.UPDATE_SUCCESS;
	}
}
