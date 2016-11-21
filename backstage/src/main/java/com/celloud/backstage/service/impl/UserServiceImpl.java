package com.celloud.backstage.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.celloud.backstage.constants.AppIsAdd;
import com.celloud.backstage.constants.Constants;
import com.celloud.backstage.constants.ConstantsData;
import com.celloud.backstage.constants.DataState;
import com.celloud.backstage.mapper.UserMapper;
import com.celloud.backstage.mapper.UserRegisterMapper;
import com.celloud.backstage.model.User;
import com.celloud.backstage.model.UserRegister;
import com.celloud.backstage.model.UserSelect;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.UserService;
import com.celloud.backstage.utils.Base64Util;
import com.celloud.backstage.utils.MD5Util;
import com.celloud.backstage.utils.ResetPwdUtils;
import com.celloud.message.alimail.AliEmailUtils;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    private Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private UserMapper userMapper;
	@Resource
	private AliEmailUtils aliEmail;

    @Resource
    private UserRegisterMapper userRegisterMapper;

	@Override
	public String getOpenIdByUser(Integer userId) {
		return userMapper.getOpenIdByUser(userId);
	}

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
    public Integer updateUserInfo(User user) {
        User temp = new User();
        temp.setCellphone(user.getCellphone());
        temp.setEmail(user.getEmail());
        temp.setUserId(ConstantsData.getLoginUserId());
        return userMapper.updateByPrimaryKeySelective(temp);
    }

    @Override
    public boolean isEmailInUse(String email, Integer userId) {
        return userMapper.isEmailInUse(email, userId == null ? 0 : userId) > 0;
    }

    @Override
    public User getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

	public List<User> getUserByNames(String... usernames) {
		return userMapper.getUserByNames(usernames);
	}

    @Override
    public Integer getCompanyIdByUserId(Integer userId) {
        return userMapper.getCompanyIdByUserId(userId);
    }

    @Override
    public PageList<User> getUserByPage(Page page,String searchField,String keyword) {
        List<User> list=userMapper.getUserByPage(DataState.ACTIVE, page,searchField,keyword.trim());
        return new PageList<User>(page,list);
    }

    @Override
    public boolean getValidate(String email, String md5) {
        logger.info("邮箱：{}   ,验证码：{}",email,md5);
        logger.info("验证{}",userRegisterMapper.getValidate(email, md5));
        return userRegisterMapper.getValidate(email, md5)>0;
    }

    @Override
    public boolean isUsernameInUse(String username, Integer userId) {
        return userMapper.isUsernameInUse(username, userId == null ? 0 : userId)> 0;
    }

    @Override
    public boolean addUser(User user,String md5code,Integer appCompanyId) {
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        user.setState(DataState.ACTIVE);
        UserRegister ur=userRegisterMapper.getUserRegisterInfo(user.getEmail(), md5code);
        int addNum = userMapper.addUser(user);
        if (addNum > 0) {
            String appIdStr=ur!=null?ur.getAppIds():null;
            if(StringUtils.isNotBlank(appIdStr)){
                String[]appIds=appIdStr.split(",");
                userMapper.addUserAppRight(user.getUserId(), appIds, AppIsAdd.NOT_ADDED);
            }
            if(appCompanyId!=null){
                userMapper.addUserCompanyRelat(user.getUserId(), appCompanyId);
            }
            userRegisterMapper.deleteUserRegisterInfo(user.getEmail());
            return true;
        } else {
            return false;
        }
    }

    @Override
	public void sendRegisterEmail(String emailArray, Integer deptId, Integer companyId, Integer appCompanyId,
			Integer[] appIdArray, Integer role, String secRole) {
		userRegisterMapper.deleteUserRegisterInfo(emailArray);
		String randomCode = MD5Util.getMD5(String.valueOf(new Date().getTime()));
		StringBuffer appIds = new StringBuffer();
		if (appIdArray != null && appIdArray.length > 0) {
			for (Integer appId : appIdArray) {
				appIds.append(appId + ",");
			}
			appIds.deleteCharAt(appIds.length() - 1);
		}
		userRegisterMapper.insertUserRegisterInfo(emailArray, randomCode, appIds.toString(), secRole);
		String param = Base64Util.encrypt(
				emailArray + "/" + randomCode + "/" + deptId + "/" + companyId + "/" + appCompanyId + "/" + role);
		String context = ResetPwdUtils.userContent.replaceAll("url",
				"<a href='" + ResetPwdUtils.userPath.replaceAll("path", param) + "'>"
						+ ResetPwdUtils.userPath.replaceAll("path", param) + "</a>");
		aliEmail.simpleSend(ResetPwdUtils.userTitle, context, emailArray);
    }

    @Override
    public List<User> getAllUserList() {
        return userMapper.getAllUserList(DataState.ACTIVE);
    }

    @Override
    public List<UserSelect> getAllUserSelectList() {
        return userMapper.getAllUserSelectList(DataState.ACTIVE);
    }

    @Override
    public PageList<User> getUserPageList(Page page, String condition) {
        List<User> list = userMapper.getUserPageList(DataState.ACTIVE, page,
                condition);
        return new PageList<User>(page, list);
    }

    @Override
    public List<User> getBigCustomers() {
        return userMapper.findUserByRole(DataState.ACTIVE, 1);
    }

	@Override
	public List<User> getUserByAppId(Integer appId) {
		return userMapper.getUserByAppId(DataState.ACTIVE, appId);
	}

}
