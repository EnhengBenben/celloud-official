(function(){
	celloudApp.service("expenseService",function($http){
		this.pageQueryConsume = function(currentPage,pageSize){
			pageSize = pageSize || 10;
			return $http({method:"POST",url:'expense/toRunExpenseList',data:$.param({"currentPage":currentPage,"pageSize":pageSize}),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		};
		this.pageQueryRecharge = function(currentPage,pageSize){
			pageSize = pageSize || 10;
			return $http({method:"POST",url:'pay/recharge/list',data:$.param({"currentPage":currentPage,"pageSize":pageSize}),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		};
		this.pageQueryInvoice = function(currentPage,pageSize){
			pageSize = pageSize || 10;
			return $http({method:"POST",url:'invoice/list',data:$.param({"currentPage":currentPage,"pageSize":pageSize}),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		};
		this.getInvoiceById = function(invoiceId){
			return $http({method:"POST",url:"invoice/detail",data:$.param({"id":invoiceId}),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
		this.apply = function(params){
			return $http({method:"POST",url:"invoice/apply",data:$.param(params),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
	});
})();