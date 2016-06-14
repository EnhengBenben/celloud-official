package com.celloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.Notice;
import com.celloud.page.Page;

public interface NoticeMapper {
    public int deleteByPrimaryKey(Integer noticeId);

    public int insert(Notice record);

    public int insertSelective(Notice record);

    public Notice selectByPrimaryKey(Integer noticeId);

    public int updateByPrimaryKeySelective(Notice record);

    public int updateByPrimaryKeyWithBLOBs(Notice record);

    public int updateByPrimaryKey(Notice record);

    public int insertNoticeUser(@Param("noticeId") Integer noticeId, @Param("usernames") String... usernames);

    public List<Notice> pageUserUnreadNotices(@Param("userId") Integer userId, Page page);

    public List<Notice> pageUserNotices(@Param("userId") Integer userId, Page page);
}