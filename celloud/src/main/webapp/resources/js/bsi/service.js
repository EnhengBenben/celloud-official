(function() {
	celloudApp.service("bsiReportService", function($http){
		this.pageQuery = function(params){
			return $http({method:"POST",url:'report/bsi/reportPageQuery',data:$.param(params),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
	});
})();
