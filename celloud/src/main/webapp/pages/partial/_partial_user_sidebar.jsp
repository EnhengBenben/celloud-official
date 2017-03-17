<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<aside class="pro-sidebar {{collapsed|proSidebarLeftFilter}}" ng-controller="proSidebarController">
  <section class="s-bar">
    <ul class="pro-sidebar-menu">
      <li class="pro-sidebar-header">账户管理</li>
      <li ng-class="{active: isActive('user/base')}">
        <a di-href="#/user/base"><span>基本信息</span></a>
      </li>
      <li ng-class="{active: isActive('/user/pwd')}">
        <a di-href="#/user/pwd"><span>修改密码</span></a>
      </li>
      <li ng-class="{active: isActive('/user/log')}">
        <a di-href="#/user/log"><span>操作日志</span></a>
      </li>
      <li ng-class="{active: isActive('/user/email')}">
        <a di-href="#/user/email"><span>修改邮箱</span></a>
      </li>
      <shiro:hasPermission name="bsi:product">
	      <li ng-class="{active: isActive('/user/set')}">
	        <a di-href="#/user/set"><span>报告设置</span></a>
	      </li>
      </shiro:hasPermission>
    </ul>
  </section>
</aside>