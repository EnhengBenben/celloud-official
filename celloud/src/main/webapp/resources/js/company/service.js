(function(){
	celloudApp.service("companyService",function($resource,$http){
		this.pageQueryUser = function(currentPage,pageSize){
			pageSize = pageSize || 10;
			return $http({method:"GET",url:'company/userInfo',params:{"currentPage":currentPage,"pageSize":pageSize}});
		};
		this.updateUserState = function(userId, state){
			return $http({method:"POST",url:'company/updateUserState',params:{"userId":userId,"state":state}});
		};
	});
}());
