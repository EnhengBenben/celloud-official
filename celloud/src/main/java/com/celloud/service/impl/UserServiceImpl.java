package com.celloud.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.celloud.alimail.AliEmail;
import com.celloud.alimail.AliEmailUtils;
import com.celloud.alimail.AliSubstitution;
import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.mapper.AppMapper;
import com.celloud.mapper.UserMapper;
import com.celloud.mapper.UserRegisterMapper;
import com.celloud.model.mysql.User;
import com.celloud.sendcloud.EmailParams;
import com.celloud.sendcloud.EmailType;
import com.celloud.service.SecResourceService;
import com.celloud.service.SecRoleService;
import com.celloud.service.UserService;
import com.celloud.utils.Base64Util;
import com.celloud.utils.MD5Util;
import com.celloud.utils.ResetPwdUtils;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    @Resource
    private SecResourceService resourceService;
    @Resource
    private SecRoleService roleService;
    @Resource
    private UserMapper userMapper;
    @Autowired
    private UserRegisterMapper userRegisterMapper;
    @Autowired
    private AliEmailUtils aliEmail;
    @Autowired
    private AppMapper appMapper;

    @Override
    public User login(User user) {
        return userMapper.checkLogin(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public void insertFindPwdInfo(Integer userId, String randomCode) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, Constants.FIND_PASSWORD_EXPIRE_TIME);
        cleanFindPwd(userId, new Date());
        userMapper.insertFindPwdInfo(userId, calendar.getTime(), randomCode);
    }

    @Override
    public void insertWechatCode(Integer userId, String randomCode) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, Constants.WECHAT_EXPIRE_TIME);
        cleanFindPwd(userId, new Date());
        userMapper.insertFindPwdInfo(userId, calendar.getTime(), randomCode);
    }

    @Override
    public void cleanFindPwd(int userId, Date expireDate) {
        userMapper.cleanFindPwdByUserId(userId, expireDate);
    }

    @Override
    public void cleanFindPwd(String username, Date expireDate) {
        userMapper.cleanFindPwdByUsername(username, expireDate);
    }

    @Override
    public User getUserByFindPwd(String username, String randomCode) {
        cleanFindPwd(null, new Date());
        return userMapper.getUserByFindPwd(username, randomCode);
    }

    @Override
    public int updatePassword(int userId, String password) {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(MD5Util.getMD5(password));
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User selectUserById(int userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public User selectUserByIdNotIcon(int userId) {
        return userMapper.selectUserByIdNotIcon(userId);
    }

    @Override
    public Integer updateUserInfo(User user) {
        User temp = new User();
        temp.setCellphone(user.getCellphone());
        temp.setEmail(user.getEmail());
        temp.setUserId(ConstantsData.getLoginUserId());
        temp.setIcon(user.getIcon());
        temp.setNavigation(user.getNavigation());
        return userMapper.updateByPrimaryKeySelective(temp);
    }

    @Override
    public Integer updateUserEmail(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public boolean isEmailInUse(String email, Integer userId) {
        return userMapper.isEmailInUse(email, userId == null ? 0 : userId) > 0;
    }

    @Override
    public User getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    @Override
    public Integer getCompanyIdByUserId(Integer userId) {
        return userMapper.getCompanyIdByUserId(userId);
    }

    @Override
    public User findByUsernameOrEmail(String username) {
        return userMapper.findByUsernameOrEmail(username);
    }

    @Override
    public Set<String> findRoles(Integer userId) {
        return roleService.findRolesByUserId(userId);
    }

    @Override
    public Set<String> findPermissions(Integer userId) {
        return resourceService.findPermissionResourcesByUserId(userId);
    }

    @Override
    public List<String> selectUserUserById(String userIds) {
        return userMapper.selectByIds(userIds);
    }

    @Override
    public List<User> selectUserByIds(String userIds) {
        return userMapper.selectUserByIds(userIds);
    }

    @Override
    public Integer insertUserWechatInfo(Integer userId, String openId, String unionId) {
        return userMapper.insertUserWechatInfo(userId, openId, unionId, new Date());
    }

    @Override
    public String getOpenIdByUser(Integer userId) {
        return userMapper.getOpenIdByUser(userId);
    }

    @Override
    public User getUserByOpenId(String openId) {
        return userMapper.getUserByOpenId(openId);
    }

    @Override
    public int checkWechatBind(String openId, Integer userId) {
        return userMapper.checkWechatBind(openId, userId);
    }

    @Override
    public int checkWechatUnBind(String openId, String pwd) {
        return userMapper.checkWechatUnBind(openId, pwd);
    }

    @Override
    public int wechatUnBind(String openId, String pwd) {
        return userMapper.wechatUnBind(openId, pwd);
    }

    @Override
    public Boolean sendRegisterEmail(String email) {
        userRegisterMapper.deleteUserRegisterInfo(email);
        String randomCode = MD5Util.getMD5(String.valueOf(new Date().getTime()));
        StringBuffer appIds = new StringBuffer();
        StringBuilder roleIds = new StringBuilder();
        // 获取登录用户
        User loginUser = ConstantsData.getLoginUser();
        Integer loginUserId = loginUser.getUserId();
        List<Integer> appIdList = appMapper.findAppIdsByUserId(loginUserId);
        List<Integer> roleIdList = userMapper.findRoleIdsByUserId(loginUserId);
        Integer appCompanyId = userMapper.getCompanyIdByUserId(loginUserId);
        if (appIdList != null && !appIdList.isEmpty()) {
            for (Integer appId : appIdList) {
                appIds.append(appId + ",");
            }
            appIds.deleteCharAt(appIds.length() - 1);
        }
        if (roleIdList != null && !roleIdList.isEmpty()) {
            for (Integer roleId : roleIdList) {
                roleIds.append(roleId + ",");
            }
            roleIds.deleteCharAt(roleIds.length() - 1);
        }
        Integer count = userRegisterMapper.insertUserRegisterInfo(email, randomCode, appIds.toString(),
                roleIds.toString());
        String param = Base64Util.encrypt(email + "/" + randomCode + "/" + loginUser.getDeptId() + "/"
                + loginUser.getCompanyId() + "/" + appCompanyId + "/" + 0);
        aliEmail.simpleSend(
                AliEmail.template(EmailType.USER_REGISTER).substitutionVars(AliSubstitution.sub()
                        .set(EmailParams.USER_REGISTER.url.name(), ResetPwdUtils.userPath.replaceAll("path", param))),
                email);
        return count > 0;
    }


}
