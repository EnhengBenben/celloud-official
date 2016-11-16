(function() {
	celloudApp.controller("bsiFileUpload",function($scope, $rootScope){
		$scope.uploadPercent = 0;
		//  ============================上传============================
		$scope.itemBtnToggleActive = function(){
		    $("#common-menu .item-btn").removeClass("active");
		    $("#to-upload-a").addClass("active");
		}
		$rootScope.bsiStepOne = function(){
			$scope.itemBtnToggleActive();
			// 判断是否在第二步, 进行回显
			if($rootScope.bsiUploader && $rootScope.bsiStep == 'two'){
				$("#upload-filelist").children().remove();
				$("#uploading-filelist").children(":not(:first)").remove();
				$.each($rootScope.bsiUploader.files, function(index, item) {
			    	var $fileDom = $('<li class="plupload_delete" id="' + item.id + '">');
			        $fileDom.append($('<div class="plupload-file-name"><span>' + item.name + '</span></div>'));
			        $fileDom.append($('<div class="plupload-file-size">'+getSize(item.size)+'</div>'));
			        $fileDom.append($('<div class="plupload-file-action"><a href="#" style="display: block;"><i class="fa fa-times-circle-o" aria-hidden="true"></i></a></div>'));
			        $fileDom.append($('<div class="plupload-clearer"></div>&nbsp;</li>'));
			        $("#upload-filelist").append($fileDom);
			        var $fileDom_uploading = $('<li id="uploading-' + item.id + '">');
			        $fileDom_uploading.append($('<div class="plupload-file-name"><span title="' + item.name + '">' + item.name + '</span></div>'));
			        $fileDom_uploading.append($('<div class="plupload-file-size">'+getSize(item.size)+'</div>'));
			        $fileDom_uploading.append($('<div class="plupload-file-surplus">_</div>'));
			        $fileDom_uploading.append($('<div class="plupload-file-status">_</div>'));
			        $fileDom_uploading.append($('<div class="plupload-file-action"><a href="#" style="display: block;"><i class="fa fa-times-circle-o" aria-hidden="true"></i></a></div>'));
			        $fileDom_uploading.append($('<div class="plupload-clearer"></div>&nbsp;</li>'));
			        $("#uploading-filelist").append($fileDom_uploading);
			        handleStatus(item);
			        var params = {"size":item.size,"lastModifiedDate":item.lastModifiedDate,"name":item.name};
					if($scope.box!=null){
						$.get($scope.box+"/box/checkBreakpoints",params,function(data){
							if(data.data){
								item.loaded = data.data;
								$("#uploading-" + item.id +" .plupload-file-status").html((item.loaded/item.size).toFixed(2)*100+"%");
							}
						});
					}
			        $('#' + item.id + '.plupload_delete a').click(function(e) {
			        	$('#' + item.id).remove();
			        	$('#uploading-' + item.id).remove();
			        	$rootScope.bsiUploader.removeFile(item);
			        	e.preventDefault();
			        	utils.stopBubble(e);
			        	$scope.uploadTextType();
			        });
			        $('#uploading-' + item.id + '.plupload_delete a').click(function(e) {
			        	$('#' + item.id).remove();
			        	$('#uploading-' + item.id).remove();
			        	$rootScope.bsiUploader.removeFile(item);
			        	e.preventDefault();
			        	utils.stopBubble(e);
			        });
		        });
				$scope.stepTwo();
			}else if($rootScope.bsiUploader && $rootScope.bsiStep == 'three'){ // 在第三步
				$("#upload-filelist").children().remove();
				$("#uploading-filelist").children(":not(:first)").remove();
				console.log($rootScope.bsiUploader.files.length);
				$.each($rootScope.bsiUploader.files, function(index, item) {
					if(item.percent == 100){
						var $fileDom_uploading = $('<li id="uploading-' + item.id + '">');
				        $fileDom_uploading.append($('<div class="plupload-file-name"><span title="' + item.name + '">' + item.name + '</span></div>'));
				        $fileDom_uploading.append($('<div class="plupload-file-size">'+getSize(item.size)+'</div>'));
				        $fileDom_uploading.append($('<div class="plupload-file-surplus">00:00:00</div>'));
				        $fileDom_uploading.append($('<div class="plupload-file-status">100%</div>'));
				        $fileDom_uploading.append($('<div class="plupload-file-action"><a href="#" style="display: block;"><i class="fa fa-times-circle-o" aria-hidden="true"></i></a></div>'));
				        $fileDom_uploading.append($('<div class="plupload-clearer"></div>&nbsp;</li>'));
				        $("#uploading-filelist").append($fileDom_uploading);
					}else{
						var $fileDom_uploading = $('<li id="uploading-' + item.id + '">');
				        $fileDom_uploading.append($('<div class="plupload-file-name"><span title="' + item.name + '">' + item.name + '</span></div>'));
				        $fileDom_uploading.append($('<div class="plupload-file-size">'+getSize(item.size)+'</div>'));
				        $fileDom_uploading.append($('<div class="plupload-file-surplus">_</div>'));
				        $fileDom_uploading.append($('<div class="plupload-file-status">_</div>'));
				        $fileDom_uploading.append($('<div class="plupload-file-action"><a href="#" style="display: block;"><i class="fa fa-times-circle-o" aria-hidden="true"></i></a></div>'));
				        $fileDom_uploading.append($('<div class="plupload-clearer"></div>&nbsp;</li>'));
				        $("#uploading-filelist").append($fileDom_uploading);
					}
			        handleStatus(item);
			        var params = {"size":item.size,"lastModifiedDate":item.lastModifiedDate,"name":item.name};
					if($scope.box!=null){
						$.get($scope.box+"/box/checkBreakpoints",params,function(data){
							if(data.data){
								item.loaded = data.data;
								$("#uploading-" + item.id +" .plupload-file-status").html((item.loaded/item.size).toFixed(2)*100+"%");
							}
						});
					}
			        $('#' + item.id + '.plupload_delete a').click(function(e) {
			        	$('#' + item.id).remove();
			        	$('#uploading-' + item.id).remove();
			        	$rootScope.bsiUploader.removeFile(item);
			        	e.preventDefault();
			        	utils.stopBubble(e);
			        	$scope.uploadTextType();
			        });
			        $('#uploading-' + item.id + '.plupload_delete a').click(function(e) {
			        	$('#' + item.id).remove();
			        	$('#uploading-' + item.id).remove();
			        	$rootScope.bsiUploader.removeFile(item);
			        	e.preventDefault();
			        	utils.stopBubble(e);
			        });
		        });
				$scope.beginUpload();
			}else{ // 第一步
				$rootScope.bsiStep = 'one';
			    if($(".plupload_filelist li").hasClass("plupload_droptext")){
			      $("#batch-info").val("");
			      $("#batch-div").removeClass("hide");
			      $("#upload-content").addClass("upload-step-one");
			    }
			    if($scope.uploadPercent >= 100){
			      waveLoading.setProgress(0);
			      document.querySelector("#upload-progress").height = document.querySelector("#upload-progress").height;
			    }
			}
			$("#bsi-upload-modal").modal("show");
		}
		$scope.uploadTextType = function(){
			if($("#upload-filelist").children().length> 2){
				$(".upload-text").addClass("hide");
	        }else{
	        	$(".upload-text").removeClass("hide");
	        }
		}
		// 下一步按钮
		$scope.stepTwo = function(){
			$(".step-one-content").addClass("hide");
		    $(".step-two-content").removeClass("hide");
		    $("#one-to-two").addClass("active");
		    $(".step-two").addClass("active");
		    $scope.uploadTextType();
		    // 如果不存在bsiUploader才去创建, 用于回显
		    if(!$rootScope.bsiBatch){
				$rootScope.bsiBatch = $scope.bsiBatch;
			}
		    if(!$rootScope.bsiUploader){
		    	initUploader();
		    }
		}
		// 第一步输入标签的回车事件
		$scope.batchInfo = function($event){
			if ($event.keyCode == 13) {
				$scope.stepTwo();
			}
		}
		// 防止session超时
		$scope.refreshSession = function(){
			$.get("uploadFile/checkAdminSessionTimeOut");
		}
		
		var initUploader = function(){
			$.get(CONTEXT_PATH+"/box/configs",function(configs){
				for(var index in configs){
					var config = configs[index];
				    var port = config.port||80;
				    var context = !config.context?'':(config.context.startsWith("/")?config.context:("/"+config.context));
				    config = "http://"+config.intranetAddress+":"+port+context;
				    var response = $.ajax(config+"/box/alive",{async: false}).responseText;
				    if(response && JSON.parse(response).success){
				    	$scope.box=config;
				    	break;
				    }
			    }
				var uploadUrl = $scope.box==null?"../uploadFile/uploadManyFile":($scope.box+"/box/upload");
				console.log($scope.box==null?"没有找到盒子...":"成功找到了一个盒子，地址为："+$scope.box);
//				var uploadUrl = "../uploadFile/uploadManyFile";
				var uploader = new plupload.Uploader({
					runtimes : 'html5,flash,silverlight,html4',
					browse_button : ['bsi-plupload-content','bsi-upload-more'],
					url :uploadUrl,
					// Maximum file size
					chunk_size : '1mb',
					dragdrop : true,
					unique_names:true,
					drop_element : 'upload-filelist',
					// Specify what files to browse for
					filters : {
						max_file_size : '10gb',
						prevent_duplicates : true, //不允许选取重复文件
						mime_types : [
						    {title : "bam", extensions : "bam"},
						    {title : "ab1", extensions : "ab1"},
						    {title : "abi", extensions : "abi"},
						    {title : "fasta", extensions : "fasta"},
						    {title : "fastq", extensions : "fastq"},
						    {title : "tsv", extensions : "tsv"},
						    {title : "gz", extensions : "gz"},
						    {title : "lis", extensions : "lis"},
						    {title : "txt", extensions : "txt"}
						]
			        },
			        max_retries : 5,
			        multiple_queues : true,
			        // Flash settings
			        flash_swf_url : '//cdn.bootcss.com/plupload/2.1.8/Moxie.swf'
			    });
			    uploader.init();
			    uploader.bind("StateChanged", function() {
			        //文件队列开始上传
			        if (uploader.state === plupload.STARTED) {
			        	//防止文件上传时意外关闭了浏览器、页签或者上传页面
			        	window.parent.isUploading = true;
			        	//防止session超时
			        	refresh = setInterval("self.refreshSession()",600000);
			        }else if(uploader.state === plupload.STOPPED){//文件队列全部上传结束
			        	window.parent.isUploading = false;
			        	clearInterval(refresh);
			        }
			    });
			    uploader.bind("UploadProgress", function(uploader, file) {
			    	waveLoading.setProgress(uploader.total.percent);
			    	uploadPercent = uploader.total.percent;
			    	$("#uploading-" + file.id +" .plupload-file-status").html(file.percent+"%");
			    	$("#uploading-" + file.id + " .plupload-file-surplus").html(utils.formatDate((file.size-file.loaded)/uploader.total.bytesPerSec));
			    	handleStatus(uploader.total.percent);
			    });
			    uploader.bind("FilesAdded", function(uploader, files) {
			    	$.get("uploadFile/checkAdminSessionTimeOut",function(response){
			    		if(response){//session超时则执行下两步
			          
			    		}else{
			    			//销毁uploader，间接取消选择文件弹窗
			    			uploader.destroy();
			    		}
			    	});
			    	$.each(files, function(index, item) {
				    	var $fileDom = $('<li class="plupload_delete" id="' + item.id + '">');
				        $fileDom.append($('<div class="plupload-file-name"><span>' + item.name + '</span></div>'));
				        $fileDom.append($('<div class="plupload-file-size">'+getSize(item.size)+'</div>'));
				        $fileDom.append($('<div class="plupload-file-action"><a href="#" style="display: block;"><i class="fa fa-times-circle-o" aria-hidden="true"></i></a></div>'));
				        $fileDom.append($('<div class="plupload-clearer"></div>&nbsp;</li>'));
				        $("#upload-filelist").append($fileDom);
				        var $fileDom_uploading = $('<li id="uploading-' + item.id + '">');
				        $fileDom_uploading.append($('<div class="plupload-file-name"><span title="' + item.name + '">' + item.name + '</span></div>'));
				        $fileDom_uploading.append($('<div class="plupload-file-size">'+getSize(item.size)+'</div>'));
				        $fileDom_uploading.append($('<div class="plupload-file-surplus">_</div>'));
				        $fileDom_uploading.append($('<div class="plupload-file-status">_</div>'));
				        $fileDom_uploading.append($('<div class="plupload-file-action"><a href="#" style="display: block;"><i class="fa fa-times-circle-o" aria-hidden="true"></i></a></div>'));
				        $fileDom_uploading.append($('<div class="plupload-clearer"></div>&nbsp;</li>'));
				        $("#uploading-filelist").append($fileDom_uploading);
				        handleStatus(item);
				        var params = {"size":item.size,"lastModifiedDate":item.lastModifiedDate,"name":item.name};
						if($scope.box!=null){
							$.get($scope.box+"/box/checkBreakpoints",params,function(data){
								if(data.data){
									item.loaded = data.data;
									$("#uploading-" + item.id +" .plupload-file-status").html((item.loaded/item.size).toFixed(2)*100+"%");
								}
							});
						}
				        $('#' + item.id + '.plupload_delete a').click(function(e) {
				        	$('#' + item.id).remove();
				        	$('#uploading-' + item.id).remove();
				        	uploader.removeFile(item);
				        	e.preventDefault();
				        	utils.stopBubble(e);
				        	$.upload.uploadTextType();
				        });
				        $('#uploading-' + item.id + '.plupload_delete a').click(function(e) {
				        	$('#' + item.id).remove();
				        	$('#uploading-' + item.id).remove();
				        	uploader.removeFile(item);
				        	e.preventDefault();
				        	utils.stopBubble(e);
				        });
			        });
			    	if($rootScope.bsiStep == 'one'){
			    		$rootScope.bsiStep = 'two';
			    	}
			        $scope.uploadTextType();
			    });
			    uploader.bind("FileUploaded", function(uploader, file, response) {
			    	var res = response.response;
			    	handleStatus(file);
			    });
			    uploader.bind("UploadComplete",function(uploader,files){
			    	if(files.length>0){
			    		waveLoading.setProgress(100);
			    		uploadPercent = 100;
			    	}else{
			    		waveLoading.init({
			    			haveInited: false
			    		});
			    		waveLoading.draw();
			    		document.querySelector("#upload-progress").height = document.querySelector("#upload-progress").height;
			    	}
			    	$(".step-one-content").removeClass("hide");
			    	$(".step-two-content").addClass("hide");
			    	$(".step-three-content").addClass("hide");
			    	$("#one-to-two").removeClass("active");
			    	$("#two-to-three").removeClass("active");
			    	$(".step-two").removeClass("active");
			    	$(".step-three").removeClass("active");
			    	$("#batch-info").val("")
			    	uploader.splice();
			    	$rootScope.bsiStep = "one";
			    	$rootScope.bsiUploader.destroy();
			    	$rootScope.bsiUploader = undefined;
			    	$("#uploading-filelist .plupload_done").remove();
			    	$("#bsi-upload-modal").modal("hide");
			    });
			    uploader.bind("BeforeUpload", function(uploader, file) {
			    	uploader.setOption("multipart_params",{'userId':window.userId,"lastModifiedDate":file.lastModifiedDate,'size':file.size,'originalName': file.name,'name': file.name,'tagId':$("#tag-info").val(),'batch': $("#batch-info").val(),'needSplit':$("#need-split:checked").val()});
			    });
			    uploader.bind("Error", function(uploader, error) {
			       if(error.code=='-602'){
			         alert("当前队列已存在文件【"+error.file.name+"】，请勿重复添加！");
			       }
			    });
			    $rootScope.bsiUploader = uploader;
			});
		}
		$scope.beginUpload = function(){
			$(".step-three-content").removeClass("hide");
    		$(".step-one-content").addClass("hide");
    		$(".step-two-content").addClass("hide");
    		$("#one-to-two").addClass("active");
    		$("#two-to-three").addClass("active");
    		$(".step-two").addClass("active");
    		$(".step-three").addClass("active");
    		$("#tags-review").html($rootScope.bsiBatch);
    		console.log("33333:" + $rootScope.bsiUploader.files.length);
			if($rootScope.bsiUploader.files.length>0 && $rootScope.bsiStep != 'three'){
	    		$("#upload-filelist").html("");
	    		$rootScope.bsiUploader.start();
	    		$rootScope.bsiStep = 'three';
	    		waveLoading.init({
	    			haveInited: true,
	    			target: document.querySelector('#upload-progress'),
	    			color: 'rgba(40, 230, 200, 0.6)',
	    			showText: false
	    		});
	    		waveLoading.draw();
	        	waveLoading.setProgress(0);
	        }
		}
		$scope.closeUploadModal = function(){
			if($rootScope.bsiUploader && $rootScope.bsiUploader.files.length<=0){
	    		$(".step-one-content").removeClass("hide");
	    		$(".step-two-content").addClass("hide");
	    		$("#one-to-two").removeClass("active");
	    		$(".step-two").removeClass("active");
	    		$(".step-three-content").addClass("hide");
	    		$("#two-to-three").removeClass("active");
	    		$(".step-three").removeClass("active");
	    	}
	    	var text = $("body .breadcrumb").text();
	    	if(text.indexOf("数据")>=0){
	    		$scope.itemBtnToggleActive($("#to-data-a"));
	    	}else if(text.indexOf("报告")>=0){
	    		$scope.itemBtnToggleActive($("#to-report-a"));
	    	}
		}
	    function getSize(fileSize){
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
	    function getSpead(spead){
	    	var unit = "b/s";
	    	if(spead > 1000){
	    		spead = spead/1000;
	    		unit = "kb/s";
	    	}
	    	if(spead > 1000){
	    		spead = spead/1000;
	    		unit = "mb/s";
	    	}
	    	return spead.toFixed(2)+unit;
	    }
	    function handleStatus(file) {
	    	var actionClass;
	    	var iconClass;
	    	if (file.status == plupload.DONE) {
	    		actionClass = 'plupload_done';
	    		iconClass = 'fa fa-check-circle';
	    	}
	    	if (file.status == plupload.FAILED) {
	    		actionClass = 'plupload_failed';
	    		iconClass = 'fa fa-exclamation-triangle';
	    	}
	    	if (file.status == plupload.QUEUED || file.status == plupload.UPLOADING) {
	    		actionClass = 'plupload_delete';
	    		iconClass = 'fa fa-times-circle-o';
	    	}
	    	var icon = $('#uploading-' + file.id).attr('class', actionClass).find('i').attr('class', iconClass);
	    	if (file.hint) {
	    		icon.attr('title', file.hint);  
	    	}
	    }
	})
	
	celloudApp.controller("bsiCommon",function($scope, $rootScope){
		
		$scope.box = null;
		
		$rootScope.sortIcon = function(params){
		    if(params.sortDate=="asc"){
		      $("#sort-date-icon").removeClass("fa-sort-amount-desc").addClass("fa-sort-amount-asc");
		    }else{
		      $("#sort-date-icon").removeClass("fa-sort-amount-asc").addClass("fa-sort-amount-desc");
		    }
		    if(params.sortBatch=="asc"){
		      $("#sort-batch-icon").removeClass("fa-sort-amount-desc").addClass("fa-sort-amount-asc");
		    }else{
		      $("#sort-batch-icon").removeClass("fa-sort-amount-asc").addClass("fa-sort-amount-desc");
		    }
		    if(params.sortName=="asc"){
		      $("#sort-name-icon").removeClass("fa-sort-amount-desc").addClass("fa-sort-amount-asc");
		    }else{
		      $("#sort-name-icon").removeClass("fa-sort-amount-asc").addClass("fa-sort-amount-desc");
		    }
		    if(params.sortPeriod=="asc"){
		    	$("#sort-period-icon").removeClass("fa-sort-amount-desc").addClass("fa-sort-amount-asc");
		    }else{
		    	$("#sort-period-icon").removeClass("fa-sort-amount-asc").addClass("fa-sort-amount-desc");
		    }
	    }
		
	});
	celloudApp.controller("bsiReportDataController",function($scope, $rootScope, $routeParams, bsiService){
		var params = {
			"reportIndex":$routeParams.reportIndex,
			"dataKey":$routeParams.dataKey,
			"projectId":$routeParams.projectId,
			"appId":$routeParams.appId,
			"page":$rootScope.bsiReportParams.page,
			"condition":$rootScope.bsiReportParams.condition,
			"sort":$rootScope.bsiReportParams.sort,
			"sortDate":$rootScope.bsiReportParams.sortDate,
			"sortPeriod":$rootScope.bsiReportParams.sortPeriod,
			"sortBatch":$rootScope.bsiReportParams.sortBatch,
			"sortName":$rootScope.bsiReportParams.sortName,
			"size":$rootScope.bsiReportParams.size
		};
		
		bsiService.getBSIPatientReport(params).
		success(function(dataMap){
			$scope.bsiCharList = dataMap.bsiCharList;
			$scope.bsi = dataMap.bsi;
			$scope.pageList = dataMap.pageList;
			$scope.batchPageList = dataMap.batchPageList;
			$scope.data = dataMap.data;
			
			$scope.tab = 'patient';
		    var havestrain = "";
		    if($scope.bsi.species_20){
		    	for(var i=0;i<$scope.bsi.species_20.length;i++){
			        havestrain += $scope.bsi.species_20[i].species_zh + ",";
			    }
			    $scope.havestrain = havestrain.substr(0,havestrain.length - 1);
		    }
		    $scope.getRowspan = function(val1, val2, val3){
		  	    var val0 = 1;
		  	    if(val1 != null){
		  	  	  val0++;
		  	    }
		  	    if(val2 != null){
		  	  	  val0++;
		  	    }
		  	    if(val3 != null){
		  	  	  val0++;
		  	    }
		  	    return val0;
		    }
		    $scope.reportPrev = function(currentPage){
				if(currentPage > 1){
					var options = $rootScope.bsiReportParams;
					$.post("report/getPrevOrNextBSIReport",{"batch":options.batch,"period":options.period,"beginDate":options.beginDate,"endDate":options.endDate,"isPrev":true,"page":currentPage-1,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName},function(response){
						if(response != null &&response !=""){
							$("#container").html(response);
						}
					});
				}
			}
			$scope.reportNext = function(currentPage){
				var totalPage = $("#total-page-hide").val();
				currentPage = parseInt(currentPage);
				if(currentPage < totalPage){
					var options = $rootScope.bsiReportParams;
					$.post("report/getPrevOrNextBSIReport",{"batch":options.batch,"period":options.period,"beginDate":options.beginDate,"endDate":options.endDate,"isPrev":false,"page":currentPage+1,"totalPage":totalPage,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName},function(response){
						if(response != null &&response !=""){
							$("#container").html(response);
						}
					});
				}
			}
		});
	});
	celloudApp.controller("bsiReportController", function($scope, $rootScope, bsiService) {
		$rootScope.bsiReportParams = {
			page: 1,
		    size: 20,
		    condition: null,
		    sort: 0,
		    sortBatch: "asc",
		    sortName: "asc",
		    sortPeriod: "asc",
		    sortDate: "desc",
		    reportType: 0,  //0:患者报告  1：分析报告
		    batch: null,
		    period: null,
		    beginDate: null,
		    endDate: null,
		    distributed: null, //0:是   1： 否
		    sampleName: null
		},
		$scope.pageQuery = function(){
			bsiService.reportPageQuery($rootScope.bsiReportParams).
			success(function(dataMap){
				$scope.batchList = dataMap.batchList;
				$scope.pageList = dataMap.pageList;
				$scope.periodMap = dataMap.periodMap;
				$scope.nowDate = dataMap.nowDate;
			})
		}
		/**
		 * 点击单个标签时的搜索
		 */
		$scope.reportBatchSearch = function(batchId){
			if(!$("#batch-sl").hasClass("select-more")){
				$rootScope.bsiReportParams.batch = "'" + $("#" + batchId).next().find("span").text() + "'";
				$rootScope.bsiReportParams.page = 1;
				$scope.pageQuery();
				$("#selected-batch span").html($("#" + batchId).next().find("span").text());
				$("#selected-batch").removeClass("hide");
				$("#to-sl-batch").addClass("hide");
			}
		}
		/**
		 * 取消单个标签时的搜索
		 */
		$scope.clearSlBatch = function(){
			$("#selected-batch").addClass("hide");
	        $("#to-sl-batch").removeClass("hide");
	        $("#batch-more").removeClass("disabled");
	        $("#batch-more").attr("disabled",false);
	        $("#batch-lists").find(".multisl-btns").addClass("hide");
	        $rootScope.bsiReportParams.batch = null;
	        $rootScope.bsiReportParams.page = 1;
	        $scope.pageQuery();
		}
		/**
		 * 更多, 收起
		 */
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
		/**
		 * 标签(批次)多选按钮
		 */
		$scope.batchMultiselect = function(){
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
	        
	        $("#batch-lists").addClass("show-more");
            $("#batch-more span").html("收起");
            $("#batch-more i").removeClass("fa-chevron-down").addClass("fa-chevron-up");
            $("#batch-lists .checkbox").removeClass("checkbox-ed").addClass("checkbox-un");
            $("#report-multibatch-search").addClass("disabled");
            $("#report-multibatch-search").attr("disabled",true);
            $("#batch-more").addClass("disabled");
            $("#batch-more").attr("disabled",true);
		}
		/**
		 * 标签(批次)多选框按钮
		 */
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
		/**
		 * 确定标签(批次)多选框按钮
		 */
		$scope.reportMultibatchSearch = function(){
			var show_val = [];
	        $("#batch-lists .checkbox-ed").each(function(){
	            $rootScope.bsiReportParams.batch == null? $rootScope.bsiReportParams.batch = "'"+$(this).next().text()+"'" : $rootScope.bsiReportParams.batch += ",'"+$(this).next().text() + "'";
	            show_val.push($(this).next().text());
	        });
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
		 * 取消标签(批次)多选
		 */
		$scope.resetBatchMultiSelect = function(){
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
		/**
		 * 点击单个状态搜索
		 */
		$scope.reportPeriodSearch = function(periodId){
			if(!$("#period-sl").hasClass("select-more")){
				$rootScope.bsiReportParams.period = $("#" + periodId).next().find("input").val();
				$rootScope.bsiReportParams.page = 1;
				$scope.pageQuery();
				$("#selected-period span").html($("#" + periodId).next().find("span").text());
				$("#selected-period").removeClass("hide");
				$("#to-sl-period").addClass("hide");
			}
		}
		/**
		 * 取消单个状态搜索
		 */
		$scope.clearSlPeriod = function(){
			$("#selected-period").addClass("hide");
			$("#to-sl-period").removeClass("hide");
			$rootScope.bsiReportParams.period = null;
			$scope.pageQuery();
		}
		/**
		 * 状态多选按钮
		 */
		$scope.periodMultiselect = function(){
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
		/**
		 * 状态多选框按钮
		 */
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
		/**
		 * 确定状态多选按钮
		 */
		$scope.reportMultiperiodSearch = function(){
			var show_val = [];
            $("#period-lists .checkbox-ed").each(function(){
              $rootScope.bsiReportParams.period == null? $rootScope.bsiReportParams.period = $(this).next().find("input[type='hidden']").val() : $rootScope.bsiReportParams.period += ","+$(this).next().find("input[type='hidden']").val();
              show_val.push($(this).next().find("span").html());
            });
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
		 * 取消状态多选
		 */
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
		/**
		 * 按照日期查询
		 */
		$scope.dateQuery = function(){
          $rootScope.bsiReportParams.beginDate = $("#report-begindate-search").val();
          $rootScope.bsiReportParams.endDate = $("#report-enddate-search").val();
          $scope.pageQuery();
        }
		/**
		 * 是否分发
		 */
		$scope.distributeQuery = function(){
			var distribute = $("#distribute");
			if(distribute.find(".sl-judge-no").hasClass("hide")){
				distribute.find(".sl-judge-no").removeClass("hide");
				distribute.find(".sl-judge-yes").addClass("hide");
	            $rootScope.bsiReportParams.distributed = 1;
	        }else{
	        	distribute.find(".sl-judge-yes").removeClass("hide");
	            distribute.find(".sl-judge-no").addClass("hide");
	            $rootScope.bsiReportParams.distributed = 0;
	        }
	        $scope.pageQuery();
		}
		$scope.sortDate = function(){
			$rootScope.bsiReportParams.sort = 0;
			$rootScope.bsiReportParams.sortDate = $rootScope.bsiReportParams.sortDate == "desc" ? "asc" : "desc";
			$rootScope.sortIcon($rootScope.bsiReportParams);
			$scope.pageQuery();
		}
		$scope.sortBatch = function(){
			$rootScope.bsiReportParams.sort = 1;
			$rootScope.bsiReportParams.sortBatch = $rootScope.bsiReportParams.sortBatch == "desc" ? "asc" : "desc";
			$rootScope.sortIcon($rootScope.bsiReportParams);
			$scope.pageQuery();
		}
		$scope.sortName = function(){
			$rootScope.bsiReportParams.sort = 2;
			$rootScope.bsiReportParams.sortName = $rootScope.bsiReportParams.sortName == "desc" ? "asc" : "desc";
			$rootScope.sortIcon($rootScope.bsiReportParams);
			$scope.pageQuery();
		}
		$scope.sortPeriod = function(){
			$rootScope.bsiReportParams.sort = 3;
			$rootScope.bsiReportParams.sortPeriod = $rootScope.bsiReportParams.sortPeriod == "desc" ? "asc" : "desc";
			$rootScope.sortIcon($rootScope.bsiReportParams);
			$scope.pageQuery();
		}
		$scope.pageSizeQuery = function(){
			$rootScope.bsiReportParams.size = $("#page-size-sel").val();
			$rootScope.bsiReportParams.page = 1;
			$scope.pageQuery();
		}
		$scope.reRun = function(dataKey,appId,projectId){
			bsiService.reportReRun(dataKey,appId,projectId).
			success(function(){
				$scope.pageQuery();
			})
		}
		$scope.paginationBtn = function(currentPage){
			$rootScope.bsiReportParams.page = currentPage;
			$scope.pageQuery();
		}
		$scope.periodError = function(dataName){
			$("#run-error-data").html(dataName);
			$("#running-error-modal").modal("show");
		}
//		$("#condition-find").unbind("click");
//        $("#condition-find").on("click",function(){
//          $.report.options.condition = $("#condition-input").val();
//          $.report.find.condition();
//        });
		
//        $("body").on("click","[data-click='report-check-all']",function(){
//          if($(this).hasClass("checkbox-ed")){
//            $(this).removeClass("checkbox-ed").addClass("checkbox-un");
//            $(".table>tbody .checkbox,.pagination .checkbox").removeClass("checkbox-ed").addClass("checkbox-un");
//          }else{
//            $(this).removeClass("checkbox-un").addClass("checkbox-ed");
//            $(".table>tbody .checkbox,.pagination .checkbox").addClass("checkbox-ed").removeClass("checkbox-un");
//          }
//        });
//		    all: function(){
//		      $.get("report/bsi/reportList",function(response){
//		        $.report.loadlist(response);
//		        $.report.options = {
//		            condition: null,
//		            sort: 0,
//		            sortBatch: "asc",
//		            sortName: "asc",
//		            sortPeriod: "asc",
//		            sortDate: "desc",
//		            pageSize: $("#page-size-sel").val(),
//		            reportType: 0,  //0:患者报告  1：分析报告
//		            batch: null,
//		            period: null,
//		            beginDate: null,
//		            endDate: null,
//		            distributed: null, //是否分发  0:是   1： 否 
//		            sampleName: null
//		        };
//		      });
//		    },
//		    condition: function(){
//		      var options = $.report.options;
//		      $.get("report/bsi/searchReportList",{"sampleName":options.sampleName,"batch":options.batch,"period":options.period,"beginDate":options.beginDate,"endDate":options.endDate,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName,"size":options.pageSize},function(response){
//		        $.report.loadlist(response);
//		      });
//		    },
//		    pagination: function(currentPage){
//		      var options = $.report.options;
//		      $.get("report/bsi/searchReportList",{"sampleName":options.sampleName,"batch":options.batch,"period":options.period,"beginDate":options.beginDate,"endDate":options.endDate,"page":currentPage,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName,"size":options.pageSize},function(response){
//		        $.report.loadlist(response);
//		      });
//		    }
//		  },
//		  loadlist: function(response){
//		    $("#report-list").html(response);
//		    $("#sample-selector").val($.report.options.sampleName);
//		    $("#sample-selector").on("keyup",function(e){
//		      e = e || window.event;
//		      if (e.keyCode == "13") {//keyCode=13是回车键
//		        $.report.options.sampleName = $("#sample-selector").val();
//		        $.report.find.condition();
//		      }
//		    });
//		    $("#data-list-tbody").find("td[name='data-name-td']").each(function(){
//		      var _data = $(this).attr("title");
//		      if(_data.length>40){
//		        var newData = utils.splitDataByInfo(_data, "\r\n" ,40);
//		        $(this).attr("title",newData);
//		      }
//		    });
//		  },
//		  detail: {
//		    option: {
//		      batchPage: 1
//		    },
//		    patient: function(dataKey,projectId,appId,reportIndex,currentPage){
//		      var options = $.report.options;
//		      $.post("report/getBSIPatientReport",{"sampleName":options.sampleName,"reportIndex":reportIndex,"dataKey":dataKey,"projectId":projectId,"appId":appId,"page":currentPage,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName,"size":options.pageSize},function(response){
//		        $("#container").html(response);
//		      });
//		    },
//		    analy: function(dataKey,projectId,appId){
//		      $.get("report/getBSIAnalyReport",{"dataKey":dataKey,"projectId":projectId,"appId":appId},function(response){
//		        $("#myTabContent").html(response);
//		      });
//		    },
//		  },
//		  period: {
//		      error: function(dataName){
//		        $("#run-error-data").html(dataName);
//		        $("#running-error-modal").modal("show");
//		      }
//		  }
		$scope.pageQuery();
	});
	celloudApp.controller("bsiDataController", function($scope, $rootScope, bsiService) {
		$scope.params = {
			page: 1,
			size: $("#page-size-sel").val(),
	        condition: null,
	        sort: 0,
	        sortBatch: "asc",
	        sortName: "asc",
	        sortDate: "desc"
	    },
	    $scope.pageQuery = function(){
			bsiService.dataPageQuery($scope.params).
			success(function(dataMap){
				$scope.pageList = dataMap.pageList;
			})
		}
		// 分页按钮
		$scope.paginationBtn = function(currentPage){
			$scope.params.page = currentPage;
			$scope.pageQuery();
		}
		$scope.sortDate = function(){
			$scope.params.sort = 0;
			$scope.params.sortDate = $scope.params.sortDate == "desc" ? "asc" : "desc";
			$rootScope.sortIcon($scope.params);
			$scope.pageQuery();
		}
		$scope.sortBatch = function(){
			$scope.params.sort = 1;
			$scope.params.sortBatch = $scope.params.sortBatch == "desc" ? "asc" : "desc";
			$rootScope.sortIcon($scope.params);
			$scope.pageQuery();
		}
		$scope.sortName = function(){
			$scope.params.sort = 2;
			$scope.params.sortName = $scope.params.sortName == "desc" ? "asc" : "desc";
			$rootScope.sortIcon($scope.params);
			$scope.pageQuery();
		}
		$scope.pageSizeQuery = function(){
			$scope.params.size = $("#page-size-sel").val();
			$scope.params.page = 1;
			$scope.pageQuery();
		}
		$scope.pageQuery();
	});
//	    loadlist: function(response){
//	      $("#container").html(response);
//	      $("#data-list-tbody").find("td[name='data-name-td']").each(function(){
//	        var _data = $(this).attr("title");
//	        if(_data.length>40){
//	          var newData = utils.splitDataByInfo(_data, "\r\n" ,40);
//	          $(this).attr("title",newData);
//	        }
//	      });
//	    }
})();
