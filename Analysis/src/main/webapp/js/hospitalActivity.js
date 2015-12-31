/////////////////////////action方法名称
var loginWeekFun = "company!activityHospitaLoginWeek";
var loginMonthFun = "company!activityHospitaLoginMonth";
var fileNumURL = "company!getHospitalFile";
var appMonthFun = "company!activityHospitalAppMonth";
var actionName = "company!company!";

var defStartDate = new Date('2014/10/10').month()
var defEndDate = new Date().format();

var start;
var end;
$(document).ready(function() {
	$('input[name=startDate]').val("2015-10-01");
	$('input[name=endDate]').val("2015-12-12");
});
// //////////////////////////////初始化
// loadLoginData();
loadActivityFile();
// loadAppRunTime();
chars();// 医院月新增数量

// //////////////////////////////函数区


function loadActivityFile() {
	var start = $("#fileTimeId").val();
	var end = $("#fileTimeId2").val();
	var topN = $("#fileTopId").val();
	
	var param = {
		"startDate" : start,
		"endDate" : end,
		"topN" : topN
	};
	logReq(fileNumURL, param);
	$.get(fileNumURL, param, function(data) {
		var fileNum = eval(data["fileNum"]);
		var fileSize =  eval(data["size"]);
		logRes(fileNum);
		logRes(fileSize);
		var xAxis = new Array(fileNum.length);
		var yAxis = new Array(fileNum.length);
		var xAxisSize = new Array(fileSize.length);
		var yAxisSize = new Array(fileSize.length);
		
		for (var i = 0; i < xAxis.length; i++) {
			xAxis[i] = fileNum[i].company_name;
			yAxis[i] = fileNum[i].fileNum;
		}
		for (var i = 0; i < xAxisSize.length; i++) {
			xAxisSize[i] = fileSize[i].company_name;
			yAxisSize[i] = parseFloat( (fileSize[i].size/(1024*1024*1024)).toFixed(2) );
		}
		var option = makeOptionScrollUnit(xAxis, yAxis, "数据量", barType,0,15)
		var sizeoption = makeOptionScrollUnit(xAxisSize, yAxisSize, "数据大小", barType,0,15)
		
		var myChart = echarts.init(document.getElementById('fileNum'));
		var sizeChart = echarts.init(document.getElementById('fileSize'));
		sizeChart.setOption(sizeoption);
		myChart.setOption(option);
	});
}

function chars() {
	$.get("company!getCompanyNumEveryMonth", {}, function(result) {
		var xAxis = (result.timeLine).split(",");
		var yAxis = eval("[" + result.data + "]");
		// 基于准备好的dom，初始化echarts图表
		var option = makeOptionScrollUnit(xAxis, yAxis, '月新增医院数量', barType, 100, 12);
		var myChart = echarts.init(document.getElementById('newHospitalEvyMonth'));
		myChart.setOption(option);
	})
}
