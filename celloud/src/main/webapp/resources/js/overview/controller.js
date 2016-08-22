(function(){
  celloudApp.controller("overviewCount", function($scope, loginCount){
    $scope.map = loginCount.get();
  });
  
  celloudApp.controller("fileDayCount", function(){
    userCount.fileDayCount();
  });
  
  celloudApp.controller("fileMonthCount", function(){
    userCount.fileMonthCount();
  });
  
  celloudApp.controller("fileSizeDayCount", function(){
    userCount.fileSizeDayCount();
  });
  
  celloudApp.controller("fileSizeMonthCount", function(){
    userCount.fileSizeMonthCount();
  });
  
  celloudApp.controller("reportDayCount", function(){
    userCount.reportDayCount();
  });
  
  celloudApp.controller("reportMonthCount", function(){
    userCount.reportMonthCount();
  });
  
  celloudApp.controller("appDayCount", function(){
    userCount.appDayCount();
  });
  
  celloudApp.controller("appMonthCount", function(){
    userCount.appMonthCount();
  });
})();
