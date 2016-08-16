(function(){
	userApp.controller("updateBaseInfo",function($scope,userService){
		$scope.user = userService.getUserInfo();
		$scope.updateUserInfo = function(){
			if(checkForm){
				var promise = userService.updateUserInfo($scope.user);
				promise.then(function(data) {  // 调用承诺API获取数据 .resolve
					$scope.message = data.message;
			    }, function(data) {  // 处理错误 .reject  
			    	$scope.message = data.message;
			    }); 
				$scope.state = true;
			}
		};
		var checkForm = function(){
			var flag = $scope.userForm.cellphone.$dirty && $scope.userForm.cellphone.$valid;
			return flag;
		};
	});
	userApp.controller("updatePassword",function($scope,userService){
		$scope.reset = function() {
	        $scope.oldPwd = "";
	        $scope.newPwd = "";
	        $scope.confirmPwd = "";
	    };
	    $scope.updatePassword = function(){
	    	userService.updatePassword($scope.oldPwd,$scope.newPwd).
	    	success(function(data){
	    		$scope.code = data.code;
	    		$scope.message = data.message;
	    	}).error(function(data) {

			});
		}
	});
	userApp.controller("updateEmail",function($scope,userService){
		$scope.user = userService.getUserInfo();
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
})();