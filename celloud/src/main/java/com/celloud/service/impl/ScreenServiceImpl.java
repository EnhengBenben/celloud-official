package com.celloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.ScreenMapper;
import com.celloud.model.mysql.Screen;
import com.celloud.service.ScreenService;

/**
 * 
 *
 * @author han
 * @date 2016年1月7日 下午1:22:31
 */
@Service("screenService")
public class ScreenServiceImpl implements ScreenService {
    @Resource
    private ScreenMapper screenMapper;
    @Override
    public List<Screen> getScreenByAppId(Integer id) {
        return screenMapper.getScreenByAppId(id);
    }
}