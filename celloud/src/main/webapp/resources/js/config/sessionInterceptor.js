(function() {
	window.celloudApp.factory('sessionTimeout', ["$q",function($q) {
	    return {
	        response: function(response) {
	        	var sessionstatus = response.headers()['sessionstatus'];
	            if(sessionstatus=="timeout"){
	            	alert("登录超时，请重新登录！");
	            	window.location.href="logout";
	            }
	            return response;
	        }
	    };
	}]);
	window.celloudApp.config(['$httpProvider', function($httpProvider) {
	    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	    $httpProvider.interceptors.push('sessionTimeout');
	}]);
}());