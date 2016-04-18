$.main = {};
var intro;
var hasNavi;
$.main.show = {
    mainDIV: function(){
      $("#dataReportDIV").addClass("hide");
      $("#uploadDIV").addClass("hide");
      $("#mainDIV").removeClass("hide");
    },
    uploadDIV: function(){
      $("#dataReportDIV").addClass("hide");
      $("#mainDIV").addClass("hide");
      $("#uploadDIV").removeClass("hide");
    },
    dataReportDIV: function(){
      $("#mainDIV").addClass("hide");
      $("#uploadDIV").addClass("hide");
      $("#dataReportDIV").removeClass("hide");
    }
}
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
	 $.main.show.mainDIV();
	 $("#mainDIV").load("pages/data/data_main.jsp");
  });
  /** 消费记录 */
  $("#to-expense-model").on("click", function(){
    $.get("expense/getTotalConsumption",{},function(result){
      $("#total-consumption").html(result);
    });
  });
  $("#to-expense-main").on("click", function(){
    $.main.show.mainDIV();
    $("#mainDIV").load("pages/expense/expense_main.jsp");
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
		            	trigger: 'axis',
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
		                        formatter: '{value}'+(typeof(unit)=='undefined'?'':unit)
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
					y[y.length]=Number(map.size);
				}
			})
			var max=Math.max.apply(null,y);
			var unit="";
			var divisor=1;
			if(max>1073741824){
				divisor=1073741824;
				unit="GB";
			}else if(max>1048576){
				divisor=1048576;
				unit="MB";
			}else{
				divisor=1024;
				unit="kB";
			}
			for(var i=0;i<y.length;i++){
				y[i]=Number((y[i]/divisor).toFixed(2));
			}
			x = x.substring(0,x.length-1);
			x += "]";
			self.lineModal("count-source-day-chart","日资源占用量统计",eval(x),y,"日上传文件大小",unit);
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
				y[y.length]=Number(map.size);
				}
			})
			var max=Math.max.apply(null,y);
			var unit="";
			var divisor=1;
			if(max>1073741824){
				divisor=1073741824;
				unit="GB";
			}else if(max>1048576){
				divisor=1048576;
				unit="MB";
			}else{
				divisor=1024;
				unit="kB";
			}
			for(var i=0;i<y.length;i++){
				y[i]=Number((y[i]/divisor).toFixed(2));
			}
			x = x.length>1?x.substring(0,x.length-1):x;
			x += "]";
			if(x!="[]"){
				self.lineModal("count-source-month-chart","月资源占用量统计",eval(x),y,"月上传文件大小",unit);
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
	  $.main.show.mainDIV();
		$.get("count/loginCount",function(response){
			$("#mainDIV").html(response);
			self.fileMonthCount();
			self.fileSizeMonthCount();
			self.reportMonthCount();
			self.appMonthCount();
		});
	};
  self.toDataMain=function(){
    $("#to-data-main").trigger("click");
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
var messageUtils = (function(messageUtils){
	var self = messageUtils||{};
	self.messageChannels = {'listenerNumbers':0};
	self.openListeners = {'listenerNumbers':0};
	self.closeListeners = {'listenerNumbers':0};
	self.addOpenListener = function(listener){
		self.openListeners[self.openListeners.listenerNumbers]=listener;
		self.openListeners.listenerNumbers=self.openListeners.listenerNumbers+1;
	};
	self.addCloseListener = function(listener){
		self.closeListeners[self.closeListeners.listenerNumbers]=listener;
		self.closeListeners.listenerNumbers=self.closeListeners.listenerNumbers+1;
	};
	self.subscribe = function(channel,callback){
		var channels = self.messageChannels[channel]||{'listenerNumbers':0};
		if($.isFunction(callback)){
			channels[channels.listenerNumbers]=callback;
			channels.listenerNumbers=channels.listenerNumbers+1;
			self.messageChannels[channel] = channels;
		}
	};
	var openWebSocket = function(){
		var contextPath = window.CONTEXT_PATH||"";
		// window.location.protocol
		var hostname = window.location.hostname;
		var port = window.location.port||80;
		var wsUrl = "ws://" + hostname + ":" + port + contextPath + "/websocket/message";
		var ws = null;
		if ('WebSocket' in window) {
			ws = new WebSocket(wsUrl);
		} else if (window.MozWebSocket) {
			ws = new MozWebSocket(wsUrl);
		} else {
			ws = new SockJS(contextPath+"/sockjs/webSocketServer");
		}
		return ws;
	}
	ws = openWebSocket();
	ws.onopen = function() {
		console.log("websocket connect is opened");
		for(var listener in self.openListeners){
			if($.isFunction(self.openListeners[listener])){
				self.openListeners[listener]();
			}
		}
	};
	ws.onmessage = function(e) {
		var message = JSON.parse(e.data);
		var channels = self.messageChannels[message.channel]||[];
		if(channels.length <= 0){
			console.log("no listener for channel: " + message.channel);
			return;
		}
		for(var channel in channels){
			if($.isFunction(channels[channel])){
				channels[channel](message.message);
			}
		}
	};
	ws.onclose = function() {
		console.log("websocket connect is closed");
		for(var listener in self.closeListeners){
			if($.isFunction(self.closeListeners[listener])){
				self.closeListeners[listener]();
			}
		}
	};
	return self;
})(messageUtils);
messageUtils.subscribe("test",function(data){
	console.log("接收到test频道的消息：" + JSON.stringify(data));
});
messageUtils.addOpenListener(function(){
	console.log("aaaaa" );
});
messageUtils.addOpenListener(function(){
	console.log("bbbbb" );
});
messageUtils.addCloseListener(function(){
	console.log("bbbbbccccc" );
});
/**
 * 数据上传
 */
function showUpload(){
	$.main.show.uploadDIV();
	if($("#uploadDIV").html()==""){
		$("#uploadDIV").load("pages/upload/upload.jsp");
	}
}

/**
 * 实验管理
 */
function showExperiment(){
  $.main.show.mainDIV();
  $.get("experiment/getExperimentDict",function(response){
    $("#mainDIV").html(response);
  });
}

/**
 * 报告
 */
function showReport(){
	$.main.show.mainDIV();
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
  $.main.show.mainDIV();
	$.get("app/toAppStore",{},function(responseText){
		$("#mainDIV").html(responseText);
	});
}

/**
 * 统计页面
 */
function showCount(){
  $.main.show.mainDIV();
	$("#mainDIV").load("pages/count/count_main.jsp");
}

/**
 * 帐号管理
 */
function showUser(){
  $.main.show.mainDIV();
	//为右上角个人信息链接过来增加active样式
	$('.treeview').removeClass("active");
  $("#accountManage").addClass('active');
	$("#mainDIV").load("user/info");
}
/**
 * 问题反馈(工单)
 */
function showFeedback(){
  $.main.show.mainDIV();
	//为右上角个人信息链接过来增加active样式
	$('.treeview').removeClass("active");
    $("#feedbackManage").addClass('active');
	$("#mainDIV").load("feedback/main");
}
/**
 * 帮助
 */
function showHelp(){
  $.main.show.mainDIV();
	$('.treeview').removeClass("active");
    $("#toHelpMenu").addClass('active');
	$("#mainDIV").load("pages/help/help_question.jsp");
}