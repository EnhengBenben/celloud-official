package com.celloud.sdo;

import java.io.Serializable;

/**
 * APP分类表
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-10-22下午1:29:48
 * @version Revision: 1.0
 */
public class Classify implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer classifyId;
    private String classifyName;
    private Integer classifyPid;
    private String notes;

    public Integer getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }
    public String getClassifyName() {
        return classifyName;
    }
    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Integer getClassifyPid() {
        return classifyPid;
    }

    public void setClassifyPid(Integer classifyPid) {
        this.classifyPid = classifyPid;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
