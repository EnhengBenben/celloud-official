<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<aside class="pro-sidebar {{collapsed|proSidebarLeftFilter}}">
  <section class="s-bar">
    <ul class="pro-sidebar-menu">
      <li class="pro-sidebar-header">医院管理</li>
      <shiro:hasPermission name="user:manage">
	      <li ng-class="{active: isActive('/company/user')}">
	        <a di-href="#/company/user"><span>医院账号管理</span></a>
	      </li>
      </shiro:hasPermission>
      <shiro:hasPermission name="base:info">
	      <li ng-class="{active: isActive('/company/base')}">
	        <a di-href="#/company/base"><span>医院基本信息</span></a>
	      </li>
      </shiro:hasPermission>
    </ul>
  </section>
</aside>