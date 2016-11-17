(function() {
	celloudApp.service("bsiService", function($http){
		this.reportPageQuery = function(params){
			return $http({method:"POST",url:'report/bsi/reportPageQuery',data:$.param(params),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
		this.reportReRun = function(dataKey,appId,projectId){
			return $http({method:"POST",url:'data/reRun',data:$.param({"dataKey":dataKey,"appId":appId,"projectId":projectId}),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
		this.getBSIPatientReport = function(params){
			return $http({method:"POST",url:'report/getBSIPatientReportInfo',data:$.param(params),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
		this.dataPageQuery = function(params){
			return $http({method:"POST",url:'data/bsi/dataPageQuery',data:$.param(params),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
		this.getPrevOrNextBSIReport = function(params){
			return $http({method:"POST",url:'report/getPrevOrNextBSIReportInfo',data:$.param(params),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
		this.getBatchPageList = function(params){
			return $http({method:"POST",url:'report/bsi/batchReportListInfo',data:$.param(params),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
		}
	});
})();
