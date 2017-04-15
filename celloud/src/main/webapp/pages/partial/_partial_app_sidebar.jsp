<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<aside class="pro-sidebar {{collapsed|proSidebarLeftFilter}}" ng-controller="proSidebarController">
  <section class="s-bar" ng-controller="appClassifyCtrl">
    <ul class="pro-sidebar-menu">
      <li class="pro-sidebar-header">应用市场</li>
      <li class="pro-main">
        <a di-href="#/app"><span>产品分类</span></a>
      </li>
      <div ng-repeat="classify in classifys">
	      <li ng-class="{active: isActive('app/list/{{classify.classifyId}}')}">
	        <a ng-href="#/app/list/{{classify.classifyId}}"><span>{{classify.classifyName}}</span></a>
	      </li>
      </div>
    </ul>
  </section>
</aside>