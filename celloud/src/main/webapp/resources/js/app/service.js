(function(){
	celloudApp.service("appService",function($http){
		this.toAppStore = function(){
			return $http({method:"POST",url:"app/toAppStore",headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
		this.getMyApp = function(){
			return $http({method:"POST",url:"app/myApps"});
		}
		this.toSclassifyApp = function(pid,pname){
			return $http({method:"POST",url:"app/toSclassifyApp",data:$.param({"paramId":pid}),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		};
		this.getAppDetail = function(pid){
			return $http({method:"POST",url:"app/appDetail",data:$.param({"paramId":pid}),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
	});
})();