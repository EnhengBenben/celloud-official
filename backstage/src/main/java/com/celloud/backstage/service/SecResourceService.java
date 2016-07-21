package com.celloud.backstage.service;

import java.util.List;

import com.celloud.backstage.model.SecResource;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;

/**
 * @author MQ:
 * @date 2016年7月15日 下午1:39:26
 * @description 资源服务接口
 */
public interface SecResourceService {

    /**
     * 
     * @author MQ
     * @date 2016年7月18日下午3:04:15
     * @description 添加资源
     *
     */
    public int addResource(SecResource resource);

    /**
     * 
     * @author MQ
     * @date 2016年7月18日下午3:04:23
     * @description 编辑资源
     *
     */
    public int editResource(SecResource resource);

    /**
     * 
     * @author MQ
     * @date 2016年7月18日下午3:04:31
     * @description 分页查询
     *
     */
    public PageList<SecResource> pageQuery(Page page, String keyword);

    /**
     * @author MQ
     * @date 2016年7月18日下午3:04:39
     * @description 查询全部资源
     *
     */
    public List<SecResource> findAllActive();

    /**
     * 
     * @author MQ
     * @date 2016年7月18日下午3:04:47
     * @description 校验名称是否重复
     *
     */
    public int checkNameRepeat(String name, Integer id);

    /**
     * 
     * @author MQ
     * @date 2016年7月18日下午3:05:03
     * @description 校验表达式是否重复
     *
     */
    public int checkPermissionRepeat(String permission, Integer id);

    /**
     * 
     * @author MQ
     * @date 2016年7月18日下午3:30:41
     * @description 校验优先级是否重复
     *
     */
    public int checkPriorityRepeat(Integer priority, Integer id);

    /**
     * 
     * @author MQ
     * @date 2016年7月18日下午3:44:06
     * @description 根据id查询资源
     *
     */
    public SecResource findById(Integer id);

    /**
     * 
     * @author MQ
     * @date 2016年7月19日下午1:30:27
     * @description 上移功能
     *
     */
    public int moveUp(Integer id, Integer parentId, Integer priority);

    /**
     * 
     * @author MQ
     * @date 2016年7月19日下午2:03:20
     * @description 下移功能
     *
     */
    public int moveDown(Integer id, Integer parentId, Integer priority);
}
