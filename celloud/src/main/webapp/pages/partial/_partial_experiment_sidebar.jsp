<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<aside class="pro-sidebar {{collapsed|proSidebarLeftFilter}}">
  <section class="s-bar">
    <ul class="pro-sidebar-menu" ng-controller="sidebarController">
      <li class="header">实验管理</li>
      <li ng-class="{active: isActive('/experiment/scanStorage')}">
        <a href="#/experiment/scanStorage"><span>样本入库</span></a>
      </li>
      <li ng-class="{active: isActive('/experiment/tokenDNA')}">
        <a href="#/experiment/tokenDNA"><span>提取DNA</span></a>
      </li>
      <li ng-class="{active: isActive('/experiment/createLibrary')}">
        <a href="#/experiment/createLibrary"><span>文库构建</span></a>
      </li>
    </ul>
  </section>
</aside>