(function(){
	celloudApp.service("clientBaseService",function($q,$http,$resource){
		this.getUserInfo = function(){
			return $http({method:"POST",url:'user/info',params:{}});
		}
		this.updateUserInfo = function(user){
			// 假同步一
			return $http({method:"POST",url:'user/updateInfo',params:{"cellphone":user.cellphone,"zipCode":user.zipCode,"address":user.address,"truename":user.truename,"age":user.age,"sex":user.sex}});
		}
	});
})();