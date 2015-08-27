package com.nova.service;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.nova.sdo.Screen;
import com.nova.service.impl.ScreenServiceImpl;

/**
 * 截图Service
 * 
 * @author <a href="mailto:linyongchao@novacloud.com">linyc</a>
 * @date 2013-1-25上午10:51:16
 * @version Revision: 1.0
 */
@ImplementedBy(ScreenServiceImpl.class)
public interface IScreenService {
    /**
     * 新增关系
     * 
     * @param screenName
     * @param softwareId
     * @return
     */
    int createScreen(String screenName, int softwareId);

    /**
     * 删除关系
     * 
     * @param softwareId
     * @return
     */
    int deleteScreen(int softwareId);

    /**
     * 根据软件id获取所有的截图
     * 
     * @param softId
     * @return
     */
    List<Screen> getAllScreen(int softId);
}
