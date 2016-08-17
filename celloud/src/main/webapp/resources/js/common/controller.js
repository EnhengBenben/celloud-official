(function(){
  var commonControllers = angular.module("commonControllers",["ngRoute"]);
  commonControllers.controller("sidebarController", function($scope, $location, $rootScope){
    $scope.isActive = function (viewLocation) { 
      if(viewLocation != "/"){
        return $location.path().indexOf(viewLocation) >=0;
      }
      return  viewLocation===$location.path();
    };
    $rootScope.collapsed = false;
    $scope.toggleCollapse = function(){
      if($scope.collapsed){
        $scope.collapsed = false;
      }else{
        $scope.collapsed = true;
      }
    };
  });
  
  commonControllers.controller("collapseSidebarController", function($scope){
    
  });
})();