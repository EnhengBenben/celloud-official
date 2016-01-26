/////////////////////////action方法名称
var loginWeekFun = "user!getLoginSortWeek";
var loginMonthFun = "user!getLoginSortMonth";
var fileMonthFun = "user!activityFileMonth";
var fileWeekFun = "user!activityFileWeek";
var appWeekFun = "user!appRunInWeek";
var appMonthFun = "user!appRunInMonth";
var UesrActivityURL = "user!getUserActivity"

$(document).ready(function() {
	$('input[name=startDate]').val("2015-10-01");
	$('input[name=endDate]').val("2015-12-12");
});
// //////////////////////////////初始化
localMonth();
function localWeek()
{
	console.log(showWeekFirstDay());
	console.log(showWeekLastDay());
	$("#timeId").val(showWeekFirstDay());
	$('#timeId2').val(showWeekLastDay());
	loadActivity();
}
function localMonth()
{
	console.log(showMonthFirstDay());
	console.log(showMonthLastDay());
	$("#timeId").val(showMonthFirstDay());
	$('#timeId2').val(showMonthLastDay());
	loadActivity();
}
function loadActivity() {
	var start = $("#timeId").val();
	var end = $("#timeId2").val();
	var topN = $("#topId").val();
	
	var param = {
		"startDate" : start,
		"endDate" : end,
		"topN" : topN,
	};
	logReq(UesrActivityURL, param);
	$.get(UesrActivityURL, param, function(data) {
		logRes(data);
		var fileNum = data["fileNum"];
		var fileSize = data["size"];
		var appRun = data["appRun"];
		if (fileNum == null || fileNum.length < 1) {
			$("#fileNum").hide();
			return;
		}else{
			$("#fileNum").show();
		}
		if (fileSize == null || fileSize.length < 1) {
			$("#fileSize").hide();
			return;
		}else{
			$("#fileSize").show();
		}
		if (appRun == null || appRun.length < 1) {
			$("#appRun").hide();
			return;
		}else{
			$("#appRun").show();
		}
		
		var xAxis = new Array(fileNum.length);
		var yAxis = new Array(fileNum.length);
		for (var i = 0; i < fileNum.length; i++) {
			xAxis[i] = fileNum[i].user_name;
			yAxis[i] = fileNum[i].fileNum;
		}
		var option = makeOptionScrollUnit(xAxis, yAxis, "数据个数", barType, 0, 15)
		var myChart = echarts.init(document.getElementById('fileNum'),theme);
		myChart.setOption(option);
		
		var xAxisSize = new Array(fileSize.length);
		var yAxisSize = new Array(fileSize.length);
		for (var i = 0; i < fileSize.length; i++) {
			xAxisSize[i] = fileSize[i].user_name;
			yAxisSize[i] = parseFloat((fileSize[i].size / (1024 * 1024 * 1024)).toFixed(2));
		}
		var sizeoption = makeOptionScrollUnit(xAxisSize, yAxisSize, "数据大小", barType, 0, 15)
		var sizeChart = echarts.init(document.getElementById('fileSize'),theme);
		sizeChart.setOption(sizeoption);
		
		var xAxisApp = new Array(appRun.length);
		var yAxisApp = new Array(appRun.length);
		for (var i = 0; i < appRun.length; i++) {
			xAxisApp[i] = appRun[i].user_name;
			yAxisApp[i] = appRun[i].runNum;
		}
		var appOpt = makeOptionScrollUnit(xAxisApp, yAxisApp, "运行次数", barType, 0, 15)
		var appChart = echarts.init(document.getElementById('appRun'),theme);
		appChart.setOption(appOpt);
		
	});
}