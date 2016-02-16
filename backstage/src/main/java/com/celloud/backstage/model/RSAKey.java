package com.celloud.backstage.model;

import java.util.Calendar;
import java.util.Date;

import com.celloud.backstage.constants.Constants;

public class RSAKey {
    private Integer id;

    private String modulus;

    private String pubExponent;

    private String priExponent;

    private Integer userId;

    private Integer state;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModulus() {
        return modulus;
    }

    public void setModulus(String modulus) {
        this.modulus = modulus == null ? null : modulus.trim();
    }

    public String getPubExponent() {
        return pubExponent;
    }

    public void setPubExponent(String pubExponent) {
        this.pubExponent = pubExponent == null ? null : pubExponent.trim();
    }

    public String getPriExponent() {
        return priExponent;
    }

    public void setPriExponent(String priExponent) {
        this.priExponent = priExponent == null ? null : priExponent.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 数据库存储的公钥私钥对是否过期(超过有效期)<br>
     * 有效期时间参考：{@link com.celloud.constants.Constants#COOKIE_MAX_AGE_DAY}
     * 
     * @return
     */
    public boolean isExpires() {
        boolean result = true;
        if (this.getCreateTime() == null) {
            return result;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 0 - Constants.COOKIE_MAX_AGE_DAY);
        return this.getCreateTime().before(calendar.getTime());
    }
}