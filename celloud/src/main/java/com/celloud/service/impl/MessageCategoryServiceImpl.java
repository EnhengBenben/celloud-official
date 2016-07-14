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
        // 1. 从关系表中查找设置过的开关
        List<Map<String, String>> userMessageCategory = messageCategoryMapper.findUserMessageCategoryByUserId(userId);
        // 2. 从分类表中查找没有更改过的开关
        StringBuilder sb = new StringBuilder();
        for(Map<String,String> map : userMessageCategory){
            sb.append(String.valueOf(map.get("mcId")) + ",");
        }
        String ids = "0";
        if (sb.length() > 0) {
            ids = sb.substring(0, sb.length() - 1);
        }
        List<Map<String, String>> userMessageCategoryNotInIds = messageCategoryMapper
                .findUserMessageCategoryNotInIds(ids);
        userMessageCategory.addAll(userMessageCategoryNotInIds);
        return userMessageCategory;
    }

    @Override
    public List<MessageCategory> getAllMessageCategory() {
        return messageCategoryMapper.findAll();
    }

    @Override
    public int initUserMessageCategory(Integer userId, String data) {
        return messageCategoryMapper.insertUserMessageCategoryRelat(userId, data);
    }

    @Override
    public int editUserMessageSwitch(Integer userId, String targetName, Integer targetVal,
            Integer relatId) {
        return messageCategoryMapper.updateSwitch(userId, targetName, targetVal, relatId);
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

	@Override
	public List<MessageCategory> getUserMessageCategory(Integer userId) {
		return messageCategoryMapper.getUserMessageCategory(userId);
	}

}
