package com.celloud.constants;

/**
 * 任务运行状态
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-11-3下午3:55:38
 * @version Revision: 1.0
 */
public class TaskPeriod {
    /** 等待 */
    public static final int WAITTING = 0;
    /** 正在运行 */
    public static final int RUNNING = 1;
    /** 运行完成 */
    public static final int DONE = 2;
    /** 正在上传 */
    public static final int UPLOADING = 3;
    /** 异常中止 */
    public static final int ERROR = 4;
    /** 送样中 */
    public static final int SAMPLING = 5;
    /** 实验中 */
    public static final int INEXPERIMENT = 6;

    /** 报告未读 */
    public static final int NO_READ = 0;
    /** 报告已读 */
    public static final int READED = 1;
}
