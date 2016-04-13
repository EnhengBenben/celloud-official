package com.celloud.model.mongo;

/**
 * ABINJ存入mongodb 的字段
 * 
 * @author lin
 * @date 2016年4月13日 下午4:01:21
 */
public class ABINJ extends Base {
	private static final long serialVersionUID = 1L;

	private String resultPng;

	public String getResultPng() {
		return resultPng;
	}

	public void setResultPng(String resultPng) {
		this.resultPng = resultPng;
	}

}
