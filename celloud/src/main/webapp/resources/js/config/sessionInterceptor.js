(function() {
	window.sessionTimeoutFlag = false;
	window.celloudApp.factory('sessionTimeout', ["$q",function($q) {
	    return {
	        response: function(response) {
	        	var sessionstatus = response.headers()['sessionstatus'];
	            if(sessionstatus=="timeout" && !window.sessionTimeoutFlag){
	            	window.sessionTimeoutFlag=true;
	            	alert("登录超时，请重新登录！");
	            	window.location.href="logout";
	            }
	            return response;
	        }
	    };
	}]);
	window.celloudApp.config(['$httpProvider', function($httpProvider) {
	    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	    if (!$httpProvider.defaults.headers.get) {
	    	$httpProvider.defaults.headers.get = {};
	    }
	    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
	    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
	    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
	    
	    $httpProvider.interceptors.push('sessionTimeout');
	}]);
}());