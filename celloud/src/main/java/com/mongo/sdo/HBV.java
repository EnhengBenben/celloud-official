package com.mongo.sdo;

import java.util.Date;
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
    private String pdf;//
    private String zip;// 压缩包
    private String seq;// 序列
    private String type;// type类型
    private String txt204;// 204位点突变细心
    private String reporttxt;// 结果报告
    /**
     * key : name.replace('.','_') eg : 原文件名：5_all.png 对应 key 为：5_all_png
     */
    private Map<String, String> png;// 图片
    private Date uploadDate;// 提交时间
    private String fileName;// 文件名

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

    public Map<String, String> getPng() {
        return png;
    }

    public void setPng(Map<String, String> png) {
        this.png = png;
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

}
