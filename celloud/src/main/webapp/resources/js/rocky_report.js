var $report = {
	showReport : function() {
		$("#container").load("pages/rocky/report_data_main.jsp");
	}
}
var rockyReport = (function(rockyReport) {
	var self = rockyReport || {};
	$(document).off("click", "#rocky_report_page [data-click='pagination-btn']");
	$(document).off("change", "#rocky_report_page #page-size-sel");
	$(document).on("change", "#rocky_report_page #page-size-sel",function(){
		self.pageReports(1,$(this).val());
	});
	$(document).on("click", "#rocky_report_page [data-click='pagination-btn']",
			function() {
				var page = $(this).data("page");
				var size = $("#rocky_report_page #page-size-sel").val();
				self.pageReports(page, size);
			});
	self.pageReports = function(page, size) {
		$("#container").load(contextPath + "/report/rocky/reportMain", {
			page : page,
			size : size
		});
	};
	return self;
})(window.rockyReport);