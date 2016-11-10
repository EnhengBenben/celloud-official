package com.celloud.model;

import java.io.Serializable;
import java.util.Date;

public class LoginCaptcha implements Serializable {

    private static final long serialVersionUID = 1L;
    private String captcha;
    private Date expireDate;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}
