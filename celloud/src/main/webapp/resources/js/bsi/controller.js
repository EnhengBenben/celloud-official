(function() {
	celloudApp.controller("bsiFileUpload",function($scope, $location, $rootScope){
		$scope.uploadPercent = 0;
		$rootScope.bsiFinished = false;
		//  ============================上传============================
		$scope.itemBtnToggleActive = function(){
		    $("#common-menu .item-btn").removeClass("active");
		    $("#to-upload-a").addClass("active");
		}
		$("#bsi-upload-modal").on("hidden.bs.modal",function(e){
			$("#to-upload-a").removeClass("active");
			if($location.path().indexOf("data") > -1){
				$("#to-data-a").addClass("active");
			}else if($location.path().indexOf("report") > -1){
				$("#to-report-a").addClass("active");
			}
		});
		$rootScope.bsiStepOne = function(){
			
			$scope.itemBtnToggleActive();
			// 判断是否在第二步, 进行回显
			if($rootScope.bsiUploader && $rootScope.bsiStep == 'two' && $rootScope.bsiUploader.files.length > 0){
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
			}else if($rootScope.bsiUploader && $rootScope.bsiStep == 'three' && $rootScope.bsiUploader.files.length > 0){ // 在第三步
				$("#upload-filelist").children().remove();
				$("#uploading-filelist").children(":not(:first)").remove();
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
				$scope.beginBsiUpload();
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
			    $rootScope.bsiFinished = false;
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
		    $("#bsi-one-to-two").addClass("active");
		    $(".step-two").addClass("active");
		    $scope.uploadTextType();
			$rootScope.bsiBatch = $scope.bsiBatch;
			// 如果不存在bsiUploader才去创建, 用于回显
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
				    config = "https://"+config.intranetAddress+":"+port+context;
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
			    		if(item.size > 0){
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
					        	$scope.uploadTextType();
					        });
					        $('#uploading-' + item.id + '.plupload_delete a').click(function(e) {
					        	$('#' + item.id).remove();
					        	$('#uploading-' + item.id).remove();
					        	uploader.removeFile(item);
					        	e.preventDefault();
					        	utils.stopBubble(e);
					        });
			    		}else{
			    			uploader.removeFile(item);
			    		}
			        });
			    	if($rootScope.bsiStep == 'one'){
			    		$rootScope.bsiStep = 'two';
			    	}
			        $scope.uploadTextType();
			        $rootScope.$apply();
			    });
			    uploader.bind("FileUploaded", function(uploader, file, response) {
			    	var res = response.response;
			    	handleStatus(file);
			    });
			    uploader.bind("FilesRemoved", function(uploader, files) {
			    	$rootScope.$apply();
			    	if(files.length <= 0){
			    		$scope.bsiBatch = "";
			    	}
			    });
			    uploader.bind("UploadComplete",function(uploader,files){
			    	if(files.length>0){
			    		waveLoading.setProgress(100);
			    		$scope.uploadPercent = 100;
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
			    	$("#bsi-one-to-two").removeClass("active");
			    	$("#bsi-two-to-three").removeClass("active");
			    	$(".step-two").removeClass("active");
			    	$(".step-three").removeClass("active");
			    	$("#batch-info").val("")
			    	uploader.splice();
			    	$rootScope.bsiFinished = true;
			    	$rootScope.bsiStep = "one";
			    	$scope.bsiBatch = "";
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
		
		window.onbeforeunload=function(){
		  if($rootScope.bsiUploader){
		    var qp=$rootScope.bsiUploader.total;
		    var percent=qp.percent;
		    if(qp.size>0&&percent<100&&percent>0){
		      return "数据正在上传，您确定要关闭页面吗?"
		    }
		  }
		}
		
		$scope.beginBsiUpload = function(){
			$(".step-three-content").removeClass("hide");
    		$(".step-one-content").addClass("hide");
    		$(".step-two-content").addClass("hide");
    		$("#bsi-one-to-two").addClass("active");
    		$("#bsi-two-to-three").addClass("active");
    		$(".step-two").addClass("active");
    		$(".step-three").addClass("active");
    		$("#tags-review").html($rootScope.bsiBatch);
			if($rootScope.bsiStep != 'three'){
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
	    		$("#bsi-one-to-two").removeClass("active");
	    		$(".step-two").removeClass("active");
	    		$(".step-three-content").addClass("hide");
	    		$("#bsi-two-to-three").removeClass("active");
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
		
		if($rootScope.bsiUploader){
			waveLoading.init({
    			haveInited: true,
    			target: document.querySelector('#upload-progress'),
    			color: 'rgba(40, 230, 200, 0.6)',
    			showText: false
    		});
    		waveLoading.draw();
    		waveLoading.setProgress($rootScope.bsiUploader.total.percent);
		}else if($rootScope.bsiFinished){
			waveLoading.init({
    			haveInited: true,
    			target: document.querySelector('#upload-progress'),
    			color: 'rgba(40, 230, 200, 0.6)',
    			showText: false
    		});
    		waveLoading.draw();
    		waveLoading.setProgress(100);
		}
		
		$rootScope.sortIcon = function(params){
			if(params.sort == 0){
			    if(params.sortDate=="asc"){
			      $(".sort-date-icon").removeClass("fa-sort-amount-desc").addClass("fa-sort-amount-asc");
			    }else{
			      $(".sort-date-icon").removeClass("fa-sort-amount-asc").addClass("fa-sort-amount-desc");
			    }
			}
			if(params.sort == 1){
			    if(params.sortBatch=="asc"){
			      $(".sort-batch-icon").removeClass("fa-sort-amount-desc").addClass("fa-sort-amount-asc");
			    }else{
			      $(".sort-batch-icon").removeClass("fa-sort-amount-asc").addClass("fa-sort-amount-desc");
			    }
			}
		    if(params.sort == 2){
		    	if(params.sortName=="asc"){
	    		  $(".sort-name-icon").removeClass("fa-sort-amount-desc").addClass("fa-sort-amount-asc");
		    	}else{
	    		  $(".sort-name-icon").removeClass("fa-sort-amount-asc").addClass("fa-sort-amount-desc");
		    	}
		    }
		    if(params.sort == 3){
			    if(params.sortPeriod=="asc"){
		    	  $(".sort-period-icon").removeClass("fa-sort-amount-desc").addClass("fa-sort-amount-asc");
			    }else{
		    	  $(".sort-period-icon").removeClass("fa-sort-amount-asc").addClass("fa-sort-amount-desc");
			    }
		    }
	    }
		
	});
	celloudApp.controller("bsiReportDataController",function($scope, $rootScope, $routeParams, bsiService){
		$scope.params = {
			"dataKey":$routeParams.dataKey,
			"projectId":$routeParams.projectId,
			"appId":$routeParams.appId,
			"batch":$routeParams.batch == 'null' ? null : $routeParams.batch,
			"dataIndex":$routeParams.dataIndex
		};
		
		$scope.batchPageQuery = function(params){
			bsiService.getBatchPageList(params).
	    	success(function(dataMap){
	    		$scope.batchPageList = dataMap.batchPageList;
	    		if(dataMap.dataIndexFlag){
	    			$scope.dataIndex = dataMap.dataIndex;
	    		}
	    	})
		}
		
		$scope.batchPageQueryBtn = function(currentPage){
			$scope.batchBtnParams = angular.copy($scope.batchParams);
			$scope.batchBtnParams.page = currentPage;
			$scope.batchBtnParams.dataIndex = 0;
			$scope.batchBtnParams.dataKey = null;
			$scope.batchPageQuery($scope.batchBtnParams);
		}
		
		bsiService.getBSIPatientReport($scope.params).
		success(function(dataMap){
			$scope.bsi = dataMap.bsi;
			$scope.data = dataMap.data;
			$rootScope.tab = $rootScope.tab == undefined?'patient':$rootScope.tab;
		    $scope.havestrain = "";
		    
		    $scope.changeTab = function(tab){
		    	$rootScope.tab = tab;
		    }
		    
		    if($scope.bsi){
		    	$scope.batchParams = {
		    		"page" : 1,
		    		"batch" : $scope.data.batch,
		    		"appId" : $scope.bsi.appId,
		    		"dataKey" : $scope.bsi.dataKey,
		    		"dataIndex" : $scope.params.dataIndex
		    	}
		    }
		    $scope.batchPageQuery($scope.batchParams);
		    
		    if($scope.bsi && $scope.bsi.species_20){
		    	for(var i=0;i<$scope.bsi.species_20.length;i++){
		    		$scope.havestrain += $scope.bsi.species_20[i].species_zh + ",";
			    }
			    $scope.havestrain = $scope.havestrain.substr(0,$scope.havestrain.length - 1);
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
				if(currentPage > 0){
					var options = $scope.params;
					window.location.href = "index#/product/bactive/rdata/" + $scope.params.dataKey + "/" + $scope.params.projectId + "/" + $scope.params.appId + "/" + $scope.data.batch + "/" + currentPage;
				}
			}
			$scope.reportNext = function(currentPage){
				var rowCount = $scope.batchPageList.page.rowCount;
				currentPage = parseInt(currentPage);
				if(currentPage <= rowCount){
					var options = $scope.params;
					window.location.href = "index#/product/bactive/rdata/" + $scope.params.dataKey + "/" + $scope.params.projectId + "/" + $scope.params.appId + "/" + $scope.data.batch + "/" + currentPage;
				}
			}
		});
	});
	
	celloudApp.controller("bsiReportController", function($scope, $rootScope, $routeParams, bsiService) {
	  $rootScope.appId = $routeParams.appId;
		$scope.bsiReportParams = {
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
		    sampleName: null,
		    appId: $rootScope.appId
		}
		$scope.pageQuery = function(){
			bsiService.reportPageQuery($scope.bsiReportParams).
			success(function(dataMap){
				$scope.batchList = dataMap.batchList;
				$scope.pageList = dataMap.pageList;
				$scope.periodMap = dataMap.periodMap;
				$scope.nowDate = dataMap.nowDate;
			})
		}
		$scope.conditionFind = function(){
			$scope.bsiReportParams.condition = $("#condition-input").val();
			$scope.bsiReportParams.page = 1;
			$scope.pageQuery();
		}
		$scope.conditionSearch = function($event){
			if($event.keyCode == 13){
	    		$scope.conditionFind();
	    	}
		}
		/**
		 * 点击单个标签时的搜索
		 */
		$scope.reportBatchSearch = function(batchId){
			if(!$("#batch-sl").hasClass("select-more")){
				$scope.bsiReportParams.batch = "'" + $("#" + batchId).next().find("span").text() + "'";
				$scope.bsiReportParams.page = 1;
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
	        $scope.bsiReportParams.batch = null;
	        $scope.bsiReportParams.page = 1;
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
	            $scope.bsiReportParams.batch == null? $scope.bsiReportParams.batch = "'"+$(this).next().text()+"'" : $scope.bsiReportParams.batch += ",'"+$(this).next().text() + "'";
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
				$scope.bsiReportParams.period = $("#" + periodId).next().find("input").val();
				$scope.bsiReportParams.page = 1;
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
			$scope.bsiReportParams.period = null;
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
              $scope.bsiReportParams.period == null? $scope.bsiReportParams.period = $(this).next().find("input[type='hidden']").val() : $scope.bsiReportParams.period += ","+$(this).next().find("input[type='hidden']").val();
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
          $scope.bsiReportParams.beginDate = $("#report-begindate-search").val();
          $scope.bsiReportParams.endDate = $("#report-enddate-search").val();
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
	            $scope.bsiReportParams.distributed = 1;
	        }else{
	        	distribute.find(".sl-judge-yes").removeClass("hide");
	            distribute.find(".sl-judge-no").addClass("hide");
	            $scope.bsiReportParams.distributed = 0;
	        }
	        $scope.pageQuery();
		}
		$scope.sortDate = function(){
			$scope.bsiReportParams.sort = 0;
			$scope.bsiReportParams.sortDate = $scope.bsiReportParams.sortDate == "desc" ? "asc" : "desc";
			$rootScope.sortIcon($scope.bsiReportParams);
			$scope.pageQuery();
		}
		$scope.sortBatch = function(){
			$scope.bsiReportParams.sort = 1;
			$scope.bsiReportParams.sortBatch = $scope.bsiReportParams.sortBatch == "asc" ? "desc" : "asc";
			$rootScope.sortIcon($scope.bsiReportParams);
			$scope.pageQuery();
		}
		$scope.sortName = function(){
			$scope.bsiReportParams.sort = 2;
			$scope.bsiReportParams.sortName = $scope.bsiReportParams.sortName == "asc" ? "desc" : "asc";
			$rootScope.sortIcon($scope.bsiReportParams);
			$scope.pageQuery();
		}
		$scope.sortPeriod = function(){
			$scope.bsiReportParams.sort = 3;
			$scope.bsiReportParams.sortPeriod = $scope.bsiReportParams.sortPeriod == "asc" ? "desc" : "asc";
			$rootScope.sortIcon($scope.bsiReportParams);
			$scope.pageQuery();
		}
		$scope.pageSizeQuery = function(){
			$scope.bsiReportParams.size = $("#page-size-sel").val();
			$scope.bsiReportParams.page = 1;
			$scope.pageQuery();
		}
		$scope.reRun = function(dataKey,appId,projectId){
			bsiService.reportReRun(dataKey,appId,projectId).
			success(function(){
				$scope.pageQuery();
			})
		}
		$scope.paginationBtn = function(currentPage){
			$scope.bsiReportParams.page = currentPage;
			$scope.pageQuery();
		}
		$scope.periodError = function(dataName){
			$("#run-error-data").html(dataName);
			$("#running-error-modal").modal("show");
		}
		$scope.pageQuery();
	});
	celloudApp.controller("bsiDataController", function($scope, $rootScope, $routeParams, bsiService) {
	  $rootScope.appId = $routeParams.appId;
		$scope.params = {
			page: 1,
			size: $("#page-size-sel").val(),
      condition: null,
      sort: 0,
      sortBatch: "asc",
      sortName: "asc",
      sortDate: "desc",
      appId: $routeParams.appId
    }
    $scope.pageQuery = function(){
  		bsiService.dataPageQuery($scope.params).
  		success(function(dataMap){
  			$scope.pageList = dataMap.pageList;
  		})
		}
		$scope.conditionFind = function(){
			$scope.params.condition = $("#condition-input").val();
			$scope.params.page = 1;
			$scope.pageQuery();
		}
		$scope.conditionSearch = function($event){
			if($event.keyCode == 13){
	    		$scope.conditionFind();
	    	}
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
			$scope.params.sortBatch = $scope.params.sortBatch == "asc" ? "desc" : "asc";
			$rootScope.sortIcon($scope.params);
			$scope.pageQuery();
		}
		$scope.sortName = function(){
			$scope.params.sort = 2;
			$scope.params.sortName = $scope.params.sortName == "asc" ? "desc" : "asc";
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
})();
