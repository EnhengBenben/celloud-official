package com.nova.tools.constant;

/**
 * @Description：报告状态常量
 * @author lin
 * @date 2013-9-2 上午10:16:39
 */
public class ReportStateConstant {
    /**
     * 未执行
     */
    public static final int UNDO = 0;
    /**
     * 执行但是没有报告产生
     */
    public static final int NOREPORT = 1;
    /**
     * 正在运行并且已经产生报告
     */
    public static final int REPORT = 2;
    /**
     * 执行完毕
     */
    public static final int FINISH = 3;
}
