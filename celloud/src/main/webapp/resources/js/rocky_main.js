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
		var url = contextPath+"/data/rocky/list";
		$("#container").load(contextPath+"/data/rocky/list",{},function(){
			$("#container").removeClass('hide');
			$("#upload-container").addClass('hide');
		});
		$("#common-menu-center").html("");
		$("#common-menu-right").load("pages/rocky/common_menu_search.jsp");
	});
	$("#to-report-a").on("click", function() {
		$("#container").load(contextPath+"/report/rocky/reportMain",function(){
			$("#container").removeClass('hide');
			$("#upload-container").addClass('hide');
		});
		$("#common-menu-center").html("");
		$("#common-menu-right").load("pages/rocky/common_menu_search.jsp");
	});
	$("#to-sample-a").trigger("click");
});
