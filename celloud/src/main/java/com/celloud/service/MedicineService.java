package com.celloud.service;

import com.celloud.model.mysql.Medicine;

/** 
 * @author MQ: 
 * @date 2016年8月12日 上午11:25:16 
 * @description 
 */
public interface MedicineService {

    /**
     * 
     * @author MQ
     * @date 2016年8月12日上午11:28:18
     * @description 根据特征,结果获取Medicine对象
     * @param feature
     *            app特征
     * @param result
     *            检测结果
     * @param appId
     *            appId
     * @return medicine对象
     *
     */
    public Medicine getByFeatureAndResult(String feature, String result, int appId);

}
