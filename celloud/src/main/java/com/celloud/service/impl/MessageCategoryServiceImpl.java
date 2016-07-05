package com.celloud.service.impl;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
    public int editUserMessageCategory(String targetName, Integer targetVal,
            Integer relatId) {
        try {
            String methodName = "update" + targetName.substring(0, 1).toUpperCase() + targetName.substring(1);
            Method targetMethod = messageCategoryMapper.getClass().getMethod(methodName, Integer.class,
                    Integer.class);
            return (int) targetMethod.invoke(messageCategoryMapper, targetVal, relatId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
