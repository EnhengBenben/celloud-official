package com.celloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.ConstantsData;
import com.celloud.mapper.NoticeMapper;
import com.celloud.mapper.UserMapper;
import com.celloud.model.mysql.Notice;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.NoticeService;

@Service("noticeServiceImpl")
public class NoticeServiceImpl implements NoticeService {
    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public void insertNotice(Notice notice, String... usernames) {
        noticeMapper.insertSelective(notice);
        noticeMapper.insertNoticeUser(notice.getNoticeId(), usernames);
    }

    @Override
    public PageList<Notice> findLastUnreadMessage() {
        Page page = new Page(1, 5);
        List<Notice> list = noticeMapper.pageUserUnreadNotices(ConstantsData.getLoginUserId(), page);
        return new PageList<>(page, list);
    }

    @Override
    public PageList<Notice> findLastMessage(Page page) {
        if (page == null) {
            page = new Page();
        }
        List<Notice> list = noticeMapper.pageUserNotices(ConstantsData.getLoginUserId(), page);
        return new PageList<>(page, list);
    }

    @Override
    public PageList<Notice> findLastUnreadNotice() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageList<Notice> findUnreadNotice(Page page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void readMessage(Integer[] noticeIds) {
        noticeMapper.readMessage(ConstantsData.getLoginUserId(), noticeIds);
    }

    @Override
    public void readAllMessage() {
        noticeMapper.readAllMessage(ConstantsData.getLoginUserId());
    }

    @Override
    public void deleteMessages(Integer[] noticeIds) {
        noticeMapper.deleteMessageRelat(ConstantsData.getLoginUserId(), noticeIds);
        noticeMapper.deleteMessages(noticeIds);
    }

}
