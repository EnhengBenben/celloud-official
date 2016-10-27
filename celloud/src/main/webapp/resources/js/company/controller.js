(function(){
	celloudApp.controller("companyUserController", function($scope, companyService){
		$scope.pageQuery = function(currentPage, pageSize){
			companyService.pageQueryUser(currentPage, pageSize).
			success(function(userList){
				$scope.userList = userList;
			});
		}
		$scope.pageQuery(1, 10);
	});
})();
