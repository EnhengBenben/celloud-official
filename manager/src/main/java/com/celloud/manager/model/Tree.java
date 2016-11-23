package com.celloud.manager.model;

public class Tree {
	/**
	 * 自增主键
	 */
	private Integer id;
	/**
	 * 父节点
	 */
	private Integer parentId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

}
