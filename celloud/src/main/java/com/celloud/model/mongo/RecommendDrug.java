package com.celloud.model.mongo;

/**
 * 推荐用药
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-8下午4:30:13
 * @version Revision: 1.0
 */
public class RecommendDrug {
    /**
     * 药物名称
     */
    private String drugName;
    /**
     * 药物描述
     */
    private String drugDescrip;

    public String getDrugName() {
	return drugName;
    }

    public void setDrugName(String drugName) {
	this.drugName = drugName;
    }

    public String getDrugDescrip() {
	return drugDescrip;
    }

    public void setDrugDescrip(String drugDescrip) {
	this.drugDescrip = drugDescrip;
    }

}
