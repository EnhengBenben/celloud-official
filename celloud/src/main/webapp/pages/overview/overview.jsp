<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="pro-body">
	<ol class="breadcrumb">
	  <li>CelLoud</li>
	  <li>我的工作台</li>
	</ol>
	<div class="content overview">
		<shiro:hasPermission name="bsi:product">
		  <section class="overview-s">
		    <h5 class="overview-header"><i class="myapp-icon"></i><span>我的产品</span></h5>
		    <div class="o-app-list">
			  <ul>
			    <li>
			      <a href="<%=request.getContextPath()%>/bsi">
			        <div class="inner">
			          <img src="<%=request.getContextPath()%>/images/app/bactive.png" alt="百菌探" title="百菌探">
	<!-- 		        <a href="#" class="footer"><i class="setup-icon"></i></a> -->
			        </div>
			      </a>
			    </li>
			  </ul>
	<!-- 		  <div class="more-app pull-right"> -->
	<!-- 	        <a href="#" class=""> 更<br>多</a> -->
	<!-- 	      </div> -->
		    </div>
		  </section>
	  </shiro:hasPermission>
	  <shiro:hasPermission name="rocky:product">
		  <section class="overview-s">
		    <h5 class="overview-header"><i class="myapp-icon"></i><span>我的产品</span></h5>
		    <div class="o-app-list">
			  <ul>
			    <li>
			      <a href="<%=request.getContextPath()%>/rocky">
			        <div class="inner">
			          <img src="<%=request.getContextPath()%>/images/app/rocky.png" alt="华木兰" title="华木兰">
			        </div>
			      </a>
			    </li>
			  </ul>
		    </div>
		  </section>
	  </shiro:hasPermission>
	  <section class="overview-s">
	    <h5 class="overview-header"><i class="mycount-icon"></i><span>使用统计</span></h5>
	    <div class="o-count-list" ng-controller="overviewCount">
	      <ul>
	        <li>
	          <div class="inner datanum">
	            <i class="bar-icon-lg"></i>
	            <h1>{{map.countData}}<span>个</span></h1>
	            <div class="footer">
		          <span>数据总量</span>
	              <a href="#/data"><span>详情</span><i class="next-icon"></i></a>
	            </div>
	          </div>
	        </li>
	        <li>
	          <div class="inner datasize">
	            <i class="pie-icon-lg"></i>
	            <h1>{{map.sumData}}<span>{{map.format}}</span></h1>
	            <div class="footer">
	              <span>数据大小</span>
	              <a href="#/data"><span>详情</span><i class="next-icon"></i></a>
	            </div>
	          </div>
	        </li>
	        <li>
	          <div class="inner appnum">
	            <i class="app-icon-lg"></i>
	            <h1>{{map.countApp}}<span>个</span></h1>
	            <div class="footer">
	              <span>产品总量</span>
	              <a href="#/app"><span>详情</span><i class="next-icon"></i></a>
	            </div>
	          </div>
	        </li>
	        <li>
	          <div class="inner reportnum">
	            <i class="report-icon-lg"></i>
	            <h1>{{map.countReport}}<span>个</span></h1>
	            <div class="footer">
	              <span>报告数量</span>
	              <a href="#/reportpro"><span>详情</span><i class="next-icon"></i></a>
	            </div>
	          </div>
	        </li>
	        <li>
	          <div class="inner expense">
	            <i class="money-icon-lg"></i>
	            <h1>{{map.countExpense}}<span>元</span></h1>
	            <div class="footer">
	              <span>消费总额</span>
	              <a href="#"><span>详情</span><i class="next-icon"></i></a>
	            </div>
	          </div>
	        </li>
	      </ul>
	    </div>
	    <div class="o-count-detail row">
	      <div class="col-xs-12 col-md-6">
	        <div class="count-box">
	          <div class="count-box-head">
	            <h5><i class="bar-icon"></i><span>数据总量</span></h5>
	            <div class="switch-btn-group pull-right">
	              <a><span class="s-btn active"  ng-controller="fileDayCount" id="data-day-span" onclick="userCount.dayMonthSwitch('data-day','data-month')">日</span></a>
	              <a><span class="s-btn" ng-controller="fileMonthCount" id="data-month-span" onclick="userCount.dayMonthSwitch('data-month','data-day')">月</span></a>
	            </div>
	          </div>
	          <div class="count-box-body" id="count-data-day-chart">
	          </div>
	          <div class="count-box-body hide" id="count-data-month-chart">
	          </div>
	        </div>
	        <div class="count-box">
	          <div class="count-box-head">
	            <h5><i class="bar-icon"></i><span>报告总量</span></h5>
	            <div class="switch-btn-group pull-right">
	              <a><span class="s-btn active"  ng-controller="reportDayCount" id="report-day-span" onclick="userCount.dayMonthSwitch('report-day','report-month')">日</span></a>
	              <a><span class="s-btn" ng-controller="reportMonthCount" id="report-month-span" onclick="userCount.dayMonthSwitch('report-month','report-day')">月</span></a>
	            </div>
	          </div>
	          <div class="count-box-body" id="count-report-day-chart">
	          </div>
	          <div class="count-box-body hide" id="count-report-month-chart">
	          </div>
	        </div>
	      </div>
	      <div class="col-xs-12 col-md-6">
		      <div class="count-box">
		        <div class="count-box-head">
		            <h5><i class="pie-icon"></i><span>数据大小</span></h5>
		            <div class="switch-btn-group pull-right">
		              <a><span class="s-btn active" ng-controller="fileSizeDayCount" id="source-day-span" onclick="userCount.dayMonthSwitch('source-day','source-month')">日</span></a>
		              <a><span class="s-btn" ng-controller="fileSizeMonthCount" id="source-month-span" onclick="userCount.dayMonthSwitch('source-month','source-day')">月</span></a>
		            </div>
		          </div>
		          <div class="count-box-body" id="count-source-day-chart">
		          </div>
		          <div class="count-box-body hide" id="count-source-month-chart">
		          </div>
		      </div>
		      <div class="count-box">
		        <div class="count-box-head">
		            <h5><i class="pie-icon"></i><span>已添加APP</span></h5>
		            <div class="switch-btn-group pull-right">
		              <a><span class="s-btn active" ng-controller="appDayCount" id="app-day-span" onclick="userCount.dayMonthSwitch('app-day','app-month')">日</span></a>
		              <a><span class="s-btn" ng-controller="appMonthCount" id="app-month-span" onclick="userCount.dayMonthSwitch('app-month','app-day')">月</span></a>
		            </div>
		          </div>
		          <div class="count-box-body" id="count-app-day-chart">
		          </div>
		          <div class="count-box-body hide" id="count-app-month-chart">
		          </div>
		      </div>
	        </div>
	      </div>
	    </div>
	  </section>
	</div>
</div>
