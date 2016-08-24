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
  <link href="<%=request.getContextPath()%>/less/celloud.less" rel="stylesheet/less" type="text/css" />
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
              <a class="btn-link" href="#/expense/consume">查看消费记录</a>
            </div>
          </li>
          <li class="dropdown">
            <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
              <i class="bell-icon">&nbsp;</i>
              <span class="label label-danger">{{notices.num==0?'':(notices.num+'')}}</span>
            </a>
            <div class="dropdown-menu message-dropdown">
              <p> 您有<span class="tips">{{notices.num}}</span>条新消息</p>
              <a class="btn-link" href="#/notices">查看所有</a>
            </div>
          </li>
           <li class="dropdown">
            <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
              <i class="bell-icon">&nbsp;</i>
              <span class="label label-danger">{{messages.num==0?'':(''+messages.num)}}</span>
            </a>
            <div class="dropdown-menu message-dropdown">
              <p> 您有<span class="tips">{{messages.num}}</span>条新消息</p>
              <a class="btn-link" href="#/messages">查看所有</a>
            </div>
          </li>
	      <li class="dropdown">
	        <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
	          <i class="user-icon">&nbsp;</i>
	        </a>
	        <div class="dropdown-menu user-dropdown">
	          <a class="btn" href="#/user/base">个人信息</a>
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
        <shiro:hasPermission name="count:menu">
        	<li ng-class="{active: isActive('/count')}">
	          <a href="#/count"><i class="count-icon"></i><span>统计</span></a>
	        </li>
        </shiro:hasPermission>
        <li>
          <a href="javascript:void(0)"><i class="experiment-icon"></i><span>实验管理</span></a>
        </li>
        <li ng-class="{active: isActive('/data')}">
          <a href="#/data"><i class="data-icon"></i><span>数据管理</span></a>
        </li>
        <li ng-class="{active: isActive('/report')}">
<%--           <shiro:lacksPermission name="bsi:product"> --%>
<!--             <a href="#/reportpro"><i class="report-icon"></i><span>报告管理</span></a> -->
<%--           </shiro:lacksPermission> --%>
<%--           <shiro:hasPermission name="bsi:product"> --%>
            <a href="#/reportdata"><i class="report-icon"></i><span>报告管理</span></a>
<%--           </shiro:hasPermission> --%>
        </li>
        <li ng-class="{active: isActive('/app')}">
          <a href="#/app"><i class="app-icon"></i><span>应用市场</span></a>
        </li>
      </ul>
      <ul class="sidebar-menu">
        <li class="header">用户中心</li>
        <li ng-class="{active: isActive('/user')}">
          <a href="#/user/base"><i class="account-icon"></i><span>账号管理</span></a>
        </li>
        <li ng-class="{active: isActive('/qa')}">
          <a href="#/qa"><i class="qa-icon"></i><span>问题反馈</span></a>
        </li>
        <li ng-class="{active: isActive('/expense')}">
          <a href="#/expense/consume"><i class="cost-icon"></i><span>费用中心</span></a>
        </li>
      </ul>
    </section>
  </aside>
  <div ng-view class="view-container"></div>
  <div id="tips-modal" class="modal tips-modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
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
  <ng-include src="'pages/partial/_partial_upload_modal.jsp'" ></ng-include>
  <script type="text/javascript">
       window.CONTEXT_PATH = '<%=request.getContextPath()%>';
  </script>
  <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
  <script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="//cdn.bootcss.com/spin.js/2.3.2/spin.min.js"></script>
  <script src="//cdn.bootcss.com/echarts/3.2.2/echarts.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular-route.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular-resource.min.js"></script>
  <script src="//cdn.bootcss.com/echarts/2.2.7/echarts.js"></script>
  
  <script src="<%=request.getContextPath()%>/js/message.js"></script>
  <script src="<%=request.getContextPath()%>/js/application.js"></script>
  <script src="<%=request.getContextPath()%>/js/app/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/app/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/expense/filter.js"></script>
  <script src="<%=request.getContextPath()%>/js/expense/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/expense/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/user/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/user/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/data/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/data/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/data/data.js"></script>
  <script src="<%=request.getContextPath()%>/js/config/routeProvider.js"></script>
  <script src="<%=request.getContextPath()%>/js/common/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/common/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/common/filter.js"></script>
  <script src="<%=request.getContextPath()%>/js/overview/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/overview/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/overview/userCount.js"></script>
  <script src="<%=request.getContextPath()%>/js/notice/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/notice/controller.js"></script>
</body>
</html>