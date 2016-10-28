package com.celloud.backstage.model;

import java.util.Date;

public class Feedback {
    private Integer id;

    private Integer userId;

    private String username;

    private String email;

    private String title;

    private Date createDate;

    private Byte solve;

    private Byte hasAttachment;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Byte getSolve() {
        return solve;
    }

    public boolean isSolved() {
        return solve != 0;
    }

    public void setSolve(Byte solve) {
        this.solve = solve;
    }

    public Byte getHasAttachment() {
        return hasAttachment;
    }

    public boolean hasAttachment() {
        return hasAttachment != null && hasAttachment != 0;
    }

    public void setHasAttachment(Byte hasAttachment) {
        this.hasAttachment = hasAttachment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}