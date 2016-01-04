package com.celloud.service;

import com.celloud.model.ActionLog;

/**
 * 用户操作日志service
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月29日 下午2:00:28
 */
public interface ActionLogService {
    public void log(String action, String message);

    public void insert(ActionLog log);

}
