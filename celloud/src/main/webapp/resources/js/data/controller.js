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
    //校验选择文件数量： 0 < num <= max
    $scope.checkNum = function(){
      var checkedIds = $.dataManager.options.checkedIds;
      if(checkedIds.length==0){
        $rootScope.errorInfo = "请选择至少一条记录";
        $("#tips-modal").modal("show");
        return false;
      }
      var maxNum = $.dataManager.options.dataMax;
      if(checkedIds.length>maxNum){
        $rootScope.errorInfo = "最多允许同时操作" + maxNum + "条数据！";
        $("#tips-modal").modal("show");
        return false;
      }
      return true;
    }
    //项目运行
    $scope.runWithProject = function() {
      if(!$scope.checkNum()){
        return ;
      }
      if($.dataManager.options.noValidIds!=0){
        $rootScope.errorInfo = "运行所勾选的数据必须有产品标签并且不处于运行状态！";
        $("#tips-modal").modal("show");
        return;
      }
      var checkedIds = $.dataManager.options.checkedIds;
      if(checkedIds.length!=$.dataManager.options.validIds){
        $rootScope.errorInfo = "数据选择异常，请刷新页面后重新选择！";
        $("#tips-modal").modal("show");
        return;
      }
      runService.run().success(function(response) {
        if(response.success){
          $scope.message = "运行成功";
          $scope.pageQuery($.dataManager.options.page,$.dataManager.options.pageSize);
          $.dataManager.refreshDataList();
        }else{
          $scope.message = response.message;
        }
        $scope.state = true;
      });
    };
    //数据删除
    $scope.deleteData = function(){
      if(!$scope.checkNum()){
        return ;
      }
      $.confirm("确定要删除所选数据？","确认框",function(){
        runService.delete().success(function(response){
          if(response.success){
            $scope.conditionList();
            $.dataManager.refreshDataList();
          }
          $scope.message = response.message;
          $scope.state = true;
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
        if(response.success){
          $scope.pageQuery($.dataManager.options.page,$.dataManager.options.pageSize);
          $.dataManager.refreshDataList();
          $("#data-detail-modal").modal("hide");
        }
        $scope.message = response.message;
        $scope.state = true;
      });
    }
  });
})();
