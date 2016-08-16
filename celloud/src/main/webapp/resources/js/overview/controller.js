(function(){
  var overview = angular.module("overviewApp",["ngRoute"]);
  
  overview.controller("overviewCount", function($scope, loginCount){
    $scope.map = loginCount.get();
  });
  
  overview.controller("fileDayCount", function(){
    userCount.fileDayCount();
  });
  
  overview.controller("fileMonthCount", function(){
    userCount.fileMonthCount();
  });
  
  overview.controller("fileSizeDayCount", function(){
    userCount.fileSizeDayCount();
  });
  
  overview.controller("fileSizeMonthCount", function(){
    userCount.fileSizeMonthCount();
  });
  
  overview.controller("reportDayCount", function(){
    userCount.reportDayCount();
  });
  
  overview.controller("reportMonthCount", function(){
    userCount.reportMonthCount();
  });
  
  overview.controller("appDayCount", function(){
    userCount.appDayCount();
  });
  
  overview.controller("appMonthCount", function(){
    userCount.appMonthCount();
  });
})();
