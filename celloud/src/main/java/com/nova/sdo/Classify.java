package com.nova.sdo;

import java.io.Serializable;

/**
 * 软件分类表
 * 
 * @author <a href="mailto:linyongchao@novacloud.com">linyc</a>
 * @date 2013-1-21下午1:20:56
 * @version Revision: 1.0
 */
public class Classify implements Serializable {
	private static final long serialVersionUID = 1L;
	private int classifyId;// 分类编号
	private String classifyName;// 分类名称
	private int classifyPid;// 分类父节点
	private String notes;// 描述

	public int getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(int classifyId) {
		this.classifyId = classifyId;
	}

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	public int getClassifyPid() {
		return classifyPid;
	}

	public void setClassifyPid(int classifyPid) {
		this.classifyPid = classifyPid;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
