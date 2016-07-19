package com.celloud.model.mongo;

import java.util.List;

public class Rocky  extends MIB {
	private static final long serialVersionUID = -919145643142962977L;
	private List<RockyRecord> records;
	public List<RockyRecord> getRecords() {
		return records;
	}

	public void setRecords(List<RockyRecord> records) {
		this.records = records;
	}
}
