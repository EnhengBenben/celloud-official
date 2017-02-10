(function() {
	celloudApp.controller("fileUpload", function($route, $location, $scope, $rootScope, uploadService) {
		$scope.step = 'one';
		$rootScope.getProTags = function(){
			// 判断是否在产品内部
			if($location.path().indexOf('/product/rocky') > -1){
				if($location.path().indexOf('upload') > -1){
					$route.reload();
				}else{
					window.location.href = "#/product/rocky/upload";
				}
			}else if($location.path().indexOf('/product/bactive') > -1){
				
			}else{
				// 检查用户角色是否为测试账号
				uploadService.checkRole().
				success(function(data){
					if(data == "0"){
						alert("测试账号不可上传，请联系我们注册正式账号");
					}else{
						if($scope.step == 'one'){
							$scope.upload.splice();
							$rootScope.tags = uploadService.getProductTags().query();
						}
						$("#upload-modal").modal("show");
					}
				})
			}
		}
		$scope.nextStep = function(){
			$scope.step = 'two';
		}
		$scope.beginUpload = function(){
			$scope.step = 'three';
			$scope.upload.start();
		}
		$scope.uploadMore = function(){
			var count = getUploadingCount($scope.upload);
			if(count == 0){
				$scope.step = 'two';
			}
		}
		var initUploader = function(button){
			var uploader = new plupload.Uploader({
				runtimes : 'html5,flash,silverlight,html4',
				browse_button : button,
				url : "../uploadFile/uploadManyFile",
				// Maximum file size
				chunk_size : '1mb',
				dragdrop : true,
				unique_names:true,
				drop_element : 'plupload-content', // 设置可拖拽上传
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
				flash_swf_url : '//cdn.bootcss.com/plupload/2.1.8/Moxie.swf',
			});
			uploader.bind("StateChanged", function() {
				//文件队列开始上传
				if (uploader.state === plupload.STARTED) {
					//防止文件上传时意外关闭了浏览器、页签或者上传页面
					window.parent.isUploading = true;
					//防止session超时
					refresh = setInterval(function refreshSession(){
						//为了防止上传过程中session超时而随便请求的一个方法
						$.get("uploadFile/checkAdminSessionTimeOut");
					},600000);
				}else if(uploader.state === plupload.STOPPED){//文件队列全部上传结束
					window.parent.isUploading = false;
					clearInterval(refresh);
				}
			});
			uploader.bind("UploadProgress", function(uploader, file) { // 上传过程中
				$("#uploading-" + file.id +" .plupload-file-status").html(file.percent+"%");
				$("#uploading-" + file.id + " .plupload-file-surplus").html(utils.formatDate((file.size-file.loaded)/uploader.total.bytesPerSec));
				$("#uploading-" + file.id + " .plupload_file_speed").html(getSize(uploader.total.bytesPerSec)+"/s");
			});
			uploader.bind("FilesAdded", function(uploader, files) { // 文件添加结束后
				$.get("uploadFile/checkAdminSessionTimeOut",function(response){
					if(response){//session超时则执行下两步
					  
					}else{
						//销毁uploader，间接取消选择文件弹窗
						uploader.destroy();
					}
				});
				$.each(files, function(index, item) {
					if(item.size > 0){
						// 第二步骤
						var $fileDom_upload = $('<tr class="plupload_delete" id="upload-' + item.id + '">');
						$fileDom_upload.append($('<td class="plupload-file-name"><span title="' + item.name + '">' + item.name + '</span></td>'));
						$fileDom_upload.append($('<td class="plupload-file-size">'+getSize(item.size)+'</td>'));
						$fileDom_upload.append($('<td class="plupload-file-action"><a href="#" style="display: block;line-height: 30px;"><i class="fa fa-times-circle-o" aria-hidden="true"></i></a></td>'));
						$fileDom_upload.append($('</tr>'));
						$("#upload-list-tbody").append($fileDom_upload);
						// 第三步
						var $fileDom_uploading = $('<tr class="plupload_delete" id="uploading-' + item.id + '">');
						$fileDom_uploading.append($('<td class="plupload-file-name"><span title="' + item.name + '">' + item.name + '</span></td>'));
						$fileDom_uploading.append($('<td class="plupload-file-status">_</td>'));
						$fileDom_uploading.append($('<td class="plupload-file-surplus">_</td>'));
						$fileDom_uploading.append($('<td class="plupload_file_speed">_</td>'));
						$fileDom_uploading.append($('<td class="plupload-file-action"><a href="#" style="display: block;line-height: 30px;"><i class="fa fa-times-circle-o" aria-hidden="true"></i></a></td>'));
						$fileDom_uploading.append($('</tr>'));
						$("#uploading-list-tbody").append($fileDom_uploading);
						
						handleStatus(item);
						$('#upload-' + item.id + '.plupload_delete a').click(function(e) {
							$('#upload-' + item.id).remove();
							$('#uploading-' + item.id).remove();
							uploader.removeFile(item);
							e.preventDefault();
							utils.stopBubble(e);
							$scope.$apply();
						});
						$('#uploading-' + item.id + '.plupload_delete a').click(function(e) {
							var count = getUploadingCount(uploader);
							if(count == 1 && item.status != 5){
								$scope.isCancel = true;
							}
							$('#upload-' + item.id).remove();
							$('#uploading-' + item.id).remove();
							uploader.removeFile(item);
							e.preventDefault();
							utils.stopBubble(e);
						});
					}else{
						uploader.removeFile(item);
					}
				});
				if(uploader.files.length > 0){
					$scope.isCancel = false;
					$scope.$apply();
				}
			});
			uploader.bind("FileUploaded", function(uploader, file, response) {
				var res = response.response;
				if(res != "1"){
					$.get("data/run",JSON.parse(res),function(result){
						$.report.find.condition();
					});
				}
				handleStatus(file);
			});
			uploader.bind("UploadComplete",function(uploader,files){ // 文件上传完成(只要uploader中没有排队的文件就算完成)
				$scope.step = 'one';
				if(!$scope.isCancel){
					$("#upload-list-tbody").children().remove();
					$("#uploading-list-tbody").children().remove();
					$("#upload-modal").modal("hide");
					if($location.path() == '/data'){
						$route.reload();
					}
				}
			});
			uploader.bind("BeforeUpload", function(uploader, file) { // 上传之前设置参数
				uploader.setOption("multipart_params",{'size':file.size,'lastModifiedDate':file.lastModifiedDate,'originalName': file.name,'tagId':$scope.tagSelected.tagId,'batch': $scope.batch});
			});
			uploader.bind("Error", function(uploader, error) { // 发生错误
				if(error.code=='-602'){
					alert("当前队列已存在文件【"+error.file.name+"】，请勿重复添加！");
				}
			});
			return uploader;
		}
		$("#upload-modal").on("hidden.bs.modal",function(e){
			if($scope.step != 'three'){
				$("#upload-list-tbody").children().remove();
				$("#uploading-list-tbody").children().remove();
				$scope.step = 'one';
				$scope.$apply();
			}
		});
		$scope.upload = initUploader(["plupload-content","uploadMore"]);
		$scope.upload.init();
		
		window.onbeforeunload=function(){
			var qp=$scope.upload.total;
			var percent=qp.percent;
			if(qp.size>0&&percent<100&&percent>0){
				return "数据正在上传，您确定要关闭页面吗?"
			}
		}
		
	});
	function getUploadingCount(uploader){
		var count = 0;
		for(var i = 0; i < uploader.files.length; i++){
			if(uploader.files[i].status != 5){
				count++;
			}
		}
		return count;
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
})();
