(function() {
	celloudApp.controller("rockyUploadController", function($route, $location, $scope, $rootScope, uploadService) {
		$scope.stepOne = function(){
			$(".steps").addClass("hide");
			$("#upload-nav-step-three").addClass("not-reached");
			$("#upload-nav-step-two").addClass("not-reached");
			$("#upload-step-one").removeClass("hide");
			$(".step-line").addClass("not-reached")
			$(".tips").addClass('hide');
			$("#batch-info-input").val('');
			$("#upload-tip-one").removeClass("hide");
		},
		$scope.stepTwo = function() {
			$(".steps").addClass("hide");
			$(".step-line").addClass("not-reached")
			$("#upload-nav-line-one").removeClass("not-reached");
			$("#upload-nav-step-two").removeClass("not-reached");
			$("#upload-nav-step-three").addClass("not-reached");
			$("#upload-step-two").removeClass("hide");
			$(".tips").addClass('hide');
			$("#upload-tip-two").removeClass("hide");
		},
		$scope.stepThree = function(){
			$(".steps").addClass("hide");
			$("#upload-step-three").removeClass("hide");
			$("#upload-nav-line-one").removeClass("not-reached");
			$("#upload-nav-line-two").removeClass("not-reached");
			$("#upload-nav-step-three").removeClass("not-reached");
			$(".tips").addClass('hide');
			$("#upload-tip-three").removeClass("hide");
		}
		
		$scope.refreshSession = function(){
			$.get("uploadFile/checkAdminSessionTimeOut");
		};
		$scope.init = function(){
			var uploader = new plupload.Uploader({
				runtimes : 'html5,flash,silverlight,html4',
				browse_button : 'plupload-content',
				url : CONTEXT_PATH+"/uploadFile/rocky",
				chunk_size : '1mb',
				drop_element : 'plupload-content',
				filters : {
					max_file_size : '3gb',
					prevent_duplicates : true, // 不允许选取重复文件
					mime_types : [
						{title : "fastq", extensions : "fastq"},
						{title : "gz", extensions : "gz"},
					]
				},
				max_retries : 0,
				multiple_queues : true,
				flash_swf_url : '//cdn.bootcss.com/plupload/2.1.8/Moxie.swf'
			});
			uploader.init();
			$(document).on("click", "[data-click='del-upload-file']", function() {
				var id = $(this).data("id");
				$("#"+id).remove();
				var file = uploader.getFile(id);
				uploader.removeFile(file);
			});
			uploader.bind("StateChanged", function() {
				if (uploader.state === plupload.STARTED) {
					window.parent.isUploading = true;
					refresh = setInterval($scope.refreshSession,600000);
				}else if(uploader.state === plupload.STOPPED){
					window.parent.isUploading = false;
					clearInterval(refresh);
				}
			});
			uploader.bind("UploadProgress", function(uploader, file) {
				$("#" + file.id + " .percent").html(file.percent+"%");
				$("#" + file.id + " .spead").html(getSize(uploader.total.bytesPerSec)+"/s");
				$("#" + file.id + " .surplus").html(utils.formatDate((file.size-file.loaded)/uploader.total.bytesPerSec));
			});
			uploader.bind("FilesAdded", function(uploader, files) {
				$("#upload-list-table").removeClass("hide");
				$.each(files, function(index, item) {
					var $fileDom = $('<tr id="' + item.id + '"></tr>');
					$fileDom.append($('<td class="filename">' + item.name + '</td>'));
					$fileDom.append($('<td class="percent">等待上传</td>'));
					$fileDom.append($('<td class="surplus">---</td>'));
					$fileDom.append($('<td class="spead">---</td>'));
					$fileDom.append($('<td><a data-click="del-upload-file" data-id="'+item.id+'"  href="javascript:void(0)"><i class="fa fa-times-circle" aria-hidden="true"></i></a></td>'));
					$("#upload-list-tbody").append($fileDom);
				});
				uploader.start();
			});
			uploader.bind("BeforeUpload", function(uploader, file) {
				$("#" + file.id +" .percent").html("正在上传");
				uploader.setOption("multipart_params",{"tagId":$("#tag-info-input").val(),"batch":$("#batch-info-input").val(),"uniqueName":file.id});
			});
			uploader.bind("FileUploaded", function(uploader, file, response) {
				var res = JSON.parse(response.response);
				$("#" + file.id +" .percent").html("上传完成");
				if(res.run=='true'){
					delete res.run;
					$.get(CONTEXT_PATH+"/data/run",JSON.parse(response.response),function(result){
						console.log(result);
					});
				}
			});
			uploader.bind("UploadComplete",function(uploader,files){
				uploader.splice(0, uploader.files.length);
				$("#upload-list-tbody").html('');
				$("#upload-list-table").addClass("hide");
				$scope.stepThree();
			});
		};
		$scope.init();
		function getSize(fileSize){
			if(!fileSize){
				return "";
			}
			var unit = "b";
			if(fileSize > 1000){
				fileSize= fileSize / 1000;
				unit = "kb";
			}
			if(fileSize > 1000){
				fileSize= fileSize / 1000;
				unit = "mb";
			}
			return fileSize.toFixed(2)+unit;
		}
		window.onbeforeunload=function(){
			var qp=$scope.upload.total;
			var percent=qp.percent;
			if(qp.size>0&&percent<100&&percent>0){
				return "数据正在上传，您确定要关闭页面吗?"
			}
		}
	});
	celloudApp.controller("rockyReportController", function($scope, rockyReportService) {
		
		$scope.showReport = function(dataKey,projectId, appId ) {
//			$("#common-menu-right").html("");
//			var url = CONTEXT_PATH + "/report/rocky/data/report";
//			$("#container").load(url, {
//				dataKey : dataKey,
//				appId : appId,
//				projectId : projectId
//			});
		}
		
//		$(document).off("click", "#rocky_report_page [data-click='pagination-btn']");
//		$(document).off("change", "#rocky_report_page #page-size-sel");
//		$(document).off("click", "#report-condition-find");
//		$(document).off("keyup", "#report-sample-filter");
//		$(document).off("keyup", "#report-condition-input");
//		$(document).off("click", "a[id^='reportSortBtn-']");
		
		$scope.batchMore = function(){
			if($("#batch-lists").hasClass("show-more")){
				$("#batch-lists").removeClass("show-more");
				$("#batch-more span").html("更多");
	            $("#batch-more i").removeClass("fa-chevron-up").addClass("fa-chevron-down");
			}else{
	            $("#batch-lists").addClass("show-more");
	            $("#batch-more span").html("收起");
	            $("#batch-more i").removeClass("fa-chevron-down").addClass("fa-chevron-up");
			}
		}
		$scope.batchMultiselect = function(){
			$("#batch-lists").addClass("show-more");
			$("#batch-more span").html("收起");
			$("#batch-more i").removeClass("fa-chevron-down").addClass("fa-chevron-up");
			$("#batch-lists .checkbox").removeClass("checkbox-ed").addClass("checkbox-un");
			$("#report-multibatch-search").addClass("disabled");
			$("#report-multibatch-search").attr("disabled",true);
			$("#batch-more").addClass("disabled");
			$("#batch-more").attr("disabled",true);
	        $(".selector-line").removeClass("select-more");
	        $("#report-multibatch-search").addClass("disabled");
	        $("#report-multibatch-search").attr("disabled",true);
	        $(".selector-line .checkbox").removeClass("checkbox-ed").addClass("checkbox-un").addClass("hide");
	        $(".selector-line").find("[name='sl-confirm']").addClass("disabled");
	        $(".selector-line").find("[name='sl-confirm']").attr("disabled",true);
	        $(".selector-line").find(".multisl-btns").addClass("hide");
	        var selectorline = $("#batch-sl");
	        selectorline.addClass("select-more");
	        selectorline.find(".sl-val").addClass("show-more");
			selectorline.find(".checkbox").removeClass("hide");
			selectorline.find(".multisl-btns").removeClass("hide");
		}
		$scope.resetBatchMultiselect = function(){
			var selectorline = $("#batch-sl");
			selectorline.removeClass("select-more");
			selectorline.find(".sl-val").removeClass("show-more");
			selectorline.find(".checkbox").addClass("hide").addClass("checkbox-un").removeClass("checkbox-ed");
			selectorline.find(".multisl-btns").addClass("hide");
			if($("#batch-more").hasClass("disabled")){
				$("#batch-more").removeClass("disabled");
				$("#batch-more").attr("disabled",false);
				$("#batch-more span").html("更多");
				$("#batch-more i").removeClass("fa-chevron-up").addClass("fa-chevron-down");
			}
		}
		$scope.resetPeriodMultiSelect = function(){
			var selectorline = $("#period-sl");
			selectorline.removeClass("select-more");
			selectorline.find(".sl-val").removeClass("show-more");
			selectorline.find(".checkbox").addClass("hide").addClass("checkbox-un").removeClass("checkbox-ed");
			selectorline.find(".multisl-btns").addClass("hide");
			if($("#batch-more").hasClass("disabled")){
				$("#batch-more").removeClass("disabled");
				$("#batch-more").attr("disabled",false);
				$("#batch-more span").html("更多");
				$("#batch-more i").removeClass("fa-chevron-up").addClass("fa-chevron-down");
			}
		}
		$scope.reportSelectMore = function(){
			$("#batch-more").removeClass("disabled");
			$("#batch-more").attr("disabled",false);
			$("#batch-more span").html("更多");
			$("#batch-more i").removeClass("fa-chevron-up").addClass("fa-chevron-down");
	        $(".selector-line").removeClass("select-more");
	        $("#report-multibatch-search").addClass("disabled");
	        $("#report-multibatch-search").attr("disabled",true);
	        $(".selector-line .checkbox").removeClass("checkbox-ed").addClass("checkbox-un").addClass("hide");
	        $(".selector-line").find("[name='sl-confirm']").addClass("disabled");
	        $(".selector-line").find("[name='sl-confirm']").attr("disabled",true);
	        $(".selector-line").find(".multisl-btns").addClass("hide");
	        var selectorline = $("#period-sl");
	        selectorline.addClass("select-more");
	        selectorline.find(".sl-val").addClass("show-more");
	        selectorline.find(".checkbox").removeClass("hide");
	        selectorline.find(".multisl-btns").removeClass("hide");
		}
		$scope.batchLists = function(batchId){
			$("#" + batchId).toggleClass("checkbox-un");
			$("#" + batchId).toggleClass("checkbox-ed");
			if($("#batch-lists .checkbox-ed").size() > 0){
				$("#report-multibatch-search").removeClass("disabled");
				$("#report-multibatch-search").attr("disabled",false);
			}else{
				$("#report-multibatch-search").addClass("disabled");
				$("#report-multibatch-search").attr("disabled",true);
			}
		}
		$scope.periodLists = function(periodId){
			$("#" + periodId).toggleClass("checkbox-un");
			$("#" + periodId).toggleClass("checkbox-ed");
			if($("#period-lists .checkbox-ed").size() > 0){
				$("#report-multiperiod-search").removeClass("disabled");
				$("#report-multiperiod-search").attr("disabled",false);
			}else{
				$("#report-multiperiod-search").addClass("disabled");
				$("#report-multiperiod-search").attr("disabled",true);
			}
		}
		$scope.reportDateSearch = function(){
			$scope.pageQuery({beginDate :$("#report-begindate-search").val(),endDate:$("#report-enddate-search").val()});
		}
		/**
		 * 点击单个标签时的搜索
		 */
		$scope.reportBatchSearch = function(batchId){
			if(!$("#batch-sl").hasClass("select-more")){
				$scope.pageQuery({batches :[$("#" + batchId).next().find("span").text()]});
				$("#selected-batch span").html($("#" + batchId).next().find("span").text());
				$("#selected-batch").removeClass("hide");
				$("#to-sl-batch").addClass("hide");
			}
		}
		/**
		 * 取消标签时的搜索
		 */
		$scope.clearSlBatch = function(){
			$("#selected-batch").addClass("hide");
	        $("#to-sl-batch").removeClass("hide");
	        $("#batch-more").removeClass("disabled");
	        $("#batch-more").attr("disabled",false);
	        $("#batch-lists").find(".multisl-btns").addClass("hide");
	        $scope.pageQuery({batches :[]});
		}
		/**
		 * 点击单个状态时的搜索
		 */
		$scope.reportPeriodSearch = function(periodId){
			if(!$("#period-sl").hasClass("select-more")){
				$scope.pageQuery({periods :[ $(this).find("input").val()]});
				$("#selected-period span").html($("#" + periodId).next().find("span").html());
				$("#selected-period").removeClass("hide");
				$("#to-sl-period").addClass("hide");
			}
		}
		/**
		 * 取消状态时的搜索
		 */
		$scope.clearSlPeriod = function(){
			$("#selected-period").addClass("hide");
			$("#to-sl-period").removeClass("hide");
			$scope.pageQuery({periods :[]});
		}
		/**
		 * 标签多选
		 */
		$scope.reportMultibatchSearch = function(){
			var show_val = [];
			$("#batch-lists .checkbox-ed").each(function(){
				show_val.push($(this).next().text());
			});
			$scope.pageQuery({batches : show_val});
			$("#selected-batch span").html(show_val.toString());
			$("#selected-batch").removeClass("hide");
			$("#to-sl-batch").addClass("hide");
			$("#batch-sl").removeClass("select-more");
			$("#batch-lists").removeClass("show-more");
	        $("#batch-lists").find(".checkbox").addClass("hide");
	        $("#batch-more span").html("更多");
	        $("#batch-more i").removeClass("fa-chevron-up").addClass("fa-chevron-down");
		}
		/**
		 * 状态多选
		 */
		$scope.reportMultiperiodSearch = function(){
			var show_val = [];
			var periods = [];
			$("#period-lists .checkbox-ed").each(function(){
				periods.push($(this).next().find("input[type='hidden']").val());
				show_val.push($(this).next().find("span").html());
			});
			$scope.pageQuery({periods :periods});
			$("#selected-period span").html(show_val.toString());
			$("#selected-period").removeClass("hide");
			$("#to-sl-period").addClass("hide");
			$("#period-sl").removeClass("select-more");
			$("#period-sl").find(".checkbox").addClass("hide");
			$("#period-sl").find(".multisl-btns").addClass("hide");
			$("#period-lists .checkbox").removeClass("checkbox-ed").addClass("checkbox-un");
		}
		
		/**
		 * 排序按钮
		 */
//		$(document).on("click", "a[id^='reportSortBtn-']", function() {
//			var id = $(this).attr("id");
//			var sort = id.split('-');
//			$scope.pageQuery({sidx : sort[1],sord : sort[2] || 'desc'});
//		});
		/**
		 * 改变分页大小
		 */
//		$(document).on("change", "#rocky_report_page #page-size-sel", function() {
//			$scope.pageQuery({size : $(this).val()});
//		});
		/**
		 * 样本编号模糊搜索
		 */
//		$(document).on("keyup", "#report-sample-filter", function(event) {
//			if (event.keyCode == 13) {
//				$("#report-condition-input").val('');
//				$scope.pageQuery({sample : $(this).val(),condition:null}, function() {
//					var val = $("#report-sample-filter").val();
//					$("#report-sample-filter").focus().val(val);
//				});
//			}
//		});
		/**
		 * 右上角的搜索
		 */
//		$(document).on("keyup", "#report-condition-input", function(event) {
//			if (event.keyCode == 13) {
//				$scope.pageQuery({condition : $(this).val(),sample:null});
//			}
//		});
		/**
		 * 右上角的搜索
		 */
//		$(document).on("click", "#report-condition-find", function(event) {
//			$scope.pageQuery({condition : $("#report-condition-input").val(),sample:null});
//		});
		/**
		 * 分页按钮
		 */
//		$(document).on("click", "#rocky_report_page [data-click='pagination-btn']",function() {
//			$scope.pageQuery({page : $(this).data("page")});
//		});
		$scope.sort = {};
		$scope.params = {};
		$scope.pageQuery = function(options, f) {
			var params = {
				page : 1,
				size : $("#rocky_report_page #page-size-sel").val()||20,
				sidx : $scope.sort.sidx,
				sord : $scope.sort.sord,
				sample : $scope.params.sample,
				beginDate : $scope.params.beginDate,
				endDate : $scope.params.endDate,
				condition : $scope.params.condition,
				batches : $scope.params.batches,
				periods : $scope.params.periods
			};
//			$scope.changeSettings(options);
//			$.extend(params, options);
//			var temp = [];
//			for(var key in params){
//				var param = params[key];
//				if($.isArray(param)){
//					for(var t in param){
//						temp.push(key+"="+$.trim(param[t]));
//					}
//				}else if(param){
//					temp.push(key+"="+params[key]);
//				}
//			}
			rockyReportService.pageQuery(params).
			success(function(dataMap){
				$scope.dataList = dataMap.pageList;
				$scope.batchList = dataMap.batchList;
				$scope.periodMap = dataMap.periodMap;
			});
		};
		$scope.curDate = new Date();
		$scope.pageQuery();
	});
})();
