package com.celloud.service.impl;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;

import com.celloud.mapper.NoticeMapper;
import com.celloud.model.mysql.Notice;
import com.celloud.service.NoticeService;

public class NoticeServiceImpl implements NoticeService {
    @Resource
    private NoticeMapper noticeMapper;

    @Async
    @Override
    public void insertNotice(Notice notice, Integer... userIds) {
        noticeMapper.insert(notice);
        noticeMapper.insertNoticeUser(notice.getNoticeId(), userIds);
    }

    @Async
    @Override
    public void insertNotice(Notice notice, String username) {
        noticeMapper.insert(notice);
//        noticeMapper.insertNoticeUser(notice.getNoticeId(), username);
    }
}
