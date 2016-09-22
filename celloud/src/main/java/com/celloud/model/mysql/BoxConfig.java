package com.celloud.model.mysql;

public class BoxConfig {
    private Integer id;

    private String name;

    private Integer userId;

    private String intranetAddress;

    private String extranetAddress;

    private Integer port;

    private String context;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIntranetAddress() {
        return intranetAddress;
    }

    public void setIntranetAddress(String intranetAddress) {
        this.intranetAddress = intranetAddress == null ? null : intranetAddress.trim();
    }

    public String getExtranetAddress() {
        return extranetAddress;
    }

    public void setExtranetAddress(String extranetAddress) {
        this.extranetAddress = extranetAddress == null ? null : extranetAddress.trim();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}