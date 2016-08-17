(function(){
	expenseApp.controller("pageQueryConsume",function($scope,expenseService){
		$scope.pageQueryLog = function(currentPage,pageSize){
			userService.pageQueryLog(currentPage,pageSize).
			success(function(dataList){
				$scope.dataList = dataList;
			});
		}
		$scope.pageQueryLog(1,10);
	});
})();