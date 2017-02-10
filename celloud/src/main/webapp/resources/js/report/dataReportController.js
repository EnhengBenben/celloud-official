(function(){
  celloudApp.controller("mibReportController",function($scope,$routeParams,mibReportService){
    mibReportService.reportInfo($routeParams.dataKey,$routeParams.proId,$routeParams.appId)
      .success(function(mibInfo){
        $scope.mib = mibInfo.mib;
        console.log(eval(mibInfo.genusDistributionInfo));
        if(mibInfo.readsDistributionInfo != undefined){
          $.reportChar.draw.circularGraph("reads-distribution-char","Reads","Distribution",mibInfo.readsDistributionInfo)
        }
        if(mibInfo.familyDistributionInfo != undefined){
          $.reportChar.draw.circularGraph("family-distribution-char","Family","Distribution",mibInfo.familyDistributionInfo);
        }
        if(mibInfo.genusDistributionInfo != undefined){
          $.reportChar.draw.singleBar("genus-distribution-char","Top 10 genus distribution","",mibInfo.genusDistributionInfo,"Depth","Depth");
        }
      });
  });
  celloudApp.controller("ngsReportController",function($scope,$routeParams,mibReportService){
    mibReportService.reportInfo($routeParams.dataKey,$routeParams.proId,$routeParams.appId)
      .success(function(mibInfo){
        $scope.mib = mibInfo.mib;
        console.log(eval(mibInfo.genusDistributionInfo));
        if(mibInfo.readsDistributionInfo != undefined){
          $.reportChar.draw.circularGraph("reads-distribution-char","Reads","Distribution",mibInfo.readsDistributionInfo)
        }
        if(mibInfo.familyDistributionInfo != undefined){
          $.reportChar.draw.circularGraph("family-distribution-char","Family","Distribution",mibInfo.familyDistributionInfo);
        }
        if(mibInfo.genusDistributionInfo != undefined){
          $.reportChar.draw.singleBar("genus-distribution-char","Top 10 genus distribution","",mibInfo.genusDistributionInfo,"Depth","Depth");
        }
      });
  });
  
})()