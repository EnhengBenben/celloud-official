<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<aside class="pro-sidebar {{collapsed|proSidebarLeftFilter}}" ng-controller="proSidebarController">
  <section class="s-bar">
    <ul class="pro-sidebar-menu">
      <li class="pro-sidebar-header">实验管理</li>
      <shiro:hasPermission name="sample:storage">
	      <li ng-class="{active: isActive('/experiment/scanStorage')}">
	        <a di-href="#/experiment/scanStorage"><span>样本入库</span></a>
	      </li>
      </shiro:hasPermission>
      <shiro:hasPermission name="extract:dna">
	      <li ng-class="{active: isActive('/experiment/tokenDNA')}">
	        <a di-href="#/experiment/tokenDNA"><span>提取DNA</span></a>
	      </li>
      </shiro:hasPermission>
      <shiro:hasPermission name="library:construction">
	      <li ng-class="{active: isActive('/experiment/createLibrary')}">
	        <a di-href="#/experiment/createLibrary"><span>文库构建</span></a>
	      </li>
      </shiro:hasPermission>
      <shiro:hasPermission name="library:list">
	      <li ng-class="{active: isActive('/experiment/libraryList')}">
	        <a di-href="#/experiment/libraryList"><span>文库列表</span></a>
	      </li>
      </shiro:hasPermission>
    </ul>
  </section>
</aside>