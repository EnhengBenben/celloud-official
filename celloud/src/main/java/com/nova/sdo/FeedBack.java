package com.nova.sdo;

import java.io.Serializable;
import java.util.Date;

public class FeedBack implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String userName;
    private String email;
    private String title;
    private String content;
    private Date createDate;
    private int solve;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public Date getCreateDate() {
	return createDate;
    }

    public void setCreateDate(Date createDate) {
	this.createDate = createDate;
    }

    public int getSolve() {
	return solve;
    }

    public void setSolve(int solve) {
	this.solve = solve;
    }

}
