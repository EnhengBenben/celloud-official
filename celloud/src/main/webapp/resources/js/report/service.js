(function(){
  celloudApp.service("projectReportService",function($resource,$http){
      var self = this;
      self.getRanAPP = function(){
        return $resource("app/getRanAPP").get();
      }
      self.getReportList = function(){
        return $resource("report/getReportPageList").get();
      }
      self.getReportListCondition = function(currentPage,pageSize,belongs,start,end,appId,condition){
        return $http.get("report/getReportPageList",{params: {page:currentPage,size:pageSize,condition:condition,start:start,end:end,appId:appId,belongs:belongs}});
      }
    });
    
}());
