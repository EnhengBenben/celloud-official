(function() {
	celloudApp.service("uploadService", function($resource) {
		this.getProductTags = function(){
			return $resource("uploadFile/getProductTag");
		}
	});
	
	celloudApp.service("rockyReportService", function($http){
		this.pageQuery = function(params){
			return $http({method:"POST",url:'report/rocky/reportMain',data:$.param({"batches":params.batches,"periods":params.periods,"page":params.page,"size":params.size}),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
	});
})();
