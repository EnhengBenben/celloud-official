(function(){
  celloudApp.service("samplingService", function($resource,$http){
    var self = this;
    self.sampleList = function(){
      return $resource("sample/getSamplingList?v="+new Date()).query();
    }
    self.getProductTags = function(){
      return $resource("uploadFile/getProductTag").query();
    }
    self.sampling = function(sampleName,tagId,type){
      return $http({method:"POST",url:'sample/sampling',params:{"sampleName":sampleName,"tagId":tagId,"type":type}});
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
  celloudApp.service("scanStorageService", function($resource,$http){
    var self = this;
    self.sampleList = function(){
      return $resource("sample/getScanStorageSamples?v="+new Date()).get();
    }
    self.pageList = function(page,size){
      return $http.get("sample/getScanStorageSamples?v="+new Date(),{params: {page:page,size:size}});
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
      return $resource("sample/getTokenDnaSamples?v="+new Date()).get();
    }
    self.pageList = function(page,size){
      return $http.get("sample/getTokenDnaSamples?v="+new Date(),{params: {page:page,size:size}});
    }
    self.tokenDNA = function(sampleName){
      return $http({method:"POST",url:'sample/toTokenDNA',params:{"sampleName":sampleName}});
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
      return $http({method:"POST",url:'sample/addSampleToLibrary',params:{"sampleName":sampleName,"sindexs":sindexs}});
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
  });
})()