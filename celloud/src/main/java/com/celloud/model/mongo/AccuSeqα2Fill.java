package com.celloud.model.mongo;

import java.util.List;

/**
 * AccuSeqα2 用户填写部分内容
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">leamo</a>
 * @date 2016年11月9日 下午1:30:16
 */
public class AccuSeqα2Fill extends PatientBasic {
    /**
     * 取样日期
     */
    private String samplingDate;
    /**
     * 报告日期
     */
    private String reportDate;
    /**
     * 病理诊断
     */
    private String pathologicDiagnosis;
    /**
     * 病理位置
     */
    private String pathologicalPosition;
    /**
     * 病理类型
     */
    private String pathologicalType;
    /**
     * 样本来源
     */
    private String sampleSource;
    /**
     * 临床诊断
     */
    private String clinicalDiagnosis;
    /**
     * 分析日期
     */
    private String analysisDate;
    /**
     * 分子生物实验操作（简写）
     */
    private String molecularBioExperOper;
    /**
     * 基因分析
     */
    private String geneAnalysis;
    /**
     * 有指导意义的阳性位点列表
     */
    private List<GeneDetectionResult> usefulGeneResult;
    /**
     * 耐药位点汇总
     */
    private List<DrugResistanceSite> resistanceSiteSum;
    /**
     * 药敏信息汇总
     */
    private List<DrugResistanceSite> personalizedMedicine;
    /**
     * 参考文献
     */
    private List<String> reference;
    /**
     * 推荐用药
     */
    private List<RecommendDrug> recommendDrug;

    public String getSamplingDate() {
        return samplingDate;
    }

    public void setSamplingDate(String samplingDate) {
        this.samplingDate = samplingDate;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getPathologicDiagnosis() {
        return pathologicDiagnosis;
    }

    public void setPathologicDiagnosis(String pathologicDiagnosis) {
        this.pathologicDiagnosis = pathologicDiagnosis;
    }

    public String getPathologicalPosition() {
        return pathologicalPosition;
    }

    public void setPathologicalPosition(String pathologicalPosition) {
        this.pathologicalPosition = pathologicalPosition;
    }

    public String getPathologicalType() {
        return pathologicalType;
    }

    public void setPathologicalType(String pathologicalType) {
        this.pathologicalType = pathologicalType;
    }

    public String getSampleSource() {
        return sampleSource;
    }

    public void setSampleSource(String sampleSource) {
        this.sampleSource = sampleSource;
    }

    public String getClinicalDiagnosis() {
        return clinicalDiagnosis;
    }

    public void setClinicalDiagnosis(String clinicalDiagnosis) {
        this.clinicalDiagnosis = clinicalDiagnosis;
    }

    public String getAnalysisDate() {
        return analysisDate;
    }

    public void setAnalysisDate(String analysisDate) {
        this.analysisDate = analysisDate;
    }

    public String getMolecularBioExperOper() {
        return molecularBioExperOper;
    }

    public void setMolecularBioExperOper(String molecularBioExperOper) {
        this.molecularBioExperOper = molecularBioExperOper;
    }

    public String getGeneAnalysis() {
        return geneAnalysis;
    }

    public void setGeneAnalysis(String geneAnalysis) {
        this.geneAnalysis = geneAnalysis;
    }

    public List<GeneDetectionResult> getUsefulGeneResult() {
        return usefulGeneResult;
    }

    public void setUsefulGeneResult(
            List<GeneDetectionResult> usefulGeneResult) {
        this.usefulGeneResult = usefulGeneResult;
    }

    public List<DrugResistanceSite> getResistanceSiteSum() {
        return resistanceSiteSum;
    }

    public void setResistanceSiteSum(
            List<DrugResistanceSite> resistanceSiteSum) {
        this.resistanceSiteSum = resistanceSiteSum;
    }

    public List<DrugResistanceSite> getPersonalizedMedicine() {
        return personalizedMedicine;
    }

    public void setPersonalizedMedicine(
            List<DrugResistanceSite> personalizedMedicine) {
        this.personalizedMedicine = personalizedMedicine;
    }

    public List<String> getReference() {
        return reference;
    }

    public void setReference(List<String> reference) {
        this.reference = reference;
    }

    public List<RecommendDrug> getRecommendDrug() {
        return recommendDrug;
    }

    public void setRecommendDrug(List<RecommendDrug> recommendDrug) {
        this.recommendDrug = recommendDrug;
    }

}
