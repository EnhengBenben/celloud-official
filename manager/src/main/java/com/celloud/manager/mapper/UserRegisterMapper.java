package com.celloud.manager.mapper;

import org.apache.ibatis.annotations.Param;

import com.celloud.manager.model.UserRegister;

public interface UserRegisterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRegister record);

    int insertSelective(UserRegister record);

    UserRegister selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRegister record);

    int updateByPrimaryKeyWithBLOBs(UserRegister record);

    int updateByPrimaryKey(UserRegister record);

    public void insertUserRegisterInfo(@Param("email") String email, @Param("randomCode") String randomCode,
            @Param("appIds") String appIds);

    /**
     * 删除验证码
     *
     * @param email
     * @author han
     * @date 2016年1月28日 下午3:06:19
     */
    public void deleteUserRegisterInfo(@Param("email") String email);

    /**
     * 根据邮箱和验证码来判断请求是否有效（用于注册）
     *
     * @param email
     * @param md5
     * @return
     * @author han
     * @date 2016年1月28日 下午2:16:15
     */
    public int getValidate(@Param("email") String email, @Param("md5") String md5);

    public UserRegister getUserRegisterInfo(@Param("email") String email, @Param("md5") String md5);
}