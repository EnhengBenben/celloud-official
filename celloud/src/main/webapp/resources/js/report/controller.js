(function(){
  celloudApp.controller("projectReportController", function($scope, projectReportService){
    //加载产品标签
    $scope.ranAppList = projectReportService.getRanAPP();
    //加载报告列表
    $scope.reportList = projectReportService.getReportList();
    //显示项目名称编辑框
    $scope.toChangePname = function(projectId){
      alert(projectId);
    }
    //修改项目名称
    $scope.changePname = function(projectId){
      alert(projectId);
    }
    //取消共享来的项目
    $scope.cancelProjectShare = function(projectId,userId){
      alert(projectId);
    }
    //下载PDF
    $scope.downPDF = function(userId,appId,projectId){
      alert(projectId);
    }
    //打开共享窗口
    $scope.toShareModal = function(projectId,projectName,dataNum){
      alert(projectId);
    }
    //打开已共享窗口
    $scope.shareModal = function(projectId,userId,projectName,dataNum){
      alert(projectId);
    }
    //删除
    $scope.removePro = function(projectId){
      alert(projectId);
    }
    
  });
})();
