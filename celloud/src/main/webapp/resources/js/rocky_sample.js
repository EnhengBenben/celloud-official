var rockySamples = (function(samples) {
	var self = samples || {};
	$(document).off("click", "#sampleAddBtn");
	$(document).on("click", "#sampleAddBtn", function() {
		self.add();
	});
	$(document).off("click", "#sample-commit");
	$(document).on('click', "#sample-commit", function() {
		self.commit();
	});
	$(document).off("click", "#sample-cancel");
	$(document).on('click', "#sample-cancel", function() {
		self.cancel();
	});
	$(document).off("click", "[data-click='del-sample']");
	$(document).on("click", "[data-click='del-sample']", function() {
		self.del($(this).data("id"));
	});
	$(document).on("click", "#close-error", function() {
		self.error.hide();
	});
	$(document).off("keyup", "#sampleInput");
	$(document).on("keyup", "#sampleInput", function(event) {
		if (event.keyCode == 13) {
			console.log('sample input enter...');
			self.add();
		}
	});
	self.add = function() {
		var sampleName = $("#sampleInput").val();
		sampleName = $.trim(sampleName);
		if (sampleName == "") {
			return
		}
		$.get("sample/rocky/addSample", {
			"sampleName" : sampleName
		}, function(result) {
			if (result == 1) {
				self.list();
				$("#sampleInput").val("");
			} else if (result == 2) {
				self.error.show();
				$("#sampleInput").select();
			}
		});
	};
	self.error = {
		show : function() {
			$("#sample-error").removeClass("hide");
		},
		hide : function() {
			$("#sample-error").addClass("hide");
		}
	};
	self.commit = function() {
		var params = $("#sample-form").serialize();
		if (params.length <= 0) {
			return;
		}
		$.post("sample/rocky/commitSamples", params, function(result) {
			self.list();
		});
	};
	self.list = function() {
		var url = contextPath + "/sample/rocky/sampleList";
		url = url + " #rocky-sample-list tr";
		$("#rocky-sample-list").load(url, function() {
			$("#sampleInput").val('');
			$("#sampleInput").select();
			self.error.hide();
		});
	};
	self.del = function(sampleId) {
		$.post("sample/rockey/deleteList", {
			sampleIds : sampleId
		}, function(result) {
			self.list();
		});
	};
	self.cancel = function() {
		var param = $("#sample-form").serialize();
		if (param.length <= 0) {
			return;
		}
		$.post("sample/rockey/deleteList", param, function(result) {
			self.list();
		});
	}
	return self;
})(rockySamples || {});