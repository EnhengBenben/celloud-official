package com.celloud.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.celloud.alidayu.AliDayuUtils;
import com.celloud.alimail.AliEmail;
import com.celloud.alimail.AliEmailUtils;
import com.celloud.alimail.AliSubstitution;
import com.celloud.constants.AppConstants;
import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.DataState;
import com.celloud.constants.UserRole;
import com.celloud.constants.UserSecRole;
import com.celloud.dao.ReportDao;
import com.celloud.mapper.UserMapper;
import com.celloud.mapper.UserRegisterMapper;
import com.celloud.model.mysql.SecRole;
import com.celloud.model.mysql.User;
import com.celloud.model.mysql.UserRegister;
import com.celloud.sendcloud.EmailParams;
import com.celloud.sendcloud.EmailType;
import com.celloud.service.SecResourceService;
import com.celloud.service.SecRoleService;
import com.celloud.service.UserService;
import com.celloud.utils.Base64Util;
import com.celloud.utils.DataUtil;
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
    private ReportDao reportDao;

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
        temp.setTruename(user.getTruename());
        temp.setAddress(user.getAddress());
        temp.setZipCode(user.getZipCode());
        temp.setAge(user.getAge());
        temp.setSex(user.getSex());
        if ("省份".equals(user.getProvince())) {
            temp.setProvince(null);
        } else {
            temp.setProvince(user.getProvince());
        }
        if ("地级市".equals(user.getCity())) {
            temp.setCity(user.getCity());
        } else {
            temp.setCity(user.getCity());
        }
        if ("市、县级市".equals(user.getDistrict())) {
            temp.setDistrict(user.getDistrict());
        } else {
            temp.setDistrict(user.getDistrict());
        }
        return userMapper.customUpdateByPrimaryKeySelective(temp);
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
    public boolean isCellphoneInUse(String cellphone) {
        return userMapper.isCellphoneInUse(cellphone) > 0;
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
	public Boolean sendRegisterEmail(String email, Integer[] appIds, Integer[] roles) {
        userRegisterMapper.deleteUserRegisterInfo(email);
        String randomCode = MD5Util.getMD5(String.valueOf(new Date().getTime()));
        // 获取登录用户
        User loginUser = ConstantsData.getLoginUser();
        Integer loginUserId = loginUser.getUserId();
        Integer appCompanyId = userMapper.getCompanyIdByUserId(loginUserId);
        Integer count = userRegisterMapper.insertUserRegisterInfo(email, randomCode, null,
                StringUtils.join(appIds, ","),
				StringUtils.join(roles, ","), loginUserId);
        String param = Base64Util.encrypt(email + "/" + randomCode + "/" + loginUser.getDeptId() + "/"
                + loginUser.getCompanyId() + "/" + appCompanyId + "/" + 0);
        aliEmail.simpleSend(
                AliEmail.template(EmailType.USER_REGISTER).substitutionVars(AliSubstitution.sub()
						.set(EmailParams.USER_REGISTER.home.name(), ConstantsData.getContextUrl())
                        .set(EmailParams.USER_REGISTER.url.name(), ResetPwdUtils.userPath.replaceAll("path", param))),
                email);
        return count > 0;
    }

    @Override
    public Boolean sendRegisterSms(String cellphone, String truename, Integer[] appIds, Integer[] roles) {
        // 删除注册信息
        userRegisterMapper.deleteUserRegisterInfo(cellphone);

        // 发送验证码
        String randomCode = DataUtil.getCapchaRandom();
        String sendResult = AliDayuUtils.sendRegisterCaptcha(cellphone, randomCode);
        Integer count = 0;

        // 保存注册信息
        if (!"error".equals(sendResult)) {
            User loginUser = ConstantsData.getLoginUser();
            Integer loginUserId = loginUser.getUserId();
            count = userRegisterMapper.insertUserRegisterInfo(cellphone, MD5Util.getMD5(randomCode), truename,
                    StringUtils.join(appIds, ","), StringUtils.join(roles, ","), loginUserId);
        }
        return count > 0;
    }

    @Override
    public Integer addClientUser(String cellphone) {
        User user = new User();
        user.setCellphone(cellphone);
        user.setUsername("cel_" + cellphone.substring(3, cellphone.length()));
        // 客户端默认密码：CelLoud+手机号
        user.setPassword(MD5Util.getMD5("CelLoud" + cellphone));
        user.setCreateDate(new Date());
        user.setRole(UserRole.C_USER);
        return userMapper.insertSelective(user);
    }

    @Override
    public Integer checkAddClientUser(String cellphone) {
        User user = userMapper.findUserByCellphoneAndRole(cellphone,
                UserRole.C_USER, DataState.ACTIVE);
        if (user == null && cellphone != null) {
            return addClientUser(cellphone);
        }
        return -1;
    }

    public Boolean updateBySelective(User updateUser) {
        return userMapper.updateByPrimaryKeySelective(updateUser) == 1;
    }

    @Override
    public User queryFromMongo(Integer userId) {
        Map<String, Object> filters = new HashMap<String, Object>();
        filters.put("userId", userId);
        List<User> users = reportDao.queryPojoByFilters(User.class, filters, null);
        if (users != null && users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public Boolean saveToMongo(User user) {
        Key<User> key = reportDao.saveObj(user);
        if (key != null) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateToMongo(User user) {
        Map<String, Object> queryFilters = new HashMap<String, Object>();
        Map<String, Object> updateFilters = new HashMap<String, Object>();
        queryFilters.put("userId", user.getUserId());
        updateFilters.put("cellphone", user.getCellphone());
        reportDao.update(User.class, queryFilters, updateFilters);
        return true;
    }

	@Override
	public List<SecRole> getRolesByUserId(Integer userId) {
		return roleService.getRolesByUserId(userId);
	}

	@Override
	public List<SecRole> getRoles(Integer userId, Integer authFrom) {
		List<SecRole> roles = roleService.getRoles(userId, authFrom);
		Map<String, SecRole> map = new HashMap<>();
		for (SecRole secRole : roles) {
			if (UserSecRole.HOSPITALMANAGER.equals(secRole.getId())) {
				continue;
			}
			if (UserSecRole.PLATFORM.equals(secRole.getId())) {
				continue;
			}
			map.put(secRole.getCode(), secRole);
		}
		roles = new ArrayList<>();
		for (Entry<String, SecRole> role : map.entrySet()) {
			roles.add(role.getValue());
		}
		return roles;
	}

    @Override
    public UserRegister getUserRegisterInfo(String email) {
        return userRegisterMapper.getUserRegisterInfoByEmail(email);
    }

    @Override
    public Boolean addCellphoneUser(String cellphone, String password) {
        UserRegister userRegister = userRegisterMapper.getUserRegisterInfoByEmail(cellphone);
        if (userRegister != null) {
            User managerUser = userMapper.selectByPrimaryKey(userRegister.getAuthFrom());
            User user = new User();
            user.setPassword(MD5Util.getMD5(password));
            user.setUsername(cellphone);
            user.setCellphone(cellphone);
            user.setTruename(userRegister.getTruename());
            user.setState(DataState.ACTIVE);
            user.setCompanyId(managerUser.getCompanyId());
            user.setCreateDate(new Date());

            Integer appCompanyId = userMapper.getCompanyIdByUserId(managerUser.getUserId());

            int addNum = userMapper.insertSelective(user);
            if (addNum > 0) {
                String appIdStr = userRegister != null ? userRegister.getAppIds() : null;
                String roleIdStr = userRegister != null ? userRegister.getRoleIds() : null;
                Integer authFrom = userRegister != null ? userRegister.getAuthFrom() : 0;
                if (StringUtils.isNotBlank(appIdStr)) {
                    String[] appIds = appIdStr.split(",");
                    userMapper.addUserAppRight(user.getUserId(), appIds, AppConstants.ALREADY_ADDED, authFrom);
                }
                if (StringUtils.isNotBlank(roleIdStr)) {
                    String[] roleIds = roleIdStr.split(",");
                    userMapper.addUserRoleRight(user.getUserId(), roleIds, authFrom);
                }
                if (appCompanyId != null) {
                    userMapper.addUserCompanyRelat(user.getUserId(), appCompanyId);
                }
                userRegisterMapper.deleteUserRegisterInfo(cellphone);
                return true;
            }
        }
        return false;
    }
}
