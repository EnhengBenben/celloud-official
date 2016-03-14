(function(console){
	console=console||{};
	console.barChart=function(id,data,title,length,x,y,theme) {
		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);
		var t;
		for (var i = 0; i < data.length; i++) {
			xAxis[i] = data[i][x];
			yAxis[i] = data[i][y];
		}
		var option = makeOptionScrollUnit(xAxis, yAxis, title, 'bar', 0, length);
		var myChart = echarts.init(document.getElementById(id), theme);
		myChart.setOption(option);
	}
	
	console.pieChart=function(id,data,x,y,title,name,rad, centerX, centerY) {
		if (document.getElementById(id) == null)
			return;
		var vlist = new Array(data.length);
		var yAxis = new Array(data.length);
		var legendName = new Array(data.length);
		for (var i = 0; i < data.length; i++) {
			legendName[i] = data[i][x];
			yAxis[i] = data[i][y];
			vlist[i] = {
				"name" : data[i][x],
				"value" : data[i][y]
			};
		}
		var opt = makePieOption(title, legendName, name, rad, centerX, centerY, vlist);
		var myChart = echarts.init(document.getElementById(id), blue);
		myChart.setOption(opt);
	}
	
})(console);

$(function(){
	
	
	$.post("console/data",function(data){
		drawCharts.mapchars(data.provinceData,'map','医院');
		console.barChart("loginId",data.loginData,'登陆次数',20,'user_name','logNum',themes.green);
		console.barChart("AppRunNum",data.appRunData,'运行次数',10,'app_name','runNum',themes.blue);
		console.barChart("UserRunNum",data.userRunData,'运行次数',10,'userName','runNum',themes.green);
		console.pieChart("browserDistribute",data.browserData,'browser','logNum','','客户端使用','60%', '50%', '55%');
	});
});