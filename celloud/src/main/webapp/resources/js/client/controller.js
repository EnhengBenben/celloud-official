(function(){
	celloudApp.controller("clientBaseInfo",function($scope,$location,clientBaseService){
	  clientBaseService.getUserInfo().
		success(function(data){
			$scope.user = data;
			$scope.province_bak = data.province;
			$scope.city_bak = data.city;
			$scope.district_bak = data.district;
			_init_area($scope.province_bak,$scope.city_bak, $scope.district_bak);
		});
		$scope.updateUserInfo = function(){
			$scope.user.province = $("#s_province").val();
			$scope.user.city = $("#s_city").val();
			$scope.user.district = $("#s_county").val();
			$scope.user.sex = $("input[name='sex']:checked").val();
			
			clientBaseService.updateUserInfo($scope.user)
			.success(function(data){
		        $.alert(data.message);
		    })
		    .error(function(data){
		        $.alert(data.message);
		    });
		};
		$scope.updateToPay = function(){
			$scope.user.province = $("#s_province").val();
			$scope.user.city = $("#s_city").val();
			$scope.user.district = $("#s_county").val();
			$scope.user.sex = $("input[name='sex']:checked").val()
		  clientBaseService.updateUserInfo($scope.user).success(function(data){
        $.alert(data.message);
        $location.path("/pay");
      }).error(function(data){
        $.alert(data.message);
      });
    };
	});
	celloudApp.controller("toRecharge",function($scope,$location,expenseService){
    expenseService.toRecharge().success(function(data){
      $scope.balance = data;
    });
    $scope.sumbitRecharge = function(){
      var payWay = $("input[name=pay-way]:checked").val() == "alipay"?"alipay":"jdpay";
      var action = CONTEXT_PATH + "/pay/recharge/" + payWay;
      $("#rechargeForm").attr("action",action);
      $.tips("请在新打开的页面完成支付操作！");
    }
  });
})();