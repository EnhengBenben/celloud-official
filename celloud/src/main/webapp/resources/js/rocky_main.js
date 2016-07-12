var rockySamples = (function(samples) {
	var self = samples || {};
	$(document).on("click", "#sampleAddBtn", function() {
		self.add();
	});
	$(document).on('click', "#sample-commit", function() {
		self.commit();
	});
	$(document).on('click', "#sample-cancel", function() {
		self.cancel();
	});
	$(document).on("click", "[data-click='del-sample']", function() {
		self.del($(this).data("id"));
	});
	$(document).on("click", "#close-error", function() {
		self.error.hide();
	});
	$(document).on("keyup", "#sampleInput", function(event) {
		if (event.keyCode == 13) {
			self.add();
		}
	});
	self.add = function() {
		var sampleName = $("#sampleInput").val();
		sampleName = $.trim(sampleName);
		if (sampleName == "") {
			return
		}
		$.get("sample/bsi/addSample", {
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
		$("#rocky-sample-list").load(
				"sample/rocky/sampleList #rocky-sample-list tr", function() {
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
$(function() {
	$(".common-sidebar").on("click", "a", function() {
		$base.itemBtnToggleActive($(this));
	});
	$("#upload-next").on("click", function() {
		$upload.next();
	})
	$("#to-sample-a").on("click", function() {
		$("#container").load("sample/rocky/sampleList",{},function(){
			$("#container").removeClass('hide');
			$("#upload-container").addClass('hide');
		});
		$("#container").removeClass('hide');
		$("#upload-container").addClass('hide');
		$("#common-menu-center").load("pages/rocky/common_menu_sampleinfo.jsp");
		$("#common-menu-right").load("pages/rocky/common_menu_btns.jsp");
	});
	$("#to-upload-a").on("click", function() {
		$("#upload-container").removeClass('hide');
		$("#container").addClass('hide');
		$("#common-menu-center").html("");
		$("#common-menu-right").load("pages/rocky/common_menu_search.jsp");
	});
	$("#to-data-a").on("click", function() {
		$("#container").load("pages/rocky/data_main.jsp",{},function(){
			$("#container").removeClass('hide');
			$("#upload-container").addClass('hide');
		});
		$("#common-menu-center").html("");
		$("#common-menu-right").load("pages/rocky/common_menu_search.jsp");
	});
	$("#to-report-a").on("click", function() {
		$("#container").load("pages/rocky/report_main.jsp",function(){
			$("#container").removeClass('hide');
			$("#upload-container").addClass('hide');
		});
		$("#common-menu-center").html("");
		$("#common-menu-right").load("pages/rocky/common_menu_search.jsp");
	});
	$("#to-sample-a").trigger("click");
});
