(function(){
  var overview = angular.module("overviewApp",["ngRoute"]);
  
  overview.controller("overviewCount", function($scope, loginCount){
    $scope.map = loginCount.get();
  });
})();
