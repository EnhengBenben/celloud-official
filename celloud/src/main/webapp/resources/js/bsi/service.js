(function() {
	celloudApp.service("bsiService", function($http){
		this.reportPageQuery = function(params){
			return $http({method:"POST",url:'report/bsi/reportPageQuery',data:$.param(params),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
		this.dataPageQuery = function(params){
			return $http({method:"POST",url:'data/bsi/dataPageQuery',data:$.param(params),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
	});
})();
