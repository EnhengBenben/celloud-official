(function(){
  celloudApp.controller("scanStorageController", function($scope, scanStorageService){
    $scope.sampleList = scanStorageService.sampleList();
  });
})()