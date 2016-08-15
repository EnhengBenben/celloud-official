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
    .when('/data/:page',{
      templateUrl: "pages/data/data_list.jsp",
      controller: "dataPageController"
    })
    
    .when('/expense/consume',{
      templateUrl: "pages/expense/expense_consume.jsp",
      controller: "dataListController"
    })
    .when('/expense/paydetail',{
      templateUrl: "pages/expense/expense_pay.jsp",
      controller: "dataPageController"
    })
    .when('/expense/paylist',{
      templateUrl: "pages/expense/expense_paylist.jsp",
      controller: "dataPageController"
    })
    .when('/expense/invoice',{
      templateUrl: "pages/expense/expense_invoicelist.jsp",
      controller: "dataPageController"
    })
    .otherwise({redirectTo:'/'});
  });
})()