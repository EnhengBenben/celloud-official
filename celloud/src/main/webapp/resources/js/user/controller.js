(function(){
	celloudApp.controller("updateBaseInfo",function($scope,userService){
		$scope.user = userService.getUserInfo();
		$scope.updateUserInfo = function(){
			if(checkForm){
				var promise = userService.updateUserInfo($scope.user).
				success(function(data){
					$scope.message = data.message;
				}).
				error(function(data){
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
	celloudApp.controller("updatePassword",function($scope,userService){
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
	celloudApp.controller("pageQueryLog",function($scope,userService){
		$scope.pageQueryLog = function(currentPage,pageSize){
			userService.pageQueryLog(currentPage,pageSize).
			success(function(dataList){
				$scope.dataList = dataList;
			});
		}
		$scope.pageQueryLog(1,10);
	});
	celloudApp.controller("updateEmail",function($scope,userService){
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