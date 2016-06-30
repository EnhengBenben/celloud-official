package com.celloud.constants;

/**
 * 充值方式类型
 * 
 * @author leamo
 * @date 2016年6月27日 下午2:56:10
 */
public enum RechargeType {
    ALIPAY("支付宝", 0, true), TRANSFER("银行转账", 1, true), PRESENT("系统赠送", 2, false), OnlineBanking("网上银行", 3, false);
    private Integer type;
    private boolean invoice;
    private String name;

    private RechargeType(String name, Integer type, boolean invoice) {
        this.type = type;
        this.invoice = invoice;
        this.name = name;
    }

    public Integer type() {
        return this.type;
    }

    public boolean invoice() {
        return this.invoice;
    }

    public static String getName(Integer type) {
        String name = "未知";
        for (RechargeType r : RechargeType.values()) {
            if (r.type().equals(type)) {
                name = r.name;
                break;
            }
        }
        return name;
    }

    public static boolean getRechargeTypeInvoice(Integer type) {
        boolean invoice = true;
        for (RechargeType r : RechargeType.values()) {
            if (r.type().equals(type)) {
                invoice = r.invoice();
                break;
            }
        }
        return invoice;
    }
}
