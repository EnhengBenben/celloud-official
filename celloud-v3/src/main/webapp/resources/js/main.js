var intro;
var hasNavi;
$(function () {
  $.ajaxSetup ({
	  complete:function(request,textStatus){
		  var sessionstatus=request.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头，sessionstatus，  
		  if(sessionstatus=="timeout"){
			  jAlert("登录超时,请重新登录！","登录超时",function(){
				  window.location.href="login";
			  });
		  }
	  },
	  cache: false //关闭AJAX相应的缓存
  });
  userCount.showUserCount();
  hasNavi = $("#user-navigation-hide").val();
  if(hasNavi==1){
  	  intro = introJs();
  	  intro.setOption('tooltipPosition', 'auto');
  	  intro.setOption('positionPrecedence', ['left', 'right', 'bottom', 'top']);
  	  intro.setOption('showStepNumbers', false);
  	  intro.setOption('showButtons', false);
  	  intro.start();
  }
  
  /** 数据管理 */
  $("#to-data-main").on("click", function(e){
	 $.AdminLTE.closeSidebar();
	 $("#dataReportDIV").css("display","none");
	 $("#uploadDIV").css("display","none");
	 $("#mainDIV").css("display","");
	 $("#mainDIV").load("pages/data/data_main.jsp");
  });
});
/**
 * 总览
 */
// 路径配置
require.config({
    paths: {
        echarts: '//cdn.bootcss.com/echarts/2.2.7/'
    }
});
//
var userCount=(function(userCount){
	var self=userCount||{};
	
	self.lineModal=function(id,legend,xdata,ydata,yname,unit){
		require(
		    [
		        'echarts',
		        'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
		    ],
		    function (ec) {
		        // 基于准备好的dom，初始化echarts图表
		        var myChart = ec.init(document.getElementById(id)); 
		        
		        var option = {
		            tooltip: {
		                show: true
		            },
		            legend: {
		                data:[yname]
		            },
		            xAxis : [
		                {
		                    type : 'category',
		                    data : xdata
		                }
		            ],
		            yAxis : [
		                {
		                    type : 'value',
		                    axisLabel : {
		                        formatter: '{value}'+typeof(unit)==undefined?'':unit 
		                    },
		                }
		            ],
		            series : [
		                {
		                    "name":yname,
		                    "type":"line",
		                    "data":ydata,
		                    "markPoint" : {  
	                            data : [  
	                                {type : 'max', name: '最大值'},  
	                                {type : 'min', name: '最小值'}  
	                            ]  
	                        }
		                }
		            ]
		        };
		        // 为echarts对象加载数据 
		        myChart.setOption(option); 
		    }
		);
	};
	self.fileDayCount=function(){
		$.get("count/fileDayCount",function(data){
			var x = "[";
			var y = [];
			$.each(data,function(index,map){
				if (typeof(map.time) != "undefined") {
					x += "'" + map.time + "',";
					y[y.length]=Number(map.num);
				}
			})
			x = x.substring(0,x.length-1);
			x += "]";
			self.lineModal("count-data-day-chart","日上传文件统计",eval(x),y,"日上传文件数量");
		});
	};
	self.fileMonthCount=function(){
		$.get("count/fileMonthCount",function(data){
			$("#count-data-month-chart").width($("#count-data-day-chart").width());
			var x = "[";
			var y = [];
			$.each(data,function(index,map){
				if (typeof(map.time) != "undefined") {
				x += "'" + map.time + "',";
				y[y.length]=Number(map.num);
				}
			})
			x = x.length>1?x.substring(0,x.length-1):x;
			x += "]";
			if(x!="[]"){
				self.lineModal("count-data-month-chart","月上传文件统计",eval(x),y,"月上传文件数量");
				self.fileDayCount();
			}else{
				$("#count-data-charts").hide();
			}
		});
	};
	self.fileSizeDayCount=function(){
		$.get("count/fileSizeDayCount",function(data){
			var x = "[";
			var y = [];
			$.each(data,function(index,map){
				if (typeof(map.time) != "undefined") {
					x += "'" + map.time + "',";
					y[y.length]=Number(map.size/1073741824).toFixed(2);
				}
			})
			x = x.substring(0,x.length-1);
			x += "]";
			self.lineModal("count-source-day-chart","日资源占用量统计",eval(x),y,"日上传文件大小");
		});
	};
	self.fileSizeMonthCount=function(){
		$.get("count/fileSizeMonthCount",function(data){
			$("#count-source-month-chart").width($("#count-source-day-chart").width());
			var x = "[";
			var y = [];
			$.each(data,function(index,map){
				if (typeof(map.time) != "undefined") {
				x += "'" + map.time + "',";
				y[y.length]=Number(map.size/1073741824).toFixed(2);
				}
			})
			x = x.length>1?x.substring(0,x.length-1):x;
			x += "]";
			if(x!="[]"){
				self.lineModal("count-source-month-chart","月资源占用量统计",eval(x),y,"月上传文件大小");
				self.fileSizeDayCount();
			}else{
				$("#count-source-charts").hide();
			}
		});
	};

	self.reportDayCount=function(){
		$.get("count/reportDayCount",function(data){
			var x = "[";
			var y = [];
			$.each(data,function(index,map){
				if (typeof(map.time) != "undefined") {
					x += "'" + map.time + "',";
					y[y.length]=Number(map.size);
				}
			})
			x = x.substring(0,x.length-1);
			x += "]";
			self.lineModal("count-report-day-chart","日报告统计",eval(x),y,"日生成的报告个数");
		});
	};

	self.reportMonthCount=function(){
		$.get("count/reportMonthCount",function(data){
			$("#count-report-month-chart").width($("#count-report-day-chart").width());
			var x = "[";
			var y = [];
			$.each(data,function(index,map){
				if (typeof(map.time) != "undefined") {
				x += "'" + map.time + "',";
				y[y.length]=Number(map.size);
				}
			})
			x = x.length>1?x.substring(0,x.length-1):x;
			x += "]";
			if(x!="[]"){
				self.lineModal("count-report-month-chart","月报告统计",eval(x),y,"月生成的报告个数");
				self.reportDayCount();
			}else{
				$("#count-report-charts").hide();
			}
		});
	};

	self.appDayCount=function(){
		$.get("count/appDayCount",function(data){
			var x = "[";
			var y = [];
			$.each(data,function(index,map){
				if (typeof(map.time) != "undefined") {
					x += "'" + map.time + "',";
					y[y.length]=Number(map.num);
				}
			})
			x = x.substring(0,x.length-1);
			x += "]";
			self.lineModal("count-app-day-chart","日已添加APP运行次数",eval(x),y,"日APP运行次数");
		});
	};
	
	self.appMonthCount=function(){
		$.get("count/appMonthCount",function(data){
			$("#count-app-month-chart").width($("#count-app-day-chart").width());
			var x = "[";
			var y = [];
			$.each(data,function(index,map){
				if (typeof(map.time) != "undefined") {
				x += "'" + map.time + "',";
				y[y.length]=Number(map.num);
				}
			})
			x = x.length>1?x.substring(0,x.length-1):x;
			x += "]";
			if(x!="[]"){
				self.lineModal("count-app-month-chart","月已添加APP运行次数",eval(x),y,"月APP运行次数");
				self.appDayCount();
			}else{
				$("#count-app-charts").hide();
			}
		});
	};
	
	self.showUserCount=function(){
		$("#dataReportDIV").css("display","none");
		$("#uploadDIV").css("display","none");
		$("#mainDIV").css("display","");
		$.get("count/loginCount",function(response){
			$("#mainDIV").html(response);
			self.fileMonthCount();
			self.fileSizeMonthCount();
			self.reportMonthCount();
			self.appMonthCount();
		});
	};
	self.toDataMain=function(){
		$.AdminLTE.closeSidebar();
		$('.treeview').removeClass("active");
		$("#toDataMenu").addClass('active');
		 $("#dataReportDIV").css("display","none");
		 $("#uploadDIV").css("display","none");
		 $("#mainDIV").css("display","");
		 $("#mainDIV").load("pages/data/data_main.jsp");
	}
	self.toReportMain=function(){
		$('.treeview').removeClass("active");
		$("#toReportMenu").addClass('active');
		showReport();
	}
	self.toAppStore=function(){
		$('.treeview').removeClass("active");
		$("#toAppStoreMenu").addClass('active');
		showAppStore();
	}
	return self;
})(userCount);

/**
 * 数据上传
 */
function showUpload(){
	$("#uploadDIV").css("display","");
	$("#dataReportDIV").css("display","none");
	$("#mainDIV").css("display","none");
	if($("#uploadDIV").html()==""){
		$("#uploadDIV").load("pages/upload/upload.jsp");
	}
}

/**
 * 报告
 */
function showReport(){
	$.AdminLTE.closeSidebar();
	$("#dataReportDIV").css("display","none");
	$("#uploadDIV").css("display","none");
	$("#mainDIV").css("display","");
	if(intro != null && hasNavi == 1){
		intro.exit();
		intro = null;
		intro = introJs();
		intro.setOption('tooltipPosition', 'auto');
		intro.setOption('positionPrecedence', ['left', 'right', 'bottom', 'top']);
		intro.setOption('showStepNumbers', false);
		intro.setOption('showButtons', true);
		intro.setOptions({
			steps: [
			        { 
			        	step: 9,
			        	img: "endintro.png",
			        	intro: ""
			        }
			        ]
		});
		intro.start(9);
	}
	$("#mainDIV").load("pages/report/report_main.jsp");
}

/**
 * 应用市场
 */
function showAppStore(){
//	$.AdminLTE.closeSidebar();
	$("#dataReportDIV").css("display","none");
	$("#uploadDIV").css("display","none");
	$("#mainDIV").css("display","");
	$.get("app/toAppStore",{},function(responseText){
		$("#mainDIV").html(responseText);
	});
}

/**
 * 统计页面
 */
function showCount(){
	$.AdminLTE.closeSidebar();
	$("#dataReportDIV").css("display","none");
	$("#uploadDIV").css("display","none");
	$("#mainDIV").css("display","");
	$("#mainDIV").load("pages/count/count_main.jsp");
}

/**
 * 帐号管理
 */
function showUser(){
	$("#dataReportDIV").css("display","none");
	$("#uploadDIV").css("display","none");
	$("#mainDIV").css("display","");
	//为右上角个人信息链接过来增加active样式
	$('.treeview').removeClass("active");
    $("#accountManage").addClass('active');
	$("#mainDIV").load("user/info");
}
/**
 * 问题反馈(工单)
 */
function showFeedback(){
	$("#dataReportDIV").css("display","none");
	$("#uploadDIV").css("display","none");
	$("#mainDIV").css("display","");
	//为右上角个人信息链接过来增加active样式
	$('.treeview').removeClass("active");
    $("#feedbackManage").addClass('active');
	$("#mainDIV").load("feedback/main");
}
/**
 * 帮助
 */
function showHelp(){
	$("#dataReportDIV").css("display","none");
	$("#uploadDIV").css("display","none");
	$("#mainDIV").css("display","");
	$('.treeview').removeClass("active");
    $("#toHelpMenu").addClass('active');
	$("#mainDIV").load("pages/help/help_question.jsp");
}