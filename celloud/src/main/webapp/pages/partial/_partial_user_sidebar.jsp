<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<aside class="pro-sidebar {{collapsed|proSidebarLeftFilter}}">
  <section class="s-bar">
    <ul class="pro-sidebar-menu" ng-controller="sidebarController">
      <li class="header">账户管理</li>
      <li ng-class="{active: isActive('user/base')}">
        <a href="#/user/base"><span>基本信息</span></a>
      </li>
      <li ng-class="{active: isActive('/user/pwd')}">
        <a href="#/user/pwd"><span>修改密码</span></a>
      </li>
      <li ng-class="{active: isActive('/user/log')}">
        <a href="#/user/log"><span>操作日志</span></a>
      </li>
      <li ng-class="{active: isActive('/user/email')}">
        <a href="#/user/email"><span>修改邮箱</span></a>
      </li>
    </ul>
  </section>
</aside>