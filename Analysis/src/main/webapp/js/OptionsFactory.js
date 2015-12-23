/**
 * make echart option factorys
 */

/**
 * title ='用户统计'
 *  legendList= [ '文件个数', '数据大小(GB)' ]
 *  xAxis
 *  yAxis
 *  seriesName 
 *  typex :line/bar
 */
function makeOption(title,xAxis,yAxis,seriesName,typex){
	if(title==null||title.length<1)title='';
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
				data : [seriesName],// [ '文件个数', '数据大小(GB)' ]
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
			dataZoom : {
				show : true,
				realtime : true,
				start : 50,
				end : 100
			},
			xAxis : [ {
				type : 'category',
				data : xAxis,
				scale : true,
				lenght : 15,
			} ],
			yAxis : [ {
				type : 'value'
			}],
			series : [ {
				name :seriesName,
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




