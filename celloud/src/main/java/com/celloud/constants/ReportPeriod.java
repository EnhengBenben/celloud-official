package com.celloud.constants;

/**
 * 报告运行状态
 * 
 * @author liuqx
 * @date 2016-1-5 上午11:18:01
 */
public class ReportPeriod {
    /**未运行*/
    public static final int NOT_RUN = 0;
    /**执行但是没有报告产生*/
    public static final int RUNNING_NO_REPORT = 1;
    /**正在运行并且已经产生报告*/
    public static final int RUNNING_HAVE_REPORT = 2;
    /**执行完毕*/
    public static final int COMPLETE = 3;
}
