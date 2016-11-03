(function(){
	celloudApp.controller("clientBaseInfo",function($scope,$location,clientBaseService){
	  clientBaseService.getUserInfo().
		success(function(data){
			$scope.user = data;
		});
		$scope.updateUserInfo = function(){
		  clientBaseService.updateUserInfo($scope.user).success(function(data){
        $.alert(data.message);
      }).error(function(data){
        $.alert(data.message);
      });
		};
		$scope.updateToPay = function(){
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
      var payWay = $("#alipay").checked?"alipay":"transfer";
      var action = CONTEXT_PATH + "/pay/recharge/" + payWay;
      $("#rechargeForm").attr("action",action);
      $.tips("请在新打开的页面完成支付操作！");
    }
  });
	celloudApp.controller("report",function($scope,$location,expenseService){
    expenseService.toRecharge().success(function(data){
      $scope.balance = data;
    });
    $scope.sumbitRecharge = function(){
      var payWay = $("#alipay").checked?"alipay":"transfer";
      var action = CONTEXT_PATH + "/pay/recharge/" + payWay;
      $("#rechargeForm").attr("action",action);
      $.tips("请在新打开的页面完成支付操作！");
    }
  });
})();