package com.nova.service.impl;

import java.util.List;

import com.google.inject.Inject;
import com.nova.dao.INoticeDao;
import com.nova.sdo.Notice;
import com.nova.service.INoticeService;

public class NoticeServiceImpl implements INoticeService {
	@Inject
	private INoticeDao noticeDao;

	/**
	 * @Methods: addNotice
	 * @Description: 新增公告
	 * @Author: ASUS
	 * @Date: 2012-6-28
	 */
	public boolean addNotice(Notice notice) {
		boolean flag = noticeDao.addNotice(notice);
		return flag;
	}

	/**
	 * @Methods: deleteNotice
	 * @Description: 失效公告
	 * @Author: ASUS
	 * @Date: 2012-6-28
	 */
	public void deleteNotice(int noticeId) {
		noticeDao.deleteNotice(noticeId);
	}

	/**
	 * @Methods: editNotice
	 * @Description: 编辑公告
	 * @Author: ASUS
	 * @Date: 2012-6-28
	 */
	public void editNotice(String noticeTitle, String noticeContext,
			int noticeId) {
		noticeDao.editNotice(noticeTitle, noticeContext, noticeId);
	}

	/**
	 * @Methods: getNoticeById
	 * @Description: 根据编号查询公告
	 * @Author: ASUS
	 * @Date: 2012-6-28
	 */
	public Notice getNoticeById(int noticeId) {
		Notice notice = noticeDao.getNoticeById(noticeId);
		return notice;
	}

	/**
	 * @Methods: getAllNotice
	 * @Description: 查询所有公告
	 * @Author: ASUS
	 * @Date: 2012-6-28
	 */
	public List<Notice> getAllNotice() {
		return noticeDao.getAllNotice();
	}

	/**
	 * @Methods: getNewsNotice
	 * @Description: 查询最新公告
	 * @Author: ASUS
	 * @Date: 2012-6-28
	 */
	public Notice getNewsNotice() {
		return noticeDao.getNewsNotice();
	}

}
