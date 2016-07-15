var rockyData = (function(rockyData) {
	var self = rockyData || {};
	$(document).off("click", "#rocky_data_page [data-click='pagination-btn']");
	$(document).off("change", "#rocky_data_page #page-size-sel");
	$(document).off("keyup", "#data-sample-filter");
	$(document).off("keyup", "#data-condition-input");
	$(document).off("change", "#rocky_data_page #page-size-sel");
	$(document).off("click", "#sort-batch-asc,#sort-batch-desc,#sort-batch");
	$(document).on("click", "#sort-batch-asc,#sort-batch-desc,#sort-batch",
			function() {
				var id = $(this).attr("id");
				var sidx = "batch";
				var sord = "asc";
				var size = $("#rocky_data_page #page-size-sell").val();
				if (id == 'sort-batch-asc') {
					sord = 'desc';
				}
				var sample = self.filter.param.sample;
				var condition = self.filter.param.condition;
				self.pageDatas(1, size, sample, condition, sidx, sord);
			});
	$(document).off("click", "#sort-date-asc,#sort-date-desc,#sort-date");
	$(document).on("click", "#sort-date-asc,#sort-date-desc,#sort-date",
			function() {
				var id = $(this).attr("id");
				var sidx = "createDate";
				var sord = "desc";
				var size = $("#rocky_data_page #page-size-sell").val();
				if (id == 'sort-date-desc') {
					sord = 'asc';
				}
				var sample = self.filter.param.sample;
				var condition = self.filter.param.condition;
				self.pageDatas(1, size, sample, condition, sidx, sord);
			});
	$(document).off("click",
			"#sort-filename-asc,#sort-filename-desc,#sort-filename");
	$(document).on("click",
			"#sort-filename-asc,#sort-filename-desc,#sort-filename",
			function() {
				var id = $(this).attr("id");
				var sidx = "filename";
				var sord = "desc";
				var size = $("#rocky_data_page #page-size-sell").val();
				if (id == 'sort-filename-desc') {
					sord = 'asc';
				}
				var sample = self.filter.param.sample;
				var condition = self.filter.param.condition;
				self.pageDatas(1, size, sample, condition, sidx, sord);
			});
	$(document).off("click",
			"#sort-filesize-asc,#sort-filesize-desc,#sort-filesize");
	$(document).on("click",
			"#sort-filesize-asc,#sort-filesize-desc,#sort-filesize",
			function() {
				var id = $(this).attr("id");
				var sidx = "filesize";
				var sord = "desc";
				var size = $("#rocky_data_page #page-size-sell").val();
				if (id == 'sort-filesize-desc') {
					sord = 'asc';
				}
				var sample = self.filter.param.sample;
				var condition = self.filter.param.condition;
				self.pageDatas(1, size, sample, condition, sidx, sord);
			});
	$(document).on("keyup", "#data-sample-filter", function(event) {
		if (event.keyCode == 13) {
			self.filter.sample($(this).val());
		}
	});
	$(document).on("keyup", "#data-condition-input", function(event) {
		if (event.keyCode == 13) {
			self.filter.condition($(this).val());
		}
	});
	$(document).on("change", "#rocky_data_page #page-size-sel", function() {
		var sample = self.filter.param.sample;
		var condition = self.filter.param.condition;
		var sidx = "";
		var sord = "";
		self.pageDatas(1, $(this).val(), sample, condition, sidx, sord);
	});
	$(document).on("click", "#rocky_data_page [data-click='pagination-btn']",
			function() {
				var page = $(this).data("page");
				var size = $("#rocky_data_page #page-size-sell").val();
				var sample = self.filter.param.sample;
				var condition = self.filter.param.condition;
				var sidx = self.sort.sidx;
				var sord = self.sort.sord;
				self.pageDatas(page, size, sample, condition, sidx, sord);
			});
	self.sort = {};
	self.pageDatas = function(page, size, sample, condition, sidx, sord, f) {
		var params = {
			page : page,
			size : size
		};
		self.page = {
			size : 20
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
		}
		if (sidx && sord) {
			params.sidx = sidx;
			params.sord = sord;
			self.sort = {
				sidx : sidx,
				sord : sord
			};
		}
		$("#container").load(contextPath + "/data/rocky/list", params,
				function() {
					if ($.isFunction(f)) {
						f();
					}
				});
	};
	self.filter = {
		sample : function(sample) {
			var size = $("#rocky_data_page #page-size-sell").val();
			$("#data-condition-input").val('');
			self.pageDatas(1, size, sample, null, null, null, function() {
				var val = $("#data-sample-filter").val();
				$("#data-sample-filter").focus().val(val);
			});
		},
		condition : function(condition) {
			var size = $("#rocky_data_page #page-size-sell").val();
			self.pageDatas(1, size, null, condition);
		},
		param : {

		}
	};
	return self;
})(window.rockyData);