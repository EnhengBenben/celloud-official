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
    <link href="dist/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
    <link href="dist/css/skins/skin-green.min.css" rel="stylesheet" type="text/css" />
    <!-- Date Picker -->
    <link href="plugins/datepicker/datepicker3.css" rel="stylesheet" type="text/css" />
    <!-- Daterange picker -->
    <link href="plugins/daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath() %>/plugins/select/select2.css" rel="stylesheet"/>
    <link href="<%=request.getContextPath() %>/dist/css/celloud.css?version=1.1" rel="stylesheet" type="text/css" />
  </head>
  <body class="skin-green sidebar-mini">
    <div class="wrapper">
      <header class="main-header">
        <a href="javascript:void(0)" class="logo">
          <span class="logo-mini"><b>C</b>el</span>
          <span class="logo-lg"><b>Cel</b>Loud</span>
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
                <ul class="dropdown-menu" style="width:180px;">
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
            <li class="header">产品和服务</li>
            <li class="active treeview">
              <a href="javascript:void(0)" onclick="showUserCount();">
                <i class="fa fa-dashboard"></i>
                <span>总览</span>
              </a>
            </li>
            <li class="treeview">
              <a href="javascript:void(0)" onclick="showUpload();">
                <i class="fa fa-sellsy"></i>
                <span>数据上传</span>
              </a>
            </li>
            <li class="treeview">
              <a href="javascript:void(0)" onclick="showData()">
                <i class="fa fa-tasks"></i>
                <span>数据管理</span>
              </a>
            </li>
            <li class="treeview">
              <a href="javascript:void(0)" onclick="showReport()">
                <i class="fa fa-files-o"></i>
                <span>报告</span>
              </a>
            </li>
            <li class="treeview">
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
            <li class="header">用户中心</li>
            <li><a href="javascript:void(0)" onclick="showUser()"><i class="fa fa-user"></i> <span>账号管理</span></a></li>
          </ul>
        </section>
        <!-- /.sidebar -->
      </aside>

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper" id="userCount">
        
      </div>
    </div>

    <script src="plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <script src="plugins/jQueryUI/jquery-ui.min.js" type="text/javascript"></script>
   
    <script src="plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>    
    <!-- Morris.js charts -->
    <script src="dist/js/raphael-min.js"></script>
    
    <!-- Sparkline -->
    <script src="plugins/sparkline/jquery.sparkline.min.js" type="text/javascript"></script>
    <!-- daterangepicker -->
    <script src="dist/js/moment.min.js" type="text/javascript"></script>
    <script src="plugins/daterangepicker/daterangepicker.js" type="text/javascript"></script>
    <!-- FastClick -->
    <script src="plugins/fastclick/fastclick.min.js"></script>
    <!-- AdminLTE App -->
    <script src="dist/js/app.min.js" type="text/javascript"></script>
    <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
    <script src="<%=request.getContextPath() %>/plugins/select/select2.min.js"></script>
	<script src="<%=request.getContextPath() %>/plugins/select/select2_locale_zh-CN.js"></script>
    <script src="js/global_v3.js?v=3.0" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/plugins/spin.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath() %>/plugins/highcharts/highcharts.js"></script>
	<script src="<%=request.getContextPath() %>/plugins/highcharts/exporting.js"></script>
	<script src="<%=request.getContextPath() %>/plugins/highcharts/char.js?version=20150526"></script>
    <!-- AdminLTE dashboard demo (This is only for demo purposes) -->
    <script type="text/javascript">
    	$(document).ready(function(){
    		showUserCount();
    	});
    </script>
  </body>
</html>