<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="bookmark" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login-add.css?version=1.0" media="all" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/user-add.css" />
<title>CelLoud 用户权限追加</title>
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
		     	<c:choose>
		     	   <c:when test="${flag}">
	                <span style="font-size:22px">权限追加成功，请登录查看！</span>
		     	   </c:when>
		     	   <c:otherwise>
	                <span style="font-size:22px">对不起，您的链接已超时，请重新申请！</span>
		     	   </c:otherwise>
		     	</c:choose>
                <br/><br/>
                <a href="${officialWebsite }" class="error_return" style="margin-left: auto;">返回首页进行登录,<span id="toOfficialWeb">5</span>秒后自动跳转到首页</a>
            </div>
            <div class="view-tips">最佳浏览体验：Chrome / Firefox 8.0+ / IE 9.0+</div>
        </div>
        <jsp:useBean id="_now" class="java.util.Date" />
        <div class="footer">© <fmt:formatDate value="${_now}" type="both" dateStyle="long" pattern="yyyy" /> Celloud，Inc. All Rights reserved. <a  href="#">生物信息云平台</a> · <a  href="javascript:void(0);">沪ICP备14035977号</a> · <a href="service.html" target="_blank">服务与支持</a> · <a href="feedBack.html" target="_blank">意见反馈</a></div>
    </div>
	<!--#E wrapper-->
</div>
<!--#E bgContainer-->
</body>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/bootstrap/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/metro.js"></script>
<script type="text/javascript">
$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
function toOfficialWeb(){
	var flag = "${flag}";
	// 错误页面, 倒计时5秒跳转到官网
	var time = 5;
	setInterval(function(){
		if(--time>0){
			$("#toOfficialWeb").text(time);
		}else{
			window.location.href = "${officialWebsite}";
		}
	},1000);
}
$(document).ready(function(){
	toOfficialWeb();
});
</script>
</html>
