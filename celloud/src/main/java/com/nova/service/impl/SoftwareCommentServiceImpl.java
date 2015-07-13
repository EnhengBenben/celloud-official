package com.nova.service.impl;

import java.util.List;

import com.google.inject.Inject;
import com.nova.dao.ISoftwareCommentDao;
import com.nova.pager.Page;
import com.nova.sdo.SoftwareComment;
import com.nova.service.ISoftwareCommentService;

public class SoftwareCommentServiceImpl implements ISoftwareCommentService {
	@Inject
	private ISoftwareCommentDao commentDao;

	@Override
	public List<SoftwareComment> getCommentList(int softwareId, Page page) {
		return commentDao.getCommentList(softwareId, page);
	}

	@Override
	public List<SoftwareComment> getCommentList(int softwareId) {
		return commentDao.getCommentList(softwareId);
	}

	@Override
	public int addComment(SoftwareComment comment) {
		return commentDao.addComment(comment);
	}

	@Override
	public int delComment(int commentId, int delType) {
		return commentDao.delComment(commentId, delType);
	}

	@Override
	public SoftwareComment getCommentById(int commentId) {
		return commentDao.getCommentById(commentId);
	}

	@Override
	public List<SoftwareComment> getReplyList(int commentId) {
		return commentDao.getReplyList(commentId);
	}
}
