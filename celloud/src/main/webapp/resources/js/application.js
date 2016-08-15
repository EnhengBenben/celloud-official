(function(){
  var celloudApp = angular.module("celloudApp",["celloudRounts","commonControllers", "dataControllers","dataService"]);
  celloudApp.config(function($routeProvider, $controllerProvider, $compileProvider, $filterProvider, $provide){
      celloudApp.controllerProvider = $controllerProvider;
      celloudApp.compileProvider    = $compileProvider;
      celloudApp.routeProvider      = $routeProvider;
      celloudApp.filterProvider     = $filterProvider;
      celloudApp.provide            = $provide;
 
    });

}());