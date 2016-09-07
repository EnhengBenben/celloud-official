(function(){
  celloudApp.service("samplingService", function($resource,$http){
    var self = this;
    self.sampleList = function(){
      return $resource("sample/getSamplingList").query();
    }
    self.getProductTags = function(){
      return $resource("uploadFile/getProductTag").query();
    }
    self.sampling = function(sampleName,tagId,type){
      return $http({method:"POST",url:'sample/sampling',params:{"sampleName":sampleName,"tagId":tagId,"type":type}});
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
  celloudApp.service("scanStorageService", function($resource,$http){
    var self = this;
    self.sampleList = function(){
      return $resource("sample/getScanStorageSamples").get();
    }
    self.pageList = function(page,size){
      return $http.get("sample/getScanStorageSamples",{params: {page:page,size:size}});
    }
    self.scanStorage = function(sampleName){
      return $http({method:"POST",url:'sample/toScanStorage',params:{"sampleName":sampleName}});
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
      return $resource("sample/getTokenDnaSamples").get();
    }
    self.pageList = function(page,size){
      return $http.get("sample/getTokenDnaSamples",{params: {page:page,size:size}});
    }
    self.tokenDNA = function(sampleName){
      return $http({method:"POST",url:'sample/toTokenDNA',params:{"sampleName":sampleName}});
    }
  });
  celloudApp.service("buidLibraryService", function($resource,$http){
    var self = this;
    self.infos = function(){
      return $resource("sample/getBuidLibrarySamples").get();
    }
    self.addSample = function(sampleName,sno){
      return $http({method:"POST",url:'sample/addSampleToLibrary',params:{"sampleName":sampleName,"sno":sno}});
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
      $http.get("sample/downExperExcel",{params: {ssId:ssId,storageName:storageName}});
    }
  });
  celloudApp.service("storagesService", function($resource,$http,$routeParams){
    var self = this;
    self.storages = function(){
      return $resource("sample/getSampleStorages").get();
    }
    self.pageList = function(page,size){
      return $http.get("sample/getSampleStorages",{params: {page:page,size:size}});
    }
    self.sampleList = function(){
      return $http.get("sample/sampleListInStorage",{params: {ssId:$routeParams.ssId}});
    }
  });
})()