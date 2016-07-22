package com.celloud.backstage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.backstage.model.SecResource;
import com.celloud.backstage.page.Page;

public interface SecResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SecResource resource);

    int insertSelective(SecResource resource);

    SecResource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SecResource resource);

    int updateByPrimaryKey(SecResource resource);
    
    /**
     * 
     * @author MQ
     * @date 2016年7月18日下午3:05:54
     * @description 分页查询
     *
     */
    List<SecResource> pageQuery(Page page, @Param("keyword") String keyword);

    /**
     * 
     * @author MQ
     * @date 2016年7月18日下午3:05:46
     * @description 查询全部资源
     *
     */
    List<SecResource> findAllActive(@Param("state") Integer state);

    /**
     * 
     * @author MQ
     * @date 2016年7月18日下午3:05:26
     * @description 根据名称查询资源
     *
     */
    List<SecResource> findByName(@Param("name") String name, @Param("id") Integer id);

    /**
     * 
     * @author MQ
     * @date 2016年7月18日下午3:05:36
     * @description 根据表达式查询资源
     *
     */
    List<SecResource> findByPermission(@Param("permission") String permission, @Param("id") Integer id);

    /**
     * 
     * @author MQ
     * @date 2016年7月18日下午3:30:04
     * @description 根据优先级查询资源
     *
     */
    List<SecResource> findByPriority(@Param("priority") Integer priority, @Param("id") Integer id,
            @Param("parentId") Integer parentId);

    /**
     * 
     * @author MQ
     * @date 2016年7月19日下午1:26:40
     * @description 获取同一父类下的上一条记录
     *
     */
    SecResource getPrePriority(@Param("parentId") Integer parentId,
            @Param("priority") Integer priority);

    /**
     * 
     * @author MQ
     * @date 2016年7月19日下午1:26:40
     * @description 获取同一父类下的下一条记录
     *
     */
    SecResource getNextPriority(@Param("parentId") Integer parentId,
            @Param("priority") Integer priority);
}