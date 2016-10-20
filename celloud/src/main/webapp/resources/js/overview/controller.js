(function(){
  celloudApp.controller("overviewCount", function($scope, loginCount){
    var width = ($(window).width()-300)/2;
    var height = width/2.5;
    $(".count-box-body").width(width);
    $(".count-box-body").height(height);
    $("body").scrollTop(0);
    loginCount.loginCount().success(function(response){
      $scope.map = response;
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
