$(document).ready(function() {
	$('input[name=startDate]').val("2015-10-01");
	$('input[name=endDate]').val("2015-12-12");
});
var defStartDate = new Date('2014/10/10').month()
var defEndDate = new Date().format();
var appWeekFun = "app!getAppRunWeek";
var appMonthFun = "app!getAppRunMonth";
var UesrActivityURL = "app!getAppRunList";

loadAppRunTime();

function onChange() {
	loadAppRunTime();
}

function loadAppRunTime() {
	var start = $("#timeId").val();
	var end = $("#timeId2").val();
	var topN = $("#topId").val();

	var param =  {
			"startDate" : start,
			"endDate" : end,
			"top":topN
		};
	logReq(UesrActivityURL, param);
	$.get(UesrActivityURL, param, function(data) {
		
		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);
		for (var i = 0; i < data.length; i++) {		
			xAxis[i] = data[i].app_name;
			yAxis[i] = data[i].runNum;
		}
		var option = makeOptionScrollUnit(xAxis, yAxis, "运行次数", barType,0,15)
		var myChart = echarts.init(document.getElementById('appListDiv'));
		myChart.setOption(option);
	});
}