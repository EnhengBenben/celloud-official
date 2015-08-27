package com.celloud.mongo.sdo;

public class User {
    private int userId;// 用户编号
    private String username;// 用户名
    private String email;// 邮箱

    public int getUserId() {
	return userId;
    }

    public void setUserId(int userId) {
	this.userId = userId;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

}
