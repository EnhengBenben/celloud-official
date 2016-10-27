<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 优先使用最新版本的IE 和 Chrome 内核 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="baidu-site-verification" content="IsldTuHqik" />
<title>CelLoud 用户登录</title>
<meta name="keywords" content="上海华点云生物科技有限公司,celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据" />
<meta name="description" content="一站式高通量基因检测数据分析系统">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="bookmark" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login.css?version=3.2.4" media="all" />
</head>
<body onselectstart="return false;">
	<!--#S bgContainer-->
	<div class="bgContainer">
	<div class="login">
		<div class="login-icon" id="logo">
			<a href="<%=request.getContextPath()%>/" style="color: transparent;">
				<img src="<%=request.getContextPath()%>/images/icon/login_logo_187.png" />
			</a>
		</div>
		<div class="logo-name">生物信息云平台</div>
		<form action="login" method="post" id="loginForm">
			<div class="login-main clearfix">
			    <div class="error">&nbsp;</div>
				<input type="hidden" id="modulusHidden" name="modulus" value="${publicKey.modulus }" />
				<input type="hidden" id="exponentHidden" name="exponent" value="${publicKey.exponent }" />
				<input type="hidden" name="checked" id="checked" value="${requestScope.checked }" />
				<input type="hidden" name="info" id="info" value="${requestScope.info }" />
				<input type="text" class="username input-top" placeholder="手机号码" id="cellphone" name="cellphone"/>
				<div class="yzm">
					<input type="text" class="yzm input-bottom" placeholder="验证码" id="captcha" name="kaptchaCode" value="${requestScope.kapcode }" />
<%-- 					<img title="看不清，换一张" src="<%=request.getContextPath()%>/kaptcha" id="kaptchaImage" alt="验证码" --%>
<!-- 						class="validateCode" style="cursor: pointer;" /> -->
				    <a class="validateCode">获取验证码</a>
				</div>
				<a id="submit" class="btn-login sign-in" href="javascript:void(0);">登录</a>
			</div>
		</form>
	</div>
	<jsp:useBean id="_now" class="java.util.Date" />
	<div class="footer">
		©
		<fmt:formatDate value="${_now}" type="both" dateStyle="long" pattern="yyyy" />
		CelLoud，Inc. All Rights reserved.
		<a href="#">生物信息云平台</a>
		·
		<a href="javascript:void();">沪ICP备14035977号</a>
		·
		<a href="service.html" target="_blank">服务与支持</a>
		·
		<a href="feedback.html" target="_blank">意见反馈</a>
	</div>
	</div>
	<!--#E bgContainer-->
	<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/utils.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/tologin.js?version=1.1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/md5.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/security.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/browser.js?version=3.1.16.1"></script>
	<script type="text/javascript">
		var browser = $.NV();
		if(browser.name=='ie'&&browser.version<9){
			$("#loginForm").load("<%=request.getContextPath()%>/browser.html");
		}else{
			//根据视口和文档的宽高设置背景图片的尺寸
			utils.setDocSize();
			utils.checkPlaceholder();
			$(window).resize(function(){  
			  utils.setDocSize();
			});
		}
	</script>
</body>
</html>