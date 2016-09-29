<%@page import="com.celloud.model.mysql.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>CelLoud 控制台</title>
  <meta name="keywords" content="上海华点云生物科技有限公司,celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据,上海华点云生物科技有限公司网站首页,上海华点云生物科技有限公司官网,上海华点云生物科技有限公司北京分公司" />
  <meta name="description" content="一站式高通量基因检测数据分析系统" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <link href="<%=request.getContextPath()%>/images/favicon.ico" rel="bookmark">
  <link href="<%=request.getContextPath()%>/images/favicon.ico" rel="shortcut icon">
  <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
  <link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">
  <link href="//cdn.bootcss.com/select2/4.0.3/css/select2.min.css" rel="stylesheet">
  <link href="<%=request.getContextPath()%>/plugins/intro/introjs.css" rel="stylesheet" type="text/css" />
  <link href="<%=request.getContextPath()%>/css/celloud.min.css?v=3.3.22" rel="stylesheet">
<%--   <link href="<%=request.getContextPath()%>/less/celloud.less" rel="stylesheet/less" type="text/css" /> --%>
<!--   <script src="//cdn.bootcss.com/less.js/2.7.1/less.min.js"></script> -->
  <link href="<%=request.getContextPath()%>/plugins/smartJqueryZoom/zoom-styles.css" rel="stylesheet" type="text/css"/>
  <script src="//cdn.bootcss.com/modernizr/2.8.3/modernizr.min.js"></script>
  <!-- [if It IE 9]>
  <script src="//cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
  <![endif]  -->
</head>
<body ng-app="celloudApp" ng-controller="sidebarController">
  <div class="container">
	<!--   <ng-include src="'pages/partial/_partial_index_header.jsp'"></ng-include> -->
	<!--   <ng-include src="'pages/partial/_partial_index_sidebar.jsp'"></ng-include> -->
	  <jsp:include page="partial/_partial_index_header.jsp"></jsp:include>
	  <jsp:include page="partial/_partial_index_sidebar.jsp"></jsp:include>
	  <div ng-view class="view-container"></div>
	  <div id="tips-modal" class="modal tips-modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	    <div class="modal-dialog modal-sm">
	      <div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
	          <h4 class="modal-title">提示</h4>
	        </div>
	        <div class="modal-body">
	          <h5><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>{{errorInfo}}</h5>
	        </div>
	      </div><!-- /.modal-content -->
	    </div><!-- /.modal-dialog -->
	  </div><!-- /.modal -->
	  <!-- 放大图片所需的div -->
	  <div id="fullbg"></div> 
	  <div id="pageContent" class="pageContent">
		<a class="zoomClose" id="closeZoom" ng-click="closeZoom();" style="margin-right: 75px;"></a>
		<img id="imageFullScreen" src="">
	  </div>
      <div id="alerts" class="alerts"></div>
      <ng-include src="'pages/partial/_partial_upload_modal.jsp'"></ng-include>
  </div>
  <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
  <script src="<%=request.getContextPath()%>/plugins/intro/intro.js?version=1.1"></script>
  <script type="text/javascript">
     window.CONTEXT_PATH = '<%=request.getContextPath()%>';
     window.companyId = '<%=((User)request.getSession().getAttribute("loginUserInSession")).getCompanyId() %>';
  </script>
  <script src="<%=request.getContextPath()%>/plugins/sockjs-modified-1.0.0.js"></script>
  <script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="//cdn.bootcss.com/spin.js/2.3.2/spin.min.js"></script>
  <script src="//cdn.bootcss.com/echarts/3.2.2/echarts.min.js"></script>
  <script src="//cdn.bootcss.com/select2/4.0.3/js/select2.full.min.js"></script>
  <script src="//cdn.bootcss.com/select2/4.0.3/js/i18n/zh-CN.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular-route.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular-resource.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular-sanitize.min.js"></script>
  <script src="//cdn.bootcss.com/echarts/2.2.7/echarts.js"></script>
  <script src="//cdn.bootcss.com/plupload/2.1.8/plupload.full.min.js"></script>
  <script src="<%=request.getContextPath() %>/plugins/smartJqueryZoom/e-smart-zoom-jquery.min.js"></script>
  <script src="<%=request.getContextPath() %>/plugins/jquery.ba-resize.min.js"></script>
  <script src="<%=request.getContextPath()%>/plugins/calendar/WdatePicker.js"></script>
  <script src="<%=request.getContextPath()%>/plugins/highcharts/char.js"></script>
  <script src="<%=request.getContextPath()%>/js/utils.js"></script>
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
  <script src="<%=request.getContextPath()%>/js/app/controller.js?v=3.3.1.1"></script>
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
  <script src="<%=request.getContextPath()%>/js/report/controller.js?v=3.3.3.4"></script>
  <script src="<%=request.getContextPath()%>/js/report/dataReportService.js"></script>
  <script src="<%=request.getContextPath()%>/js/report/dataReportController.js"></script>
  <script src="<%=request.getContextPath()%>/js/config/routeProvider.js?v=3.3.2"></script>
  <script src="<%=request.getContextPath()%>/js/common/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/common/controller.js?v=3.3.3.0"></script>
  <script src="<%=request.getContextPath()%>/js/common/filter.js"></script>
  <script src="<%=request.getContextPath()%>/js/overview/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/overview/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/overview/userCount.js"></script>
  <script src="<%=request.getContextPath()%>/js/notice/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/notice/messageController.js"></script>
  <script src="<%=request.getContextPath()%>/js/notice/noticeController.js"></script>
  <script src="<%=request.getContextPath()%>/js/feedback/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/feedback/controller.js"></script>
<%--   <script src="<%=request.getContextPath()%>/js/main.min.js"></script> --%>
  <script type="text/javascript">
	  window.navigation = '<%=((User)request.getSession().getAttribute("loginUserInSession")).getNavigation() %>';
  </script>
</body>
</html>