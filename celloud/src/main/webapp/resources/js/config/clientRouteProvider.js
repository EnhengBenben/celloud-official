(function(){
  celloudApp.config(function($routeProvider){
    $routeProvider
    .when('/',{
      templateUrl: "pages/client/overview.jsp"
    })
    .when('/base',{
      templateUrl: "pages/client/base.jsp"
    })
    .when('/pay',{
      templateUrl: "pages/client/pay.jsp"
    })
    .otherwise({redirectTo:'/'});
  });
})()