<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="bookmark" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="stylesheet" type="text/css" href="css/login.css" media="all" />
<title>CelLoud 找不到页面</title>
</head>
<body onselectstart="return false;">
<!--#S bgContainer-->
<div class="bgContainer">
	<img src="images/home/login_bg.png" id="bg" />
	<!--#S wrapper-->
	<div class="wrapper">
    	<div class="login">
        	<div class="logo" id="logo"><img src="images/login/login_logo.png" /></div>
            <div class="main_f clearfix">
             	<span style="font-size:50px">404</span>
            	<a href="home.html" class="error_return">返回首页</a>
            </div>
            <div class="view-tips">最佳浏览体验：Chrome / Firefox 8.0+ / IE 9.0+</div>
        </div>
        <jsp:useBean id="_now" class="java.util.Date" />
        <div class="footer">© <fmt:formatDate value="${_now}" type="both" dateStyle="long" pattern="yyyy" /> Celloud，Inc. All Rights reserved. <a  href="#">生物信息云平台</a> · <a  href="javascript:void();">沪ICP备14035977号</a> · <a href="service.html" target="_blank">服务与支持</a> · <a href="content/feedBack.html" target="_blank">意见反馈</a></div>
    </div>
	<!--#E wrapper-->
</div>
<!--#E bgContainer-->
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/metro.js"></script>
</body>
</html>