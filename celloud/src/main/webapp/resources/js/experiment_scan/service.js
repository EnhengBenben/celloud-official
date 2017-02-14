(function(){
  celloudApp.service("samplingService", function($resource,$http){
    var self = this;
    self.sampleList = function(){
      return $resource("sample/getSamplingList?v="+new Date()).query();
    }
    self.getProductTags = function(){
      return $resource("uploadFile/getProductTag").query();
    }
    self.typeList = function(){
      return $resource("metadata/sampleType").query();
    }
    self.sampling = function(sampleName,tagId,type){
      return $http({
                method:"POST",
                url:'sample/sampling',
                headers: {'x-requested-with':null},
                params:{"sampleName":sampleName,"tagId":tagId,"type":type}
              });
    }
    self.deleteSample = function(id){
      return $http({method:"POST",url:'sample/bsi/deleteOne',params:{"sampleId":id}});
    }
    self.commitSample = function(sampleList){
      var sampleIds=new Array()
      for(s in sampleList){
        if(sampleList[s].sampleId != undefined){
          sampleIds.push(sampleList[s].sampleId);
        }
      }
      return $http({method:"POST",url:'sample/commitSampling',params:{"sampleIds":sampleIds}});
    }
  });
  
  celloudApp.service("sampleInfoService", function($resource,$http){
    var self = this;
    self.getSampleInfo = function(id){
    	return $http({ method:"GET", url:'sample/sampleInfos/' + id});
    }
    self.listSampleInfo = function(){
    	return $http({ method:"GET", url:'sample/listSampleInfo' });
    }
    self.getProductTags = function(){
    	return $resource("uploadFile/getProductTag").query();
    }
    self.typeList = function(){
    	return $resource("metadata/sampleType").query();
    }
    self.sampleInfos = function(patient, sampleName, tagId, type){
    	var p ={"sampleName":sampleName,"tagId":tagId[0],"type":type[0]};
    	$.extend(patient,p);
    	console.log(patient);
    	return $http({ method:"POST", url:'sample/sampleInfos',headers: { 'Content-Type': 'application/x-www-form-urlencoded' }, data:$.param(patient) });
    }
    self.removeSampleInfo = function(id){
    	return $http({method:"POST",url:'sample/removeSampleInfo',headers: { 'Content-Type': 'application/x-www-form-urlencoded' },data:$.param({"sampleId":id})});
    }
    self.commitSample = function(sampleList){
    	var sampleIds=new Array()
    	for(s in sampleList){
    		if(sampleList[s].sampleId != undefined){
    			sampleIds.push(sampleList[s].sampleId);
    		}
    	}
    	return $http({method:"POST",url:'sample/commitSampling',params:{"sampleIds":sampleIds}});
    }
  });
  
  celloudApp.service("sampleOrderService", function($resource,$http){
    this.sampleOrderInfo = function(orderId){
      return $http({method:"POST",url:'sample/getSampleOrderInfo',params:{"orderId":orderId,"v":new Date()}});
    }
  });
  celloudApp.service("sampleTrackingService", function($resource,$http){
    var self = this;
    self.sampleList = function(sampleName){
      return $resource("sample/sampleTranking?v="+new Date()).get();
    }
    self.pageList = function(page,size,sampleName){
      return $http.get("sample/sampleTranking?v="+new Date(),{params: {page:page,size:size,sampleName:sampleName}});
    }
  });
  celloudApp.service("scanStorageService", function($resource,$http){
    var self = this;
    self.sampleList = function(){
      return $resource("sample/getScanStorageSamples?v="+new Date()).get();
    }
    self.pageList = function(page,size){
      return $http.get("sample/getScanStorageSamples?v="+new Date(),{params: {page:page,size:size}});
    }
    self.scanStorage = function(orderNo,sampleName){
      return $http({method:"POST",url:'sample/toScanStorage',params:{"orderNo":orderNo,"sampleName":sampleName}});
    }
    self.editRemark = function(id,remark){
      return $http({method:"POST",url:'sample/editRemark',params:{"sampleId":id,"remark":remark}});
    }
    self.remove = function(id){
      return $http({method:"POST",url:'sample/removeSampleLog',params:{"sampleLogId":id}});
    }
  });
  celloudApp.service("tokenDNAService", function($resource,$http){
    var self = this;
    self.sampleList = function(){
      return $resource("sample/getTokenDnaSamples?v="+new Date()).get();
    }
    self.pageList = function(page,size){
      return $http.get("sample/getTokenDnaSamples?v="+new Date(),{params: {page:page,size:size}});
    }
    self.tokenDNA = function(sampleName){
      return $http({method:"POST",url:'sample/toTokenDNA',params:{"experSampleName":sampleName}});
    }
  });
  celloudApp.service("buidLibraryService", function($resource,$http){
    var self = this;
    self.infos = function(){
      return $resource("sample/getBuidLibrarySamples?v="+new Date()).get();
    }
    self.addSample = function(sampleName,sampleList){
      var sindexs=new Array();
      for(s in sampleList){
         sindexs.push(sampleList[s].sindex);
      }
      return $http({method:"POST",url:'sample/addSampleToLibrary',params:{"experSampleName":sampleName,"sindexs":sindexs}});
    }
    self.addLibrary = function(libraryName,sindex,sampleList){
      var sampleIds=new Array();
      for(s in sampleList){
        if(sampleList[s].sampleId != undefined){
          sampleIds.push(sampleList[s].sampleId);
        }
      }
      return $http({method:"POST",url:'sample/addLibrary',params:{"libraryName":libraryName,"sindex":sindex,"sampleIds":sampleIds}});
    }
    self.downloadExcel = function(ssId,storageName){
      return $http.get("sample/downExperExcel",{params: {ssId:ssId,storageName:storageName}});
    }
  });
  celloudApp.service("storagesService", function($resource,$http,$routeParams){
    var self = this;
    self.storages = function(){
      return $resource("sample/getSampleStorages?v="+new Date()).get();
    }
    self.pageList = function(page,size){
      return $http.get("sample/getSampleStorages?v="+new Date(),{params: {page:page,size:size}});
    }
    self.sampleList = function(){
      return $http.get("sample/sampleListInStorage?v="+new Date(),{params: {ssId:$routeParams.ssId}});
    }
    self.changeInMachine = function(id){
      return $http({method:"POST",url:'sample/changeInMachine',params:{"sampleStorageId":id}});
    }
  });
})()