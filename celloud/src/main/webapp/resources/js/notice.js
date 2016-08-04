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
		pageSize = pageSize||10;
		page.message.currentPage = currentPage;
		page.message.pageSize = pageSize;
		var url = CONTEXT_PATH + "/notice/list/message";
		$("#userMessage").load(url + " #userMessage", {
			"currentPage" : currentPage,
			"pageSize" : pageSize
		});
	};
	/**
	 * 分页查询所有站内消息
	 */
	self.pageNotice = function(currentPage, pageSize) {
		pageSize = pageSize||5;
		page.notice.currentPage = currentPage;
		page.notice.pageSize = pageSize;
		var url = CONTEXT_PATH + "/notice/list/notice";
		$("#userNotice").load(url + " #userNotice", {
			"currentPage" : currentPage,
			"pageSize" : pageSize
		},function(){
			self.showNotice();
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
		self.pageNotice();
	};
	/**
	 * 展示单个站内消息
	 */
	self.showNotice = function(noticeId) {
		var $noticeBtn = null;
		if(noticeId){
			$noticeBtn = $("#userNotice .list-group button.list-group-item[_data_notice_id='"+noticeId+"']");
		}else{
			$noticeBtn = $("#userNotice .list-group button.list-group-item:first");
			noticeId = $noticeBtn.attr("_data_notice_id");
		}
		$noticeBtn.addClass("bg-gray");
		var $badge = $noticeBtn.find(".badge");
		$badge.removeClass("fa-folder-o");
		$badge.addClass("fa-folder-open-o");
		$badge.addClass("bg-gray");
		$noticeBtn.removeClass("unread");
		$noticeBtn.siblings().removeClass("bg-gray");
		$("#noticeDetail").load(CONTEXT_PATH+"/notice/detail",{"noticeId":noticeId},function(data){
			$("#notices-menu").load(CONTEXT_PATH + "/notice/lastUnread/notice");
		});
	};
	/**
	 * 删除一个站内消息
	 */
	self.deleteNotice = function(noticeId){
		if(!window.confirm("确定要删除这条站内消息吗？")){
			return;
		}
		$.post(CONTEXT_PATH+"/notice/delete",{"noticeIds":noticeId},function(response){
			if(response.success){
				self.pageNotice(page.notice.currentPage,page.notice.pageSize);
			}
		});
	};
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
		$.post(CONTEXT_PATH+"/notice/readAll/message",{},function(response){
			if(response.success){
				self.pageMessage(page.message.currentPage,page.message.pageSize);
				$("#messages-menu").load(CONTEXT_PATH + "/notice/lastUnread/message");
			}
		});
	};
	/**
	 * 将全部站内消息置为已读
	 */
	self.readAllNotice = function(){
		$.post(CONTEXT_PATH+"/notice/readAll/notice",{},function(response){
			if(response.success){
				self.pageNotice(page.message.currentPage,page.message.pageSize);
				$("#messages-menu").load(CONTEXT_PATH + "/notice/lastUnread/notice");
			}
		});
	};
	/**
	 * 删除选中的消息，无论是否已读，都可以直接删除
	 */
	self.deleteMessage = function(){
		if(!window.confirm("确定要删除选中的所有消息吗？")){
			return;
		}
		$.post(CONTEXT_PATH+"/notice/delete",$("#messageListForm").serialize(),function(response){
			if(response.success){
				self.pageMessage(page.message.currentPage,page.message.pageSize);
				$("#messages-menu").load(CONTEXT_PATH + "/notice/lastUnread/message");
			}
		});
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
	$('.create-switch').bootstrapSwitch({
		onColor : 'success',
		offColor : 'danger',
		onText : '开',
		offText : '关',
		onSwitchChange : function(event, state){
			var flag = $(this).parents("tr").find("input[name='flag']").val();
			if(flag==0){
				var $tr = $(this).parents("tr");
				var obj0 = $tr.find("input:eq(0)");
				var email = obj0.prop("disabled")==true?2:(obj0.prop("checked")==false?0:1);
				var obj1 = $tr.find("input:eq(1)");
				var window = obj1.prop("disabled")==true?2:(obj1.prop("checked")==false?0:1);
				var obj2 = $tr.find("input:eq(2)");
				var wechat = obj2.prop("disabled")==true?2:(obj2.prop("checked")==false?0:1);
				var messageCategoryId = $tr.find("input:eq(3)").val();
				var data = email + "," + window + "," + wechat + "," + messageCategoryId;
				// 插入用户数据
				$.post("message/category/insert",{"data":data},function(data){
					if(data>0){
						$tr.find("input[name='flag']").val(1);
					}
				});
			}else{
				var targetName = $(this).attr("name");
				var targetVal = $(this).prop("checked")==false?0:1;
				var relatId = $(this).parents("tr").find("input[name='messageCategoryId']").val();
				// 更新用户数据
				$.post("message/category/update",{"targetName":targetName, "targetVal" : targetVal, "relatId":relatId},function(data){
					
				});
			}
		}
	});
	return self;
})(notices);