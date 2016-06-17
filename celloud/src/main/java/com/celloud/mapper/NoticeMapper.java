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

    public int insertNoticeAllUser(@Param("noticeId") Integer noticeId);

    public List<Notice> pageUserUnreadNotices(@Param("userId") Integer userId, @Param("type") String type, Page page);

    public List<Notice> pageUserNotices(@Param("userId") Integer userId, @Param("type") String type, Page page);

    public void readMessage(@Param("userId") Integer userId, @Param("noticeIds") Integer[] noticeIds);

    public void readAllMessage(@Param("userId") Integer userId);

    public void deleteMessages(@Param("noticeIds") Integer[] noticeIds);

    public void deleteMessageRelat(@Param("userId") Integer userId, @Param("noticeIds") Integer[] noticeIds);
}