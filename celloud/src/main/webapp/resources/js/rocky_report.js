var $report = {
	showReport : function(dataKey,projectId, appId ) {
		$("#common-menu-right").html("");
		var url = contextPath + "/report/rocky/data/report";
		$("#container").load(url, {
			dataKey : dataKey,
			appId : appId,
			projectId : projectId
		});
	}
}
var rockyReport = (function(rockyReport) {
	var self = rockyReport || {};
	$(document).off("click", "#rocky_report_page [data-click='pagination-btn']");
	$(document).off("change", "#rocky_report_page #page-size-sel");
	$(document).off("click", "#report-condition-find");
	$(document).off("keyup", "#report-sample-filter");
	$(document).off("keyup", "#report-condition-input");
	$(document).off("click", "a[id^='reportSortBtn-']");
	$(document).on("click","#batch-more",function(){
		if($("#batch-lists").hasClass("show-more")){
			$("#batch-lists").removeClass("show-more");
			$("#batch-more span").html("更多");
            $("#batch-more i").removeClass("fa-chevron-up").addClass("fa-chevron-down");
		}else{
            $("#batch-lists").addClass("show-more");
            $("#batch-more span").html("收起");
            $("#batch-more i").removeClass("fa-chevron-down").addClass("fa-chevron-up");
		}
	});
	$(document).on("click","#batch-multiselect",function(){
		$("#batch-lists").addClass("show-more");
		$("#batch-more span").html("收起");
		$("#batch-more i").removeClass("fa-chevron-down").addClass("fa-chevron-up");
		$("#batch-lists .checkbox").removeClass("checkbox-ed").addClass("checkbox-un");
		$("#report-multibatch-search").addClass("disabled");
		$("#report-multibatch-search").attr("disabled",true);
		$("#batch-more").addClass("disabled");
		$("#batch-more").attr("disabled",true);
	});
	$(document).on("click","[data-click='reset-multiselect']",function(){
		var selectorline = $(this).parent().parent().parent().parent();
		$(selectorline).removeClass("select-more");
		$(selectorline).find(".sl-val").removeClass("show-more");
		$(selectorline).find(".checkbox").addClass("hide").addClass("checkbox-un").removeClass("checkbox-ed");
		$(selectorline).find(".multisl-btns").addClass("hide");
		if($("#batch-more").hasClass("disabled")){
			$("#batch-more").removeClass("disabled");
			$("#batch-more").attr("disabled",false);
			$("#batch-more span").html("更多");
			$("#batch-more i").removeClass("fa-chevron-up").addClass("fa-chevron-down");
		}
	});
	$(document).on("click","[data-click='report-select-more']",function(){
		if($(this).attr("id")!='batch-multiselect'){
			$("#batch-more").removeClass("disabled");
			$("#batch-more").attr("disabled",false);
			$("#batch-more span").html("更多");
			$("#batch-more i").removeClass("fa-chevron-up").addClass("fa-chevron-down");
		}
        $(".selector-line").removeClass("select-more");
        $("#report-multibatch-search").addClass("disabled");
        $("#report-multibatch-search").attr("disabled",true);
        $(".selector-line .checkbox").removeClass("checkbox-ed").addClass("checkbox-un").addClass("hide");
        $(".selector-line").find("[name='sl-confirm']").addClass("disabled");
        $(".selector-line").find("[name='sl-confirm']").attr("disabled",true);
        $(".selector-line").find(".multisl-btns").addClass("hide");
        var selectorline = $(this).parent().parent().parent();
        $(selectorline).addClass("select-more");
        $(selectorline).find(".sl-val").addClass("show-more");
        $(selectorline).find(".checkbox").removeClass("hide");
        $(selectorline).find(".multisl-btns").removeClass("hide");
     });
	$(document).on("click","#batch-lists .sl-val-content",function(){
		$(this).find(".checkbox").toggleClass("checkbox-un");
		$(this).find(".checkbox").toggleClass("checkbox-ed");
		if($("#batch-lists .checkbox-ed").size() > 0){
			$("#report-multibatch-search").removeClass("disabled");
			$("#report-multibatch-search").attr("disabled",false);
		}else{
			$("#report-multibatch-search").addClass("disabled");
			$("#report-multibatch-search").attr("disabled",true);
		}
	});
	$(document).on("click","#period-lists .sl-val-content",function(){
		$(this).find(".checkbox").toggleClass("checkbox-un");
		$(this).find(".checkbox").toggleClass("checkbox-ed");
		if($("#period-lists .checkbox-ed").size() > 0){
			$("#report-multiperiod-search").removeClass("disabled");
			$("#report-multiperiod-search").attr("disabled",false);
		}else{
			$("#report-multiperiod-search").addClass("disabled");
			$("#report-multiperiod-search").attr("disabled",true);
		}
	});
	$("body").on("click","[data-click='report-date-search']",function(){
		self.pageReports({beginDate :$("#report-begindate-search").val(),endDate:$("#report-enddate-search").val()});
	});
	/**
	 * 点击单个标签时的搜索
	 */
	$("body").on("click","[data-click='report-batch-search']",function(){
		if(!$("#batch-sl").hasClass("select-more")){
			self.pageReports({batches :[$(this).text()]});
			$("#selected-batch span").html($(this).text());
			$("#selected-batch").removeClass("hide");
			$("#to-sl-batch").addClass("hide");
		}
	});
	/**
	 * 取消标签时的搜索
	 */
	$(document).on("click","#clear-sl-batch",function(){
		$("#selected-batch").addClass("hide");
        $("#to-sl-batch").removeClass("hide");
        $("#batch-more").removeClass("disabled");
        $("#batch-more").attr("disabled",false);
        $("#batch-lists").find(".multisl-btns").addClass("hide");
        self.pageReports({batches :[]});
	});
	/**
	 * 点击单个状态时的搜索
	 */
	$("body").on("click","[data-click='report-period-search']",function(){
		if(!$("#period-sl").hasClass("select-more")){
			self.pageReports({periods :[ $(this).find("input").val()]});
			$("#selected-period span").html($(this).find("span").html());
			$("#selected-period").removeClass("hide");
			$("#to-sl-period").addClass("hide");
		}
	});
	/**
	 * 取消状态时的搜索
	 */
	$(document).on("click","#clear-sl-period",function(){
		$("#selected-period").addClass("hide");
		$("#to-sl-period").removeClass("hide");
		self.pageReports({periods :[]});
	});
	/**
	 * 标签多选
	 */
	$(document).on("click","#report-multibatch-search",function(){
		var show_val = [];
		$("#batch-lists .checkbox-ed").each(function(){
			show_val.push($(this).next().text());
		});
		self.pageReports({batches : show_val});
		$("#selected-batch span").html(show_val.toString());
		$("#selected-batch").removeClass("hide");
		$("#to-sl-batch").addClass("hide");
		$("#batch-sl").removeClass("select-more");
		$("#batch-lists").removeClass("show-more");
        $("#batch-lists").find(".checkbox").addClass("hide");
        $("#batch-more span").html("更多");
        $("#batch-more i").removeClass("fa-chevron-up").addClass("fa-chevron-down");
	});
	/**
	 * 状态多选
	 */
	$(document).on("click","#report-multiperiod-search",function(){
		var show_val = [];
		var periods = [];
		$("#period-lists .checkbox-ed").each(function(){
			periods.push($(this).next().find("input[type='hidden']").val());
			show_val.push($(this).next().find("span").html());
		});
		self.pageReports({periods :periods});
		$("#selected-period span").html(show_val.toString());
		$("#selected-period").removeClass("hide");
		$("#to-sl-period").addClass("hide");
		$("#period-sl").removeClass("select-more");
		$("#period-sl").find(".checkbox").addClass("hide");
		$("#period-sl").find(".multisl-btns").addClass("hide");
		$("#period-lists .checkbox").removeClass("checkbox-ed").addClass("checkbox-un");
	});
	/**
	 * 排序按钮
	 */
	$(document).on("click", "a[id^='reportSortBtn-']", function() {
		var id = $(this).attr("id");
		var sort = id.split('-');
		self.pageReports({sidx : sort[1],sord : sort[2] || 'desc'});
	});
	/**
	 * 改变分页大小
	 */
	$(document).on("change", "#rocky_report_page #page-size-sel", function() {
		self.pageReports({size : $(this).val()});
	});
	/**
	 * 样本编号模糊搜索
	 */
	$(document).on("keyup", "#report-sample-filter", function(event) {
		if (event.keyCode == 13) {
			$("#report-condition-input").val('');
			self.pageReports({sample : $(this).val(),condition:null}, function() {
				var val = $("#report-sample-filter").val();
				$("#report-sample-filter").focus().val(val);
			});
		}
	});
	/**
	 * 右上角的搜索
	 */
	$(document).on("keyup", "#report-condition-input", function(event) {
		if (event.keyCode == 13) {
			self.pageReports({condition : $(this).val(),sample:null});
		}
	});
	/**
	 * 右上角的搜索
	 */
	$(document).on("click", "#report-condition-find", function(event) {
		self.pageReports({condition : $("#report-condition-input").val(),sample:null});
	});
	/**
	 * 分页按钮
	 */
	$(document).on("click", "#rocky_report_page [data-click='pagination-btn']",function() {
		self.pageReports({page : $(this).data("page")});
	});
	self.sort = {};
	self.params = {};
	self.changeSettings = function(options){
		self.params = self.params||{};
		if (options.sample) {
			delete self.params.condition;
			self.params = {
				sample : options.sample
			};
		} else if (options.condition) {
			delete self.params.sample;
			self.params = {
				condition :  options.condition
			};
		}
		if (options.sidx && options.sord) {
			self.sort = {
				sidx :  options.sidx,
				sord :  options.sord
			};
		}
		if('batches' in options){
			self.params.batches=options.batches;
		}
		if('periods' in options){
			self.params.periods=options.periods;
		}
		if('beginDate' in options){
			self.params.beginDate=options.beginDate;
		}
		if('endDate' in options){
			self.params.endDate=options.endDate;
		}
	}
	self.pageReports = function(options, f) {
		var params = {
			page : 1,
			size : $("#rocky_report_page #page-size-sel").val()||20,
			sidx : self.sort.sidx,
			sord : self.sort.sord,
			sample : self.params.sample,
			beginDate : self.params.beginDate,
			endDate : self.params.endDate,
			condition : self.params.condition,
			batches : self.params.batches,
			periods : self.params.periods
		};
		self.changeSettings(options);
		$.extend(params, options);
		var temp = [];
		for(var key in params){
			var param = params[key];
			if($.isArray(param)){
				for(var t in param){
					temp.push(key+"="+$.trim(param[t]));
				}
			}else if(param){
				temp.push(key+"="+params[key]);
			}
		}
		var url = contextPath + "/report/rocky/reportMain #rocky-report-list";
		$("#rocky-report-list").load(url, temp.join("&"), function() {
			if ($.isFunction(f)) {
				f();
			}
		});
	};
	return self;
})(window.rockyReport);