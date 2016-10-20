package com.celloud.model.mysql;

import java.util.Date;

public class Client {
    private Integer id;

    private String version;

    private String nameX86;

    private String nameX64;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getNameX86() {
        return nameX86;
    }

    public void setNameX86(String nameX86) {
        this.nameX86 = nameX86 == null ? null : nameX86.trim();
    }

    public String getNameX64() {
        return nameX64;
    }

    public void setNameX64(String nameX64) {
        this.nameX64 = nameX64 == null ? null : nameX64.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}