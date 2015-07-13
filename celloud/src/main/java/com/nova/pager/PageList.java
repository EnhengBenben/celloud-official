package com.nova.pager;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据.包括分页对象和具体的list数据.
 * 
 * @version $Id: PageList.java 2010-09-17 13:53:23Z tomaer $
 */
public final class PageList<T> implements Serializable {

	private static final long serialVersionUID = 7636400405542683379L;

	private List<T> datas;

	private Page page;

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}
