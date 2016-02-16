package com.celloud.model;

/**
 * CMP报告中基因结果部分
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-8下午2:59:58
 * @version Revision: 1.0
 */
public class GeneDetectionResult {
    /**
     * 基因名
     */
    private String geneName;
    /**
     * 已知突变位点数(简写)
     */
    private String knownMSNum;
    /**
     * 测序深度
     */
    private Integer sequencingDepth;

    public String getGeneName() {
	return geneName;
    }

    public void setGeneName(String geneName) {
	this.geneName = geneName;
    }

    public String getKnownMSNum() {
	return knownMSNum;
    }

    public void setKnownMSNum(String knownMSNum) {
	this.knownMSNum = knownMSNum;
    }

    public Integer getSequencingDepth() {
	return sequencingDepth;
    }

    public void setSequencingDepth(Integer sequencingDepth) {
	this.sequencingDepth = sequencingDepth;
    }
}
