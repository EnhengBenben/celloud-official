(function(){
	celloudApp.controller("updateBaseInfo",function($route, $scope,userService){
		userService.getUserInfo().
		success(function(data){
			$scope.user = data;
			$scope.user_bak = angular.copy(data);
		});
		$scope.sendCaptcha = function(){
			userService.sendCaptcha($scope.user.cellphone).
			success(function(data, status){
				if(status == 200){
					// 倒计时60秒
			        time = 60;
			        $("#captchaButton").prop("disabled",true);
			        $("#captchaButton").html("重新发送(<span id='times'>60</span>)");
			        var setinterval = setInterval(function(){
			            time--;    
			            if(time==0){
			                $("#captchaButton").prop("disabled",false);
			                clearInterval(setinterval);
			                $("#captchaButton").html("重新发送");
			            } else {
			                $("#times").html(time);
			            }
			        }, 1000);
				}
			}).
			error(function(data, status){
				if(status == 403){
					alert("测试账号不允许修改信息");
				}
				if(status == 400){
					// 参数错误
					$.alert("手机号格式有误!");
				}
				if(status == 500){
					// 请勿频繁发送
					$.alert("请勿频繁获取验证码");
				}
			})
		};
		$scope.authenticationCellphone = function(){
			userService.authenticationCellphone($scope.user.cellphone, $scope.captcha).
			success(function(data, status){
				$route.reload();
				$.alert(data.message);
			}).
			error(function(data, status){
				if(status == 403){
					alert("测试账号不允许修改信息");
				}else{
					$.alert(data.message);
				}
			});
		};
		$scope.reset = function(){
			$scope.user = angular.copy($scope.user_bak);
			$scope.captcha = undefined;
		};
	});
	celloudApp.controller("updatePassword",function($scope,userService){
	  $scope.reset = function() {
        $scope.oldPwd = "";
        $scope.newPwd = "";
        $scope.confirmPwd = "";
    };
    $scope.updatePassword = function(){
    	$scope.state = false;
    	userService.updatePassword($scope.oldPwd,$scope.newPwd).success(function(data){
    		$scope.code = data.code;
    		if($scope.code == 5){
    			alert("测试账号不允许修改信息");
    		}if($scope.code == 102){
    			$.alert("修改成功");
    		}else{
    			$scope.pwdMessage = data.message;
    		}
    	}).error(function(data) {
    		$.alert("修改密码失败");
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
				if(data == -1){
					alert("测试账号不允许修改邮箱");
				}else if(data==1){
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
					prevent_duplicates : false
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