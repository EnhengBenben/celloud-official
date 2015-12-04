<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="<%=request.getContextPath() %>/css/report.css?version=3,0" rel="stylesheet">
<link href="<%=request.getContextPath() %>/css/buttons.css?version=20150730" rel="stylesheet">
<link href="<%=request.getContextPath() %>/plugins/font-awesome-4.4.0/css/font-awesome.min.css" rel="stylesheet"/>
<link href="<%=request.getContextPath() %>/plugins/select/select2.css" rel="stylesheet"/>
<link href="<%=request.getContextPath() %>/plugins/backToTop/toTop.1.0.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<section class="content-header">
  <h1>
    <small>&nbsp;</small>
  </h1>
  <ol class="breadcrumb">
    <li><a href="javascript:void(0)"><i class="fa fa-files-o"></i> 报告</a></li>
    <li class="active">数据报告</li>
    <li><a href="javascript:void(0)" onclick="showReport()">返回</a></li>
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
<script src="<%=request.getContextPath() %>/plugins/backToTop/toTop.1.0.js"></script>