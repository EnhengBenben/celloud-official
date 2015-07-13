$(document).ready(function(){
	$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
	});
  	$.get("getUserInfo.action",{},function(user){
	  $("#inputEmail").val(user.email);
	  $("#inputPhone").val(user.cellPhone);
  	});
  	$("#emailSpanInfo").html("");
  	$("#phoneSpanInfo").html("");
  	$("#updateBaseInfoDiv").html("");
});
function searchLogInfo(page){
	$.get("getUserLogInfo.action",{"page.currentPage":page,"page.pageSize":10},function(responseText){
		$("#tab3").html(responseText);
	});
}
//个人资料维护begin
//打开个人资料维护对话框
function openPersonalInfo(){
  $("#personalInfoDiv").attr("style","");
  showTab(0);
  $.get("getUserInfo.action",{},function(user){
	  $("#inputEmail").val(user.email);
	  $("#inputPhone").val(user.cellPhone);
  });
  $("#emailSpanInfo").html("");
  $("#phoneSpanInfo").html("");
  $("#updateBaseInfoDiv").html("");
}
//密码修改
function openResetPwd(){
  $("#inputOldPassword").val("");
  $("#inputNewPassword").val("");
  $("#inputConfirmPassword").val("");
  $("#resetPwdInfo").attr("style","display: none;");
  $("#resetPwdInfo").html("");
  $("#personalInfoDiv").attr("style","");
  $("#liBaseInfo").removeClass("active");
  $("#liPassword").removeClass("active");
  $("#liPassword").addClass("active");
  $("#tab1").attr("style","display: none;");
  $("#tab1").removeClass("active");
  $("#tab2").attr("style","");
  $("#tab2").addClass("active");
}
//关闭资料维护对话框
function closePersonInfo(){
  $("#personalInfoDiv").attr("style","display: none;");
}
//修改密码校验
function validateUpdatePwd(){
  var flag = true;
  var oldPwd = $("#inputOldPassword").val();
  var newPwd = $("#inputNewPassword").val();
  var confirmPwd = $("#inputConfirmPassword").val();
  if(oldPwd==""){
	  flag = false;
	  $("#oldPwdSpanInfo").html("请输入原密码");
  }else{
	  $("#oldPwdSpanInfo").html("");
	  if(newPwd==""){
		  flag = false;
		  $("#newPwdSpanInfo").html("请输入新密码");
	  }else{
		  $("#newPwdSpanInfo").html("");
		  var numberRegex = /^[\d+]{6,16}$/;
			if(numberRegex.test(newPwd)){
				flag = false;
				$("#newPwdSpanInfo").html("密码不能全为数字");
			}else{
				$("#newPwdSpanInfo").html("");
				 var combineRegex = /^[a-zA-Z0-9_]{6,16}$/;
				if(!combineRegex.test(newPwd)){
					flag = false;
					$("#newPwdSpanInfo").html("密码为6-16位的字母、数字及下划线组合");
				}else{
					$("#newPwdSpanInfo").html("");
					if(confirmPwd==""){
						  flag = false;
						  $("#confirmPwdSpanInfo").html("请输入确认密码");
					  }else{
						  $("#confirmPwdSpanInfo").html("");
						  if(confirmPwd!=newPwd){
							  flag = false;
							  $("#confirmPwdSpanInfo").html("确认密码与新密码不一致");
						  }else{
							  $("#confirmPwdSpanInfo").html("");
						  }
					  }
				}
			}
	  }
  }
  return flag;
}
//修改密码提交
function submitUpdatePwd(){
  if(!validateUpdatePwd()){
	  return;
  }else{
	  var oldPwd = $("#inputOldPassword").val();
	  var newPwd = $("#inputNewPassword").val();
	  $.get("validatePwd.action",{"password":oldPwd},function(result){
		  if(!result){
			  flag = false;
			  $("#oldPwdSpanInfo").html("原密码输入不正确");
		  }else{
			  $("#oldPwdSpanInfo").html("");
			  $.get("updateUserPwd.action",{"password":newPwd},function(result){
				  $("#resetPwdSpanInfo").attr("style","");
				  if(result){
					  $("#resetPwdSpanInfo").html("修改成功！");
				  }else{
					  $("#resetPwdSpanInfo").html("修改失败！");
				  }
				  setInterval(clearPwdInfo,5000);
			  });
		  }
	  });
  }
}
function clearPwdInfo(){
  $("#resetPwdSpanInfo").html("");
}
//提交个人资料
function validateBaseInfo(){
  //邮箱为必输项
  var flag = true;
  var email = $("#inputEmail").val();
  var telphone = $("#inputPhone").val();
  if(email==""){
	  flag = false;
	  $("#emailSpanInfo").html("请输入邮箱");
  }else{
	  $("#emailSpanInfo").html("");
	  var emailregex = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	  if(!emailregex.test(email)){
		  flag = false;
		  $("#emailSpanInfo").html("邮箱格式不正确");
	  }else{
		  $("#emailSpanInfo").html("");
	  }
  }

  if(telphone!=""){
	  if(telphone.length!=11){
		  flag = false;
		  $("#phoneSpanInfo").html("手机号格式不正确");
	  }else{
		  var mobileregex = /^[\d+]{11}$/;
		  if(!mobileregex.test(telphone)){
			  flag = false;
			  $("#phoneSpanInfo").html("手机号格式不正确");
		  }else{
			  $("#phoneSpanInfo").html("");
		  }
	  }
  }
  return flag;
}
function submitBaseInfo(){
  if(!validateBaseInfo()){
	  return;
  }else{
	  var email = $("#inputEmail").val();
	  var telphone = $("#inputPhone").val();
	  $.get("user!checkUserEmailByUserId",{"user.userId":$.trim($("#baseInfoUserIdHidden").val()),"user.email":email},function(flag){
		  if(flag){
			  $("#emailSpanInfo").html("该邮箱已存在");
			  return flag;
		  }else{
			  $("#emailSpanInfo").html("");
			  $.get("updateUserBaseInfo.action",{"user.email":email,"user.cellPhone":telphone},function(flag){
				  $("#updateBaseInfoDiv").attr("style","");
				  if(flag>0){
					  $("#updateBaseInfoDiv").html("保存成功！");
				  }else{
					  $("#updateBaseInfoDiv").html("保存失败！");
				  }
				  setInterval(clearBaseInfo,5000);
			  });
		  }
	  });
  }
}
function clearBaseInfo(){
  $("#updateBaseInfoDiv").html("");
}
//页签切换
function showTab(tabIndex){
  if(tabIndex==1){//个人信息
	  $("#tab1").css({
		 	display:"block"
	  });
	  $("#tab2").css({
	 	    display:"none"
	  });
	  $("#tab3").css({
	 	    display:"none"
	  });
	  $.get("getUserInfo.action",{},function(user){
		  $("#inputEmail").val(user.email);
		  $("#inputPhone").val(user.cellPhone);
	  });
	  $("#emailSpanInfo").html("");
	  $("#phoneSpanInfo").html("");
	  $("#updateBaseInfoDiv").html("");
  }else if(tabIndex==2){//密码修改
	  $("#tab1").css({
		 	display:"none"
	  });
	  $("#tab2").css({
	 	    display:"block"
	  });
	  $("#tab3").css({
	 	    display:"none"
	  });
	  $("#inputOldPassword").val('');
	  $("#oldPwdSpanInfo").html("");
	  $("#inputNewPassword").val('');
	  $("#newPwdSpanInfo").html("");
	  $("#inputConfirmPassword").val('');
	  $("#confirmPwdSpanInfo").html("");
	  $("#resetPwdSpanInfo").html("");
  }else if(tabIndex==3){//查看历史登录日志
	  $("#tab1").css({
		 	display:"none"
	  });
	  $("#tab2").css({
	 	    display:"none"
	  });
	  $("#tab3").css({
	 	    display:"block"
	  });
	  //获取日志信息
	  $.get("getUserLogInfo.action",{},function(responseText){
		  $("#tab3").html(responseText);
	  });
  }
}
//个人资料维护end