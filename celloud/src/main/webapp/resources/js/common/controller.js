(function(){
  var celloudControoler = angular.module("celloudControoler",["ngRoute"]);
  celloudControoler.controller("commonSidebarController", function($scope, $location){
    $scope.isActive = function (viewLocation) {
      var active = (viewLocation === $location.path());
      return active;
    };
  });
})()