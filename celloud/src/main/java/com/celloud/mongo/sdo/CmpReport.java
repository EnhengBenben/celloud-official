package com.celloud.mongo.sdo;

import java.util.List;
import java.util.Map;

import com.google.code.morphia.annotations.Entity;

/**
 * CMP报告内容
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-8下午3:54:55
 * @version Revision: 1.0
 */
@Entity(noClassnameStored = true)
public class CmpReport extends Base {
	/**
	 * Mysql中的reportId
	 */
	private Integer reportId;
	/**
	 * 基因检测结果
	 */
	private List<GeneDetectionResult> cmpGeneResult;
	/**
	 * 共获得有效片段
	 */
	private String totalEffectFragments;
	/**
	 * 平均质量
	 */
	private String avgQuality;
	/**
	 * 平均GC含量(%)
	 */
	private String avgGCContent;
	/**
	 * 可用片段
	 */
	private String usableFragment;
	/**
	 * 待检基因
	 */
	private String noDetectedGene;
	/**
	 * 检测基因数
	 */
	private String detectedGene;
	/**
	 * 平均测序深度
	 */
	private String avgSequencingDepth;
	/**
	 * 检测详细信息
	 */
	private List<CmpGeneDetectionDetail> geneDetectionDetail;
	/**
	 * QC序列质量,数据1
	 */
	private Map<String, String> basicStatistics1;
	/**
	 * QC序列质量,数据2
	 */
	private Map<String, String> basicStatistics2;

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public List<GeneDetectionResult> getCmpGeneResult() {
		return cmpGeneResult;
	}

	public void setCmpGeneResult(List<GeneDetectionResult> cmpGeneResult) {
		this.cmpGeneResult = cmpGeneResult;
	}

	public String getTotalEffectFragments() {
		return totalEffectFragments;
	}

	public void setTotalEffectFragments(String totalEffectFragments) {
		this.totalEffectFragments = totalEffectFragments;
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

	public String getUsableFragment() {
		return usableFragment;
	}

	public void setUsableFragment(String usableFragment) {
		this.usableFragment = usableFragment;
	}

	public String getNoDetectedGene() {
		return noDetectedGene;
	}

	public void setNoDetectedGene(String noDetectedGene) {
		this.noDetectedGene = noDetectedGene;
	}

	public String getDetectedGene() {
		return detectedGene;
	}

	public void setDetectedGene(String detectedGene) {
		this.detectedGene = detectedGene;
	}

	public String getAvgSequencingDepth() {
		return avgSequencingDepth;
	}

	public void setAvgSequencingDepth(String avgSequencingDepth) {
		this.avgSequencingDepth = avgSequencingDepth;
	}

	public List<CmpGeneDetectionDetail> getGeneDetectionDetail() {
		return geneDetectionDetail;
	}

	public void setGeneDetectionDetail(
			List<CmpGeneDetectionDetail> geneDetectionDetail) {
		this.geneDetectionDetail = geneDetectionDetail;
	}

	public Map<String, String> getBasicStatistics1() {
		return basicStatistics1;
	}

	public void setBasicStatistics1(Map<String, String> basicStatistics1) {
		this.basicStatistics1 = basicStatistics1;
	}

	public Map<String, String> getBasicStatistics2() {
		return basicStatistics2;
	}

	public void setBasicStatistics2(Map<String, String> basicStatistics2) {
		this.basicStatistics2 = basicStatistics2;
	}
}
