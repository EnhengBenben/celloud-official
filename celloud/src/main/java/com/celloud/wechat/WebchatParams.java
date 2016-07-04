package com.celloud.wechat;

/**
 * 微信消息模板所需参数
 * 
 * @author leamo
 * @date 2016年6月30日 下午3:00:11
 */
public class WebchatParams {
    public static enum RUN_OVER {
        first,
        /**
         * 运行APP
         */
        keyword1,
        /**
         * 开始时间
         */
        keyword2,
        /**
         * 结束时间
         */
        keyword3, remark
    }

    public static enum LOGIN {
        first, time, ip, reason
    }

    /**
     * 资金变动
     * 
     * @author leamo
     * @date 2016年6月30日 下午3:07:25
     */
    public static enum BALANCE_CHANGE {
        first,
        /**
         * 变动时间
         */
        date,
        /**
         * 变动金额
         */
        adCharge,
        /**
         * 资金类型
         */
        type,
        /**
         * 账户余额
         */
        cashBalance, remark
    }

    public static enum PWD_UPDATE {
        first,
        /**
         * 邮箱类型
         */
        productName,
        /**
         * 修改时间
         */
        time, remark
    }
}
