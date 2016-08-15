(function(){
    var dataService = angular.module("dataService",["ngResource"]);
    
    dataService.factory("dataPageListService",function($resource){
      return $resource("data/dataPageList");
    });
    
    dataService.factory("dataByPageService",function($resource){
      return $resource("data/dataPageList?page=:page");
    });
    
}());