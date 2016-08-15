(function(){
    var dataService = angular.module("dataService",["ngResource"]);
    
    dataService.factory("dataService",function($resource){
      return $resource("data/dataPageList");
    });
    
}());