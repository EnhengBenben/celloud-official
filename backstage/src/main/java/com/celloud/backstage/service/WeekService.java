package com.celloud.backstage.service;

import com.celloud.backstage.model.Week;

/**
 * @author MQ:
 * @date 2016年8月8日 下午6:09:00
 * @description 周统计接口
 */
public interface WeekService {
    /**
     * 
     * @author MQ
     * @date 2016年8月8日下午6:09:18
     * @description 添加一条周记录
     * @param week
     *            周统计对象
     *
     */
    public void add(Week week);

}
