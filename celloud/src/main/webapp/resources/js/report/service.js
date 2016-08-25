(function(){
  celloudApp.service("projectReportService",function($resource,$http){
      var self = this;
//      self.options = {
//          belongs = 1,  //报告所属
//          start = null,   //检索开始时间
//          end = null, //检索结束时间
//          app = 0,    //appid
//          fileName = null,    //文件名称模糊检索条件
//          currentPage = 1,  //当前页
//          pageSize = 10  //每页显示记录数
//      }
      self.getRanAPP = function(){
        return $resource("app/getRanAPP").get();
      }
      self.getReportList = function(){
        return $resource("report/getReportPageList").get();
      }
      self.getReportListCondition = function(currentPage,pageSize){
        return $http.get("report/getReportPageList",{params: {page:currentPage,size:pageSize}});
      }
    });
    
}());
