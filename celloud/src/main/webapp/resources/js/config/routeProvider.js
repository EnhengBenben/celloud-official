 (function(){
  var celloudRounts = angular.module("celloudRounts",["ngRoute"]);
  celloudRounts.config(function($routeProvider){
    $routeProvider
    .when('/',{
      templateUrl: "pages/overview/overview.jsp"
    })
    .when('/user/base',{
      templateUrl: "pages/user/user_base.jsp"
    })
    .when('/user/pwd',{
      templateUrl: "pages/user/user_pwdreset.jsp"
    })
    .when('/user/log',{
      templateUrl: "pages/user/user_log.jsp"
    })
    .when('/user/report',{
      templateUrl: "pages/user/user_reportset.jsp"
    })
    .when('/data',{
      templateUrl: "pages/data/data_list.jsp",
      controller: "dataListController"
    })
    .otherwise({redirectTo:'/'});
  });
})()