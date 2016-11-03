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
  <link href="<%=request.getContextPath()%>/css/celloud.min.css?v=3.3.3" rel="stylesheet">
<%--   <link href="<%=request.getContextPath()%>/css/client.min.css" rel="stylesheet"> --%>
  <link href="<%=request.getContextPath()%>/less/client.less" rel="stylesheet/less" type="text/css" />
  <script src="//cdn.bootcss.com/less.js/2.7.1/less.min.js"></script>
  <link href="<%=request.getContextPath()%>/plugins/smartJqueryZoom/zoom-styles.css" rel="stylesheet" type="text/css"/>
  <script src="//cdn.bootcss.com/modernizr/2.8.3/modernizr.min.js"></script>
  <!-- [if It IE 9]>
  <script src="//cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
  <![endif]  -->
</head>
<body ng-app="celloudApp">
  <div class="container client">
      <header class="header">
	    <nav class="navbar navbar-default navbar-fixed-top">
	      <div class="container-fluid client">
	        <div class="navbar-header">
	          <div class="navbar-logo">
	            <a class="c-rocky-logo" href="#"></a>
	          </div>
	        </div>
	        <ul class="nav navbar-nav pull-right">
	          <li class="dropdown">
	            <a href="#/"  role="button" aria-haspopup="true" aria-expanded="false"><i class="c-rocky-home"></i></a>
	          </li>
	          <li class="dropdown">
	            <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
	              <i class="c-rocky-exit"></i>
	            </a>
	            <div class="dropdown-menu user-dropdown">
	              <a class="btn" href="#/base">个人信息</a>
	              <a class="btn btn-reset" href="logout">退出</a>
	            </div>
	          </li>
	        </ul>
	      </div>
	    </nav>
	  </header>
      <div class="view-container">
        <div class="pro-body rocky">
          <h1>乳腺癌易感基因(BRCA1/2)检测报告</h1>
          <div ng-view class="content">
          </div>
        </div>
      </div>
      <div id="alerts" class="alerts"></div>
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
  <script src="<%=request.getContextPath() %>/plugins/jquery.ba-resize.min.js"></script>
  <script src="<%=request.getContextPath()%>/plugins/area/area.js?v=3.3.4"></script>
  
  <script src="<%=request.getContextPath()%>/js/client.min.js"></script>
  <script type="text/javascript">
      window.username = '<%=((User)request.getSession().getAttribute("loginUserInSession")).getUsername() %>';
  </script>
</body>
</html>