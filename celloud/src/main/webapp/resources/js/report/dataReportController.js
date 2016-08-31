(function(){
  celloudApp.controller("mibReportController",function($scope,mibReportService){
    var mibInfo = mibReportService.reportInfo();
    $scope.mib = mibInfo.mib;
  });
})()