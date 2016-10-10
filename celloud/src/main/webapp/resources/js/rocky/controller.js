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
})();
