/////////////////////////action方法名称
var loginWeekFun = "user!getLoginSortWeek";
var loginMonthFun = "user!getLoginSortMonth";
var fileMonthFun = "user!activityFileMonth";
var fileWeekFun = "user!activityFileWeek";
var appWeekFun = "user!appRunInWeek";
var appMonthFun = "user!appRunInMonth";

$(document).ready(function() {
	$('input[name=startDate]').val("2015-10-01");
	$('input[name=endDate]').val("2015-12-12");
});
// //////////////////////////////初始化
loadLoginData();
loadActivityFile();
loadAppRunTime();

// //////////////////////////////函数区
function fileOnChange() {
	loadActivityFile();
}
function appOnChange() {
	loadAppRunTime();
}

function loginOnChange() {
	loadLoginData();
}

function loadAppRunTime() {

	var start = $("#appTimeId").val();
	var end = $("#appTimeId2").val();

	var topN = $("#appTopId").val();
	var uids = $("#appSelectId").val();
	var type = $("#appGoupId").val();

	var url;
	if (type == "week") {
		url = appWeekFun;
	} else {
		url = appMonthFun;
	}
	if(uids=='-1')
		uids=''
	var param =  {
			"startDate" : start,
			"endDate" : end,
			"userIds":uids,
		};
	logReq(url, param);
	$.get(url,param, function(data) {
		logRes(data);
		if (type == "week") {
			data = doTopN(data, topN, "weekDate");
		} else {
			data = doTopN(data, topN, "yearMonth");
		}
		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);
		for (var i = 0; i < data.length; i++) {
			if (type == "week") {
				var sun = new Date(data[i].weekDate);
				t = "(" + data[i].weekDate + "~" + sun.sunday() + ")";
			} else {
				t = "(" + data[i].yearMonth + ")";
			}
			xAxis[i] = data[i].userName + t;
			yAxis[i] = data[i].runNum;
		}
		// 基于准备好的dom，初始化echarts图表
		option = {
			title : {
				text : '',
				subtext : ''
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ 'app运行统计' ]
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
				name : 'app运行统计',
				type : 'line',
				data : yAxis,
				itemStyle : {
					normal : {
						barBorderWidth: 8,
						label : {
							show : true,
						}
					}
				}
			} ]
		};
		var myChart = echarts.init(document.getElementById('appListDiv'));
		myChart.setOption(option);
	});
}

function loadActivityFile() {
	var start = $("#fileTimeId").val();
	var end = $("#fileTimeId2").val();

	var topN = $("#fileTopId").val();
	var type = $("#fileGoupId").val();
	var hids = $("#fileSelectId").val();
	var orderType = $("#orderType").val();//排序方式：文件数量、文件大小
	var url;

	if (type == "week") {
		url = fileWeekFun;
	} else {
		url = fileMonthFun;
	}

	if(hids=='-1')
		hids='';
	var param = {
		"startDate" : start,
		"endDate" : end,
		"userIds" : hids,
		"orderType" : orderType
	};
	logReq(url, param);
	$.get(url, param, function(data) {
		if (type == "week") {
			data = doTopN(data, topN, "weekDate");
		} else {
			data = doTopN(data, topN, "yearMonth");
		}
		var xAxis = new Array(data.length);
		var fileNum = new Array(data.length);
		var fileSize = new Array(data.length);
		var t;
		for (var i = 0; i < data.length; i++) {
			if (type == "week") {
				var sun = new Date(data[i].weekDate);
				t = "(" + data[i].weekDate + "~" + sun.sunday() + ")";
			} else {
				t = "(" + data[i].yearMonth + ")";
			}
			xAxis[i] = data[i].userName+t;
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
				data : [ '文件数量', '文件大小（GB）' ]
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
				data : fileNum,
				itemStyle : {
					normal : {
						label : {
							show : true
						}
					}
				}
			}, {
				name : '文件大小(GB)',
				type : 'line',
				data : fileSize,
				itemStyle : {
					normal : {
						label : {
							show : true
						}
					}
				}
			} ]
		};
		var myChart = echarts.init(document.getElementById('userFileListDiv'));
		myChart.setOption(option);
	});
}

function loadLoginData() {
	var start = $("#loginTimeId").val();
	var end = $("#loginTimeId2").val();
	var topN = $("#loginTopId").val();
	var type = $("#loginGoupId").val();
	var uids = $("#loginSelectId").val();

	var url
	if (type == "week") {
		url = loginWeekFun;
	} else {
		url = loginMonthFun;
	}
	if(uids=='-1')
		uids='';
	var param = {
			"startDate" : start,
			"endDate" : end,
			"userIds" : uids,
		};
	logReq(url, param);
	$.get(url, param, function(data) {
		if (type == "week") {
			data = doTopN(data, topN, "weekDate");
		} else {
			data = doTopN(data, topN, "yearMonth");
		}
		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);
		for (var i = 0; i < data.length; i++) {
			if (type == "week") {
				var sun = new Date(data[i].weekDate);
				t = "(" + data[i].weekDate + "~" + sun.sunday() + ")";
			} else {
				t = "(" + data[i].yearMonth + ")";
			}
			xAxis[i] = data[i].userName + t;
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
				data : [ '用户登陆次数' ]
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
				name : '用户登陆次数',
				type : 'line',
				data : yAxis,
				itemStyle : {
					normal : {
						label : {
							show : true,
						}
					}
				}
			} ]
		};
		var myChart = echarts.init(document.getElementById('loginListDiv'));
		myChart.setOption(option);
	});
}
function selectOnChange() {
	var sweek = $("#groupType").val();
	var formatStr = "";
	if (sweek == "week") {
		formatStr = "YYYY-MM-dd";
	} else {
		formatStr = "YYYY-MM";
	}
}
