(function(){
	userApp.controller("userController",function($scope,userService){
		$scope.user = userService.getUserInfo();
	});
})();