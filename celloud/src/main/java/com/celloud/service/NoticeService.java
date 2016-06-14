package com.celloud.service;

import com.celloud.model.mysql.Notice;
import com.celloud.page.Page;
import com.celloud.page.PageList;

public interface NoticeService {

    public void insertNotice(Notice notice, String... usernames);

    public PageList<Notice> findLastUnreadNotice();

    public PageList<Notice> findLastUnreadMessage();

    public PageList<Notice> findUnreadNotice(Page page);

    public PageList<Notice> findLastMessage(Page page);

}
