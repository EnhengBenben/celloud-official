package com.nova.utils;

public class Page {
	private int pageNow;// 当前页
	private int pageSize = 6;// 页大小
	private int totalPage;// 总页数
	private int totalSize;// 记录总数
	private boolean hasFirst;// 是否有第一页
	private boolean hasPre;// 是否有上一页
	private boolean hasNext;// 是否有下一页
	private boolean hasLast;// 是否有最后一页

	public Page() {

	}

	public Page(int pageNow, int pageSize, int totalSize) {
		this.pageNow = pageNow;
		this.pageSize = pageSize;
		this.totalSize = totalSize;
		this.totalPage = this.getTotalPage();
		this.hasFirst = this.isHasFirst();
		this.hasPre = this.isHasPre();
		this.hasNext = this.isHasNext();
		this.hasLast = this.isHasLast();
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		totalPage = getTotalSize() / getPageSize();
		if (totalSize % pageSize != 0)
			totalPage++;
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public boolean isHasFirst() {
		if (pageNow == 1)
			return false;
		else
			return true;
	}

	public void setHasFirst(boolean hasFirst) {
		this.hasFirst = hasFirst;
	}

	public boolean getHasFirst() {
		this.hasFirst = isHasFirst();
		return this.hasFirst;
	}

	public boolean isHasPre() {
		if (this.isHasFirst())
			return true;
		else
			return false;
	}

	public void setHasPre(boolean hasPre) {
		this.hasPre = hasPre;
	}

	public boolean getHasPre() {
		this.hasPre = this.isHasPre();
		return this.hasPre;
	}

	public boolean isHasNext() {
		if (isHasLast())
			return true;
		else
			return false;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public boolean getHasNext() {
		this.hasNext = this.isHasNext();
		return this.hasNext;
	}

	public boolean isHasLast() {
		if (pageNow == this.getTotalPage())
			return false;
		else
			return true;
	}

	public void setHasLast(boolean hasLast) {
		this.hasLast = hasLast;
	}

	public boolean getHasLast() {
		this.hasLast = this.isHasLast();
		return this.hasLast;
	}
}
