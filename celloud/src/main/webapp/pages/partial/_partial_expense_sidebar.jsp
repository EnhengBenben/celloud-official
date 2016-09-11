<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<aside class="pro-sidebar {{collapsed|proSidebarLeftFilter}}">
  <section class="s-bar">
    <ul class="pro-sidebar-menu" ng-controller="sidebarController">
      <li class="pro-sidebar-header">费用设置</li>
      <li ng-class="{active: isActive('/expense/consume')}">
        <a di-href="#/expense/consume"><span>消费记录</span></a>
      </li>
      <li ng-class="{active: isActive('/expense/paydetail')}">
        <a di-href="#/expense/paydetail"><span>账户充值</span></a>
      </li>
      <li ng-class="{active: isActive('/expense/paylist')}">
        <a di-href="#/expense/paylist"><span>充值记录</span></a>
      </li>
      <li ng-class="{active: isActive('/expense/invoice')}">
        <a di-href="#/expense/invoice"><span>发票管理</span></a>
      </li>
    </ul>
  </section>
</aside>