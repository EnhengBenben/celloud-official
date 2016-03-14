var themes = {
			"blue" : blue,
			"gray" : gray,
			"green" : green,
			"helianthus" : helianthus,
			"infographic" : infographic,
			"macarons" : macarons,
			"macarons2" : macarons2,
			"mint" : mint,
			"red" : red,
			"roma" : roma,
			"sakura" : sakura,
			"shine" : shine
		};
var drawCharts=(function(drawCharts){
	var self=drawCharts||{};
	
	//地图
	self.mapchars=function(data,id,yname) {
		var myChart = echarts.init(document.getElementById(id));
		option = {
			title : {
				text : '',
				x : 'center'
			},
			tooltip : {
				trigger : 'item'
			},
			roamController : {
				show : true,
				x : 'right',
				mapTypeControl : {
					'china' : true
				}
			},
			dataRange : {
				x : 'right',
				y : 'bottom',
				min : 0,
				max : 20,
				calculable : true,
				color : [ 'maroon', 'purple', 'red', 'orange' ]
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
				name : 'company',
				type : 'map',
				mapType : 'china',
				roam : false,
				mapLocation : {
					x : 60,
					width : "600",
					height : "500"
				},
				hoverable : false,
				data : [],
				scaleLimit : {
					min : 0.9,
					max : 1.1
				},
				selectedMode : 'single',
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
				name : yname,
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
	
	
	return self;
})(drawCharts);

/**
 * make echart option factorys
 */
var lineType = 'line';
var barType = 'bar';
/**
 * @param title
 * @param xAxis
 * @param yAxis
 * @param seriesName
 * @param typex
 * @param startZoom
 * @returns {___anonymous243_245}
 */
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
/**
 * @param xAxis
 *            值
 * @param yAxis值
 * @param seriesName
 *            legend
 * @param typex
 *            line bar
 * @param positon
 *            显示开始位置
 * @param showNum
 *            要显示多少个 showNum小于等于length不显示TｉｍｅＬｉｎｅ
 * @param length
 *            全部有多少个 *
 * @param isArea
 *            曲线图时是否阴影
 * @param showPoint{}
 *            是否显示最大最小值
@param showItem
 *            是否显示各点数据默认显示 
 */
function makeOptionScrollUnit(xAxis, yAxis, seriesName, typex, position, showNum, isArea, showPoint,showItem) {
	var length =xAxis!=null? xAxis.length:0;
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
	option = addArea(option, isArea);
	option = addMakePoint(option,showPoint);
	if(showItem!=null){
		if(option.series[0].itemStyle.normal)
		option.series[0].itemStyle.normal.label.show=false;
	}
	return option;
}
function addMakePoint(option, showPoint) {
	if (showPoint != null) {
		option.series[0].data = [ {
			type : 'max',
			name : '最大值'
		}, {
			type : 'min',
			name : '最小值'
		} ];
	}
	return option;
}
/**
 * 添加拆线图
 * 
 * @param option
 * @param yAxis
 * @param serierName
 * @param typex
 * @param isArear
 *            是否阴影
 * @returns
 */
function makeOptionAdd(option, yAxis, serierName, typex, isArear) {
	option.series[option.series.length] = {
		name : serierName,
		type : typex,
		data : yAxis,
		smooth : "false",
		itemStyle : {}
	};
	option.legend.data[option.legend.data.length] = serierName;
	option = addArea(option, isArear);
	return option;
}

function addArea(option, isArear) {
	if (isArear != null) {
		option.series[option.series.length - 1].itemStyle.normal = {
			areaStyle : {
				type : 'default'
			}
		};
	}
	return option;
}
/**
 * @param title
 *            标题
 * @param legenName
 * @param name
 * @param rad
 * @param centerX
 *            左右位置 ％比
 * @param centerY
 *            上下位置 ％比
 * @param value {}
 * @param legendPosition
 *            {x,orient}
 * @returns
 */

function makePieOption(title, legenName, name, rad, centerX, centerY, value, legendPosition) {
	var legendObj = {
		show:true,
		orient : 'vertical',// horizontal
		x : 'left',
		y : "top",
		data : legenName,
	};
	if (legendPosition != null) {
		legendObj.x = legendPosition.x != null ? legendPosition.x : 'left';
		legendObj.y = legendPosition.y != null ? legendPosition.y : 'top';
		legendObj.orient = legendPosition.orient != null ? legendPosition.orient : 'vertical';
	}else{
		legendObj.show = false;
	}
	
	var opt = {
		title : {
			text : '',
			subtext : ''
		},
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		legend : legendObj,
		toolbox : {
			orient : 'vertical',
			x : 'right',
			show : true,
			feature : {
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		series : [ {
			name : name,
			type : 'pie',
			radius : rad,// '70%',
			center : [ centerX, centerY ],// [ '40%', '40%' ],
			data : value,
	
		} ]
	};
	return opt;
}
function makePieOptionAdd(option,name,rad, centerX, centerY, value){
	option.series[option.series.length]={
			name : name,
			type : 'pie',
			radius : rad,// '70%',
			center : [ centerX, centerY ],// [ '40%', '40%' ],
			data : value
		};
	return option;
}
/**
 * title ='用户统计' legendList= [ '文件个数', '数据大小(GB)' ] xAxis yAxis seriesName typex
 * :line/bar
 */
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
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		xAxis : [ {
			type : 'category',
			position : 'bottom',
			data : xAxis,
			scale : true,
			barMaxWidth : 100,
			lenght : 15,
			axisLabel : {
				show : true,
				interval : 'auto', // {number}
				rotate : 0,
				margin : 8,
				formatter : '{value}',
				textStyle : {
					align:'center',
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
					fontSize : 12,
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
			smooth : "false",
			barMaxWidth : 100,
			itemStyle : {
				normal : {
					label : {
						show : true,
						textStyle : {
							fontSize : 14,
							fontWeight : 'bolder',
						}
					}
				},
			},
			markPoint : '',
			markLine : {
				data : [ {
					type : 'average',
					name : '平均值'
				} ]
			}
		} ]
	};
	if (max >= 8 && xAxis.length > 10) {
		opt.xAxis[0].axisLabel.rotate = 0;
		opt.xAxis[0].axisLabel.show= false;
	} else {
		opt.xAxis[0].axisLabel.rotate = 0;
	}
	if (seriesName.indexOf("大小") > 0) {
		var len = seriesName.length;
		var ustr = seriesName.substring(len - 3, len - 1)
		opt.yAxis[0].axisLabel.formatter = '{value}' + ustr;
	}
	return opt;
}

