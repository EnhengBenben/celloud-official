package com.celloud.security;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.model.mysql.User;
import com.celloud.model.mysql.UserRegister;
import com.celloud.service.UserService;

public class UserRealm extends AuthorizingRealm {
    private static Logger logger = LoggerFactory.getLogger(UserRealm.class);
    @Resource
    private UserService userService;

    /**
     * 提供给shiro的用户授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.debug("doGetAuthorizationInfo。。。");
        String username = (String) principals.getPrimaryPrincipal();
        logger.debug("doGetAuthorizationInfo username:{}", username);
        User user = ConstantsData.getLoginUser();
        if (user == null) {
            if (SecurityUtils.getSubject().isRemembered()) {
                user = userService.findByUsernameOrEmail(username);
                SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_LOGIN_USER, user);
            } else {
                throw new UnknownAccountException();// 没找到帐号
            }
        }
        return loadAuthorizationInfo(user);
    }

    @SuppressWarnings("unchecked")
    public SimpleAuthorizationInfo loadAuthorizationInfo(User user) {
        Session session = SecurityUtils.getSubject().getSession();
        Set<String> roles = (Set<String>) session.getAttribute(Constants.SESSION_LOGIN_USER_ROLES);
        if (roles == null) {
            roles = userService.findRoles(user.getUserId());
            roles = roles == null ? new HashSet<String>() : roles;
            logger.info("初始化用户{}的角色信息：共{}个角色", user.getUsername(), roles.size());
            session.setAttribute(Constants.SESSION_LOGIN_USER_ROLES, roles);
        }
        Set<String> permissions = (Set<String>) session.getAttribute(Constants.SESSION_LOGIN_USER_PERMISSIONS);
        if (permissions == null) {
            permissions = userService.findPermissions(user.getUserId());
            permissions = permissions == null ? new HashSet<String>() : permissions;
            logger.info("初始化用户{}的权限信息：共{}个权限", user.getUsername(), permissions.size());
            session.setAttribute(Constants.SESSION_LOGIN_USER_PERMISSIONS, permissions);
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 提供给shiro的用户认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.debug("doGetAuthenticationInfo...");
        String username = (String) token.getPrincipal();
        logger.info("getting authenticationInfo: username={}", username);
        User user = userService.findByUsernameOrEmail(username);
        Session session = SecurityUtils.getSubject().getSession();
        UserRegister userRegister = null;
        String password = "";
        if (user == null) {
            userRegister = userService.getUserRegisterInfo(username);
            if (userRegister == null) {// 用户表&注册表都没有
                throw new UnknownAccountException();// 没找到帐号
            } else {
                session.setAttribute("isCellphoneRegister", true);
                password = userRegister.getMd5();
            }
            if (userRegister.getExpireDate().compareTo(new Date()) <= 0) {// 验证码超时
                throw new ExpiredCredentialsException();
            }
        } else {
            password = user.getPassword();
            user.setPassword("");
            session.setAttribute(Constants.SESSION_LOGIN_USER, user);
            session.removeAttribute(Constants.SESSION_LOGIN_USER_ROLES);
            session.removeAttribute(Constants.SESSION_LOGIN_USER_PERMISSIONS);
        }
		return new SimpleAuthenticationInfo(username, password, getName());
    }

}
