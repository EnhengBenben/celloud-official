package com.celloud.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.celloud.sdo.Task;
import com.celloud.service.TaskService;
import com.google.inject.Inject;
import com.nova.action.BaseAction;
import com.nova.utils.Base64Util;
import com.nova.utils.PropertiesUtil;
import com.nova.utils.RemoteRequests;

/**
 * 任务应用
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-11-3下午5:18:22
 * @version Revision: 1.0
 */
@ParentPackage("celloud-default")
@Action("task")
@Results({ @Result(name = "success", type = "json", params = { "root",
        "conditionInt" }) })
public class TaskAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(DataAction.class);
    @Inject
    private TaskService taskService;
    private Task task;
    private Integer conditionInt;

    /**
     * 修改任务状态并执行排队任务
     * 
     * @return
     */
    public String updateTaskState() {
        taskService.updateToDone(task.getTaskId());
        log.info("任务" + task.getTaskId() + "执行完毕");
        task = taskService.getFirstTask(task.getAppId());
        if (task != null) {
            StringBuffer remotePath = new StringBuffer();
            Long taskId = task.getTaskId();
            remotePath.append(PropertiesUtil.toolsOutPath)
                    .append("Procedure!runApp?userId=")
                    .append(task.getUserId()).append("&appId=")
                    .append(task.getAppId()).append("&dataKey=")
                    .append(task.getDataKey()).append("&taskId=")
                    .append(taskId).append("&command=")
                    .append(Base64Util.encrypt(task.getCommand())).append("&")
                    .append(task.getParams());
            RemoteRequests rr = new RemoteRequests();
            rr.run(remotePath.toString());
            log.info("任务" + task.getTaskId() + "开始完成投递");
            taskService.updateToRunning(taskId);
        }
        conditionInt = 1;
        return SUCCESS;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Integer getConditionInt() {
        return conditionInt;
    }

    public void setConditionInt(Integer conditionInt) {
        this.conditionInt = conditionInt;
    }

}
