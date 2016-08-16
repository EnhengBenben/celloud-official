(function(){
	
	userApp.service("userService",function($http,$resource){
		this.getUserInfo = function(){
			var user = $resource("user/info").get();
			return user;
		}
	});
})();