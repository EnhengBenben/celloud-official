package com.celloud.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.celloud.mapper.MedicineMapper;
import com.celloud.model.mysql.Medicine;
import com.celloud.service.MedicineService;

/** 
 * @author MQ: 
 * @date 2016年8月12日 上午11:29:05 
 * @description 
 */
@Controller
public class MedicineServiceImpl implements MedicineService {

    @Resource
    private MedicineMapper medicineMapper;

    @Override
    public Medicine getByFeatureAndResultDetail(String feature, String resultDetail, int appId) {
        return medicineMapper.findByFeatureAndResult(feature, resultDetail, appId);
    }

}
