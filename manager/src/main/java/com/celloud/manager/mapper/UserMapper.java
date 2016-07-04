package com.celloud.manager.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.manager.model.User;
import com.celloud.manager.model.UserSelect;
import com.celloud.manager.page.Page;

public interface UserMapper {
    public User checkLogin(User user);

    /**
     * 统计大客户下的用户数量
     *
     * @param companyId
     * @param state
     * @return
     * @author han
     * @date 2016年3月10日 上午11:27:10
     */
    public int countUser(@Param("companyId") Integer companyId, @Param("state") Integer state,
            @Param("testAccountIds") String testAccountIds);

    /**
     * 统计大客户下的医院数量
     *
     * @param companyId
     * @param state
     * @return
     * @author han
     * @date 2016年3月10日 下午1:36:17
     */
    public int countCompany(@Param("companyId") Integer companyId, @Param("state") Integer state,
            @Param("testAccountIds") String testAccountIds);

    /**
     * 医院地理分布统计
     *
     * @param companyId
     * @param state
     * @return
     * @author han
     * @date 2016年3月11日 下午1:38:05
     */
    public List<Map<String, Integer>> countCompanyByProvince(@Param("companyId") Integer companyId,
            @Param("state") Integer state, @Param("testAccountIds") String testAccountIds);

    /**
     * 用户登录统计
     *
     * @param companyId
     * @param state
     * @return
     * @author han
     * @date 2016年3月11日 下午2:06:08
     */
    public List<Map<String, Integer>> countLogin(@Param("companyId") Integer companyId, @Param("state") Integer state,
            @Param("testAccountIds") String testAccountIds);

    /**
     * 大客户的新增用户统计
     *
     * @param companyId
     * @param state
     * @return
     * @author han
     * @date 2016年3月16日 下午3:44:02
     */
    public List<Map<String, Object>> getUserNumOfMon(@Param("companyId") Integer companyId,
            @Param("state") Integer state, @Param("testAccountIds") String testAccountIds);

    /**
     * 所有大客户的用户数量统计
     *
     * @param state
     * @param testAccountIds
     * @return
     * @author han
     * @date 2016年3月21日 上午11:39:43
     */
    public List<Map<String, Integer>> getUserNumCount(@Param("state") Integer state,
            @Param("testAccountIds") String testAccountIds);

    public List<User> getUser(@Param("companyId") Integer companyId, @Param("state") Integer state,
            @Param("testAccountIds") String testAccountIds);

    public int deleteByPrimaryKey(Integer userId);

    public int insert(User record);

    public int insertSelective(User record);

    public User selectByPrimaryKey(Integer userId);

    public int updateByPrimaryKeySelective(User record);

    public int updateByPrimaryKey(User record);

    public User getUserByEmail(@Param("email") String email);

    public void insertFindPwdInfo(@Param("userId") Integer userId, @Param("expireDate") Date expireDate,
            @Param("randomCode") String randomCode);

    public void cleanFindPwdByUserId(@Param("userId") Integer userId, @Param("expireDate") Date expireDate);

    public void cleanFindPwdByUsername(@Param("username") String username, @Param("expireDate") Date expireDate);

    public User getUserByFindPwd(@Param("username") String username, @Param("randomCode") String randomCode);

    public Integer isEmailInUse(@Param("email") String email, @Param("userId") int userId);

    public Integer isUsernameInUse(@Param("username") String username, @Param("userId") int userId);

    public User getUserByName(@Param("username") String username);

    // TODO 返回值将来也许需要改成List<Integer>
    /**
     * 获取用户所属的大客户
     * 
     * @param userId
     * @return
     * @date 2016-1-9 下午10:37:35
     */
    public Integer getCompanyIdByUserId(@Param("userId") Integer userId);

    /**
     * 分页获取用户列表
     *
     * @param state
     * @param page
     * @return
     * @author han
     * @date 2016年1月27日 下午4:15:48
     */
    public List<Map<String, String>> getUserByPage(@Param("companyId") Integer companyId, @Param("state") Integer state,
            Page page,
            @Param("searchField") String searchField, @Param("keyword") String keyword,
            @Param("testAccountIds") String testAccountIds);

    /**
     * 添加用户
     *
     * @param user
     * @return
     * @author han
     * @date 2016年1月29日 下午1:30:45
     */
    public int addUser(User user);

    /**
     * 注册用户时授权app
     *
     * @param userId
     * @param appIds
     * @param isAdded
     * @return
     * @author han
     * @date 2016年1月29日 下午1:51:39
     */
    public int addUserAppRight(@Param("userId") int userId, @Param("appIds") String[] appIds,
            @Param("isAdded") int isAdded);

    /**
     * 注册用户增加用户和大客户关系
     *
     * @param userId
     * @param companyId
     * @return
     * @author han
     * @date 2016年1月29日 下午1:52:48
     */
    public int addUserCompanyRelat(@Param("userId") int userId, @Param("companyId") int companyId);

    /**
     * 
     *
     * @param state
     * @return
     * @author han
     * @date 2016年2月18日 下午5:19:10
     */
    public List<User> getAllUserList(@Param("state") Integer state);

    /**
     * 获取select2用户信息
     * 
     * @param state
     * @return
     */
    public List<UserSelect> getAllUserSelectList(@Param("state") Integer state);

    /**
     * 根据用户id获取已授权的app
     */
    public List<Map<String, String>> getAppListByUserId(Integer userId);

    /**
     * 为置顶的用户授权
     */
    public void grantUserApp(@Param("userId") Integer userId,
            @Param("appAddList") List<Map<String, String>> appAddList);

    public void addUserRoleRight(@Param("userId") Integer userId, @Param("roleIds") String[] roleIds);
}
