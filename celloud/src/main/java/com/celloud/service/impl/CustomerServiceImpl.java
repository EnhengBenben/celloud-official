package com.celloud.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.celloud.alidayu.AliDayuUtils;
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
        List<UserCaptcha> list = reportDao.queryByFilters(UserCaptcha.class, queryFilters, null);
        UserCaptcha userCaptcha = null;
        if (list != null && list.size() > 0) {
            userCaptcha = list.get(0);
        }
        if (userCaptcha != null) { // 2. 有就更新
            Map<String, Object> updateFilters = new HashMap<String, Object>();
            updateFilters.put("captcha", captcha);
            updateFilters.put("createDate", new Date());
            reportDao.update(UserCaptcha.class, queryFilters, updateFilters).intValue();
        } else { // 3. 没有就插入
            userCaptcha = new UserCaptcha();
            userCaptcha.setCaptcha(captcha);
            // 加上8小时
            userCaptcha.setCreateDate(new Date());
            userCaptcha.setCellphone(cellphone);
            reportDao.saveObj(userCaptcha);
        }
        String result = AliDayuUtils.sendCaptcha(cellphone, captcha);
        return !StringUtils.equals(result, "error");
    }

    @Override
    public UserCaptcha getUserCaptchaByCellphone(String cellphone) {
        Map<String, Object> queryFilters = new HashMap<String, Object>();
        queryFilters.put("cellphone", cellphone);
        List<UserCaptcha> list = reportDao.queryByFilters(UserCaptcha.class, queryFilters, null);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void removeUserCaptchaByCellphone(String cellphone) {
        Map<String, Object> queryFilters = new HashMap<String, Object>();
        queryFilters.put("cellphone", cellphone);
        reportDao.deleteByFilters(UserCaptcha.class, queryFilters);
    }

}
