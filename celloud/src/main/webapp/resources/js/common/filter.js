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
  /**
   * 内容长度截取
   */
  celloudApp.filter("contextLengthFilter",function(){
    return function(context,length){
      if(context.length<=length){
        return context;
      }
      return context.substring(0,length) + "...";
    }
  });
  celloudApp.filter("getResultByCompare",function(){
	  return function(input, compare, r1, r2){
		  return input != compare ? r1 : r2;
	  }
  });
  /**
   * 文件大小格式化
   */
  celloudApp.filter("fileSizeFormat",function(){
    return function(size){
      if(size==0){
        return 0;
      }else if(size<1024&&size>0){
        return size + "B";
      }else if(size<1048576&&size>=1024){
        temp = size/1024;
        return temp.toFixed(2) + "KB";
      }else if(size<1073741824&&size>=1048576){
        temp = size/1048576;
        return temp.toFixed(2) + "MB";
      }else if(size>=1073741824){
        temp = size/1073741824;
        return temp.toFixed(2) + "GB";
      }
      return "文件大小异常";
    }
  });
  /**
   * 将内容以HTML方式展示
   */
  celloudApp.filter('trustHtml', ['$sce', function ($sce) {
    return function (text) {
      return $sce.trustAsHtml(text);
    };
  }]);
  
  celloudApp.filter("taskPeriodFilter",function(){
    return function(period){
      if(period == 0){
        return "等待分析";
      }else if(period == 1){
        return "分析中";
      }else if(period == 2){
        return "完成";
      }else if(period == 3){
        return "数据不完整";
      }else if(period == 4){
        return "异常终止";
      }else if(period == 5){
        return "送样中";
      }else if(period == 6){
        return "实验中";
      }
      return "";
    }
  });
})();
