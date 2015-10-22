package com.celloud.sdo;

import java.io.Serializable;

public class DataFormat implements Serializable {
    private static final long serialVersionUID = 1L;
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
        this.formatDesc = formatDesc;
    }
}
