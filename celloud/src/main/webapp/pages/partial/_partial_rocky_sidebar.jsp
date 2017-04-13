<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<aside class="pro-sidebar product-sidebar {{collapsed|proSidebarLeftFilter}}" ng-controller="proSidebarController">
  <section class="s-bar rocky">
    <ul class="pro-sidebar-menu">
      <li class="pro-sidebar-header"><img src="<%=request.getContextPath()%>/app/image?file=rocky.png" alt="华木兰" title="华木兰"></li>
      <shiro:hasPermission name="rocky:upload">
	      <li ng-class="{active: isActive('/product/rocky/upload/')}">
	        <a di-href="#/product/rocky/upload/{{rockyTagId}}"><span>上传</span></a>
	      </li>
      </shiro:hasPermission>
      <shiro:hasPermission name="rocky:data">
	      <li ng-class="{active: isActive('/product/rocky/d')}">
	        <a di-href="#/product/rocky/d"><span>数据</span></a>
	      </li>
      </shiro:hasPermission>
      <shiro:hasPermission name="rocky:report">
	      <li ng-class="{active: isActive('/product/rocky/report/')}">
	        <a di-href="#/product/rocky/report/{{rockyTagId}}"><span>报告</span></a>
	      </li>
      </shiro:hasPermission>
    </ul>
  </section>
</aside>