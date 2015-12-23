$(document).ready(function(){
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

	var checked = $("#checked").val();
	if(checked=="true"){
		document.getElementById("remPass").innerHTML="<img src='images/login/checked.png'/>";
		tmpRem=1;
		document.getElementById("isRem").value=1;
		if(error==""){
			//若记住密码，则没有验证码
			$(".yzm").css("display","none");
		}
	}else{
		document.getElementById("remPass").innerHTML="<img src='images/login/nocheck.png'/>";
		tmpRem=0;
		document.getElementById("isRem").value=0;
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
		if(checked=="true"&&error==""){
			if(username==""){
				$(".error").html("请输入用户名！");
				$(".error").show();
			}else if(password==""){
				$(".error").html("请输入密码！");
				$(".error").show();
			}else {
				flag = true;
				var password1 = $.trim($("#password1").val());
				var pwd = password + password1;
				$("input[name='password']").val(pwd);
			}
		}else{
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
				$("input[name='password']").val(secPWD(password));
			}
			delCookie("username");
			delCookie("password");
		}
		return flag;
	});

	$("#remPass").click(function(){
		if(tmpRem==0){
			//之前未勾选，现在勾选
			document.getElementById("remPass").innerHTML="<img src='images/login/checked.png'/>";
			tmpRem=1;
			document.getElementById("isRem").value=1;
			$("#checked").val(true);
		}else{
			//之前勾选，现在取消勾选，则清空之前的密码，显示验证码
			document.getElementById("remPass").innerHTML="<img src='images/login/nocheck.png'/>";
			tmpRem=0;
			document.getElementById("isRem").value=0;
			$("#checked").val(false);
			if($(".yzm").attr("style")){
				$("#password").val("");
			}
			$(".yzm").css("display","");
			checked = $("#checked").val();
		}
	});
	
	$("#logo").click(function(){
			window.location = "home.html";
	});
});

function secPWD(password){
	var modulus = $.trim($("#modulusHidden").val());
	var exponent = $.trim($("#exponentHidden").val());
	var key = RSAUtils.getKeyPair(exponent, '', modulus);
	return RSAUtils.encryptedString(key, password);
}

function getCookie(name){
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
     if(arr != null) return unescape(arr[2]); return null;
}
function delCookie(name){
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}
