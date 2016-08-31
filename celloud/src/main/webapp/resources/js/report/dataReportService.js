(function(){
  celloudApp.service("mibReportService",function($http){
    var self = this;
    self.reportInfo = function(dataKey,projectId,appId){
      return $http.get("report/getMIBReportInfo",{params: {dataKey:dataKey,projectId:projectId,appId:appId}});
    }
  });
})()