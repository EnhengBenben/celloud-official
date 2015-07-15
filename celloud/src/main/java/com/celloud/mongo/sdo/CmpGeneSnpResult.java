/**  */
package com.celloud.mongo.sdo;

/**
 * 基因snp检测详细结果（对应CMP报告第五部分中表格内容）
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-8下午5:09:11
 * @version Revision: 1.0
 */
public class CmpGeneSnpResult {
	private String gene;
	private String refBase;
	private String mutBase;
	private String depth;
	private String cdsMutSyntax;
	private String aaMutSyntax;

	public String getGene() {
		return gene;
	}

	public void setGene(String gene) {
		this.gene = gene;
	}

	public String getRefBase() {
		return refBase;
	}

	public void setRefBase(String refBase) {
		this.refBase = refBase;
	}

	public String getMutBase() {
		return mutBase;
	}

	public void setMutBase(String mutBase) {
		this.mutBase = mutBase;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getCdsMutSyntax() {
		return cdsMutSyntax;
	}

	public void setCdsMutSyntax(String cdsMutSyntax) {
		this.cdsMutSyntax = cdsMutSyntax;
	}

	public String getAaMutSyntax() {
		return aaMutSyntax;
	}

	public void setAaMutSyntax(String aaMutSyntax) {
		this.aaMutSyntax = aaMutSyntax;
	}

}
