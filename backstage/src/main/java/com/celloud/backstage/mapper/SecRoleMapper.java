package com.celloud.backstage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.backstage.model.SecResource;
import com.celloud.backstage.model.SecRole;
import com.celloud.backstage.model.User;
import com.celloud.backstage.page.Page;

public interface SecRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SecRole role);

    int insertSelective(SecRole role);

    SecRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SecRole role);

    int updateByPrimaryKey(SecRole role);

    List<SecRole> pageQuery(Page page);

	List<SecRole> selectAll();

    List<SecRole> findByName(@Param("name") String name, @Param("id") Integer id);

    List<SecRole> findByCode(@Param("code") String code, @Param("id") Integer id);

    List<User> findBigCustomersByRole(@Param("state") Integer state, @Param("roleId") Integer roleId);

    int deleteRoleBigCustomerRelatByRoleId(@Param("roleId") Integer roleId);

    int insertRoleBigCustomerRelat(@Param("roleId") Integer roleId, @Param("bigCustomerIds") Integer[] bigCustomerIds);

    List<SecResource> findResourcesByRole(@Param("roleId") Integer roleId);

    int deleteRoleResourceRelatByRoleId(@Param("roleId") Integer roleId);

    int insertRoleResourceRelat(@Param("roleId") Integer roleId, @Param("resourceIds") Integer[] resourceIds);
}