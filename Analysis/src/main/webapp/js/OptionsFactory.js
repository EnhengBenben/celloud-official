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
					fontFamily : 'sans-serif',
					fontSize : 12,
					fontStyle : 'italic',
					fontWeight : 'bold',
					align : 'left'
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
