package com.nova.service;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.Behavior;
import com.nova.service.impl.BehaviorServiceImpl;

@ImplementedBy(BehaviorServiceImpl.class)
public interface IBehaviorService {
    /**
     * 记录用户登录信息
     * 
     * @param behavior
     * @return
     */
    public int logLoginInfo(Behavior behavior);

    /**
     * 获取日志信息列表
     * 
     * @param page
     * @return
     */
    public PageList<Behavior> getUserLogInfo(Page page);

    /**
     * 查询日志记录，参数不为空则检索该日登录信息，否则全查
     * 
     * @param date
     *            ：日期，格式 yyyy-MM-dd
     * @return
     */
    public List<Behavior> getBehaviorList(String date);
}
