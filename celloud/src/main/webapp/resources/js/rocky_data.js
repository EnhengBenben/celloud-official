var rockyData = (function(rockyData) {
	var self = rockyData || {};
	$(document).off("click", "#rocky_data_page [data-click='pagination-btn']");
	$(document).off("change", "#rocky_data_page #page-size-sel");
	$(document).off("keyup", "#data-sample-filter");
	$(document).off("keyup", "#data-condition-input");
	$(document).off("change", "#rocky_data_page #page-size-sel");
	$(document).off("click", "#data-condition-find");
	$(document).off("click", "a[id^='dataSortBtn-']");
	$(document).on("click", "a[id^='dataSortBtn-']", function() {
		var id = $(this).attr("id");
		var sort = id.split('-');
		var sidx = sort[1];
		var sord = sort[2] || 'desc';
		var size = $("#rocky_data_page #page-size-sel").val();
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
	$(document).on("click", "#data-condition-find", function(event) {
		var condition = $("#data-condition-input").val();
		if(condition){
			self.filter.condition(condition);
		}
	});
	$(document).on("change", "#rocky_data_page #page-size-sel", function() {
		var sample = self.filter.param.sample;
		var condition = self.filter.param.condition;
		var sidx = self.sort.sidx;
		var sord = self.sort.sord;
		self.pageDatas(1, $(this).val(), sample, condition, sidx, sord);
	});
	$(document).on("click", "#rocky_data_page [data-click='pagination-btn']",
			function() {
				var page = $(this).data("page");
				var size = $("#rocky_data_page #page-size-sel").val();
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
		var url = contextPath + "/data/rocky/list";
		$("#container").load(url, params, function() {
			if ($.isFunction(f)) {
				f();
			}
		});
	};
	self.filter = {
		sample : function(sample) {
			var size = $("#rocky_data_page #page-size-sell").val();
			$("#data-condition-input").val('');
			var sidx = self.sort.sidx;
			var sord = self.sort.sord;
			self.pageDatas(1, size, sample, null, sidx, sord, function() {
				var val = $("#data-sample-filter").val();
				$("#data-sample-filter").focus().val(val);
			});
		},
		condition : function(condition) {
			var size = $("#rocky_data_page #page-size-sell").val();
			var sidx = self.sort.sidx;
			var sord = self.sort.sord;
			self.pageDatas(1, size, null, condition, sidx, sord);
		},
		param : {

		}
	};
	return self;
})(window.rockyData);