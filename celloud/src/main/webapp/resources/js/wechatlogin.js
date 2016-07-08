$(document).ready(function(){
	$.ajaxSetup ({
		cache: false //关闭AJAX相应的缓存
	});
	error = $("#info").val();
	if(error!=""){
		$(".error").html(error);
	}else{
		$(".error").html("");
	}

	$("#submit").click(function(){
		if(checkForm()){
			$("#loginForm").submit();
		}
	});
	function checkForm(){
		$(".error").html("");
		//校验用户名是否为空
		var username = $.trim($("#username").val());
		if(username==""||username==$.trim($("#username").attr("placeholder"))){
			$(".error").html("请输入用户名！");
			$("#username").val('').focus();
			return false;
		}
		//校验密码是否为空
		var password = $.trim($("#password").val());
		if(password==""){
			$(".error").html("请输入密码！");
			$("#password").focus();
			return false;
		}
	  //全部校验已通过
    if(password){
      $("input[name='password']").val(secPWD(password));
    }
    return true;
	}
});
function secPWD(password){
	var modulus = $.trim($("#modulusHidden").val());
	var exponent = $.trim($("#exponentHidden").val());
	var key = RSAUtils.getKeyPair(exponent, '', modulus);
	return RSAUtils.encryptedString(key, password);
}
