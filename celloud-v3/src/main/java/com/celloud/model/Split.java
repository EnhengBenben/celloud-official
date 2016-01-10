package com.celloud.model;

import java.util.List;
import java.util.Map;

/**
 * 文件分类app
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-10-14下午1:16:46
 * @version Revision: 1.0
 */
public class Split extends Base {
    private static final long serialVersionUID = 1L;
    /**
     * 所运行数据信息
     */
    private List<DataFile> data;
    /** 序列总量 */
    private String totalReads;
    /** 平均质量 */
    private String avgQuality;
    /** 平均GC含量(%) */
    private String avgGCContent;
    /**
     * 分离后的结果文件 Key: name(数据名称) number(序列数量)
     */
    private List<Map<String, String>> resultList;
    /** 1:分离的结果文件已上传 */
    private Integer upload;
    /** 分析数据在mysql tb_file表中的id号, 格式： id1,id2,id3,..., */
    private String splitDataIds;
    /**
     * QC序列质量,数据1
     */
    private Map<String, String> basicStatistics1;
    private String qualityPath1;
    private String seqContentPath1;
    /**
     * QC序列质量,数据2
     */
    private Map<String, String> basicStatistics2;
    private String qualityPath2;
    private String seqContentPath2;

    public List<DataFile> getData() {
        return data;
    }

    public void setData(List<DataFile> data) {
        this.data = data;
    }

    public String getTotalReads() {
        return totalReads;
    }

    public void setTotalReads(String totalReads) {
        this.totalReads = totalReads;
    }

    public String getAvgQuality() {
        return avgQuality;
    }

    public void setAvgQuality(String avgQuality) {
        this.avgQuality = avgQuality;
    }

    public String getAvgGCContent() {
        return avgGCContent;
    }

    public void setAvgGCContent(String avgGCContent) {
        this.avgGCContent = avgGCContent;
    }

    public List<Map<String, String>> getResultList() {
        return resultList;
    }

    public void setResultList(List<Map<String, String>> resultList) {
        this.resultList = resultList;
    }

    public Integer getUpload() {
        return upload;
    }

    public void setUpload(Integer upload) {
        this.upload = upload;
    }

    public String getSplitDataIds() {
        return splitDataIds;
    }

    public void setSplitDataIds(String splitDataIds) {
        this.splitDataIds = splitDataIds;
    }

    public Map<String, String> getBasicStatistics1() {
        return basicStatistics1;
    }

    public void setBasicStatistics1(Map<String, String> basicStatistics1) {
        this.basicStatistics1 = basicStatistics1;
    }

    public String getQualityPath1() {
        return qualityPath1;
    }

    public void setQualityPath1(String qualityPath1) {
        this.qualityPath1 = qualityPath1;
    }

    public String getSeqContentPath1() {
        return seqContentPath1;
    }

    public void setSeqContentPath1(String seqContentPath1) {
        this.seqContentPath1 = seqContentPath1;
    }

    public Map<String, String> getBasicStatistics2() {
        return basicStatistics2;
    }

    public void setBasicStatistics2(Map<String, String> basicStatistics2) {
        this.basicStatistics2 = basicStatistics2;
    }

    public String getQualityPath2() {
        return qualityPath2;
    }

    public void setQualityPath2(String qualityPath2) {
        this.qualityPath2 = qualityPath2;
    }

    public String getSeqContentPath2() {
        return seqContentPath2;
    }

    public void setSeqContentPath2(String seqContentPath2) {
        this.seqContentPath2 = seqContentPath2;
    }

}
