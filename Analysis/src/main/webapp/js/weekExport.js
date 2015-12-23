String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
	if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
		return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi" : "g")),
				replaceWith);
	} else {
		return this.replace(reallyDo, replaceWith);
	}
}
$("#loginfileapptop10").show();
function change() {
	var time1 = $("#timeId").val();
	var d = new Date(time1);
	var m=d.Monday();
	var s = d.sunday();
	console.log(d.getDay());
	console.log(s);
	console.log(m.format());
	$("#lastWeek").html(m.format());
	$("#endWeek").html(s);
	//reflush
	EachDayLoginNum();// 每天登陆次数
	appRunNumEachDay();// 每天APP运行
	userLoginNum();// 用户登陆
	loadFileNumSize();// 用户文件个数、大小
	loadeachDataSize();// 每上传文件大小统计
	userAppRunNum();// 用户运行APP
	useBrowser();
}
// /URL
var loginNumURL = "home!eachDayLoginNum";
var appRunNumURL = "home!eachDayAppRunNum;"
var eachUserLoginNumURL = "home!allUserLoginNunInWeek";
var eachDayDataSizeURL = "home!eachDayDataSize"; // 周内每上传数据大小统计
var eachUserRunAppURL = "home!eachUserRunApp";
var weekBrowserURL = "home!weekBrowser;"

var weekTime = newDate().format();

param = {
		"startDate" : $("#timeId").val(),
		"endDate":$("#endWeek").val()
	};
console.log(param);
// init
EachDayLoginNum();// 每天登陆次数
appRunNumEachDay();// 每天APP运行
userLoginNum();// 用户登陆
loadFileNumSize();// 用户文件个数、大小
loadeachDataSize();// 每上传文件大小统计
userAppRunNum();// 用户运行APP
useBrowser();
initTime();

function initTime() {
	var now = new Date();
	var lastMonday = now.lastWeekMonday(now);
	var lasSunday = now.lastWeekSunday(now);
	console.log(lastMonday);
	console.log(lasSunday);

	$("#lastWeek").html(lastMonday.format());
	$("#endWeek").html(lasSunday.format());

}

var param = {
		"startDate" : $("#timeId").val(),
		"endDate":$("#endWeek").val()
	};
// userAppUseCount();
/*
 * function userAppUseCount() { var viewId = "userAppCountViewId";
 * $.get(userAppRunNumURL, {}, function(data) { console.log(data); if (data ==
 * null || data.length < 1) { $("#" + viewId).css({ "display" : "none" });
 * return; } else { $("#" + viewId).css({ "display" : "block" }); } var xAxis =
 * new Array(data.length); var yAxis = new Array(data.length);
 * 
 * var t; for (var i = 0; i < data.length; i++) { xAxis[i] = data[i].userName;
 * yAxis[i] = data[i].runNum; } option = { title : { text : '用户运行App统计', subtext : '' },
 * tooltip : { trigger : 'axis' }, legend : { data : [ '运行次数' ] }, toolbox : {
 * show : true, feature : { dataZoom : { show : true }, dataView : { show : true },
 * magicType : { show : true, type : [ 'line', 'bar' ] }, restore : { show :
 * true }, saveAsImage : { show : true } } }, calculable : true, dataZoom : {
 * show : true, realtime : true, start : 0, end : 80 }, xAxis : [ { type :
 * 'category', boundaryGap : false, data : xAxis } ], yAxis : [ { type : 'value' } ],
 * series : [ { name : '运行次数', type : 'line', data : yAxis, itemStyle : { normal : {
 * label : { show : true } } } } ] }; var myChart =
 * echarts.init(document.getElementById(viewId)); myChart.setOption(option); }); }
 */
function useBrowser() {
	var viewId = "browserCountVId";
	$.get(weekBrowserURL, param, function(data) {
		
		var vlist = new Array(data.length);
		var yAxis = new Array(data.length);
		var legendName = new Array(data.length);
		for (var i = 0; i < data.length; i++) {
			legendName[i] = data[i].browser;
			yAxis[i] = data[i].logNum;
			vlist[i] = {
				"name" : data[i].browser,
				"value" : data[i].logNum
			};
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
				orient : 'vertical',
				x : 'left',
				data : legendName,
			},
			toolbox : {
				orient : 'vertical',
				x : 'right',
				show : true,
				feature : {
					dataZoom : {
						show : true
					},
					dataView : {
						show : true
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
			/*
			 * dataZoom : { show : true, realtime : true, start : 0, end : 80 },
			 * 
			 * xAxis : [ { type : 'category', boundaryGap : false, data : xAxis } ],
			 * yAxis : [ { type : 'value' } ],
			 */
			series : [ {
				name : '客户端使用',
				type : 'pie',
				radius : '70%',
				center : [ '40%', '40%' ],
				data : vlist
			} ]
		};
		var myChart = echarts.init(document.getElementById(viewId));
		myChart.setOption(option);
	});
}

function userAppRunNum() {
	var viewId = "eachUserRunApp";
	$.get(eachUserRunAppURL, param, function(data) {
		console.log(data);
	
		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);

		var t;
		for (var i = 0; i < data.length; i++) {
			xAxis[i] = data[i].userName;
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
				dataZoom : {
					show : true,
					realtime : true,
					start : 0,
					end : 80
				},
				data : [ '运行次数' ]
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
				name : '运行次数',
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
		var myChart = echarts.init(document.getElementById(viewId));
		myChart.setOption(option);
	});
}

function loadFileNumSize() {
	var viewId = "fileNumSize";
	$.get(eachDayDataSizeURL, param,
			function(data) {
				console.log(data);
				
				var xAxis = new Array(data.length);
				var yAxis = new Array(data.length);
				var fileNum = new Array(data.length);

				var t;
				for (var i = 0; i < data.length; i++) {
					xAxis[i] = data[i].userName;
					yAxis[i] = parseFloat(data[i].size / 1024 / 1024 / 1024)
							.toFixed(2);
					fileNum[i] = data[i].fileNum;
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
						dataZoom : {
							show : true,
							realtime : true,
							start : 0,
							end : 80
						},
						data : [ '数据大小(GB)', '文件数量' ]
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
					xAxis : [ {
						type : 'category',
						boundaryGap : false,
						data : xAxis
					} ],
					yAxis : [ {
						type : 'value'
					} ],
					series : [ {
						name : '数据大小(GB)',
						type : 'bar',
						data : yAxis,
						itemStyle : {
							normal : {
								label : {
									show : true
								}
							}
						}
					}, {
						name : '文件数量',
						type : 'bar',
						data : fileNum,
						itemStyle : {
							normal : {
								label : {
									show : true
								}
							}
						}
					} ]
				};
				var myChart = echarts.init(document.getElementById(viewId));
				myChart.setOption(option);
			});
}

function loadeachDataSize() {
	var viewId = "eachDataSize";
	$.get(eachDayDataSizeURL, param,
			function(data) {
				console.log(data);
		
				var xAxis = new Array(data.length);
				var yAxis = new Array(data.length);

				var t;
				for (var i = 0; i < data.length; i++) {
					xAxis[i] = data[i].weekDate;
					yAxis[i] = parseFloat(data[i].size / 1024 / 1024 / 1024)
							.toFixed(2);
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
						dataZoom : {
							show : true,
							realtime : true,
							start : 0,
							end : 80
						},
						data : [ '文件大小(GB)' ]
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
					xAxis : [ {
						type : 'category',
						boundaryGap : false,
						data : xAxis
					} ],
					yAxis : [ {
						type : 'value'
					} ],
					series : [ {
						name : '文件大小(GB)',
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
				var myChart = echarts.init(document.getElementById(viewId));
				myChart.setOption(option);
			});
}

function userLoginNum() {
	var viewId = "eachUserLoginNun";
	$.get(eachUserLoginNumURL, param, function(data) {
		console.log(data);

		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);

		var t;
		for (var i = 0; i < data.length; i++) {
			xAxis[i] = data[i].userName;
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
				dataZoom : {
					show : true,
					realtime : true,
					start : 0,
					end : 80
				},
				data : [ '登陆次数' ]
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
				name : '登陆次数',
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
		var myChart = echarts.init(document.getElementById(viewId));
		myChart.setOption(option);
	});
}
function appRunNumEachDay() {
	var viewId = "appRunEachDay";
	$.get(appRunNumURL, param, function(data) {
		console.log(data);
	
		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);

		var t;
		for (var i = 0; i < data.length; i++) {
			xAxis[i] = data[i].weekDate;
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
				data : [ '运行次数' ]
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
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : xAxis
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series : [ {
				name : '运行次数',
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
		var myChart = echarts.init(document.getElementById(viewId));
		myChart.setOption(option);
	});
}

function EachDayLoginNum() {
	var viewId = "loginEachDay";
	$.get(loginNumURL, param, function(data) {
		console.log(data);
	
		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);

		var t;
		for (var i = 0; i < data.length; i++) {
			xAxis[i] = data[i].weekDate;
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
				data : [ '登陆次数' ]
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
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : xAxis
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series : [ {
				name : '登陆次数',
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
		var myChart = echarts.init(document.getElementById(viewId));
		myChart.setOption(option);
	});
}

/** ** */
$(function() {
	setInterval("GetTime(newDate())", 1000);
});
function newDate() {
	return new Date();
}

function GetTime(now) {
	var mon, day, hour, min, ampm, time, str, tz, end, beg, sec;
	/*
	 * mon = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
	 * "Sep", "Oct", "Nov", "Dec");
	 */
	mon = new Array("一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月",
			"十一月", "十二月");
	/*
	 * day = new Array("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
	 */
	day = new Array("周日", "周一", "周二", "周三", "周四", "周五", "周六");
	hour = now.getHours();
	min = now.getMinutes();
	sec = now.getSeconds();
	if (hour < 10) {
		hour = "0" + hour;
	}
	if (min < 10) {
		min = "0" + min;
	}
	if (sec < 10) {
		sec = "0" + sec;
	}
	$("#Timer").html(
			"<h2 style='alige:center'>"
			// + day[now.getDay()] + ", " + mon[now.getMonth()] + " "
			// + now.getDate() + ", "
			+ now.getFullYear() + "年 " + (1 + now.getMonth()) + "月"
					+ now.getDate() + "日&nbsp;&nbsp;" + hour + ":" + min + ":"
					+ sec + "</h2>");

}
