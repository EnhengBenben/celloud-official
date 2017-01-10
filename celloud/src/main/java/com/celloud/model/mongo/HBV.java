package com.celloud.model.mongo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lin
 * @date 2015-10-27 下午4:47:04
 * @description：HBV 存入mongodb 的字段
 */
public class HBV extends Base {
    private static final long serialVersionUID = 1L;

    private String clinical;// 突变信息表格
    /**
     * key：["169", "173", "180", "181", "184", "194", "202","204", "236","250"]+
     * ["_wild","_mutation"] eg : 169_wild , 169_mutation
     */
    private Map<String, String> site;// 突变位点及信息
    /** 文件编号 */
    private Integer fileId;
    private String pdf;//
    private String zip;// 压缩包
    private String seq;// 序列
    private String type;// type类型
    private String txt204;// 204位点突变细心
    private String reporttxt;// 结果报告
    /**
     * key : name.replace('.','_') eg : 原文件名：5_all.png 对应 key 为：5_all_png
     */
    private Map<String, String> known;//已知位点峰图
    private Map<String, String> original;//原始峰图
    private Map<String, String> other;//其它检测结果
    private String imgString;//将其它检测结果排序后整合成字符串
    private Map<String, String> out;//非上述三种图片的图片
    private Date uploadDate;// 提交时间
    private String fileName;// 文件名
    private Map<String, String> briefBaseInfo;
    private Map<String, String> detailBaseInfo;
    private List<String> lowQc;

    public String getClinical() {
        return clinical;
    }

    public void setClinical(String clinical) {
        this.clinical = clinical;
    }

    public Map<String, String> getSite() {
        return site;
    }

    public void setSite(Map<String, String> site) {
        this.site = site;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTxt204() {
        return txt204;
    }

    public void setTxt204(String txt204) {
        this.txt204 = txt204;
    }

    public String getReporttxt() {
        return reporttxt;
    }

    public void setReporttxt(String reporttxt) {
        this.reporttxt = reporttxt;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Map<String, String> getKnown() {
        return known;
    }

    public void setKnown(Map<String, String> known) {
        this.known = known;
    }

    public Map<String, String> getOriginal() {
        return original;
    }

    public void setOriginal(Map<String, String> original) {
        this.original = original;
    }

    public Map<String, String> getOther() {
        return other;
    }

    public void setOther(Map<String, String> other) {
        this.other = other;
    }

    public Map<String, String> getOut() {
        return out;
    }

    public void setOut(Map<String, String> out) {
        this.out = out;
    }

    public String getImgString() {
        return imgString;
    }

    public void setImgString(String imgString) {
        this.imgString = imgString;
    }

    public Map<String, String> getBriefBaseInfo() {
        return briefBaseInfo;
    }

    public void setBriefBaseInfo(Map<String, String> briefBaseInfo) {
        this.briefBaseInfo = briefBaseInfo;
    }

    public Map<String, String> getDetailBaseInfo() {
        return detailBaseInfo;
    }

    public void setDetailBaseInfo(Map<String, String> detailBaseInfo) {
        this.detailBaseInfo = detailBaseInfo;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public List<String> getLowQc() {
        return lowQc;
    }

    public void setLowQc(List<String> lowQc) {
        this.lowQc = lowQc;
	}

}
