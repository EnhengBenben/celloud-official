package com.celloud.service;

import com.celloud.model.mysql.Notice;
import com.celloud.page.PageList;

public interface NoticeService {
    public void insertNotice(Notice notice, Integer... userIds);

    public void insertNotice(Notice notice, String... usernames);

    public PageList<Notice> findLastUnreadNotice();

}
