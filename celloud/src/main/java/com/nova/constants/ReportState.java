package com.nova.constants;

/**
 * @报告状态常量类 @ 类名称：ReportState  
 * @类描述：  
 * @创建人：zl  
 * @创建时间：2013-8-16 下午1:40:04    
 * @version      
 */
public class ReportState {
    /**
     * 未运行
     */
    public static final int UNRUN = 0;
    /**
     * 运行,但是没有产生报告
     */
    public static final int NOREPORT = 1;
    /**
     * 运行并且已经产生报告
     */
    public static final int HAVEREPORT = 2;
    /**
     * 运行完成
     */
    public static final int COMPLETE = 3;
}
