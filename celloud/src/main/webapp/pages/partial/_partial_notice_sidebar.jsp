<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<aside class="pro-sidebar {{collapsed|proSidebarLeftFilter}}">
  <section class="s-bar">
    <ul class="pro-sidebar-menu" ng-controller="sidebarController">
      <li class="header">消息管理</li>
      <li ng-class="{active: isActive('/messages')}">
        <a href="#/messages"><span>消息提醒</span></a>
      </li>
      <li ng-class="{active: isActive('/notices')}">
        <a href="#/notices"><span>系统公告</span></a>
      </li>
      <li ng-class="{active: isActive('/message/setting')}">
        <a href="#/message/setting"><span>消息设置</span></a>
      </li>
    </ul>
  </section>
</aside>