(function() {
	celloudApp.controller("rockyUploadController", function($route, $location, $scope, $rootScope, uploadService) {
		$scope.stepOne = function(){
			// 在第二步存在了uploader对象但是没选择文件
			if($rootScope.rockyUploader && $rootScope.rockyUploader.files.length <=0 && $rootScope.rockyStep != 'three'){
				$rootScope.rockyUploader = undefined;
				$(".steps").addClass("hide");
				$("#upload-nav-step-three").addClass("not-reached");
				$("#upload-nav-step-two").addClass("not-reached");
				$("#upload-step-one").removeClass("hide");
				$(".step-line").addClass("not-reached")
				$(".uploadtips .tips").addClass('hide');
				$("#batch-info-input").val('');
				$("#upload-tip-one").removeClass("hide");
				$rootScope.rockyBatch = "";
			}else if(!$rootScope.rockyUploader){ // 在第三步点击新上传
				$(".steps").addClass("hide");
				$("#upload-nav-step-three").addClass("not-reached");
				$("#upload-nav-step-two").addClass("not-reached");
				$("#upload-step-one").removeClass("hide");
				$(".step-line").addClass("not-reached")
				$(".uploadtips .tips").addClass('hide');
				$("#batch-info-input").val('');
				$("#upload-tip-one").removeClass("hide");
				$rootScope.rockyBatch = "";
			}
		},
		$scope.stepTwo = function() {
			if(!$rootScope.rockyBatch){
				$rootScope.rockyBatch = $scope.rockyBatch;
			}
			if(!$rootScope.rockyUploader){
				$rootScope.rockyUploader = initUploader();
				$rootScope.rockyUploader.init();
				$rootScope.rockyUploadSpead = {};
			}
			$(".steps").addClass("hide");
			$(".step-line").addClass("not-reached")
			$("#upload-nav-line-one").removeClass("not-reached");
			$("#upload-nav-step-two").removeClass("not-reached");
			$("#upload-nav-step-three").addClass("not-reached");
			$("#upload-step-two").removeClass("hide");
			$(".uploadtips .tips").addClass('hide');
			$("#upload-tip-two").removeClass("hide");
		},
		$scope.stepThree = function(){
			$(".steps").addClass("hide");
			$("#upload-step-three").removeClass("hide");
			$("#upload-nav-line-one").removeClass("not-reached");
			$("#upload-nav-line-two").removeClass("not-reached");
			$("#upload-nav-step-three").removeClass("not-reached");
			$("#upload-nav-step-two").removeClass("not-reached");
			$(".uploadtips .tips").addClass('hide');
			$("#upload-tip-three").removeClass("hide");
		}
		if($rootScope.rockyUploader && $rootScope.rockyStep == 'two'){
			$scope.stepTwo();
			$("#rocky-upload-list-table").removeClass("hide");
			$.each($rootScope.rockyUploader.files, function(index, item) {
				if(item.percent == 100){
					var $fileDom = $('<tr id="' + item.id + '"></tr>');
					$fileDom.append($('<td class="filename">' + item.name + '</td>'));
					$fileDom.append($('<td class="percent">上传完成</td>'));
					$fileDom.append($('<td class="surplus">00:00:00</td>'));
					$fileDom.append($('<td class="spead">'+$rootScope.rockyUploadSpead[item.id]+'</td>'));
					$fileDom.append($('<td><a data-click="del-upload-file" data-id="'+item.id+'"  href="javascript:void(0)"><i class="fa fa-times-circle" aria-hidden="true"></i></a></td>'));
					$("#rocky-upload-list-tbody").append($fileDom);
				}else if(item.percent >0){
					var $fileDom = $('<tr id="' + item.id + '"></tr>');
					$fileDom.append($('<td class="filename">' + item.name + '</td>'));
					$fileDom.append($('<td class="percent">'+item.percent+'%</td>'));
					$fileDom.append($('<td class="surplus">'+getSize($rootScope.rockyUploader.total.bytesPerSec)+'/s</td>'));
					$fileDom.append($('<td class="spead">'+utils.formatDate((item.size-item.loaded)/$rootScope.rockyUploader.total.bytesPerSec)+'</td>'));
					$fileDom.append($('<td><a data-click="del-upload-file" data-id="'+item.id+'"  href="javascript:void(0)"><i class="fa fa-times-circle" aria-hidden="true"></i></a></td>'));
					$("#rocky-upload-list-tbody").append($fileDom);
				}else{
					var $fileDom = $('<tr id="' + item.id + '"></tr>');
					$fileDom.append($('<td class="filename">' + item.name + '</td>'));
					$fileDom.append($('<td class="percent">等待上传</td>'));
					$fileDom.append($('<td class="surplus">---</td>'));
					$fileDom.append($('<td class="spead">---</td>'));
					$fileDom.append($('<td><a data-click="del-upload-file" data-id="'+item.id+'"  href="javascript:void(0)"><i class="fa fa-times-circle" aria-hidden="true"></i></a></td>'));
					$("#rocky-upload-list-tbody").append($fileDom);
				}
			});
		}
		
		if($rootScope.rockyUploader && $rootScope.rockyStep == 'three'){
			$rootScope.rockyUploader.destroy();
			$scope.stepThree();
		}
		
		$scope.refreshSession = function(){
			$.get("uploadFile/checkAdminSessionTimeOut");
		};
		
		// 继续上传
		$scope.continueUpload = function(){
			$rootScope.rockyUploader = undefined;
			$rootScope.rockyStep = 'one';
			$scope.stepTwo();
		}
		
		// 新上传
		$scope.newUpload = function(){
			$rootScope.rockyUploader = undefined;
			$rootScope.rockyStep = 'one';
			$scope.stepOne();
		}
		var refreshSession = function(){
			return 
		}
		var initUploader = function(){
			var uploadUrl = "../uploadFile/uploadManyFile";
			$.ajax({
				type:"GET",
				async:false,
				url:CONTEXT_PATH+"/box/configs",
				success:function(configs){
					for(var index in configs){
						var config = configs[index];
						var port = config.port||80;
						var context = !config.context?'':(config.context.startsWith("/")?config.context:("/"+config.context));
						config = "https://"+config.intranetAddress+":"+port+context;
						var response = $.ajax(config+"/box/alive",{async: false}).responseText;
						if(response && JSON.parse(response).success){
							$scope.box=config;
							break;
						}
					}
					if($scope.box != null){
						uploadUrl = $scope.box + "/box/upload";
						console.log("成功找到了一个盒子，地址为："+$scope.box);
					}else{
						console.log("没有找到盒子...");
					}
				}
			});
			var uploader = new plupload.Uploader({
				runtimes : 'html5,flash,silverlight,html4',
				browse_button : 'plupload-content',
				url : uploadUrl,
				drop_element : 'plupload-content',
				filters : {
					max_file_size : '10gb',
					prevent_duplicates : true, // 不允许选取重复文件
					mime_types : [
						{title : "fastq", extensions : "fastq"},
						{title : "txt", extensions : "txt"},
						{title : "gz", extensions : "gz"},
						{title : "bam", extensions : "bam"}
					]
				},
				max_retries : 5,
				flash_swf_url : '//cdn.bootcss.com/plupload/2.1.8/Moxie.swf'
			});
			$(document).on("click", "[data-click='del-upload-file']", function() {
				var id = $(this).data("id");
				uploader.removeFile(id);
				$("#"+id).remove();
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
				var curSpead = getSize(uploader.total.bytesPerSec)+"/s";
				$("#" + file.id + " .spead").html(curSpead);
				if(curSpead && curSpead != 'undefined'){
					$rootScope.rockyUploadSpead[file.id] = curSpead;
				}
				$("#" + file.id + " .surplus").html(utils.formatDate((file.size-file.loaded)/uploader.total.bytesPerSec));
			});
			uploader.bind("FilesAdded", function(uploader, files) {
				$.each(files, function(index, item) {
					if(item.size > 0){
						var $fileDom = $('<tr id="' + item.id + '"></tr>');
						$fileDom.append($('<td class="filename">' + item.name + '</td>'));
						$fileDom.append($('<td class="percent">等待上传</td>'));
						$fileDom.append($('<td class="surplus">---</td>'));
						$fileDom.append($('<td class="spead">---</td>'));
						$fileDom.append($('<td><a data-click="del-upload-file" data-id="'+item.id+'"  href="javascript:void(0)"><i class="fa fa-times-circle" aria-hidden="true"></i></a></td>'));
						$("#rocky-upload-list-tbody").append($fileDom);
					}else{
						uploader.removeFile(item);
					}
				});
				if(uploader.files.length > 0){
					$("#rocky-upload-list-table").removeClass("hide");
					uploader.start();
					$rootScope.rockyStep = 'two'; // 只有选择了文件才算真的在第二步
				}
			});
			uploader.bind("BeforeUpload", function(uploader, file) {
				if($scope.box==null){
					$("#" + file.id +" .percent").html("正在上传");
					var object = $.ajax({
						 url: CONTEXT_PATH+"/oss/upload/postPolicy",
						 async: false,
						 data:'name='+file.name+'&oName=ddd'+file.id
					}).responseText;
					object = JSON.parse(object);
					uploader.setOption({
						url:"https://"+object.host,
						multipart_params:{
							'key' : object.dir + file.id +object.ext,
							'policy': object.policy,
					        'OSSAccessKeyId': object.accessid, 
					        'success_action_status' : '200', //让服务端返回200,不然，默认会返回204
					        'signature': object.signature,
					        'x-oss-meta-name':file.name,
					        'x-oss-meta-batch':$rootScope.rockyBatch,
					        'x-oss-meta-tagId':2
						}
					});
					file.objectKey = object.dir + file.id +object.ext;
				} else {
					uploader.setOption("multipart_params",{'userId':window.userId,"lastModifiedDate":file.lastModifiedDate,'size':file.size,'originalName': file.name,'name': file.name,'tagId':2,'batch': $rootScope.rockyBatch});
				}
			});
			uploader.bind("FileUploaded", function(uploader, file, response) {
				if($scope.box==null){
					var res = JSON.parse(response.response);
					uploader.setOption("multipart_params",{'originalName': file.name, "tagId":2, "batch":$rootScope.rockyBatch, 'size':file.size, 'lastModifiedDate':file.lastModifiedDate, "uniqueName":file.id});
					$("#" + file.id +" .percent").html("上传完成");
          messageUtils.notify("上传完成","数据【" + file.name + "】上传成功。");
					$.post(CONTEXT_PATH+"/oss/upload/newfile",{
						'name':file.name,
						'batch':$rootScope.rockyBatch,
						'size':file.size, 
						'objectKey':file.objectKey,
						'tagId':2
					},function(data){
						console.log(data);
					});
				}
			});
			uploader.bind("UploadComplete",function(uploader,files){
				uploader.splice(0, uploader.files.length);
				$("#rocky-upload-list-tbody").html('');
				$("#rocky-upload-list-table").addClass("hide");
				$rootScope.rockyUploader.destroy();
				$rootScope.rockyStep = 'three';
				$scope.stepThree();
			});
			return uploader;
		};
		
		$scope.stepOne();
		
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
			var qp=$rootScope.rockyUploader.total;
			var percent=qp.percent;
			if(qp.size>0&&percent<100&&percent>0){
				return "数据正在上传，您确定要关闭页面吗?"
			}
		}
	});
	
	celloudApp.controller("rockyDataController", function($scope, $rootScope, rockyDataService) {
		$scope.params = {
		  page: 1,
		  size: 20,
		  sample: null,
	      condition: null,
	      sortField: "createDate",
	      sortType: "desc"
	    }
	    $scope.pageQuery = function(){
			rockyDataService.pageQuery($scope.params).
	  		success(function(dataMap){
	  			$scope.dataList = dataMap.dataList;
	  			$scope.periodMap = dataMap.periodMap;
	  		})
		}
		// 分页按钮
		$scope.paginationBtn = function(currentPage){
			$scope.params.page = currentPage;
			$scope.pageQuery();
		}
		/**
		 * 改变分页大小
		 */
		$scope.changePageSize = function(){
			$scope.params.size = $("#rocky_report_page #page-size-sel").val();
			$scope.params.page = 1;
			$scope.pageQuery();
		};
		$scope.sortQuery = function(sortField){
			if(sortField == $scope.params.sortField){
				$scope.params.sortType = $scope.params.sortType == "asc" ? "desc" : "asc";
			}else{
				$scope.params.sortField = sortField;
				$scope.params.sortType = "desc";
			}
			$scope.pageQuery();
		}
		/**
		 * 样本编号查询
		 */
		$scope.sampleQuery = function($event){
			if($event.keyCode == 13){
				$scope.params.sample = $scope.sample;
				$scope.params.page = 1;
				$scope.pageQuery();
			}
		}
		/**
		 * 上方搜索框查询
		 */
		$scope.conditionQuery = function($event){
			if($event.keyCode == 13){
				$scope.params.condition = $scope.condition;
				$scope.params.page = 1;
				$scope.pageQuery();
		    	}
		}
		$scope.curDate = new Date();
		$scope.pageQuery();
	});
	
	celloudApp.controller("rockyReportController", function($scope, rockyReportService) {
		
		$scope.showReport = function(dataKey,projectId,appId) {
			var url = CONTEXT_PATH + "/report/rocky/data/report";
			$.get(url,{
				dataKey : dataKey,
				appId : appId,
				projectId : projectId
			},function(data){
				$("#common-container").html(data);
			});
		}
		
		/**
		 * 样本编号模糊搜索
		 */
		$scope.reportSampleFilter = function($event){
			if ($event.keyCode == 13) {
				$scope.params.page = 1;
				$scope.pageQuery();
			}
		}
		
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
			$scope.params.beginDate = $("#report-begindate-search").val();
			$scope.params.endDate = $("#report-enddate-search").val();
			$scope.params.page = 1;
			$scope.pageQuery();
		}
		/**
		 * 点击单个标签时的搜索
		 */
		$scope.reportBatchSearch = function(batchId){
			if(!$("#batch-sl").hasClass("select-more")){
				$scope.params.batches = $("#" + batchId).next().find("span").text();
				$scope.params.page = 1;
				$scope.pageQuery();
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
	        $scope.params.batches = "";
	        $scope.params.page = 1;
	        $scope.pageQuery();
		}
		/**
		 * 点击单个状态时的搜索
		 */
		$scope.reportPeriodSearch = function(periodId){
			if(!$("#period-sl").hasClass("select-more")){
				$scope.params.periods = $("#" + periodId).next().find("input").val();
				$scope.params.page = 1;
				$scope.pageQuery();
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
			$scope.params.periods = "";
			$scope.params.page = 1;
			$scope.pageQuery();
		}
		/**
		 * 标签多选
		 */
		$scope.reportMultibatchSearch = function(){
			var show_val = [];
			var batches = "";
			$("#batch-lists .checkbox-ed").each(function(){
				batches += $(this).next().find("span").html() + ",";
				show_val.push($(this).next().text());
			});
			$scope.params.batches = batches.substring(0,batches.length);
			$scope.params.page = 1;
			$scope.pageQuery();
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
			var periods = "";
			$("#period-lists .checkbox-ed").each(function(){
				periods += $(this).next().find("input[type='hidden']").val() + ",";
				show_val.push($(this).next().find("span").html());
			});
			$scope.params.periods = periods.substring(0,periods.length);
			$scope.params.page = 1;
			$scope.pageQuery();
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
		$scope.reportSortBtn = function(id){
			var sort = id.split('-');
			$scope.params.sidx = sort[1];
			$scope.params.sord = sort[2] || 'desc';
			$scope.pageQuery();
		}
		
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
		 * 改变分页大小
		 */
		$scope.changePageSize = function(){
			$scope.params.size = $("#rocky_report_page #page-size-sel").val();
			$scope.params.page = 1;
			$scope.pageQuery();
		};
		
		/**
		 * 分页按钮
		 */
		$scope.paginationBtn = function(page){
			$scope.params.page = page;
			$scope.pageQuery();
		}
		
		$scope.sort = {};
		
		$scope.params = {
			page : 1,
			size : 20,
			sidx : $scope.sidx,
			sord : $scope.sord,
			sample : $scope.sample,
			beginDate : $scope.beginDate,
			endDate : $scope.endDate,
			condition : $scope.condition,
			batches : $scope.batches,
			periods : $scope.periods
		};
		
		$scope.pageQuery = function() {
			rockyReportService.pageQuery($scope.params).
			success(function(dataMap){
				$scope.dataList = dataMap.pageList;
				$scope.batchList = dataMap.batchList;
				$scope.periodMap = dataMap.periodMap;
				$scope.sidx = dataMap.sidx;
				$scope.sord = dataMap.sord;
			});
		};
		$scope.curDate = new Date();
		$scope.pageQuery();
	});
})();
