(function(){
	celloudApp.controller("pageQueryConsume",function($scope,expenseService){
		$scope.pageQueryConsume = function(currentPage,pageSize){
			expenseService.pageQueryConsume(currentPage,pageSize).
			success(function(dataList){
				$scope.dataList = dataList;
			});
		}
		$scope.pageQueryConsume(1,10);
	});
	celloudApp.controller("pageQueryRecharge",function($scope,$rootScope,expenseService){
		$scope.pageQueryRecharge = function(currentPage,pageSize){
			expenseService.pageQueryRecharge(currentPage,pageSize).
			success(function(dataList){
				$scope.dataList = dataList;
			});
		};
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
		$("[name=invoiceType]").click(function(){
			$("[name=invoiceType]").prop("checked",false);
			$(this).prop("checked",true);
		});
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
		$scope.pageQueryRecharge(1,10);
		$scope.invoiceForm = {};
	});
	celloudApp.controller("pageQueryInvoice",function($scope,expenseService){
		$scope.pageQueryInvoice = function(currentPage,pageSize){
			expenseService.pageQueryInvoice(currentPage,pageSize).
			success(function(dataList){
				$scope.dataList = dataList;
			});
		};
		$scope.showInvoiceDetail = function(invoiceId){
			expenseService.getInvoiceById(invoiceId).
			success(function(invoiceDetail){
				$scope.invoiceDetail = invoiceDetail;
			});
		};
		$scope.pageQueryInvoice(1,10);
	});
	celloudApp.controller("toRecharge",function($scope,expenseService){
		expenseService.toRecharge().
		success(function(data){
			$scope.balance = data;
		});
		$scope.tab = 'pay_tab_alipay';
	});
})();