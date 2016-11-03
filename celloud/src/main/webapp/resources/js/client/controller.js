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
    $scope.tab = 'pay_tab_alipay';
    $scope.pay_type = 'online';
    $scope.rechargeForm = {
      money : 10
    };
    $scope.checkMoney = function(){
      var money = $scope.rechargeForm.money;
      if(!$.isNumeric(money)){
        $scope.moneyError = "请正确输入充值金额！";
        $scope.checkFlag = true;
        $scope.checkSubmit = true;
        return false;
      }
      if(money*1 < 10 && money*1 != 0.01){
        $scope.moneyError = "充值金额要大于10元哦！";
        $scope.checkFlag = true;
        $scope.checkSubmit = true;
        return false;
      }
      if(money*1 >10000){
        $scope.moneyError = "大于10000元的充值金额，请使用公司转账方式充值！";
        $scope.checkFlag = true;
        $scope.checkSubmit = true;
        return false;
      }
      if(money != parseInt(money) && money != 0.01){
        $scope.moneyError = "请输入整数金额！";
        $scope.checkFlag = true;
        $scope.checkSubmit = true;
        return false;
      }
      if($.isNumeric(money)){//测试用的
        $scope.checkFlag = false;
        $scope.checkSubmit = false;
        return true;
      }
    }
    $scope.sumbitRecharge = function(){
      var payWay = $("#alipay").checked?"alipay":"transfer";
      var action = CONTEXT_PATH + "/pay/recharge/" + payWay;
      alert(action);
      $("#rechargeForm").attr("action",action);
      $.tips("请在新打开的页面完成支付操作！");
    }
  });
})();