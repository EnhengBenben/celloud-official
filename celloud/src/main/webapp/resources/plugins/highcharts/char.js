var DATAPATH = "/share/data/count/";
//var DATAPATH = "D:\\count\\";
var charMap = {};
charMap[81] = "totalReads,duplicate";
charMap[83] = "totalReads,duplicate";
charMap[85] = "totalReads,duplicate,gcCount";
charMap[86] = "totalReads,duplicate,gcCount";
charMap[87] = "totalReads,duplicate,sd";
charMap[88] = "totalReads,duplicate,sd";
charMap[122] = "totalReads,duplicate,sd";
charMap[91] = "totalReads,duplicate,gcCount";
charMap[92] = "totalReads,duplicate,sd";
charMap[93] = "totalReads,duplicate,sd";
charMap[94] = "totalReads,duplicate,gcCount";

function showChar(id, title, data, single) {
	$("#" + id).highcharts({
		chart : {
			type : 'scatter',
			zoomType : 'xy'
		},
		title : {
			text : title
		},
        credits:{
        	enabled:false//默认true
        },
		xAxis : {
			title : {
				enabled : true,
				text : '序号'
			},
			startOnTick : true,
			endOnTick : true,
			showLastLabel : true
		},
		yAxis : {
			title : {
				text : '值'
			}
		},
		legend : {
			layout : 'vertical',
			align : 'right',
			verticalAlign : 'middle',
			borderWidth : 0
		},
		plotOptions : {
			scatter : {
				marker : {
					radius : 5,
					states : {
						hover : {
							enabled : true,
							lineColor : 'rgb(100,100,100)'
						}
					}
				},
				states : {
					hover : {
						marker : {
							enabled : false
						}
					}
				},
				tooltip : {
					headerFormat : '<b>{series.name}</b><br>',
					pointFormat : '{point.x} , {point.y} '
				}
			}
		},
		series : [ {
			name : 'All Data',
			color : 'rgba(223, 83, 83, .5)',
			data : data
		} ,{
			name : 'This One',
			color:'blue',
			data : single,
		}]
	});
}

function showHBVType(id, type, aType){
	$("#" + id).highcharts({
        chart: {
            type: 'column',
            borderColor: '#4572A7' //边框颜
        },
        title: {
            text: '基因型分类总览表'
        },
        subtitle: {
            text: '数据来源:CelLoud全部HBV数据'
        },
        credits:{
        	enabled:true,//默认true
        	text:"",//显示内容
        	href:"javascript:void(0)"
        },
        xAxis: {
            categories: ['A','B','C','D','E','F','G','H','比对失败','异常数据'],
        	lineWidth:1,//纵轴一直为空所对应的轴，即X轴
        	plotLines: [{//一条竖线
	        	color: '#C0D0E0',
	        	width: 1,
	        	value: -0.5
        	}],
    	    labels: {//设置横轴坐标的显示样式  
	   	        rotation: -30,//倾斜度
	   	        x:5,
	   	        y:10
   	        }
        },
        yAxis: {
            min: 0,
            allowDecimals:false,//去掉小数点
            title: {
                text: '基因型数量 (个)'
            } 
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}数量: </td>' +
                '<td style="padding:0"><b>{point.y}</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true,
            crosshairs: [false, //十字线
        	{
            	enabled:true,//是否显示Y轴标线
            	width:1,//标线宽度
            	dashStyle:'ShortDash',
            	color:'green' //标线颜色值
        	}]
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            },
            scatter: {
                marker: {
                	radius:6,
                    symbol: 'triangle',
                    fillColor:"#CC3333",
                    lineColor:"#fff", 
                    lineWidth: 1,
                    states:{  
                      hover:{  
                          enabled:true//鼠标放上去点是否放大  
                      }
                    }  
                },
                tooltip: {
                	headerFormat: '<b>当前数据基因型：{point.key}</b> ',
                	pointFormat: ''
            	}	
            }
        },
        series: [{
            name: 'HBV基因型',
            data: type
        }, {
            type: 'scatter',
            name: '当前数据基因型',
            symbol: 'triangle',
            data: aType
        }]
    });
}
function showHBVny(id, type, aType, X){
	$("#" + id).highcharts({
        chart: {
            type: 'column',
            borderColor: '#4572A7' //边框颜
        },
        title: {
            text: 'HBV药物连耐统计'
        },
        subtitle: {
            text: '数据来源:CelLoud全部HBV数据'
        },
        credits:{
        	enabled:true,//默认true
        	text:"",//显示内容
        	href:"javascript:void(0)"
        },
        xAxis: {
            categories: X,
        	lineWidth:1,//纵轴一直为空所对应的轴，即X轴
        	plotLines: [{//一条竖线
	        	color: '#C0D0E0',
	        	width: 1,
	        	value: -0.5
        	}],
    	    labels: {//设置横轴坐标的显示样式  
	   	        rotation: -30,//倾斜度
	   	        x:5,
	   	        y:10
   	        }
        },
        yAxis: {
            min: 0,
            allowDecimals:false,//去掉小数点
            title: {
                text: '耐药数据数量 (个)'
            } 
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}数量: </td>' +
                '<td style="padding:0"><b>{point.y}</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true,
            crosshairs: [false, //十字线
        	{
            	enabled:true,//是否显示Y轴标线
            	width:1,//标线宽度
            	dashStyle:'ShortDash',
            	color:'green' //标线颜色值
        	}]
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            },
            scatter: {
                marker: {
                	radius:6,
                    symbol: 'triangle',
                    fillColor:"#CC3333",
                    lineColor:"#fff", 
                    lineWidth: 1,
                    states:{  
                      hover:{  
                          enabled:true//鼠标放上去点是否放大  
                      }
                    }  
                },
                tooltip: {
                	headerFormat: '<b>当前数据耐药情况：{point.key}</b> ',
                	pointFormat: ''
            	}	
            }
        },
        series: [{
            name: '常见药物连耐(以_分割)',
            data: type
        }, {
            type: 'scatter',
            name: '当前数据耐药情况',
            symbol: 'triangle',
            data: aType
        }]
    });
}

function showCharHCV(id, title, X, Y,rotation) {
	$("#" + id).highcharts({
		chart : {
			type : 'column',
			zoomType : 'xy'
		},
		//去掉标题，不能注释，否则有默认标题
		title : {
			text : ''
		},
        credits:{
        	enabled:true,//默认true
        	text:"CelLoud",//显示内容
        	href:"javascript:void(0)"
        },
        xAxis: {
        	allowDecimals:false,//允许小数
            categories: X,
	        labels: {//设置横轴坐标的显示样式  
	          rotation: rotation,//倾斜度
	          x:5,
	          y:10
	        }
        },
		yAxis : {
			title : {
				text : '样本数量'
			}
		},
		plotOptions : {
			scatter : {
				marker : {
					radius : 5,
					states : {
						hover : {
							enabled : true,
							lineColor : 'rgb(100,100,100)'
						}
					}
				},
				states : {
					hover : {
						marker : {
							enabled : false
						}
					}
				},
				tooltip : {
					headerFormat : '',
					pointFormat : '{point.y} '
				}
			}
		},
		legend : {
			layout : 'vertical',
			align : 'center',
			verticalAlign : 'top',
			borderWidth : 0
		},
		series : [ {
			name :title,
			color : 'rgba(223, 83, 83, .5)',
			data : Y
		} ]
	});
}

//function showPie(id, title, data) {
//	$('#'+id).highcharts({
//        chart: {
//            plotBackgroundColor: null,
//            plotBorderWidth: null,
//            plotShadow: false
//        },
//        title: {
//            text: title
//        },
//        tooltip: {
//    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
//        },
//        plotOptions: {
//            pie: {
//                allowPointSelect: true,
//                cursor: 'pointer',
//                dataLabels: {
//                    enabled: true,
//                    color: '#000000',
//                    connectorColor: '#000000',
//                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
//                }
//            }
//        },
//        series: [{
//            type: 'pie',
//            name: 'Browser share',
//            data: data
//        }]
//    });
//}