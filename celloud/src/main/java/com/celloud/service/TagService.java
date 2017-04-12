package com.celloud.service;

import java.util.List;
import java.util.Map;

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

    /**
     * 
     * @description 查询产品标签返回给select2
     * @author miaoqi
     * @date 2017年2月13日 下午2:48:19
     * @param userId
     * @return
     */
    List<Map<String, String>> listProductTagsToSelect(Integer userId);

    /**
     * 
     * @description 根据tagId获取tag对象t
     * @author miaoqi
     * @date 2017年4月11日 下午6:23:17
     * @param tagId
     * @return
     */
    public Tag get(Integer tagId);

}
