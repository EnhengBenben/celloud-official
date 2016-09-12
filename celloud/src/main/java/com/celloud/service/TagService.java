package com.celloud.service;

import java.util.List;

import com.celloud.model.mysql.Tag;

public interface TagService {

    List<Tag> findTags(Integer userId);

    /**
     * 
     * @author MQ
     * @date 2016年9月1日上午11:20:50
     * @description 根据用户id查找产品标签
     * @param userId
     *            用户id
     * @return
     *
     */
    List<Tag> findProductTags(Integer userId);
}
