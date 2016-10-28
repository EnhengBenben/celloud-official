(function(){
	celloudApp.controller("companyUserController", function($scope, companyService){
		
		$scope.pageInfo = {"currentPage" : 1, "pageSize" : 20};
		
		$scope.pageQuery = function(currentPage, pageSize){
			$scope.pageInfo.currentPage = currentPage || $scope.pageInfo.currentPage;
			$scope.pageInfo.pageSize = pageSize || $scope.pageInfo.pageSize;
			companyService.pageQueryUser($scope.pageInfo.currentPage, $scope.pageInfo.pageSize).
			success(function(userList){
				$scope.userList = userList;
			});
		}
		$scope.updateUserState = function(userId, state){
			companyService.updateUserState(userId, state).
			success(function(data, status){
				if(status == 204){
					if(state == 0){
						$.alert("禁用成功");
					}else{
						$.alert("启用成功");
					}
					$scope.pageQuery($scope.pageInfo.currnetPage, $scope.pageInfo.pageSize);
				}
				if(status == 500){
					if(state == 0){
						$.alert("禁用失败");
					}else{
						$.alert("启用失败");
					}
				}
			})
		}
		$('#kaptchaImage').click(function() {
			$(this).hide().attr('src','kaptcha.jpg?' + Math.floor(Math.random() * 100)).fadeIn();
		});
		$scope.pageQuery($scope.pageInfo.currnetPage, $scope.pageInfo.pageSize);
	});
})();
