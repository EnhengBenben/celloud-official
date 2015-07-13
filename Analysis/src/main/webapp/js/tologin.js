$(document).ready(function(){
	$.get("user!toLogin",{},function(data){
		$("#modulusHidden").val(data.modulus);
		$("#exponentHidden").val(data.exponent);
	});
	var wid =$(window).width();
	var per =(1-(553/wid))*50; 
	$(".loginWrapper").css("left",per+"%");
	$.ajaxSetup ({
		cache: false //关闭AJAX相应的缓存
	});
	//变换随机验证码
	$('#kaptchaImage').click(function() {
		$(this).hide().attr('src','kaptcha.jpg?' + Math.floor(Math.random() * 100)).fadeIn();
	});
	$(".error").hide();
	error = $("#info").val();
	if(error!=""){
		$(".error").html(error);
		$(".error").show();
	}else{
		$(".error").html("");
		$(".error").hide();
	}
	var tmpRem=0;
	$("#submit").click(function(){
		$("#loginForm").submit();
	});
	$('#submit').keydown(function(e){
		if(e.keyCode==13){
			$("#submit").click();
		}
	});
	document.onkeydown = function(e){ 
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	$("#submit").click();
	     }
	};
	
	$("#loginForm").submit(function(){
		var flag = false;
		var username = $.trim($("#username").val());
		var password = $.trim($("#password").val());
		var captcha = $.trim($("#captcha").val());
		if(username==""){
			$(".error").html("请输入用户名！");
			$(".error").show();
		}else if(password==""){
			$(".error").html("请输入密码！");
			$(".error").show();
		}else if(captcha==""){
			$(".error").html("请输入验证码！");
			$(".error").show();
		}else{
			flag = true;
			$("input[name='user.password']").val(secPWD(password));
		}
		return flag;
	});
});

function secPWD(password){
	var modulus = $.trim($("#modulusHidden").val());
	var exponent = $.trim($("#exponentHidden").val());
	var key = RSAUtils.getKeyPair(exponent, '', modulus);
	return RSAUtils.encryptedString(key, password);
}
