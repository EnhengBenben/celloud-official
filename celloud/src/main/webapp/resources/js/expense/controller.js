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
})();