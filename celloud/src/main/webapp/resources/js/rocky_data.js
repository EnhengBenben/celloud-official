var rockyData = (function(rockyData) {
	var self = rockyData || {};
	$(document).off("click", "#rocky_data_page [data-click='pagination-btn']");
	$(document).off("change", "#rocky_data_page #page-size-sel");
	$(document).off("keyup", "#data-sample-filter");
	$(document).off("change", "#rocky_data_page #page-size-sel");
	$(document).on("keyup", "#data-sample-filter", function(event) {
		if (event.keyCode == 13) {
			self.filter.sample($(this).val());
		}
	});
	$(document).on("change", "#rocky_data_page #page-size-sel", function() {
		self.pageDatas(1, $(this).val());
	});
	$(document).on("click", "#rocky_data_page [data-click='pagination-btn']",
			function() {
				var page = $(this).data("page");
				var size = $("#rocky_data_page #page-size-sell").val();
				self.pageDatas(page, size);
			});
	self.pageDatas = function(page, size) {
		$("#container").load(contextPath + "/data/rocky/list", {
			page : page,
			size : size
		});
	};
	self.filter = {
		sample : function(sample) {
			alert(sample);
		},
		keyword : function() {

		}
	};
	return self;
})(window.rockyData);