var $report = {
	showReport : function() {
		$("#container").load("pages/rocky/report_data_main.jsp");
	}
}
var rockyReport = (function(rockyReport) {
	var self = rockyReport || {};
	$(document)
			.off("click", "#rocky_report_page [data-click='pagination-btn']");
	$(document).off("change", "#rocky_report_page #page-size-sel");
	$(document).off("keyup", "#report-sample-filter");
	$(document).off("keyup", "#report-condition-input");
	$(document).off("click", "a[id^='reportSortBtn-']");
	$(document).on("click", "a[id^='reportSortBtn-']", function() {
		var id = $(this).attr("id");
		var sort = id.split('-');
		var sidx = sort[1];
		var sord = sort[2] || 'desc';
		var size = $("#rocky_report_page #page-size-sell").val();
		var sample = self.filter.param.sample;
		var condition = self.filter.param.condition;
		self.pageReports(1, size, sample, condition, sidx, sord);
	});
	$(document).on("change", "#rocky_report_page #page-size-sel", function() {
		var sample = self.filter.param.sample;
		var condition = self.filter.param.condition;
		var sidx = self.sort.sidx;
		var sord = self.sort.sord;
		self.pageReports(1, $(this).val(), sample, condition, sidx, sord);
	});
	$(document).on("keyup", "#report-sample-filter", function(event) {
		if (event.keyCode == 13) {
			self.filter.sample($(this).val());
		}
	});
	$(document).on("keyup", "#report-condition-input", function(event) {
		if (event.keyCode == 13) {
			self.filter.condition($(this).val());
		}
	});
	$(document).on("click", "#rocky_report_page [data-click='pagination-btn']",
			function() {
				var page = $(this).data("page");
				var size = $("#rocky_report_page #page-size-sel").val();
				var sample = self.filter.param.sample;
				var condition = self.filter.param.condition;
				var sidx = self.sort.sidx;
				var sord = self.sort.sord;
				self.pageReports(page, size, sample, condition, sidx, sord);
			});
	self.sort = {};
	self.pageReports = function(page, size, sample, condition, sidx, sord, f) {
		var params = {
			page : page,
			size : size
		};
		if (sample) {
			params.sample = sample;
			self.filter.param = {
				sample : sample
			};
		} else if (condition) {
			params.condition = condition;
			self.filter.param = {
				condition : condition
			};
		} else {
			self.filter.param = {};
		}
		if (sidx && sord) {
			params.sidx = sidx;
			params.sord = sord;
			self.sort = {
				sidx : sidx,
				sord : sord
			};
		}
		var url = contextPath + "/report/rocky/reportMain";
		$("#container").load(url, params, function() {
			if ($.isFunction(f)) {
				f();
			}
		});
	};
	self.filter = {
		sample : function(sample) {
			var size = $("#rocky_report_page #page-size-sell").val();
			$("#report-condition-input").val('');
			var sidx = self.sort.sidx;
			var sord = self.sort.sord;
			self.pageReports(1, size, sample, null, sidx, sord, function() {
				var val = $("#report-sample-filter").val();
				$("#report-sample-filter").focus().val(val);
			});
		},
		condition : function(condition) {
			var size = $("#rocky_report_page #page-size-sell").val();
			var sidx = self.sort.sidx;
			var sord = self.sort.sord;
			self.pageReports(1, size, null, condition, sidx, sord);
		},
		param : {

		}
	};
	return self;
})(window.rockyReport);