(function(){
  celloudApp.controller("dataListController", function($scope,$routeParams, runService){
    if($routeParams.page == null){
      $scope.dataList = runService.list();
    }else{
      $scope.dataList = runService.pageList($routeParams.page);
    }
    $scope.dataCondition = $.dataManager.options.condition;
    $scope.conditionList = function(){
      $.dataManager.options.condition = $scope.dataCondition;
      runService.conditionList().success(function(response){
        $scope.dataList = response;
      });
    }
    $scope.pageList = function(pageSize){
      $.dataManager.options.pageSize = pageSize;
      $.dataManager.options.condition = $scope.dataCondition;
      runService.conditionList().success(function(response){
        $scope.dataList = response;
      });
    }
    $scope.runWithProject = function() {
      runService.run().success(function(response) {
        if(response.length==0){
          alert("run success");
          $scope.dataList = runService.list();
          $.dataManager.refreshDataList();
        }else{
          alert("run error");
        }
      });
    };
    $scope.deleteData = function(){
      runService.delete().success(function(response){
        if(response.code == "104"){
          alert("success");
          $scope.dataList = runService.list();
          $.dataManager.refreshDataList();
        }else{
          alert("失败");
        }
      });
    }
  });
//  celloudApp.controller("dataPageController", function($scope,$routeParams, runService){
//    $scope.dataList = runService.pageList($routeParams.page);
//    alert($scope.dataList);
//  });
})();