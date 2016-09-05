(function(){
  function viewDataReport(userId,dataKey,fileName,appId,appName,proId,proName,obj){
	  window.location.href = "#/reportpro/"+ appName + "/" + appId + "/" + dataKey + "/" + proId;
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
  /**
   * egfr数据报告controller
   */
  celloudApp.controller("egfrDataReportController", function($scope, $routeParams, dataReportService){
	  dataReportService.getDataReportInfo("report/getEGFRInfo",$routeParams.dataKey,$routeParams.projectId,$routeParams.appId).
	  success(function(egfrInfo){
		  $scope.egfr = egfrInfo.egfr;
		  $scope.project = egfrInfo.project;
		  $scope.uploadPath = egfrInfo.uploadPath;
		  // 数据参数同比
		  var length = $scope.egfr.pos;
		  if(length==0 || isNaN(length)){
			  $("#charDiv").html("<p style=\"color: red;\">数据异常，没有同比结果</p>");
		  }else{  
			  $.get("count/egfrCompare",{"length":length},function(data){
			      var div = $("<div id='char0' class='col-lg-6' style='width: 1000px;height:400px;'></div>");
				  $("#charDiv").append(div);
				  var X = "[";
				  var Y = "[";
				  var value = data.split("\n");
				  if(value.length > 1){
					  for(var k=0;k<value.length-1;k++){
						  var n = value[k].split("\t");
						  X+="'"+n[0]+"',";
						  Y+=n[1]+",";
					  }
				  }else{
					  var n = data.split("\t");
					  X+="'"+n[0]+"',";
					  Y+=n[1]+",";
				  }
				  X = X.substring(0,X.length-1)+"]";
				  Y = Y.substring(0,Y.length-1)+"]";
				  $.reportChar.draw.echartsShowBar("char0", "位点", eval(X), eval(Y), 0, 500, 300);
			  });
		  }
		  $scope.showHelp = function(){
			  $("#helpModal").modal("show");
		  }
	  });
  });
  /**
   * kras数据报告controller
   */
  celloudApp.controller("krasDataReportController", function($scope, $routeParams, dataReportService){
	  dataReportService.getDataReportInfo("report/getKRASInfo",$routeParams.dataKey,$routeParams.projectId,$routeParams.appId).
	  success(function(krasInfo){
		  $scope.kras = krasInfo.kras;
		  $scope.project = krasInfo.project;
		  $scope.uploadPath = krasInfo.uploadPath;
		  // 数据参数同比
		  var length = $scope.kras.pos;
		  if(length==0 || isNaN(length)){
			  $("#charDiv").html("<p style=\"color: red;\">数据异常，没有同比结果</p>");
		  }else{  
			  $.get("count/krasCompare",{"length":length},function(data){
				  var div = $("<div id='char0' class='col-lg-6' style='width: 1000px;height:400px;'></div>");
				  $("#charDiv").append(div);
				  var X = "[";
				  var Y = "[";
				  var value = data.split("\n");
				  if(value.length > 1){
					  for(var k=0;k<value.length-1;k++){
						  var n = value[k].split("\t");
						  X+="'"+n[0]+"',";
						  Y+=n[1]+",";
					  }
				  }else{
					  var n = data.split("\t");
					  X+="'"+n[0]+"',";
					  Y+=n[1]+",";
				  }
					  X = X.substring(0,X.length-1)+"]";
					  Y = Y.substring(0,Y.length-1)+"]";
					  $.reportChar.draw.echartsShowBar("char0", "位点", eval(X), eval(Y), 0, 500, 300);
		    });
		  }
		  $scope.showHelp = function(){
			  $("#helpModal").modal("show");
		  }
	  });
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
      $("#showPname"+projectId).addClass("hide");
      $("#changePname"+projectId).removeClass("hide");
    }
    //修改项目名称
    $scope.changePname = function(projectId){
      var name = $("#updatePname"+projectId).val();
      var oldName = $("#pnameSpan"+projectId).text().trim();
      //名称修改才进行后台提交，未修改则不提交后台
      if(name==oldName){
          $("#showPname"+projectId).removeClass("hide");
          $("#changePname"+projectId).addClass("hide");
          return ;
      }
      projectReportService.changeProjectName(projectId,name).success(function(response){
        if(response.success){
          if(name.length>13){
            name=name.substring(0,12)+"...";
          }
          $("#pnameSpan"+projectId).html(name);
          $("#showPname"+projectId).removeClass("hide");
          $("#changePname"+projectId).addClass("hide");
        }
        $scope.message = response.message;
        $scope.state = true;
      });
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
                  viewDataReport(userId,$.trim($(this).parent().prev().html()),$.trim($(this).html()),appId,appName,proId,proName,$(this));
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
  
  celloudApp.controller("dataReportController", function($scope,projectReportService,dataReportService){
    $scope.searchInfo = dataReportService.getSearchInfos();
    $scope.reportList = dataReportService.getReports();
    var options = {
        page : 1,
        pageSize : 20,
        beginDate : null,
        endDate : null,
        tagId : null,
        period : null,
        batch : null,
        condition : null,
        sort: 'desc'
    };
    var paramQuqery = function(){
      dataReportService.getReportsByParams(options.page,options.pageSize,options.condition,options.beginDate,options.endDate,options.batch,options.tagId,options.period,options.sort)
      .success(function(dataList){
        $scope.reportList = dataList;
      });
    }
    $scope.pageQuery = function(page,pageSize){
      options.page = page;
      options.pageSize = pageSize;
      paramQuqery();
    }
    $scope.tagsQuery = function(tagId){
      options.tagId = tagId;
      options.page = 1;
      paramQuqery();
    }
    $scope.batchsQuery = function(batch){
      options.batch = batch;
      options.page = 1;
      paramQuqery();
    }
    $scope.periodQuery = function(period){
      options.period = period;
      options.page = 1;
      paramQuqery();
    }
    $scope.fullDateQuery = function(days){
      if(days==0){
        options.beginDate = null;
        options.endDate = null;
      }else{
        var d = new Date();
        var date = new Date(d.getTime()-1000*60*60*24*days);
        var year = date.getFullYear(); 
        var mounth = date.getMonth()+1;
        var day = date.getDate(); 
        options.beginDate = year+"-"+mounth+"-"+day+" 00:00:00";
        options.endDate = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate()+" 23:59:59";
      }
      options.page = 1;
      paramQuqery();
    }
    $scope.chooseDate = function(){
      var d = new Date();
      var begin = $("#begin-date").val();
      var end = $("#end-date").val();
      end = end ? end : d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
      if(!begin){
        $.tips("请选择开始时间！","时间错误");
        return;
      }
      if(begin > end){
        $.tips("请确认开始时间  < 结束时间！","时间错误");
        return;
      }
      options.beginDate = begin + " 00:00:00";
      options.endDate = end + " 23:59:59";
      options.page = 1;
      paramQuqery();
    }
  });
})();
