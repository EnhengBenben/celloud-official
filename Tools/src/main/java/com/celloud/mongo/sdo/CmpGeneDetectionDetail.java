/**  */
package com.celloud.mongo.sdo;

import java.util.List;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-8下午5:00:32
 * @version Revision: 1.0
 */
public class CmpGeneDetectionDetail {
    /**
     * 平均测序深度
     */
    private String avgCoverage;
    /**
     * 检测结果详细列表
     */
    private List<CmpGeneSnpResult> result;

    public String getAvgCoverage() {
	return avgCoverage;
    }

    public void setAvgCoverage(String avgCoverage) {
	this.avgCoverage = avgCoverage;
    }

    public List<CmpGeneSnpResult> getResult() {
	return result;
    }

    public void setResult(List<CmpGeneSnpResult> result) {
	this.result = result;
    }
}
