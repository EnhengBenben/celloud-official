package com.celloud.message.web;

import java.util.Date;

public class Message {
    private String title;
    private String message;
    private Date createTime;

    public Message() {
    }

    public Message(String title, String message) {
        this.title = title;
        this.message = message;
        this.createTime = new Date();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
