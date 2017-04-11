/**
 * app service
 */
(function(){
  celloudApp.service("AppService",["$http",function($http){
    var self = this;
  
    //获取精选APP
    self.classic = function(){
      return $http({method:"GET",url:"app/classic"});
    }
    //获取推荐APP
    self.recommend = function(){
      return $http({method:"GET",url:"app/recommend"});
    }
    self.classifyApps = function(){
      return $http({method:"GET",url:"app/classifys"});
    }
    self.classifys = function(){
      return $http({method:"GET",url:"classifys"});
    };
    self.classifysByPid = function(pid){
      return $http({method:"GET",url:"classifys",params:{"pid":pid}});
    };
    self.updateAdd = function(appId){
      return $http({method:"PUT",url:"app/addOrRemoveApp",params:{"appId":appId}});
    }
    self.classifyAppsByCid = function(cid,currentPage,pageSize){
      return $http({method:"GET",url:"app/classifys/"+cid,params:{"currentPage":currentPage,"pageSize":pageSize}});
    }
    self.appDetail = function(id){
      return $http({method:"GET",url:"app/"+id});
    }
    self.appComments = function(id,currentPage,pageSize){
      return $http({method:"GET",url:"appComments",params:{"appId":id,"currentPage":currentPage,"pageSize":pageSize}});
    }
    self.updateComment = function(id,score,comment){
      return $http({method:"POST",url:"appComments",params:{"appId":id,"score":score,"comment":comment}});
    }
  }]);
})();