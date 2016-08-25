package com.celloud.service.impl;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
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
    public List<Map<String, Object>> getMessageCategoryByUserId(Integer userId) {
        // 1. 从关系表中查找设置过的开关
        List<Map<String, Object>> userMessageCategory = messageCategoryMapper.findUserMessageCategoryByUserId(userId);
        // 2. 从分类表中查找没有更改过的开关
        StringBuilder sb = new StringBuilder();
        for (Map<String, Object> map : userMessageCategory) {
            sb.append(String.valueOf(map.get("mcId")) + ",");
        }
        String ids = "0";
        if (sb.length() > 0) {
            ids = sb.substring(0, sb.length() - 1);
        }
        List<Map<String, Object>> userMessageCategoryNotInIds = messageCategoryMapper
                .findUserMessageCategoryNotInIds(ids);
        userMessageCategory.addAll(userMessageCategoryNotInIds);
        Collections.sort(userMessageCategory, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                return Integer.parseInt(o1.get("mcId").toString()) - Integer.parseInt(o2.get("mcId").toString());
            }
        });
        return userMessageCategory;
    }

    @Override
    public List<MessageCategory> getAllMessageCategory() {
        return messageCategoryMapper.findAll();
    }

    @Override
    public int initUserMessageCategory(Integer userId, String targetName, Integer targetVal, Integer relatId) {
        try {
            // 从分类表中获取原始数据
            MessageCategory messageCategory = messageCategoryMapper.selectByPrimaryKey(relatId);
            Class<MessageCategory> clazz = MessageCategory.class;
            // 获取对应的set方法(setEmail,setWindow,setWechat)
            Method method = clazz.getDeclaredMethod("set" + targetName, Integer.class);
            method.setAccessible(true);
            // 更改用户更改的那一项值
            method.invoke(messageCategory, targetVal);
            // 将新的分类对象插入到用户消息分类关系表中
            return messageCategoryMapper.insertUserMessageCategoryRelat(userId, messageCategory);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
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
    public List<MessageCategory> getBeanByUserId(Integer userId) {
        // 1. 从关系表中查找设置过的开关
        List<MessageCategory> userMessageCategory = messageCategoryMapper
                .findUserMessageCategoryBeanByUserId(userId);
        // 2. 从分类表中查找没有更改过的开关
        StringBuilder sb = new StringBuilder();
        for (MessageCategory mc : userMessageCategory) {
            sb.append(mc.getId() + ",");
        }
        String ids = "0";
        if (sb.length() > 0) {
            ids = sb.substring(0, sb.length() - 1);
        }
        List<MessageCategory> userMessageCategoryNotInIds = messageCategoryMapper
                .findUserMessageCategoryBeanNotInIds(ids);
        userMessageCategory.addAll(userMessageCategoryNotInIds);
        return userMessageCategory;
    }

}
