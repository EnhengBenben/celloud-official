<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
  <aside class="sidebar {{collapsed|collapsedFilter}}" id="common-sidebar">
    <section class="s-bar">
      <div class="sidebar-collapse">
        <a href="javascript:void(0)" ng-click="toggleCollapse()"><i class="{{collapsed|collapsedIconFilter}}"></i></a>
      </div>
      <ul class="sidebar-menu">
        <li class="header">产品与服务</li>
        <li ng-class="{active: isActive('/')}">
          <a di-href="<%=request.getContextPath()%>/index#/"><i class="overview-icon"></i><span>我的工作台</span></a>
        </li>
        <shiro:hasAnyRoles name="bsier,rocky">
            <li ng-class="{active: isActive('/sampling')}">
              <a di-href="<%=request.getContextPath()%>/index#/sampling"><i class="sample-icon"></i><span>样本采集</span></a>
            </li>
        </shiro:hasAnyRoles>
        <shiro:hasAnyRoles name="bsier">
	        <li ng-class="{active: isActive('/experiment')}">
	          <a di-href="<%=request.getContextPath()%>/index#/experiment/scanStorage"><i class="experiment-icon"></i><span>实验管理</span></a>
	        </li>
        </shiro:hasAnyRoles>
        <li ng-class="{active: isActive('/data')}">
          <a di-href="<%=request.getContextPath()%>/index#/data"><i class="data-icon"></i><span>数据管理</span></a>
        </li>
        <li ng-class="{active: isActive('/report')}">
            <shiro:hasPermission name="runWithProject:button">
                <a di-href="<%=request.getContextPath()%>/index#/reportpro"><i class="report-icon"></i><span>报告管理</span></a>
            </shiro:hasPermission>
            <shiro:hasPermission name="runWithData:button">
                <a di-href="<%=request.getContextPath()%>/index#/reportdata"><i class="report-icon"></i><span>报告管理</span></a>
            </shiro:hasPermission>
        </li>
        <li ng-class="{active: isActive('/app')}">
          <a di-href="<%=request.getContextPath()%>/index#/app"><i class="app-icon"></i><span>应用市场</span></a>
        </li>
        <shiro:hasPermission name="count:menu">
            <li ng-class="{active: isActive('/count')}">
              <a di-href="<%=request.getContextPath()%>/index#/count"><i class="count-icon"></i><span>统计</span></a>
            </li>
        </shiro:hasPermission>
      </ul>
      <ul class="sidebar-menu">
        <li class="header">用户中心</li>
        <li ng-class="{active: isActive('/user')}">
          <a di-href="<%=request.getContextPath()%>/index#/user/base"><i class="account-icon"></i><span>账号管理</span></a>
        </li>
        <li ng-class="{active: isActive('/feedback')}">
          <a di-href="<%=request.getContextPath()%>/index#/feedback"><i class="qa-icon"></i><span>问题反馈</span></a>
        </li>
        <li ng-class="{active: isActive('/expense')}">
          <a di-href="<%=request.getContextPath()%>/index#/expense/consume"><i class="cost-icon"></i><span>费用中心</span></a>
        </li>
      </ul>
    </section>
  </aside>