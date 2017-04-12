<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="pro-body">
	<ol class="breadcrumb">
	  <li>CelLoud</li>
	  <li>我的工作台</li>
	</ol>
	<div class="content overview">
		<div ng-if="userProduct.app123==123||userProduct.app118==118||userProduct.app133==133||userProduct.app134==134||userProduct.app135==135||userProduct.app136==136||userProduct.app137==137">
		  <section class="overview-s">
		    <h5 class="overview-header"><i class="myapp-icon"></i><span>我的产品</span></h5>
		    <div class="o-app-list">
			  <ul>
			    <li ng-if="userProduct.app118==118">
			      <a href="${pageContext.request.contextPath }/index#/product/bactive/report/118/1/20/all/all/all/all/all/0/asc/asc/asc/desc">
			        <div class="inner">
			          <img src="${pageContext.request.contextPath }/app/image?file=bsi.png" alt="百菌探" title="百菌探">
			        </div>
			      </a>
			    </li>
			    <li ng-if="userProduct.app133==133">
                  <a href="${pageContext.request.contextPath }/index#/product/bactive/report/133/1/20/all/all/all/all/all/0/asc/asc/asc/desc">
                    <div class="inner">
                      <img src="${pageContext.request.contextPath }/app/image?file=bsi.png" alt="百菌探" title="百菌探">
                    </div>
                  </a>
                </li>
                <li ng-if="userProduct.app134==134">
                  <a href="${pageContext.request.contextPath }/index#/product/bactive/report/134/1/20/all/all/all/all/all/0/asc/asc/asc/desc">
                    <div class="inner">
                      <img src="${pageContext.request.contextPath }/app/image?file=bsi.png" alt="百菌探" title="百菌探">
                    </div>
                  </a>
                </li>
                <li ng-if="userProduct.app135==135">
                  <a href="${pageContext.request.contextPath }/index#/product/bactive/report/135/1/20/all/all/all/all/all/0/asc/asc/asc/desc">
                    <div class="inner">
                      <img src="${pageContext.request.contextPath }/app/image?file=bsi.png" alt="百菌探" title="百菌探">
                    </div>
                  </a>
                </li>
                <li ng-if="userProduct.app136==136">
                  <a href="${pageContext.request.contextPath }/index#/product/bactive/report/136/1/20/all/all/all/all/all/0/asc/asc/asc/desc">
                    <div class="inner">
                      <img src="${pageContext.request.contextPath }/app/image?file=bsi.png" alt="百菌探" title="百菌探">
                    </div>
                  </a>
                </li>
                <li ng-if="userProduct.app137==137">
                  <a href="${pageContext.request.contextPath }/index#/product/bactive/report/137/1/20/all/all/all/all/all/0/asc/asc/asc/desc">
                    <div class="inner">
                      <img src="${pageContext.request.contextPath }/app/image?file=bsi.png" alt="百菌探" title="百菌探">
                    </div>
                  </a>
                </li>
			    <li ng-if="userProduct.app123==123">
			      <a href="${pageContext.request.contextPath }/index#/product/rocky/upload/2" ng-if="userProduct.rockyupload==true">
			        <div class="inner">
			          <img src="${pageContext.request.contextPath }/app/image?file=rocky.png" alt="华木兰" title="华木兰">
			        </div>
			      </a>
			      <a href="${pageContext.request.contextPath }/index#/product/rocky/report" ng-if="userProduct.rockyreport==true">
			        <div class="inner">
			          <img src="${pageContext.request.contextPath }/app/image?file=rocky.png" alt="华木兰" title="华木兰">
			        </div>
			      </a>
			    </li>
			    <li ng-if="userProduct.app280==280">
                  <a href="${pageContext.request.contextPath }/index#/product/rocky/upload/187" ng-if="userProduct.rockyupload==true">
                    <div class="inner">
                      <img src="${pageContext.request.contextPath }/app/image?file=rocky.png" alt="华木兰-白金" title="华木兰-白金">
                    </div>
                  </a>
                  <a href="${pageContext.request.contextPath }/index#/product/rocky/report" ng-if="userProduct.rockyreport==true">
                    <div class="inner">
                      <img src="${pageContext.request.contextPath }/app/image?file=rocky.png" alt="华木兰-白金" title="华木兰-白金">
                    </div>
                  </a>
                </li>
			  </ul>
		    </div>
		  </section>
	  </div>
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
		          <shiro:hasPermission name="data:manage">
		              <a href="#/data"><span>详情</span><i class="next-icon"></i></a>
		          </shiro:hasPermission>
	            </div>
	          </div>
	        </li>
	        <li>
	          <div class="inner datasize">
	            <i class="pie-icon-lg"></i>
	            <h1>{{map.sumData}}<span>{{map.format}}</span></h1>
	            <div class="footer">
	              <span>数据大小</span>
	              <shiro:hasPermission name="data:manage">
		              <a href="#/data"><span>详情</span><i class="next-icon"></i></a>
	              </shiro:hasPermission>
	            </div>
	          </div>
	        </li>
	        <li>
	          <div class="inner appnum">
	            <i class="app-icon-lg"></i>
	            <h1>{{map.countApp}}<span>个</span></h1>
	            <div class="footer">
	              <span>产品总量</span>
<%-- 	              <shiro:hasPermission name="application:center"> --%>
		              <a href="#/app"><span>详情</span><i class="next-icon"></i></a>
<%-- 	              </shiro:hasPermission> --%>
	            </div>
	          </div>
	        </li>
	        <li>
	          <div class="inner reportnum">
	            <i class="report-icon-lg"></i>
	            <h1>{{map.countReport}}<span>个</span></h1>
	            <div class="footer">
	              <span>报告数量</span>
	              <shiro:hasPermission name="project:report">
	              	<a href="<%=request.getContextPath()%>/index#/reportpro/1/10/1/0/all/all/0/all"><span>详情</span><i class="next-icon"></i></a>
		          </shiro:hasPermission>
		          <shiro:hasPermission name="data:report">
	              	<a href="<%=request.getContextPath()%>/index#/reportdata/1/20/0/all/all/all/all/all/all"><span>详情</span><i class="next-icon"></i></a>
		          </shiro:hasPermission>
	            </div>
	          </div>
	        </li>
	       <shiro:hasPermission name="cost:center">
	        <li>
	          <div class="inner expense">
	            <i class="money-icon-lg"></i>
	            <h1>{{map.countExpense}}<span>元</span></h1>
	            <div class="footer">
	              <span>消费总额</span>
	              <a href="#/expense/consume"><span>详情</span><i class="next-icon"></i></a>
	            </div>
	          </div>
	        </li>
	       </shiro:hasPermission>
	      </ul>
	    </div>
	    <div class="o-count-detail row">
	      <div class="col-xs-12 col-md-6">
	        <div class="count-box">
	          <div class="count-box-head">
	            <h5><i class="bar-icon"></i><span>数据总量</span></h5>
	            <div class="switch-btn-group pull-right">
	              <a><span class="s-btn active" id="data-day-span" onclick="userCount.dayMonthSwitch('data-day','data-month')">日</span></a>
	              <a><span class="s-btn" id="data-month-span" onclick="userCount.dayMonthSwitch('data-month','data-day')">月</span></a>
	            </div>
	          </div>
	          <div class="count-box-body" id="count-data-day-chart" style="min-width: 100px;">
	          </div>
	          <div class="count-box-body hide" id="count-data-month-chart">
	          </div>
	        </div>
	        <div class="count-box">
	          <div class="count-box-head">
	            <h5><i class="report-icon-min"></i><span>报告总量</span></h5>
	            <div class="switch-btn-group pull-right">
	              <a><span class="s-btn active" id="report-day-span" onclick="userCount.dayMonthSwitch('report-day','report-month')">日</span></a>
	              <a><span class="s-btn" id="report-month-span" onclick="userCount.dayMonthSwitch('report-month','report-day')">月</span></a>
	            </div>
	          </div>
	          <div class="count-box-body" id="count-report-day-chart" style="min-width: 100px;">
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
		              <a><span class="s-btn active" id="source-day-span" onclick="userCount.dayMonthSwitch('source-day','source-month')">日</span></a>
		              <a><span class="s-btn" id="source-month-span" onclick="userCount.dayMonthSwitch('source-month','source-day')">月</span></a>
		            </div>
		          </div>
		          <div class="count-box-body" id="count-source-day-chart" style="min-width: 100px;">
		          </div>
		          <div class="count-box-body hide" id="count-source-month-chart">
		          </div>
		      </div>
		      <div class="count-box">
		        <div class="count-box-head">
		            <h5><i class="app-icon-min"></i><span>已添加APP</span></h5>
		            <div class="switch-btn-group pull-right">
		              <a><span class="s-btn active" id="app-day-span" onclick="userCount.dayMonthSwitch('app-day','app-month')">日</span></a>
		              <a><span class="s-btn" id="app-month-span" onclick="userCount.dayMonthSwitch('app-month','app-day')">月</span></a>
		            </div>
		          </div>
		          <div class="count-box-body" id="count-app-day-chart" style="min-width: 100px;">
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
