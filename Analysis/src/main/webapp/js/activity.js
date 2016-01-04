/////////////////////////action方法名称
var fileNumURL = "company!getActivityList";

// //////////////////////////////初始化
loadActivityFile();
// chars();// 医院月新增数量

jQuery(function($) {
	var companyTable = $('#fileListId').dataTable({
		"aoColumns" : [ null, null, null, null ],
		"aaSorting":[[2,"desc"]],
		iDisplayLength : 10
	});
	var userDataTable = $('#userFileList').dataTable({
		"aoColumns" : [ null, null, null, null, null ],
		"aaSorting":[[2,"desc"]],
		iDisplayLength : 10
	});
	var appTable = $('#appList').dataTable({
		"aoColumns" : [ null, null, null ],
		"aaSorting":[[2,"desc"]],
		iDisplayLength : 10
	});
	
	console.log("queue");
})

// //////////////////////////////函数区

function loadActivityFile() {
	var start = $("#timeId").val();
	var end = $("#timeId2").val();
	var topN = $("#fileTopId").val();
	
	var param = {
		"startDate" : start,
		"endDate" : end,
		"topN" : topN
	};
	logReq(fileNumURL, param);
	$.get(fileNumURL, param, function(data) {
		hospital_chart(data);
		app_chart(data);
		user_chart(data);
	});
}
function app_chart(data) {
	console.log(data);
	var appRun = data["appRun"];
	if (appRun == null || appRun.length < 1) {
		$("#appListDiv").hide();
		return;
	} else {
		$("#appListDiv").show();
	}
	
	var xAxisApp = new Array(appRun.length);
	var yAxisApp = new Array(appRun.length);
	for (var i = 0; i < appRun.length; i++) {
		xAxisApp[i] = appRun[i].app_name;
		yAxisApp[i] = appRun[i].runNum;
	}
	var option = makeOptionScrollUnit(xAxisApp, yAxisApp, "运行次数", barType, 0, 10)
	var myChart = echarts.init(document.getElementById('appListDiv'));
	myChart.setOption(option);
}
function hospital_chart(data) {
	var fileNum = data["hFileNum"];
	var fileSize = data["hSize"];
	var fileNumId = "hfileNum";
	var fileSizeId = "hfileSize";
	var isReturn = false;
	
	if (fileNum == null || fileNum.length < 1) {
		$("#" + fileNumId).hide();
		isReturn = true;
	} else {
		$("#" + fileNumId).show();
	}
	if (fileSize == null || fileSize.length < 1) {
		$("#" + fileSizeId).hide();
		isReturn = true;
	} else {
		$("#" + fileSizeId).show();
	}
	if (isReturn == true)
		return;
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
		yAxisSize[i] = parseFloat((fileSize[i].size / (1024 * 1024 * 1024)).toFixed(2));
	}
	
	var option = makeOptionScrollUnit(xAxis, yAxis, "数据量", barType, 0, 6)
	var sizeoption = makeOptionScrollUnit(xAxisSize, yAxisSize, "数据大小", barType, 0, 6)
	var myChart = echarts.init(document.getElementById(fileNumId));
	var sizeChart = echarts.init(document.getElementById(fileSizeId));
	sizeChart.setOption(sizeoption);
	myChart.setOption(option);
}

function user_chart(data) {
	var fileNum = data["uFileNum"];
	var fileSize = data["uSize"];
	var appRun = data["uAppRun"];
	
	var fileNumId = "uFileNum";
	var fileSizeId = "uFileSize";
	var appRunId = "uAppRun";
	var isReturn = false;
	if (fileNum == null || fileNum.length < 1) {
		$("#" + fileNumId).hide();
		isReturn = true;
	} else {
		$("#" + fileNumId).show();
	}
	if (fileSize == null || fileSize.length < 1) {
		$("#" + fileSizeId).hide();
		isReturn = true;
	} else {
		$("#" + fileSizeId).show();
	}
	if (appRun == null || appRun.length < 1) {
		$("#" + appRunId).hide();
		isReturn = true;
	} else {
		$("#" + appRunId).show();
	}
	if (isReturn == true)
		return;
	var xAxis = new Array(fileNum.length);
	var yAxis = new Array(fileNum.length);
	for (var i = 0; i < fileNum.length; i++) {
		xAxis[i] = fileNum[i].user_name;
		yAxis[i] = fileNum[i].fileNum;
	}
	var option = makeOptionScrollUnit(xAxis, yAxis, "数据量", barType, 0, 15)
	var myChart = echarts.init(document.getElementById(fileNumId));
	myChart.setOption(option);
	
	var xAxisSize = new Array(fileSize.length);
	var yAxisSize = new Array(fileSize.length);
	for (var i = 0; i < fileSize.length; i++) {
		xAxisSize[i] = fileSize[i].user_name;
		yAxisSize[i] = parseFloat((fileSize[i].size / (1024 * 1024 * 1024)).toFixed(2));
	}
	var sizeoption = makeOptionScrollUnit(xAxisSize, yAxisSize, "数据大小", barType, 0, 15)
	var sizeChart = echarts.init(document.getElementById(fileSizeId));
	sizeChart.setOption(sizeoption);
	
	var xAxisApp = new Array(appRun.length);
	var yAxisApp = new Array(appRun.length);
	for (var i = 0; i < appRun.length; i++) {
		xAxisApp[i] = appRun[i].user_name;
		yAxisApp[i] = appRun[i].runNum;
	}
	var appOpt = makeOptionScrollUnit(xAxisApp, yAxisApp, "运行次数", barType, 0, 15)
	var appChart = echarts.init(document.getElementById(appRunId));
	appChart.setOption(appOpt);
	
}

function chars() {
	$.get("company!getCompanyNumEveryMonth", {}, function(result) {
		var xAxis = (result.timeLine).split(",");
		var yAxis = eval("[" + result.data + "]");
		var option = makeOptionScrollUnit(xAxis, yAxis, '月新增医院数量', barType, 100, 12);
		var myChart = echarts.init(document.getElementById('newHospitalEvyMonth'));
		myChart.setOption(option);
	})
}
