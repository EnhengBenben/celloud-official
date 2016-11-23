package com.celloud.manager.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.celloud.manager.alimail.AliEmail;
import com.celloud.manager.alimail.AliEmailUtils;
import com.celloud.manager.alimail.AliSubstitution;
import com.celloud.manager.alimail.EmailParams;
import com.celloud.manager.alimail.EmailType;
import com.celloud.manager.constants.AppIsAdd;
import com.celloud.manager.constants.Constants;
import com.celloud.manager.constants.ConstantsData;
import com.celloud.manager.constants.DataState;
import com.celloud.manager.constants.RechargeType;
import com.celloud.manager.constants.UserRole;
import com.celloud.manager.mapper.UserMapper;
import com.celloud.manager.mapper.UserRegisterMapper;
import com.celloud.manager.model.User;
import com.celloud.manager.model.UserRegister;
import com.celloud.manager.model.UserSelect;
import com.celloud.manager.page.Page;
import com.celloud.manager.page.PageList;
import com.celloud.manager.service.ExpensesService;
import com.celloud.manager.service.RechargeService;
import com.celloud.manager.service.UserService;
import com.celloud.manager.utils.Base64Util;
import com.celloud.manager.utils.MD5Util;
import com.celloud.manager.utils.PropertiesUtil;
import com.celloud.manager.utils.ResetPwdUtils;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private UserMapper userMapper;
	@Resource
	private RechargeService rechargeService;
	@Resource
	private ExpensesService expensesService;
    @Resource
    private AliEmailUtils aliEmail;

    @Override
    public User login(User user) {
        return userMapper.checkLogin(user);
    }

    @Resource
    private UserRegisterMapper userRegisterMapper;

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
        temp.setIcon(user.getIcon());
        return userMapper.updateByPrimaryKeySelective(temp);
    }

    @Override
    public boolean isEmailInUse(String email, Integer userId) {
        return userMapper.isEmailInUse(email, userId == null ? 0 : userId) > 0;
    }

	@Override
	public boolean isEmailAuth(String email, Integer userId) {
		return userMapper.isEmailAuth(email, userId, UserRole.ADMINISTRATOR) == 0;
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
    public PageList<Map<String, String>> getUserByPage(Integer companyId, Page page, String searchField,
            String keyword) {
        List<Map<String, String>> list = userMapper.getUserByPage(companyId, DataState.ACTIVE, page, searchField,
                keyword.trim(),
                PropertiesUtil.testAccountIds);
        return new PageList<Map<String, String>>(page, list);
    }

    @Override
    public boolean getValidate(String email, String md5) {
        logger.info("邮箱：{}   ,验证码：{}", email, md5);
        logger.info("验证{}", userRegisterMapper.getValidate(email, md5));
        return userRegisterMapper.getValidate(email, md5) > 0;
    }

	@Override
	public UserRegister getUserRegisterInfo(String email, String md5) {
		return userRegisterMapper.getUserRegisterInfo(email, md5);
	}

    @Override
    public boolean isUsernameInUse(String username, Integer userId) {
        return userMapper.isUsernameInUse(username, userId == null ? 0 : userId) > 0;
    }

    @Override
    public boolean addUser(User user, String md5code, Integer appCompanyId) {
		Integer userId = ConstantsData.getLoginUserId();
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        user.setState(DataState.ACTIVE);
        int addNum = userMapper.addUser(user);
        if (addNum > 0) {
			UserRegister ur = userRegisterMapper.getUserRegisterInfo(user.getEmail(), md5code);
            String appIdStr = ur != null ? ur.getAppIds() : null;
            String roleIdStr = ur != null ? ur.getRoleIds() : null;
			Integer authFrom = ur != null ? ur.getAuthFrom() : 0;
            if (StringUtils.isNotBlank(appIdStr)) {
                String[] appIds = appIdStr.split(",");
				userMapper.addUserAppRight(user.getUserId(), appIds, AppIsAdd.NOT_ADDED, authFrom);
            }
            if (StringUtils.isNotBlank(roleIdStr)) {
                String[] roleIds = roleIdStr.split(",");
				userMapper.addUserRoleRight(user.getUserId(), roleIds, authFrom);
            }
            if (appCompanyId != null) {
                userMapper.addUserCompanyRelat(user.getUserId(), appCompanyId);
            }
			userRegisterMapper.deleteUserRegisterInfo(user.getEmail(), userId);
            // 发送注册成功邮件
            aliEmail.simpleSend(
                    AliEmail.template(EmailType.REGISTER_SUCCESS)
                            .substitutionVars(
                                    AliSubstitution.sub().set(EmailParams.REGISTER_SUCCESS.url.name(),
                                            ResetPwdUtils.officialWebsite)),
                    user.getEmail());
            return true;
        }
		return false;
    }

    @Override
    public void sendRegisterEmail(String[] emailArray, Integer deptId, Integer companyId, Integer appCompanyId,
            Integer[] appIdArray, Integer[] roleIdArray, Integer role) {
		Integer userId = ConstantsData.getLoginUserId();
        for (String email : emailArray) {
			userRegisterMapper.deleteUserRegisterInfo(email, userId);
            String randomCode = MD5Util.getMD5(String.valueOf(new Date().getTime()));
            StringBuffer appIds = new StringBuffer();
            StringBuilder roleIds = new StringBuilder();
            if (appIdArray != null && appIdArray.length > 0) {
                for (Integer appId : appIdArray) {
                    appIds.append(appId + ",");
                }
                appIds.deleteCharAt(appIds.length() - 1);
            }
            if (roleIdArray != null && roleIdArray.length > 0) {
                for (Integer roleId : roleIdArray) {
                    roleIds.append(roleId + ",");
                }
                roleIds.deleteCharAt(roleIds.length() - 1);
            }
			userRegisterMapper.insertUserRegisterInfo(email, randomCode, appIds.toString(), roleIds.toString(), userId);
            String param = Base64Util.encrypt(
                    email + "/" + randomCode + "/" + deptId + "/" + companyId + "/" + appCompanyId + "/" + role);
            aliEmail.simpleSend(AliEmail.template(EmailType.USER_REGISTER)
                    .substitutionVars(AliSubstitution.sub().set(EmailParams.USER_REGISTER.url.name(),
                            ResetPwdUtils.userPath.replaceAll("path", param))),
                    email);
        }

    }

	@Override
	public void sendAddPermissionEmail(String email, String appIds, String roleIds) {
		Integer userId = ConstantsData.getLoginUserId();
		userRegisterMapper.deleteUserRegisterInfo(email, userId);
		String randomCode = MD5Util.getMD5(String.valueOf(new Date().getTime()));
		userRegisterMapper.insertUserRegisterInfo(email, randomCode, appIds, roleIds, userId);
		String param = Base64Util.encrypt(email + "/" + randomCode + "/" + userId);
		aliEmail.simpleSend(
				AliEmail.template(EmailType.ADD_PERMISION).substitutionVars(AliSubstitution.sub()
						.set(EmailParams.ADD_PERMISION.url.name(),
								ResetPwdUtils.permissionPath.replaceAll("path", param))
						.set(EmailParams.ADD_PERMISION.authFrom.name(), ConstantsData.getLoginUserName())),
				email);
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
    public List<Map<String, String>> getAppListByUserId(Integer userId) {
        return userMapper.getAppListByUserId(userId);
    }

    @Override
    public void grantUserApp(Integer userId, List<Map<String, String>> appAddList) {
        userMapper.grantUserApp(userId, appAddList);
    }

    @Override
    public void grantUserRole(Integer userId, String[] roleIds) {
		Integer authFrom = ConstantsData.getLoginUserId();
		userMapper.addUserRoleRight(userId, roleIds, authFrom);
    }

    @Override
    public List<User> getAllUserByBigCustomer(Integer companyId) {
        return userMapper.findUserByBigCustomer(companyId, DataState.ACTIVE, PropertiesUtil.testAccountIds);
    }

	@Override
	public boolean moneyGiven(Integer from, Integer to, String toUserName, BigDecimal amount) {
		Integer expenseId = expensesService.saveExpenses(from, to, toUserName, amount);
		Integer num = rechargeService.saveRecharge(amount, to, RechargeType.GRANT, expenseId);
		return num == 1;
	}

	@Override
	public int addUserAppRight(int userId, String[] appIds, int isAdded, Integer authFrom) {
		return userMapper.addUserAppRight(userId, appIds, isAdded, authFrom);
	}

	@Override
	public int addUserCompanyRelat(int userId, int companyId) {
		return userMapper.addUserCompanyRelat(userId, companyId);
	}

	@Override
	public void addUserRoleRight(Integer userId, String[] roleIds, Integer authFrom) {
		userMapper.addUserRoleRight(userId, roleIds, authFrom);
	}

	@Override
	public void deleteUserRegisterInfo(String email, Integer authFrom) {
		userRegisterMapper.deleteUserRegisterInfo(email, authFrom);
	}
}
