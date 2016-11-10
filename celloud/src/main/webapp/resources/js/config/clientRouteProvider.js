(function(){
  celloudApp.config(function($routeProvider){
    $routeProvider
    .when('/',{
      templateUrl: "pages/client/overview.jsp"
    })
    .when('/base',{
      templateUrl: "pages/client/base.jsp",
      controller: "clientBaseInfo"
    })
    .when('/pay',{
      templateUrl: "pages/client/pay.jsp",
      controller: "toRecharge"
    })
    .when('/report',{
      templateUrl: "pages/client/report.jsp"
    })
    .otherwise({redirectTo:'/'});
  });
})()