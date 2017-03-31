<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="bookmark" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login-add.css?version=1.1" media="all" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/user-add.css" />
<title>Session超时</title>
</head>
<body onselectstart="return false;">
<!--#S bgContainer-->
<div class="bgContainer">
	<img src="<%=request.getContextPath()%>/images/home/login_bg.png" id="bg" />
	<!--#S wrapper-->
	<div class="wrapper">
    	<div class="register">
        	<div class="register-logo" id="logo"><img src="<%=request.getContextPath()%>/images/icon/login_logo.png" /></div>
   	       <div class="main_f clearfix">
	            <span style="font-size:22px">对不起，您的登录信息已超时，请重新登录！</span>
                <br/><br/>
                <a href="" class="error_return" id="loginPath" style="margin-left: auto;">点击重新登录！<span id="toLoginWeb">5</span>秒后自动跳转到登录页面</a>
            </div>
        </div>
        <jsp:useBean id="_now" class="java.util.Date" />
        <div class="footer">© <fmt:formatDate value="${_now}" type="both" dateStyle="long" pattern="yyyy" /> Celloud，Inc. All Rights reserved. <a  href="#">生物信息云平台</a> · <a  href="javascript:void(0);">沪ICP备14035977号</a> · <a href="service.html" target="_blank">服务与支持</a> · <a href="feedBack.html" target="_blank">意见反馈</a></div>
    </div>
	<!--#E wrapper-->
</div>
<!--#E bgContainer-->
</body>
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript">
$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
function toLoginWeb(){
	// 错误页面, 倒计时5秒跳转到登录页面
	var time = 5;
	var href = window.location.href;
	href = href.replace('sessionTimeOut.html','login')
	$("#loginPath").prop("href",href);
	setInterval(function(){
		if(--time>0){
			$("#toLoginWeb").text(time);
		}else{
			window.location.href = href;
		}
	},1000);
}
$(document).ready(function(){
	toLoginWeb();
});
</script>
</html>
