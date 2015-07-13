package com.nova.sdo;

import java.io.Serializable;

/**
 * 项目参数-物种实体类
 * @author zl
 *
 */
public class ParamStrain implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String strainText;
	private String strainVal;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStrainText() {
		return strainText;
	}
	public void setStrainText(String strainText) {
		this.strainText = strainText;
	}
	public String getStrainVal() {
		return strainVal;
	}
	public void setStrainVal(String strainVal) {
		this.strainVal = strainVal;
	}
	
}
