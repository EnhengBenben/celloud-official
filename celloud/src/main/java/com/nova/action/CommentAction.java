package com.nova.action;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.nova.constants.CommentState;
import com.nova.sdo.SoftwareComment;
import com.nova.service.ISoftwareCommentService;

public class CommentAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(CommentAction.class);
    @Inject
    private ISoftwareCommentService commentService;
    private SoftwareComment comment;
    private int userId;
    private int flag;

    /**
     * 添加软件评论
     * 
     * @return
     */
    public String addComment() {
	userId = (Integer) session.get("userId");
	comment.setCommentUserId(userId);
	comment.setCommentType(CommentState.COMMENT);
	flag = commentService.addComment(comment);
	return SUCCESS;
    }

    /**
     * 删除评论
     * 
     * @return
     */
    private int delType;// 0:删除评论 1：删除回复

    public String delComment() {
	flag = commentService.delComment(comment.getId(), delType);
	return SUCCESS;
    }

    /**
     * 回复评论
     * 
     * @return
     */
    public String replyComment() {
	String tempComment = comment.getComment();
	comment = commentService.getCommentById(comment.getId());
	comment.setComment(tempComment);
	comment.setCommentType(CommentState.REPLY);
	userId = (Integer) session.get("userId");
	comment.setReplyUserId(userId);
	comment.setReplyId(comment.getId());
	flag = commentService.addComment(comment);
	return SUCCESS;
    }

    public SoftwareComment getComment() {
	return comment;
    }

    public void setComment(SoftwareComment comment) {
	this.comment = comment;
    }

    public int getFlag() {
	return flag;
    }

    public void setFlag(int flag) {
	this.flag = flag;
    }

    public int getUserId() {
	return userId;
    }

    public void setUserId(int userId) {
	this.userId = userId;
    }

    public int getDelType() {
	return delType;
    }

    public void setDelType(int delType) {
	this.delType = delType;
    }

}
