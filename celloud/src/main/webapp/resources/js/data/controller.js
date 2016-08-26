(function(){
  celloudApp.controller("dataListController", function($scope, $rootScope, $location, $routeParams, runService){
    //基本检索方法
    $scope.pageQuery = function(page,pageSize){
      $.dataManager.options.condition = $scope.dataCondition;
      $.dataManager.options.page = page;
      $.dataManager.options.pageSize = pageSize;
      runService.pageList().success(function(response){
        $scope.dataList = response;
      });
    }
    //初始化列表
    $scope.pageQuery($.dataManager.options.page,$.dataManager.options.pageSize);
    //条件检索
    $scope.conditionList = function(){
      $scope.pageQuery(1,$.dataManager.options.pageSize);
    }
    //项目运行
    $scope.runWithProject = function() {
      runService.run().success(function(response) {
        if(response.length==0){
          alert("run success");
          $scope.message = "运行成功";
          $scope.state = true;
          $scope.conditionList();
          $.dataManager.refreshDataList();
        }else{
          alert("run error");
        }
      });
    };
    //数据删除
    $scope.deleteData = function(){
      $.tips("提示框aaaaaaa","提示框");
      $.confirm("确定要删除所选数据？","确认框",function(){
        runService.delete().success(function(response){
          if(response.code == "104"){
            alert("success");
            $scope.dataList = runService.list();
            $.dataManager.refreshDataList();
          }else{
            alert("失败");
          }
        });
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
})();