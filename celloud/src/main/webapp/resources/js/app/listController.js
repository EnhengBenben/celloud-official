(function(){
  celloudApp.controller("appListCtrl",['$scope','$routeParams','AppService',function($scope, $routeParams, AppService){
    
    //分页检索
    $scope.getApp = getApp;
    $scope.pageQuery = pageQuery;
    $scope.getAppsByCid = getAppsByCid;
    return init();
    
    function init(){
      AppService.classifysByPid($routeParams.cid).success(function(data) {
        $scope.sclassifys = data;
        $scope.rootClassifyId = $routeParams.cid;
        if(data.length>0){
          $scope.nowCid = data[0].classifyId;
          $scope.nowCName = data[0].classifyName;
          $scope.currentPage = 1;
          $scope.pageSize = 10;
          getAppsByCid();
        }
      });
    }
    function getAppsByCid(id,name){
      if(id != undefined){
        $scope.nowCid = id;
        $scope.nowCName = name;
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
    //获取APP授权
    function getApp(appId){
      AppService.updateAdd(appId)
      .then(
          function successCallback(res) {
            init();
            // 请求成功执行代码
          }, function errorCallback(res) {
            console.log("errorCallback" + res.status);
            if(res.status==400){
              $.tips("您还没有此应用的使用权限，请通过右侧“联系我们”获取!");
            }
          }
      );
    }
  }]);
})();