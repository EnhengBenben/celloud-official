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
<link href="<%=request.getContextPath()%>/css/less/main.less?v=3.2.5" rel="stylesheet/less" type="text/css" />
<link href="<%=request.getContextPath()%>/css/less/components.less?v=3.2.4" rel="stylesheet/less" type="text/css" />
<link href="<%=request.getContextPath()%>/css/less/theme.less?v=3.2.4" rel="stylesheet/less" type="text/css" />
<script src="//cdn.bootcss.com/less.js/2.7.1/less.min.js"></script>
</head>
<body class="container-fluid">
	<header class="main-header">
		<a href="javascript:void(0)" class="logo">
			<img src="<%=request.getContextPath()%>/images/icon/logo-gray.png">
		</a>
		<nav class="navbar">
			<div class="menu">
				<ul class="nav navbar-nav">
					<li>
						<a href="#">
							<i class="fa fa-bell" aria-hidden="true"></i>
						</a>
					</li>
					<li class="dropdown">
						<a class="dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
							<i class="fa fa-user" aria-hidden="true"></i>
						</a>
						<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
							<li>
								<a href="javascript:;" id="logoutBtn">
									<i class="fa fa-sign-out"></i> 退出
								</a>
							</li>
						</ul>
					</li>
				</ul>
			</div>
		</nav>
	</header>
	<aside class="main-sidebar">
		<ul class="menu">
			<li class="treeview -active">
				<a href="javascript:void(0)"></a>
			</li>
		</ul>
	</aside>
	<div class="main-container">
		<div class="header">
			<ol class="breadcrumb">
				<li>主页</li>
				<li>应用</li>
				<li id="app-name">华木兰</li>
				<li id="menu-name">收样</li>
			</ol>
		</div>
		<section class="content rocky">
			<header class="common-menu">
				<div class="logo">
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
					<li class="treeview -active">
						<a id="to-sample-a" href="javascript:void(0)" data-menu="收样">
							<i class="celicon -sample"></i>收样
						</a>
					</li>
					<li class="treeview">
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
		</section>
	</div>
	<script type="text/javascript">
		window.contextPath = '<%=request.getContextPath()%>';
	</script>
	<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
	<script src="//cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
	<script src="//cdn.bootcss.com/plupload/2.1.8/plupload.full.min.js"></script>
	<script src="//cdn.bootcss.com/plupload/2.1.8/i18n/zh_CN.js"></script>
	<script src="//cdn.bootcss.com/swfobject/2.2/swfobject.min.js"></script>
	<script src="//cdn.bootcss.com/echarts/2.2.7/echarts.js"></script>
	<script src="<%=request.getContextPath()%>/plugins/calendar/WdatePicker.js"></script>
	<script src="<%=request.getContextPath()%>/plugins/sockjs-modified-1.0.0.js"></script>
	<script src="<%=request.getContextPath()%>/plugins/waveLoading.min.js?version=1.1"></script>
	<script src="<%=request.getContextPath()%>/js/utils.js?v=3.2.4" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/base.js?v=3.2.4" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/rocky_main.js?v=3.2.4" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/rocky_upload.js?v=3.2.4"></script>
	<script src="<%=request.getContextPath()%>/js/rocky_report.js?v=3.2.4"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/rocky_data.js"></script>
</body>
</html>