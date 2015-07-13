<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据,统计系统" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="bookmark" href="<%=request.getContextPath()%>/favicon.ico"/>
<title>CelLoud统计系统</title>
<!-- basic styles -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="css/font-awesome.min.css" />
<!--[if IE 7]>
<link rel="stylesheet" href="css/font-awesome-ie7.min.css" />
<![endif]-->
<!-- fonts -->
<!-- <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" /> -->
<link rel="stylesheet" href="css/analy.min.css" />
<link rel="stylesheet" href="css/analy-rtl.min.css" />
<link rel="stylesheet" href="css/analy-skins.min.css" />
<link rel="stylesheet" href="css/daterangepicker.css" />
<!--[if lte IE 8]>
<link rel="stylesheet" href="css/analy-ie.min.css" />
<![endif]-->
<!-- inline styles related to this page -->
<!-- ace settings handler -->
<script src="js/analy-extra.min.js"></script>
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="js/html5shiv.js"></script>
<script src="js/respond.min.js"></script>
<![endif]-->
</head>
<body>
<div class="navbar navbar-default" id="navbar">
	<script type="text/javascript">
		try{ace.settings.check('navbar' , 'fixed');}catch(e){}
	</script>
	<div class="navbar-container" id="navbar-container">
		<div class="navbar-header pull-left">
			<a href="#" class="navbar-brand">
				<small>
					<i class="icon-leaf"></i>
					CelLoud统计系统
				</small>
			</a><!-- /.brand -->
		</div><!-- /.navbar-header -->

		<div class="navbar-header pull-right" role="navigation">
			<ul class="nav ace-nav">
				<li class="light-blue">
					<a data-toggle="dropdown" href="#" class="dropdown-toggle">
						<span class="user-info">
							<small>欢迎光临,</small>
							${session.userName }
						</span>
						<i class="icon-caret-down"></i>
					</a>
					<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
						<li class="divider"></li>
						<li>
							<a href="user!logout">
								<i class="icon-off"></i>
								退出
							</a>
						</li>
					</ul>
				</li>
			</ul><!-- /.ace-nav -->
		</div><!-- /.navbar-header -->
	</div><!-- /.container -->
</div>

<div class="main-container" id="main-container">
	<script type="text/javascript">
		try{ace.settings.check('main-container' , 'fixed');}catch(e){}
	</script>
	<div class="main-container-inner">
		<a class="menu-toggler" id="menu-toggler" href="#">
			<span class="menu-text"></span>
		</a>
		<div class="sidebar" id="sidebar">
			<script type="text/javascript">
				try{ace.settings.check('sidebar' , 'fixed');}catch(e){}
			</script>
			<ul class="nav nav-list">
				<li class="active">
					<a href="javascript:toHome()">
						<i class="icon-dashboard"></i>
						<span class="menu-text"> 控制台 </span>
					</a>
				</li>
				<li>
					<a href="javascript:toHospitalAll()" class="dropdown-toggle">
						<i class="icon-list"></i>
						<span class="menu-text">医院统计 </span>
						<b class="arrow icon-angle-down"></b>
					</a>

					<ul class="submenu">
						<li>
							<a href="javascript:toActivity()">
								<i class="icon-double-angle-right"></i>
								活跃度统计
							</a>
						</li>

						<li>
							<a href="javascript:toHospitalList()">
								<i class="icon-double-angle-right"></i>
								医院详细信息
							</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="#" class="dropdown-toggle">
						<i class="icon-list"></i>
						<span class="menu-text">数据统计 </span>

						<b class="arrow icon-angle-down"></b>
					</a>

					<ul class="submenu">
						<li>
							<a href="javascript:getUserDataList()">
								<i class="icon-double-angle-right"></i>
								全部用户数据量
							</a>
						</li>

						<li>
							<a href="javascript:getMonthDataList()">
								<i class="icon-double-angle-right"></i>
								数据量月统计
							</a>
						</li>
						
						<li>
							<a href="javascript:gotoOutputData()">
								<i class="icon-double-angle-right"></i>
								数据导出
							</a>
						</li>
					</ul>
				</li>

			</ul><!-- /.nav-list -->

			<div class="sidebar-collapse" id="sidebar-collapse">
				<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
			</div>

			<script type="text/javascript">
				try{ace.settings.check('sidebar' , 'collapsed');}catch(e){}
			</script>
		</div>

		<div class="main-content" id="content">
		</div><!-- /.main-content -->
	</div><!-- /.main-container-inner -->

	<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
		<i class="icon-double-angle-up icon-only bigger-110"></i>
	</a>
</div><!-- /.main-container -->

<!-- basic scripts -->
<script type="text/javascript">
	window.jQuery || document.write("<script src='js/jquery-2.0.3.min.js'>"+"<"+"script>");
</script>
<script type="text/javascript">
	if("ontouchend" in document) document.write("<script src='js/jquery.mobile.custom.min.js'>"+"<"+"script>");
</script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="js/typeahead-bs2.min.js"></script>

<!-- page specific plugin scripts -->
<!--[if lte IE 8]>
  <script src="js/excanvas.min.js"></script>
<![endif]-->
<script src="js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="js/jquery.ui.touch-punch.min.js"></script>
<script src="js/jquery.slimscroll.min.js"></script>

<!-- analysis scripts -->
<script src="js/analy-elements.min.js"></script>
<script src="js/analy.min.js"></script>
<!-- inline scripts related to this page -->

<script src="js/user.js"></script>
<script type="text/javascript" src="js/date-time/moment.min.js"></script>
<script type="text/javascript" src="js/date-time/daterangepicker.min.js"></script>

<script type="text/javascript">
	jQuery(function($) {
		toHome();
	});
	function getUserDataList(){
		$.get("data!getAllUsersDataNum",{},function(responseText){
			$("#content").html(responseText);
		});
	}
	function getMonthDataList(){
		$("#secondTitle").addClass("hide");
		$.get("data!getUsersMonthDataList",{},function(responseText){
			$("#content").html(responseText);
		});
	}
	function toHome(){
		$.get("home!toHome",{},function(responseText){
			$("#content").html(responseText);
		});
	}
	function toActivity(){
		$.get("company!toActivity",{},function(responseText){
			$("#content").html(responseText);
		});
	}
	function toHospitalList(){
		$.get("company!getCompanyDetail",{},function(responseText){
			$("#content").html(responseText);
		});
	}
</script>
</body>
</html>