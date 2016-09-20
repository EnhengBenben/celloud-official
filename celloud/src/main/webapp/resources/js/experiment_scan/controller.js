(function(){
  celloudApp.controller("samplingController", function($rootScope, $scope, samplingService){
    $scope.sampleList = samplingService.sampleList();
    $scope.productTags = samplingService.getProductTags();
    $scope.typeList = ["血","组织液","引流液","关节液","心包积液","胸水","脓液","脑脊液","阴道拭子","腹水","尿液","肺泡灌洗液"];
    $scope.addSample = function(){
      samplingService.sampling($scope.sampleName,$scope.selTags.tagId,$scope.type).success(function(data){
        if(data == 2){
          $.alert("此样品信息已经收集过，请核查或者采集下一管样品信息！");
        }else {
          $scope.sampleList = samplingService.sampleList();
        }
        $scope.sampleName = "";
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
  
  celloudApp.controller("scanStorageController", function($rootScope, $scope, scanStorageService){
    $scope.pages = {
      page : 1,
      pageSize : 20
    };
    $scope.pageQuery = function(page,pageSize){
      $scope.pages = {
        page : page,
        pageSize : pageSize
      };
      scanStorageService.pageList($scope.pages.page,$scope.pages.pageSize).success(function(data){
    	  $rootScope.sampleList = data;
      });
    }
    
    $scope.doOnKeyPress= function($event){
    	if($event.keyCode == 13){
    		if($scope.sampleName==''||$scope.sampleName==undefined){
    		}else{
    			$scope.scanStorage();
    		}
    	}
    }
    
    $scope.scanStorage = function(){
    	if($scope.sampleName == "" || $scope.sampleName == undefined){
    		$.alert("请输入样本信息！");
    		return false;
    	}
      scanStorageService.scanStorage($scope.sampleName).success(function(data){
        if(data == 0){
          $.alert("系统中无此样本信息，请确认是已采样样本！")
        }else if(data > 0){
          $rootScope.sampleList = $scope.pageQuery($scope.page,$scope.pageSize);
        }else {
          $.alert("此样品信息已经收集过，请核查或者采集下一管样品信息！")
        }
        $scope.sampleName = "";
      });
    }
    
    $scope.remove = function(id){
      scanStorageService.remove(id).success(function(data){
        if(data > 0){
          $scope.pageQuery($scope.pages.page,$scope.pages.pageSize);
          $.alert("样本删除成功");
        }else{
          $.alert("样本删除失败");
        }
      });
    }
    
    $rootScope.sampleList = $scope.pageQuery($scope.page,$scope.pageSize);
  });
  
  celloudApp.controller("tokenDNAController",function($rootScope, $scope,scanStorageService, tokenDNAService){
      $scope.pages = {
        page : 1,
        pageSize : 20
      };
      $scope.pageQuery = function(page,pageSize){
        $scope.pages = {
          page : page,
          pageSize : pageSize
        };
        tokenDNAService.pageList($scope.pages.page,$scope.pages.pageSize).success(function(data){
          $rootScope.sampleList = data
        });
      }
      
      $scope.doOnKeyPress= function($event){
      	if($event.keyCode == 13){
      		if($scope.sampleName==''||$scope.sampleName==undefined){
      		}else{
      			$scope.tokenDNA();
      		}
      	}
      }
    
      $scope.tokenDNA = function(){
    	if($scope.sampleName == "" || $scope.sampleName == undefined){
    		$.alert("请输入样本信息！");
    		return false;
    	}
        tokenDNAService.tokenDNA($scope.sampleName).success(function(data){
          if(data == 0){
            $.alert("此样本未入库");
          }else if(data > 0){
            $rootScope.sampleList = $scope.pageQuery($scope.pages.page,$scope.pages.pageSize);
          }else {
            $.alert("此样品信息已经收集过，请核查或者采集下一管样品信息！");
          }
          $scope.sampleName = "";
        });
      }
      $scope.remove = function(id){
        scanStorageService.remove(id).success(function(data){
          if(data > 0){
            $rootScope.sampleList = $scope.pageQuery($scope.pages.page,$scope.pages.pageSize);
            $.alert("样本删除成功");
          }else{
            $.alert("样本删除失败");
          }
        });
      }
      $rootScope.sampleList = $scope.pageQuery($scope.pages.page,$scope.pages.pageSize);
  });
  
  celloudApp.controller("buidLibraryController",function($rootScope, $scope,scanStorageService, buidLibraryService){
    $scope.infos = buidLibraryService.infos();
    
    $scope.doOnKeyPress= function($event){
    	if($event.keyCode == 13){
    		if($scope.sampleName==''||$scope.sampleName==undefined){
    			$.alert("请输入样本信息");
    		}else{
    			$scope.addSample();
    		}
    	}
    }
    
    $scope.addSample = function(){
      var sampleList = $scope.infos.pageList.datas;
      if(sampleList.length>=12){
        $.tips("每个文库最多12个样本！")
      }else if($scope.sampleName == '' || $scope.sampleName == undefined){
    	  $.alert("请输入样本信息");
      }else{
        buidLibraryService.addSample($scope.sampleName,sampleList).success(function(data){
          if(data > 0){
            $scope.infos = buidLibraryService.infos();
          }else if(data == 0){
            $.alert("此样本未提取DNA");
          }else {
            $.alert("此样品信息已经入库，请核查或者扫描下一管样品信息！");
          }
        });
      }
      $scope.sampleName = "";
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
        if(data > 0){
          $scope.infos = buidLibraryService.infos();
          $.alert("建库成功！");
        }else {
          $scope.repeatError = true;
          $.alert("建库失败！");
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
              $.alert("建库并下载成功！");
            }
          });
        }else {
          $scope.repeatError = true;
        }
      });
    }
  });
  
  celloudApp.controller("storagesController",function($rootScope, $scope, storagesService,buidLibraryService){
    $scope.storages = storagesService.storages();
    storagesService.sampleList().success(function(data){
      $scope.sampleList = data;
    });
    var pages = {
        page : 1,
        pageSize : 10
      };
      $scope.pageQuery = function(page,pageSize){
        pages = {
          page : page,
          pageSize : pageSize
        };
        storagesService.pageList(page,pageSize).success(function(data){
          $scope.storages = data;
        });
      }
    $scope.download = function(id,storageName){
      buidLibraryService.downloadExcel(id,storageName).success(function(flag){
        if(flag==1){
          $.alert("没有正确生成Excel文件");
        }else{
          var url = window.location.href.split("index")[0];
          window.location.href=url+"sample/downExperExcel?ssId="+id+"&storageName="+storageName;
          $.alert("下载Excel文件成功！");
        }
      });
    }
  });
  
  celloudApp.controller("editSampleController",function($rootScope, $scope, scanStorageService, tokenDNAService){
    $scope.toEditRemark = function(id,remark){
      $scope.sampleId = id;
      $scope.remark = remark;
      $scope.remark_bak = remark;
    }
    $scope.resetSampleRemark = function() {
      $scope.remark = angular.copy($scope.remark_bak);
    };
    
    $scope.editRemark = function(){
      scanStorageService.editRemark($scope.sampleId,$scope.remark).success(function(data){
        if(data > 0){
          if($scope.exper_state == 1){
        	  scanStorageService.pageList($scope.pages.page,$scope.pages.pageSize).success(function(data){
            	  $rootScope.sampleList = data;
              });
          }else{
        	  tokenDNAService.pageList($scope.pages.page,$scope.pages.pageSize).success(function(data){
                  $rootScope.sampleList = data
                });
          }
          $.alert("样本修改成功");
        }else{
          $.alert("样本修改失败");
        }
        $("#sample-remark-modal").modal("hide");
      });
    }
  });
})()