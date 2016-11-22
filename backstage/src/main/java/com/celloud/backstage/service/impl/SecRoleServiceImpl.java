package com.celloud.backstage.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.backstage.constants.ConstantsData;
import com.celloud.backstage.constants.DataState;
import com.celloud.backstage.mapper.SecRoleMapper;
import com.celloud.backstage.model.SecResource;
import com.celloud.backstage.model.SecRole;
import com.celloud.backstage.model.User;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.SecRoleService;
import com.celloud.backstage.utils.SortUtils;

/** 
 * @author MQ: 
 * @date 2016年7月19日 下午2:56:42 
 * @description 
 */
@Service("roleService")
public class SecRoleServiceImpl implements SecRoleService {

    @Resource
    private SecRoleMapper roleMapper;

    @Override
    public int addRole(SecRole role) {
        role.setCreateDate(new Date());
        return roleMapper.insert(role);
    }

    @Override
    public int editRole(SecRole role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public PageList<SecRole> pageQuery(Page page) {
        List<SecRole> datas = roleMapper.pageQuery(page);
        return new PageList<>(page, datas);
    }

    @Override
    public SecRole findById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int checkNameRepeat(String name, Integer id) {
        return roleMapper.findByName(name, id).size();
    }

    @Override
    public int checkCodeRepeat(String code, Integer id) {
        return roleMapper.findByCode(code, id).size();
    }

    @Override
    public List<User> getBigCustomersByRole(Integer roleId) {
        return roleMapper.findBigCustomersByRole(DataState.ACTIVE, roleId);
    }

    @Override
    public int distribute(Integer roleId, Integer[] bigCustomerIds) {
        if (bigCustomerIds == null) {
            // 首先删除该角色下的所有大客户关系
            return roleMapper.deleteRoleBigCustomerRelatByRoleId(roleId);
        } else {
            roleMapper.deleteRoleBigCustomerRelatByRoleId(roleId);
            // 为该角色添加大客户
			Integer userId = ConstantsData.getLoginUserId();
			return roleMapper.insertRoleBigCustomerRelat(roleId, bigCustomerIds, userId);
        }
    }

    @Override
    public List<SecResource> getResourcesByRole(Integer roleId) {
        return roleMapper.findResourcesByRole(roleId);
    }

    @Override
    public int grant(Integer roleId, Integer[] resourceIds) {
        if (resourceIds == null) {
            return roleMapper.deleteRoleResourceRelatByRoleId(roleId);
        } else {
            roleMapper.deleteRoleResourceRelatByRoleId(roleId);
            return roleMapper.insertRoleResourceRelat(roleId, resourceIds);
        }
    }

	@Override
	public List<SecRole> selectAll() {
		List<SecRole> list = roleMapper.selectAll();
		return SortUtils.listToTree(list);
	}

}
