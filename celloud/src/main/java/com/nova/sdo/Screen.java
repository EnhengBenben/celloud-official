package com.nova.sdo;

import java.io.Serializable;

/**
 * 软件截图表
 * 
 * @author <a href="mailto:linyongchao@novacloud.com">linyc</a>
 * @date 2013-1-21下午1:27:42
 * @version Revision: 1.0
 */
public class Screen implements Serializable {
    private static final long serialVersionUID = 1L;
    private int screenId;// 编号
    private String screenName;// 截图名称
    private int softwareId;// 软件编号

    public int getScreenId() {
	return screenId;
    }

    public void setScreenId(int screenId) {
	this.screenId = screenId;
    }

    public String getScreenName() {
	return screenName;
    }

    public void setScreenName(String screenName) {
	this.screenName = screenName;
    }

    public int getSoftwareId() {
	return softwareId;
    }

    public void setSoftwareId(int softwareId) {
	this.softwareId = softwareId;
    }
}