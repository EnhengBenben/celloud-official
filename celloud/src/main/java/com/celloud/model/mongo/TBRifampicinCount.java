package com.celloud.model.mongo;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class TBRifampicinCount {
    @Id
    private ObjectId id;
    private Integer userId;
    private String dataKey;
    private Integer site;

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

}
