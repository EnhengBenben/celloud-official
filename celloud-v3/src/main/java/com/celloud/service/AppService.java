package com.celloud.service;

import java.util.List;
import java.util.Map;

/**
 * app接口
 *
 * @author han
 * @date 2015年12月25日 下午5:43:03
 */
public interface AppService {
    /**
     * (重构)获取已添加的APP数量
     * 
     * @param userId
     * @return
     * @date 2015年12月25日 下午5:44:58
     */
    Integer countMyApp(Integer userId);
    /**
     * (重构)按时间段获取已添加的APP数量
     * 
     * @param userId
     * @return
     * @date 2015年12月25日 下午5:44:58
     */
    List<Map<String, String>> countMyApp(Integer userId,Integer time);

}
