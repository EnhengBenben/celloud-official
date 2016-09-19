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
      $.dataManager.refreshDataList("clean");
    }
    //初始化列表
    $scope.pageQuery($.dataManager.options.page,$.dataManager.options.pageSize);
    //条件检索
    $scope.conditionList = function(){
      $scope.pageQuery(1,$.dataManager.options.pageSize);
    }
    // 为搜索框绑定回车事件
    $scope.doSearch = function($event){
    	if($event.keyCode == 13){
    		$scope.conditionList();
    	}
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
      if($.dataManager.options.isRun!=0){
        $rootScope.errorInfo = "所勾选的数据必须不处于运行状态！";
        $("#tips-modal").modal("show");
        return;
      }
      if($.dataManager.options.isTag!=0){
        $rootScope.errorInfo = "所勾选的数据必须有产品标签！";
        $("#tips-modal").modal("show");
        return;
      }
      if($.dataManager.options.isBSI!=0){
        $rootScope.errorInfo = "所勾选的数据产品标签不能为百菌探！";
        $("#tips-modal").modal("show");
        return;
      }
      if($.dataManager.options.isRocky!=0){
        $rootScope.errorInfo = "所勾选的数据产品标签不能为华木兰！";
        $("#tips-modal").modal("show");
        return;
      }
      if($.dataManager.options.isPair!=0){
        $.confirm("请保证所选数据为配对数据！","确认框",function(){
          $scope.run();
        });
      }else{
        $scope.run();
      }
    };
    $scope.run = function(){
      var checkedIds = $.dataManager.options.checkedIds;
      if(checkedIds.length!=$.dataManager.options.validIds){
        $rootScope.errorInfo = "数据选择异常，请刷新页面后重新选择！";
        $("#tips-modal").modal("show");
        return;
      }
      runService.run().success(function(response) {
        if(response.success){
          $.alert("运行成功");
          $scope.pageQuery($.dataManager.options.page,$.dataManager.options.pageSize);
          $.dataManager.refreshDataList();
        }else{
          $scope.message = response.message;
        }
      });
    }
    //数据删除
    $scope.deleteData = function(){
      if(!$scope.checkNum()){
        return ;
      }
      $.confirm("确定要归档所选数据？","确认框",function(){
        runService.deleteData().success(function(response){
          if(response.success){
            $scope.conditionList();
            $.dataManager.refreshDataList();
          }
          $.alert(response.message);
        });
      });
    };
    //跳转数据编辑
    $scope.toEditData = function(fileId){
      $scope.updateState = false;
      runService.toEditData(fileId).success(function(response){
        $scope.dataFile = response['file'];
        $scope.appList = response['appList'];
        for(i = 0; i < $scope.appList.length; i++){
        	if($.trim($scope.appList[i].tagName) == $.trim($scope.dataFile.tagName)){
        		$scope.appSelected = $scope.appList[i];
        		break;
        	}
        }
      });
    }
    //修改数据信息
    $scope.submitEditData = function(){
      var alias = $scope.dataFile.anotherName;
      if(alias!=null && alias.length>50){
        $scope.updateMessage = "文件别名不能超过50个字符";
        $scope.updateState = true;
        return;
      }
      var batch = $scope.dataFile.batch;
      if(batch!=null && batch.length>50){
        $scope.updateMessage = "数据标签不能超过50个字符";
        $scope.updateState = true;
        return;
      }
      $scope.updateState = false;
      $scope.dataFile.tagName = $scope.appSelected == undefined? "" : $scope.appSelected.tagName;
      runService.submitEditData($scope.dataFile).success(function(response){
        if(response.success){
          $scope.pageQuery($.dataManager.options.page,$.dataManager.options.pageSize);
          $.dataManager.refreshDataList();
          $("#data-detail-modal").modal("hide");
        }
        $.alert(response.message);
      });
    }
  });
})();
