(function(){
	celloudApp.controller("updateBaseInfo",function($scope,userService){
		userService.getUserInfo().
		success(function(data){
			$scope.user = data;
			$scope.user_bak = angular.copy(data);
		});
		$scope.updateUserInfo = function(){
			userService.updateUserInfo($scope.user).
			success(function(data){
				$.alert(data.message);
			}).
			error(function(data){
				$.alert(data.message);
			});
		};
		$scope.reset = function(){
			$scope.user = angular.copy($scope.user_bak);
		}
	});
	celloudApp.controller("updatePassword",function($scope,userService){
		$scope.reset = function() {
	        $scope.oldPwd = "";
	        $scope.newPwd = "";
	        $scope.confirmPwd = "";
	    };
	    $scope.updatePassword = function(){
	    	$scope.state = false;
	    	userService.updatePassword($scope.oldPwd,$scope.newPwd).
	    	success(function(data){
	    		$scope.code = data.code;
	    		if($scope.code==203){
	    			$scope.pwdMessage = data.message;
	    		}else{
	    			$.alert(data.message);
	    		}
	    	}).error(function(data) {

			});
		};
		$scope.checkOldPwd = function(){
			if($.trim($scope.oldPwd) == ""){
				$scope.code = 0;
			}
		};
	});
	celloudApp.controller("pageQueryLog",function($scope,$rootScope,$routeParams,$location,userService){
		$scope.pageQueryLog = function(currentPage,pageSize){
			userService.pageQueryLog(currentPage,pageSize).
			success(function(dataList){
				$scope.dataList = dataList;
			});
		}
		$scope.pageList = function(pageSize){
		  $rootScope.pageSize = pageSize;
		  $scope.pageQueryLog(1,pageSize);
		  $location.path($scope.pageType);
		}
		if($routeParams.page == null){
		  $scope.pageQueryLog(1,$rootScope.pageSize);
		  $location.path($scope.pageType);
		}else{
		  $scope.pageQueryLog($routeParams.page,$rootScope.pageSize);
		}
	});
	celloudApp.controller("updateEmail",function($scope,userService){
		userService.getUserInfo().
		success(function(data){
			$scope.user = data;
		});
		$scope.isSend = false;
		$scope.updateEmail = function(){
			userService.updateEmail($scope.user.email).
			success(function(data){
				if(data==1){
					$scope.sendFail = true;
				}else if(data==0){
					$scope.sendSuccess = true;
				}
			})
		}
	});
	celloudApp.controller("setReportController",function($scope,userService){
		$scope.icon = "";
		$scope.haveImage = false;
		userService.getUserCompanyIcon().
		success(function(company){
			$scope.icon = company.companyIcon;
			if($scope.icon == ''){
				$scope.isTemp = true;
			}else{
				$scope.haveImage = true;
				$scope.isTemp = false;
				$scope.path = CONTEXT_PATH + "/user/icon?file=" + $scope.icon;
			}
		});
		$scope.apply = function(){
			userService.setUserCompanyIcon($scope.icon).
			success(function(data){
				if(data > 0){
					$.alert("更新成功!");
					$scope.icon = "";
				}
			})
		}
		var initUploader = function(button) {
			var uploader = new plupload.Uploader({
				browse_button : button,
				url : "../user/uploadCompanyIcon",
				chunk_size : "2mb",
				file_data_name : 'file',
				filters : {
					max_file_size : "10mb",
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
			$scope.tempPath = CONTEXT_PATH + "/user/icon/temp?file=" + response.response;
			$scope.icon = response.response;
			$scope.isTemp = true;
			$scope.haveImage = true;
			$scope.$apply();
		});
		unsaveUploader.init();
	});
})();