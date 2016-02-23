package com.celloud.model.mongo;


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
    private String mutationType;
    private String diseaseEngName;
    private String diseaseName;
    private String diseaseType;
    /** 遗传方式 */
    private String geneticMethod;
    /** het:杂合 Hom:纯合 */
    private String hetOrHom;
    private Integer mutNum;

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

    public String getMutationType() {
        return mutationType;
    }

    public void setMutationType(String mutationType) {
        this.mutationType = mutationType;
    }

    public String getDiseaseEngName() {
        return diseaseEngName;
    }

    public void setDiseaseEngName(String diseaseEngName) {
        this.diseaseEngName = diseaseEngName;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }

    public Integer getMutNum() {
        return mutNum;
    }

    public void setMutNum(Integer mutNum) {
        this.mutNum = mutNum;
    }

    public String getGeneticMethod() {
        return geneticMethod;
    }

    public void setGeneticMethod(String geneticMethod) {
        this.geneticMethod = geneticMethod;
    }

    public String getHetOrHom() {
        return hetOrHom;
    }

    public void setHetOrHom(String hetOrHom) {
        this.hetOrHom = hetOrHom;
    }
}