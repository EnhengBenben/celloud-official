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
		$scope.sendEmail = function(){
			companyService.sendRegisterEmail($scope.email,$scope.kaptcha).
			success(function(data, status){
				 if(status == 204){
					$("#company-addUser-modal").modal("hide");
					$.alert("发送成功!");
				} 
			}).
			error(function(data, status){
				if(status == 400){
					$scope.emailError = data.emailError;
					$scope.kaptchaError = data.kaptchaError;
					$('#kaptchaImage').click();
				}else if(status == 500){
					
				}
			});
		}
		$scope.clearState = function(){
			$scope.emailError = null;
			$scope.kaptchaError = null;
		}
		$scope.showAddUserForm = function(){
			$scope.userAddForm.$setPristine();
			$scope.userAddForm.$setPristine();
			$scope.email = '';
			$scope.kaptcha = '';
			$scope.clearState();
		}
		$('#kaptchaImage').click(function() {
			$(this).hide().attr('src','kaptcha?' + Math.floor(Math.random() * 100)).fadeIn();
		});
		$scope.pageQuery($scope.pageInfo.currnetPage, $scope.pageInfo.pageSize);
	});
	celloudApp.controller("companyBaseController", function($scope, companyService){
		companyService.getCompanyInfo().
		success(function(data, status){
			if(status == 200){
				$scope.company = data;
				$scope.company_bak = angular.copy(data);
				$scope.province = data.province;
				_init_area();
			}
		}).
		error(function(data, status){
			
		})
		$scope.reset = function(){
			$scope.company = angular.copy($scope.company_bak);
		}
	});
})();
