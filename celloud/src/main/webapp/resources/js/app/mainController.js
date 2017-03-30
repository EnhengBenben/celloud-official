(function(){
  celloudApp.controller("appMainCtrl",['$scope','AppService',function($scope, AppService){
    AppService.classic.then(function(res) {
      $scope.classicApps = res.data;//获取精选APP
    });
    AppService.recommend.then(function(res) {
      $scope.recommendApps = res.data;//获取推荐APP
    });
    AppService.recommend.then(function(res) {
      $scope.classifysApps = res.data;
    });
  }]);
})();