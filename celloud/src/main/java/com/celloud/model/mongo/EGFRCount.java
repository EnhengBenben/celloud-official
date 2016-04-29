package com.celloud.model.mongo;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class EGFRCount {
    @Id
    private ObjectId id;
    private Integer userId;
    private String dataKey;
    private Integer length;
    private Integer site;
    private Integer count;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getSite() {
        return site;
    }

    public void setSite(Integer site) {
        this.site = site;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "EGFRCount [id=" + id + ", userId=" + userId + ", dataKey=" + dataKey + ", length=" + length + ", site="
                + site + "]";
    }

}
