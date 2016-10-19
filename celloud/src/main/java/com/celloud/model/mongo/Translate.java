package com.celloud.model.mongo;

/**
 * Translate存入mongodb的字段
 * 
 * @author lin
 * @date 2016年4月19日 下午5:44:18
 */
public class Translate extends Base {
	private static final long serialVersionUID = 1L;

	private String result;
	private String source;
	private String fileName;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
