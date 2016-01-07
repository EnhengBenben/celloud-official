package com.celloud.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.User;

public interface UserMapper {
    public int deleteByPrimaryKey(Integer userId);

    public int insert(User record);

    public int insertSelective(User record);

    public User selectByPrimaryKey(Integer userId);

    public int updateByPrimaryKeySelective(User record);

    public int updateByPrimaryKey(User record);

    public User checkLogin(User user);

    public User getUserByEmail(@Param("email") String email);

    public void insertFindPwdInfo(@Param("userId") Integer userId, @Param("expireDate") Date expireDate,
            @Param("randomCode") String randomCode);

    public void cleanFindPwdByUserId(@Param("userId") Integer userId, @Param("expireDate") Date expireDate);

    public void cleanFindPwdByUsername(@Param("username") String username, @Param("expireDate") Date expireDate);

    public User getUserByFindPwd(@Param("username") String username, @Param("randomCode") String randomCode);
    
    public Integer isEmailInUse(@Param("email")String email,@Param("userId") int userId);

}