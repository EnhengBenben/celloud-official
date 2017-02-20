package com.celloud.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.User;
import com.celloud.page.Page;

public interface UserMapper {
    public int deleteByPrimaryKey(Integer userId);

    public int insert(User record);

    public int insertSelective(User record);

    public User selectByPrimaryKey(Integer userId);

	public User selectUserByIdNotIcon(Integer userId);

    public int updateByPrimaryKeySelective(User record);

    public int customUpdateByPrimaryKeySelective(User record);

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

	public List<User> selectUserByIds(@Param("userIds") String userIds);

    public int insertUserWechatInfo(@Param("userId") Integer userId,
            @Param("openId") String openId, @Param("unionId") String unionId,
            @Param("createDate") Date createDate);

    public String getOpenIdByUser(@Param("userId") Integer userId);

    public User getUserByOpenId(@Param("openId") String openId);

	public int checkWechatBind(@Param("openId") String openId, @Param("userId") Integer userId);

	/**
	 * 微信解除绑定时的校验
	 * 
	 * @param openId
	 * @param pwd
	 * @return
	 * @author lin
	 * @date 2016年7月11日下午1:25:08
	 */
	public int checkWechatUnBind(@Param("openId") String openId, @Param("pwd") String pwd);

	/**
	 * 微信解除绑定
	 * 
	 * @param openId
	 * @param pwd
	 * @return
	 * @author lin
	 * @date 2016年7月11日下午1:35:58
	 */
	public int wechatUnBind(@Param("openId") String openId, @Param("pwd") String pwd);

    /**
     * 
     * @description 根据条件查询用户
     * @author miaoqi
     * @date 2016年10月27日上午11:12:50
     *
     * @param user
     * @return
     */
    public List<User> select(@Param("user") User user, @Param("testAccountIds") String testAccountIds, Page page);

    /**
     * 
     * @description 根据所属公司查找用户
     * @author miaoqi
     * @date 2016年10月27日下午5:20:27
     *
     * @param companyId
     * @param testAccountIds
     * @param page
     * @return
     */
    public List<User> findUsersByCompanyId(@Param("loginUserId") Integer loginUserId,
            @Param("companyId") Integer companyId,
            @Param("testAccountIds") String testAccountIds, Page page);

    /**
     * 
     * @description 根据用户id查找roleIds
     * @author miaoqi
     * @date 2016年10月28日下午2:44:26
     *
     * @param userId
     * @return
     */
    public List<Integer> findRoleIdsByUserId(@Param("userId") Integer loginUserId);

    public User findUserByCellphoneAndRole(@Param("cellphone") String cellphone,
            @Param("role") Integer role, @Param("state") Integer state);

}
