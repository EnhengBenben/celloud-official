package com.nova.sdo;

import java.io.Serializable;

/**
 * 
 * CMP中50个基因信息
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">leamox</a>
 * @date 2015-6-17上午11:34:00
 * @version Revision: 1.0
 */
public class SoftCMPGene implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer cmpId;
    private String cmpName;
    private String cmpDescription;
    private String cmpTreatment;
    private String cmpTreatmentDesc;
    private String cmpImage;

    public Integer getCmpId() {
	return cmpId;
    }

    public void setCmpId(Integer cmpId) {
	this.cmpId = cmpId;
    }

    public String getCmpName() {
	return cmpName;
    }

    public void setCmpName(String cmpName) {
	this.cmpName = cmpName;
    }

    public String getCmpDescription() {
	return cmpDescription;
    }

    public void setCmpDescription(String cmpDescription) {
	this.cmpDescription = cmpDescription;
    }

    public String getCmpTreatment() {
	return cmpTreatment;
    }

    public void setCmpTreatment(String cmpTreatment) {
	this.cmpTreatment = cmpTreatment;
    }

    public String getCmpTreatmentDesc() {
	return cmpTreatmentDesc;
    }

    public void setCmpTreatmentDesc(String cmpTreatmentDesc) {
	this.cmpTreatmentDesc = cmpTreatmentDesc;
    }

    public String getCmpImage() {
	return cmpImage;
    }

    public void setCmpImage(String cmpImage) {
	this.cmpImage = cmpImage;
    }
}
