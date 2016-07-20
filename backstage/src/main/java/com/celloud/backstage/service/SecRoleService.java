package com.celloud.backstage.service;

import java.util.List;

import com.celloud.backstage.model.SecRole;
import com.celloud.backstage.model.User;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;

/**
 * @author MQ:
 * @date 2016年7月19日 下午2:54:58
 * @description 角色服务接口
 */
public interface SecRoleService {
    /**
     * 
     * @author MQ
     * @date 2016年7月19日下午2:56:51
     * @description 添加角色
     *
     */
    public int addRole(SecRole role);

    /**
     * 
     * @author MQ
     * @date 2016年7月19日下午2:56:59
     * @description 编辑角色
     *
     */
    public int editRole(SecRole role);

    /**
     * 
     * @author MQ
     * @date 2016年7月19日下午2:57:07
     * @description 分页查询
     *
     */
    public PageList<SecRole> pageQuery(Page page);

    /**
     * 
     * @author MQ
     * @date 2016年7月19日下午2:57:15
     * @description 根绝id查询角色
     *
     */
    public SecRole findById(Integer id);

    /**
     * 
     * @author MQ
     * @date 2016年7月19日下午6:51:46
     * @description 检查name是否重复
     *
     */
    public int checkNameRepeat(String name, Integer id);

    /**
     * 
     * @author MQ
     * @date 2016年7月19日下午6:53:18
     * @description 检查code是否重复
     *
     */
    public int checkCodeRepeat(String code, Integer id);

    /**
     * 
     * @author MQ
     * @date 2016年7月20日下午1:48:16
     * @description 获取角色下的大客户
     *
     */
    public List<User> getBigCustomersByRole(Integer roleId);

    /**
     * 
     * @author MQ
     * @date 2016年7月20日下午2:23:17
     * @description 为大客户分配角色
     *
     */
    public int distribute(Integer roleId, Integer[] bigCustomerIds);
}
