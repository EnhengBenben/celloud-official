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
    .when('/user/pwd/reset/:cellphone',{
      templateUrl: "pages/user/user_pwdreset.jsp",
      controller: "updatePwdAndRegister"
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
    .when('/reportdata/EGFR/:appId/:dataKey/:projectId',{
      templateUrl: "pages/report/data_report_data_egfr.jsp",
      controller: "dataEgfrDataReportController"
    })
    .when('/reportpro/KRAS/:appId/:dataKey/:projectId',{
      templateUrl: "pages/report/report_data_kras.jsp",
      controller: "krasDataReportController"
    })
    .when('/reportdata/KRAS/:appId/:dataKey/:projectId',{
      templateUrl: "pages/report/data_report_data_kras.jsp",
      controller: "dataKrasDataReportController"
    })
    .when('/reportpro/HCV_Genotype/:appId/:dataKey/:projectId',{
	  templateUrl: "pages/report/report_data_hcv.jsp",
	  controller: "hcvDataReportController"
    })
    .when('/reportdata/HCV_Genotype/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/data_report_data_hcv.jsp",
    	controller: "dataHcvDataReportController"
    })
    .when('/reportpro/BRAF/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_braf.jsp",
    	controller: "brafDataReportController"
    })
    .when('/reportdata/BRAF/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/data_report_data_braf.jsp",
    	controller: "dataBrafDataReportController"
    })
    .when('/reportpro/TB-Rifampicin/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_tbrifampicin.jsp",
    	controller: "tbRifampicinDataReportController"
    })
    .when('/reportdata/TB-Rifampicin/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/data_report_data_tbrifampicin.jsp",
    	controller: "dataTbRifampicinDataReportController"
    })
    .when('/reportpro/TB-INH/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_tbinh.jsp",
    	controller: "tbinhDataReportController"
    })
    .when('/reportdata/TB-INH/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/data_report_data_tbinh.jsp",
    	controller: "dataTbinhDataReportController"
    })
    .when('/reportpro/HBV_SNP2/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_hbv.jsp",
    	controller: "hbvDataReportController"
    })
    .when('/reportdata/HBV_SNP2/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/data_report_data_hbv.jsp",
    	controller: "dataHbvDataReportController"
    })
    .when('/reportpro/oncogene/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_oncogene.jsp",
    	controller: "oncogeneDataReportController"
    })
    .when('/reportdata/oncogene/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/data_report_data_oncogene.jsp",
    	controller: "dataOncogeneDataReportController"
    })
    .when('/reportpro/DPD/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_dpd.jsp",
    	controller: "dpdDataReportController"
    })
    .when('/reportdata/DPD/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/data_report_data_dpd.jsp",
    	controller: "dataDpdDataReportController"
    })
    .when('/reportpro/ABI_NJ/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_abinj.jsp",
    	controller: "abinjDataReportController"
    })
    .when('/reportdata/ABI_NJ/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/data_report_data_abinj.jsp",
    	controller: "dataAbinjDataReportController"
    })
    .when('/reportpro/UGT/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_ugt.jsp",
    	controller: "ugtDataReportController"
    })
    .when('/reportdata/UGT/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/data_report_data_ugt.jsp",
    	controller: "dataUgtDataReportController"
    })
    .when('/reportpro/16S/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_16s.jsp",
    	controller: "16sDataReportController"
    })
    .when('/reportdata/16S/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/data_report_data_16s.jsp",
    	controller: "data16sDataReportController"
    })
    .when('/reportpro/translate_simplified/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_translate.jsp",
    	controller: "translateDataReportController"
    })
    .when('/reportdata/translate_simplified/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/data_report_data_translate.jsp",
    	controller: "dataTranslateDataReportController"
    })
    .when('/reportpro/PGS/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_pgs.jsp",
    	controller: "pgsDataReportController"
    })
    .when('/reportdata/PGS/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/data_report_data_pgs.jsp",
    	controller: "dataPgsDataReportController"
    })
    .when('/reportpro/GDD/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_gdd.jsp",
    	controller: "gddDataReportController"
    })
    .when('/reportdata/GDD/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/data_report_data_gdd.jsp",
    	controller: "dataGddDataReportController"
    })
    .when('/reportpro/AccuSeqΩ/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_gdd.jsp",
    	controller: "gddDataReportController"
    })
    .when('/reportdata/AccuSeqΩ/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/data_report_data_gdd.jsp",
    	controller: "dataGddDataReportController"
    })
    .when('/reportpro/CMP/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_cmp.jsp",
    	controller: "cmpDataReportController"
    })
    .when('/reportdata/CMP/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/data_report_data_cmp.jsp",
    	controller: "dataCmpDataReportController"
    })
    .when('/reportpro/AccuSeqα/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_cmp.jsp",
    	controller: "cmpDataReportController"
    })
    .when('/reportdata/AccuSeqα/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/data_report_data_cmp.jsp",
    	controller: "dataCmpDataReportController"
    })
    .when('/reportpro/AccuSeqα2/:appId/:dataKey/:projectId',{
      templateUrl: "pages/report/report_data_accuseqα2.jsp",
      controller: "accuseqa2DataReportController"
    })
    .when('/reportdata/AccuSeqα2/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/data_report_data_accuseqα2.jsp",
    	controller: "dataAccuseqa2DataReportController"
    })
    .when('/reportpro/CMP_199/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_cmp.jsp",
    	controller: "cmpDataReportController"
    })
    .when('/reportdata/CMP_199/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/data_report_data_cmp.jsp",
    	controller: "dataCmpDataReportController"
    })
    .when('/reportpro/AccuSeqα199/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_cmp.jsp",
    	controller: "cmpDataReportController"
    })
    .when('/reportdata/AccuSeqα199/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/data_report_data_cmp.jsp",
    	controller: "dataCmpDataReportController"
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
    .when('/reportdata/FS-ocg/:appId/:dataKey/:projectId',{
    	templateUrl: "pages/report/report_data_fsocg.jsp",
    	controller: "fsocgDataReportController"
    })
    .when('/app',{
      templateUrl: "pages/app/main.jsp",
      controller: "appMainCtrl"
    })
    .when('/app/detail/:pid',{
      templateUrl: "pages/app/app_detail_main.jsp",
      controller: "toAppDetail"
    })
    .when('/reportdata/bsi',{
      templateUrl: "pages/report/report_data_bsi.jsp"
    })
    .when('/reportdata/Sanger/:appId/:dataKey/:proId',{
      templateUrl: "pages/report/report_data_sanger.jsp",
      controller: "sangerDataReportController"
    })
    .when('/reportdata/Ngs/:appId/:dataKey/:proId',{
      templateUrl: "pages/report/report_data_ngs.jsp",
      controller: "ngsReportController"
    })
    .when('/reportpro/MIB/:appId/:dataKey/:proId',{
      templateUrl: "pages/report/report_data_mib.jsp",
      controller: "mibReportController"
    })
    .when('/reportdata/MIB/:appId/:dataKey/:proId',{
      templateUrl: "pages/report/data_report_data_mib.jsp",
      controller: "dataMibReportController"
    })
    .when('/sampling/collection',{
      templateUrl: "pages/experiment_scan/sampling.jsp",
      controller: "samplingController"
    })
    .when('/sampling/info-collection',{
      templateUrl: "pages/experiment_scan/sampling_info.jsp",
      controller: "samplingInfoController"
    })
    .when('/sampling/order/:orderId',{
      templateUrl: "pages/experiment_scan/order_detail.jsp",
      controller: "sampleOrderController"
    })
    .when('/samplinginfo/order/:orderId',{
      templateUrl: "pages/experiment_scan/order_info_detail.jsp",
      controller: "sampleInfoOrderController"
    })
    .when('/sampling/tracking',{
      templateUrl: "pages/experiment_scan/sample_tracking.jsp",
      controller: "sampleTrackingController"
    })
    .when('/sampling/info-tracking',{
      templateUrl: "pages/experiment_scan/sample_info_tracking.jsp",
      controller: "sampleInfoTrackingController"
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
    .when('/product/rocky/d',{
    	templateUrl: "pages/rocky/rocky_data.jsp",
    	controller: "rockyDataController"
    })
    .when('/product/rocky/report',{
      templateUrl: "pages/rocky/rocky_report.jsp",
      controller: "rockyReportController"
    })
    .when('/product/bactive/report/:appId',{
      templateUrl: "pages/bsi/bsi_report_list.jsp",
      controller: "bsiReportController"
    })
    .when('/product/bactive/report/:appId/:page/:size/:begin/:end/:period/:batch/:condition/:sort/:sortBatch/:sortName/:sortPeriod/:sortDate',{
      templateUrl: "pages/bsi/bsi_report_list.jsp",
      controller: "bsiReportController"
    })
    .when('/product/bactive/dat/:appId',{
      templateUrl: "pages/bsi/bsi_data.jsp",
      controller: "bsiDataController"
    })
    .when('/product/bactive/rdata/:dataKey/:projectId/:appId/:batch/:dataIndex',{
      templateUrl: "pages/bsi/bsi_report_data.jsp",
      controller: "bsiReportDataController"
    })
    .when('/company/user',{
	  templateUrl: "pages/company/company_user.jsp",
      controller: "companyUserController"
    })
    .when('/company/base',{
	  templateUrl: "pages/company/company_base.jsp",
      controller: "companyBaseController"
    })
    .when('/company/key',{
	  templateUrl: "pages/company/company_key.jsp",
      controller: "companyKeyController"
    })
    .otherwise({redirectTo:'/'});
  });
})()