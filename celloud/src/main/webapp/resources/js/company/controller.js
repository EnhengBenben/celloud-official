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
				// 用于reset
				$scope.province_bak = data.province;
				$scope.city_bak = data.city;
				$scope.district_bak = data.district;
				$scope.companyIcon_bak = data.companyIcon;
				
				$("#view").css("background-color","rgb(102, 102, 102)");
				$("#view").css("background-repeat","no-repeat");
				$("#view").css("background-position","center center");
				$("#view").css("background-size","contain");
				$("#view").css("background-image","url(" + $scope.company.companyIcon + ")");
				
				
				_init_area($scope.province_bak, $scope.city_bak, $scope.district_bak);
				
				var clipArea = new bjj.PhotoClip("#clipArea", {
			        size: [120, 50],
			        outputSize: [120, 50],
			        file: "#file",
			        view: "#view",
			        ok: "#clipBtn",
			        loadStart: function() {
			            console.log("照片读取中");
			        },
			        loadComplete: function() {
			            console.log("照片读取完成");
			        },
			        clipFinish: function(dataURL) {
			        	$scope.company.companyIcon = dataURL;
			        }
			    });
				
			}
		}).
		error(function(data, status){
			
		})
		$scope.reset = function(){
			$scope.company = angular.copy($scope.company_bak);
			$("#view").css("background-image","url(" + $scope.company.companyIcon + ")");
			_init_area($scope.province_bak, $scope.city_bak, $scope.district_bak);
		}
		$scope.updateCompanyInfo = function(){
			$scope.company.province = $("#s_province").val();
			$scope.company.city = $("#s_city").val();
			$scope.company.district = $("#s_county").val();
			companyService.updateCompanyInfo($scope.company).
			success(function(data, status){
				if(204 == status){
					$.alert("修改成功");
				}
			}).
			error(function(data, status){
				if(400 == status){
					$.alert("您填写的数据有误, 请核查后再提交");
				}
				if(500 == status){
					$.alert("提交失败, 请联系管理员");
				}
			})
		}
	});
})();
