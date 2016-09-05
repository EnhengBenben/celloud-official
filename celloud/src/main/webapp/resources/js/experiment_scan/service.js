(function(){
  celloudApp.service("scanStorageService", function($resource, $http){
    var self = this;
    self.sampleList = function(){
      return $resource("sample/getScanStorageSamples").get();
    }
    self.pageList = function(page,size){
      return $http.get("sample/getScanStorageSamples",{params: {page:page,size:size}});
    }
  });
})()