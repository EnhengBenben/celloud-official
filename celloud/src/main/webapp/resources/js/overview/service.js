(function(){
    var overviewService = angular.module("overviewService",["ngResource"]);
    
    overviewService.factory("loginCount",function($resource){
      return $resource("count/loginCount");
    });
    
}());
