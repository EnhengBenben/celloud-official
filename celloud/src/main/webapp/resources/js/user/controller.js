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
		
	});
})();