package com.celloud.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.stereotype.Service;

import com.celloud.model.mongo.TBINH;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

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
    
    @SuppressWarnings("rawtypes")
    @Override
    public <T> Iterable getHCVCompare(Class<T> clazz) {
        BasicDBObject group = new BasicDBObject();
        group.put("_id", new BasicDBObject("subtype","$subtype"));
        group.put("count", new BasicDBObject("$sum", "$count"));

        List<DBObject> aggParam = new ArrayList<DBObject>();
        aggParam.add(new BasicDBObject("$group",group));
        
        DBCollection collection = dataStore.getCollection(clazz);
        AggregationOutput output = collection.aggregate(aggParam);
        return output.results();
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public <T> Iterable getTBRifampicinCompare(Class<T> clazz) {
        BasicDBObject group = new BasicDBObject();
        group.put("_id", new BasicDBObject("site","$site"));
        group.put("count", new BasicDBObject("$sum", "$count"));

        List<DBObject> aggParam = new ArrayList<DBObject>();
        aggParam.add(new BasicDBObject("$group",group));
        aggParam.add(new BasicDBObject("$sort", new BasicDBObject("count",-1)));
        aggParam.add(new BasicDBObject("$limit", 10));
        
        DBCollection collection = dataStore.getCollection(clazz);
        AggregationOutput output = collection.aggregate(aggParam);
        return output.results();
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public <T> Iterable getEGFROrKRASCompare(Class<T> clazz, Integer length) {
        BasicDBObject group = new BasicDBObject();
        group.put("_id", new BasicDBObject("site","$site"));
        group.put("count", new BasicDBObject("$sum", "$count"));

        List<DBObject> aggParam = new ArrayList<DBObject>();
        aggParam.add(new BasicDBObject("$match", new BasicDBObject("length",length)));
        aggParam.add(new BasicDBObject("$group",group));
        aggParam.add(new BasicDBObject("$sort", new BasicDBObject("count",-1)));
        aggParam.add(new BasicDBObject("$limit", 10));
        
        DBCollection collection = dataStore.getCollection(clazz);
        AggregationOutput output = collection.aggregate(aggParam);
        return output.results();
    }
    
    @Override
    public Integer getTBINHisWild(Integer userId, String simpleGeneName, Integer isWild) {
        return dataStore.createQuery(TBINH.class).filter("userId =", userId).filter("simpleGeneName =", simpleGeneName)
                .filter("isWild =", isWild).asList().size();
    }

    @Override
    public <T> T getDataReport(Class<T> T, String dataKey, Integer projectId, Integer appId) {
        return dataStore.createQuery(T).filter("dataKey", dataKey).filter("projectId", projectId).filter("appId", appId)
                .get();
    }

    @Override
    public <T> List<T> getAppList(Class<T> T, Integer userId) {
        return dataStore.createQuery(T).filter("userId", userId).order("-createDate").asList();
    }

    @Override
    public <T> T getDataById(Class<T> T, ObjectId id) {
        return dataStore.createQuery(T).filter("_id", id).get();
    }

    @Override
    public <T> List<T> getAllAppList(Class<T> T, String[] retrievedFields) {
        return dataStore.createQuery(T).retrievedFields(true, retrievedFields).asList();
    }

    @Override
    public <T> T getDataFileds(Class<T> T, String dataKey, Integer projectId, Integer appId, String[] retrievedFields) {
        return dataStore.createQuery(T).retrievedFields(true, retrievedFields).filter("dataKey", dataKey)
                .filter("projectId", projectId).filter("appId", appId).get();
    }

    @Override
    public <T> List<T> getDataFieldInAndOrder(Class<T> T, String[] fields, Map<String, List<String>> conditionMap,
            String sortField) {
        Query<T> q = dataStore.createQuery(T);
        for (String s : fields) {
            q.filter(s + " nin", conditionMap.get(s));
        }
        return q.order(sortField).asList();
    }

    @Override
    public <T> UpdateResults editData(Class<T> T, ObjectId id, String field,
            Object obj) {
        return dataStore.update(dataStore.createQuery(T).filter("_id", id),
                dataStore.createUpdateOperations(T).set(field, obj));
    }

    @Override
    public <T> void saveData(T T) {
        dataStore.save(T);
    }

    @Override
    public <T> List<T> getDataListAndOrder(Class<T> T, Map<String, Object> conditionMap, String sortField) {
        Query<T> q = dataStore.createQuery(T);
        if (conditionMap != null) {
            for (Map.Entry<String, Object> entry : conditionMap.entrySet()) {
                q.filter(entry.getKey(), entry.getValue());
            }
        }
        return q.order(sortField).asList();
    }

    @Override
    public <T> PageList<T> getDataPageListAndOrder(Class<T> T, Map<String, Object> conditionMap, String sortField,
            Page page) {
        PageList<T> pageList = new PageList<T>();
        Query<T> q = dataStore.find(T);
        if (conditionMap != null) {
            for (Map.Entry<String, Object> entry : conditionMap.entrySet()) {
                q.filter(entry.getKey(), entry.getValue());
            }
        }
        List<T> list_all = q.asList();
        page.make(list_all.size());
        List<T> list = q.order(sortField).offset((page.getCurrentPage() - 1) * page.getPageSize())
                .limit(page.getPageSize()).asList();
        pageList.setDatas(list);
        pageList.setPage(page);
        return pageList;
    }

    @Override
    public <T> List<T> getDataFieldsByAppId(Class<T> clazz, Integer appId, String[] columns) {
        return dataStore.createQuery(clazz).filter("appId =", appId).retrievedFields(true, columns).asList();
    }

    @Override
    public <T> List<T> getAllByClass(Class<T> clazz) {
        return dataStore.createQuery(clazz).asList();
    }

    @Override
    public <T> void delete(Class<T> T, String dataKey, Integer projectId,
            Integer appId) {
        dataStore.delete(dataStore.createQuery(T).filter("dataKey", dataKey)
                .filter("projectId", projectId).filter("appId", appId));
    }

}
