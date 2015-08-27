package com.nova.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.nova.utils.FileTools;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("json-default")
@Action("count")
@Results({ @Result(name = "success", type = "json", params = { "root", "flag" }) })
public class CountAction extends ActionSupport {
    Logger log = Logger.getLogger(CountAction.class);

    private static final long serialVersionUID = 1L;

    private String appId;
    private String columns;
    private String path;
    private String flag;
    private Integer length;

    /**
     * PGS流程数据分析
     * 
     * @return
     */
    public String getData() {
	if (columns == null) {
	    flag = "";
	    return SUCCESS;
	}
	String column[] = columns.split(",");
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < column.length; i++) {
	    String fileName = path + appId + "_" + column[i];
	    File file = new File(fileName);
	    if (file.exists()) {
		sb.append(";" + column[i] + ":");
		BufferedReader br = null;
		try {
		    br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
		String line = null;
		try {
		    while ((line = br.readLine()) != null) {
			sb.append(line + ",");
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
			if (br != null) {
			    br.close();
			}
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}
	    }
	}
	flag = sb.toString();
	return SUCCESS;
    }

    /**
     * HCV流程的数据报告分析
     * 
     * @return
     */
    public String getHCV() {
	List<String> list = FileTools.fileSearch(path, appId, "startWith");
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < list.size(); i++) {
	    String name = list.get(i);
	    String val = FileTools.getFirstLine(path + name);
	    sb.append(name.substring(name.lastIndexOf("_") + 1) + "," + val
		    + ";");
	}
	flag = sb.toString();
	return SUCCESS;
    }

    /**
     * HBV流程数据参数同比
     * 
     * @return
     */
    public String getHBV() {
	List<String> list = FileTools.fileSearch(path, appId, "startWith");
	StringBuffer sb = new StringBuffer();
	StringBuffer type = new StringBuffer();
	String[] sl = new String[list.size()];
	list.toArray(sl);
	Arrays.sort(sl);
	for (int i = 0; i < sl.length; i++) {
	    for (int j = sl.length - 1; j > i; j--) {
		if ((sl[i].length() > sl[j].length()) && (sl[i].length() > 4)
			&& (sl[j].length() > 4)) {
		    String temp = sl[i];
		    sl[i] = sl[j];
		    sl[j] = temp;
		}
	    }
	}
	// 数组排序完成后再写回到列表中
	ListIterator<String> l = list.listIterator();
	for (int j = 0; j < sl.length; j++) {
	    l.next();
	    l.set(sl[j]);
	}
	int[] hbvType = new int[10];
	for (int i = 0; i < list.size(); i++) {
	    String name = list.get(i);
	    String column = name
		    .substring(name.indexOf("_") + 1, name.length());
	    String val = FileTools.getFirstLine(path + name);
	    int val_i = Integer.valueOf(val);
	    if (name.equals("82_A")) {
		hbvType[0] = val_i;
	    } else if (name.equals("82_B")) {
		hbvType[1] = val_i;
	    } else if (name.equals("82_C")) {
		hbvType[2] = val_i;
	    } else if (name.equals("82_D")) {
		hbvType[3] = val_i;
	    } else if (name.equals("82_E")) {
		hbvType[4] = val_i;
	    } else if (name.equals("82_F")) {
		hbvType[5] = val_i;
	    } else if (name.equals("82_G")) {
		hbvType[6] = val_i;
	    } else if (name.equals("82_H")) {
		hbvType[7] = val_i;
	    } else if (name.equals("82_no match")) {
		hbvType[8] = val_i;
	    } else if (name.equals("82_")) {
		hbvType[9] = val_i;
	    }
	    if (name.length() == 4 || name.endsWith("no match")) {
		type.append(column).append(",").append(val).append(";");
	    } else if (name.equals("82_")) {
		type.append("none").append(",").append(val).append(";");
	    } else if (name.length() > 4 && !name.endsWith("yes")
		    && !name.endsWith("no")) {
		sb.append(column).append(",").append(val).append(";");
	    } else if (name.equals("82")) {
		sb.append("none").append(",").append(val).append(";");
	    }
	}
	flag = sb.toString() + "@" + Arrays.toString(hbvType);
	return SUCCESS;
    }

    /**
     * TB流程的数据报告分析
     * 
     * @return
     */
    public String getTB() {
	flag = FileTools.getLimitLines(path + appId, 1, 10);
	return SUCCESS;
    }

    /**
     * EGFR和KRAS流程数据报告分析
     * 
     * @return
     */
    public String getEGFR() {
	flag = FileTools.getLimitLines(path + appId + "_" + length, 1, 10);
	return SUCCESS;
    }

    public String getAppId() {
	return appId;
    }

    public void setAppId(String appId) {
	this.appId = appId;
    }

    public String getFlag() {
	return flag;
    }

    public void setFlag(String flag) {
	this.flag = flag;
    }

    public String getColumns() {
	return columns;
    }

    public void setColumns(String columns) {
	this.columns = columns;
    }

    public String getPath() {
	return path;
    }

    public void setPath(String path) {
	this.path = path;
    }

    public Integer getLength() {
	return length;
    }

    public void setLength(Integer length) {
	this.length = length;
    }

}