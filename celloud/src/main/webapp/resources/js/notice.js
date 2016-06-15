var notices = (function(object) {
	var self = object || {};
	var page = {
			'message':{'currentPage':1,'pageSize':10},
			'notice':{'currentPage':1,'pageSize':10}
	};
	self.pageMessage = function(currentPage, pageSize) {
		page.message.currentPage = currentPage;
		page.message.pageSize = pageSize;
		pageSize = pageSize||10;
		var url = CONTEXT_PATH + "/notice/list/message";
		$("#userMessage").load(url + " #userMessage", {
			"currentPage" : currentPage,
			"pageSize" : pageSize
		});
	};
	self.pageNotice = function(currentPage, pageSize) {
		page.notice.currentPage = currentPage;
		page.notice.pageSize = pageSize;
		pageSize = pageSize||10;
		var url = CONTEXT_PATH + "/notice/list/notice";
		$("#userNotice").load(url + " #userNotice", {
			"currentPage" : page,
			"pageSize" : pageSize
		});
	};
	self.showUserMessage = function(){
		$("#userMessageTab").siblings().removeClass("active");
		$("#userMessageTab").addClass("active");
		$("#userMessage").siblings().addClass("hide");
		$("#userMessage").removeClass("hide");
		$("#subtitle").html("消息提醒");
	};
	self.showUserNotice = function(){
		$("#userNoticeTab").siblings().removeClass("active");
		$("#userNoticeTab").addClass("active");
		$("#userNotice").siblings().addClass("hide");
		$("#userNotice").removeClass("hide");
		$("#subtitle").html("系统公告");
	};
	$(".mainpage").on("click","#messageListCheckAll",function(){
		if($(this).is(":checked")){
			$("#messageListForm [name='noticeIds']").prop('checked',true);
		}else{
			$("#messageListForm [name='noticeIds']").removeAttr("checked");
		}
		$("#messageListForm [name='noticeIds']").change();
	});
	$(".mainpage").on("click","#messageListForm [name='noticeIds']",function(){
		if(!$(this).is(":checked")){
			$("#messageListCheckAll").removeAttr("checked");
		}else{
			if($("#messageListForm input[name='noticeIds']:not(:checked)").length == 0){
				$("#messageListCheckAll").prop('checked',true);
			}
		}
	});
	$(".mainpage").on("change","#messageListForm [name='noticeIds']",function(){
		if($("#messageListForm input[name='noticeIds']:checked").length != 0){
			$("#messageDeleteBtn").removeAttr("disabled");
			$("#messageReadBtn").removeAttr("disabled");
		}else{
			$("#messageDeleteBtn").attr("disabled","disabled");
			$("#messageReadBtn").attr("disabled","disabled");
		}
	});
	self.readMessage = function(){
		$.post(CONTEXT_PATH+"/notice/read",$("#messageListForm").serialize(),function(response){
			if(response.success){
				self.pageMessage(page.message.currentPage,page.message.pageSize);
				$("#messages-menu").load(CONTEXT_PATH + "/notice/lastUnread/message");
			}
		});
	};
	self.readAllMessage = function(){
		$.post(CONTEXT_PATH+"/notice/readAll",{},function(response){
			if(response.success){
				self.pageMessage(page.message.currentPage,page.message.pageSize);
				$("#messages-menu").load(CONTEXT_PATH + "/notice/lastUnread/message");
			}
		});
	};
	self.deleteMessage = function(){
		$.post(CONTEXT_PATH+"/notice/delete",$("#messageListForm").serialize(),function(response){
			if(response.success){
				self.pageMessage(page.message.currentPage,page.message.pageSize);
				$("#messages-menu").load(CONTEXT_PATH + "/notice/lastUnread/message");
			}
		});
	};
	return self;
})(notices);