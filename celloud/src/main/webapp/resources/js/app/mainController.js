(function(){
  celloudApp.controller("appClassifyCtrl",['$rootScope','AppService',function($rootScope, AppService){
    if($rootScope.classifys == undefined){
      //APP分类
      AppService.classifys().success(function(data) {
        $rootScope.classifys = data;
      });
    }
  }]);
  celloudApp.controller("appMainCtrl",['$scope','AppService',function($scope, AppService){
    //获取精选APP
    $scope.getClassicApps = function(){
      AppService.classic().success(function(data) {
        $scope.classicApps = data;
      });
    }
    //获取推荐APP
    $scope.getRecommendApps = function(){
      AppService.recommend().success(function(data) {
        $scope.recommendApps = data;
      });
    }
    //获取分类APP列表
    $scope.getClassifysApps = function(){
      AppService.classifyApps().success(function(data) {
        $scope.classifysApps = data;
      });
    }
    //获取APP
    $scope.getApp = function(appId){
      AppService.updateAdd(appId)
      .then(
          function successCallback(res) {
            console.log("successCallback" + res.status +res.status==400);
            $scope.getClassifysApps();
            // 请求成功执行代码
          }, function errorCallback(res) {
            console.log("errorCallback" + res.status);
            if(res.status==400){
              $.tips("您还没有此应用的使用权限，请通过右侧“联系我们”获取!");
            }
          }
      );
    }
    $scope.getClassicApps();
    $scope.getRecommendApps();
    $scope.getClassifysApps();
    
    function init(){
      
    }
  }]);
})();