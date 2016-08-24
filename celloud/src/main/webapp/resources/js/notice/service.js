(function() {
	celloudApp.service("noticeService", function($resource) {
		var actions = {
				listNotices:{method:'get',params:{type:'notice'}},
				listMessage:{method:'get',params:{type:'message'}},
				read:{url:"notice/read",method:'post',params:{noticeIds:'@noticeIds'}},
				readNotices:{url:'notice/readAll/notice',method:'post'},
				readMessage:{url:'notice/readAll/message',method:'post'},
				deleteNotice:{url:'notice/delete',method:'post',params:{noticeIds:'@noticeIds'}}				
		};
		return $resource("notice/list/:type",{},actions);
	});
})();