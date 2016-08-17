(function(){
	expenseApp.service("expenseService",function($http){
		this.pageQueryConsume = function(currentPage,pageSize){
			pageSize = pageSize||10;
			return $http({method:"POST",url:'expense/toRunExpenseList',params:{"currentPage":currentPage,"pageSize":pageSize}});
		}
	});
})();