(function(){
  var dataControllers = angular.module("dataControllers",["ngRoute"]);
  
  dataControllers.controller("dataListController", function($scope, dataService){
    $scope.dataList = dataService.get();
  });
})();