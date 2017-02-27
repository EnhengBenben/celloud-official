<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<aside class="pro-sidebar {{collapsed|proSidebarLeftFilter}}">
  <section class="s-bar">
    <ul class="pro-sidebar-menu">
      <li class="pro-sidebar-header">样本管理</li>
      <shiro:hasPermission name="sample:collection">
	      <li ng-class="{active: isActive('/sampling/collection')}">
	        <a di-href="#/sampling/collection"><span>样本采集</span></a>
	      </li>
      </shiro:hasPermission>
      <shiro:hasPermission name="sample:collection-info">
          <li ng-class="{active: isActive('/sampling/info-collection')}">
            <a di-href="#/sampling/info-collection"><span>样本信息采集</span></a>
          </li>
      </shiro:hasPermission>
      <shiro:hasPermission name="sample:tracking">
	      <li ng-class="{active: isActive('/sampling/tracking')}">
	        <a di-href="#/sampling/tracking"><span>样本追踪</span></a>
	      </li>
      </shiro:hasPermission>
    </ul>
  </section>
</aside>