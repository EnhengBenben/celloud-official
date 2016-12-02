<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<aside class="pro-sidebar {{collapsed|proSidebarLeftFilter}}">
  <section class="s-bar">
    <ul class="pro-sidebar-menu">
      <li class="pro-sidebar-header">样本管理</li>
      <li ng-class="{active: isActive('/sampling/collection')}">
        <a di-href="#/sampling/collection"><span>样本采集</span></a>
      </li>
      <li ng-class="{active: isActive('/sampling/tracking')}">
        <a di-href="#/sampling/tracking"><span>样本追踪</span></a>
      </li>
    </ul>
  </section>
</aside>