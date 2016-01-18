/**
 * 问题反馈，登录后的
 */
$.ajaxSetup({
	cache : false
// 关闭AJAX相应的缓存
});
var feedbacks = (function(feedbacks) {
	var self = feedbacks || {};
	self.currentPage = 1;
	var resetFeedbackFormState = function(){
		var $form = $("#feedbackCreateForm");
		$form.find(".form-group").removeClass("has-error");
		$form.find(".help-block").html("");
	}
	var validateFeedback = function(){
		resetFeedbackFormState();
		var $form = $("#feedbackCreateForm");
		var $title = $form.find("[name=title]");
		var title = $title.val();
		var $titleGroup = $title.parent(".form-group");
		var $content = $form.find("[name=content]");
		var content = $content.val();
		if(!title){
			//输入标题
			$title.parents(".form-group:first").addClass("has-error");
			$title.parents(".form-group:first").find(".help-block").html("请输入标题！");
			$title.focus();
			return false;
		}
		if(title.length>30||title.length<4){
			//4-30字符
			$title.parents(".form-group:first").addClass("has-error");
			$title.parents(".form-group:first").find(".help-block").html("请输入4-30字符的标题！");
			$title.focus();
			return false;
		}
		if(!content){
			$content.parents(".form-group:first").addClass("has-error");
			$content.parents(".form-group:first").find(".help-block").html("请输入内容！");
			$content.focus();
			return false;
			//输入内容
		}
		if(content.length>1000||content.length<10){
			//10-1000字符
			$content.parents(".form-group:first").addClass("has-error");
			$content.parents(".form-group:first").find(".help-block").html("请输入10-1000字符的内容！");
			$content.focus();
			return false;
		}
		return true;
	}
	var validateReply = function(){
		var value = $("#feedbackReplyContent").val();
		if(!$.trim(value)){
			$("#feedbackReplyBtn").attr("disabled","disabled");
			return false;
		}
		return true;
	}
	self.create = function() {
		if(!validateFeedback()){
			return;
		}
		var $form = $("#feedbackCreateForm");
		var url = $form.attr("action");
		$.post(url, $form.serialize(), function(data) {
			if(data.success){
				$form[0].reset();
				$("#addQa").modal('hide');
				self.page(1);
			}
		});
	}
	self.showForm = function() {
		$("#addQa").modal('show');
	}
	self.attach = function() {

	}
	self.reloadFeedback = function(id){
		$.get("feedback/list",{'currentPage':self.currentPage},function(data){
			var $feedbackList = $(data);
			var $feedbackOld = $("#feedbackList").find(".list-group-item[feedback_id="+id+"]");
			var $feedbackNew = $feedbackList.find(".list-group-item[feedback_id="+id+"]");
			$feedbackOld.html($feedbackNew.html());
			self.detail(id);
		});
	}
	self.page = function(page) {
		self.currentPage = page;
		$("#feedbackList").load("feedback/list",{'currentPage':page},function(data){
			var $item = $("#feedbackList").find(".list-group-item[feedback_id]:first");
			var id = $item.attr('feedback_id');
			if(id){
				self.detail(id);
			}
		});
	}
	self.detail = function(id) {
		var $item = $("#feedbackList").find(".list-group-item[feedback_id="+id+"]");
		$item.addClass("bg-gray");
		$item.siblings(".list-group-item").removeClass("bg-gray");
		$("#feedbackDetail").load("feedback/"+id,function(){
			self.listReplies(id)
		});
	}
	self.listReplies = function(id){
		$("#feedbackReplyList").load("feedback/replies/"+id);
	}
	
	self.reply = function(){
		if(!validateReply()){
			return;
		}
		var $form=$("#feedbackReplyForm");
		var url = $form.attr("action");
		$.post(url,$form.serialize(),function(data){
			if(data.success){
				self.listReplies(data.data);
				$("#feedbackReplyBtn").attr("disabled","disabled");
				$("#feedbackReplyContent").val('');
			}
		});
	}
	self.solve = function(id){
		jConfirm('确认要关闭这个问题吗?', '问题反馈', function(result) {
			if(!result){
				return;
			}
			$.post('feedback/solve/'+id,function(data){
				if(data.success){
					self.reloadFeedback(id);
				}else{
					jAlert(data.message);
				}
			});
		});
	}
	$(document).on("keyup","#feedbackReplyContent",function(e){
		var value = $(this).val();
		if($.trim(value)){
			$("#feedbackReplyBtn").removeAttr("disabled");
		}else{
			$("#feedbackReplyBtn").attr("disabled","disabled");
		}
	});
	return self;
})(feedbacks);
// var editor;
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
	editor.setContent("");
	$("#userNameSpanInfo").html("");
	$("#titleSpanInfo").html("");
	$("#contentSpanInfo").html("");
	$("#submitSpanInfo").html("");
	$("#chkUsername").prop("checked", false);
	$("#userName").attr("disabled", false);
}
function validateFeed() {
	var result = true;
	var userName = $.trim($("#userName").val());
	var email = $.trim($("#email").val());
	var title = $.trim($("#title").val());
	var content = $.trim(editor.getContentTxt());
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
feedbacks.page(1);