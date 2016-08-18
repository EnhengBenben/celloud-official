(function(){
	expenseApp.controller("pageQueryConsume",function($scope,expenseService){
		$scope.pageQueryConsume = function(currentPage,pageSize){
			expenseService.pageQueryConsume(currentPage,pageSize).
			success(function(dataList){
				$scope.dataList = dataList;
			});
		}
		$scope.pageQueryConsume(1,10);
	});
	expenseApp.controller("pageQueryRecharge",function($scope,expenseService){
		$scope.pageQueryRecharge = function(currentPage,pageSize){
			expenseService.pageQueryRecharge(currentPage,pageSize).
			success(function(dataList){
				$scope.dataList = dataList;
			});
		}
		$scope.pageQueryRecharge(1,10);
	});
	expenseApp.controller("pageQueryInvoice",function($scope,expenseService){
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
})();