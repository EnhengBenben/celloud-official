(function(){
  celloudApp.controller("dataListController", function($scope, runService){
    //初始化数据列表
    $scope.dataList = runService.list();
    //条件检索
    $scope.conditionList = function(){
      $.dataManager.options.condition = $scope.dataCondition;
      runService.conditionList().success(function(response){
        $scope.dataList = response;
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
  celloudApp.controller("dataPageController", function($scope, dataByPageService){
    $scope.dataList = dataByPageService.get({page:2});
  });
})();