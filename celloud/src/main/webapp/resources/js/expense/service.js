(function(){
	celloudApp.service("expenseService",function($http){
		this.pageQueryConsume = function(currentPage,pageSize){
			pageSize = pageSize || 10;
			return $http({method:"POST",url:'expense/toRunExpenseList',params:{"currentPage":currentPage,"pageSize":pageSize}});
		};
		this.pageQueryRecharge = function(currentPage,pageSize){
			pageSize = pageSize || 10;
			return $http({method:"POST",url:'pay/recharge/list',params:{"currentPage":currentPage,"pageSize":pageSize}});
		};
		this.pageQueryInvoice = function(currentPage,pageSize){
			pageSize = pageSize || 10;
			return $http({method:"POST",url:'invoice/list',params:{"currentPage":currentPage,"pageSize":pageSize}});
		};
		this.getInvoiceById = function(invoiceId){
			return $http({method:"POST",url:"invoice/detail",params:{"id":invoiceId}});
		}
		this.apply = function(params){
			return $http({method:"POST",url:"invoice/apply",data:$.param(params),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
	});
})();