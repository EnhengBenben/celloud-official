var BrowserURL = "home!totalBrowser";
var UserRunNumURL = "home!userRunNum";
var AppRunNumURL = "home!appRunNum";
var LoginNumURL = "home!loginNum"

// auto load
UserRunNum();
AppRunNum();
LoadBrowser();
LoginNum();
//
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
		var option = makeOptionScrollUnit( xAxis, yAxis, '登陆次数', 'bar', 0, 20);
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

function UserRunNum() {
	var viewId = "UserRunNum";
	$.get(UserRunNumURL, {}, function(data) {
		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);
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
		data = data==null?[]:data;
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
		var opt = makePieOption('', legendName, '客户端使用', '70%', '50%', '55%', vlist);
		var myChart = echarts.init(document.getElementById((viewId)));
		myChart.setOption(opt);
	});
}
function chars(data) {
	var myChart = echarts.init(document.getElementById('map'));
	option = {
		title : {
			text : '',
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
			scaleLimit:{min:0.9,max:1.1},
			selectedMode : 'multiple',
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
				"安徽" : [ 117.25, 30.83 ],
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
				"香港" : [ 115.08, 22.2 ],
				"澳门" : [ 114.33, 21.33 ],
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