(function(){
  celloudApp.controller("dataListController", function($scope, runService){
    $scope.dataList = runService.list();
    $scope.conditionList = function(){
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
  celloudApp.controller("dataPageController", function($scope, dataByPageService){
    $scope.dataList = dataByPageService.get({page:2});
  });
})();