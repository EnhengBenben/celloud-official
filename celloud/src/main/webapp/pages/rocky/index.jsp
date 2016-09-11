<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 优先使用最新版本的IE 和 Chrome 内核 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="baidu-site-verification" content="IsldTuHqik" />
<title>CelLoud</title>
<meta name="keywords" content="上海华点云生物科技有限公司,celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据,上海华点云生物科技有限公司网站首页,上海华点云生物科技有限公司官网,上海华点云生物科技有限公司北京分公司" />
<meta name="description" content="一站式高通量基因检测数据分析系统" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="bookmark" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
<link href="//cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/less/celloud.less" rel="stylesheet/less" type="text/css" />
<link href="<%=request.getContextPath()%>/css/less/main.less" rel="stylesheet/less" type="text/css" />
<script src="//cdn.bootcss.com/less.js/2.7.1/less.min.js"></script>
<script src="//cdn.bootcss.com/modernizr/2.8.3/modernizr.min.js"></script>
<!-- [if It IE 9]>
<script src="//cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]  -->
</head>
<body class="container" ng-app="celloudApp" ng-controller="sidebarController">
  <jsp:include page="../partial/_partial_index_header.jsp"></jsp:include>
  <jsp:include page="../partial/_partial_index_sidebar.jsp"></jsp:include>
  <div class="view-container">
    <div class="pro-body">
      <ol class="breadcrumb">
        <li>CelLoud</li>
        <li>我的产品</li>
        <li>华木兰</li>
      </ol>
      <div class="content rocky">
			<header class="common-menu">
				<div class="rocky-logo">
					<img alt="华木兰" src="<%=request.getContextPath()%>/images/app/breast_mulations_scan.png">
				</div>
				<hr class="-left">
				<div id="common-menu-center" class="info"></div>
				<div id="common-menu-right" class="searchs"></div>
				<hr class="-right">
			</header>
			<aside class="common-sidebar">
				<ul id="common-menu" class="menu">
					<li class="treeview">产品功能</li>
<!-- 					<li class="treeview -active"> -->
<!-- 						<a id="to-sample-a" href="javascript:void(0)" data-menu="收样"> -->
<!-- 							<i class="celicon -sample"></i>收样 -->
<!-- 						</a> -->
<!-- 					</li> -->
					<li class="treeview -active">
						<a id="to-upload-a" href="javascript:void(0)" data-menu="上传">
							<i class="celicon -upload"></i> 上传
						</a>
					</li>
					<li class="treeview">
						<a id="to-report-a" href="javascript:void(0)" data-menu="报告">
							<i class="celicon -report"></i> 报告
						</a>
					</li>
				</ul>
			</aside>
			<div id="container" class="common-container"></div>
			<div id="upload-container" class="common-container hide">
				<jsp:include page="upload/upload.jsp"></jsp:include>
			</div>
	    </div>
	</div>
  <div id="fullbg"></div> 
  <div id="pageContent" class="pageContent hide">
    <a class="zoomClose" id="closeZoom" ng-click="closeZoom();" style="margin-right: 75px;"></a>
    <img id="imageFullScreen" src="">
  </div>
  <script type="text/javascript">
	window.contextPath = '<%=request.getContextPath()%>';
  </script>
  <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
  <script src="<%=request.getContextPath()%>/plugins/sockjs-modified-1.0.0.js"></script>
  <script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="//cdn.bootcss.com/spin.js/2.3.2/spin.min.js"></script>
  <script src="//cdn.bootcss.com/echarts/3.2.2/echarts.min.js"></script>
  <script src="//cdn.bootcss.com/select2/4.0.3/js/select2.full.min.js" type="text/javascript"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular-route.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular-resource.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular-sanitize.min.js"></script>
  <script src="//cdn.bootcss.com/echarts/2.2.7/echarts.js"></script>
  <script src="//cdn.bootcss.com/plupload/2.1.8/plupload.full.min.js"></script>
  <script src="<%=request.getContextPath() %>/plugins/jquery.ba-resize.min.js"></script>
  <script src="<%=request.getContextPath() %>/plugins/smartJqueryZoom/e-smart-zoom-jquery.min.js"></script>
  <script src="<%=request.getContextPath()%>/js/utils.js"></script>
  <script src="<%=request.getContextPath()%>/plugins/calendar/WdatePicker.js"></script>
  <script src="<%=request.getContextPath()%>/plugins/highcharts/char.js"></script>
  <script src="<%=request.getContextPath()%>/js/charts.js"></script>
  <script src="<%=request.getContextPath()%>/js/report_codon.js"></script>
  <script src="<%=request.getContextPath()%>/js/message.js"></script>
  <script src="<%=request.getContextPath()%>/js/alert.js"></script>
  <script src="<%=request.getContextPath()%>/js/confirm.js"></script>
  <script src="<%=request.getContextPath()%>/js/application.js"></script>
  <script src="<%=request.getContextPath()%>/js/directive/href.js"></script>
  <script src="<%=request.getContextPath()%>/js/directive/pagination.js"></script>
  <script src="<%=request.getContextPath()%>/js/upload/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/upload/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/app/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/app/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/expense/filter.js"></script>
  <script src="<%=request.getContextPath()%>/js/expense/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/expense/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/experiment_scan/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/experiment_scan/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/user/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/user/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/data/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/data/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/data/data.js"></script>
  <script src="<%=request.getContextPath()%>/js/report/filter.js"></script>
  <script src="<%=request.getContextPath()%>/js/report/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/report/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/report/dataReportService.js"></script>
  <script src="<%=request.getContextPath()%>/js/report/dataReportController.js"></script>
  <script src="<%=request.getContextPath()%>/js/config/routeProvider.js"></script>
  <script src="<%=request.getContextPath()%>/js/common/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/common/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/common/filter.js"></script>
  <script src="<%=request.getContextPath()%>/js/overview/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/overview/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/overview/userCount.js"></script>
  <script src="<%=request.getContextPath()%>/js/notice/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/notice/messageController.js"></script>
  <script src="<%=request.getContextPath()%>/js/notice/noticeController.js"></script>
  <script src="<%=request.getContextPath()%>/js/feedback/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/feedback/controller.js"></script>
  
	<script src="<%=request.getContextPath()%>/js/base.js?v=3.2.4" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/rocky_main.js?v=3.2.4" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/rocky_upload.js?v=3.2.4"></script>
	<script src="<%=request.getContextPath()%>/js/rocky_report.js?v=3.2.4"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/rocky_data.js"></script>
</body>
</html>