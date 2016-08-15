(function(){
  var commonControllers = angular.module("commonControllers",["ngRoute"]);
  commonControllers.controller("sidebarController", function($scope, $location){
    $scope.isActive = function (viewLocation) { 
      if(viewLocation != "/"){
        return $location.path().indexOf(viewLocation) >=0;
      }
      return  viewLocation===$location.path();
    };
  });
})();