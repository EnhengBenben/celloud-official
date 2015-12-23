package com.celloud.model;

public class RSAKey {
    private Integer id;

    private String modulus;

    private String pubExponent;

    private String priExponent;

    private Integer userId;

    private Integer state;

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
}