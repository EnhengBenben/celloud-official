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
<link href="<%=request.getContextPath() %>/css/celloud.min.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/css/less/main.less" rel="stylesheet/less" type="text/css" />
<script src="//cdn.bootcss.com/less.js/2.7.1/less.min.js"></script>
<script src="//cdn.bootcss.com/modernizr/2.8.3/modernizr.min.js"></script>
<!-- [if It IE 9]>
<script src="//cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]  -->
</head>
<body class="container" ng-app="celloudApp" ng-controller="sidebarController">
<header class="header">
    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <div class="navbar-logo {{collapsed|logoMiniFilter}}">
            <a class="logo" href="#"></a>
          </div>
        </div>
        <ul class="nav navbar-nav pull-left">
            <shiro:hasPermission name="rocky:product">
              <li>
                <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
                  <i class="cubes-icon">&nbsp;</i>
                </a>
                <div class="dropdown-menu product-dropdown">
                  <a href=""><img src="<%=request.getContextPath()%>/images/app/rocky.png" alt="华木兰" title="华木兰"></a>
                </div>
              </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="bsi:product">
              <li>
                <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
                  <i class="cubes-icon">&nbsp;</i>
                </a>
                <div class="dropdown-menu product-dropdown">
                  <a href=""><img src="<%=request.getContextPath()%>/images/app/rocky.png" alt="百菌探" title="百菌探"></a>
                  <a href=""><img src="<%=request.getContextPath()%>/images/app/rocky.png" alt="华木兰" title="华木兰"></a>
                </div>
              </li>
            </shiro:hasPermission>
          <li><a data-toggle="modal" data-target="#upload-modal"><i class="upload-icon"></i></a></li>
        </ul>
        <ul class="nav navbar-nav pull-right">
          <li class="dropdown">
            <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
              <i class="code-icon"></i>
            </a>
            <div class="dropdown-menu code-dropdown">
              <img alt="扫码关注" src="<%=request.getContextPath()%>/images/icon/qrcode.jpg">
            </div>
          </li>
          <li class="dropdown">
            <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
              <i class="money-icon">&nbsp;</i>
            </a>
            <div class="dropdown-menu money-dropdown">
              <p>账户余额：<span class="tips">{{userInfo.balances}}</span>元</p>
              <a class="btn" href="#/expense/paydetail">立即充值</a><br>
              <a class="btn-link" href="<%=request.getContextPath()%>/index#/expense/consume">查看消费记录</a>
            </div>
          </li>
          <li class="dropdown">
            <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
              <i class="bell-icon">&nbsp;</i>
              <span class="label label-danger">{{notices.num==0?'':(notices.num+'')}}</span>
            </a>
            <div class="dropdown-menu message-dropdown">
              <p> 您有<span class="tips">{{notices.num}}</span>条新消息</p>
              <a class="btn-link" href="<%=request.getContextPath()%>/index#/notices">查看所有</a>
            </div>
          </li>
           <li class="dropdown">
            <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
              <i class="bell-icon">&nbsp;</i>
              <span class="label label-danger">{{messages.num==0?'':(''+messages.num)}}</span>
            </a>
            <div class="dropdown-menu message-dropdown">
              <p> 您有<span class="tips">{{messages.num}}</span>条新消息</p>
              <a class="btn-link" href="<%=request.getContextPath()%>/index#/messages">查看所有</a>
            </div>
          </li>
          <li class="dropdown">
            <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
              <i class="user-icon">&nbsp;</i>
            </a>
            <div class="dropdown-menu user-dropdown">
              <a class="btn" href="<%=request.getContextPath()%>/index#/user/base">个人信息</a>
              <a class="btn btn-cancel" href="logout">退出</a>
            </div>
          </li>
        </ul>
      </div>
    </nav>
  </header>
  <aside class="sidebar {{collapsed|collapsedFilter}}" id="common-sidebar">
    <section class="s-bar">
      <div class="sidebar-collapse">
        <a href="javascript:void(0)" ng-click="toggleCollapse()"><i class="{{collapsed|collapsedIconFilter}}"></i></a>
      </div>
      <ul class="sidebar-menu">
        <li class="header">产品与服务</li>
        <li ng-class="{active: isActive('/')}">
          <a href="#/"><i class="overview-icon"></i><span>我的工作台</span></a>
        </li>
        <li>
          <a href="javascript:void(0)"><i class="sample-icon"></i><span>样本采集</span></a>
        </li>
        <li>
          <a href="javascript:void(0)"><i class="experiment-icon"></i><span>实验管理</span></a>
        </li>
        <li ng-class="{active: isActive('/data')}">
          <a href="<%=request.getContextPath()%>/index#/data"><i class="data-icon"></i><span>数据管理</span></a>
        </li>
        <li ng-class="{active: isActive('/report')}">
          <a href="#/reportdata"><i class="report-icon"></i><span>报告管理</span></a>
        </li>
        <li ng-class="{active: isActive('/app')}">
          <a href="<%=request.getContextPath()%>/index#/app"><i class="app-icon"></i><span>应用市场</span></a>
        </li>
      </ul>
      <ul class="sidebar-menu">
        <li class="header">用户中心</li>
        <li ng-class="{active: isActive('/user')}">
          <a href="<%=request.getContextPath()%>/index#/user/base"><i class="account-icon"></i><span>账号管理</span></a>
        </li>
        <li ng-class="{active: isActive('/qa')}">
          <a href="<%=request.getContextPath()%>/index#/qa"><i class="qa-icon"></i><span>问题反馈</span></a>
        </li>
        <li ng-class="{active: isActive('/expense')}">
          <a href="<%=request.getContextPath()%>/index#/expense/consume"><i class="cost-icon"></i><span>费用中心</span></a>
        </li>
      </ul>
    </section>
  </aside>
  <div class="view-container">
    <div class="pro-body">
      <ol class="breadcrumb">
        <li>CelLoud</li>
        <li>我的产品</li>
        <li>百菌探</li>
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
<!-- 					<li class="treeview"> -->
<!-- 						<a id="to-data-a" href="javascript:void(0)" data-menu="数据"> -->
<!-- 							<i class="celicon -data"></i> 数据 -->
<!-- 						</a> -->
<!-- 					</li> -->
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
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular-route.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular-resource.min.js"></script>
  <script src="//cdn.bootcss.com/echarts/2.2.7/echarts.js"></script>
  
  <script src="<%=request.getContextPath()%>/js/message.js"></script>
  <script src="<%=request.getContextPath()%>/js/application.js"></script>
  <script src="<%=request.getContextPath()%>/js/config/routeProvider.js"></script>
  <script src="<%=request.getContextPath()%>/js/common/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/common/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/common/filter.js"></script>
  
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