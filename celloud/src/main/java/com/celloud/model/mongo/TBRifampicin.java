package com.celloud.model.mongo;

import java.util.List;
import java.util.Map;

public class TBRifampicin extends Base {

    private static final long serialVersionUID = 1L;
    private String fileName; // 文件名
    private String report; // report.txt
    private String position; // report.txt.wz.1
    private String mutationPosition;// report.txt.wz.2
    private String conclusion; // report.txt.Report
    private String pdf; // pdf
    private String seq; // seq
    private List<String> original;// *_png
    private Map<String, String> baseInfo;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

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

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
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

    public Map<String, String> getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(Map<String, String> baseInfo) {
        this.baseInfo = baseInfo;
    }

}
