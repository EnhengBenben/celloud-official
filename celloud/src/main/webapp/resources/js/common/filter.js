(function(){
  var commonFilters = angular.module("commonFilters",[]);
  commonFilters.filter("collapsedFilter",function(){
    return function(collapsed){
      return collapsed ? "collapsed":"";
    }
  });
  commonFilters.filter("logoMiniFilter",function(){
    return function(collapsed){
      return collapsed ? "mini":"";
    }
  });
  commonFilters.filter("collapsedIconFilter",function(){
    return function(collapsed){
      return collapsed ? "right-indent-icon":"left-indent-icon";
    }
  });
  commonFilters.filter("proSidebarLeftFilter",function(){
    return function(collapsed){
      return collapsed ? "":"large-left";
    }
  });
})();
