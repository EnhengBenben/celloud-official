/////////////////////////action方法名称
var loginWeekFun = "company!activityHospitaLoginWeek";
var loginMonthFun = "company!activityHospitaLoginMonth";
var fileMonthFun = "company!activityHospitalFileMonth";
var fileWeekFun = "company!activityHospitalFileWeek";
var appWeekFun = "company!activityHospitalAppWeek";
var appMonthFun = "company!activityHospitalAppMonth";
var actionName = "company!company!";

var defStartDate = new Date('2014/10/10').month()
var defEndDate = new Date().format();
$('input[name=date-picker]').val(defStartDate + " ~ " + defEndDate);

var start;
var end;
$(document).ready(function() {
	$('input[name=startDate]').val("2015-10-01");
	$('input[name=endDate]').val("2015-12-12");

});
// //////////////////////////////初始化
loadLoginData();
loadActivityFile();
loadAppRunTime();
chars();// 医院月新增数量

// //////////////////////////////函数区
function appOnChange() {
	loadAppRunTime();
}
function loginOnChange() {
	loadLoginData();
}
function fileOnChange() {
	loadActivityFile();
}
function selectOnChange(type) {
	loadLoginData();
}

/** 请求后台处理echarts* */
//app运行次数统计
function loadAppRunTime() {
	var start = $("#appTimeId").val();
	var end = $("#appTimeId2").val();

	var cmpIds = $("#appHospId").val();
	var topN = $("#appTopId").val();
	var type =$("#appGroupId").val();
	var url;

	if (type == "week") {
		url = appWeekFun;
	} else {
		url =  appMonthFun;
	}
	if(cmpIds=='-1')
		cmpIds='';
	$.get(url, {
		"startDate" : start,
		"endDate" : end,
		"companyIds":cmpIds,
	}, function(data) {
		if (type == "week") {
			data = doTopN(data, topN, "weekDate");
		} else {
			data = doTopN(data, topN, "yearMonth");
		}
		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);
		var t;
		for (var i = 0; i < data.length; i++) {
			if (type== "week") {
				var sun = new Date(data[i].weekDate);
				t = "(" + data[i].weekDate + "~" + sun.sunday() + ")";
			} else {
				t = "(" + data[i].yearMonth + ")";
			}
			xAxis[i] = data[i].companyName + t;
			yAxis[i] = data[i].runNum;
		}
		option = {
			title : {
				text : '',
				subtext : ''
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ 'app运行次数' ]
			},
			toolbox : {
				show : true,
				feature : {
					dataZoom : {
						show : true
					},
					dataView : {
						show : true
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			dataZoom : {
				show : true,
				realtime : true,
				start : 0,
				end : 80
			},
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : xAxis
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series : [ {
				name : 'app运行次数',
				type : 'line',
				data : yAxis
			} ]
		};
		var myChart = echarts.init(document.getElementById('appListDiv'));
		myChart.setOption(option);
	});
}

function loadActivityFile() {
	var start = $("#fileTimeId").val();
	var end = $("#fileTimeId2").val();

	var type = $("#fileGroupId").val();
	var cmpIds = $("#fileHospId").val();
	var topN = $("#fileTopId").val();
	var orderType = $("#fileOrderId").val();
	var url;
	
	if (type== "week") {
		url = fileWeekFun;
	} else {
		url = fileMonthFun;
	}
	
	if(cmpIds=='-1')
			cmpIds='';
	var param = {
			"startDate" : start,
			"endDate" : end,
			"companyIds" : cmpIds,
			"orderby":orderType
		};
	logReq(url, param);
	$.get(url,param , function(data) {
		if (type == "week") {
			data = doTopN(data, topN, "weekDate");
		} else {
			data = doTopN(data, topN, "yearMonth");
		}
		/*if (data == null || data.length < 1) {
			$("#userFileListDiv").css({
				"display" : "none"
			});
			return;
		}else{
			$("#userFileListDiv").css({
				"display" : "block"
			});
		}*/
		var xAxis = new Array(data.length);
		var fileNum = new Array(data.length);
		var fileSize = new Array(data.length);
		
		var t;
		for (var i = 0; i < data.length; i++) {
			if (type != "week") {
				t = "(" + data[i].yearMonth + ")";
				xAxis[i] = data[i].company_name+t;
			} else {
				var sun = new Date(data[i].weekDate);
				t = "(" + data[i].weekDate + "~" + sun.sunday() + ")";
				xAxis[i] = data[i].company_name+t;
			}
			fileNum[i] = data[i].fileNum;
			fileSize[i] = (data[i].size / (1024 * 1024 * 1024)).toFixed(2);
		}
		option = {
			title : {
				text : '',
				subtext : ''
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '文件数量', '文件大小(GB)' ]
			},
			toolbox : {
				show : true,
				feature : {
					dataZoom : {
						show : true
					},
					dataView : {
						show : true
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			dataZoom : {
				show : true,
				realtime : true,
				start : 0,
				end : 80
			},
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : xAxis
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series : [ {
				name : '文件数量',
				type : 'line',
				data : fileNum
			}, {
				name : '文件大小(GB)',
				type : 'line',
				data : fileSize
			} ]
		};
		var myChart = echarts.init(document.getElementById('userFileListDiv'));
		myChart.setOption(option);
	});
}

function loadLoginData() {
	var start = $("#loginTimeId").val();
	var end = $("#loginTimeId2").val();
	var type = $("#loginGroupId").val();
	var cmpIds = $("#loginHospId").val();
	var topN = $("#loginTopId").val();	
	var url;
	
	if (type == "week") {
		url = loginWeekFun;
	} else {
		url = loginMonthFun;
	}
	if(cmpIds=='-1')
		cmpIds='';
	var param =  {
			"startDate" : start,
			"endDate" : end,
			"companyIds" : cmpIds
		};
	logReq(url, param);
	$.get(url,param, function(data) {
		if (type == "week") {
			data = doTopN(data, topN, "weekDate");
		} else {
			data = doTopN(data, topN, "yearMonth");
		}
		/*if (data == null || data.length < 1) {
			$("#loginListDiv").css({
				"display" : "none"
			});
			return;
		}else{
			$("#loginListDiv").css({
				"display" : "block"
			});
		}*/
		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);
		for (var i = 0; i < data.length; i++) {
			if (type== "week") {
				var sun = new Date(data[i].weekDate);
				t = "(" + data[i].weekDate + "~" + sun.sunday() + ")";
			} else {
				t = "(" + data[i].yearMonth + ")";
			}
			xAxis[i] = data[i].companyName + t;
			yAxis[i] = data[i].logNum;
		}
		option = {
			title : {
				text : '',
				subtext : ''
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '医院登陆次数' ]
			},
			toolbox : {
				show : true,
				feature : {
					dataZoom : {
						show : true
					},
					dataView : {
						show : true
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			dataZoom : {
				show : true,
				realtime : true,
				start : 0,
				end : 80
			},
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : xAxis
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series : [ {
				name : '医院登陆次数',
				type : 'line',
				data : yAxis
			} ]
		};
		var myChart = echarts.init(document.getElementById('loginListDiv'));
		myChart.setOption(option);
	});
}

function chars() {
	$.get("company!getCompanyNumEveryMonth", {}, function(result) {
		var xAxis = (result.timeLine).split(",");
		var yAxis = eval("[" + result.data + "]");
		// 基于准备好的dom，初始化echarts图表
		option = {
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '月新增医院数量' ]
			},
			toolbox : {
				show : true,
				feature : {
					dataZoom : {
						show : true
					},
					dataView : {
						show : true
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			dataZoom : {
				show : true,
				realtime : true,
				start : 0,
				end : 80
			},
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : xAxis
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series : [ {
				name : '月新增医院数量',
				type : 'line',
				data : yAxis
			} ]
		};
		var myChart = echarts.init(document
				.getElementById('newHospitalEvyMonth'));
		myChart.setOption(option);
	})
}
