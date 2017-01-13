<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<aside class="pro-sidebar product-sidebar {{collapsed|proSidebarLeftFilter}}">
  <section class="s-bar rocky">
    <ul class="pro-sidebar-menu">
      <li class="pro-sidebar-header"><img src="<%=request.getContextPath()%>/app/image?file=rocky.png" alt="华木兰" title="华木兰"></li>
      <li ng-class="{active: isActive('/product/rocky/upload')}">
        <a di-href="#/product/rocky/upload"><span>上传</span></a>
      </li>
      <li ng-class="{active: isActive('/product/rocky/data')}">
        <a di-href="#/product/rocky/data"><span>数据</span></a>
      </li>
      <li ng-class="{active: isActive('/product/rocky/report')}">
        <a di-href="#/product/rocky/report"><span>报告</span></a>
      </li>
    </ul>
  </section>
</aside>