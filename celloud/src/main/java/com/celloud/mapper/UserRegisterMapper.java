package com.celloud.mapper;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.UserRegister;

public interface UserRegisterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRegister record);

    int insertSelective(UserRegister record);

    UserRegister selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRegister record);

    int updateByPrimaryKeyWithBLOBs(UserRegister record);

    int updateByPrimaryKey(UserRegister record);

    public Integer insertUserRegisterInfo(@Param("email") String email, @Param("randomCode") String randomCode,
            @Param("truename") String truename, @Param("appIds") String appIds, @Param("roleIds") String roleIds,
            @Param("authFrom") Integer authFrom);

    /**
     * 删除验证码
     *
     * @param email
     * @author han
     * @date 2016年1月28日 下午3:06:19
     */
    public Integer deleteUserRegisterInfo(@Param("email") String email);

    UserRegister getUserRegisterInfoByEmail(@Param("email") String email);
}