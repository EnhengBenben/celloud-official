package com.celloud.manager.service;

import com.celloud.manager.model.ActionLog;
import com.celloud.manager.page.Page;
import com.celloud.manager.page.PageList;

/**
 * 用户操作日志service
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月29日 下午2:00:28
 */
public interface ActionLogService {
    /**
     * 记录用户操作日志
     * 
     * @param action
     * @param message
     */
    public void log(String action, String message);

    /**
     * 插入用户操作日志
     * 
     * @param log
     */
    public void insert(ActionLog log);

    /**
     * 分页获取用户的操作日志
     * 
     * @param userId
     * @param page
     * @return
     */
    public PageList<ActionLog> findLogs(int userId, Page page);

}
