(function() {
	celloudApp.factory("tipsModalService", function() {
		$("#tips-modal").modal("show");
	});
	celloudApp.service("commonService",function($resource){
		var self = this;
		self.getUserInfo = function(){
			return $resource("user/info");
		}
		self.messages = $resource("notice/lastUnread/message");
		self.notices = $resource("notice/lastUnread/notice");
	});
}());