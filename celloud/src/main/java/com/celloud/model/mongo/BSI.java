package com.celloud.model.mongo;

import java.util.List;
import java.util.Map;

/**
 * BSI数据报告内容
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2016年4月14日 下午2:08:14
 * @version Revision: 1.0
 */
public class BSI extends MIB {
    private static final long serialVersionUID = 1L;
    /**
     * 继承MIB baseInfo填写信息不同：
     * 用户填写信息,报告基本信息——姓名[name],性别[sex],年龄[age],科室[department],住院编号[inpatientNo],
     * 床号[bedNo],ID号[idNo],样本种类[sampleType],样本编号[sampleNo],送检医生[doctor],送检日期[
     * inspectionDate],检测人[inspector],复核人[compounder],报告时间[reportDate]
     */
    /**
     * 20种菌的检测结果。包括：species——菌株名，species_zh——菌株中文名，
     * site1——突变位点1信息，site2——突变位点2信息，site3——突变位点3信息，site4——突变位点4信息，
     * unique_reads_num——唯一识别序列数，seq_proportion——序列比重
     * seq1_name——序列1名称，seq1_no——序列1编号，seq1——代表序列1，
     * seq2_name——序列2名称，seq2_no——序列2编号，seq2——代表序列2，
     * seq3_name——序列3名称，seq3_no——序列3编号，seq3——代表序列3，
     * seq4_name——序列4名称，seq4_no——序列4编号，seq4——代表序列4， cross_species——覆盖区域交叉菌
     */
    private List<Map<String, String>> species_20;
    /**
     * 20种菌之外的其他菌检测结果。包括：species——菌株名，species_zh——菌株中文名，
     * site1——突变位点1信息，site2——突变位点2信息，site3——突变位点3信息，site4——突变位点4信息，
     * unique_reads_num——唯一识别序列数，seq1——代表序列1，seq2——代表序列2，seq3——代表序列3，seq4——
     * 代表序列4
     */
    private List<Map<String, String>> species_other;
    /** 人序列总量 */
    private String human_reads;
    /** 16S序列数 */
    private String reads_16s;
    public List<Map<String, String>> getSpecies_20() {
        return species_20;
    }

    public void setSpecies_20(List<Map<String, String>> species_20) {
        this.species_20 = species_20;
    }

    public List<Map<String, String>> getSpecies_other() {
        return species_other;
    }

    public void setSpecies_other(List<Map<String, String>> species_other) {
        this.species_other = species_other;
    }

    public String getHuman_reads() {
        return human_reads;
    }

    public void setHuman_reads(String human_reads) {
        this.human_reads = human_reads;
    }

    public String getReads_16s() {
        return reads_16s;
    }

    public void setReads_16s(String reads_16s) {
        this.reads_16s = reads_16s;
    }
}
