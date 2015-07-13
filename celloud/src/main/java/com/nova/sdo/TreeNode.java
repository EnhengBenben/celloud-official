package com.nova.sdo;

import java.io.Serializable;

/**
 * 树节点sdo
 * 
 * @author <a href="mailto:linyongchao@novacloud.com">linyc</a>
 * @date 2012-12-19上午11:29:36
 * @version Revision: 1.0
 */
public class TreeNode implements Serializable {
	private static final long serialVersionUID = 1L;
	public int id;// 节点ID
	public int pId;// 父节点
	public String name;// 节点名称
	public boolean checked;// 是否选中
	public boolean open;// 是否展开 true 是，false 否

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

}
