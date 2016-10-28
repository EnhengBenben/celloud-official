package com.celloud.mongo.sdo;

/**
 * 基因耐药位点及耐药汇总
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-8下午3:52:26
 * @version Revision: 1.0
 */
public class DrugResistanceSite {
    /**
     * 基因名
     */
    private String geneName;
    /**
     * 突变位点
     */
    private String mutationSite;
    /**
     * 药物
     */
    private String drug;

    public String getGeneName() {
	return geneName;
    }

    public void setGeneName(String geneName) {
	this.geneName = geneName;
    }

    public String getMutationSite() {
	return mutationSite;
    }

    public void setMutationSite(String mutationSite) {
	this.mutationSite = mutationSite;
    }

    public String getDrug() {
	return drug;
    }

    public void setDrug(String drug) {
	this.drug = drug;
    }
}
