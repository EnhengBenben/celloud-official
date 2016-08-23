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
				$scope.message = data.message;
			}).
			error(function(data){
				$scope.message = data.message;
			});
			$scope.state = true;
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
})();