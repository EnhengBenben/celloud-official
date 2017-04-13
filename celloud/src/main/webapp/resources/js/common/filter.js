(function(){
  celloudApp.filter("collapsedFilter",function(){
    return function(collapsed){
      return collapsed? "collapsed":"";
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
  celloudApp.filter("chevronTypeTextFilter", ['$translate', function($translate){
    return function(chevronType){
      return chevronType ? $translate.instant("REPORT.MORE"):$translate.instant("REPORT.PACK_UP");
    }
  }]);
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
   * BSI获取跨几行
   */
  celloudApp.filter("getBsiRowSpan",function(){
	 return function(val1, val2, val3){
		 var val0 = 1;
  	     if(val1 != null && val1 != ''){
  	  	   val0++;
  	     }
  	     if(val2 != null && val2 != ''){
  	  	   val0++;
  	     }
  	     if(val3 != null && val3 != ''){
  	  	   val0++;
  	     }
  	     return val0;
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
  
  celloudApp.filter("taskPeriodFilter",['$translate', function($translate){
    return function(period){
      if(typeof period == "string" && period.indexOf(",")>=0){
        var p = period.split(",");
        var result = "";
        for(var i=0; i<p.length; i++){
          period=p[i];
          if(period == 0){
            result += $translate.instant("TASK.WAIT_RUN")+"    ";
          }else if(period == 1){
            result += $translate.instant("TASK.RUNNING")+"    ";
          }else if(period == 2){
            result += $translate.instant("TASK.COMPLETE")+"    ";
          }else if(period == 3){
            result += $translate.instant("TASK.INCOMPLETE_DATA")+"    ";
          }else if(period == 4){
            result += $translate.instant("TASK.ERROR")+"    ";
          }else if(period == 5){
            result += $translate.instant("TASK.SAMPING")+"    ";
          }else if(period == 6){
            result += $translate.instant("TASK.EXPERIMENT")+"    ";
          }
        }
        return result; 
      }else{
        if(period == 0){
          return $translate.instant("TASK.WAIT_RUN");
        }else if(period == 1){
          return $translate.instant("TASK.RUNNING");
        }else if(period == 2){
          return $translate.instant("TASK.COMPLETE");
        }else if(period == 3){
          return $translate.instant("TASK.INCOMPLETE_DATA");
        }else if(period == 4){
          return $translate.instant("TASK.ERROR");
        }else if(period == 5){
          return $translate.instant("TASK.SAMPING");
        }else if(period == 6){
          return $translate.instant("TASK.EXPERIMENT");
        }
        return ""; 
      }
    }
  }]);
  celloudApp.filter("experStateFilter",function(){
    return function(experState){
      if(experState == 0){
        return "已采样";
      }else if(experState == 1){
        return "入库";
      }else if(experState == 2){
        return "提DNA";
      }else if(experState == 3){
        return "建库中";
      }else if(experState == 4){
        return "等待上机";
      }else if(experState == 5){
        return "上机完成";
      }
    }
  });
  //报告已读样式过滤器
  celloudApp.filter("reportReadFilter",function(){
    return function(read){
       return read == 1? "read" : "";
    }
  });
  //查看的报告样式
  celloudApp.filter("thisReportFilter",function(){
    return function(thisReport){
      return thisReport ? "this-report" : "";
    }
  });
  
  //应用市场评分
  celloudApp.filter("scoreFilter",function(){
    return function(score,scoreType){
      var result = "score-full";
      if(score>scoreType-1 && score<scoreType){
        result = "score-half";
      }else if(score<scoreType){
        result = "score-null";
      }
      return result;
    }
  });
})();
