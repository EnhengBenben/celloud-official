package com.celloud.backstage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.backstage.model.MessageCategory;

public interface MessageCategoryMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(MessageCategory record);

    int insertSelective(MessageCategory record);

    MessageCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessageCategory record);

    int updateByPrimaryKey(MessageCategory record);

    /**
     * @author MQ
     * @date 2016年7月4日下午3:44:03
     * @description 查询所有分类(tb_message_category)
     *
     */
    List<MessageCategory> findAll();
    
    /**
     * @author MQ
     * @date 2016年7月4日下午5:28:41
     * @description 根据用户id从关系表中查找(tb_user_message_category_relat)
     *
     */
    List<Map<String, Object>> findUserMessageCategoryByUserId(@Param("userId") Integer userId);

    /**
     * 
     * @author MQ
     * @date 2016年7月14日下午5:02:44
     * @description 返回JavaBean
     * @param ids
     * @return
     *
     */
    List<MessageCategory> findUserMessageCategoryBeanByUserId(@Param("userId") Integer userId);


    /**
     * @author MQ
     * @date 2016年7月4日下午4:08:23
     * @description 插入用户消息设置(tb_user_message_category_relat)
     * @return
     *
     */
    int insertUserMessageCategoryRelat(@Param("userId") Integer userId, @Param("data") String data);


    /**
     * @author MQ
     * @date 2016年7月5日下午12:36:31
     * @description 更新用户的某个开关(tb_user_message_category_relat)
     * @return
     *
     */
    int updateSwitch(@Param("userId") Integer userId, @Param("targetSwitch") String targetSwitch,
            @Param("targetVal") Integer targetVal,
            @Param("relatId") Integer relatId);

    /**
     * @author MQ
     * @date 2016年7月5日下午2:19:58
     * @description 获取用户的某个开关(tb_user_message_category_relat)
     * @return
     *
     */
    Integer getUserSwitch(@Param("userId") Integer userId, @Param("targetSwitch") String targetSwitch,
            @Param("messageCategoryId") Integer messageCategoryId);

    /**
     * @author MQ
     * @date 2016年7月6日下午4:00:28
     * @description 获取默认的某个开关
     * @return
     *
     */
    Integer getDefaultSwitch(@Param("targetSwitch") String targetSwitch,
            @Param("messageCategoryId") Integer messageCategoryId);

    /**
     * @author MQ
     * @date 2016年7月14日上午11:44:55
     * @description 获取不在ids内的开关
     * @param ids
     * @return
     *
     */
    List<Map<String, Object>> findUserMessageCategoryNotInIds(@Param("ids") String ids);

    /**
     * 
     * @author MQ
     * @date 2016年7月14日下午5:04:08
     * @description 返回JavaBean
     * @param ids
     * @return
     *
     */
    List<MessageCategory> findUserMessageCategoryBeanNotInIds(@Param("ids") String ids);

}