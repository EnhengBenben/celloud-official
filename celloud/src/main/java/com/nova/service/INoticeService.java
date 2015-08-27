package com.nova.service;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.nova.sdo.Notice;
import com.nova.service.impl.NoticeServiceImpl;

@ImplementedBy(NoticeServiceImpl.class)
public interface INoticeService {
    boolean addNotice(Notice notice);// 增加公告

    void deleteNotice(int noticeId);// 公告失效

    void editNotice(String noticeTitle, String noticeContext, int noticeId);// 编辑公告

    Notice getNoticeById(int noticeId);// 根据公告号查询公告

    List<Notice> getAllNotice();// 查询所有公告

    Notice getNewsNotice();// 查询最新公告
}
