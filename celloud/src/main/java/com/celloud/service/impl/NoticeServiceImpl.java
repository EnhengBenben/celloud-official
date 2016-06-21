package com.celloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.ConstantsData;
import com.celloud.constants.NoticeConstants;
import com.celloud.exception.BusinessException;
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
    public void insertMessage(Notice notice, String... usernames) {
        noticeMapper.insertSelective(notice);
        int result = 0;
        if (usernames == null || usernames.length == 0) {
            result = noticeMapper.insertNoticeAllUser(notice.getNoticeId());
        } else {
            result = noticeMapper.insertNoticeUser(notice.getNoticeId(), usernames);
        }
        if (result == 0) {
            throw new BusinessException("消息没有接收者！");
        }
    }

    @Override
    public PageList<Notice> findLastUnreadMessage() {
        Page page = new Page(1, 5);
        List<Notice> list = noticeMapper.pageUserUnreadNotices(ConstantsData.getLoginUserId(),
                NoticeConstants.TYPE_MESSAGE, page);
        return new PageList<>(page, list);
    }

    @Override
    public PageList<Notice> findLastMessage(Page page) {
        if (page == null) {
            page = new Page();
        }
        List<Notice> list = noticeMapper.pageUserNotices(ConstantsData.getLoginUserId(), NoticeConstants.TYPE_MESSAGE,
                page);
        return new PageList<>(page, list);
    }

    @Override
    public PageList<Notice> findLastUnreadNotice() {
        Page page = new Page(1, 5);
        List<Notice> list = noticeMapper.pageUserUnreadNotices(ConstantsData.getLoginUserId(),
                NoticeConstants.TYPE_NOTICE, page);
        return new PageList<>(page, list);
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

    @Override
    public PageList<Notice> findLastNotice(Page page) {
        if (page == null) {
            page = new Page(1, 5);
        }
        List<Notice> list = noticeMapper.pageUserNotices(ConstantsData.getLoginUserId(), NoticeConstants.TYPE_NOTICE,
                page);
        return new PageList<>(page, list);
    }

    @Override
    public Notice getNoticeById(Integer noticeId) {
        Notice notice = noticeMapper.selectByPrimaryKey(noticeId);
        if (notice != null && notice.getReadState() != NoticeConstants.READAD) {
            noticeMapper.readMessage(ConstantsData.getLoginUserId(), new Integer[] { noticeId });
        }
        return notice;
    }

}
