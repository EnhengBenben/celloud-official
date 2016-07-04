<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login.css?version=3.1.14" media="all" />
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
		<form action="<%=request.getContextPath()%>/api/wechat/login" method="post" id="loginForm">
			<div class="login-main clearfix">
				<input type="hidden" name="info" id="info" value="${requestScope.info }" />
				<input type="hidden" name="code" id="code" value="${code }" />
			    <c:choose>
		            <c:when test="${requestScope.isSuccess=='true' }">
		                <br>
		                <br>
		                <br>
		                <br>
		                <div class="autolog">
		                    <span class="error" style="padding-left: 0; font-size: 16px;">${info }</span>
		                </div>
		            </c:when>
		            <c:otherwise>
		                <div class="error">&nbsp;test</div>
		                <input type="hidden" id="modulusHidden" name="modulus" value="${publicKey.modulus }" />
	                    <input type="hidden" id="exponentHidden" name="exponent" value="${publicKey.exponent }" />
	                    <input type="text" class="username input-top" placeholder="用户名或邮箱" id="username" name="username"/>
	                    <input type="password" class="pwd" placeholder="密码" id="password" name="password"/>
	                    <input type="hidden" id="tempPassword" />
                        <input type="hidden" name="newPassword" />
	                    <a id="submit" class="btn-login sign-in" href="javascript:void(0);">登录</a>
		            </c:otherwise>
		        </c:choose>
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
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/wechatlogin.js?version=0.1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/md5.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/security.js"></script>
	<script type="text/javascript">
		//根据视口和文档的宽高设置背景图片的尺寸
		utils.setDocSize();
		utils.checkPlaceholder();
		$(window).resize(function(){  
		  utils.setDocSize();
		});
	</script>
</body>
</html>