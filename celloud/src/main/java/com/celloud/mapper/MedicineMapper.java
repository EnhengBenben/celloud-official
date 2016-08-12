package com.celloud.mapper;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.Medicine;

public interface MedicineMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Medicine record);

    int insertSelective(Medicine record);

    Medicine selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Medicine record);

    int updateByPrimaryKey(Medicine record);

    /**
     * 
     * @author MQ
     * @date 2016年8月12日上午11:26:13
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
    Medicine findByFeatureAndResult(@Param("feature") String feature, @Param("result") String result,
            @Param("appId") int appId);
}