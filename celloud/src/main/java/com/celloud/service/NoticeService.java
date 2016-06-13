package com.celloud.service;

import com.celloud.model.mysql.Notice;

public interface NoticeService {
    public void insertNotice(Notice notice, Integer... userIds);

    public void insertNotice(Notice notice, String username);

}
