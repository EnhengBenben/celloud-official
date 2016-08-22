(function(){
  celloudApp.controller("dataListController", function($scope, runService){
    $scope.dataList = runService.list();
    $scope.runWithProject = function() {
      runService.run().success(function(response) {
        if(response.length==0){
          alert("run success");
          $scope.dataList = runService.list();
          $.dataManager.options.checkedIds = new Array();
          $.dataManager.editBtn.update();
          $.dataManager.cleanCheckbox("demo-checkbox1");
        }else{
          alert("run error");
        }
      });
    };
  });
  celloudApp.controller("dataPageController", function($scope, dataByPageService){
    $scope.dataList = dataByPageService.get({page:2});
  });
})();