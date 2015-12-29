<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<link rel="bookmark" href="<%=request.getContextPath()%>/images/favicon.ico"/>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login.css" media="all" />
</head>
<body onselectstart="return false;">
	<!--#S bgContainer-->
	<div class="bgContainer">
		<img src="images/home/login_bg.png" id="bg" />
		<!--#S wrapper-->
		<!--    <div class="wrapper"> -->

		<div class="login" style="position: absolute;">
			<div class="logo" id="logo">
				<a href="<%=request.getContextPath()%>/">
					<img src="images/login/login_logo.png" />
				</a>
			</div>
			<form action="login" method="post" id="loginForm">
				<div class="main clearfix">
					<input type="hidden" id="modulusHidden" name="modulus"
						value="${publicKey.modulus }" />
					<input type="hidden" id="exponentHidden" name="exponent"
						value="${publicKey.exponent }" />
					<input type="hidden" name="checked" id="checked"
						value="${requestScope.checked }" />
					<input type="hidden" name="info" id="info"
						value="${requestScope.info }" />
					<input type="text" class="username" placeholder="用户名或邮箱"
						id="username" name="username" value="${requestScope.user.username }" />
					<input type="password" class="pwd" placeholder="密码" id="password"
						value="${fn:substring(requestScope.user.password,0,16)}" />
					<input type="hidden" id="password1"
						value="${fn:substring(requestScope.user.password,16,requestScope.user.password.length())}">
					<input type="hidden" name="password">
					<div class="yzm">
						<input type="text" class="yzm" placeholder="验证码" id="captcha"
							name="kaptchaCode" value="${requestScope.kapcode }" />
						<img title="看不清，换一张"
							src="<%=request.getContextPath()%>/kaptcha.jpg" id="kaptchaImage"
							alt="验证码" class="validateCode" style="cursor: pointer;" />
					</div>
					<div class="autolog">
						<span id="remPass">
							<img src="images/login/nocheck.png" />
						</span>
						<input id="isRem" name="isRem" value="0" style="display: none">
						记住密码
						<span class="error"></span>
					</div>
					<a href="javascript:void(0);" class="btn-login" id="submit">登录</a>
					<a href="forgot.html" class="forget">忘记密码</a>
					<a href="download.html" class="download" target="_blank">下载客户端</a>
				</div>
			</form>
			<div class="view-tips">最佳浏览体验：Chrome / Firefox 8.0+ / IE 9.0+</div>
		</div>
		<jsp:useBean id="_now" class="java.util.Date" />
		<div class="footer">
			©
			<fmt:formatDate value="${_now}" type="both" dateStyle="long"
				pattern="yyyy" />
			CelLoud，Inc. All Rights reserved.
			<a href="#">生物信息云平台</a>
			·
			<a href="javascript:void();">沪ICP备14035977号</a>
			·
			<a href="service.html" target="_blank">服务与支持</a>
			·
			<a href="feedBack.html" target="_blank">意见反馈</a>
		</div>
		<!--     </div> -->
		<!--#E wrapper-->
	</div>
	<!--#E bgContainer-->
	<script type="text/javascript"
		src="plugins/jQuery/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="js/metro.js"></script>
	<script type="text/javascript" src="js/tologin.js?version=1.0"></script>
	<script type="text/javascript" src="js/external.js"></script>
	<script type="text/javascript" src="plugins/md5.js"></script>
	<script type="text/javascript" src="plugins/security.js"></script>
	<script type="text/javascript" src="plugins/baidu.js"></script>
	<script type="text/javascript">
		//判断浏览器是否支持 placeholder属性
		function isPlaceholder(){
		    var input = document.createElement('input');
		    return 'placeholder' in input;
		}
	
		if (!isPlaceholder()) {//不支持placeholder 用jquery来完成
		    $(document).ready(function() {
		        if(!isPlaceholder()){
		            $("input").not("input[name='password']").each(//把input绑定事件 排除password框
		                function(){
		                    if($(this).val()=="" && $(this).attr("placeholder")!=""){
		                        $(this).val($(this).attr("placeholder"));
		                        $(this).addClass("placeholder");
		                        $(this).focus(function(){
		                            if($(this).val()==$(this).attr("placeholder")) $(this).val("");
		                        });
		                        $(this).blur(function(){
		                            if($(this).val()=="") $(this).val($(this).attr("placeholder"));
		                        });
		                    }
		            });
		            //对password框的特殊处理1.创建一个text框 2获取焦点和失去焦点的时候切换
		            var pwdField    = $("input[type=password]");
		            var pwdVal      = pwdField.attr('placeholder');
		            pwdField.after('<input id="pwdPlaceholder" type="text" class="pwd" value='+pwdVal+' autocomplete="off" />');
		            var pwdPlaceholder = $('#pwdPlaceholder');
		            pwdPlaceholder.show();
		            pwdField.hide();
		            
		            pwdPlaceholder.focus(function(){
		                pwdPlaceholder.hide();
		                pwdField.show();
		                pwdField.focus();
		            });
		            
		            pwdField.blur(function(){
		                if(pwdField.val() == '') {
		                    pwdPlaceholder.show();
		                    pwdField.hide();
		                }
		            });
		        }
		    });
		}
	</script>
</body>
</html>