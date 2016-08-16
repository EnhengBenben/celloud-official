(function(){
	
	userApp.service("userService",function($q,$http,$resource){
		this.getUserInfo = function(){
			var user = $resource("user/info").get();
			return user;
		}
		this.updateUserInfo = function(user){
			var deferred = $q.defer();// 假同步
			$http({method:"POST",url:'user/updateInfo',params:{"cellphone":user.cellphone}}).
			success(function(data){
				deferred.resolve(data);
			}).
			error(function(data){
				deferred.reject(data);
			});
			return deferred.promise;
		}
	});
})();