(function() {
	celloudApp.service("rockyReportService", function($http){
		this.pageQuery = function(params){
			return $http({method:"POST",url:'report/rocky/reportMain',data:$.param({"batches":params.batches,"periods":params.periods,"page":params.page,"size":params.size,"beginDate":params.beginDate,"endDate":params.endDate,"sample":params.sample,"sidx":params.sidx,"sord":params.sord}),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
	});
	celloudApp.service("rockyDataService", function($http){
		this.pageQuery = function(params){
			return $http({method:"POST",url:'data/rocky/list',data:$.param(params),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
	});
})();
