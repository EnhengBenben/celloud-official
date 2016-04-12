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
<title>CelLoud 控制台</title>
<meta name="keywords" content="上海华点云生物科技有限公司,celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据" />
<meta name="description" content="一站式高通量基因检测数据分析系统">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="bookmark" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link href="//cdn.bootcss.com/Buttons/2.0.0/css/buttons.min.css" rel="stylesheet">
<link href="//cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/plugins/select/select2.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/plugins/intro/introjs.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/font.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/app.css?version=1.0" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.css" rel="stylesheet"
	type="text/css" media="screen" />
<link href="<%=request.getContextPath()%>/plugins/backToTop/toTop.1.0.css" rel="stylesheet">
</head>
<body class="skin-green sidebar-mini">
	<div class="fixed wrapper">
		<header class="main-header">
			<a href="javascript:void(0)" class="logo">
				<span class="logo-mini">
					<img src="<%=request.getContextPath()%>/images/icon/mini-logo.png">
				</span>
				<span class="logo-lg">
					<img src="<%=request.getContextPath()%>/images/icon/logo.png">
				</span>
			</a>
			<!-- Header Navbar: style can be found in header.less -->
			<nav class="navbar navbar-static-top">
				<!-- Sidebar toggle button-->
				<a href="#" class="sidebar-toggle" data-toggle="offcanvas">
					<span class="sr-only">Toggle navigation</span>
				</a>
				<div class="navbar-custom-menu">
					<ul class="nav navbar-nav">
						<!-- Messages: style can be found in dropdown.less-->
						<li class="dropdown messages-menu expense-menu">
							<a id="to-expense-model" href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
								<i class="fa fa-rmb"></i>
								<span class="hidden-xs">消费记录</span>
							</a>
							<div class="dropdown-menu">
								<div class="dropdown-menu-inner">
									<p>
										总消费金额: <small class="pull-right"><span id="total-consumption"></span>C</small>
									</p>
									<div class="text-center">
										<a id="to-expense-main" class="btn btn-sm btn-celloud-success btn-flat" href="javascript:void(0)">查看详情</a>
									</div>
								</div>
							</div>
						</li>
						<li class="dropdown user user-menu">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								<img src="${sessionScope.loginUserInSession.avatar}" class="user-image" alt="User Image" id="userImageSmall" />
								<span class="hidden-xs">${sessionScope.loginUserInSession.username }</span>
							</a>
							<ul class="dropdown-menu">
								<li class="user-header">
									<img src="${sessionScope.loginUserInSession.avatar}" class="img-circle" alt="User Image" id="userImageLarge">
									<p>
										CelLoud <small>您身边的基因数据分析云平台</small>
									</p>
								</li>
								<li class="user-footer">
									<div class="pull-left">
										<a href="javascript:void(0)" onclick="showUser()" class="btn btn-default btn-flat">个人信息</a>
									</div>
									<div class="pull-right">
										<a href="logout" class="btn btn-default btn-flat">退出</a>
									</div>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</nav>
		</header>
		<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar">
			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">
				<!-- sidebar menu: : style can be found in sidebar.less -->
				<ul class="sidebar-menu">
					<li class="header">
						<span>产品和服务</span>
					</li>
					<li class="active treeview">
						<a href="javascript:void(0)" onclick="userCount.showUserCount();">
							<i class="fa fa-dashboard"></i>
							<span>总览</span>
						</a>
					</li>
					<shiro:hasPermission name="experiment:menu">
						<li class="treeview">
							<a href="javascript:void(0)" onclick="showExperiment();">
								<i class="fa fa-stack-overflow"></i>
								<span>实验管理</span>
							</a>
						</li>
					</shiro:hasPermission>
					<li class="treeview" id="toUploadMenu" data-step="4" data-position="right" data-intro="" data-img="toupload.png">
						<a href="javascript:void(0)" onclick="showUpload();">
							<i class="fa fa-sellsy"></i>
							<span>数据上传</span>
						</a>
					</li>
					<li class="treeview" id="toDataMenu" data-step="7" data-position="right" data-intro="" data-img="todata.png">
						<a id="to-data-main" href="javascript:void(0)">
							<i class="fa fa-tasks"></i>
							<span>数据管理</span>
						</a>
					</li>
					<li class="treeview" id="toReportMenu" data-step="8" data-position="right" data-intro="" data-img="toreport.png">
						<a href="javascript:void(0)" onclick="showReport()">
							<i class="fa fa-files-o"></i>
							<span>报告</span>
						</a>
					</li>
					<li class="treeview" id="toAppStoreMenu" data-step="1" data-position="right" data-intro="" data-img="yysc.png">
						<a href="javascript:void(0)" onclick="showAppStore()">
							<i class="fa fa-cubes"></i>
							<span>应用市场</span>
						</a>
					</li>
					<shiro:hasPermission name="count:menu">
						<li class="treeview">
							<a href="javascript:void(0)" onclick="showCount()">
								<i class="fa fa-heartbeat"></i>
								<span>统计</span>
							</a>
						</li>
					</shiro:hasPermission>
					<li class="header">
						<span>用户中心</span>
					</li>
					<li class="treeview" id="accountManage">
						<a href="javascript:void(0)" onclick="showUser()">
							<i class="fa fa-user"></i>
							<span>账号管理</span>
						</a>
					</li>
					<li class="treeview" id="feedbackManage">
						<a href="javascript:void(0)" onclick="showFeedback()">
							<i class="fa fa-comments text-yellow"></i>
							<span>问题反馈</span>
						</a>
					</li>
					<li class="treeview" id="toHelpMenu">
						<a href="javascript:void(0);" onclick="showHelp()">
							<i class="fa fa-question-circle text-aqua"></i>
							<span>帮助</span>
						</a>
					</li>
				</ul>
			</section>
			<!-- /.sidebar -->
		</aside>
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper" id="uploadDIV"></div>
		<div class="content-wrapper" id="mainDIV"></div>
		<div class="content-wrapper" id="dataReportDIV"></div>
	</div>
	<input type="hidden" id="user-navigation-hide" value="${sessionScope.loginUserInSession.navigation }">
	<script src="//cdn.bootcss.com/spin.js/2.3.2/spin.min.js"></script>
	<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
	<script src="//cdn.bootcss.com/jqueryui/1.11.4/jquery-ui.min.js"></script>
	<script src="//cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
	<script src="<%=request.getContextPath()%>/plugins/intro/intro.js?version=1.1"></script>
	<script src="//cdn.bootcss.com/echarts/2.2.7/echarts.js"></script>
	<!-- TODO 数据参数同比有用，待统一为echarts -->
	<script src="//cdn.bootcss.com/highcharts/4.2.1/highcharts.js"></script>
	<script src="//cdn.bootcss.com/highcharts/4.2.1/modules/exporting.js"></script>
	<script src="<%=request.getContextPath()%>/plugins/select/select2.min.js"></script>
	<script src="<%=request.getContextPath()%>/plugins/select/select2_locale_zh-CN.js"></script>
	<!--   <script src="//cdn.bootcss.com/select2/4.0.1/js/i18n/zh-CN.js"></script> -->
	<script src="<%=request.getContextPath()%>/js/utils.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/main_init.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/main.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/charts.js"></script>
	<script src="<%=request.getContextPath()%>/plugins/highcharts/char.js?version=20150526"></script>
	<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.ui.draggable.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/echarts/echarts.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/echarts/myecharts.js"></script>
</body>
</html>