package com.celloud.backstage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.backstage.model.Notice;
import com.celloud.backstage.page.Page;

public interface NoticeMapper {
	int deleteByPrimaryKey(Integer noticeId);

	int insert(Notice record);

	int insertSelective(Notice record);

	Notice selectByPrimaryKey(Integer noticeId);

	int updateByPrimaryKeySelective(Notice record);

	int updateByPrimaryKeyWithBLOBs(Notice record);

	int updateByPrimaryKey(Notice record);

	public List<Notice> getNoticeByPage(@Param("state") int state, @Param("type") String type, Page page);

	public int updateNotice(Notice notice);

	public int addNotice(Notice notice);

	public Notice getNoticeById(Integer noticeId);
}