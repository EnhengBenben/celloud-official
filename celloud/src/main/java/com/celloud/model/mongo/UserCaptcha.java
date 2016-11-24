package com.celloud.model.mongo;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class UserCaptcha implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private ObjectId id;
    private String captcha;
    private String cellphone;
    private Date createdAt;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "LoginCaptcha [id=" + id + ", captcha=" + captcha + ", cellphone=" + cellphone + ", createdAt="
                + createdAt + "]";
    }

}
