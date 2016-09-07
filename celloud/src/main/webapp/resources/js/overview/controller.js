(function(){
  celloudApp.controller("overviewCount", function($scope, loginCount){
    loginCount.loginCount().success(function(response){
      $scope.map = response;
      var width = ($(window).width()-300)/2;
      var heitht = width/2.6;
      $(".count-box-body").width(width);
      $(".count-box-body").height(heitht);
      userCount.fileDayCount();
      userCount.fileMonthCount();
      userCount.fileSizeDayCount();
      userCount.fileSizeMonthCount();
      userCount.reportDayCount();
      userCount.reportMonthCount();
      userCount.appDayCount();
      userCount.appMonthCount();
    });
  });
})();
