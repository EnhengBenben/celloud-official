package com.celloud.service;

import java.util.List;

import com.celloud.model.mysql.Classify;

/**
 * 
 *
 * @author han
 * @date 2016年1月6日 上午10:20:13
 */
public interface ClassifyService {
    /**
     * 
     * @description 根据pid查询分类列表
     * @author miaoqi
     * @date 2017年3月23日 下午6:31:09
     * @param pid
     * @return
     */
    public List<Classify> listClassifyByPid(Integer pid);

    /**
     * 
     * @description 根据appId查询app所属分类
     * @author miaoqi
     * @date 2017年3月24日 下午3:57:37
     * @param appId
     * @return
     */
    public List<Classify> listClassifyByAppId(Integer appId);

}
