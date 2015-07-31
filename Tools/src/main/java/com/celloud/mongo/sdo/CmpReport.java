package com.celloud.mongo.sdo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.code.morphia.annotations.Embedded;
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
	 * mysql中的数据报告编号
	 */
	private String dataKey;
	/**
	 * 报告所属用户id
	 */
	private Integer userId;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * Mysql 软件id
	 */
	private Integer appId;
	/**
	 * 软件名称
	 */
	private String appName;
	/**
	 * 所运行文件名称
	 */
	@Embedded
	private List<Data> data;
	/**
	 * 所属公司/医院信息
	 */
	private Company company;
	/**
	 * 所属用户详细信息
	 */
	private User user;
	/**
	 * 基因检测结果
	 */
	private List<GeneDetectionResult> cmpGeneResult;
	/**
	 * 分析日期
	 */
	private String runDate;
	/**
	 * 共获得有效片段
	 */
	private String allFragment;
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
	private String avgCoverage;
	/**
	 * 检测详细信息
	 */
	private Map<String, CmpGeneDetectionDetail> geneDetectionDetail;
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
	/**
	 * 用户填写部分
	 */
	private CmpFilling cmpFilling;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRunDate() {
		return runDate;
	}

	public void setRunDate(String runDate) {
		this.runDate = runDate;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	public List<GeneDetectionResult> getCmpGeneResult() {
		return cmpGeneResult;
	}

	public void setCmpGeneResult(List<GeneDetectionResult> cmpGeneResult) {
		this.cmpGeneResult = cmpGeneResult;
	}

	public String getAllFragment() {
		return allFragment;
	}

	public void setAllFragment(String allFragment) {
		this.allFragment = allFragment;
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

	public String getAvgCoverage() {
		return avgCoverage;
	}

	public void setAvgCoverage(String avgCoverage) {
		this.avgCoverage = avgCoverage;
	}

	public Map<String, CmpGeneDetectionDetail> getGeneDetectionDetail() {
		return geneDetectionDetail;
	}

	public void setGeneDetectionDetail(
			Map<String, CmpGeneDetectionDetail> geneDetectionDetail) {
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

	public CmpFilling getCmpFilling() {
		return cmpFilling;
	}

	public void setCmpFilling(CmpFilling cmpFilling) {
		this.cmpFilling = cmpFilling;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}
