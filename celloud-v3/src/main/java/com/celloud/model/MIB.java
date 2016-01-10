package com.celloud.model;

import java.util.List;
import java.util.Map;

/**
 * MIB数据报告内容
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-10-22下午3:00:52
 * @version Revision: 1.0
 */
public class MIB extends Base {
    private static final long serialVersionUID = 1L;
    /** 所运行数据信息 */
    private List<DataFile> data;
    /** 序列总量 */
    private String totalReads;
    /** 平均质量 */
    private String avgQuality;
    /** 平均GC含量(%) */
    private String avgGCContent;
    /** 低质量序列、宿主序列、16S序列以及未能比对上的reads比例分布图 */
    private String readsDistribution;
    /** 16s相关的序列在科层次上的比例分布图 */
    private String familyDistribution;
    /** 样品中属层次上reads的比例 */
    private String genusDistribution;
    /**
     * 将以数字形式具体展示各个属的详细情况。 其中包括: Species——目标病原体在种水平上的鉴定(目前该结果属于推测)，
     * Genus——目标病原体在属水平上的鉴定， GI——参考序列的编号， %Coverage——reads能覆盖参考序列的百分比，
     * Reads_hit——reads比对上该参考序列的数目， Reads_num——reads分类在该属下的数目， Average depth of
     * coverage——平均测序深度
     */
    private List<Map<String, String>> summaryTable;
    private Map<String, String> pngPath;
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

    public String getReadsDistribution() {
        return readsDistribution;
    }

    public void setReadsDistribution(String readsDistribution) {
        this.readsDistribution = readsDistribution;
    }

    public String getFamilyDistribution() {
        return familyDistribution;
    }

    public void setFamilyDistribution(String familyDistribution) {
        this.familyDistribution = familyDistribution;
    }

    public String getGenusDistribution() {
        return genusDistribution;
    }

    public void setGenusDistribution(String genusDistribution) {
        this.genusDistribution = genusDistribution;
    }

    public List<Map<String, String>> getSummaryTable() {
        return summaryTable;
    }

    public void setSummaryTable(List<Map<String, String>> summaryTable) {
        this.summaryTable = summaryTable;
    }

    public Map<String, String> getPngPath() {
        return pngPath;
    }

    public void setPngPath(Map<String, String> pngPath) {
        this.pngPath = pngPath;
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
