package com.celloud.manager.model;

/**
 * select2下拉列表bean对象
 * 
 * @author MQ
 *
 */
public class UserSelect {
	// 对应option的value
	private String id;
	// 对应option的显示名称
	private String text;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "UserSelect [id=" + id + ", text=" + text + "]";
	}

}
