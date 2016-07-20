/**
 * 问题反馈，登录前的，手机端的
 */
$.ajaxSetup({
	cache : false
// 关闭AJAX相应的缓存
});
function clearForm() {
	$("#toOther").removeClass("active");
	$("#toMe").addClass("active");
	$("#otherSay").hide();
	$("#selfSay").show();
	if (userName != null && userName != "" && userName != "null") {
		$("#userName").val(userName);
	} else {
		$("#userName").val("");
	}
	$("#email").val("");
	$("#emailSpanInfo").html("");
	$("#title").val("");
	$("#userNameSpanInfo").html("");
	$("#titleSpanInfo").html("");
	$("#contentSpanInfo").html("");
	$("#submitSpanInfo").html("");
	$("#chkUsername").prop("checked", false);
	$("#userName").attr("disabled", false);
}
$("#chkUsername").click(function() {
	var checked = $(this).prop("checked");
	if (checked) {
		$("#userName").val("匿名");
		$("#userName").attr("disabled", true);
		$("#userNameSpanInfo").html("");
	} else {
		if (userName != null && userName != "" && userName != "null") {
			$("#userName").val(userName);
		} else {
			$("#userName").val("");
		}
		$("#userName").attr("disabled", false);
	}
});
function validateFeed() {
	var result = true;
	var userName = $.trim($("#userName").val());
	var email = $.trim($("#email").val());
	var title = $.trim($("#title").val());
	var content = $.trim($("#content").val());
	if (userName == "") {
		result = false;
		$("#userNameSpanInfo").html("请输入用户名");
	} else {
		$("#userNameSpanInfo").html("");
	}
	if (email != "") {
		var emailregex = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if (!emailregex.test(email)) {
			result = false;
			$("#emailSpanInfo").html("邮箱格式不正确");
		} else {
			$("#emailSpanInfo").html("");
		}
	}
	if (title != "") {
		if (title.length < 4 || title.length > 30) {
			result = false;
			$("#titleSpanInfo").html("请输入4~30个字符的标题");
		} else {
			$("#titleSpanInfo").html("");
		}
	}
	if (content == "") {
		result = false;
		$("#contentSpanInfo").html("请输入10~10000个字符的内容");
	} else {
		if (content.length < 10 || content.length > 10000) {
			result = false;
			$("#contentSpanInfo").html("请输入10~10000个字符的内容");
		} else {
			$("#contentSpanInfo").html("");
		}
	}
	return result;
}
function submitFeedBack() {
	if (!validateFeed()) {
		return;
	}
	var userName = $.trim($("#userName").val());
	var title = $.trim($("#title").val());
	var content = $.trim($("#content").val());
	var email = $.trim($("#email").val());
	$.post("feedback/save", {
		"username" : userName,
		"email" : email,
		"title" : title,
		"content" : content,
		"_method" : "put"
	}, function(flag) {
		if (flag) {
			$("#submitSpanInfo").html("感谢您的反馈！我们正在快马加鞭的处理，请耐心等候哦~~");
			$(".submitBtn").attr("disabled", "disabled");
		} else {
			$("#submitSpanInfo").html("保存失败");
		}
	});
}
