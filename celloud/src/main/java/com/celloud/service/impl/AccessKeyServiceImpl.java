package com.celloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.celloud.mapper.AccessKeyMapper;
import com.celloud.model.mysql.AccessKey;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.AccessKeyService;
import com.celloud.utils.MD5Util;

@Service("accessKeyServiceImpl")
public class AccessKeyServiceImpl implements AccessKeyService {
    @Resource
    private AccessKeyMapper accessKeyMapper;

    @Override
    public int insertSelective(AccessKey record) {
        return accessKeyMapper.insert(record);
    }

    @Override
    public AccessKey selectByUserId(Integer userId) {
        return accessKeyMapper.selectByUserId(userId);
    }

    @Override
    public AccessKey selectByIdAndSecret(String keyId, String keySecret) {
        return accessKeyMapper.selectByIdAndSecret(keyId, keySecret);
    }

    @Override
    public int updateByPrimaryKeySelective(AccessKey record) {
        return accessKeyMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public PageList<AccessKey> listKeys(Integer userId, Page page) {
        List<AccessKey> datas = accessKeyMapper.selectKeysByPage(userId, page);
        return new PageList<AccessKey>(page, datas);
    }

    @Override
    public Boolean save(Integer userId, String username) {
        AccessKey accessKey = new AccessKey();
        accessKey.setUserId(userId);
        String keyId = MD5Util.getMD5ofStr(username + System.currentTimeMillis());
        accessKey.setKeyId(keyId);
        accessKey.setKeySecret(new ObjectId().toString());
        accessKey.setState(0);
        accessKey.setCreateDate(new DateTime().toDate());
        Integer num = accessKeyMapper.insertSelective(accessKey);
        return num.intValue() == 1;
    }

    @Override
    public Boolean remove(Integer id) {
        Integer num = accessKeyMapper.deleteByPrimaryKey(id);
        return num.intValue() == 1;
    }

    @Override
    public AccessKey get(Integer id) {
        return accessKeyMapper.selectByPrimaryKey(id);
    }
}