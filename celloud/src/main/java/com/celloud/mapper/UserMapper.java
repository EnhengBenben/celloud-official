package com.celloud.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.User;

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

    public Integer isEmailInUse(@Param("email") String email, @Param("userId") int userId);

    public User getUserByName(@Param("username") String username);

    public List<Integer> getUserIdsByName(@Param("usernames") List<String> usernames);

    // TODO 返回值将来也许需要改成List<Integer>
    /**
     * 获取用户所属的大客户
     * 
     * @param userId
     * @return
     * @date 2016-1-9 下午10:37:35
     */
    public Integer getCompanyIdByUserId(@Param("userId") Integer userId);

    public User findByUsernameOrEmail(@Param("username") String username);

    public List<String> selectByIds(@Param("userIds") String userIds);

    public int insertUserWechatInfo(@Param("userId") Integer userId,
            @Param("openId") String openId, @Param("unionId") String unionId,
            @Param("createDate") Date createDate);

    public String getOpenIdByUser(@Param("userId") Integer userId);

    public User getUserByOpenId(@Param("openId") String openId);

	public int checkWechatBind(@Param("openId") String openId, @Param("userId") Integer userId);
}
