(function() {
	celloudApp.service("uploadService", function($resource) {
		this.getProductTags = function(){
			return $resource("uploadFile/getProductTag");
		}
	});
	
	celloudApp.service("rockyReportService", function($http){
		this.pageQuery = function(params){
			return $http({method:"POST",url:'report/rocky/reportMain',params:$.param(params)});
		}
	});
})();
