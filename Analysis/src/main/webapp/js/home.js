//function url
//var loadHistoryURL = "home!getHistory";

var BrowserURL = "home!totalBrowser";
var UserRunNumURL = "home!userRunNum";
var AppRunNumURL = "home!appRunNum";
var LoginNumURL = "home!loginNum"

// auto load
UserRunNum();
AppRunNum();
LoadBrowser();
LoginNum();
// /
function tableswitch(id) {
	$("#" + id).toggle();
}

function LoginNum() {
	var viewId = "loginId";
	$.get(LoginNumURL, {}, function(data) {
		console.log(data);
		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);
		var t;
		for (var i = 0; i < data.length; i++) {
			xAxis[i] = data[i].user_name;
			yAxis[i] = data[i].logNum;
		}
		var option = makeOptionScroll('', xAxis, yAxis, '登陆次数', 'bar', 0, 25);
		var myChart = echarts.init(document.getElementById(viewId));
		myChart.setOption(option);
	});
}

function AppRunNum() {
	var viewId = "AppRunNum";
	$.get(AppRunNumURL, {}, function(data) {
		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);
		var t;
		for (var i = 0; i < data.length; i++) {
			xAxis[i] = data[i].app_name;
			yAxis[i] = data[i].runNum;
		}
		var option = makeOptionScroll('', xAxis, yAxis, '运行次数', 'bar', 0, 50);
		var myChart = echarts.init(document.getElementById(viewId));
		myChart.setOption(option);
	});
}
/*
 * function loadHistory() { $.get(loadHistoryURL, {}, function(res) { var viewId =
 * 'historyId'; var xAxis = new Array(res.length); var logNum = new
 * Array(res.length); var activityUesr = new Array(res.length); var appRunNum =
 * new Array(res.length); var dataSize = new Array(res.length); var activityApp =
 * new Array(res.length); var legendTitle = [ "登陆次数", "活跃用户数", "App运行次数",
 * "活跃App数量", "数据大小(MB)" ]; for (var i = 0; i < res.length; i++) { xAxis[i] =
 * res[i].time; logNum[i] = res[i].logNum; activityUesr[i] =
 * res[i].activityUser; appRunNum[i] = res[i].runNum; activityApp[i] =
 * res[i].activityApp; dataSize[i] = parseFloat((res[i].dataSize / (1024 *
 * 1024)) .toFixed(2)); } option = { tooltip : { trigger : 'axis', axisPointer : {
 * type : 'shadow', } }, legend : { data : legendTitle, }, toolbox : { show :
 * true, feature : { magicType : { show : true, type : [ 'bar', ' line' ] },
 * restore : { show : true }, saveAsImage : { show : true } } }, calculable :
 * true, xAxis : [ { type : 'category', data : xAxis, axisLabel : { rotate : 60 } } ],
 * yAxis : [ { type : 'value', name : "数量" }, { type : 'value', name : "数据大小",
 * axisLabel : { formatter : '{value} (GB)' } } ], series : [ { name :
 * legendTitle[0], type : 'bar', data : logNum, markLine : { data : [ { type :
 * 'average', name : '平均值' } ] }, itemStyle : { normal : { label : { show : true } } },
 * markPoint : { data : [ { type : 'max', name : '最大值' }, ] }, }, { name :
 * legendTitle[1], type : 'bar', data : activityUesr, itemStyle : { normal : {
 * label : { show : true } } }, markPoint : { data : [ { type : 'max', name :
 * '最大值' } ] },startZoom }, { name : legendTitle[2], type : 'bar', data :
 * appRunNum, itemStyle : { normal : { label : { show : true } } }, markPoint : {
 * data : [ { type : 'max', name : '最大值' } ] }, }, { name : legendTitle[3], type :
 * 'bar', data : activityApp, itemStyle : { normal : { label : { show : true } } },
 * markLine : { data : [ { type : 'average', name : '平均值' } ] }, markPoint : {
 * data : [ { type : 'max', name : '最大值' } ] }, }, { name : legendTitle[4], type :
 * 'bar', data : dataSize, itemStyle : { normal : { label : { show : true } } },
 * markPoint : { data : [ { type : 'max', name : '最大值' } ] }, } ] }; var myChart =
 * echarts.init(document.getElementById(viewId)); myChart.setOption(option); }); }
 */

function UserRunNum() {
	var viewId = "UserRunNum";
	$.get(UserRunNumURL, {}, function(data) {
		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);
		var t;
		for (var i = 0; i < data.length; i++) {
			xAxis[i] = data[i].userName;
			yAxis[i] = data[i].runNum;
		}
		var option = makeOptionScroll('', xAxis, yAxis, '运行次数', 'bar', 0, 25);
		var myChart = echarts.init(document.getElementById(viewId));
		myChart.setOption(option);
	});
}

// app运行次数统计
function LoadBrowser() {
	var viewId = "browserDistribute";
	if (document.getElementById(viewId) == null)
		return;
	
	$.get(BrowserURL, {}, function(data) {
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
		var opt = makePieOption('', legendName, '客户端使用', '80%', '40%', '55%', vlist);
		var myChart = echarts.init(document.getElementById((viewId)));
		myChart.setOption(opt);
	});
}
function chars(data) {
	var myChart = echarts.init(document.getElementById('map'));
	option = {
		title : {
			text : '医院用户所分布区域',
			x : 'center'
		},
		tooltip : {
			trigger : 'item'
		},
		dataRange : {
			min : 0,
			max : 10,
			calculable : true,
			color : [ 'maroon', 'purple', 'red', 'orange', 'yellow', 'lightgreen' ]
		},
		toolbox : {
			show : true,
			orient : 'vertical',
			x : 'right',
			y : 'center',
			feature : {
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		series : [ {
			name : 'pm2.5',
			type : 'map',
			mapType : 'china',
			hoverable : false,
			roam : true,
			data : [],
			itemStyle : {
				normal : {
					label : {
						show : true
					}
				},
				emphasis : {
					label : {
						show : true
					}
				}
			},
			geoCoord : {
				"北京" : [ 116.4, 39.9 ],
				"天津" : [ 117.2, 39.12 ],
				"上海" : [ 121.47, 31.23 ],
				"重庆" : [ 106.55, 29.57 ],
				"河北" : [ 114.52, 38.05 ],
				"山西" : [ 112.55, 37.87 ],
				"辽宁" : [ 123.43, 41.8 ],
				"吉林" : [ 125.32, 43.9 ],
				"黑龙江" : [ 126.53, 45.8 ],
				"江苏" : [ 118.78, 32.07 ],
				"浙江" : [ 120.15, 30.28 ],
				"安徽" : [ 117.25, 31.83 ],
				"福建" : [ 119.3, 26.08 ],
				"江西" : [ 115.85, 28.68 ],
				"山东" : [ 116.98, 36.67 ],
				"河南" : [ 113.62, 34.75 ],
				"湖北" : [ 114.3, 30.6 ],
				"湖南" : [ 112.93, 28.23 ],
				"广东" : [ 113.27, 23.13 ],
				"海南" : [ 110.32, 20.03 ],
				"四川" : [ 104.07, 30.67 ],
				"贵州" : [ 106.63, 26.65 ],
				"云南" : [ 102.72, 25.05 ],
				"陕西" : [ 108.93, 34.27 ],
				"甘肃" : [ 103.82, 36.07 ],
				"青海" : [ 101.78, 36.62 ],
				"西藏自治区" : [ 91.13, 29.65 ],
				"宁夏回族自治区" : [ 106.28, 38.47 ],
				"广西壮族自治区" : [ 108.37, 22.82 ],
				"新疆维吾尔" : [ 87.62, 43.82 ],
				"内蒙古自治区" : [ 111.73, 40.83 ],
				"香港" : [ 114.08, 22.2 ],
				"澳门" : [ 113.33, 22.13 ],
				"台北市" : [ 121.5, 25.03 ]
			}
		}, {
			name : '医院',
			type : 'map',
			mapType : 'china',
			data : [],
			markPoint : {
				symbol : 'emptyCircle',
				symbolSize : function(v) {
					return 10 + v / 100
				},
				effect : {
					show : true,
					shadowBlur : 0
				},
				data : data
			}
		} ]
	};
	myChart.setOption(option);
}
$(document).ready(function() {
	$.get("company!getProvince", function(result) {
		var data = "[";
		for ( var i in result) {
			var map = result[i];
			data += "{name: '" + map['province'] + "', value: " + map['num'] + "},";
		}
		data += "]";
		chars(eval(data));
	});
});