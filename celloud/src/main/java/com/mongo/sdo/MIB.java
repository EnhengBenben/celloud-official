package com.mongo.sdo;

import java.util.List;
import java.util.Map;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;

/**
 * MIB数据报告内容
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-10-22下午3:00:52
 * @version Revision: 1.0
 */
@Entity(noClassnameStored = true)
public class MIB extends Base {
    private static final long serialVersionUID = 1L;
    /** 所运行数据信息 */
    @Embedded
    private List<Data> data;
    /** 低质量序列、宿主序列、16S序列以及未能比对上的reads比例分布图 */
    private String readsDistribution;
    /** 16s相关的序列在科层次上的比例分布图 */
    private String familyDistribution;
    /** 样品中属层次上reads的比例 */
    private String genusDistribution;
    /**
     * 将以数字形式具体展示各个属的详细情况。 其中包括: Species——目标病原体在种水平上的鉴定(目前该结果属于推测)，
     * Genus——目标病原体在属水平上的鉴定， GI——参考序列的编号，
     * %Coverage(简称Coverage)——reads能覆盖参考序列的百分比， Reads_hit——reads比对上该参考序列的数目，
     * Reads_num——reads分类在该属下的数目， Average depth of
     * coverage(简称avgCoverage)——平均测序深度
     */
    private List<Map<String, String>> summaryTable;
    private String legionella;
    private String acinetobacter;
    private String fluoribacter;
    private String acetobacter;
    private String staphylococcus;
    private String halothiobacillus;
    private String bifidobacterium;
    private String xanthomonas;
    private String flavobacterium;
    private String corynebacterium;
    private String propionibacterium;
    private String planomicrobium;
    private String micrococcus;
    private String hymenobacter;
    private String silanimonas;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
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

    public String getLegionella() {
        return legionella;
    }

    public void setLegionella(String legionella) {
        this.legionella = legionella;
    }

    public String getAcinetobacter() {
        return acinetobacter;
    }

    public void setAcinetobacter(String acinetobacter) {
        this.acinetobacter = acinetobacter;
    }

    public String getFluoribacter() {
        return fluoribacter;
    }

    public void setFluoribacter(String fluoribacter) {
        this.fluoribacter = fluoribacter;
    }

    public String getAcetobacter() {
        return acetobacter;
    }

    public void setAcetobacter(String acetobacter) {
        this.acetobacter = acetobacter;
    }

    public String getStaphylococcus() {
        return staphylococcus;
    }

    public void setStaphylococcus(String staphylococcus) {
        this.staphylococcus = staphylococcus;
    }

    public String getHalothiobacillus() {
        return halothiobacillus;
    }

    public void setHalothiobacillus(String halothiobacillus) {
        this.halothiobacillus = halothiobacillus;
    }

    public String getBifidobacterium() {
        return bifidobacterium;
    }

    public void setBifidobacterium(String bifidobacterium) {
        this.bifidobacterium = bifidobacterium;
    }

    public String getXanthomonas() {
        return xanthomonas;
    }

    public void setXanthomonas(String xanthomonas) {
        this.xanthomonas = xanthomonas;
    }

    public String getFlavobacterium() {
        return flavobacterium;
    }

    public void setFlavobacterium(String flavobacterium) {
        this.flavobacterium = flavobacterium;
    }

    public String getCorynebacterium() {
        return corynebacterium;
    }

    public void setCorynebacterium(String corynebacterium) {
        this.corynebacterium = corynebacterium;
    }

    public String getPropionibacterium() {
        return propionibacterium;
    }

    public void setPropionibacterium(String propionibacterium) {
        this.propionibacterium = propionibacterium;
    }

    public String getPlanomicrobium() {
        return planomicrobium;
    }

    public void setPlanomicrobium(String planomicrobium) {
        this.planomicrobium = planomicrobium;
    }

    public String getMicrococcus() {
        return micrococcus;
    }

    public void setMicrococcus(String micrococcus) {
        this.micrococcus = micrococcus;
    }

    public String getHymenobacter() {
        return hymenobacter;
    }

    public void setHymenobacter(String hymenobacter) {
        this.hymenobacter = hymenobacter;
    }

    public String getSilanimonas() {
        return silanimonas;
    }

    public void setSilanimonas(String silanimonas) {
        this.silanimonas = silanimonas;
    }
}
