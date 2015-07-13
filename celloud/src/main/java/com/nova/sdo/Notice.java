package com.nova.sdo;

import java.io.Serializable;
import java.sql.Date;

public class Notice implements Serializable {
	private static final long serialVersionUID = 1L;
	private int noticeId;//公告编号
	private int stats;//公告状态: 0,正常；1:失效
	private String noticeTitle;//公告标题
	private String noticeContext;//公告内容
	private Date createDate; //创建时间
	private Date expireDate;//失效时间
	public int getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}
	public int getStats() {
		return stats;
	}
	public void setStats(int stats) {
		this.stats = stats;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeContext() {
		return noticeContext;
	}
	public void setNoticeContext(String noticeContext) {
		this.noticeContext = noticeContext;
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
	
}
