package com.celloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.MessageCategoryEnum;
import com.celloud.constants.MessageWay;
import com.celloud.mapper.MessageCategoryMapper;
import com.celloud.model.mysql.MessageCategory;
import com.celloud.service.MessageCategoryService;

/** 
 * @author MQ: 
 * @date 2016年7月4日 下午5:47:07 
 * @description 
 */
@Service
public class MessageCategoryServiceImpl implements MessageCategoryService {

    @Resource
    private MessageCategoryMapper messageCategoryMapper;

    @Override
    public List<Map<String, String>> getMessageCategoryByUserId(Integer userId) {
        return messageCategoryMapper.findUserMessageCategoryByUserId(userId);
    }

    @Override
    public List<MessageCategory> getAllMessageCategory() {
        return messageCategoryMapper.findAll();
    }

    @Override
    public int initUserMessageCategory(Integer userId, String datas) {
        return messageCategoryMapper.insertUserMessageCategoryRelat(userId, datas.split(";"));
    }

    @Override
    public int editUserMessageSwitch(String targetName, Integer targetVal,
            Integer relatId) {
        return messageCategoryMapper.updateSwitch(targetName, targetVal, relatId);
    }

    @Override
    public Integer getUserMessageSwitch(Integer userId, MessageCategoryEnum messageCategoryEnum,
            MessageWay messageWay) {
        Integer setting = messageCategoryMapper.getUserSwitch(userId, messageWay.name(), messageCategoryEnum.getId());
        if (setting == null) {
            setting = messageCategoryMapper.getDefaultSwitch(messageWay.name(), messageCategoryEnum.getId());
        }
        return setting;
    }

}
