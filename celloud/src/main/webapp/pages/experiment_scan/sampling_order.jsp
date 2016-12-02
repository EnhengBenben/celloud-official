<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="baidu-site-verification" content="IsldTuHqik" />
<title>样本寄送订单</title>
<meta name="keywords" content="上海华点云生物科技有限公司,celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据" />
<meta name="description" content="一站式高通量基因检测数据分析系统">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="bookmark" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link href="//cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
<%-- <link href="<%=request.getContextPath()%>/css/celloud.min.css" rel="stylesheet"> --%>
<link href="<%=request.getContextPath()%>/css/print.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/print_bsi.css" rel="stylesheet">
<script src="//cdn.bootcss.com/modernizr/2.8.3/modernizr.min.js"></script>
  <!-- [if It IE 9]>
  <script src="//cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
  <![endif]  -->
</head>
<body ng-app="celloudApp">
<div class="content" ng-view></div>
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/plugins/intro/intro.js?version=1.1"></script>
<script type="text/javascript">
   window.CONTEXT_PATH = '<%=request.getContextPath()%>';
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
<script src="<%=request.getContextPath()%>/plugins/Lodop/LodopFuncs.js?v=3.3.4"></script>
<script src="<%=request.getContextPath()%>/js/main.min.js?v=3.3.10.1"></script>
</body>
</html>