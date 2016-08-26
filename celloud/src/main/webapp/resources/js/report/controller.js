(function(){
  function viewDataReport(userId,dataKey,fileName,appId,appName,proId,proName,obj){
  }
  celloudApp.directive('loadOver', function ($timeout) {
    return {
        restrict: 'A',
        link: function(scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function() {
                    scope.$emit('reportLoadOver');
                });
            }
        }
    };
  });
  celloudApp.controller("projectReportController", function($scope,$rootScope,$routeParams,$location,projectReportService){
    //加载产品标签
    $scope.ranAppList = projectReportService.getRanAPP();
    //初始化参数
    var options = {
        page : 1,
        pageSize : 10,
        belongs : 1,
        start : null,
        end : null,
        app : 0,
        condition : null
    };
    //分页检索主方法
    $scope.pageQuery = function(currentPage,pageSize){
      var belongs = options.belongs;
      var start = options.start;
      var end = options.end;
      var app = options.app;
      var condition = options.condition;
      options.pageSize = pageSize;
      projectReportService.getReportListCondition(currentPage,pageSize,belongs,start,end,app,condition).
        success(function(dataList){
          $scope.dataList = dataList;
        });
    }
    $scope.pageQuery(options.page,options.pageSize);
    //切换所属
    $scope.changeBelongs = function(id){
      $(".belongs").removeClass("active");
      $("#belongs"+id).addClass("active");
      options.belongs = id;
      $scope.pageQuery(1,options.pageSize);
    }
    var START = null;
    var END = null;
    //设定时间检索
    $scope.changeDate = function(flag){
      $("#_searchDate").val("");
      $("#_endDate").val("");
      $(".changeDate").removeClass("active");
      $("#changeDate"+flag).addClass("active");
      if(flag==0){
        START = null;
        END = null;
      }else{
        var d = new Date();
        var date = new Date(d.getTime()-1000*60*60*24*flag);
        var year = date.getFullYear();    //获取完整的年份(4位,1970-????)
        var mounth = date.getMonth()+1;       //获取当前月份(0-11,0代表1月)
        var day = date.getDate();        //获取当前日(1-31)
        START = year+"-"+mounth+"-"+day;
        END = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
      }
      $scope.dateQuery();
    }
    //自定义时间检索
    $scope.chooseDate = function(){
      $(".changeDate").removeClass("active");
      START =$("#_searchDate").val();
      END = $("#_endDate").val();
      if((!START && END)||(START && !END)){
        $("#_alertSpan").css("display","");
        $("#_alertSpan").html("请同时选择起始时间和结束时间");
        alert("请同时选择起始时间和结束时间");
        return ;
      }
      if(START>END){
        $("#_alertSpan").css("display","");
        $("#_alertSpan").html("起始日期不能大于结束日期");
        alert("起始日期不能大于结束日期");
        return ;
      }
      $scope.dateQuery();
    }
    //时间检索入口
    $scope.dateQuery = function(){
      if(START != null){
        START = START+" 00:00:00";
      }
      if(END != null){
        END = END+" 23:59:59";
      }
      options.start = START;
      options.end = END;
      $scope.pageQuery(1,options.pageSize);
    }
    //根据appId精确检索
    $scope.changeApp = function(appId){
      $(".changeApp").removeClass("active");
      $("#changeApp" + appId).addClass("active");
      options.app = appId;
      $scope.pageQuery(1,options.pageSize);
    }
    //数据检索
    $scope.changeCondition = function(){
      options.condition = $scope.reportCondition;
      $scope.pageQuery(1,options.pageSize);
    }
    //显示项目名称编辑框
    $scope.toChangePname = function(projectId){
      alert(projectId);
    }
    //修改项目名称
    $scope.changePname = function(projectId){
      alert(projectId);
    }
    //取消共享来的项目
    $scope.cancelProjectShare = function(projectId,userId){
      alert(projectId);
    }
    //下载PDF
    $scope.downPDF = function(userId,appId,projectId){
      alert(projectId);
    }
    //打开共享窗口
    $scope.toShareModal = function(projectId,projectName,dataNum){
      alert(projectId);
    }
    //打开已共享窗口
    $scope.shareModal = function(projectId,userId,projectName,dataNum){
      alert(projectId);
    }
    //删除
    $scope.removePro = function(projectId){
      if(confirm("确定删除该项目报告吗？")){
        $.get("project/deleteByState",{"projectId":projectId},function(flag){
          if(flag ==1){
            $scope.pageQuery(1,options.pageSize);
          }else{
            alert("项目报告删除失败");
          }
        });
      }
    }
    
    $scope.$on('reportLoadOver', function(){
      $("#_show").find(".projectContext").each(function(){
        var info = $.trim($(this).prev().html()).split(",");
        var appId = info[0];
        var appName = info[1];
        var proId = info[2];
        var userId = info[3];
        var proName = $("#pnameSpan" + proId).html();
        var length = 0;
        var th_size = 0;
        var td_size = 0;
        var tr_size = $(this).find("tr").length;
        $(this).find("table").addClass("table table-main info-table");
        $(this).find("tr").each(function(j){
          if(j==0){
            th_size = $(this).children().length;
          }else{
            td_size = $(this).children().length;
          }
          var trc = $(this).children().length;
          if(appId=="80"){
            if(j==0){
              length = $(this).children().length;
            }
            if(length>6){
              if(j>1&&j%2==0){
                $(this).addClass("_HCVTR");
                $(this).css("display","none");
                $(this).children().attr("colspan","7");
                $(this).children().css("word-break","break-all");
              }
            }
          }
          $(this).mouseover(function(){
            $(this).find("span").addClass("link_hover");
          });
          $(this).mouseout(function(){
            $(this).find("span").removeClass("link_hover");
          });
          $(this).find("td").each(function(i){
            if(appId=="90"){
              if(i!=1){
                if(trc==4){
                  if(i==0){
                    $(this).addClass("hide");
                  }
                }else{
                  $(this).addClass("hide");
                }
              }else{
                $(this).css("border-left","none");
                if(j>0){
                  $(this).addClass("sub");
                  var fileName = $(this).html();
                  if(fileName.length>30){
                    fileName = fileName.substring(0,30) + "...";
                  }
                  $(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+$(this).prev().html()+" （"+fileName+"）</span>");
                  $(this).find("span").bind("click",function(){
                    viewDataReport(userId,$.trim($(this).prev().html()),$.trim($(this).html()),appId,appName,proId,proName,$(this));
                  });
                  $(this).find("span").addClass("link");
                }
              }
            }
            if(appId=="128"||appId=="127"||appId=="126"||appId=="118"||appId=="117"||appId=="114"||appId=="113"||appId=="112"||appId=="111"||appId=="110"||appId=="109"||appId=="106"||appId=="107"||appId=="108"||appId=="105"||appId=="82"||appId=="84"||appId=="89"||appId=="73"||appId=="1"){
              if(j>0&&i==1){
                $(this).addClass("sub");
                var fileName = $(this).html();
                if(fileName.length>30&&appId!="113"&&appId!="112"&&appId!="111"&&appId!="110"&&appId!="126"&&appId!="127"&&appId!="128"){
                  fileName = fileName.substring(0,30) + "...";
                }
                if(appId!="114"&&appId!="118"){
                  $(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+$(this).prev().html()+" （"+fileName+"）</span>");
                }else{
                  $(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+fileName+"</span>");
                }
                $(this).find("span").bind("click",function(){
                  viewDataReport(userId,$.trim($(this).prev().html()),$.trim($(this).html()),appId,appName,proId,proName,$(this));
                });
                $(this).find("span").addClass("link");
              }
              if(i==0){
                $(this).addClass("hide");
              }
              if(i==1){
                $(this).css("border-left","none");
              }
              if(appId=="82"){
                if($(this).html()=="不敏感"){
                  $(this).addClass("red");
                }
              }
            }
            if(appId=="91"||appId=="95"||appId=="92"||appId=="93"||appId=="94"||appId=="86"||appId=="87"||appId=="88"||appId=="81"||appId=="83"||appId=="85"||appId=="96"||appId=="97"||appId=="98"||appId=="99"||appId=="100"||appId=="101"||appId=="102"||appId=="103"||appId=="104"||appId=="116"||appId=="119"||appId=="120"||appId=="121"||appId=="122"||appId=="124"||appId=="125"){
              if(j>0&&i==0){
                $(this).addClass("sub");
                var fileName = $(this).html();
                if(fileName.length>30){
                  fileName = fileName.substring(0,30) + "...";
                }
                $(this).html("<span id='dataSpan"+proId+$(this).next().html()+"'>"+$(this).next().html()+" （"+fileName+"）</span>");
                $(this).find("span").bind("click",function(){
                  viewDataReport(userId,$.trim($(this).next().html()),$.trim($(this).html()),appId,appName,proId,proName,$(this));
                });
                $(this).find("span").addClass("link");
              }
              if(i==1){
                $(this).addClass("hide");
              }
              if(j>0&&(i==4||i==3)){
                var val = $(this).html();
                if(!isNaN(val)&&val>999){
                  $(this).html($.format(val, 3, ','));
                }
              }
            }
            if(appId=="80"){
              if(length==5){
                if(j>0&&i==1){
                  $(this).addClass("sub");
                  var fileName = $(this).html();
                  if(fileName.length>30){
                    fileName = fileName.substring(0,30) + "...";
                  }
                  $(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+$(this).prev().html()+" （"+fileName+"）</span>");
                  $(this).find("span").bind("click",function(){
                    viewDataReport(userId,$.trim($(this).prev().html()),$.trim($(this).html()),appId,appName,proId,proName,$(this));
                  });
                  $(this).find("span").addClass("link");
                }
                if(i==0){
                  $(this).addClass("hide");
                }
                if(i==1){
                  $(this).css("border-left","none");
                }
                if($(this).html()=="No_Alignment"||$(this).html()=="Low_Quality"
                  ||$(this).html()=="No Alignment"||$(this).html()=="Low Quality"){
                  $(this).attr("colspan","2");
                }
              }
              if(length==7||length==8){
                if($(this).html()=="No_Alignment"||$(this).html()=="Low_Quality"
                  ||$(this).html()=="No Alignment"||$(this).html()=="Low Quality"){
                  $(this).attr("colspan","5");
                }
              }
              if(length==7){
                if(j>0&&j%2==1&&i==0){
                  $(this).addClass("sub");
                  $(this).html("<span id='dataSpan"+proId+$(this).html()+"'>"+$(this).html()+"</span>");
                  $(this).find("span").bind("click",viewNext);
                  $(this).find("span").addClass("link");
                }
              }else if(length==8){
                if(i==1){
                  $(this).css("border-left","none");
                }
                if((j==0||j%2==1)&&i==0){
                  $(this).addClass("hide");
                }
                if(j>0&&j%2==1&&i==1){
                  $(this).addClass("sub");
                  var fileName = $(this).html();
                  if(fileName.length>30){
                    fileName = fileName.substring(0,30) + "...";
                  }
                  $(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+$(this).prev().html()+" （"+fileName+"）</span>");
                  $(this).find("span").bind("click",viewNext);
                  $(this).find("span").addClass("link");
                }
              }
            }
            if(appId=="11"){
              if(j>0&&i==1){
                $(this).addClass("sub");
                var fileName = $(this).html();
                if(fileName.length>30){
                  fileName = fileName.substring(0,30) + "...";
                }
                $(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+$(this).prev().html()+" （"+fileName+"）</span>");
                $(this).find("span").bind("click",function(){
                  viewDataReport(userId,$.trim($(this).prev().html()),$.trim($(this).html()),appId,appName,proId,proName,$(this));
                });
                $(this).find("span").addClass("link");
              }
              if(i==0){
                $(this).addClass("hide");
              }
              if(i==1){
                $(this).css("border-left","none");
              }
            }
            
            if(td_size<th_size&&i==td_size-1){
              $(this).attr("colspan",th_size - td_size+1);
            }
            
          });
        });
        var minTdNum = 5;
        if(appId=="128"||appId=="127"||appId=="126"||appId=="113"||appId=="112"||appId=="111"||appId=="110"){
          minTdNum = 4;
        }
        var rdataNum = $("#rdataNum"+proId).html();
        if((appId=="114"||appId=="118") && tr_size-1<rdataNum){
          var num = Number(rdataNum)-tr_size+1;
          var height = 30*(5-tr_size);
          var adHtml = "<tr><td colspan='"+th_size+"' style='border-left-style: none;vertical-align: middle;height:"+height+"px' align='center'><img src='/celloud/images/report/running.png' title='正在运行...'/><br>"+num+"个数据正在运行...</td></tr>";
          $(this).find("tbody").append(adHtml);
        }else if(tr_size<minTdNum){
          var num = 5-tr_size;
          for(i=0;i<num;i++){
            var adHtml = "<tr>";
            for(j=0;j<th_size-1;j++){
              adHtml+="<td></td>";
            }
            adHtml+="</tr>";
            $(this).find("tbody").append(adHtml);
          }
        }
      });
    });
  });
  
  celloudApp.controller("dataReportController", function($scope,projectReportService){
    $scope.ranAppList = projectReportService.getRanAPP();
  });
})();
