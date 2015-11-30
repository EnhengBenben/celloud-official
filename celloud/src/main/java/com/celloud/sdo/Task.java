package com.celloud.sdo;

import java.io.Serializable;
import java.util.Date;

import com.google.code.morphia.annotations.Entity;

/**
 * 数据投递任务表
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-11-3下午2:02:30
 * @version Revision: 1.0
 */
@Entity
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long taskId;
    private Long userId;// 用户编号
    private Long appId;// 应用编号
    private Long projectId;// 项目编号
    private String dataKey;// 数据编号
    private String command;// 投递命令
    private Integer state;// 命令执行状态 0-等待 1-正在运行 2-运行完成
    private Date createDate;// 创建时间
    private Date startDate;// 开始运行时间
    private Date endDate;// 运行完成时间
    private String params;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

}
