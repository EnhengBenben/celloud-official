<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
  <link href="<%=request.getContextPath()%>/less/celloud.less" rel="stylesheet/less" type="text/css" />
  <script src="//cdn.bootcss.com/less.js/2.7.1/less.min.js"></script>
  <script src="//cdn.bootcss.com/modernizr/2.8.3/modernizr.min.js"></script>
  <!-- [if It IE 9]>
  <script src="//cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
  <![endif]  -->
</head>
<body class="container" ng-app="celloudApp">
  <header class="header">
    <nav class="navbar navbar-default navbar-fixed-top">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <div class="navbar-logo">
	        <a class="logo" href="#"></a>
	      </div>
	    </div>
	    <ul class="nav navbar-nav pull-left">
	      <li><a class="" href="#"><i class="cubes-icon">&nbsp;</i></a></li>
	      <li><a class="" href="#"><i class="upload-icon">&nbsp;</i></a></li>
	    </ul>
	    <ul class="nav navbar-nav pull-right">
	      <li><a href="#"><i class="code-icon">&nbsp;</i></a></li>
	      <li><a href="#"><i class="bell-icon">&nbsp;</i></a></li>
	      <li><a href="#"><i class="money-icon">&nbsp;</i></a></li>
	      <li class="dropdown">
	        <a href="#" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
	          <i class="user-icon">&nbsp;</i>
	        </a>
	        <ul class="dropdown-menu">
              <li><a href="#">Action</a></li>
              <li><a href="#">Another action</a></li>
              <li><a href="#">Something else here</a></li>
              <li role="separator" class="divider"></li>
              <li><a href="#">Separated link</a></li>
            </ul>
	      </li>
	    </ul>
	  </div>
	</nav>
  </header>
  <aside class="sidebar">
    <section class="s-bar">
      <div class="sidebar-collapse">
        <a href="#"><i class="left-indent-icon"></i></a>
      </div>
      <ul class="sidebar-menu">
        <li class="header">产品与服务</li>
        <li class="treeview">
          <a href="javascript:void(0)"><i class="overview-icon"></i><span>我的工作台</span></a>
        </li>
        <li class="treeview">
          <a href="javascript:void(0)"><i class="sample-icon"></i><span>样本采集</span></a>
        </li>
        <li class="treeview">
          <a href="javascript:void(0)"><i class="experiment-icon"></i><span>实验管理</span></a>
        </li>
        <li class="treeview">
          <a href="javascript:void(0)"><i class="data-icon"></i><span>数据管理</span></a>
        </li>
        <li class="treeview">
          <a href="javascript:void(0)"><i class="report-icon"></i><span>报告管理</span></a>
        </li>
        <li class="treeview">
          <a href="javascript:void(0)"><i class="app-icon"></i><span>应用市场</span></a>
        </li>
      </ul>
      <ul class="sidebar-menu">
        <li class="header">用户中心</li>
        <li class="treeview">
          <a href="javascript:void(0)"><i class="account-icon"></i><span>账号管理</span></a>
        </li>
        <li class="treeview">
          <a href="javascript:void(0)"><i class="qa-icon"></i><span>问题反馈</span></a>
        </li>
        <li class="treeview">
          <a href="javascript:void(0)"><i class="cost-icon"></i><span>费用中心</span></a>
        </li>
      </ul>
    </section>
  </aside>
  <div ng-view class="view-container"></div>
  <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
  <script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="//cdn.bootcss.com/spin.js/2.3.2/spin.min.js"></script>
  <script src="//cdn.bootcss.com/echarts/3.2.2/echarts.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular-route.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular-resource.min.js"></script>
  
<%--   <script src="<%=request.getContextPath()%>/js/config/route.js"></script> --%>
  <script src="<%=request.getContextPath()%>/js/config/routeProvider.js"></script>
  <script src="<%=request.getContextPath()%>/js/application.js"></script>
</body>
</html>