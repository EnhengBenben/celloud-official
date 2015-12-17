/**
 * 总览
 */
// 路径配置
require.config({
    paths: {
        echarts: 'http://echarts.baidu.com/build/dist'
    }
});

function showUserCount(){
	$("#uploadDIV").css("display","none");
	$("#mainDIV").css("display","");
	$.get("count3!loginCount",function(response){
		$("#mainDIV").html(response);
		fileMonthCount();
	});
}

function fileMonthCount(){
	$.get("count3!fileMonthCount",function(data){
		var x = "[";
		var y = "[";
		$.each(data,function(index,map){
			$.each(map,function(key,val){
				x += "'" + key + "',";
				y += "'" + val + "',";
			});
		})
		x = x.substring(0,x.length-1);
		y = y.substring(0,y.length-1);
		x += "]";
		y += "]";
		lineModal("month-chart","月上传文件统计",eval(x),eval(y),"月上传文件数量");
		fileDayCount();
	});
}

function fileDayCount(){
	$.get("count3!fileDayCount",function(data){
		var x = "[";
		var y = "[";
		$.each(data,function(index,map){
			$.each(map,function(key,val){
				x += "'" + key + "',";
				y += "'" + val + "',";
			});
		})
		x = x.substring(0,x.length-1);
		y = y.substring(0,y.length-1);
		x += "]";
		y += "]";
		lineModal("day-chart","日上传文件统计",eval(x),eval(y),"日上传文件数量");
	});
}

function lineModal(id,legend,xdata,ydata,yname){
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
	                data:[legend]
	            },
	            xAxis : [
	                {
	                    type : 'category',
	                    data : xdata
	                }
	            ],
	            yAxis : [
	                {
	                    type : 'value'
	                }
	            ],
	            series : [
	                {
	                    "name":yname,
	                    "type":"line",
	                    "data":ydata
	                }
	            ]
	        };
	        // 为echarts对象加载数据 
	        myChart.setOption(option); 
	    }
	);
}

/**
 * 数据上传
 */
function showUpload(){
	$("#uploadDIV").css("display","");
	$("#mainDIV").css("display","none");
	if(intro != null){
		intro.exit();
		intro = null;
		intro = introJs();
		intro.setOption('tooltipPosition', 'auto');
		intro.setOption('positionPrecedence', ['left', 'right', 'bottom', 'top']);
		intro.setOption('showStepNumbers', false);
		intro.setOption('showButtons', false);
	}
	if($("#uploadDIV").html()==""){
		$("#uploadDIV").load("pages/data/fileUpload.jsp");
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
	$("#mainDIV").load("pages/report/reportDetail.jsp");
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
            	img: "endintro.png"
              }
            ]
		});
		intro.start(9);
	}
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
