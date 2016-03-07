package com.celloud.manager.model;

public class FileFormat {
    private Integer formatId;

    private String formatDesc;

    public Integer getFormatId() {
        return formatId;
    }

    public void setFormatId(Integer formatId) {
        this.formatId = formatId;
    }

    public String getFormatDesc() {
        return formatDesc;
    }

    public void setFormatDesc(String formatDesc) {
        this.formatDesc = formatDesc == null ? null : formatDesc.trim();
    }
}