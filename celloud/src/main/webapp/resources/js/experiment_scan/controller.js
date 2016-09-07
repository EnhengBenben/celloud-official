(function(){
  celloudApp.controller("samplingController", function($scope, samplingService){
    $scope.sampleList = samplingService.sampleList();
    $scope.productTags = samplingService.getProductTags();
    $scope.typeList = ["血","组织液","引流液","关节液","心包积液","胸水","脓液","脑脊液","阴道拭子","腹水","尿液","肺泡灌洗液"];
    $scope.addSample = function(){
      samplingService.sampling($scope.sampleName,$scope.selTags.tagId,$scope.type).success(function(data){
        if(data == 2){
          $scope.repeatError = true;
        }else {
          $scope.sampleList = samplingService.sampleList();
        }
      })
    }
    $scope.commitSample = function(){
      samplingService.commitSample($scope.sampleList).success(function(data){
        if(data > 0){
          $scope.sampleList = samplingService.sampleList();
        }else {
          $.alert("样本已提交");
        }
      })
    }
  });
  
  celloudApp.controller("scanStorageController", function($scope, scanStorageService){
    $scope.sampleList = scanStorageService.sampleList();
    
    $scope.scanStorage = function(){
      scanStorageService.scanStorage($scope.sampleName).success(function(data){
        if(data == 0){
          $scope.notPrevError = true;
        }else if(data > 0){
          $scope.sampleList = scanStorageService.sampleList();
        }else {
          $scope.repeatError = true;
        }
      });
    }
  });
  
  celloudApp.controller("tokenDNAController",function($scope, tokenDNAService){
    $scope.sampleList = tokenDNAService.sampleList();
    
    $scope.tokenDNA = function(){
      tokenDNAService.tokenDNA($scope.sampleName).success(function(data){
        if(data == 0){
          $scope.notPrevError = true;
        }else if(data > 0){
          $scope.sampleList = tokenDNAService.sampleList();
        }else {
          $scope.repeatError = true;
        }
      });
    }
  });
  
  celloudApp.controller("buidLibraryController",function($scope, buidLibraryService){
    $scope.infos = buidLibraryService.infos();
    var sno = 0;
    $scope.addSample = function(){
      buidLibraryService.addSample($scope.sampleName,sno).success(function(data){
        if(data == 0){
          $scope.notPrevError = true;
        }else if(data > 0){
          $scope.infos = buidLibraryService.infos();
          sno++;
        }else {
          $scope.repeatError = true;
        }
      });
    }
    
    $scope.addLibrary = function(){
      buidLibraryService.addLibrary($scope.infos.libraryName,$scope.sindex,$scope.infos.pageList.datas).success(function(data){
        if(data == 0){
          $scope.notPrevError = true;
        }else if(data > 0){
          $scope.infos = buidLibraryService.infos();
          sno++;
        }else {
          $scope.repeatError = true;
        }
      });
    }
    $scope.addAndDownLibrary = function(){
      buidLibraryService.addLibrary($scope.infos.libraryName,$scope.sindex,$scope.infos.pageList.datas).success(function(data){
        if(data == 0){
          $scope.notPrevError = true;
        }else if(data > 0){
          $scope.infos = buidLibraryService.infos();
          buidLibraryService.downloadExcel(data,$scope.infos.libraryName).success(function(flag){
            if(flag==1){
              $.alert("没有正确生成Excel文件");
            }else{
              var url = window.location.href.split("index")[0];
              window.location.href=url+"sample/downExperExcel?ssId="+data+"&storageName="+$scope.infos.libraryName;
            }
          });
        }else {
          $scope.repeatError = true;
        }
      });
    }
    
  });
  
  celloudApp.controller("storagesController",function($scope, storagesService){
    $scope.storages = storagesService.storages();
    storagesService.sampleList().success(function(data){
      $scope.sampleList = data;
    });
  });
  
  celloudApp.controller("editSampleController",function($scope, scanStorageService){
    $scope.toEditRemark = function(id,remark){
      $scope.sampleId = id;
      $scope.remark = remark;
    }
    
    $scope.editRemark = function(){
      scanStorageService.editRemark($scope.sampleId,$scope.remark).success(function(data){
        if(data > 0){
          $scope.sampleList = scanStorageService.sampleList();
          $.alert("样本修改成功");
        }else{
          $.alert("样本修改失败");
        }
        $("#sample-remark-modal").modal("hide");
      });
    }
    
    $scope.remove = function(id){
      scanStorageService.remove(id).success(function(data){
        if(data > 0){
          $scope.sampleList = scanStorageService.sampleList();
          $.alert("样本删除成功");
        }else{
          $.alert("样本删除失败");
        }
      });
    }
  });
})()