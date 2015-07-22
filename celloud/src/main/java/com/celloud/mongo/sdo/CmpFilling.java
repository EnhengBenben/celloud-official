/**  */
package com.celloud.mongo.sdo;

import java.util.Date;
import java.util.List;

/**
 * CMP报告——用户填写部分
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-13下午1:37:58
 * @version Revision: 1.0
 */
public class CmpFilling {
	/**
	 * 肿瘤类型
	 */
	private String tumorType;
	/**
	 * 病人详细信息：姓名、性别、年龄
	 */
	private PatientBasic patientBasic;
	/**
	 * 取样日期
	 */
	private Date samplingDate;
	/**
	 * 报告日期
	 */
	private Date reportDate;
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
	private Date analysisDate;
	/**
	 * 分子生物实验操作（简写）
	 */
	private String molecularBioExperOper;
	/**
	 * 基因分析
	 */
	private String geneAnalysis;
	/**
	 * 耐药位点汇总
	 */
	private List<DrugResistanceSite> resistanceSiteSum;
	/**
	 * 个性化用药——耐药位点
	 */
	private List<DrugResistanceSite> personalizedMedicine;
	/**
	 * 推荐用药
	 */
	private List<RecommendDrug> recommendDrug;

	public String getTumorType() {
		return tumorType;
	}

	public void setTumorType(String tumorType) {
		this.tumorType = tumorType;
	}

	public PatientBasic getPatientBasic() {
		return patientBasic;
	}

	public void setPatientBasic(PatientBasic patientBasic) {
		this.patientBasic = patientBasic;
	}

	public Date getSamplingDate() {
		return samplingDate;
	}

	public void setSamplingDate(Date samplingDate) {
		this.samplingDate = samplingDate;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
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

	public Date getAnalysisDate() {
		return analysisDate;
	}

	public void setAnalysisDate(Date analysisDate) {
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

	public List<DrugResistanceSite> getResistanceSiteSum() {
		return resistanceSiteSum;
	}

	public void setResistanceSiteSum(List<DrugResistanceSite> resistanceSiteSum) {
		this.resistanceSiteSum = resistanceSiteSum;
	}

	public List<DrugResistanceSite> getPersonalizedMedicine() {
		return personalizedMedicine;
	}

	public void setPersonalizedMedicine(
			List<DrugResistanceSite> personalizedMedicine) {
		this.personalizedMedicine = personalizedMedicine;
	}

	public List<RecommendDrug> getRecommendDrug() {
		return recommendDrug;
	}

	public void setRecommendDrug(List<RecommendDrug> recommendDrug) {
		this.recommendDrug = recommendDrug;
	}
}
