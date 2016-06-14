package com.celloud.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.celloud.constants.ConstantsData;
import com.celloud.constants.NoticeState;
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

    @Async
    @Override
    public void insertNotice(Notice notice, Integer... userIds) {
        noticeMapper.insertSelective(notice);
        noticeMapper.insertNoticeUser(notice.getNoticeId(), userIds);
    }

    @Override
    public void insertNotice(Notice notice, String... usernames) {
        List<Integer> userIds = userMapper.getUserIdsByName(Arrays.asList(usernames));
        insertNotice(notice, userIds.toArray(new Integer[] {}));
    }
    @Override
    public PageList<Notice> findLastUnreadNotice() {
        Page page = new Page(1, 5);
        List<Notice> list = noticeMapper.pageUserUnreadNotices(ConstantsData.getLoginUserId(), NoticeState.UNREAD,
                page);
        return new PageList<>(page, list);
    }
}
