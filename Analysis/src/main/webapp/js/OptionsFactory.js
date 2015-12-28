/**
 * make echart option factorys
 */
var lineType = 'line';
var barType = 'bar';
/**
 * 
 * @param title
 * @param xAxis
 * @param yAxis
 * @param seriesName
 * @param typex
 * @param startZoom
 * @param endZoom
 * @returns {___anonymous243_245}
 */
function makeOptionScroll(title, xAxis, yAxis, seriesName, typex, startZoom, endZoom) {
	var opt = makeOption(title, xAxis, yAxis, seriesName, typex);
	opt.dataZoom = {
		show : true,
		realtime : true,
		start : startZoom,
		end : endZoom
	}, console.log(opt);
	return opt;
}
/**
 * 
 * @param xAxis 值
 * @param yAxis值
 * @param seriesName legend
 * @param typex line bar 
 * @param positon 显示开始位置
 * @param showNum  要显示多少个
 * @param length 全部有多少个
 */
function makeOptionScrollUnit(xAxis, yAxis, seriesName, typex, position,showNum, length){
	var len =position+ (showNum/length)*100;
	return makeOptionScroll('',xAxis,yAxis,seriesName,typex,position,len);
}

/**
 * 
 * @param title 标题
 * @param legenName 
 * @param name 
 * @param rad
 * @param centerX 左右位置 ％比
 * @param centerY 上下位置 ％比
 * @param value {}
 * @returns
 */

function makePieOption(title, legenName, name, rad, centerX, centerY, value) {
	var opt = {
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
			data : legenName,
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
		series : [ {
			name : name,
			type : 'pie',
			radius : rad,// '70%',
			center : [ centerX, centerY ],// [ '40%', '40%' ],
			data : value
		} ]
	};
	return opt;
}
/**
 * title ='用户统计' 
 * legendList= [ '文件个数', '数据大小(GB)' ]
 *  xAxis 
 *  yAxis 
 *  seriesName 
 *  typex :line/bar
 */
function makeOption(title, xAxis, yAxis, seriesName, typex) {
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
		} ],
		yAxis : [ {
			type : 'value'
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
	return opt;
}
