package com.celloud.model.mongo;

import org.mongodb.morphia.annotations.Entity;

@Entity(noClassnameStored = true)
public class ExpenseDiscount {
    /** 折扣名称 */
    private String name;
    /** 折扣率 */
    private Float discountRate;
    /** 折扣价 */
    private String discountPrice;

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

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }
}
