(function(){
  celloudApp.controller("dataListController", function($scope, $rootScope, $location, $routeParams, runService){
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
      $.dataManager.options.condition = $scope.dataCondition;
      $.dataManager.options.pageSize = pageSize;
      runService.conditionList().success(function(response){
        $scope.dataList = response;
        $location.path("/data");
      });
    }
    //项目运行
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
    //数据删除
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
    };
    //跳转数据编辑
    $scope.toEditData = function(fileId){
      runService.toEditData(fileId).success(function(response){
        $scope.dataFile = response['file'];
        $scope.appList = response['appList'];
      });
    }
    //修改数据信息
    $scope.submitEditData = function(){
      $scope.dataFile.tagName = $("input[type='radio'][name='dataTagName']:checked").val();
      runService.submitEditData($scope.dataFile).success(function(response){
        if(response==1){
          alert("success");
          $scope.dataList = runService.list();
          $.dataManager.refreshDataList();
          $("#data-detail-modal").modal("hide");
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