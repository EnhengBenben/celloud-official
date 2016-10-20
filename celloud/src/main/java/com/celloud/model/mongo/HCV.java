package com.celloud.model.mongo;

import java.util.Map;

/**
 * HCV存入mongodb的字段
 * 
 * @author lin
 * @date 2016年3月7日 下午5:05:12
 */
public class HCV extends Base {
	private static final long serialVersionUID = 1L;

	private String fileName;
	private String subtype;
	private String subjectName;
	private String identity;
	private String total;
	private String evalue;
	private String score;
	private String seq;
	private Map<String, String> original;// 原始峰图
    private Map<String, String> baseInfo;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getEvalue() {
		return evalue;
	}

	public void setEvalue(String evalue) {
		this.evalue = evalue;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public Map<String, String> getOriginal() {
		return original;
	}

	public void setOriginal(Map<String, String> original) {
		this.original = original;
	}

    public Map<String, String> getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(Map<String, String> baseInfo) {
        this.baseInfo = baseInfo;
    }

}
