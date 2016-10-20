package com.celloud.backstage.model;

import java.util.Date;

import com.celloud.backstage.constants.NoticeConstants;
import com.celloud.backstage.constants.NoticeConstants.MessageCategory;

public class Notice {
	private Integer noticeId;

	private String noticeTitle;

	private Date createDate;

	private Date expireDate;

	private Integer state;

	private String noticeContext;

	private String type = NoticeConstants.TYPE_NOTICE;

	private String category;

	private Integer readState;

	private String icon;

	public Notice() {
	}

	public Notice(String category, String noticeTitle, String noticeContext) {
		this.category = category;
		this.noticeTitle = noticeTitle;
		this.noticeContext = noticeContext;
		this.createDate = new Date();
	}

	public Integer getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle == null ? null : noticeTitle.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getNoticeContext() {
		return noticeContext;
	}

	public void setNoticeContext(String noticeContext) {
		this.noticeContext = noticeContext == null ? null : noticeContext.trim();
	}

	public Integer getReadState() {
		return readState;
	}

	public void setReadState(Integer readState) {
		this.readState = readState;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
		setIcon(MessageCategory.getIcon(category));
	}

	public String getIcon() {
		if (this.icon != null) {
			return this.icon;
		}
		return MessageCategory.getIcon(this.category);
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}