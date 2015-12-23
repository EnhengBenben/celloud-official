package com.celloud.sdo;

public class TotalCount {
	private String time;
	private int  logNum;
	private int activityUser;;
	private int runNum;
	private int activityApp;
	private long dataSize;
	
	public TotalCount() {
		super();
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getLogNum() {
		return logNum;
	}
	public void setLogNum(int logNum) {
		this.logNum = logNum;
	}
	public int getActivityUser() {
		return activityUser;
	}
	public void setActivityUser(int activityUser) {
		this.activityUser = activityUser;
	}

	public int getRunNum() {
		return runNum;
	}
	public void setRunNum(int runNum) {
		this.runNum = runNum;
	}
	public int getActivityApp() {
		return activityApp;
	}
	public void setActivityApp(int activityApp) {
		this.activityApp = activityApp;
	}
	public long getDataSize() {
		return dataSize;
	}
	public void setDataSize(long dataSize) {
		this.dataSize = dataSize;
	}
	@Override
	public String toString() {
		return "TotalCount [time=" + time + ", logNum=" + logNum + ", activityUser=" + activityUser + ", runTime="
				 + ", activityApp=" + activityApp + ", dataSize=" + dataSize + "]";
	}
}
