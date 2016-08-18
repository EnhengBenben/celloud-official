<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<aside class="pro-sidebar {{collapsed|proSidebarLeftFilter}}">
  <section class="s-bar">
    <ul class="pro-sidebar-menu" ng-controller="sidebarController">
      <li class="header">消息管理</li>
      <li ng-class="{active: isActive('/notice/list')}">
        <a href="#/notice/list"><span>消息提醒</span></a>
      </li>
      <li ng-class="{active: isActive('/notice/system')}">
        <a href="#/notice/system"><span>系统公告</span></a>
      </li>
      <li ng-class="{active: isActive('/notice/set')}">
        <a href="#/notice/set"><span>消息设置</span></a>
      </li>
    </ul>
  </section>
</aside>