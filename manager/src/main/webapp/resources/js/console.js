$(function(){
	$.post("console/data",function(data){
		drawCharts.mapchars(data.provinceData,'map','医院');
		drawCharts.barChart("loginId",data.loginData,'登陆次数','user_name','logNum',themes.green);
		drawCharts.barChart("AppRunNum",data.appRunData,'运行次数','app_name','runNum',themes.blue);
		drawCharts.barChart("UserRunNum",data.userRunData,'运行次数','userName','runNum',themes.green);
		drawCharts.pieChart("browserDistribute",data.browserData,'browser','logNum','','客户端使用','60%', '50%', '55%');
	});
});