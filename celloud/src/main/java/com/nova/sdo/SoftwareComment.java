package com.nova.sdo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 软件评论实体类 类名称：SoftwareComment  
 * 
 * @类描述：  
 * @创建人：zl  
 * @创建时间：2013-8-7 下午3:05:56    
 * @version      
 */
public class SoftwareComment implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;// 自增主键
    private int commentUserId;// 评论人编号
    private String commentUserName;// 评论人姓名
    private int replyUserId;// 回复人编号
    private String replyUserName;// 回复人姓名
    private int softwareId;// 软件编号
    private String comment;// 评论内容
    private Date commentDate;// 评论或者回复日期
    private int commentType;// 评论类型 0：评论 1：回复
    private int replyId;// 回复评论的编号
    private List<SoftwareComment> replyList;// 回复列表

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getCommentUserId() {
	return commentUserId;
    }

    public void setCommentUserId(int commentUserId) {
	this.commentUserId = commentUserId;
    }

    public int getReplyUserId() {
	return replyUserId;
    }

    public void setReplyUserId(int replyUserId) {
	this.replyUserId = replyUserId;
    }

    public int getSoftwareId() {
	return softwareId;
    }

    public void setSoftwareId(int softwareId) {
	this.softwareId = softwareId;
    }

    public String getComment() {
	return comment;
    }

    public void setComment(String comment) {
	this.comment = comment;
    }

    public Date getCommentDate() {
	return commentDate;
    }

    public void setCommentDate(Date commentDate) {
	this.commentDate = commentDate;
    }

    public int getCommentType() {
	return commentType;
    }

    public void setCommentType(int commentType) {
	this.commentType = commentType;
    }

    public List<SoftwareComment> getReplyList() {
	return replyList;
    }

    public void setReplyList(List<SoftwareComment> replyList) {
	this.replyList = replyList;
    }

    public String getCommentUserName() {
	return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
	this.commentUserName = commentUserName;
    }

    public String getReplyUserName() {
	return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
	this.replyUserName = replyUserName;
    }

    public int getReplyId() {
	return replyId;
    }

    public void setReplyId(int replyId) {
	this.replyId = replyId;
    }

}
