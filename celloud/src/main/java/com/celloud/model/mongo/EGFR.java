package com.celloud.model.mongo;

import java.util.List;
import java.util.Map;

public class EGFR extends Base {
    private static final long serialVersionUID = 1L;
    private String fileName;// 文件名
    private String report;// report.txt
    private String position;// wz1
    private String pos;// length
    private String conclusion;// report
    private String seq;// seq
    private String pdf;// pdf
    private String mutationPosition;// wz2
    private List<String> knowMutationBig;// know
    private List<String> knowMutation;//
    private List<String> original;// listAll
    private List<String> out;// unknow
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

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
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

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getMutationPosition() {
        return mutationPosition;
    }

    public void setMutationPosition(String mutationPosition) {
        this.mutationPosition = mutationPosition;
    }

    public List<String> getKnowMutationBig() {
        return knowMutationBig;
    }

    public void setKnowMutationBig(List<String> knowMutationBig) {
        this.knowMutationBig = knowMutationBig;
    }

    public List<String> getKnowMutation() {
        return knowMutation;
    }

    public void setKnowMutation(List<String> knowMutation) {
        this.knowMutation = knowMutation;
    }

    public List<String> getOriginal() {
        return original;
    }

    public void setOriginal(List<String> original) {
        this.original = original;
    }

    public List<String> getOut() {
        return out;
    }

    public void setOut(List<String> out) {
        this.out = out;
    }

    public Map<String, String> getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(Map<String, String> baseInfo) {
        this.baseInfo = baseInfo;
    }

}