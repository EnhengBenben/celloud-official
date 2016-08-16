$(function() {
	var timeout = false;
	$.ajaxSetup({
		complete : function(request, textStatus) {
			var sessionstatus = request.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus，
			if (sessionstatus == "timeout" && !timeout) {
				timeout = true;
				alert("登录超时,请重新登录！");
				window.location.href = contextPath+"/login";
			}
		},
		cache : false
	// 关闭AJAX相应的缓存
	});
	$("#logoutBtn").click(function(){
		window.location.href = contextPath+"/logout";
	});
	$(".common-sidebar").on("click", "a", function() {
		$base.itemBtnToggleActive($(this));
	});
	$("#upload-next").on("click", function() {
		$upload.stepTwo();
	})
	$("#to-sample-a").on("click",function() {
		var url=contextPath + "/sample/rocky/sampleList";
		$("#container").load(url,{}, function() {
			$("#container").removeClass('hide');
			$("#upload-container").addClass('hide');
		});
		$("#container").removeClass('hide');
		$("#upload-container").addClass('hide');
		$("#common-menu-center").load("pages/rocky/sample/sample_menu_sampleinfo.jsp");
		$("#common-menu-right").load("pages/rocky/sample/sample_menu_btns.jsp");
	});
	$("#to-upload-a").on("click", function() {
		$("#upload-container").removeClass('hide');
		$("#container").addClass('hide');
		$("#common-menu-center").html("");
		$("#common-menu-right").html("");
	});
	$("#to-data-a").on("click", function() {
		var url = contextPath + "/data/rocky/list";
		$("#container").load(url, {}, function() {
			rockyData.filter.param={};
			$("#container").removeClass('hide');
			$("#upload-container").addClass('hide');
		});
		$("#common-menu-center").html("");
		$("#common-menu-right").load("pages/rocky/data/data_menu_search.jsp");
	});
	$("#to-report-a").on("click",function() {
		var url = contextPath + "/report/rocky/reportMain";
		$("#container").load(url,function() {
			rockyReport.params={};
			$("#container").removeClass('hide');
			$("#upload-container").addClass('hide');
		});
		$("#common-menu-center").html("");
		$("#common-menu-right").load("pages/rocky/report/report_menu_search.jsp");
	});
	$("#to-sample-a").trigger("click");
});
