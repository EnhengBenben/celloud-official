$(document).ready(function() {
  var opts = {
      lines: 13, // The number of lines to draw
      length: 28, // The length of each line
      width: 14, // The line thickness
      radius: 42, // The radius of the inner circle
      corners: 1, // Corner roundness (0..1)
      rotate: 0, // The rotation offset
      direction: 1, // 1: clockwise, -1: counterclockwise
      color: '#000', // #rgb or #rrggbb or array of colors
      speed: 1, // Rounds per second
      trail: 60, // Afterglow percentage
      shadow: false, // Whether to render a shadow
      hwaccel: false, // Whether to use hardware acceleration
      className: 'spinner', // The CSS class to assign to the spinner
      zIndex: 2e9, // The z-index (defaults to 2000000000)
      top: '40%', // Top position relative to parent in px
      left: '45%' // Left position relative to parent in px
    };
	//showSysInfo();
  spinner = new Spinner(opts);
  var target = document.getElementById('appMain');
  spinner.spin(target);
	var companyId = $("#sessionCompanyId").val();
	if (companyId == 6) {
		$.get("count/pgsCount",function(responseText){
			$("#countDiv").html(responseText);
			var url = $("#downUrl").val();
      if(url){
        $("#_down").attr("href", url);
        $("#_down").removeClass("hide");
      }
			spinner.stop();
		});
	} else if (companyId == 3 || companyId == 966 || companyId == 965 || companyId == 961) {
		$.get("count/hbvCount",function(responseText){
			$("#countDiv").html(responseText);
			var url = $("#downUrl").val();
			if(url){
			  $("#_down").attr("href", url);
			  $("#_down").removeClass("hide");
			}
			spinner.stop();
		});
	} else if (companyId == 33) {
		$.get("count/cmpCount",function(responseText){
			$("#countDiv").html(responseText);
			var url = $("#downUrl").val();
			$("#_down").attr("href", url);
			spinner.stop();
		});
	}else{
	  $("#_down").parent().parent().remove();
	  spinner.stop();
	}
});
function showSeq(seq) {
	$("#showSeq").html(seq);
	$("#seqModal").modal("show");
}
function showGeneResult(result) {
	$("#showGeneResult").html(result);
	$("#geneResultModal").modal("show");
}
function showSysInfo() {
	var SystemCountURL = "count/systemCount";
	$.get(SystemCountURL, function(data) {
		var monthData = data["monthData"];
		var monthSize = data["monthSize"];
		var monthReport = data["monthReport"];
		var monthApp = data["monthApp"];
		var appRum = data["appRum"];
		var size = data["size"];
		var fileNum = data["fileNum"];
		var weekFile = data["weekData"];
		var weekReport = data["weekReport"];
		var weekApp = data["weekApp"];

		console.log(size);
		console.log(fileNum);
		$("#run_File_Num").html(fileNum["runFileNum"]);
		$("#unRun_Num").html(parseInt(fileNum["fileNum"])-parseInt(fileNum["runFileNum"]));


		var max = 0;
		var min = 2 << 32;
		$.each(monthSize, function(i, item) {
			var tmp = parseInt(item["size"]);
			max = tmp > max ? tmp : max;
			min = tmp < min ? tmp : min;
		});
		var ustr = "(MB)";
		var len = 0;
		var mid = max + min;
		console.log(mid);
		console.log(max);
		console.log(min);

		if ((mid >> 30) > 0) {
			len = 30;
			ustr = "(GB)"
		} else if ((mid >> 20) > 0) {
			len = 20;
			ustr = "(MB)"
		} else if ((mid >> 10) > 0) {
			len = 10;
			ustr = "(KB)"
		}
		$.each(monthSize, function(i, item) {
			monthSize[i]["size"] = parseFloat((item["size"] / (2 << len)).toFixed(2));
		});
		try {
			buildView("size_month_id", monthSize, "time", "size", "数据大小" + ustr, "line", 0, 12);
			buildView("size_week_id", weekFile, "time", "size", "数据大小" + ustr, "line", 0, 12);
		} catch (e) {
			console.log(e);
		}
		try {
			buildView("data_month_id", monthData, "time", "num", "数据量", "line", 0, 12);
			buildView("data_week_id", weekFile, "time", "fileNum", "数据量", "line", 0, 12);

		} catch (e) {
			console.log(e);
		}

		try {
			buildView("report_week_id", weekReport, "time", "reportNum", "报告数量", "line", 0, 12);
			buildView("app_run_id", appRum, "app_name", "runNum", "运行次数", "line", 0, 12);
			buildView("report_month_id", monthReport, "time", "reportNum", "报告数量", "line", 0, 12);
		} catch (e) {
			console.log(e);
		}
		try {
			buildView("app_month_id", monthApp, "time", "num", "运行次数", "line", 0, 12);
			buildView("app_week_id", weekApp, "time", "runNum", "运行次数", "line", 0, 12);

		} catch (e) {
			console.log(e);
		}

	});
}
function buildView(id, list, xPer, yPer, seriesName, typex, position, showNum) {
	var x = new Array(list.length);
	var y = new Array(list.length);
	$.each(list, function(index, item) {
		x[index] = item[xPer];
		y[index] = item[yPer];
	});
	makeOptionScrollUnit(id, x, y, seriesName, typex, position, showNum);
}
/*
 * var userCount = (function(userCount) { var self = userCount || {};
 * 
 * return self; })(userCount);
 */
function makeOptionScrollUnit(id, xAxis, yAxis, seriesName, typex, position, showNum) {

	var length = xAxis.length;
	var option = null;
	if (showNum < length) {
		if (position < 100) {
			var len = position + (showNum / length) * 100;
			option = makeOptionScroll('', xAxis, yAxis, seriesName, typex, position, len);
		} else {
			var len = (showNum / length) * 100;
			option = makeOptionScroll('', xAxis, yAxis, seriesName, typex, 100 - len, 100);
		}
	} else {
		option = makeOption('', xAxis, yAxis, seriesName, typex);
	}
	require([ 'echarts', 'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
	'echarts/chart/bar' ], function(es) {
		var eview = es.init(document.getElementById(id));
		eview.setOption(option);
	});
}
function makeOptionScroll(title, xAxis, yAxis, seriesName, typex, startZoom, endZoom) {
	var opt = makeOption(title, xAxis, yAxis, seriesName, typex);
	opt.dataZoom = {
		show : true,
		realtime : true,
		start : startZoom,
		end : endZoom
	};
	return opt;
}

function makeOption(title, xAxis, yAxis, seriesName, typex) {
	var max = 0;
	for (var i = 0; i < xAxis.length; i++) {
		max = max > xAxis[i].length ? max : xAxis[i].length;
		if (max > 8)
			break;
	}

	if (title == null || title.length < 1)
		title = '';
	var opt = {
		title : {
			text : title,
			subtext : ''
		},
		tooltip : {
			trigger : 'axis',
			axisPointer : { // 坐标轴指示器，坐标轴触发有效
				type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		legend : {
			data : [ seriesName ],// [ '文件个数', '数据大小(GB)' ]
		},
		toolbox : {
			show : true,
			feature : {
				dataView : {
					show : true,
					readOnly : false
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
			data : xAxis,
			scale : true,
			lenght : 15,
			axisLabel : {
				show : true,
				interval : 'auto', // {number}
				rotate : 0,
				margin : 8,
				formatter : '{value}',
				textStyle : {
					color : 'blue',
					fontFamily : 'sans-serif',
					fontSize : 12,
					fontStyle : 'italic',
					fontWeight : 'bold'
				}
			},
		} ],
		yAxis : [ {
			type : 'value',
			axisLabel : {
				show : true,
				interval : 'auto', // {number}
				rotate : 0,
				margin : 18,
				formatter : '{value}', // Template formatter!
				textStyle : {
					color : 'green',
					fontFamily : 'verdana',
					fontSize : 10,
					fontStyle : 'normal',
					fontWeight : 'bold'
				}
			},
		} ],
		dataZoom : {
			show : false,
		},
		series : [ {
			name : seriesName,
			type : typex,
			data : yAxis,
			itemStyle : {
				normal : {
					label : {
						show : true
					}
				}
			},
			markPoint : {
				data : [ {
					type : 'max',
					name : '最大值'
				}, {
					type : 'min',
					name : '最小值'
				} ]
			},
			markLine : {
				data : [ {
					type : 'average',
					name : '平均值'
				} ]
			}
		} ]
	};
	if (max >= 8) {
		opt.xAxis[0].axisLabel.rotate = 30;
	}
	if (seriesName.indexOf("大小") > 0) {
		var len = seriesName.length;
		var ustr = seriesName.substring(len - 3, len - 1)
		opt.yAxis[0].axisLabel.formatter = '{value}' + ustr;
	}
	return opt;
}
