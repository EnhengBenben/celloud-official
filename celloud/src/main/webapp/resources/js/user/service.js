(function(){
	celloudApp.service("userService",function($q,$http,$resource){
		this.getUserInfo = function(){
			return $http({method:"POST",url:'user/info',params:{}});
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
		this.getUserCompanyIcon = function(){
			return $http({method:"POST",url:'user/getCompanyIcon',data:{},headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
		this.setUserCompanyIcon = function(companyIcon){
			return $http({method:"POST",url:'user/setCompanyIcon',data:$.param({companyIcon:companyIcon}),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
		this.sendCaptcha = function(cellphone){
			return $http({method:"POST",url:'user/sendCaptcha',data:$.param({cellphone:cellphone}),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
		this.authenticationCellphone = function(cellphone, captcha){
			return $http({method:"POST",url:'user/authenticationCellphone',data:$.param({cellphone:cellphone,captcha:captcha}),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
	});
})();