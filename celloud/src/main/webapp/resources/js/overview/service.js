(function(){
  celloudApp.factory("loginCount",function($resource){
      return $resource("count/loginCount");
  });
    
}());
