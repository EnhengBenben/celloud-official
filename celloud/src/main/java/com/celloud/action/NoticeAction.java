package com.celloud.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@ResponseBody
	@RequestMapping("lastUnread/{type}")
	public Map<String, Object> lastUnread(@PathVariable String type) {
		// TODO 修改service和mapper，改为只查询数量
		PageList<Notice> pageList = null;
		if (NoticeConstants.TYPE_MESSAGE.equals(type)) {
			pageList = noticeService.findLastUnreadMessage();
		} else {
			pageList = noticeService.findLastUnreadNotice();
			type = NoticeConstants.TYPE_NOTICE;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("num", pageList.getPage().getRowCount());
		return map;
	}

	@ActionLog(value = "获取消息", button = "查看所有")
	@ResponseBody
	@RequestMapping("list/{type}")
	public PageList<Notice> list(@PathVariable String type, Page page) {
		PageList<Notice> pageList = null;
		if (NoticeConstants.TYPE_MESSAGE.equals(type)) {
			pageList = noticeService.findLastMessage(page);
		} else {
			pageList = noticeService.findLastNotice(page);
			type = NoticeConstants.TYPE_NOTICE;
		}
		return pageList;
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

	@ActionLog(value = "全部置为已读", button = "全部置为已读")
	@RequestMapping(value = "readAll/{type}", method = RequestMethod.POST)
	@ResponseBody
	public Response readAll(@PathVariable String type) {
		if (NoticeConstants.TYPE_MESSAGE.equals(type)) {
			noticeService.readAllMessage();
		} else if (NoticeConstants.TYPE_NOTICE.equals(type)) {
			noticeService.readAllNotice();
		}
		return Response.UPDATE_SUCCESS;
	}
}
