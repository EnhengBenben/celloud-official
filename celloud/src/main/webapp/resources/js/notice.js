var notices = (function(object) {
	var self = object || {};
	var page = {
			'message':{'currentPage':1,'pageSize':10},
			'notice':{'currentPage':1,'pageSize':10}
	};
	/**
	 * 分页查询所有消息提醒
	 */
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
	/**
	 * 切换页签到消息提醒
	 */
	self.showUserMessage = function(){
		$("#userMessageTab").siblings().removeClass("active");
		$("#userMessageTab").addClass("active");
		$("#userMessage").siblings().addClass("hide");
		$("#userMessage").removeClass("hide");
		$("#subtitle").html("系统消息");
		self.pageMessage();
	};
	/**
	 * 切换页签到系统公告
	 */
	self.showUserNotice = function(){
		$("#userNoticeTab").siblings().removeClass("active");
		$("#userNoticeTab").addClass("active");
		$("#userNotice").siblings().addClass("hide");
		$("#userNotice").removeClass("hide");
		$("#subtitle").html("站内消息");
		self.pageNotice()
	};
	/**
	 * 列表check all 的checkbox事件
	 */
	$(".mainpage").on("click","#messageListCheckAll",function(){
		if($(this).is(":checked")){
			$("#messageListForm [name='noticeIds']").prop('checked',true);
		}else{
			$("#messageListForm [name='noticeIds']").removeAttr("checked");
		}
		//手动触发一下每个checkbox的change事件，用来改变按钮状态，不加first过滤，会触发多次，这里触发一次就够了。
		$("#messageListForm [name='noticeIds']:first").change();
	});
	/**
	 * 列表中每个checkbox的事件
	 */
	$(".mainpage").on("click","#messageListForm [name='noticeIds']",function(){
		if(!$(this).is(":checked")){
			$("#messageListCheckAll").removeAttr("checked");
		}else{
			if($("#messageListForm input[name='noticeIds']:not(:checked)").length == 0){
				$("#messageListCheckAll").prop('checked',true);
			}
		}
	});
	/**
	 * 监听每个checkbox的cheng事件，用来判断【已读】【删除】两个按钮的disabled状态
	 */
	$(".mainpage").on("change","#messageListForm [name='noticeIds']",function(){
		if($("#messageListForm input[name='noticeIds']:checked").length != 0){
			$("#messageDeleteBtn").removeAttr("disabled");
		}else{
			$("#messageDeleteBtn").attr("disabled","disabled");
		}
		if($("#messageListForm tr[read-state='0'] input[name='noticeIds']:checked").length != 0){
			$("#messageReadBtn").removeAttr("disabled");
		}else{
			$("#messageReadBtn").attr("disabled","disabled");
		}
	});
	/**
	 * 将选中的消息置为已读
	 */
	self.readMessage = function(){
		$.post(CONTEXT_PATH+"/notice/read",$("#messageListForm").serialize(),function(response){
			if(response.success){
				self.pageMessage(page.message.currentPage,page.message.pageSize);
				$("#messages-menu").load(CONTEXT_PATH + "/notice/lastUnread/message");
			}
		});
	};
	/**
	 * 将全部消息置为已读
	 */
	self.readAllMessage = function(){
		$.post(CONTEXT_PATH+"/notice/readAll",{},function(response){
			if(response.success){
				self.pageMessage(page.message.currentPage,page.message.pageSize);
				$("#messages-menu").load(CONTEXT_PATH + "/notice/lastUnread/message");
			}
		});
	};
	/**
	 * 删除选中的消息，无论是否已读，都可以直接删除
	 */
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