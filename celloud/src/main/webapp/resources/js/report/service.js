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
      self.changeProjectName = function(projectId,projectName){
        return $http.get("project/update",{params:{projectId:projectId,projectName:projectName}});
      }
      self.cancelProjectShare = function(projectId){
        return $http.get("project/deleteShare",{params:{projectId:projectId}});
      }
      self.projectShare = function(projectId,userNames){
        return $http.get("project/shareProject",{params:{projectId:projectId,userNames:userNames}});
      }
      //检索已共享人
      self.getShareTo = function(projectId){
        return $http.get("project/getShareTo",{params:{projectId:projectId}});
      }
    });
  
  celloudApp.service("dataReportService", function($resource,$http){
    var self = this;
    self.getReports = function(){
      return $resource("report/dataReportPages").get();
    }
    self.getReportsByParams = function(currentPage,pageSize,condition,beginDate,endDate,batch,tagId,period,sort){
      return $http.get("report/dataReportPages",{params:{page:currentPage,size:pageSize,condition:condition,beginDate:beginDate,endDate:endDate,tagId:tagId,batch:batch,period:period,sort:sort}});
    }
    self.getSearchInfos = function(){
      return $resource("report/reportSearchInfo").get();
    }
    self.getDataReportInfo = function(url, dataKey, projectId, appId){
    	return $http({method:"POST",url:url,data:$.param({dataKey:dataKey,projectId:projectId,appId:appId}),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
    }
  });
    
}());
