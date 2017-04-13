(function(){
  celloudApp.controller("appListCtrl",['$scope','$routeParams','AppService',function($scope, $routeParams, AppService){
    
    //分页检索
    $scope.pageQuery = pageQuery;
    $scope.getAppsByCid = getAppsByCid;
    return init();
    
    function init(){
      AppService.classifysByPid($routeParams.cid).success(function(data) {
        $scope.sclassifys = data;
        if(data.length>0){
          $scope.nowCid = data[0].classifyId;
          $scope.nowCName = data[0].classifyName;
          $scope.currentPage = 1;
          $scope.pageSize = 10;
          getAppsByCid();
        }
      });
    }
    function getAppsByCid(id){
      if(id != undefined){
        $scope.nowCid = id;
        $scope.currentPage = 1;
        $scope.pageSize = 10;
      }
      AppService.classifyAppsByCid($scope.nowCid,$scope.currentPage,$scope.pageSize).success(function(data) {
        $scope.appPageInfo = data;
      });
      $("#app-classify-"+id).parent().parent().find(".active").removeClass("active");
      $("#app-classify-"+id).addClass("active");
    }
    function pageQuery(currentPage,pageSize){
      $scope.currentPage = currentPage;
      $scope.pageSize = pageSize;
      getAppsByCid();
    }
  }]);
})();