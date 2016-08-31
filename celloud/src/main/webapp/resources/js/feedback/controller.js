(function() {
	celloudApp.controller("feedbackController", function($scope,
			feedbackService) {
		var loadFeedbacks = function() {
			$scope.feedbacks = feedbackService.list(function(data) {
				if (data.datas.length > 0) {
					$scope.change(data.datas[0].id);
				}
			});
		};
		loadFeedbacks();
		$scope.change = function(id) {
			$scope.current = feedbackService.get({
				id : id
			});
		};
		$scope.solve = function(id) {
			feedbackService.solve({
				id : id
			}, function() {
				$scope.change(id);
			});
		};
		$scope.reply = function(id, content) {
			if (!content) {
				return;
			}
			feedbackService.reply({
				id : id,
				content : content
			}, function() {
				$scope.change(id);
				$scope.replyContent = '';
			});
		};
		$scope.replyKeyUp = function($event) {
			if ($event.keyCode == 13) {
				$scope.reply($scope.current.id, $scope.replyContent);
			}
		};
		$scope.saveFeedback = function(isValid) {
			if (isValid) {
				feedbackService.save({
					title : $scope.unsavedFeedback.title,
					content : $scope.unsavedFeedback.content,
					attachments : $scope.unsavedAttachments
				}, function() {
					$scope.unsavedAttachments = [];
					$("#new-qa-modal").modal('hide');
					loadFeedbacks();
					$scope.resetCreateForm();
				});
			}
		};
		$scope.resetCreateForm = function() {
			$scope.unsavedFeedback = angular.copy({});
			$scope.unsavedAttachments = [];
			unsaveUploader.splice(0, unsaveUploader.files.length);
		}
		$scope.deleteAttach = function(attachId, name) {
			feedbackService.deleteAttach({
				name : name,
				attachId : attachId
			}, function(data) {
				$.alert(data.message);
				$("#attachmentModal").modal('hide');
				$scope.currentAttach = null;
				$scope.change($scope.current.id);
			});
		}
		$scope.showAttach = function(attach) {
			$scope.currentAttach = attach;
			$("#attachmentModal").modal("show");
		}
		var initUploader = function(button) {
			var uploader = new plupload.Uploader({
				browse_button : button,
				url : "../feedback/attach",
				chunk_size : "2mb",
				file_data_name : 'file',
				filters : {
					max_file_size : "2mb",
					mime_types : [ {
						title : "Image files",
						extensions : "jpg,jpeg,png"
					} ],
					prevent_duplicates : true
				// 不允许选取重复文件
				},
				multi_selection : false,
				flash_swf_url : '../plugins/plupload-2.1.2/Moxie.swf',
				init : {
					FilesAdded : function(uploader, files) {
						uploader.start();
					},
					QueueChanged : function(uploader) {
						uploader.disableBrowse(uploader.files.length >= 5);
					}
				}
			});
			return uploader;
		};
		var unsaveUploader = initUploader('unsavedAttachmentUploadBtn');
		unsaveUploader.bind("BeforeUpload", function(uploader, file) {
			uploader.setOption("multipart_params", {
				"fileName" : file.name
			});
		});
		unsaveUploader.bind("FileUploaded", function(uploader, file, response) {
			var attachments = $scope.unsavedAttachments || [];
			attachments.push(response.response);
			$scope.unsavedAttachments = attachments;
			$scope.$apply();
		});
		unsaveUploader.init();
		var savedUploader = initUploader('attachmentUploadBtn');
		savedUploader.bind("BeforeUpload", function(uploader, file) {
			uploader.setOption("multipart_params", {
				"fileName" : file.name,
				"feedbackId" : $scope.current.id
			});
		});
		savedUploader.bind("FileUploaded", function(uploader, file, response) {
			$scope.change($scope.current.id);
		});
		savedUploader.init();
	});
})();