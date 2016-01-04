var intro;
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
  var hasNavi = $("#user-navigation-hide").val();
  if(hasNavi==1){
  	  intro = introJs();
  	  intro.setOption('tooltipPosition', 'auto');
  	  intro.setOption('positionPrecedence', ['left', 'right', 'bottom', 'top']);
  	  intro.setOption('showStepNumbers', false);
  	  intro.setOption('showButtons', false);
  	  intro.start();
  }
  $("#to-data-main").on('click', function(e){
      alert('click event');
  });
});
/**
 * 总览
 */
// 路径配置
require.config({
    paths: {
        echarts: 'http://echarts.baidu.com/build/dist'
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
		$.get("count/fileDayCount.action",function(data){
			var x = "[";
			var y = [];
			$.each(data,function(index,map){
					x += "'" + map.time + "',";
					y[y.length]=Number(map.num);
			})
			x = x.substring(0,x.length-1);
			x += "]";
			self.lineModal("count-data-day-chart","日上传文件统计",eval(x),y,"日上传文件数量");
		});
	};
	self.fileMonthCount=function(){
		$.get("count/fileMonthCount.action",function(data){
			var x = "[";
			var y = [];
			$.each(data,function(index,map){
				x += "'" + map.time + "',";
				y[y.length]=Number(map.num);
			})
			x = x.substring(0,x.length-1);
			x += "]";
			self.lineModal("count-data-month-chart","月上传文件统计",eval(x),y,"月上传文件数量");
			self.fileDayCount();
		});
	};
	self.fileSizeDayCount=function(){
		$.get("count/fileSizeDayCount.action",function(data){
			var x = "[";
			var y = [];
			$.each(data,function(index,map){
					x += "'" + map.time + "',";
					y[y.length]=Number(map.size/1073741824).toFixed(3);
			})
			x = x.substring(0,x.length-1);
			x += "]";
			self.lineModal("count-source-day-chart","日上传文件统计",eval(x),y,"日资源占用量");
		});
	};
	self.fileSizeMonthCount=function(){
		$.get("count/fileSizeMonthCount.action",function(data){
			var x = "[";
			var y = [];
			$.each(data,function(index,map){
				x += "'" + map.time + "',";
				y[y.length]=Number(map.size/1073741824).toFixed(3);
			})
			x = x.substring(0,x.length-1);
			x += "]";
			self.lineModal("count-source-month-chart","月上传文件统计",eval(x),y,"月资源占用量");
			self.fileSizeDayCount();
		});
	};

	self.reportDayCount=function(){
		$.get("count/reportDayCount.action",function(data){
			var x = "[";
			var y = [];
			$.each(data,function(index,map){
					x += "'" + map.time + "',";
					y[y.length]=Number(map.size);
			})
			x = x.substring(0,x.length-1);
			x += "]";
			self.lineModal("count-report-day-chart","日上传文件统计",eval(x),y,"日资源占用量");
		});
	};

	self.reportMonthCount=function(){
		$.get("count/reportMonthCount.action",function(data){
			var x = "[";
			var y = [];
			$.each(data,function(index,map){
				x += "'" + map.time + "',";
				y[y.length]=Number(map.size);
			})
			x = x.substring(0,x.length-1);
			x += "]";
			self.lineModal("count-report-month-chart","月上传文件统计",eval(x),y,"月资源占用量");
			self.reportDayCount();
		});
	};

	self.appDayCount=function(){
		$.get("count/appDayCount.action",function(data){
			var x = "[";
			var y = [];
			$.each(data,function(index,map){
					x += "'" + map.time + "',";
					y[y.length]=Number(map.num);
			})
			x = x.substring(0,x.length-1);
			x += "]";
			self.lineModal("count-app-day-chart","日上传文件统计",eval(x),y,"日资源占用量");
		});
	};
	
	self.appMonthCount=function(){
		$.get("count/appMonthCount.action",function(data){
			var x = "[";
			var y = [];
			$.each(data,function(index,map){
				x += "'" + map.time + "',";
				y[y.length]=Number(map.num);
			})
			x = x.substring(0,x.length-1);
			x += "]";
			self.lineModal("count-app-month-chart","月上传文件统计",eval(x),y,"月资源占用量");
			self.appDayCount();
		});
	};
	
	self.showUserCount=function(){
		$("#uploadDIV").css("display","none");
		$("#mainDIV").css("display","");
		$.get("count/loginCount.action",function(response){
			$("#mainDIV").html(response);
			self.fileMonthCount();
			self.fileSizeMonthCount();
			self.reportMonthCount();
			self.appMonthCount();
		});
	};
	
	return self;
})(userCount);

/**
 * 数据上传
 */
function showUpload(){
	$("#uploadDIV").css("display","");
	$("#mainDIV").css("display","none");
	if($("#uploadDIV").html()==""){
		$("#uploadDIV").load("pages/data/file_upload.jsp");
	}
}

/**
 * 数据管理
 */
function showData(){
	$.AdminLTE.closeSidebar();
	$("#uploadDIV").css("display","none");
	$("#mainDIV").css("display","");
	$("#mainDIV").load("pages/data/data.jsp");
}

/**
 * 报告
 */
function showReport(){
	$.AdminLTE.closeSidebar();
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
	$("#uploadDIV").css("display","none");
	$("#mainDIV").css("display","");
	$.get("app3!toAppStore",{},function(responseText){
		$("#mainDIV").html(responseText);
	});
}

/**
 * 统计页面
 */
function showCount(){
	$.AdminLTE.closeSidebar();
	$("#uploadDIV").css("display","none");
	$("#mainDIV").css("display","");
	$("#mainDIV").load("pages/count/count.jsp");
}

/**
 * 帐号管理
 */
function showUser(){
	$("#uploadDIV").css("display","none");
	$("#mainDIV").css("display","");
	//为右上角个人信息链接过来增加active样式
	$('.treeview').removeClass("active");
    $("#accountManage").addClass('active');
	$("#mainDIV").load("pages/user/user.jsp");
}

var EventUtil = {
   addHandler: function(element,type,handler){
        if(element.addEventListener){
               element.addEventListener(type, handler, false);
         }else if(element.attachEvent){
               element.attachEvent("on" + type, handler);
          }
      },
      removeHandler: function(element,type,handler){
           if(element.removeEventListener){
                 element.removeEventListener(type, handler, false);
           }else if(element.detachEvent){
                  element.detachEvent("on" + type, handler);
                }            
           }
 };    
