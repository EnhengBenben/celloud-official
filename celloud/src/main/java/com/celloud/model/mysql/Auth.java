package com.celloud.model.mysql;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Auth {
	@JsonIgnore
    private Integer id;
	@JsonIgnore
    private Integer userId;
	@JsonIgnore
    private Integer accessKeyId;

    private String token;

    private Date tokenExpireDate;

    private String refreshToken;

    private Date refreshTokenExpireDate;
	@JsonIgnore
    private Date createDate;

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

    public Integer getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(Integer accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Date getTokenExpireDate() {
        return tokenExpireDate;
    }

    public void setTokenExpireDate(Date tokenExpireDate) {
        this.tokenExpireDate = tokenExpireDate;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken == null ? null : refreshToken.trim();
    }

    public Date getRefreshTokenExpireDate() {
        return refreshTokenExpireDate;
    }

    public void setRefreshTokenExpireDate(Date refreshTokenExpireDate) {
        this.refreshTokenExpireDate = refreshTokenExpireDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}