package com.celloud.model.mongo;

import java.util.List;
import java.util.Map;

public class Rocky extends Base {
	private static final long serialVersionUID = -919145643142962977L;
	private boolean pathogenic;
	private Map<String, String> baseInfo;
	private List<RockyRecord> records;

	public List<RockyRecord> getRecords() {
		return records;
	}

	public void setRecords(List<RockyRecord> records) {
		this.records = records;
	}

	public boolean isPathogenic() {
		return pathogenic;
	}

	public void setPathogenic(boolean pathogenic) {
		this.pathogenic = pathogenic;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Map<String, String> getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(Map<String, String> baseInfo) {
		this.baseInfo = baseInfo;
	}

}
