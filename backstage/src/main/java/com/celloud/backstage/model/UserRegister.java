package com.celloud.backstage.model;

import java.util.Date;

public class UserRegister {
    private Integer id;

    private Integer userId;

    private Date expireDate;

    private String md5;

    private String email;

    private String appIds;

	private String roleIds;

	private String authFrom;

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

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5 == null ? null : md5.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAppIds() {
        return appIds;
    }

    public void setAppIds(String appIds) {
        this.appIds = appIds == null ? null : appIds.trim();
    }

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getAuthFrom() {
		return authFrom;
	}

	public void setAuthFrom(String authFrom) {
		this.authFrom = authFrom;
	}

}