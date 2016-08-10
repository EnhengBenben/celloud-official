(function(){
  alert("dd---d");
  var celloudRounts = angular.module("celloudRounts",["ngRoute"]);
  celloudRounts.config(function($routeProvider){
    $routeProvider
    .when('/',{
      templateUrl: "pages/overview/overview.jsp"
    })
    .otherwise({redirectTo:'/'});
  });
})()