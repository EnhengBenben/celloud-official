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
    .when('/user/set',{
      templateUrl: "pages/user/user_reportset.jsp",
      controller: "setReportController"
    })
    .when('/count',{
      templateUrl: "pages/count/count_main.jsp"
    })
    .when('/data',{
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
      templateUrl: "pages/notice/notice_set.jsp",
      controller:"settingController"
    })
    .when('/reportdata/:page/:pageSize/:fullDate/:beginDate/:endDate/:tagId/:period/:batch/:condition',{
      templateUrl: "pages/report/report_data.jsp",
      controller: "dataReportController"
    })
    .when('/reportpro/:page/:pageSize/:belongs/:changeDate/:start/:end/:app/:condition',{
      templateUrl: "pages/report/report_project.jsp",
      controller: "projectReportController"
    })
    .when('/reportpro/EGFR/:appId/:dataKey/:projectId',{
      templateUrl: "pages/report/report_data_egfr.jsp",
      controller: "egfrDataReportController"
    })
    .when('/reportpro/KRAS/:appId/:dataKey/:projectId',{
      templateUrl: "pages/report/report_data_kras.jsp",
      controller: "krasDataReportController"
    })
    .when('/reportpro/HCV_Genotype/:appId/:dataKey/:projectId',{
	  templateUrl: "pages/report/report_data_hcv.jsp",
	  controller: "hcvDataReportController"
    })
    .when('/reportpro/BRAF/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_braf.jsp",
    	controller: "brafDataReportController"
    })
    .when('/reportpro/TB-Rifampicin/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_tbrifampicin.jsp",
    	controller: "tbRifampicinDataReportController"
    })
    .when('/reportpro/TB-INH/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_tbinh.jsp",
    	controller: "tbinhDataReportController"
    })
    .when('/reportpro/HBV_SNP2/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_hbv.jsp",
    	controller: "hbvDataReportController"
    })
    .when('/reportpro/oncogene/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_oncogene.jsp",
    	controller: "oncogeneDataReportController"
    })
    .when('/reportpro/DPD/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_dpd.jsp",
    	controller: "dpdDataReportController"
    })
    .when('/reportpro/ABI_NJ/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_abinj.jsp",
    	controller: "abinjDataReportController"
    })
    .when('/reportpro/UGT/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_ugt.jsp",
    	controller: "ugtDataReportController"
    })
    .when('/reportpro/16S/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_16s.jsp",
    	controller: "16sDataReportController"
    })
    .when('/reportpro/translate_simplified/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_translate.jsp",
    	controller: "translateDataReportController"
    })
    .when('/reportpro/PGS/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_pgs.jsp",
    	controller: "pgsDataReportController"
    })
    .when('/reportpro/GDD/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_gdd.jsp",
    	controller: "gddDataReportController"
    })
    .when('/reportpro/AccuSeqΩ/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_gdd.jsp",
    	controller: "gddDataReportController"
    })
    .when('/reportpro/CMP/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_cmp.jsp",
    	controller: "cmpDataReportController"
    })
    .when('/reportpro/AccuSeqα/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_cmp.jsp",
    	controller: "cmpDataReportController"
    })
    .when('/reportpro/AccuSeqα2/:appId/:dataKey/:projectId',{
      templateUrl: "pages/report/report_data_accuseqα2.jsp",
      controller: "accuseqα2DataReportController"
    })
    .when('/reportpro/CMP_199/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_cmp.jsp",
    	controller: "cmpDataReportController"
    })
    .when('/reportpro/AccuSeqα199/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_cmp.jsp",
    	controller: "cmpDataReportController"
    })
    .when('/reportdata/split/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_split.jsp",
    	controller: "splitDataReportController"
    })
    .when('/reportdata/BSI/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_bsi.jsp",
    	controller: "bsiDataReportController"
    })
    .when('/reportdata/rocky/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_rocky.jsp",
    	controller: "rockyDataReportController"
    })
    .when('/app',{
      templateUrl: "pages/app/app_main.jsp",
      controller: "toAppStore"
    })
    .when('/app/detail/:pid',{
      templateUrl: "pages/app/app_detail_main.jsp",
      controller: "toAppDetail"
    })
    .when('/reportdata/bsi',{
      templateUrl: "pages/report/report_data_bsi.jsp"
    })
    .when('/reportdata/MIB/:appId/:dataKey/:proId',{
      templateUrl: "pages/report/report_data_mib.jsp",
      controller: "mibReportController"
    })
    .when('/reportpro/MIB/:appId/:dataKey/:proId',{
      templateUrl: "pages/report/report_data_mib.jsp",
      controller: "mibReportController"
    })
    .when('/sampling',{
      templateUrl: "pages/experiment_scan/sampling.jsp",
      controller: "samplingController"
    })
    .when('/sampling/order/:orderId',{
      templateUrl: "pages/experiment_scan/order_detail.jsp",
      controller: "sampleOrderController"
    })
    .when('/experiment/scanStorage',{
      templateUrl: "pages/experiment_scan/scan_storage.jsp",
      controller: "scanStorageController"
    })
    .when('/experiment/tokenDNA',{
      templateUrl: "pages/experiment_scan/token_dna.jsp",
      controller: "tokenDNAController"
    })
    .when('/experiment/createLibrary',{
      templateUrl: "pages/experiment_scan/create_library.jsp",
      controller: "buidLibraryController"
    })
    .when('/experiment/libraryList',{
      templateUrl: "pages/experiment_scan/library_list.jsp",
      controller: "storagesController"
    })
    .when('/experiment/libraryList/:ssId',{
      templateUrl: "pages/experiment_scan/library_detail.jsp",
      controller: "storagesController"
    })
    .when('/product/rocky/upload',{
      templateUrl: "pages/rocky/rocky_upload.jsp",
      controller: "rockyUploadController"
    })
    .when('/product/rocky/report',{
      templateUrl: "pages/rocky/rocky_report.jsp",
      controller: "rockyReportController"
    })
    .when('/product/bsi/bsireport',{
      templateUrl: "pages/bsi/bsi_report_list.jsp",
      controller: "bsiReportController"
    })
    .when('/product/bsi/bsidata',{
      templateUrl: "pages/bsi/bsi_data.jsp",
      controller: "bsiDataController"
    })
    .when('/company/user',{
	  templateUrl: "pages/company/company_user.jsp",
      controller: "companyUserController"
    })
    .when('/company/base',{
	  templateUrl: "pages/company/company_base.jsp",
      controller: "companyBaseController"
    })
    .otherwise({redirectTo:'/'});
  });
})()