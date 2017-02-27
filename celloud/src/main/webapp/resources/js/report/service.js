(function(){
  celloudApp.service("projectReportService",function($resource,$http){
      var self = this;
      //检索有报告的APP
      self.getRanAPP = function(){
        return $resource("app/getRanAPP").get();
      }
      //初始化报告列表
      self.getReportList = function(){
        return $resource("report/getReportPageList").get();
      }
      //条件检索报告
      self.getReportListCondition = function(currentPage,pageSize,belongs,start,end,appId,condition){
        return $http.get("report/getReportPageList?random="+new Date().getTime(),{params: {page:currentPage,size:pageSize,condition:condition,start:start,end:end,appId:appId,belongs:belongs}});
      }
      //修改项目名称
      self.changeProjectName = function(projectId,projectName){
        return $http.get("project/update",{params:{projectId:projectId,projectName:projectName}});
      }
      //删除项目
      self.deleteProject = function(projectId){
        return $http.get("project/deleteByState",{params:{projectId:projectId}});
      }
      //取消共享来的项目
      self.cancelProjectShare = function(projectId){
        return $http.get("project/deleteShare",{params:{projectId:projectId}});
      }
      //项目共享
      self.projectShare = function(projectId,userNames){
        return $http.get("project/shareProject",{params:{projectId:projectId,userNames:userNames}});
      }
      //检索项目的已共享人
      self.getShareTo = function(projectId){
        return $http.get("project/getShareTo",{params:{projectId:projectId}});
      }
      //下载PDF
      self.downPDF = function(path){
        return $http.get("report/down",{params:{path:path}});
      }
    });
  
  celloudApp.service("dataReportService", function($resource,$http){
    var self = this;
    self.getReports = function(){
      return $resource("report/dataReportPages?v="+new Date().getTime()).get();
    }
    self.getReportsByParams = function(currentPage,pageSize,condition,beginDate,endDate,batch,tagId,period,sort){
      return $http.get("report/dataReportPages?v="+new Date().getTime(),{params:{page:currentPage,size:pageSize,condition:condition,beginDate:beginDate,endDate:endDate,tagId:tagId,batch:batch,period:period,sort:sort}});
    }
    self.getSearchInfos = function(){
      return $resource("report/reportSearchInfo?v="+new Date().getTime()).get();
    }
    self.getDataReportInfo = function(url, dataKey, projectId, appId){
    	return $http({method:"POST",url:url,data:$.param({dataKey:dataKey,projectId:projectId,appId:appId}),headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
    }
    self.dataInBatch = function(params){
    	return $http({url:"report/reportInBatch", method:"POST", data:$.param(params), headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
    }
  });
    
}());
