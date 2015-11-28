<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="<%=request.getContextPath() %>/css/report.css?v=1.0" rel="stylesheet" type="text/css" />
<section class="content-header">
  <h1>
    <small><a href="report.html">返回</a></small>
  </h1>
  <ol class="breadcrumb">
    <li><a href="#"><i class="fa fa-dashboard"></i> 报告</a></li>
    <li class="active">数据报告</li>
  </ol>
</section>
<section class="content">
  <div class="row">
    <div class="col-xs-10">
      <div class="box box-success color-palette-box" id="reportResultDiv">
      
      </div>
    </div>
    <div class="col-xs-2">
     <div class="btn-group-vertical" id="fileListUl">
      </div>
    </div>
  </div>
</section>
