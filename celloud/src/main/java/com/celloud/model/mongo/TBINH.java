package com.celloud.model.mongo;

import java.util.List;

public class TBINH extends Base {
	private static final long serialVersionUID = 1L;
	private String report; // report.txt
	private String position; // report.txt.1
	private String mutationPosition; // report.txt.2
	private String conclusion; // report.txt.Report
	private String seq; // Report.txt.seq.txt
	private List<String> original; // *_all.png
	private String fileName;
	private String geneName;
	private Integer isWild; // 0:都不是 1:野生型 2:非野生型
	private String simpleGeneName; // 第一行最后个单词

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getMutationPosition() {
		return mutationPosition;
	}

	public void setMutationPosition(String mutationPosition) {
		this.mutationPosition = mutationPosition;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public List<String> getOriginal() {
		return original;
	}

	public void setOriginal(List<String> original) {
		this.original = original;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getGeneName() {
		return geneName;
	}

	public void setGeneName(String geneName) {
		this.geneName = geneName;
	}

    public Integer getIsWild() {
        return isWild;
    }

    public void setIsWild(Integer isWild) {
        this.isWild = isWild;
    }

    public String getSimpleGeneName() {
        return simpleGeneName;
    }

    public void setSimpleGeneName(String simpleGeneName) {
        this.simpleGeneName = simpleGeneName;
    }

}
