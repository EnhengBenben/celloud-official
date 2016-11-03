(function(){
	celloudApp.service("clientBaseService",function($q,$http,$resource){
		this.getUserInfo = function(){
			return $http({method:"POST",url:'user/info',params:{}});
		}
		this.updateUserInfo = function(user){
			// 假同步一
			return $http({method:"POST",url:'user/updateInfo',params:{"province":user.province,"city":user.city,"district":user.district,"cellphone":user.cellphone,"zipCode":user.zipCode,"address":user.address,"truename":user.truename,"age":user.age,"sex":$("input[name='sex']:checked").val()}});
		}
	});
})();