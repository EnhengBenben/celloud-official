<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>CelLoud</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.4 -->
    <link href="plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" /> 
    <link href="plugins/font-awesome-4.4.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />    
    <link href="plugins/ionicons-master/css/ionicons.min.css" rel="stylesheet" type="text/css" />   
    <!-- Theme style -->
    <link href="dist/css/skins/skin-green.css?v=1.0" rel="stylesheet" type="text/css" />
    <!-- Date Picker -->
    <link href="plugins/datepicker/datepicker3.css" rel="stylesheet" type="text/css" />
    <!-- Daterange picker -->
    <link href="plugins/daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath() %>/plugins/select/select2.css" rel="stylesheet"/>
    <link href="<%=request.getContextPath() %>/dist/css/celloud.css?version=1.5" rel="stylesheet" type="text/css" />
    <link href="plugins/intro/introjs.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
  	<link href="<%=request.getContextPath() %>/dist/css/productDetail.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath() %>/dist/css/productList.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath() %>/dist/css/getNoResult.css" rel="stylesheet" type="text/css" />
  </head>
  <body class="skin-green sidebar-mini">
    <div class="wrapper">
      <header class="main-header">
        <a href="javascript:void(0)" class="logo">
          <span class="logo-mini"><img src="images/home/mini_logo.png"></span>
          <span class="logo-lg"><img src="dist/img/logo.png"></span>
        </a>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top" role="navigation">
          <!-- Sidebar toggle button-->
          <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
          </a>
          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
              <li class="dropdown user user-menu">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <img src="dist/img/user2-160x160.jpg" class="user-image" alt="User Image"/>
                  <span class="hidden-xs">${sessionScope.userName }</span>
                </a>
                <ul class="dropdown-menu">
                  <li class="user-header">
                    <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
                    <p>
                      CelLoud
                      <small>您身边的基因数据分析云平台</small>
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
            <li class="header"><span>产品和服务</span></li>
            <li class="active treeview">
              <a href="javascript:void(0)" onclick="showUserCount();">
                <i class="fa fa-dashboard"></i>
                <span>总览</span>
              </a>
            </li>
            <li class="treeview" id="toUploadMenu" data-step="4" data-position="right" data-intro="" data-img="toupload.png">
              <a href="javascript:void(0)" onclick="showUpload();">
                <i class="fa fa-sellsy"></i>
                <span>数据上传</span>
              </a>
            </li>
            <li class="treeview" id="toDataMenu" data-step="7" data-position="right" data-intro="" data-img="todata.png">
              <a href="javascript:void(0)" onclick="showData()">
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
            <li class="treeview">
              <a href="javascript:void(0)" onclick="showCount()">
                <i class="fa fa-heartbeat"></i>
                <span>统计</span>
              </a>
            </li>
            <li class="header"><span>用户中心</span></li>
            <li class="treeview" id="accountManage">
	            <a href="javascript:void(0)" onclick="showUser()">
	            	<i class="fa fa-user"></i>
	            	<span>账号管理</span>
	            </a>
            </li>
          </ul>
        </section>
        <!-- /.sidebar -->
      </aside>

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper" id="uploadDIV"></div>
      <div class="content-wrapper" id="mainDIV">
        
      </div>
    </div>
	<script src="<%=request.getContextPath() %>/plugins/intro/intro.js?version=1.0"></script>
	<script src="http://fgnass.github.io/spin.js/spin.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jQuery/jquery-1.11.3.min.js"></script>
    <script src="plugins/jQueryUI/jquery-ui.min.js" type="text/javascript"></script>
   
    <script src="plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="plugins/bootstrap/js/respond.js" type="text/javascript"></script>    
    <!-- Morris.js charts -->
    <script src="dist/js/raphael-min.js"></script>
    
    <!-- Sparkline -->
    <script src="plugins/sparkline/jquery.sparkline.min.js" type="text/javascript"></script>
    <!-- daterangepicker -->
    <script src="dist/js/moment.min.js" type="text/javascript"></script>
    <script src="plugins/daterangepicker/daterangepicker.js" type="text/javascript"></script>
    <!-- FastClick -->
<!--     <script src="plugins/fastclick/fastclick.min.js"></script> -->
    <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/select/select2.min.js"></script>
	<script src="<%=request.getContextPath() %>/plugins/select/select2_locale_zh-CN.js"></script>
    <script src="<%=request.getContextPath() %>/js/global_v3.js?v=3.2" type="text/javascript"></script>
    <script src="<%=request.getContextPath() %>/plugins/highcharts/highcharts.js"></script>
	<script src="<%=request.getContextPath() %>/plugins/highcharts/exporting.js"></script>
	<script src="<%=request.getContextPath() %>/plugins/highcharts/char.js?version=20150526"></script>
    <script src="dist/js/app.js?v=2" type="text/javascript"></script>
    <!-- AdminLTE dashboard demo (This is only for demo purposes) -->
    <!-- jquery_alert_dialogs begin -->
	<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.ui.draggable.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var hasNavi = <%=session.getAttribute("userNav")%>;
    	var intro = null;
	    $.ajaxSetup ({
		    cache: false //关闭AJAX相应的缓存
		});
    	$(document).ready(function(){
    		showUserCount();
    		if(hasNavi==1){
    			intro = introJs();
    			intro.setOption('tooltipPosition', 'auto');
    			intro.setOption('positionPrecedence', ['left', 'right', 'bottom', 'top']);
    			intro.setOption('showStepNumbers', false);
    			intro.setOption('showButtons', false);
				intro.start();
    		}
    	});
    	
    </script>
  </body>
</html>