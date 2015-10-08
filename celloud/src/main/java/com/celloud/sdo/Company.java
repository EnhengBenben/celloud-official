package com.celloud.sdo;

import java.io.Serializable;

public class Company implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer companyId;
    private String companyName;
    private String englishName;
    private String companyIcon;
    private String address;
    /** 英文地址 */
    private String addressEn;
    /** 邮编 */
    private String zipCode;
    private String tel;
    /** 是否删除：0-未删除，1-已删除 */
    private Integer state;

    public Integer getCompanyId() {
	return companyId;
    }

    public void setCompanyId(Integer companyId) {
	this.companyId = companyId;
    }

    public String getCompanyName() {
	return companyName;
    }

    public void setCompanyName(String companyName) {
	this.companyName = companyName;
    }

    public String getEnglishName() {
	return englishName;
    }

    public void setEnglishName(String englishName) {
	this.englishName = englishName;
    }

    public String getCompanyIcon() {
	return companyIcon;
    }

    public void setCompanyIcon(String companyIcon) {
	this.companyIcon = companyIcon;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getAddressEn() {
	return addressEn;
    }

    public void setAddressEn(String addressEn) {
	this.addressEn = addressEn;
    }

    public String getZipCode() {
	return zipCode;
    }

    public void setZipCode(String zipCode) {
	this.zipCode = zipCode;
    }

    public String getTel() {
	return tel;
    }

    public void setTel(String tel) {
	this.tel = tel;
    }

    public Integer getState() {
	return state;
    }

    public void setState(Integer state) {
	this.state = state;
    }
}