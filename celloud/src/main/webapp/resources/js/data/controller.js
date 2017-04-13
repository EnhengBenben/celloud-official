(function(){
  celloudApp.controller("dataListController", function($scope, $rootScope, $location, $routeParams,$translate, runService, uploadService){
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
    $.dataManager.options.sort = 0;
    $scope.sortQuery = function(column, order){
    	console.log(column, order);
    	if(column == 'anotherName'){
    		$scope.column = 'anotherName';
    		$.dataManager.options.sort = 3;
    		if(order == 'asc'){
    			$scope.order = 'desc';
    			$.dataManager.options.sortAnotherName = 'desc';
    		}else{
    			$scope.order = 'asc';
    			$.dataManager.options.sortAnotherName = 'asc';
    		}
    	}else if(column == 'run'){
    		$scope.column = 'run';
    		$.dataManager.options.sort = 4;
    		if(order == 'asc'){
    			$scope.order = 'desc';
    			$.dataManager.options.sortRun = 'desc';
    		}else{
    			$scope.order = 'asc';
    			$.dataManager.options.sortRun = 'asc';
    		}
    	}
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
        $.tips($translate.instant("PROMPT.AT_LEAST_ONE"),$translate.instant("PROMPT.PROMPT"));
        return false;
      }
      var maxNum = $.dataManager.options.dataMax;
      if(checkedIds.length>maxNum){
        $.tips($translate.instant("PROMPT.ARCHIVE_MAX",{maxNum: maxNum}),$translate.instant("PROMPT.PROMPT"));
        return false;
      }
      return true;
    }
    //项目运行
    $scope.runWithProject = function() {
    	uploadService.checkRole().
		success(function(data){
			if(data == "0"){
				$.alert($translate.instant("ALERT.RUN_SUCC"));
				return;
			}else{
				if(!$scope.checkNum()){
			        return ;
			      }
			      if($.dataManager.options.isRun!=0){
			        $.tips($translate.instant("PROMPT.SELECT_NOT_RUN"),$translate.instant("PROMPT.PROMPT"));
			        return;
			      }
			      if($.dataManager.options.isTag!=0){
			        $.tips($translate.instant("PROMPT.SELECT_NO_PRODUCT"),$translate.instant("PROMPT.PROMPT"));
			        return;
			      }
			      if($.dataManager.options.isBSI!=0){
			        $.tips($translate.instant("PROMPT.SELECT_NOT_BACTIVE"),$translate.instant("PROMPT.PROMPT"));
			        return;
			      }
			      if($.dataManager.options.isRocky!=0){
			        $.tips($translate.instant("PROMPT.SELECT_NOT_ROCKY"),$translate.instant("PROMPT.PROMPT"));
			        return;
			      }
			      if($.dataManager.options.isPair!=0){
			        window.modalConfirmBtn = $translate.instant("BUTTON.CONFIRM");
			        window.modalCancelBtn = $translate.instant("BUTTON.CANCEL");
			        $.confirm(
			            $translate.instant("CONFIRM.DATA_PAIRED"),
			            $translate.instant("CONFIRM.CONFIRM"),
			            function(){
			              $scope.run();
			            }
			        );
			      }else{
			        $scope.run();
			      }
			}
		});
    };
    var opts = {
      lines: 13, // The number of lines to draw
      length: 28, // The length of each line
      width: 14, // The line thickness
      radius: 42, // The radius of the inner circle
      corners: 1, // Corner roundness (0..1)
      rotate: 0, // The rotation offset
      direction: 1, // 1: clockwise, -1: counterclockwise
      color: '#000', // #rgb or #rrggbb or array of colors
      speed: 1, // Rounds per second
      trail: 60, // Afterglow percentage
      shadow: false, // Whether to render a shadow
      hwaccel: false, // Whether to use hardware acceleration
      className: 'spinner', // The CSS class to assign to the spinner
      zIndex: 2e9, // The z-index (defaults to 2000000000)
      top: 'auto', // Top position relative to parent in px
      left: '50%' // Left position relative to parent in px
    };
    $scope.run = function(){
      var checkedIds = $.dataManager.options.checkedIds;
      if(checkedIds.length!=$.dataManager.options.validIds){
        $rootScope.errorInfo = "数据选择异常，请刷新页面后重新选择！";
        $("#tips-modal").modal("show");
        return;
      }
      spinner = new Spinner(opts);
      var target = document.getElementById('_data_table');
      spinner.spin(target);
      runService.run().success(function(response) {
        if(response.success){
          $.alert($translate.instant("PROMPT.RUN_SUCC"));
          $scope.pageQuery($.dataManager.options.page,$.dataManager.options.pageSize);
          $.dataManager.refreshDataList();
        }else{
          $scope.message = response.message;
        }
        spinner.stop();
      });
    }
    //数据删除
    $scope.deleteData = function(){
    	// 检查用户角色是否为测试账号
		  uploadService.checkRole().success(function(data){
  			if(data == "0"){
  			  $.tips($translate.instant("PROMPT.TEST_USER_NO_ARCHIVE"),$translate.instant("PROMPT.PROMPT"));
  			}else{
  		      if(!$scope.checkNum()){
  			      return ;
  			    }
  		      window.modalConfirmBtn = $translate.instant("BUTTON.CONFIRM");
            window.modalCancelBtn = $translate.instant("BUTTON.CANCEL");
  		       $.confirm(
  		          $translate.instant("CONFIRM.ARCHIVE_DATA"),
  		          $translate.instant("CONFIRM.CONFIRM"),
  		          function(){
      		        runService.deleteData().success(function(response){
      		          if(response.success){
      		            $scope.conditionList();
      		            $.dataManager.refreshDataList();
      		          }
      		          if(response.message == "归档成功")
      		            $.alert($translate.instant("ALERT.ARCHIVE_SUCC"));
      		        });
  		          }
  		       );
  			}
  		});
    };
    //跳转数据编辑
    $scope.toEditData = function(fileId){
    	
    	uploadService.checkRole().
  		success(function(data){
  			if(data == "0"){
  			  $.tips($translate.instant("PROMPT.TEST_USER_NO_MODIFIED"),$translate.instant("PROMPT.PROMPT"));
  			}else{
  				$scope.updateState = false;
  			    runService.toEditData(fileId).
  			    success(function(response){
  			    	$scope.dataFile = response['file'];
  			    	$scope.appList = response['appList'];
  			    	for(i = 0; i < $scope.appList.length; i++){
  			    		if($.trim($scope.appList[i].tagName) == $.trim($scope.dataFile.tagName)){
  			    			$scope.appSelected = $scope.appList[i];
  			    			break;
  			    		}
  			    	}
  			    });
  			    $("#data-detail-modal").modal("show");
  			}
  		});
    }
    //修改数据信息
    $scope.submitEditData = function(){
      var alias = $scope.dataFile.anotherName;
      if(alias!=null && alias.length>50){
        $.tips($translate.instant("PROMPT.FILE_ALIAS_MAX_CHAR",{maxNum: 50}),$translate.instant("PROMPT.PROMPT"));
        return;
      }
      var batch = $scope.dataFile.batch;
      if(batch!=null && batch.length>50){
        $.tips($translate.instant("PROMPT.DATA_LABEL_MAX_CHAR",{maxNum: 50}),$translate.instant("PROMPT.PROMPT"));
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
        if(response.message == "修改成功")
          $.alert($translate.instant("ALERT.MODIFIED_SUCC"));
      });
    }
  });
})();
