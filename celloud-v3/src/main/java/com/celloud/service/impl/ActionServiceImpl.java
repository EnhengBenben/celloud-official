package com.celloud.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.ConstantsData;
import com.celloud.mapper.ActionLogMapper;
import com.celloud.model.ActionLog;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.ActionLogService;
import com.celloud.utils.UserAgentUtil;

/**
 * 用户操作日志service实现类
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月29日 下午2:10:01
 */
@Service("actionLogServiceImpl")
public class ActionServiceImpl implements ActionLogService {
    @Resource
    private ActionLogMapper logMapper;

    @Override
    public void log(String action, String message) {
        ActionLog log = UserAgentUtil.getActionLog(ConstantsData.getRequset());
        log.setOperate(action);
        log.setMessage(message);
        log.setLogDate(new Date());
        log.setUserId(ConstantsData.getLoginUserId());
        log.setUsername(ConstantsData.getLoginUserName());
        new ActionLogThread(log).setService(this).start();
    }

    @Override
    public void insert(ActionLog log) {
        logMapper.insert(log);
    }

    class ActionLogThread extends Thread {
        private ActionLog log;
        private ActionLogService service;

        public ActionLogThread(ActionLog log) {
            this.log = log;
        }

        @Override
        public void run() {
            service.insert(log);
        }

        public ActionLogThread setService(ActionLogService service) {
            this.service = service;
            return this;
        }

    }

    @Override
    public PageList<ActionLog> findLogs(int userId, Page page) {
        List<ActionLog> list = logMapper.findLogs(ConstantsData.getLoginUserId(), page);
        return new PageList<>(page, list);
    }
}