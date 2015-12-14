<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
  <div class="row">
  	<div class="col-lg-3 col-xs-6">
      <!-- small box -->
      <div class="small-box bg-green">
        <div class="inner">
          <h3>${countData }<sup style="font-size: 16px">个</sup></h3>
          <p>数据总量</p>
        </div>
        <div class="icon">
          <i class="fa fa-list"></i>
        </div>
<!--         <a href="javascript:void(0)" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a> -->
      </div>
    </div><!-- ./col -->
    <div class="col-lg-3 col-xs-6">
      <!-- small box -->
      <div class="small-box bg-aqua">
        <div class="inner">
          <h3><c:choose><c:when test="${sumData>1073741824 }"><fmt:formatNumber pattern="0.00" value="${sumData/1073741824 }"/><sup style="font-size: 16px">GB</sup></c:when><c:when test="${sumData>1048576 }"><fmt:formatNumber pattern="0.00" value="${sumData/1048576 }"/><sup style="font-size: 16px">MB</sup></c:when><c:otherwise><fmt:formatNumber pattern="0.00" value="${sumData/1024 }"/><sup style="font-size: 16px">KB</sup></c:otherwise></c:choose></h3>
          <p>总资源占用</p>
        </div>
        <div class="icon">
          <i class="fa fa-pie-chart"></i>
        </div>
        <!-- <a href="javascript:void(0)" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a> -->
      </div>
    </div><!-- ./col -->
    <div class="col-lg-3 col-xs-6">
      <!-- small box -->
      <div class="small-box bg-yellow">
        <div class="inner">
          <h3>${countReport }<sup style="font-size: 16px">个</sup></h3>
          <p>报告总量</p>
        </div>
        <div class="icon">
          <i class="fa fa-file-text-o"></i>
        </div>
        <!-- <a href="javascript:void(0)" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a> -->
      </div>
    </div><!-- ./col -->
    <div class="col-lg-3 col-xs-6">
      <!-- small box -->
      <div class="small-box bg-red">
        <div class="inner">
          <h3>${countApp }<sup style="font-size: 16px">个</sup></h3>
          <p>已添加APP</p>
        </div>
        <div class="icon">
          <i class="fa fa-cube"></i>
        </div>
        <!-- <a href="javascript:void(0)" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a> -->
      </div>
    </div><!-- ./col -->
  </div><!-- /.row -->
  <!-- Main row -->
  <div class="row">
    <!-- Left col -->
     <section class="col-lg-12 connectedSortable">
       <!-- Custom tabs (Charts with tabs)-->
       <div class="nav-tabs-custom">
         <!-- Tabs within a box -->
         <ul class="nav nav-tabs pull-right">
           <li class="active"><a href="#day-chart" data-toggle="tab">每天</a></li>
           <li><a href="#month-chart" data-toggle="tab">每月</a></li>
           <li class="pull-left header"><i class="fa fa-inbox"></i> 数据量</li>
         </ul>
         <div class="tab-content no-padding">
           <!-- Morris chart - Sales -->
           <div class="chart tab-pane active" id="day-chart" style="position: relative; height: 300px;width: 1165px;"></div>
           <div class="chart tab-pane" id="month-chart" style="position: relative; height: 300px;width:1165px;"></div>
         </div>
       </div><!-- /.nav-tabs-custom -->
	</section><!-- /.Left col -->
   </div><!--/.Main row-->
</section><!-- /.content -->
