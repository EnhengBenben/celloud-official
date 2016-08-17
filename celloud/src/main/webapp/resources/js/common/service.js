(function(){
  var commonServices = angular.module("commonServices",["ngRoute"]);
  
  commonServices.factory("tipsModalService", function(){
    $("#tips-modal").modal("show");
  });
}());