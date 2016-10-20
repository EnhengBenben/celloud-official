(function(){
  celloudApp.service("loginCount",function($http){
    var self = this;
    self.loginCount = function(){
      return $http.get("count/loginCount");
    }
  });
    
}());
