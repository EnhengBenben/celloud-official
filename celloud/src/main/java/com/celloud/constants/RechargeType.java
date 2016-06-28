package com.celloud.constants;

/**
 * 充值方式类型
 * 
 * @author leamo
 * @date 2016年6月27日 下午2:56:10
 */
public enum RechargeType {
    ALIPAY(0, true), TRANSFER(1, true), PRESENT(2, false);
    private Integer type;
    private boolean invoice;

    private RechargeType(Integer type, boolean invoice) {
        this.type = type;
        this.invoice = invoice;
    }

    public Integer type() {
        return this.type;
    }

    public boolean invoice() {
        return this.invoice;
    }

    public static boolean getIcon(Integer type) {
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
