(function(){
  var dataControllers = angular.module("dataControllers",["ngRoute"]);
  
  dataControllers.controller("dataListController", function($scope, runService){
    $scope.dataList = runService.list();
    $scope.runWithProject = function() {
      runService.run().success(function(response) {
        if(response.length==0){
          alert("$http.get success");
          $scope.dataList = runService.list();
          $.dataManager.options.checkedIds = new Array();
          $.dataManager.editBtn.update();
        }else{
          alert("$http.get error");
        }
      });
    };
  });
  dataControllers.controller("dataPageController", function($scope, dataByPageService){
    $scope.dataList = dataByPageService.get({page:2});
  });
})();