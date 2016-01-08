package com.celloud.service;

import java.util.List;

import com.celloud.model.Screen;

/**
 * 
 *
 * @author han
 * @date 2016年1月7日 上午11:35:47
 */
public interface ScreenService {
    /**
     * 获取软件截图
     *
     * @param id
     * @return
     * @author han
     * @date 2016年1月7日 上午11:35:55
     */
    public List<Screen> getScreenByAppId(Integer id);
}