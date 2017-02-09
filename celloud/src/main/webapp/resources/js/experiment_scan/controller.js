(function(){
  celloudApp.controller("samplingController", function($scope, samplingService){
    $scope.productTags = samplingService.getProductTags();
    $scope.typeList = samplingService.typeList();
    var refreshList = function(){
      $scope.sampleList = samplingService.sampleList();
    }
    refreshList();
    $scope.addSample = function(){
      samplingService.sampling($scope.sampleName,$scope.selTags.tagId,$scope.type.name).success(function(data){
        if(data == 2){
          $.alert("此样品信息已经收集过，请核查或者采集下一管样品信息！");
        }else {
          refreshList();
        }
        $scope.sampleName = "";
      })
    }
    $scope.commitSample = function(){
      samplingService.commitSample($scope.sampleList).success(function(data){
        if(data > 0){
          window.open(window.CONTEXT_PATH+"/sample_order.html#/sampling/order/"+data);
          refreshList();
        }else {
          $.alert("样本已提交");
        }
      })
    }
    $scope.deleteSample = function(id){
      samplingService.deleteSample(id).success(function(data){
        if(data > 0){
          refreshList();
          $.alert("删除样本成功!");
        }else {
          $.alert("删除样本失败!");
        }
      })
    }
  });
  
  celloudApp.controller("samplingInfoController", function($scope, samplingService){
    $scope.productTags = samplingService.getProductTags();
    $scope.typeList = samplingService.typeList();
    var refreshList = function(){
      $scope.sampleList = samplingService.sampleList();
    }
    refreshList();
    $scope.addSample = function(){
      samplingService.sampling($scope.sampleName,$scope.selTags.tagId,$scope.type.name).success(function(data){
        if(data == 2){
          $.alert("此样品信息已经收集过，请核查或者采集下一管样品信息！");
        }else {
          refreshList();
        }
        $scope.sampleName = "";
      })
    }
    $scope.commitSample = function(){
      samplingService.commitSample($scope.sampleList).success(function(data){
        if(data > 0){
          window.open(window.CONTEXT_PATH+"/sample_order.html#/sampling/order/"+data);
          refreshList();
        }else {
          $.alert("样本已提交");
        }
      })
    }
    $scope.deleteSample = function(id){
      samplingService.deleteSample(id).success(function(data){
        if(data > 0){
          refreshList();
          $.alert("删除样本成功!");
        }else {
          $.alert("删除样本失败!");
        }
      })
    }
  });
  
  celloudApp.controller("sampleTrackingController", function($scope, $routeParams, sampleTrackingService){
    $scope.pages = {
        page : 1,
        pageSize : 20
    };
    $scope.pageQuery = function(page,pageSize){
      $scope.pages = {
        page : page,
        pageSize : pageSize
      };
      sampleTrackingService.pageList($scope.pages.page,$scope.pages.pageSize,$scope.sampleName).success(function(data){
        $scope.sampleList = data;
      });
    }
    $scope.sampleNameQuery = function(){
      $scope.pageQuery(1,$scope.pages.pageSize);
    }
    $scope.doOnKeyPress= function($event){
      if($event.keyCode == 13){
        if($scope.sampleName==''||$scope.sampleName==undefined){
        }else{
          $scope.sampleNameQuery();
        }
      }
    }
    $scope.sampleList = $scope.pageQuery($scope.pages.page,$scope.pages.pageSize);
  });
  
  celloudApp.controller("sampleOrderController", function($scope, $routeParams, sampleOrderService){
    sampleOrderService.sampleOrderInfo($routeParams.orderId).success(function(data){
      if(data != null){
        $scope.sampleOrderInfo = data;
      }else {
        $.alert("样本已提交");
      }
    });
    $scope.print = function(){
      window.print();
    };
  });
  
  celloudApp.controller("scanStorageController", function($scope, scanStorageService){
    $scope.pages = {
      page : 1,
      pageSize : 20
    };
    
    $scope.checkedLength = 0;
    
    $scope.pageQuery = function(page,pageSize){
      $scope.pages = {
        page : page,
        pageSize : pageSize
      };
      scanStorageService.pageList($scope.pages.page,$scope.pages.pageSize).success(function(data){
        $scope.sampleList = data;
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
    $scope.checkAll = function(){
    	var flag = $("input[name='checkAll']").prop("checked");
    	if(flag){
    		$scope.checkedLength = $("input[name='checkOne']").length;
    	}else{
    		$scope.checkedLength = 0;
    	}
    	$("input[name='checkOne']").each(function(){
    		$(this).prop("checked", flag);
    	})
    }
    $scope.checkOne = function(){
    	// 获取选中的输入框的数量
    	var length1 = $("input[name='checkOne']:checked").length;
    	$scope.checkedLength = length1;
    	// 获取全部的输入框的数量
    	var length2 = $("input[name='checkOne']").length;
    	if(length1 == length2){
    		$("input[name='checkAll']").prop("checked",true);
    	}else{
    		$("input[name='checkAll']").prop("checked",false);
    	}
    }
    $scope.printQRCode = function(){
    	// 获取选中的checkbox, 封装数组进行打印
    	var sampleNames = new Array();
    	var dates = new Array();
    	$("input[name='checkOne']:checked").each(function(){
    		sampleNames.unshift($(this).parent().parent().parent().children(".experSampleName").html().trim());
    		dates.unshift($(this).parent().parent().parent().children(".createDate").html().trim());
    	});
    	printQRCodeBatch(sampleNames, dates);
    }
    $scope.scanStorage = function(){
      if($scope.orderNo == "" || $scope.orderNo == undefined){
        $.alert("请输入订单编号！");
        return false;
      }
    	if($scope.sampleName == "" || $scope.sampleName == undefined){
    		$.alert("请输入样本信息！");
    		return false;
    	}
      scanStorageService.scanStorage($scope.orderNo,$scope.sampleName).success(function(data){
        if(data.error != undefined){
          $.alert(data.error);
        }else{
          //打印二维码
//          printQRCode(data.experName,data.date);
          $scope.sampleList = $scope.pageQuery($scope.page,$scope.pageSize);
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
    
    $scope.sampleList = $scope.pageQuery($scope.page,$scope.pageSize);
  });
  
  celloudApp.controller("tokenDNAController",function($scope,scanStorageService, tokenDNAService){
      $scope.pages = {
        page : 1,
        pageSize : 20
      };
      $scope.checkedLength = 0;
      $scope.pageQuery = function(page,pageSize){
        $scope.pages = {
          page : page,
          pageSize : pageSize
        };
        tokenDNAService.pageList($scope.pages.page,$scope.pages.pageSize).success(function(data){
          $scope.sampleList = data
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
      		$.alert("请输入实验样本编号！");
      		return false;
      	}
        tokenDNAService.tokenDNA($scope.sampleName).success(function(data){
          if(data.error != undefined){
            $.alert(data.error);
          }else{
            //打印二维码
//            printQRCode($scope.sampleName,data.date);
            $scope.sampleList = $scope.pageQuery($scope.pages.page,$scope.pages.pageSize);
          }
          $scope.sampleName = "";
        });
      }
      $scope.checkAll = function(){
      	var flag = $("input[name='checkAll']").prop("checked");
      	if(flag){
    		$scope.checkedLength = $("input[name='checkOne']").length;
    	}else{
    		$scope.checkedLength = 0;
    	}
      	$("input[name='checkOne']").each(function(){
      		$(this).prop("checked", flag);
      	})
      }
      $scope.checkOne = function(){
      	// 获取选中的输入框的数量
      	var length1 = $("input[name='checkOne']:checked").length;
      	$scope.checkedLength = length1;
      	// 获取全部的输入框的数量
      	var length2 = $("input[name='checkOne']").length;
      	if(length1 == length2){
      		$("input[name='checkAll']").prop("checked",true);
      	}else{
      		$("input[name='checkAll']").prop("checked",false);
      	}
      }
      $scope.printQRCode = function(){
      	// 获取选中的checkbox, 封装数组进行打印
      	var sampleNames = new Array();
      	var dates = new Array();
      	$("input[name='checkOne']:checked").each(function(){
      		sampleNames.unshift($(this).parent().parent().parent().children(".experSampleName").html().trim());
      		dates.unshift($(this).parent().parent().parent().children(".createDate").html().trim());
      	});
      	printQRCodeBatch(sampleNames, dates);
      }
      $scope.remove = function(id){
        scanStorageService.remove(id).success(function(data){
          if(data > 0){
            $scope.sampleList = $scope.pageQuery($scope.pages.page,$scope.pages.pageSize);
            $.alert("样本删除成功");
          }else{
            $.alert("样本删除失败");
          }
        });
      }
      $scope.sampleList = $scope.pageQuery($scope.pages.page,$scope.pages.pageSize);
  });
  
  celloudApp.controller("buidLibraryController",function($scope,scanStorageService, buidLibraryService){
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
      var samplelength = $scope.infos.sampleIndex.length;
      if(sampleList.length>=samplelength){
        $.tips("每个文库最多"+samplelength+"个样本！")
      }else if($scope.sampleName == '' || $scope.sampleName == undefined){
    	  $.alert("请输入实验样本编号");
      }else{
        buidLibraryService.addSample($scope.sampleName,sampleList).success(function(data){
          if(data > 0){
            $scope.infos = buidLibraryService.infos();
          }else if(data == 0){
            $.alert("此样本未提取DNA");
          }else {
            $.alert("此样品已在等待建库，请核查或者扫描下一管样品信息！");
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
      var select = $scope.sindex.name+":"+$scope.sindex.seq;
      buidLibraryService.addLibrary($scope.infos.libraryName,select,$scope.infos.pageList.datas).success(function(data){
        if(data != null && data != undefined){
          //打印二维码
          printQRCode(data.storageName,data.sindex);
          $scope.infos = buidLibraryService.infos();
          $.alert("建库成功！");
        }else {
          $scope.repeatError = true;
          $.alert("建库失败！");
        }
      });
    }
    $scope.addAndDownLibrary = function(){
      var select = $scope.sindex.name+":"+$scope.sindex.seq;
      buidLibraryService.addLibrary($scope.infos.libraryName,select,$scope.infos.pageList.datas).success(function(data){
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
  
  celloudApp.controller("storagesController",function($scope, storagesService,buidLibraryService){
    $scope.storages = storagesService.storages();
    storagesService.sampleList().success(function(data){
      $scope.sampleList = data;
    });
    $scope.pages = {
      page : 1,
      pageSize : 20
    };
    $scope.pageQuery = function(page,pageSize){
      $scope.pages = {
        page : page,
        pageSize : pageSize
      };
      storagesService.pageList($scope.pages.page,$scope.pages.pageSize).success(function(data){
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
    
    $scope.updateInMachine = function(id){
      storagesService.changeInMachine(id).success(function(flag){
        if(flag>0){
          $.alert("修改上机状态成功");
          $scope.storages = $scope.pageQuery($scope.pages.page,$scope.pages.pageSize);
        }else{
          $.alert("修改上机状态失败");
        }
      })
    }
  });
  
  celloudApp.controller("editSampleController",function($scope, scanStorageService, tokenDNAService){
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
        	    $scope.sampleList = data;
            });
          }else{
        	  tokenDNAService.pageList($scope.pages.page,$scope.pages.pageSize).success(function(data){
        	    $scope.sampleList = data
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