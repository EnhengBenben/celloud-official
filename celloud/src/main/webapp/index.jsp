<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="bookmark" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="stylesheet" type="text/css" href="css/login.css" media="all" />
<title>CelLoud 用户登录</title>
</head>
<body onselectstart="return false;">
<!--#S bgContainer-->
<div class="bgContainer">
	<img src="images/home/login_bg.png" id="bg" />
	<!--#S wrapper-->
<!-- 	<div class="wrapper"> -->
	<input type="hidden" id="modulusHidden" value="${requestScope.publicKey.modulus }" />
	<input type="hidden" id="exponentHidden" value="${requestScope.publicKey.exponent }" />
    	<div class="login" style="position:absolute;">
        	<div class="logo" id="logo"><img src="images/login/login_logo.png" /></div>
        	<form action="login" method="post" id="loginForm">
	            <div class="main clearfix">
	                <input type="hidden" name="checked" id="checked" value="${requestScope.checked }" />
					<input type="hidden" name="info" id="info" value="${requestScope.info }" />
	            	<input type="text" class="username" placeholder="用户名或邮箱" id="username" name="username" value="${requestScope.username }"/>
	                <input type="password" class="pwd" placeholder="密码"  id="password" value="${fn:substring(requestScope.password,0,16)}"/>
	                <input type="hidden" id="password1" value="${fn:substring(requestScope.password,16,requestScope.password.length())}">
	                <input type="hidden" name="password">
	                <div class="yzm"><input type="text" class="yzm" placeholder="验证码"  id="captcha" name="kaptchaCode" value="${requestScope.kapcode }"/>
	                	<img title="看不清，换一张" src="<%=request.getContextPath() %>/kaptcha.jpg" id="kaptchaImage" alt="验证码" class="validateCode" style="cursor: pointer;"/>
	                </div>
	                <div class="autolog">
	                	<span id="remPass"><img src="images/login/nocheck.png"/></span>
	                	<input id="isRem" name="isRem" value="0" style="display: none">
	                	记住密码 <span class="error"></span>
	                </div>
	                <a href="javascript:void();" class="btn-login" id="submit">登录</a>
	                <a href="forgetPwd" class="forget">忘记密码</a>
	                <a href="download.html" class="download" target="_blank">下载客户端</a>
	            </div>
            </form>
            <div class="view-tips">最佳浏览体验：Chrome / Firefox 8.0+ / IE 9.0+</div>
        </div>
        <jsp:useBean id="_now" class="java.util.Date" />
        <div class="footer">© <fmt:formatDate value="${_now}" type="both" dateStyle="long" pattern="yyyy" /> CelLoud，Inc. All Rights reserved. <a  href="#">生物信息云平台</a> · <a  href="javascript:void();">沪ICP备14035977号</a> · <a href="service.html" target="_blank">服务与支持</a> · <a href="feedBack.html" target="_blank">意见反馈</a></div>
<!--     </div> -->
	<!--#E wrapper-->
</div>
<!--#E bgContainer-->
<script type="text/javascript" src="plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript" src="js/metro.js"></script>
<script type="text/javascript" src="js/tologin.js?version=1.0"></script>
<script type="text/javascript" src="js/external.js"></script>
<script type="text/javascript" src="plugins/md5.js"></script>
<script type="text/javascript" src="plugins/security.js"></script>
<script type="text/javascript" src="plugins/baidu.js"></script>
<script type="text/javascript">
//session超时iframe完全退出
if(window.top!=window.self){
	window.top.location = "<%=request.getContextPath() %>/toLogin";
}

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
	        var pwdField	= $("input[type=password]");
	        var pwdVal		= pwdField.attr('placeholder');
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