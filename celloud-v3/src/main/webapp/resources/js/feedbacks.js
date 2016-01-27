/**
 * 问题反馈，登录后的
 */
var feedbacks = (function(feedbacks) {
	var self = feedbacks || {};
	self.currentPage = 1;
	var uploader = null;
	var resetFeedbackFormState = function(){
		var $form = $("#feedbackCreateForm");
		$form.find(".form-group").removeClass("has-error");
		$form.find("[name=attachments]").remove();
		$("#attachmentUploading").siblings("img").remove();
		self.initUploader();
		$form.find(".help-block").html("");
	}
	var validateFeedback = function(){
		var $form = $("#feedbackCreateForm");
		$form.find(".form-group").removeClass("has-error");
		$form.find(".help-block").html("");
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
	self.initUploader = function(){
		if(uploader){
			uploader.destroy();
		}
		uploader = new plupload.Uploader({
			browse_button : 'uploadAttachmentBtn', 
			url : "../feedback/attach",
			container:'attachmentContainer',
	        chunk_size : "1mb",
	        file_data_name:'file',
	        filters : {
				max_file_size : "2mb",
				mime_types: [
				    {title : "Image files", extensions : "jpg,jpeg,png" }
				],
				prevent_duplicates : true //不允许选取重复文件
			},
			multi_selection:false,
	        flash_swf_url : '../plugins/plupload-2.1.2/Moxie.swf',
	        init:{
	        	PostInit: function() {
	        		$("#feedbackCreateForm").find("#attachment-group").removeClass("hide");
	        		$("#feedbackCreateForm").find("#attachment-group").show();
	        		$("#feedbackCreateForm").find("#warning-group").hide();
	            },
	        	FilesAdded: function(up, files) {
	        		$("#attachmentUploading").show();
	        		uploader = up;
	        		up.start();
	            },
	            QueueChanged:function(up){
	            	uploader = up;
	            	up.disableBrowse(up.files.length>=5);
	            },
	            FileUploaded:function(up,file,response){
	            	uploader = up;
	            	$("#attachmentUploading").hide();
	            	var $img = $("<img />");
	            	$img.attr("src","feedback/attach/temp?file="+response.response);
	            	$img.addClass("img-thumbnail");
	            	$img.addClass("feedback-attachment");
	            	$img.click(function(){
	            		self.showAttachment(response.response,file.id);
	            	});
	            	$img.attr("name","feedback_attachment_"+response.response)
	            	var $input = $('<input type="hidden" name="attachments"/>');
	            	$input.val(response.response);
	            	$("#attachmentUploading").before($img);
	            	$("#feedbackCreateForm").append($input);
	            }
	        }
	    });
		uploader.init();
	}
	self.create = function() {
		if(!validateFeedback()){
			return;
		}
		var $button = $("#addQa").find(".modal-footer button.btn-primary");
		$button.attr("disabled","disabled");
		var $form = $("#feedbackCreateForm");
		var url = $form.attr("action");
		$.post(url, $form.serialize(), function(data) {
			if(data.success){
				$("#addQa").modal('hide');
				$button.removeAttr("disabled");
				self.page(1);
			}
		});
	}
	self.showForm = function() {
		$("#feedbackCreateForm")[0].reset();
		resetFeedbackFormState();
		$("#addQa").modal('show');
	}
	self.showAttachment=function(filename,temp){
		var $delButton = $("#showAttachment").find(".modal-footer button.btn-danger");
		$("#showAttachment").find(".modal-body input[name='attachment_file_id']").val(temp?temp:"");
		$("#showAttachment").find(".modal-body input[name='attachment_file_name']").val(temp?filename:"");
		$delButton.click(function(){
			var id = $("#showAttachment").find(".modal-body input[name='attachment_file_id']").val();
			var name = $("#showAttachment").find(".modal-body input[name='attachment_file_name']").val();
			self.removeAttachment(id,name);
		});
		var url = "feedback/attach"+(temp?"/temp":"")+"?file="+filename;
		$("#showAttachment").find(".modal-body img").attr("src",url);
		$("#showAttachment").find(".modal-body a").attr("href",url);
		$("#showAttachment").modal("show");
	}
	self.removeAttachment = function(id,filename){
		$("#attachmentUploading").siblings("img[name='feedback_attachment_"+filename+"']").remove();
		$("#feedbackCreateForm").find("input[name='attachments']").each(function(index,item){
			if($(this).val()==filename){
				$(this).remove();
			}
		});
		var file = uploader.getFile(id);
		uploader.removeFile(file);
		$("#showAttachment").modal("hide");
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
				self.initUploader();
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
feedbacks.page(1);