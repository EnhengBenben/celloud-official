$(document).ready(function() {
	$('input[name=startDate]').val("2015-10-01");
	$('input[name=endDate]').val("2015-12-12");
});
var defStartDate = new Date('2014/10/10').month()
var defEndDate = new Date().format();
var appWeekFun = "app!getAppRunWeek";
var appMonthFun = "app!getAppRunMonth";
loadAppRunTime();

function onChange() {
	loadAppRunTime();
}

function loadAppRunTime() {
	var start = $("#appTimeId").val();
	var end = $("#appTimeId2").val();
	var type = $("#groupTypeApp").val();
	var topN = $("#topId").val();
	var url;
	if (type == "week") {
		url = appWeekFun;
	} else {
		url = appMonthFun;
	}
	var softId = $("#softList").val() == '-1' ? '' : $("#softList").val();
	$.get(url, {
		"startDate" : start,
		"endDate" : end,
		"user.userId" : "16",
		"softwareId" : softId
	}, function(data) {
		if ($("#groupTypeApp").val() == "week") {
			data = doTopN(data, topN, "weekDate");
		} else {
			data = doTopN(data, topN, "yearMonth");
		}
		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);
		for (var i = 0; i < data.length; i++) {
			var t;
			if ($("#groupTypeApp").val() == "week") {
				var sun = new Date(data[i].weekDate);
				t = "(" + data[i].weekDate + "~" + sun.sunday() + ")";
			} else {
				t = "(" + data[i].yearMonth + ")";
			}
			xAxis[i] = data[i].softwareName + t;
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
				type : 'bar',
				data : yAxis,
				itemStyle : {
					normal : {
						label : {
							show : true
						}
					}
				}
			} ]
		};
		var myChart = echarts.init(document.getElementById('appListDiv'));
		myChart.setOption(option);
	});
}