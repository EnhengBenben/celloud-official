 (function(){
  celloudApp.config(function($routeProvider){
    $routeProvider
    .when('/',{
      templateUrl: "pages/overview/overview.jsp"
    })
    .when('/user/base',{
      templateUrl: "pages/user/user_base.jsp",
      controller: "updateBaseInfo"
    })
    .when('/user/pwd',{
      templateUrl: "pages/user/user_pwdreset.jsp",
      controller: "updatePassword"
    })
    .when('/user/log',{
      templateUrl: "pages/user/user_log.jsp",
      controller: "pageQueryLog"
    })
    .when('/user/report',{
      templateUrl: "pages/user/user_reportset.jsp"
    })
    .when('/user/email',{
      templateUrl: "pages/user/user_emailreset.jsp",
      controller: "updateEmail"
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
      controller: "pageQueryConsume"
    })
    .when('/expense/paydetail',{
      templateUrl: "pages/expense/expense_pay.jsp"
    })
    .when('/expense/paylist',{
      templateUrl: "pages/expense/expense_paylist.jsp",
      controller: "pageQueryRecharge"
    })
    .when('/expense/invoice',{
      templateUrl: "pages/expense/expense_invoicelist.jsp",
      controller: "pageQueryInvoice"
    })
    .when('/qa',{
      templateUrl: "pages/qa/qa_list.jsp"
    })
    .when('/notice/list',{
      templateUrl: "pages/notice/notice_list.jsp"
    })
    .when('/notice/system',{
      templateUrl: "pages/notice/notice_system.jsp"
    })
    .when('/notice/set',{
      templateUrl: "pages/notice/notice_set.jsp"
    })
    .when('/reportdata',{
      templateUrl: "pages/report/report_data.jsp"
    })
    .when('/reportpro',{
      templateUrl: "pages/report/report_project.jsp"
    })
    .when('/app',{
      templateUrl: "pages/app/app_main.jsp"
    })
    .otherwise({redirectTo:'/'});
  });
})()