package com.celloud.backstage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.celloud.backstage.utils.AvatarUtil;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String username;

    private String password;

    private String email;

    private String cellphone;

    private Date createDate;

    private Integer role;

    private String remark;

    private Integer navigation;

    private String truename;

    private Integer state;

    private Integer deptId;

    private String loginUuid;

    private Integer companyId;

    private String avatar;

    private String sign;

    private BigDecimal balances;

    // only show
    private String companyName;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone == null ? null : cellphone.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getNavigation() {
        return navigation;
    }

    public void setNavigation(Integer navigation) {
        this.navigation = navigation;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename == null ? null : truename.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getLoginUuid() {
        return loginUuid;
    }

    public void setLoginUuid(String loginUuid) {
        this.loginUuid = loginUuid == null ? null : loginUuid.trim();
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public String getAvatar() {
        return AvatarUtil.getAvatar(avatar);
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public BigDecimal getBalances() {
        return balances;
    }

    public void setBalances(BigDecimal balances) {
        this.balances = balances;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}