package com.celloud.model;

import java.util.List;
import java.util.Map;

public class Oncogene extends Base {
    private static final long serialVersionUID = 1L;
    private String wz1;//
    private String wz2;//
    private String report;//
    private String length;//
    private String conclusion;//
    private String seq;//
    private List<String> knowMutation;//
    /**
     * key : name.replace('.','_') eg : 原文件名：5_all.png 对应 key 为：5_all_png
     */
    private Map<String, String> original;// 原始峰图
    private List<String> out;// 非上述三种图片的图片
    private String fileName;// 文件名
    private String pdf;

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getWz1() {
        return wz1;
    }

    public void setWz1(String wz1) {
        this.wz1 = wz1;
    }

    public String getWz2() {
        return wz2;
    }

    public void setWz2(String wz2) {
        this.wz2 = wz2;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
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

    public Map<String, String> getOriginal() {
        return original;
    }

    public void setOriginal(Map<String, String> original) {
        this.original = original;
    }

    public List<String> getKnowMutation() {
        return knowMutation;
    }

    public void setKnowMutation(List<String> knowMutation) {
        this.knowMutation = knowMutation;
    }

    public List<String> getOut() {
        return out;
    }

    public void setOut(List<String> out) {
        this.out = out;
    }

}