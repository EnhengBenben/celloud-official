package com.nova.sdo;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: User
 * @Description: (用户的数据服务对象)
 * @author summer
 * @date 2012-6-19 下午02:45:47
 * 
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
    private int userId;// 用户编号
    private String username;// 用户名
    private String password;// 密码
	private String hostPwd;// 标识主机 密码
    private String email;// 邮箱
	private String cellPhone; // 个人手机
    private Date createDate;// 创建日期
    private int type;// 标志位
    private String theme;// 主题
	private int role;// 角色，0，1 普通用户，2超级管理员，3管理员
    private String remark;// 备注
    private int navigation;// 用户导航
    private String truename;// 真实姓名
	private int state = 0;// 0 正常用户，1 已删除
	private int deptId;// 所属部门的ID
	private String loginUuid;// uuid
	private Integer companyId;// 提供app的公司id
	private String kaptchaCode;// 验证码

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getHostPwd() {
        return hostPwd;
    }

    public void setHostPwd(String hostPwd) {
        this.hostPwd = hostPwd;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getKaptchaCode() {
        return kaptchaCode;
    }

    public void setKaptchaCode(String kaptchaCode) {
        this.kaptchaCode = kaptchaCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getNavigation() {
        return navigation;
    }

    public void setNavigation(int navigation) {
        this.navigation = navigation;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

	public String getLoginUuid() {
		return loginUuid;
	}

	public void setLoginUuid(String loginUuid) {
		this.loginUuid = loginUuid;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}
