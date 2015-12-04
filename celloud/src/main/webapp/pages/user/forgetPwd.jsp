<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="bookmark" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="stylesheet" type="text/css" href="css/login.css" media="all" />
<title>CelLoud 用户找回密码</title>
</head>
<body onselectstart="return false;">
<!--#S bgContainer-->
<div class="bgContainer">
	<img src="images/home/login_bg.png" id="bg" />
	<!--#S wrapper-->
	<div class="wrapper">
    	<div class="login">
        	<div class="logo" id="logo"><img src="images/login/login_logo.png" /></div>
        	<form>
              <div class="main_f clearfix">
            	<input type="text" class="username" placeholder="已注册的邮箱" id="inputEmail" name="email"/>
                <div class="yzm"><input type="text" class="yzm" placeholder="验证码"  id="inputValidateCode" name="validateCode" value="${requestScope.kapcode }"/>
                	<img title="看不清，换一张" src="<%=request.getContextPath() %>/kaptcha.jpg" id="kaptchaImage" alt="验证码" class="validateCode" style="cursor: pointer;"/>
                </div>
                <div class="autolog">
                	 <span class="error"></span>
                </div>
                <a href="javascript:void()" class="btn-email" onclick="javascript:submitEmail();">发送邮件</a>
	            <a href="toLogin" class="return">返回登录</a>
              </div>
            </form>
            <div class="view-tips">最佳浏览体验：Chrome / Firefox 8.0+ / IE 9.0+</div>
        </div>
        <jsp:useBean id="_now" class="java.util.Date" />
        <div class="footer">© <fmt:formatDate value="${_now}" type="both" dateStyle="long" pattern="yyyy" /> Celloud，Inc. All Rights reserved. <a  href="#">生物信息云平台</a> · <a  href="javascript:void();">沪ICP备14035977号</a> · <a href="service.html" target="_blank">服务与支持</a> · <a href="feedBack.html" target="_blank">意见反馈</a></div>
    </div>
	<!--#E wrapper-->
</div>
<!--#E bgContainer-->
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/metro.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/forgetRwd.js"></script>
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