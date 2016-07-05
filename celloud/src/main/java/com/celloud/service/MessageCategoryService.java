package com.celloud.service;

import java.util.List;
import java.util.Map;

import com.celloud.constants.MessageCategoryEnum;
import com.celloud.constants.MessageWay;
import com.celloud.model.mysql.MessageCategory;


/**
 * @author MQ:
 * @date 2016年7月4日 下午3:34:59
 * @description 消息设置
 */
public interface MessageCategoryService {

    /**
     * @author MQ
     * @date 2016年7月4日下午3:35:39
     * @description 根据用户id查询自定义消息设置
     *
     */
    List<Map<String, String>> getMessageCategoryByUserId(Integer userId);

    /**
     * @author MQ
     * @date 2016年7月4日下午5:44:24
     * @description 获取全部消息分类
     *
     */
    List<MessageCategory> getAllMessageCategory();

    /**
     * @author MQ
     * @date 2016年7月5日上午11:22:47
     * @description 初始化用户消息设置
     *
     */
    int initUserMessageCategory(Integer userId, String datas);

    /**
     * @author MQ
     * @date 2016年7月5日上午11:22:47
     * @description 更改用户消息开关
     *
     */
    int editUserMessageSwitch(String targetName, Integer targetVal, Integer relatId);

    /**
     * @author MQ
     * @date 2016年7月5日下午2:45:55
     * @description 获取用户消息开关
     * @return
     *
     */
    int getUserMessageSwitch(Integer userId, MessageCategoryEnum messageCategoryEnum, MessageWay messageWay);
}
