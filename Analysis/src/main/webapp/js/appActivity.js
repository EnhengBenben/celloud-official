$(document).ready(function() {
	$('input[name=startDate]').val("2015-10-01");
	$('input[name=endDate]').val("2015-12-12");
});

var appWeekFun = "app!getAppRunWeek";
var appMonthFun = "app!getAppRunMonth";
var UesrActivityURL = "app!getAppRunList";

localMonth();

function localWeek() {
	console.log(showWeekFirstDay());
	console.log(showWeekLastDay());
	
	$("#timeId").val(showWeekFirstDay());
	$('#timeId2').val(showWeekLastDay());
	loadAppRunTime();
}
function localMonth() {
	console.log(showMonthFirstDay());
	console.log(showMonthLastDay());
	$("#timeId").val(showMonthFirstDay());
	$('#timeId2').val(showMonthLastDay());
	loadAppRunTime();
}

function loadAppRunTime() {
	var start = $("#timeId").val();
	var end = $("#timeId2").val();
	var topN = $("#topId").val();
	
	var param = {
		"startDate" : start,
		"endDate" : end,
		"top" : topN
	};
	logReq(UesrActivityURL, param);
	$.get(UesrActivityURL, param, function(data) {
		if (data == null || data.length < 1) {
			$("#appListDiv").hide();
			return;
		}else{
			$("#appListDiv").show();
		}
		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);
		for (var i = 0; i < data.length; i++) {
			xAxis[i] = data[i].app_name;
			yAxis[i] = data[i].runNum;
		}
		var option = makeOptionScrollUnit(xAxis, yAxis, "运行次数", barType, 0, 15)
		var myChart = echarts.init(document.getElementById('appListDiv'),theme);
		myChart.setOption(option);
	});
}