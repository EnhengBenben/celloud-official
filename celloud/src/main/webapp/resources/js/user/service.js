(function(){
	celloudApp.service("userService",function($q,$http,$resource){
		this.getUserInfo = function(){
			return $http({method:"POST",url:'user/info',params:{}});
		}
		this.updateUserInfo = function(user){
			// 假同步一
			return $http({method:"POST",url:'user/updateInfo',params:{"cellphone":user.cellphone}});
		}
		this.updatePassword = function(oldPassword,newPassword){
			// 假同步二
			return $http({method:"POST",url:'user/updatePassword',params:{"oldPassword":oldPassword,"newPassword":newPassword}});
		}
		this.updateEmail = function(email){
			return $http({method:"POST",url:'user/sendOldEmail',params:{"email":email}});
		}
		this.pageQueryLog = function(currentPage,pageSize){
			pageSize = pageSize||10;
			return $http({method:"POST",url:'user/logInfo',params:{"currentPage":currentPage,"pageSize":pageSize}});
		}
	});
})();