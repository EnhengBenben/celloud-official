(function() {
	celloudApp.service("uploadService", function($resource, $http) {
		this.getProductTags = function(){
			return $resource("uploadFile/getProductTag?v="+new Date().getTime(),{cache: false});
		}
		this.checkRole = function(){
			return $http({method:"GET", url:'uploadFile/checkRole', headers: {'Content-Type': 'application/x-www-form-urlencoded' }});
		}
	});
})();
