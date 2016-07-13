var rockyData = (function(rockyData) {
	var self = rockyData || {};
	$(document).off("click", "#rocky_data_page [data-click='pagination-btn']");
	$(document).off("change", "#rocky_data_page #page-size-sel");
	$(document).on("change", "#rocky_data_page #page-size-sel",function(){
		self.pageDatas(1,$("#page-size-sel").val());
	});
	$(document).on("click", "#rocky_data_page [data-click='pagination-btn']",
			function() {
				var page = $(this).data("page");
				var size = $("#page-size-sel").val();
				self.pageDatas(page,size);
			});
	self.pageDatas = function(page, size) {
		$("#container").load(contextPath + "/data/rocky/list", {
			page : page,
			size : size
		});
	};
	return self;
})(window.rockyData);