(function() {
	celloudApp.service("noticeService", function($resource) {
		var actions = {
				listNotices:{method:'get',params:{type:'notice',currentPage:'1',pageSize:'10'}},
				listMessage:{method:'get',params:{type:'message',currentPage:'1',pageSize:'10'}},
				read:{url:"notice/read",method:'post',params:{noticeIds:'@noticeIds'}},
				readNotices:{url:'notice/readAll/notice',method:'post'},
				readMessage:{url:'notice/readAll/message',method:'post'},
				deleteNotice:{url:'notice/delete',method:'post',params:{noticeIds:'@noticeIds'}}				
		};
		return $resource("notice/list/:type",{},actions);
	});
	celloudApp.service("messageService", function($resource,$http){
		this.getUserSetting = function(){
			return $resource("message/category/setting");
		}
		this.insertUserSetting = function(targetName, targetVal, relatId){
			return $http({method:"POST", url:"message/category/insert", data:$.param({"targetName":targetName, "targetVal" : targetVal, "relatId":relatId}),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
		this.updateUserSetting = function(targetName, targetVal, relatId){
			return $http({method:"POST", url:"message/category/update", data:$.param({"targetName":targetName, "targetVal" : targetVal, "relatId":relatId}),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
	});
})();