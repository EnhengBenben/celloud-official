package com.celloud.service;

import com.celloud.model.mysql.Notice;
import com.celloud.page.Page;
import com.celloud.page.PageList;

public interface NoticeService {

    public void insertMessage(Notice notice, String... usernames);

    public PageList<Notice> findLastUnreadNotice();

    public PageList<Notice> findLastUnreadMessage();

    public PageList<Notice> findLastMessage(Page page);

    public void readMessage(Integer[] noticeIds);

    public void readAllMessage();

    public void deleteMessages(Integer[] noticeIds);

    public PageList<Notice> findLastNotice(Page page);

    public Notice getNoticeById(Integer noticeId);

	public void readAllNotice();

}
