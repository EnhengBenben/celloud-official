package com.celloud.wechat;

/**
 * 微信消息模板所需参数
 * 
 * @author leamo
 * @date 2016年6月30日 下午3:00:11
 */
public class WebchatParams {
    public static enum RUN_OVER {
        app, startDate, endDate
    }

    public static enum LOGIN {
        username, ip
    }

    /**
     * 资金变动
     * 
     * @author leamo
     * @date 2016年6月30日 下午3:07:25
     */
    public static enum BALANCE_CHANGE {
        /**
         * 变动金额
         */
        amount,
        /**
         * 当前余额
         */
        balances
    }

    public static enum PWD_UPDATE {
        username
    }
}
