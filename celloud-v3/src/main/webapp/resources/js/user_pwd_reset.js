var wid =$(window).width();
	var per =(1-(553/wid))*50; 
	$(".loginWrapper").css("left",per+"%");
	$.ajaxSetup ({
		cache: false //关闭AJAX相应的缓存
	});
	$(document).ready(function(){
		$("#alertInfo").html("");
		//变换随机验证码
		$('#kaptchaImage').click(function() {
			$(this).hide().attr('src','kaptcha.jpg?' + Math.floor(Math.random() * 100)).fadeIn();
		});
		$(document).on("keypress","#findPasswordForm",function(e){
			if(e.keyCode==13){
				submitEmail();
			}
		});
	});
	//跳转到登录页面
	function forwardIndex(){
		window.location.href="<%=request.getContextPath() %>/login";
	}
	//表单提交验证
	function validateSubmitEmail(){
		var email = $("#inputEmail").val();
		if(!email){
			$(".error").html("请输入邮箱");
			$("#inputEmail").focus();
			return false;
		}
		var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if (!filter.test(email)){
			$(".error").html("您的电子邮件格式不正确");
			$("#inputEmail").focus();
			return false;
		}
		var validateCode = $("#inputValidateCode").val();
		if(!validateCode){
			$(".error").html("请输入验证码");
			$("#inputValidateCode").focus();
			return false;
		}
		return true;
	}
	//提交表单
	function submitEmail(){
		$(".error").html("");
		if(!validateSubmitEmail()){
			return;
		}
		$("#findPasswordForm").submit();
	}
	//关闭网页
	function closePage(){
		window.opener=null;
		window.open('','_self'); 
		window.close();
	}
	$("#logo").click(function(){
		window.location = "home.html";
	});