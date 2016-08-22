(function(){
  celloudApp.filter("collapsedFilter",function(){
    return function(collapsed){
      return collapsed ? "collapsed":"";
    }
  });
  celloudApp.filter("logoMiniFilter",function(){
    return function(collapsed){
      return collapsed ? "mini":"";
    }
  });
  celloudApp.filter("collapsedIconFilter",function(){
    return function(collapsed){
      return collapsed ? "right-indent-icon":"left-indent-icon";
    }
  });
  celloudApp.filter("proSidebarLeftFilter",function(){
    return function(collapsed){
      return collapsed ? "":"large-left";
    }
  });
  celloudApp.filter("chevronTypeFaFilter",function(){
    return function(chevronType){
      return chevronType ? "fa fa-chevron-down":"fa fa-chevron-up";
    }
  });
  celloudApp.filter("chevronTypeTextFilter",function(){
    return function(chevronType){
      return chevronType ? "更多":"收起";
    }
  });
  celloudApp.filter("chevronTypeDivFilter",function(){
    return function(chevronType){
      return chevronType ? "":"more";
    }
  });
})();
