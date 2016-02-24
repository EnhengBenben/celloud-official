package com.celloud.model.mongo;

import java.math.BigDecimal;

import org.mongodb.morphia.annotations.Entity;

@Entity(noClassnameStored = true)
public class ExpenseDiscount {
    /** 折扣名称 */
    private String name;
    /** 折扣率 */
    private Float discountRate;
    /** 折扣价 */
    private BigDecimal discountPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Float discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }
}
