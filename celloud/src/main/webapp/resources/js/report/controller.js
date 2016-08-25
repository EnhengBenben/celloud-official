(function(){
  function viewDataReport(){
    
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
    //分页检索主方法
    $scope.pageQuery = function(currentPage,pageSize){
      projectReportService.getReportListCondition(currentPage,pageSize).
        success(function(dataList){
          $scope.dataList = dataList;
        });
      
    }
    //切换每页显示记录数
    $scope.pageList = function(pageSize){
      $rootScope.pageSize = pageSize;
      $scope.pageQuery(1,pageSize);
      $location.path($scope.pageType);
    }
    //切换分页
    if($routeParams.page == null){
      $scope.pageQuery(1,$rootScope.pageSize);
      $location.path($scope.pageType);
    }else{
      $scope.pageQuery($routeParams.page,$rootScope.pageSize);
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
      alert(projectId);
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
              param = {"fileName":$.trim($(this).html()),"dataKey":$.trim($(this).prev().html()),"softwareId":appId,"softwareName":appName,"userId":userId,"obj":$(this),"proId":proId,"proName":proName};
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
                  $(this).find("span").bind("click",param,viewDataReport);
                  $(this).find("span").addClass("link");
                }
              }
            }
            if(appId=="128"||appId=="127"||appId=="126"||appId=="118"||appId=="117"||appId=="114"||appId=="113"||appId=="112"||appId=="111"||appId=="110"||appId=="109"||appId=="106"||appId=="107"||appId=="108"||appId=="105"||appId=="82"||appId=="84"||appId=="89"||appId=="73"||appId=="1"){
              param = {"fileName":$.trim($(this).html()),"dataKey":$.trim($(this).prev().html()),"softwareId":appId,"softwareName":appName,"userId":userId,"obj":$(this),"proId":proId,"proName":proName};
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
                $(this).find("span").bind("click",param,viewDataReport);
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
                param = {"fileName":$.trim($(this).html()),"dataKey":$.trim($(this).next().html()),"softwareId":appId,"softwareName":appName,"userId":userId,"obj":$(this),"proId":proId,"proName":proName};
                $(this).addClass("sub");
                var fileName = $(this).html();
                if(fileName.length>30){
                  fileName = fileName.substring(0,30) + "...";
                }
                $(this).html("<span id='dataSpan"+proId+$(this).next().html()+"'>"+$(this).next().html()+" （"+fileName+"）</span>");
                $(this).find("span").bind("click",param,viewDataReport);
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
                param = {"fileName":$.trim($(this).html()),"dataKey":$.trim($(this).prev().html()),"softwareId":appId,"softwareName":appName,"userId":userId,"obj":$(this),"proId":proId,"proName":proName};
                if(j>0&&i==1){
                  $(this).addClass("sub");
                  var fileName = $(this).html();
                  if(fileName.length>30){
                    fileName = fileName.substring(0,30) + "...";
                  }
                  $(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+$(this).prev().html()+" （"+fileName+"）</span>");
                  $(this).find("span").bind("click",param,viewDataReport);
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
              param = {"fileName":$.trim($(this).html()),"projectId":proId,"softwareId":appId,"softwareName":appName,"userId":userId,"obj":$(this),"proId":proId,"proName":proName,"dataKey":$(this).prev().html()};
              if(j>0&&i==1){
                $(this).addClass("sub");
                var fileName = $(this).html();
                if(fileName.length>30){
                  fileName = fileName.substring(0,30) + "...";
                }
                $(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+$(this).prev().html()+" （"+fileName+"）</span>");
                $(this).find("span").bind("click",param,viewDataReport);
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
})();
