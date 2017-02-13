package com.celloud.model.mongo;

import java.util.List;
import java.util.Map;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.celloud.model.mysql.DataFile;

@Entity(noClassnameStored = true)
public class FSocg extends Base {
	private static final long serialVersionUID = 1L;
	private List<List<String>> dna;
	private List<String> rna;
	@Embedded
	private List<DataFile> data;
	private Map<String, String> baseInfo;

	public List<List<String>> getDna() {
		return dna;
	}

	public void setDna(List<List<String>> dna) {
		this.dna = dna;
	}

	public List<String> getRna() {
		return rna;
	}

	public void setRna(List<String> rna) {
		this.rna = rna;
	}

	public List<DataFile> getData() {
		return data;
	}

	public void setData(List<DataFile> data) {
		this.data = data;
	}

	public Map<String, String> getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(Map<String, String> baseInfo) {
		this.baseInfo = baseInfo;
	}

}