package com.celloud.manager.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Service;

import com.celloud.manager.dao.ReportDao;

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
    public <T> List<T> queryByFilters(Class<T> clazz, Map<String, Object> filters, String[] fields) {
        Query<T> query = dataStore.createQuery(clazz);
        for (String key : filters.keySet()) {
            query.filter(key, filters.get(key));
        }
        if (null != fields) {
            query.retrievedFields(true, fields);
        }
        return query.order("-createDate").asList();
    }

}
