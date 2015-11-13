package com.celloud.sdo;

import java.io.Serializable;

/**
 * 软件截图表
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-10-29下午5:39:24
 * @version Revision: 1.0
 */
public class Screen implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer screenId;
    private String screenName;
    private Integer softwareId;

    public Integer getScreenId() {
        return screenId;
    }

    public void setScreenId(Integer screenId) {
        this.screenId = screenId;
    }
    public String getScreenName() {
        return screenName;
    }
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Integer getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Integer softwareId) {
        this.softwareId = softwareId;
    }

}