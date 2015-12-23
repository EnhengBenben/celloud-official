package com.celloud.sdo;

public class Entry {
	private String skey;
	private String svalue;

	public Entry(String key, String value) {
		this.skey = key;
		this.svalue = value;
	}

	public Entry() {
		super();
	}

	public String getSkey() {
		return skey;
	}

	public void setSkey(String skey) {
		this.skey = skey;
	}

	public String getSvalue() {
		return svalue;
	}

	public void setSvalue(String svalue) {
		this.svalue = svalue;
	}

}