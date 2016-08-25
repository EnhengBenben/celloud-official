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
    .when('/user/log/p:page',{
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
    .when('/count',{
      templateUrl: "pages/count/count_main.jsp"
    })
    .when('/data',{
      templateUrl: "pages/data/data_list.jsp",
      controller: "dataListController"
    })
    .when('/data/p:page',{
      templateUrl: "pages/data/data_list.jsp",
      controller: "dataListController"
    })
    .when('/expense/consume',{
      templateUrl: "pages/expense/expense_consume.jsp",
      controller: "pageQueryConsume"
    })
    .when('/expense/consume/p:page',{
      templateUrl: "pages/expense/expense_consume.jsp",
      controller: "pageQueryConsume"
    })
    .when('/expense/paydetail',{
      templateUrl: "pages/expense/expense_pay.jsp",
      controller: "toRecharge"
    })
    .when('/expense/paylist',{
      templateUrl: "pages/expense/expense_paylist.jsp",
      controller: "pageQueryRecharge"
    })
    .when('/expense/paylist/p:page',{
      templateUrl: "pages/expense/expense_paylist.jsp",
      controller: "pageQueryRecharge"
    })
    .when('/expense/invoice',{
      templateUrl: "pages/expense/expense_invoicelist.jsp",
      controller: "pageQueryInvoice"
    })
    .when('/expense/invoice/p:page',{
      templateUrl: "pages/expense/expense_invoicelist.jsp",
      controller: "pageQueryInvoice"
    })
    .when('/feedback',{
      templateUrl: "pages/feedback/feedback_main.jsp",
      controller:"feedbackController"
    })
    .when('/notices',{
      templateUrl: "pages/notice/notices.jsp",
      controller:"noticeController"
    })
    .when('/messages',{
      templateUrl: "pages/notice/messages.jsp",
      controller:"messageController"
    })
    .when('/message/setting',{
      templateUrl: "pages/notice/notice_set.jsp"
    })
    .when('/reportdata',{
      templateUrl: "pages/report/report_data.jsp"
    })
    .when('/reportpro',{
      templateUrl: "pages/report/report_project.jsp",
      controller: "projectReportController"
    })
    .when('/reportpro/p:page',{
      templateUrl: "pages/report/report_project.jsp",
      controller: "projectReportController"
    })
    .when('/app',{
      templateUrl: "pages/app/app_main.jsp",
      controller: "toAppStore"
    })
    .otherwise({redirectTo:'/'});
  });
})()