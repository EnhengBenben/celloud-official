(function(){
	celloudApp.service("companyService",function($resource,$http){
		this.pageQueryUser = function(currentPage,pageSize){
			pageSize = pageSize || 10;
			return $http({method:"GET",url:'company/userInfo',params:{"currentPage":currentPage,"pageSize":pageSize}});
		};
		this.updateUserState = function(userId, state){
			return $http({method:"POST",url:'company/updateUserState',params:{"userId":userId,"state":state}});
		};
		this.sendRegisterEmail = function(email, kaptcha,apps,roles){
			return $http({method:"POST", url:"user/sendRegistEmail", params:{"email":email, "kaptcha":kaptcha,"apps":apps,"roles":roles}});
		};
		this.getCompanyInfo = function(){
			return $http({method:"GET", url:"company"});
		};
		this.updateCompanyInfo = function(company){
			return $http({method:"POST", url:"company", data:$.param(company), headers: {'Content-Type': 'application/x-www-form-urlencoded' }});
		}
		this.getAppList = function(){
		  return $http({method:"GET", url:"app/getRightAppList"});
		}
		this.getRoleList = function(){
		  return $http({method:"GET", url:"user/findRoles"});
		}
		
		this.toAddApp = function(userId){
      return $http({method:"GET", url:"app/toAddApp", params:{"userId":userId}});
    }
		this.addApp = function(userId,apps){
      return $http({method:"POST", url:"app/grantApp", params:{"userId":userId,"apps":apps}});
    };
    this.toRemoveApp = function(userId){
      return $http({method:"GET", url:"app/toRemoveApp", params:{"userId":userId}});
    }
    this.removeApp = function(userId,apps){
      return $http({method:"POST", url:"app/deleteApp", params:{"userId":userId,"apps":apps}});
    };
    this.toAddRole = function(userId){
      return $http({method:"GET", url:"user/toAddRole", params:{"userId":userId}});
    }
    this.addRole = function(userId,roles){
      return $http({method:"POST", url:"user/addRole", params:{"userId":userId,"roles":roles}});
    };
    this.toRemoveRole = function(userId){
      return $http({method:"GET", url:"user/toRemoveRole", params:{"userId":userId}});
    }
    this.removeRole = function(userId,roles){
      return $http({method:"POST", url:"user/removeRole", params:{"userId":userId,"roles":roles}});
    };
	});
}());
