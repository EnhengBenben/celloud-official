package com.nova.action;

import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.google.inject.Inject;
import com.nova.sdo.Notice;
import com.nova.service.INoticeService;

@ParentPackage("celloud-default")
@Action("notice")
@Results({
	@Result(name = "success", type = "json", params = { "root", "notice" }),
	@Result(name = "add", type = "json", params = { "root", "flag" }),
	@Result(name = "edit", type = "json", params = { "root", "flag" }),
	@Result(name = "getAll", location = "/pages/admin/noticeList.jsp"),
	@Result(name = "getNoticeById", location = "/pages/admin/editNotice.jsp") })
public class NoticeAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    @Inject
    private INoticeService noticeService;

    private Notice notice;
    private int noticeId;
    private List<Notice> list;
    private boolean flag;

    public String getNewsNotice() {
	notice = noticeService.getNewsNotice();
	if (notice != null) {
	    Cookie noticeCreateDate = new Cookie("noticeId",
		    String.valueOf(notice.getNoticeId()));
	    noticeCreateDate.setMaxAge(60 * 60 * 24 * 7);
	    noticeCreateDate.setPath("/");
	    response.addCookie(noticeCreateDate);
	}
	return SUCCESS;
    }

    public String addNotice() {
	flag = noticeService.addNotice(notice);
	return "add";
    }

    public String editNotice() {
	noticeService.editNotice(notice.getNoticeTitle(),
		notice.getNoticeContext(), notice.getNoticeId());
	return "edit";
    }

    public String deleteNotice() {
	noticeService.deleteNotice(notice.getNoticeId());
	return SUCCESS;
    }

    public String getAllNotice() {
	list = noticeService.getAllNotice();
	return "getAll";
    }

    public String getNoticeById() {
	notice = noticeService.getNoticeById(noticeId);
	return "getNoticeById";
    }

    public List<Notice> getList() {
	return list;
    }

    public void setList(List<Notice> list) {
	this.list = list;
    }

    public boolean isFlag() {
	return flag;
    }

    public void setFlag(boolean flag) {
	this.flag = flag;
    }

    public int getNoticeId() {
	return noticeId;
    }

    public void setNoticeId(int noticeId) {
	this.noticeId = noticeId;
    }

    public Notice getNotice() {
	return notice;
    }

    public void setNotice(Notice notice) {
	this.notice = notice;
    }
}
