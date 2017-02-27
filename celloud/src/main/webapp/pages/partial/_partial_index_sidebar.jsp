<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
  <aside class="sidebar {{collapsed|collapsedFilter}}" id="common-sidebar">
    <section class="s-bar">
      <div class="sidebar-collapse">
        <a href="javascript:void(0)" ng-click="toggleCollapse()"><i class="{{collapsed|collapsedIconFilter}}"></i></a>
      </div>
      <ul class="sidebar-menu">
        <li class="sidebar-header">产品与服务</li>
        <shiro:hasPermission name="data:board">
	        <li ng-class="{active: (isActive('/') || isActive('/product'))}">
	          <a di-href="<%=request.getContextPath()%>/index#/"><i class="overview-icon"></i><span>我的工作台</span></a>
	        </li>
        </shiro:hasPermission>
        <shiro:hasPermission name="sample:manage">
            <shiro:hasPermission name="sample:collection">
	            <li ng-class="{active: isActive('/sampling')}">
	              <a di-href="<%=request.getContextPath()%>/index#/sampling/collection"><i class="sample-icon"></i><span>样本采集</span></a>
	            </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="sample:collection-info">
	            <li ng-class="{active: isActive('/sampling')}">
	               <a di-href="#/sampling/info-collection"><i class="sample-icon"></i><span>样本信息采集</span></a>
	            </li>
	        </shiro:hasPermission>
        </shiro:hasPermission>
        <shiro:hasPermission name="experiment:manage">
	        <li ng-class="{active: isActive('/experiment')}">
	          <a di-href="<%=request.getContextPath()%>/index#/experiment/scanStorage"><i class="experiment-icon"></i><span>实验管理</span></a>
	        </li>
        </shiro:hasPermission>
        <shiro:hasPermission name="data:manage">
	        <li ng-class="{active: isActive('/data')}" ng-show="!userProduct.onlyBSI">
	          <a di-href="<%=request.getContextPath()%>/index#/data"><i class="data-icon"></i><span>数据管理</span></a>
	        </li>
	    </shiro:hasPermission>
        <shiro:hasPermission name="project:report">
            <li ng-class="{active: isActive('/reportpro')}" ng-show="!userProduct.onlyBSI">
                <a di-href="<%=request.getContextPath()%>/index#/reportpro/1/10/1/0/all/all/0/all"  ng-show="!userProduct.onlyBSI"><i class="report-icon"></i><span>报告管理</span></a>
	        </li>
        </shiro:hasPermission>
        <shiro:hasPermission name="data:report">
            <li ng-class="{active: isActive('/reportdata')}" ng-show="!userProduct.onlyBSI">
	            <a di-href="<%=request.getContextPath()%>/index#/reportdata/1/20/0/all/all/all/all/all/all"  ng-show="!userProduct.onlyBSI"><i class="report-icon"></i><span>报告管理</span></a>
	        </li>
        </shiro:hasPermission>
        <%-- <shiro:hasPermission name="application:center"> --%>
	        <li ng-class="{active: isActive('/app')}">
	          <a di-href="<%=request.getContextPath()%>/index#/app"><i class="app-icon"></i><span>应用市场</span></a>
	        </li>
        <%-- </shiro:hasPermission> --%>
        <shiro:hasPermission name="statistics">
            <li ng-class="{active: isActive('/count')}">
              <a di-href="<%=request.getContextPath()%>/index#/count"><i class="statistics-icon"></i><span>统计</span></a>
            </li>
        </shiro:hasPermission>
      </ul>
      <ul class="sidebar-menu">
        <li class="sidebar-header">用户中心</li>
        <li ng-class="{active: isActive('/user/')}">
          <a di-href="<%=request.getContextPath()%>/index#/user/base"><i class="account-icon"></i><span>账号管理</span></a>
        </li>
        <shiro:hasPermission name="system:setting">
	        <li ng-class="{active: isActive('/company')}">
	          <a di-href="<%=request.getContextPath()%>/index#/company/user"><i class="company-icon"></i><span>系统设置</span></a>
	        </li>
        </shiro:hasPermission>
        <li ng-class="{active: isActive('/feedback')}">
          <a di-href="<%=request.getContextPath()%>/index#/feedback"><i class="qa-icon"></i><span>问题反馈</span></a>
        </li>
        <shiro:hasPermission name="cost:center">
	        <li ng-class="{active: isActive('/expense')}">
	          <a di-href="<%=request.getContextPath()%>/index#/expense/consume"><i class="cost-icon"></i><span>费用中心</span></a>
	        </li>
        </shiro:hasPermission>
      </ul>
    </section>
  </aside>