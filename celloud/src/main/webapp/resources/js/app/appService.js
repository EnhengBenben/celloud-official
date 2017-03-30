/**
 * app service
 */
(function(){
  celloudApp.service("AppService",["$http",function($http){
    //获取精选APP
    this.classic = function(){
      return $http({method:"GET",url:"app/classic"});
    }
    //获取推荐APP
    this.recommend = function(){
      return $http({method:"GET",url:"app/recommend"});
    }
    this.classifys = function(){
      return $http({method:"GET",url:"app/classifys"});
    };
  }]);
})();