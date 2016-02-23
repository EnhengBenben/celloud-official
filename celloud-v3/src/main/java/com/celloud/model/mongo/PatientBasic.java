package com.celloud.model.mongo;

/**
 * 各个报告的病人信息
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-8下午1:58:06
 * @version Revision: 1.0
 */
public class PatientBasic {
    /**
     * 姓名
     */
    private String patientName;
    /**
     * 性别
     */
    private String patientSex;
    /**
     * 年龄
     */
    private String patientAge;

    public String getPatientName() {
	return patientName;
    }

    public void setPatientName(String patientName) {
	this.patientName = patientName;
    }

    public String getPatientSex() {
	return patientSex;
    }

    public void setPatientSex(String patientSex) {
	this.patientSex = patientSex;
    }

    public String getPatientAge() {
	return patientAge;
    }

    public void setPatientAge(String patientAge) {
	this.patientAge = patientAge;
    }
}
