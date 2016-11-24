package com.celloud.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.celloud.dao.ReportDao;
import com.celloud.model.mongo.UserCaptcha;
import com.celloud.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private ReportDao reportDao;

    @Override
    public Boolean addOrUpdateUserCaptcha(String cellphone, String captcha) {
        // 1. 根据cellphone查询mongo中是否包含该手机
        Map<String, Object> queryFilters = new HashMap<>();
        queryFilters.put("cellphone", cellphone);
        UserCaptcha userCaptcha = reportDao.queryByFilters(UserCaptcha.class, queryFilters, null).get(0);
        if (null != userCaptcha) { // 2. 有就更新
            Map<String, Object> updateFilters = new HashMap<String, Object>();
            updateFilters.put("captcha", captcha);
            reportDao.update(UserCaptcha.class, queryFilters, updateFilters);
            return true;
        } else { // 3. 没有就插入
            userCaptcha = new UserCaptcha();
            userCaptcha.setCaptcha(captcha);
            // 加上8小时
            userCaptcha.setCreatedAt(new DateTime().plusHours(8).toDate());
            userCaptcha.setCellphone(cellphone);
            Key<UserCaptcha> key = reportDao.saveObj(userCaptcha);
            return null != key;
        }
    }

}
