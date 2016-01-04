<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="bookmark" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/login.css" media="all" />
<title>CelLoud 用户重置密码</title>
</head>
<body onselectstart="return false;">
<!--#S bgContainer-->
<div class="bgContainer">
	<img src="<%=request.getContextPath() %>/images/home/login_bg.png" id="bg" />
	<!--#S wrapper-->
	<div class="wrapper">
    	<div class="login">
        	<div class="logo" id="logo"><img src="<%=request.getContextPath() %>/images/icon/login_logo.png" /></div>
        	<form>
              <div class="main_r clearfix" id="resetPwdMainDiv">
                <input type="hidden" id="userNameHidden" value="${username}">
				<input type="hidden" id="userCodeHidden" value="${code}">
				<input type="hidden" id="result" value="${result}">
                <div class="autolog">
                	   用户名：${username }
                </div>                         
            	<input type="password" class="pwd" id="inputPassword" placeholder="新密码(字母数字及下划线组合)" name="password"/>
            	<input type="password" class="pwd" id="inputConfirmPassword" placeholder="确认密码" name="confirmPassword"/>
                <div class="autolog">
                	 <span class="error"></span>
                </div>
                <a href="javascript:void()" class="btn-confirm" onclick="javascript:submitPassword();"></a>
	            <a href="<%=request.getContextPath() %>/" class="btn-cancle"></a>
              </div>
              <div style="display: none;" id="resetPwdExpireDiv"  class="main_f clearfix">
                <div class="autolog">
                	 <span class="error" id="resetPwdExpireInfo"></span>
                </div>
              </div>
            </form>
            <div class="view-tips">最佳浏览体验：Chrome / Firefox 8.0+ / IE 9.0+</div>
        </div>
        <jsp:useBean id="_now" class="java.util.Date" />
        <div class="footer">© <fmt:formatDate value="${_now}" type="both" dateStyle="long" pattern="yyyy" /> Celloud，Inc. All Rights reserved. <a  href="#">生物信息云平台</a> · <a  href="javascript:void();">沪ICP备14035977号</a> · <a href="../../service.html" target="_blank">服务与支持</a> · <a href="../../feedback.html" target="_blank">意见反馈</a></div>
    </div>
	<!--#E wrapper-->
</div>
<!--#E bgContainer-->
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jQuery/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/utils.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/user_pwd_reset.js"></script>
<script type="text/javascript">
$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
$(document).ready(function(){
	//根据视口和文档的宽高设置背景图片的尺寸
    setDocSize();
	$("#backToLogin").attr("style","display: none;");
	var result = $("#result").val();
	if(result==0){//链接错误或超时
		$("#resetPwdMainDiv").attr("style","display: none;");
		$("#resetPwdExpireDiv").attr("style","");
		$("#resetPwdExpireInfo").html("找回密码的链接错误或已过期");
	}
});
//跳转到登录页面
function forwardIndex(){
	window.location.href="<%=request.getContextPath() %>/toLogin";
}
//表单提交验证
function validateSubmitPassword(){
	var flag = true;
	var pwd = $("#inputPassword").val();
	var confirmPwd = $("#inputConfirmPassword").val();
	if(pwd==""){
		flag = false;
		$(".error").html("请输入新密码");
	}else{
		$(".error").html("");
		var numberRegex = /^[\d+]{6,16}$/;
		if(numberRegex.test(pwd)){
			flag = false;
			$(".error").html("密码不能全为数字");
		}else{
			$(".error").html("");
			//验证密码格式6-16位，字母数字组合，可以有下划线
			var combineRegex = /^[a-zA-Z0-9_]{6,16}$/;
			if(!combineRegex.test(pwd)){
				flag = false;
				$(".error").html("密码为6-16位的字母数字组合，也可以包含下划线_");
			}else{
				$(".error").html("");
				if(confirmPwd==""){
					flag = false;
					$(".error").html("请输入确认密码");
				}else{
					$(".error").html("");
					if(confirmPwd!=pwd){
						flag = false;
						$(".error").html("确认密码与新密码不一致");
					}else{
						$(".error").html("");
					}
				}
			}
		}
	}
	return flag;
}
var setinterval;
var time = 5;
//修改密码
function submitPassword(){
	if(!validateSubmitPassword()){
		return;
	}else{
		var userName = $("#userNameHidden").val();
		var pwd = $("#inputPassword").val();
		$.get("updatePwdByUserName.action",{"username":userName,"user.password":pwd},function(result){
			if(result==0){
				//修改找回密码链接的失效时间
				time = 5;
				$(".error").html("密码修改成功,"+time+"秒后将跳转到登录页面...");
				setinterval = setInterval(forwardProgress,1000);
			}else if(result==1){
				$(".error").html("密码修改失败");
			}
		});
	}
}
//跳转倒计时
function forwardProgress(){
	time--;
	if(time==0){
		clearInterval(setinterval);
		forwardIndex();
	}else{
		$(".error").html("密码修改成功,"+time+"秒后将跳转到登录页面...");
	}
}
</script>

</body>
</html>