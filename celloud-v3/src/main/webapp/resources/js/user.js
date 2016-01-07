$(document).ready(function() {
	$.ajaxSetup({
		cache : false
	// 关闭AJAX相应的缓存
	});
	$("#emailSpanInfo").html("");
	$("#phoneSpanInfo").html("");
	$("#updateBaseInfoDiv").html("");
});

var users = (function(users) {
	var self = users || {};
	self.clearActive = function() {
		$("#appMain").find(".baseInfo").html("");
		$("#userinfo").addClass("hide");
		$("#changePwd").addClass("hide");
		$("#operlog").addClass("hide");
		$("#userOperaUl li").removeClass("active");
	}
	self.showUserInfo = function() {
		self.clearActive();
		$("#userinfoTab").addClass("active");
		$("#userinfo").removeClass("hide");
		$("#subtitle").html("基本信息");
	}
	self.showChangePwd = function() {
		self.clearActive();
		$("#inputOldPassword").val("");
		$("#inputNewPassword").val("");
		$("#inputConfirmPassword").val("");
		$("#changePwdTab").addClass("active");
		$("#changePwd").removeClass("hide");
		$("#subtitle").html("修改密码");
	}
	self.showOperaLog = function() {
		self.clearActive();
		$("#operlogTab").addClass("active");
		$("#operlog").removeClass("hide");
		$("#subtitle").html("操作日志");
		// 获取日志信息
		$.get("user/logInfo", {}, function(responseText) {
			$("#operlog").html(responseText);
		});
	}
	self.searchLogInfo = function(page) {
		if (!page) {
			return;
		}
		$("#operlog").load("user/logInfo", {
			'currentPage' : page
		});
	}
	self.validateBaseInfo = function() {
		// 邮箱为必输项
		var email = $("#inputEmail").val();
		var telphone = $("#inputPhone").val();
		if (email == "") {
			$("#emailSpanInfo").html("请输入邮箱");
			$("#inputEmail").focus();
			return false;
		}
		$("#emailSpanInfo").html("");
		var emailregex = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if (!emailregex.test(email)) {
			flag = false;
			$("#emailSpanInfo").html("邮箱格式不正确");
			$("#inputPhone").focus();
			return false;
		}
		if (telphone == "") {
			return true;
		}
		if (telphone.length != 11) {
			$("#phoneSpanInfo").html("手机号格式不正确");
			$("#inputPhone").focus();
			return false;
		}
		$("#phoneSpanInfo").html("");
		var mobileregex = /^[\d+]{11}$/;
		if (!mobileregex.test(telphone)) {
			$("#phoneSpanInfo").html("手机号格式不正确");
			$("#inputPhone").focus();
			return false;
		}
		return true;
	}
	self.validateUpdatePwd = function() {
		$("#oldPwdSpanInfo").html("");
		$("#newPwdSpanInfo").html("");
		$("#confirmPwdSpanInfo").html("");
		var oldPwd = $("#inputOldPassword").val();
		var newPwd = $("#inputNewPassword").val();
		var confirmPwd = $("#inputConfirmPassword").val();
		if (oldPwd == "") {
			$("#oldPwdSpanInfo").html("请输入原密码");
			$("#inputOldPassword").focus();
			return false;
		}
		if (newPwd == "") {
			$("#newPwdSpanInfo").html("请输入新密码");
			$("#inputNewPassword").focus();
			return false;
		}
		var numberRegex = /^[\d+]{6,16}$/;
		if (numberRegex.test(newPwd)) {
			$("#newPwdSpanInfo").html("密码不能全为数字");
			$("#inputNewPassword").focus();
			return false;
		}
		var combineRegex = /^[a-zA-Z0-9_]{6,16}$/;
		if (!combineRegex.test(newPwd)) {
			$("#newPwdSpanInfo").html("密码为6-16位的字母、数字及下划线组合");
			$("#inputNewPassword").focus();
			return false;
		}
		if (confirmPwd == "") {
			$("#confirmPwdSpanInfo").html("请输入确认密码");
			$("#inputConfirmPassword").focus();
			return false;
		}
		if (confirmPwd != newPwd) {
			$("#confirmPwdSpanInfo").html("确认密码与新密码不一致");
			$("#inputConfirmPassword").focus();
			return false;
		}
		return true;
	}
	self.saveBaseInfo = function() {
		$("#updateBaseInfoDiv").hide();
		if (!self.validateBaseInfo()) {
			return;
		}
		var url = $("#userBaseInfoForm").attr("action");
		$.post(url, $("#userBaseInfoForm").serialize(), function(data) {
			if (data.code == '202') {
				$("#emailSpanInfo").html("该邮箱已存在");
				$("#inputEmail").focus();
			} else {
				$("#updateBaseInfoDiv").show();
				$("#updateBaseInfoDiv").html(data.message);
				if(data.data){
					$("#inputEmail").val(data.data.email);
					$("#inputPhone").val(data.data.cellphone);
				}
			}
		});
	}
	self.updatePassword = function() {
		$("#resetPwdSpanInfo").html("");
		if (!self.validateUpdatePwd()) {
			return;
		}
		var url = $("#updatePasswordForm").attr("action");
		$.post(url, $("#updatePasswordForm").serialize(), function(data) {
			if (data.code == '203') {
				$("#oldPwdSpanInfo").html("原始密码错误");
				$("#inputOldPassword").focus();
			} else {
				$("#resetPwdSpanInfo").show();
				$("#resetPwdSpanInfo").html(data.message);
			}
		})
	}
	self.showUserImages = function(){
		$("#userImages").modal("show");
	}
	self.chooseUserImage = function(icon){
		$("#userBaseInfoForm input[name='icon']").val(icon);
		$("#userImage").attr("src",self.getAvatar(icon));
		$("#userImages").modal("hide");
	}
	self.getAvatar = function(avatar){
		return 'images/avatar/'+avatar+'.png';
	}
	return self;
})(users);
// 修改密码校验
function validateUpdatePwd() {

}
// 修改密码提交
function submitUpdatePwd() {
	if (!validateUpdatePwd()) {
		return;
	} else {
		var oldPwd = $("#inputOldPassword").val();
		var newPwd = $("#inputNewPassword").val();
		$.get("validatePwd.action", {
			"password" : oldPwd
		}, function(result) {
			if (!result) {
				flag = false;
				$("#oldPwdSpanInfo").html("原密码输入不正确");
			} else {
				$("#oldPwdSpanInfo").html("");
				$.get("updateUserPwd.action", {
					"password" : newPwd
				}, function(result) {
					$("#resetPwdSpanInfo").attr("style", "");
					if (result) {
						$("#resetPwdSpanInfo").html("修改成功！");
					} else {
						$("#resetPwdSpanInfo").html("修改失败！");
					}
					setInterval(clearPwdInfo, 5000);
				});
			}
		});
	}
}
function clearPwdInfo() {
	$("#resetPwdSpanInfo").html("");
}
function submitBaseInfo() {
	if (!validateBaseInfo()) {
		return;
	} else {
		var email = $("#inputEmail").val();
		var telphone = $("#inputPhone").val();
		$.get("user!checkUserEmailByUserId", {
			"user.email" : email
		}, function(flag) {
			if (flag) {
				$("#emailSpanInfo").html("该邮箱已存在");
				return flag;
			} else {
				$("#emailSpanInfo").html("");
				$.get("updateUserBaseInfo.action", {
					"user.email" : email,
					"user.cellPhone" : telphone
				}, function(flag) {
					$("#updateBaseInfoDiv").attr("style", "");
					if (flag > 0) {
						$("#updateBaseInfoDiv").html("保存成功！");
					} else {
						$("#updateBaseInfoDiv").html("保存失败！");
					}
					setInterval(clearBaseInfo, 5000);
				});
			}
		});
	}
}
function clearBaseInfo() {
	$("#updateBaseInfoDiv").html("");
}
// 个人资料维护end
