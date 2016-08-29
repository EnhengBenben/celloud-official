(function(){
	celloudApp.controller("pageQueryConsume",function($scope,$rootScope,$routeParams,$location,expenseService){
		$scope.pageQueryConsume = function(currentPage,pageSize){
			expenseService.pageQueryConsume(currentPage,pageSize).
			success(function(dataList){
				$scope.dataList = dataList;
			});
		}
		$scope.pageList = function(pageSize){
		$rootScope.pageSize = pageSize;
		$scope.pageQueryConsume(1,pageSize);
		$location.path($scope.pageType);
    }
    if($routeParams.page == null){
      $scope.pageQueryConsume(1,$rootScope.pageSize);
      $location.path($scope.pageType);
    }else{
      $scope.pageQueryConsume($routeParams.page,$rootScope.pageSize);
    }
	});
	celloudApp.controller("pageQueryRecharge",function($scope,$rootScope,$routeParams,$location,expenseService){
		$scope.pageQueryRecharge = function(currentPage,pageSize){
			expenseService.pageQueryRecharge(currentPage,pageSize).
			success(function(dataList){
				$scope.dataList = dataList;
			});
		};
		$scope.pageList = function(pageSize){
      $rootScope.pageSize = pageSize;
      $scope.pageQueryRecharge(1,pageSize);
      $location.path($scope.pageType);
    }
    if($routeParams.page == null){
      $scope.pageQueryRecharge(1,$rootScope.pageSize);
      $location.path($scope.pageType);
    }else{
      $scope.pageQueryRecharge($routeParams.page,$rootScope.pageSize);
    }
		$scope.reset = function(){
			$scope.invoiceForm = {};
		};
		$("input[name=checkAll]").click(function(){
	        if($(this).prop("checked")){
	            $("input[name='rechargeIds'][disabled!=disabled][type=checkbox]").prop('checked',true);
	        }else{
	            $("input[name='rechargeIds'][disabled!=disabled][type=checkbox]").prop('checked',false);
	        }
	    });
		$scope.rechargeIds = function(){
			if($("input[name='rechargeIds'][disabled!=disabled][type=checkbox]").length == $("input[name='rechargeIds'][disabled!=disabled][type=checkbox]:checked").length){
				$("#checkAll").prop("checked",true);
			}else{
				$("#checkAll").prop("checked",false);
			}
		}
		$scope.applyInvoice = function(){
			var rechargeIds = $("input[name='rechargeIds']:checked");
			var money = 0;
			if(rechargeIds.length < 1){
				$rootScope.errorInfo = "请选择至少一条记录";
				$("#tips-modal").modal("show");
				return;
			}
			rechargeIds.each(function(){
				money += parseFloat($(this).parent().parent().siblings().eq(0).html());
			});
			if(money < 100 && money != 0.02){
				$rootScope.errorInfo = "最少申请100元";
				$("#tips-modal").modal("show");
				return;
			}
			$("#apply-invoice-modal").modal("show");
			$("#money").attr("value",money);
			var ids = new Array();
			rechargeIds.each(function(){
	            ids.push($(this).val());
	        });
			$("#rechargeIds").val(ids.join(","));
		};
		$scope.apply = function(){
			$("#invoiceSubmit").prop("disabled",true);
			$scope.invoiceForm.invoiceType = $("input[name=invoiceType]:checked").val();
			$scope.invoiceForm.rechargeIds = $("#rechargeIds").val();
			expenseService.apply($scope.invoiceForm).
			success(function(data){
				$("#apply-invoice-modal").modal("hide");
				$scope.state = true;
				if(data > 0){
					$scope.message = "申请成功!";
					$scope.pageQueryRecharge(1,10);
				}else{
					$scope.message = "发生未知错误,请联系管理员!";
				}
			});
			$("#invoiceSubmit").prop("disabled",false);
		};
		$scope.length = 0;
		$scope.invoiceForm = {};
	});
	celloudApp.controller("pageQueryInvoice",function($scope,$rootScope,$routeParams,$location,expenseService){
		$scope.pageQueryInvoice = function(currentPage,pageSize){
			expenseService.pageQueryInvoice(currentPage,pageSize).
			success(function(dataList){
				$scope.dataList = dataList;
			});
		};
		$scope.pageList = function(pageSize){
      $rootScope.pageSize = pageSize;
      $scope.pageQueryInvoice(1,pageSize);
      $location.path($scope.pageType);
    }
    if($routeParams.page == null){
      $scope.pageQueryInvoice(1,$rootScope.pageSize);
      $location.path($scope.pageType);
    }else{
      $scope.pageQueryInvoice($routeParams.page,$rootScope.pageSize);
    }
		$scope.showInvoiceDetail = function(invoiceId){
			expenseService.getInvoiceById(invoiceId).
			success(function(invoiceDetail){
				$scope.invoiceDetail = invoiceDetail;
			});
		};
	});
	celloudApp.controller("toRecharge",function($scope,$rootScope,expenseService){
		expenseService.toRecharge().
		success(function(data){
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
			if(money != parseInt(money)){
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
			var payWay = $("input[name=pay-way]:checked").val();
			var action = CONTEXT_PATH + "/pay/recharge/" + payWay;
			$("#rechargeForm").attr("action",action);
			$rootScope.errorInfo = "请在新打开的页面完成支付操作！";
			$("#tips-modal").modal("show");
		}
	});
})();