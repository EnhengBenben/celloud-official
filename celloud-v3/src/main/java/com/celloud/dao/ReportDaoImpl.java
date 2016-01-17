package com.celloud.dao;

import java.util.List;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.springframework.stereotype.Service;

/**
 * mongodb 操作实现
 * 
 * @author lin
 * @date 2016-1-8 下午4:18:53
 */
@Service("reportDaoImpl")
public class ReportDaoImpl implements ReportDao {
    @Resource
    private Datastore dataStore;

    @Override
    public <T> T getDataReport(Class<T> T, String dataKey, Integer projectId,
            Integer appId) {
        return dataStore.createQuery(T).filter("dataKey", dataKey)
                .filter("projectId", projectId).filter("appId", appId).get();
    }

    @Override
    public <T> List<T> getAppList(Class<T> T, Integer userId) {
        return dataStore.createQuery(T).filter("userId", userId)
                .order("-createDate").asList();
    }

    @Override
    public <T> T getDataById(Class<T> T, ObjectId id) {
        return dataStore.createQuery(T).filter("_id", id).get();
    }

    @Override
    public <T> List<T> getAllAppList(Class<T> T, String[] retrievedFields) {
        return dataStore.createQuery(T).retrievedFields(true, retrievedFields)
                .asList();
    }

}
