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
    $scope.deleteSample = function(id){
      samplingService.deleteSample(id).success(function(data){
        if(data > 0){
          $scope.sampleList = samplingService.sampleList();
          $.alert("删除样本成功");
        }else {
          $.alert("删除样本失败");
        }
      })
    }
  });
  
  celloudApp.controller("scanStorageController", function($scope, scanStorageService){
    $scope.sampleList = scanStorageService.sampleList();
    var pages = {
      page : 1,
      pageSize : 10
    };
    $scope.pageQuery = function(page,pageSize){
      pages = {
        page : page,
        pageSize : pageSize
      };
      scanStorageService.pageList(page,pageSize).success(function(data){
        $scope.sampleList = scanStorageService.sampleList();
      });
    }
    
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
  
  celloudApp.controller("tokenDNAController",function($scope,scanStorageService, tokenDNAService){
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
    $scope.remove = function(id){
      scanStorageService.remove(id).success(function(data){
        if(data > 0){
          $scope.sampleList = tokenDNAService.sampleList();
          $.alert("样本删除成功");
        }else{
          $.alert("样本删除失败");
        }
      });
    }
  });
  
  celloudApp.controller("buidLibraryController",function($scope,scanStorageService, buidLibraryService){
    $scope.infos = buidLibraryService.infos();
    $scope.addSample = function(){
      var sampleList = $scope.infos.pageList.datas;
      if(sampleList.length>=12){
        $.tips("每个文库最多12个样本！")
      }else{
        buidLibraryService.addSample($scope.sampleName,sampleList).success(function(data){
          if(data == 0){
            $scope.notPrevError = true;
          }else if(data > 0){
            $scope.infos = buidLibraryService.infos();
          }else {
            $scope.repeatError = true;
          }
        });
      }
    }
    $scope.remove = function(id){
      scanStorageService.remove(id).success(function(data){
        if(data > 0){
          $scope.infos = buidLibraryService.infos();
          $.alert("样本删除成功");
        }else{
          $.alert("样本删除失败");
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
          var storageName = $scope.infos.libraryName;
          $scope.infos = buidLibraryService.infos();
          buidLibraryService.downloadExcel(data,storageName).success(function(flag){
            if(flag==1){
              $.alert("没有正确生成Excel文件");
            }else{
              var url = window.location.href.split("index")[0];
              window.location.href=url+"sample/downExperExcel?ssId="+data+"&storageName="+storageName;
            }
          });
        }else {
          $scope.repeatError = true;
        }
      });
    }
  });
  
  celloudApp.controller("storagesController",function($scope, storagesService,buidLibraryService){
    $scope.storages = storagesService.storages();
    storagesService.sampleList().success(function(data){
      $scope.sampleList = data;
    });
    
    $scope.download = function(id,storageName){
      buidLibraryService.downloadExcel(id,storageName).success(function(flag){
        if(flag==1){
          $.alert("没有正确生成Excel文件");
        }else{
          var url = window.location.href.split("index")[0];
          window.location.href=url+"sample/downExperExcel?ssId="+id+"&storageName="+storageName;
        }
      });
    }
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
  });
})()