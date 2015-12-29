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
	});
	//跳转到登录页面
	function forwardIndex(){
		window.location.href="<%=request.getContextPath() %>/toLogin";
	}
	//表单提交验证
	function validateSubmitEmail(){
		var email = $("#inputEmail").val();
		if(!email){
			$(".error").html("请输入邮箱");
			return false;
		}
		var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if (!filter.test(email)){
			$(".error").html("您的电子邮件格式不正确");
			return false;
		}
		var validateCode = $("#inputValidateCode").val();
		if(!validateCode){
			$(".error").html("请输入验证码");
			return false;
		}
		return true;
	}
	//提交表单
	function submitEmail(){
		$(".error").html("");
		if(!validateSubmitEmail()){
			return;
		}else{
			var email = $("#inputEmail").val();
			var validateCode = $("#inputValidateCode").val();
			$.get("user!validateUserEmail",{"user.email":email,"kaptchaCode":validateCode},function(result){
				if(result==1){//验证码输入有误
					$(".error").html("验证码输入有误");
				}else if(result==2){//邮箱验证不通过
					$(".error").html("此邮箱不存在");
				}else{
					var firstLetter = email.substring(0,1);
					var emailExt = email.substring(email.indexOf("@"));
					$(".error").html("您的申请已提交成功，请查看您的"+firstLetter+"******"+emailExt+"邮箱");
				}
			});
		}
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