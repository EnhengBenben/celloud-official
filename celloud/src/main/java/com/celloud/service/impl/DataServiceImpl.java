package com.celloud.service.impl;

import java.util.List;
import java.util.Map;

import com.celloud.dao.DataDao;
import com.celloud.sdo.Data;
import com.celloud.service.DataService;
import com.google.inject.Inject;
import com.nova.pager.Page;
import com.nova.pager.PageList;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-14下午1:39:40
 * @version Revision: 1.0
 */
public class DataServiceImpl implements DataService {
    @Inject
    DataDao dataDao;

    @Override
    public PageList<Data> getAllData(Page page, Integer userId) {
	return dataDao.getAllData(page, userId);
    }

    @Override
    public PageList<Data> getDataByCondition(Page page, Integer userId,
	    Integer sortType,
	    String sortByName, String sortByDate, String condition) {
	return dataDao.getDataByCondition(page, userId, sortType, sortByName,
		sortByDate,
		condition);
    }

    @Override
    public Map<String, Integer> getFormatNumByIds(String dataIds) {
	return dataDao.getFormatNumByIds(dataIds);
    }

    @Override
    public List<Integer> getRunningDataBySoft(String dataIds, Integer appId) {
	return dataDao.getRunningDataBySoft(dataIds, appId);
    }

    @Override
    public Integer deleteDataByIds(String dataIds) {
	return dataDao.deleteDataByIds(dataIds);
    }

    @Override
    public List<Map<String, String>> getStrainList(Integer userId) {
	return dataDao.getStrainList(userId);
    }

    @Override
    public Data getDataAndStrain(Integer userId, Integer fileId) {
	return dataDao.getDataAndStrain(userId, fileId);
    }

    @Override
    public List<Data> getDatasByIds(String dataIds) {
	return dataDao.getDatasByIds(dataIds);
    }

    @Override
    public Integer updateData(String dataIds, Data data) {
	return dataDao.updateData(dataIds, data);
    }

    @Override
    public Integer updateDatas(List<Data> list) {
	return dataDao.updateDatas(list);
    }

}
