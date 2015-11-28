/**
 * 首页
 */

// 路径配置
require.config({
    paths: {
        echarts: 'http://echarts.baidu.com/build/dist'
    }
});

function showUserCount(){
	$.get("count3!loginCount",function(response){
		$("#userCount").html(response);
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
 * 应用市场
 */

function showAppStore(){
	$("#userCount").load("pages/software/app.jsp");
}

/**
 * 数据管理
 */

function showData(){
	$("#userCount").load("pages/data/data.jsp");
}

/**
 * 统计页面
 */
function showCount(){
	$("#userCount").load("pages/count/count.jsp");
}
/**
 * 数据上传
 */
function showUpload(){
	$("#userCount").load("pages/data/fileUpload.jsp");
}

/**
 * 报告模块
 */
function showReport(){
	$("#userCount").load("pages/report/reportDetail.jsp");
}
/**
 * 帐号管理
 */
function showUser(){
	$("#userCount").load("pages/user/user.jsp");
}