<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="<%=request.getContextPath()%>/css/count.css" rel="stylesheet" type="text/css" />
<section class="content-header">
  <h1>
    <small>&nbsp;</small>
  </h1>
  <ol class="breadcrumb">
    <li><a href="javascript:void(0)"><i class="fa fa-dashboard"></i> 总览</a></li>
    <li class="active">全部</li>
  </ol>
</section>
<section class="content">
  <!-- Small boxes (Stat box) -->
  <div class="row nav-tabs">
  	<div class="col-lg-3 col-xs-6" data-toggle="tab" href="#count-data-charts">
      <!-- small box -->
      <div class="small-box bg-green">
        <div class="inner">
          <h3>${countData }<sup style="font-size: 16px">个</sup></h3>
          <p>数据总量</p>
        </div>
        <div class="icon">
          <i class="fa fa-list"></i>
        </div>
        <a href="javascript:void(0)" onClick="userCount.toDataMain()" class="small-box-footer">详情 <i class="fa fa-arrow-circle-right"></i></a>
      </div>
    </div><!-- ./col -->
    <div class="col-lg-3 col-xs-6" data-toggle="tab" href="#count-source-charts">
      <!-- small box -->
      <div class="small-box bg-aqua">
        <div class="inner">
          <h3><c:choose><c:when test="${sumData>1073741824 }"><fmt:formatNumber pattern="0.00" value="${sumData/1073741824 }"/><sup style="font-size: 16px">GB</sup></c:when><c:when test="${sumData>1048576 }"><fmt:formatNumber pattern="0.00" value="${sumData/1048576 }"/><sup style="font-size: 16px">MB</sup></c:when><c:otherwise><fmt:formatNumber pattern="0.00" value="${sumData/1024 }"/><sup style="font-size: 16px">KB</sup></c:otherwise></c:choose></h3>
          <p>总资源占用</p>
        </div>
        <div class="icon">
          <i class="fa fa-pie-chart"></i>
        </div>
        <a href="javascript:void(0)" onClick="userCount.toDataMain()" class="small-box-footer">详情 <i class="fa fa-arrow-circle-right"></i></a>
      </div>
    </div><!-- ./col -->
    <div class="col-lg-3 col-xs-6" data-toggle="tab" href="#count-report-charts">
      <!-- small box -->
      <div class="small-box bg-yellow">
        <div class="inner">
          <h3><c:choose><c:when test="${not empty countReport }">${countReport }</c:when><c:otherwise>0</c:otherwise></c:choose>
          <sup style="font-size: 16px">个</sup></h3>
          <p>报告总量</p>
        </div>
        <div class="icon">
          <i class="fa fa-file-text-o"></i>
        </div>
        <a href="javascript:void(0);" onClick="userCount.toReportMain()" class="small-box-footer">详情 <i class="fa fa-arrow-circle-right"></i></a>
      </div>
    </div><!-- ./col -->
    <div class="col-lg-3 col-xs-6" data-toggle="tab" href="#count-app-charts">
      <!-- small box -->
      <div class="small-box bg-red">
        <div class="inner">
          <h3>${countApp }<sup style="font-size: 16px">个</sup></h3>
          <p>已添加APP</p>
        </div>
        <div class="icon">
          <i class="fa fa-cube"></i>
        </div>
        <a href="javascript:void(0);" onClick="userCount.toAppStore()" class="small-box-footer">详情 <i class="fa fa-arrow-circle-right"></i></a>
      </div>
    </div><!-- ./col -->
  </div><!-- /.row -->
  <!-- Main row -->
  <div class="row">
    <!-- Left col -->
     <section class="col-lg-6 connectedSortable tab-pane active" id="count-data-charts">
       <!-- Custom tabs (Charts with tabs)-->
       <div class="nav-tabs-custom">
         <!-- Tabs within a box -->
         <ul class="nav nav-tabs pull-right line-charts-nav">
           <li class="active"><a href="#count-data-day-chart" data-toggle="tab">每天</a></li>
           <li><a href="#count-data-month-chart" data-toggle="tab">每月</a></li>
           <li class="pull-left header"><i class="fa fa-list"></i> 数据量</li>
         </ul>
         <div class="tab-content no-padding">
           <!-- Morris chart - Sales -->
           <div class="chart tab-pane active" id="count-data-day-chart" style="height: 300px;"></div>
           <div class="chart tab-pane" id="count-data-month-chart" style="height: 300px;"></div>
         </div>
       </div><!-- /.nav-tabs-custom -->
	</section><!-- /.Left col -->
	<!-- Left col -->
     <section class="col-lg-6 connectedSortable tab-pane" id="count-source-charts">
       <!-- Custom tabs (Charts with tabs)-->
       <div class="nav-tabs-custom">
         <!-- Tabs within a box -->
         <ul class="nav nav-tabs pull-right line-charts-nav">
           <li class="active"><a href="#count-source-day-chart" data-toggle="tab">每天</a></li>
           <li><a href="#count-source-month-chart" data-toggle="tab">每月</a></li>
           <li class="pull-left header"><i class="fa fa-pie-chart"></i> 总资源占用</li>
         </ul>
         <div class="tab-content no-padding">
           <!-- Morris chart - Sales -->
           <div class="chart tab-pane active" id="count-source-day-chart" style="height: 300px;"></div>
           <div class="chart tab-pane" id="count-source-month-chart" style="height: 300px;"></div>
         </div>
       </div><!-- /.nav-tabs-custom -->
    </section><!-- /.Left col -->
    <!-- Left col -->
     <section class="col-lg-6 connectedSortable tab-pane" id="count-report-charts">
       <!-- Custom tabs (Charts with tabs)-->
       <div class="nav-tabs-custom">
         <!-- Tabs within a box -->
         <ul class="nav nav-tabs pull-right line-charts-nav">
           <li class="active"><a href="#count-report-day-chart" data-toggle="tab">每天</a></li>
           <li><a href="#count-report-month-chart" data-toggle="tab">每月</a></li>
           <li class="pull-left header"><i class="fa fa-file-text-o"></i> 报告量</li>
         </ul>
         <div class="tab-content no-padding">
           <!-- Morris chart - Sales -->
           <div class="chart tab-pane active" id="count-report-day-chart" style="height: 300px;"></div>
           <div class="chart tab-pane" id="count-report-month-chart" style="height: 300px;"></div>
         </div>
       </div><!-- /.nav-tabs-custom -->
    </section><!-- /.Left col -->
    <!-- Left col -->
     <section class="col-lg-6 connectedSortable tab-pane" id="count-app-charts">
       <!-- Custom tabs (Charts with tabs)-->
       <div class="nav-tabs-custom">
         <!-- Tabs within a box -->
         <ul class="nav nav-tabs pull-right line-charts-nav">
           <li class="active"><a href="#count-app-day-chart" data-toggle="tab">每天</a></li>
           <li><a href="#count-app-month-chart" data-toggle="tab">每月</a></li>
           <li class="pull-left header"><i class="fa fa-cube"></i> 已添加APP</li>
         </ul>
         <div class="tab-content no-padding">
           <!-- Morris chart - Sales -->
           <div class="chart tab-pane active" id="count-app-day-chart" style="height: 300px;"></div>
           <div class="chart tab-pane" id="count-app-month-chart" style="height: 300px;"></div>
         </div>
       </div><!-- /.nav-tabs-custom -->
    </section><!-- /.Left col -->
   </div><!--/.Main row-->
</section><!-- /.content -->
