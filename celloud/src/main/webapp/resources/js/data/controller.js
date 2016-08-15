(function(){
  var dataControllers = angular.module("dataControllers",["ngRoute"]);
  
  dataControllers.controller("dataListController", function($scope, dataPageListService){
    $scope.dataList = dataPageListService.get();
  });
  dataControllers.controller("dataPageController", function($scope, dataByPageService){
    $scope.dataList = dataByPageService.get({page:2});
  });
})();