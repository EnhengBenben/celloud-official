var notices = (function(object) {
	var self = object || {};
	self.pageMessage = function(page, pageSize) {
		pageSize = pageSize||10;
		var url = CONTEXT_PATH + "/notice/list/message";
		$("#userMessage").load(url + " #userMessage", {
			"currentPage" : page,
			"pageSize" : pageSize
		});
	};
	self.pageNotice = function(page, pageSize) {
		pageSize = pageSize||10;
		var url = CONTEXT_PATH + "/notice/list/notice";
		$("#userNotice").load(url + " #userNotice", {
			"currentPage" : page,
			"pageSize" : pageSize
		});
	}
	return self;
})(notices);