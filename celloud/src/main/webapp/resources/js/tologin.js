function CreateScript(file){  
	var new_element;  
	new_element=document.createElement("script");  
	new_element.setAttribute("type","text/javascript");  
	new_element.setAttribute("src",file);  
	document.body.appendChild(new_element);
} 
/**
 * 加载js百度统计
 */
document.write("<script src='plugins/baidu.js'><\/script>");
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
		document.getElementById("remPass").innerHTML="<img src='images/icon/checked.png'/>";
		tmpRem=1;
		document.getElementById("isRem").value=1;
	}else{
		document.getElementById("remPass").innerHTML="<img src='images/icon/nocheck.png'/>";
		tmpRem=0;
		document.getElementById("isRem").value=0;
	}
	
	var tmpRem=0;
	$("#submit").click(function(){
		if(checkForm()){
			$("#loginForm").submit();
		}
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
	function checkForm(){
		$(".error").html("");
		//校验用户名是否为空
		var username = $.trim($("#username").val());
		if(username==""||username==$.trim($("#username").attr("placeholder"))){
			$(".error").html("请输入用户名！");
			$(".error").show();
			$("#username").val('').focus();
			return false;
		}
		//校验密码是否为空
		var password = $.trim($("#password").val());
		if(password==""){
			$(".error").html("请输入密码！");
			$(".error").show();
			$("#password").focus();
			return false;
		}
		//校验验证码是否为空
		if($("#captcha").length>=1){
			var captcha = $.trim($("#captcha").val());
			if(captcha==""||captcha==$.trim($("#captcha").attr("placeholder"))){
				$(".error").html("请输入验证码！");
				$(".error").show();
				$("#captcha").val().focus();
				return false;
			}
		}
		//全部校验已通过
		var newPassword = $("input[name='newPassword']").val();
		if(newPassword){
			$("input[name='newPassword']").val(secPWD(newPassword));
		}
		delCookie("username");
		delCookie("password");
		return true;
	}
	$("#password").keyup(function(event){
		$("#tempPassword").val($("#password").val());
	});
	$("#password").change(function(){
		$("#tempPassword").val($("#password").val());
	});
	$("#remPass").click(function(){
		if(tmpRem==0){
			//之前未勾选，现在勾选
			document.getElementById("remPass").innerHTML="<img src='images/icon/checked.png'/>";
			tmpRem=1;
			document.getElementById("isRem").value=1;
			$("#checked").val(true);
		}else{
			//之前勾选，现在取消勾选，则清空之前的密码，显示验证码
			document.getElementById("remPass").innerHTML="<img src='images/icon/nocheck.png'/>";
			tmpRem=0;
			document.getElementById("isRem").value=0;
			$("#checked").val(false);
			$("#password").val("");
			checked = $("#checked").val();
		}
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
