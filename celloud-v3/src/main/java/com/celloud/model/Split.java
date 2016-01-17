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
    /** 样本数Number of Values */
    private String sampleNum;
    /** 样本序列数平均值 Mean */
    private String avgSampleSeq;
    /** 方差 Variance */
    private String variance;
    /** 标准差 SD */
    private String stdev;
    /** 样本序列数最小值 */
    private String minSampleSeq;
    /** 样本序列数最大值 */
    private String maxSampleSeq;
    /** <5000条序列的样本数 */
    private String less5000;
    /** >20000条序列的样本数 */
    private String more2000;
    /**
     * 分离后的结果文件 Key: name(数据名称) number(序列数量)
     */
    private List<Map<String, String>> resultList;
    /** 有效序列数量 */
    private String usefulReads;
    /** 未知序列数量 */
    private String unknownReads;
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

    public String getSampleNum() {
        return sampleNum;
    }

    public void setSampleNum(String sampleNum) {
        this.sampleNum = sampleNum;
    }

    public String getAvgSampleSeq() {
        return avgSampleSeq;
    }

    public void setAvgSampleSeq(String avgSampleSeq) {
        this.avgSampleSeq = avgSampleSeq;
    }

    public String getVariance() {
        return variance;
    }

    public void setVariance(String variance) {
        this.variance = variance;
    }

    public String getStdev() {
        return stdev;
    }

    public void setStdev(String stdev) {
        this.stdev = stdev;
    }

    public String getMinSampleSeq() {
        return minSampleSeq;
    }

    public void setMinSampleSeq(String minSampleSeq) {
        this.minSampleSeq = minSampleSeq;
    }

    public String getMaxSampleSeq() {
        return maxSampleSeq;
    }

    public void setMaxSampleSeq(String maxSampleSeq) {
        this.maxSampleSeq = maxSampleSeq;
    }

    public String getLess5000() {
        return less5000;
    }

    public void setLess5000(String less5000) {
        this.less5000 = less5000;
    }

    public String getMore2000() {
        return more2000;
    }

    public void setMore2000(String more2000) {
        this.more2000 = more2000;
    }

    public String getUsefulReads() {
        return usefulReads;
    }

    public void setUsefulReads(String usefulReads) {
        this.usefulReads = usefulReads;
    }

    public String getUnknownReads() {
        return unknownReads;
    }

    public void setUnknownReads(String unknownReads) {
        this.unknownReads = unknownReads;
    }

}
