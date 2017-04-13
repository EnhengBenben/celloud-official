$.reportChar = {};

/**-------- 报告画图 -------*/
$.reportChar.draw = {
    _require: function(chartName,option,id){
      require.config({
        paths: {
            echarts: '//cdn.bootcss.com/echarts/2.2.7/'
        }
      });
      require(
      [
            'echarts',
            'echarts/chart/' + chartName
        ],
        function (ec) {
            var myChart = ec.init(document.getElementById(id),macarons_theme); 
            myChart.setOption(option, true); 
      }
      );
    },
    
    _requireMulti: function(chartName,option,id){
        require.config({
          paths: {
              echarts: '//cdn.bootcss.com/echarts/2.2.7/'
          }
        });
        var chartArr = new Array();
        chartArr.push("echarts");
        for(var i = 0; i < chartName.length; i++){
        	chartArr.push("echarts/chart/" + chartName[i]);
        }
        require(chartArr,function (ec) {
              var myChart = ec.init(document.getElementById(id),macarons_theme); 
              myChart.setOption(option, true); 
        	}
        );
      },
    
    /** 
     * ========
     * 环形图 
     * ========
     */
    circularGraphDataOptions: {
      dataStyle: {
      normal: {
        label: {show:false},
        labelLine: {show:false}
      }
      },
      placeHolderStyle: {
      normal : {
            color: "rgba(0,0,0,0)",
            label: {show:false},
            labelLine: {show:false}
          },
          emphasis : {
            color: "rgba(0,0,0,0)"
          }
      },
      legendData: null,
      seriesData: null
    },
    circularGraph: function(id,title,subTitle,data){
      $.reportChar.circularGraphSeriesData(data);
      option = {
          title: {
              text: title,
              subtext: subTitle,
              sublink: '',
              x: 'center',
              y: 'center',
              itemGap: 5,
              textStyle : {
                  color : 'rgba(30,144,255,0.8)',
                  fontFamily : '微软雅黑',
                  fontSize : 16,
                  fontWeight : 'bolder'
              }
          },
          tooltip : {
              show: true,
              formatter: "{b}"
          },
          legend: {
              orient : 'vertical',
              x : $("#"+id).width() / 2-10,
              y : 30,
              itemGap:6,
              data: $.reportChar.draw.circularGraphDataOptions.legendData //['','','']要与series data的name对应
          },
          series : $.reportChar.draw.circularGraphDataOptions.seriesData
      };
      $.reportChar.draw._require('pie',option,id);
  },
  
  /**
   * 饼状图
   */
  echartsShowPie : function(id,title,data) {
        var option = {
            title : {
                text: title,
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            series : [
                {
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:data,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        $.reportChar.draw._require('pie',option,id);
  	},
  	
  	/**
  	 * 垂直柱状图
  	 */
  	echartsShowBar : function(id, title, X, Y, rotate, width, height) {
        var option = {
        		tooltip : {
        	        trigger: 'axis'
        	    },
        	    legend: {
        	        data:[title],
        	        top : 15
        	    },
        	    calculable : true,
        	    xAxis : [
        	        {
        	            type : 'category',
        	            data : X,
        	            position : 'bottom',
        	            axisLabel : {
        		        	rotate : rotate		        	
        		        }
        	        }
        	    ],
        	    yAxis : [
        	        {
        	            type : 'value',
        	            name : '样本数量'
        	        }
        	    ],
        	    series : [
        	        {
        	            name:title,
        	            type:'bar',
        	            data:Y,
        	            barWidth:20
        	        }
        	    ],
                color : ['#feabac'],
                grid : {
                	width : width,
                	height : height
                }
        };
        $.reportChar.draw._require('bar',option,id);
  	},
  	
  	/**
  	 * 水平柱状图
  	 */
  	echartsShowHorizontalBar : function(id, title, X, Y, width, height, xname) {
        var option = {
        		tooltip : {
        	        trigger: 'axis'
        	    },
        	    calculable : true,
        	    xAxis : [
        	        {
        	            type : 'value',
        		        name : xname
        	        }
        	    ],
        	    yAxis : [
        	        {
        	            type : 'category',
        	            data : Y,
        	        }
        	    ],
        	    series : [
        	        {
        	            name:title,
        	            type:'bar',
        	            data:X,
        	            barWidth:20
        	        }
        	    ],
                color : ['#feabac'],
                grid : {
                	width : width
                }
        };
        $.reportChar.draw._require('bar',option,id);
  	},
  	
  	/**
  	 * HBV基因型分类总览
  	 */
  	echartsShowHBVType : function(id, appId, hbvType, currentType, rotate,title,subTitle,serName,legend,xAxis1,xAxis2,yAxis,series) {
  		subtext = "";
  		genDat = "";
  		seriesName = "";
  		titleText = "";
  		legendData = "";
  		xAxisData1 = "";
  		xAxisData2 = "";
  		yAxisName = "";
  		seriesName = "";
  		if(appId == 82){
  			subtext = subTitle;
  			genDat = serName;
  			seriesName = serName;
  			titleText = title;
  			legendData = legend;
  			xAxisData1 = xAxis1;
        xAxisData2 = xAxis2;
        yAxisName = yAxis;
        seriesName = series;
  		}else {
  			subtext = "数据来源:CelLoud全部Sanger数据";
  			genDat = "Sanger基因型";
  			seriesName = "Sanger基因型";
  			titleText = "基因型分类总览表";
  			legendData = "当前数据基因型";
  			xAxisData1 = "比对失败";
        xAxisData2 = "异常数据";
        yAxisName = "基因型数量(个)";
        seriesName = "当前数据基因型";
  		}
        var option = {
    		title : {
    			text : titleText,
    			subtext : subtext,
    			x : 'center'
    		},
    		tooltip : {
    		    trigger: 'axis'
    		},
    		legend: {
    		    data:[genDat,legendData],
    		    y : 340
    		},
    		calculable : true,
    		xAxis : [
    		    {
    		        type : 'category',
    		        data : ['A','B','C','D','E','F','G','H',xAxisData1,xAxisData2],
    		        position : 'bottom',
    		        axisLabel : {
    		        	rotate : rotate		        	
    		        }
    		    }
    		],
    		yAxis : [
    		    {
    		        type : 'value',
    		        name : yAxisName
    		    }
    		],
    		series : [
    		    {
    		        name:seriesName,
    		        type:'bar',
    		        data:eval(hbvType),
    		        barWidth:30
    		    },
    		    {
                    name:seriesName,
                    type:'scatter',
                    data:eval(currentType),
                    barWidth:30,
                    symbol : 'triangle'
                }
    		],
    		grid : {
    		    width : 750,
    		    height : 250
    		},
            color : ['#00cccc','#ff0000']
        };
        $.reportChar.draw._requireMulti(['scatter','bar'],option,id);
  	},
  
  /**
   * ===============
   * 单根渐变色柱状图
   * ===============
   */
  singleBar: function(id,title,subTitle,data,yAxisName,seriesName){
    var zrColor = require('zrender/tool/color');
    var _xariaData = new Array();
    var _seriesData = new Array();
    for (var i = 0; i < data.length; i++) {
      var key = data[i][0];
      var value = data[i][1];
      var num_value = value!= ''?parseInt(value):0;
      _xariaData.push(key);
      _seriesData.push(num_value);
    }
    option = {
        title : {
            x: 'center',
            text: title,
            subtext: '',
            textStyle : {
                  color : 'rgba(30,144,255,0.8)',
                  fontFamily : '微软雅黑',
                  fontSize : 16,
                  fontWeight : 'bolder'
              }
        },
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                  type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
              }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                axisLabel : {
                  rotate : 10,
                  margin : 3,
                  interval : 0,
                  textStyle: {
                    align: 'right'
                  }
                },
                data: _xariaData
            }
        ],
        yAxis : [
            {
                type : 'value',
                name: yAxisName
            }
        ],
        series : [
            {
                name:seriesName,
                type:'bar',
                itemStyle: {
                    normal: {
                        color: function(params) { 
                          return zrColor.lift(
                            '#87cefa', params.dataIndex * 0.02
                          );
                        },
                        label: {
                            show: true,
                            position: 'top',
                            formatter: '{c}'
                        }
                    }
                },
                data: _seriesData
            }
        ]
    };
    $.reportChar.draw._require('bar',option,id);
  },
  /**
   * 使用echarts绘制散点图
   */
  echartsShowScatter: function(id,title,multi,single){
	  var option = {
		    title : {
		        text: title,
		        x:'center'
		    },
		    grid : {
		    	width : 220
		    },
		    tooltip : {
		        trigger: 'item',
		        axisPointer:{
		            show: true,
		            type : 'cross',
		            lineStyle: {
		                type : 'dashed',
		                width : 1
		            }
		        }
		    },
		    legend: {
		        data: ['All data','This one'],
		        x: 300,
		        y: 180
		    },
		    xAxis : [
		        {
		            type : 'value',
		            scale:true,
		            min : 0,
		            max : 2,
		            splitNumber : 2,
		            name : '序号',
		            nameLocation : 'middle',
		            nameGap : 20,
		            splitLine : {
		            	show : false
		            }
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            scale:true,
		            axisLabel : {
		                formatter: '{value}'
		            },
		            name : '值',
		        }
		    ],
		    series : [
		        {
		            name:'All data',
		            type:'scatter',
		            data: multi
		        },
		        {
		            name:'This one',
		            type:'scatter',
		            data: single
		        }
		    ]
		};
		$.reportChar.draw._require('scatter',option,id);
  }
  
  
};
/**
 * 环形图数据参数
 * @param list : [["16s_reads","4"],[],[]]
 */
$.reportChar.circularGraphSeriesData=function(list){
  var o = $.reportChar.draw.circularGraphDataOptions;
  o.legendData = new Array();
  o.seriesData = new Array();
  var radius_inner = 120;
  var radius_outer = 140;
  for (var i = 0; i < list.length; i++) {
    var _key = list[i][0];
    var _value = list[i][1];
    var num_value = _value!= ''?parseInt(_value):0;
    var data_name = _value+"% "+_key;
    o.legendData.push(data_name),
    o.seriesData.push({
        name:_key,
        type:'pie',
        clockWise:false,
        radius: [radius_inner, radius_outer],
        center: ['45%', '50%'],
        itemStyle : o.dataStyle,
        data:[
            {
                value:num_value,
                name:data_name
            },
            {
                value:100-num_value,
                name:data_name,
                tooltip:null,
                itemStyle : o.placeHolderStyle
            }
        ]
      });
      if(radius_inner>0){
        radius_inner-=20;
        radius_outer-=20;
      }
  }
};
//数据参数同比画点图
function drawScatter(id,totaldata,thisdata,title,xAxisName,yAxisName){
  require(
      [
          'echarts',
          'echarts/chart/scatter' // 使用柱状图就加载bar模块，按需加载
      ],
      function (ec) {
          // 基于准备好的dom，初始化echarts图表
          var myChart = ec.init(document.getElementById(id),macarons_theme); 
          
          option = {
          title : {
              text: title,
              subtext: '结果来自: CelLoud全部数据',
              y: 0
          },
          grid: {
              y: 60
          },
          tooltip : {
              trigger: 'axis',
              showDelay : 0,
              formatter : function (params) {
                  if (params.value.length > 1) {
                      return params.seriesName + ' :<br/>'
                         + params.value[0] + ' ' 
                         + params.value[1] + ' ';
                  }
                  else {
                      return params.seriesName + ' :<br/>'
                         + params.name + ' : '
                         + params.value + ' ';
                  }
              },  
              axisPointer:{
                  show: true,
                  type : 'cross',
                  lineStyle: {
                      type : 'dashed',
                      width : 1
                  }
              }
          },
          legend: {
              data:['全部数据','当前数据']
          },
          toolbox: {
              show : true,
              feature : {
                  dataZoom : {show: true},
                  restore : {show: true}
              }
          },
          xAxis : [
              {
                  type : 'value',
                  name : xAxisName,
                  scale:true,
                  axisLabel : {
                      formatter: '{value} '
                  }
              }
          ],
          yAxis : [
              {
                  type : 'value',
                  name : yAxisName,
                  scale:true,
                  axisLabel : {
                      formatter: '{value} '
                  }
              }
          ],
          series : [
              {
                  name:'全部数据',
                  type:'scatter',
                  data: totaldata,
                  markPoint : {
                      data : [
                          {type : 'max', name: '最大值'},
                          {type : 'min', name: '最小值'}
                      ]
                  },
                  markLine : {
                      data : [
                          {type : 'average', name: '平均值'}
                      ]
                  }
              },
              {
                  name:'当前数据',
                  type:'scatter',
                  data: thisdata,
                  symbol:'star',
                  symbolSize: 10,
                  markPoint : {
                      data : [
                          {type : 'average', name: '当前数据平均值'}
                      ]
                  }
              }
          ]
      };
          // 为echarts对象加载数据 
          myChart.setOption(option, true); 
      }
  );
}

var macarons_theme = {
    // 默认色板
    color: [
        '#2ec7c9','#9a7fd1','#5ab1ef','#ffb980','#d87a80',
        '#8d98b3','#e5cf0d','#97b552','#95706d','#dc69aa',
        '#07a2a4','#b6a2de','#588dd5','#f5994e','#c05050',
        '#59678c','#c9ab00','#7eb00a','#6f5553','#c14089'
    ],

    // 图表标题
    title: {
        textStyle: {
            fontWeight: 'normal',
            color: '#008acd'          // 主标题文字颜色
        }
    },
    
    // 值域
    dataRange: {
        itemWidth: 15,
        color: ['#5ab1ef','#e0ffff']
    },

    // 工具箱
    toolbox: {
        color : ['#1e90ff', '#1e90ff', '#1e90ff', '#1e90ff'],
        effectiveColor : '#ff4500'
    },

    // 提示框
    tooltip: {
        backgroundColor: 'rgba(50,50,50,0.5)',     // 提示背景颜色，默认为透明度为0.7的黑色
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'line',         // 默认为直线，可选为：'line' | 'shadow'
            lineStyle : {          // 直线指示器样式设置
                color: '#008acd'
            },
            crossStyle: {
                color: '#008acd'
            },
            shadowStyle : {                     // 阴影指示器样式设置
                color: 'rgba(200,200,200,0.2)'
            }
        }
    },

    // 区域缩放控制器
    dataZoom: {
        dataBackgroundColor: '#efefff',            // 数据背景颜色
        fillerColor: 'rgba(154,127,209,0.2)',   // 填充颜色
        handleColor: '#008acd'    // 手柄颜色
    },

    // 网格
    grid: {
        borderColor: '#eee'
    },

    // 类目轴
    categoryAxis: {
        axisLine: {            // 坐标轴线
            lineStyle: {       // 属性lineStyle控制线条样式
                color: '#008acd'
            }
        },
        splitLine: {           // 分隔线
            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                color: ['#eee']
            }
        }
    },

    // 数值型坐标轴默认参数
    valueAxis: {
        axisLine: {            // 坐标轴线
            lineStyle: {       // 属性lineStyle控制线条样式
                color: '#008acd'
            }
        },
        splitArea : {
            show : true,
            areaStyle : {
                color: ['rgba(250,250,250,0.1)','rgba(200,200,200,0.1)']
            }
        },
        splitLine: {           // 分隔线
            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                color: ['#eee']
            }
        }
    },

    polar : {
        axisLine: {            // 坐标轴线
            lineStyle: {       // 属性lineStyle控制线条样式
                color: '#ddd'
            }
        },
        splitArea : {
            show : true,
            areaStyle : {
                color: ['rgba(250,250,250,0.2)','rgba(200,200,200,0.2)']
            }
        },
        splitLine : {
            lineStyle : {
                color : '#ddd'
            }
        }
    },

    timeline : {
        lineStyle : {
            color : '#008acd'
        },
        controlStyle : {
            normal : { color : '#008acd'},
            emphasis : { color : '#008acd'}
        },
        symbol : 'emptyCircle',
        symbolSize : 3
    },

    // 柱形图默认参数
    bar: {
        itemStyle: {
            normal: {
                barBorderRadius: 5
            },
            emphasis: {
                barBorderRadius: 5
            }
        }
    },

    // 折线图默认参数
    line: {
        smooth : true,
        symbol: 'emptyCircle',  // 拐点图形类型
        symbolSize: 3           // 拐点图形大小
    },
    
    // K线图默认参数
    k: {
        itemStyle: {
            normal: {
                color: '#d87a80',       // 阳线填充颜色
                color0: '#2ec7c9',      // 阴线填充颜色
                lineStyle: {
                    color: '#d87a80',   // 阳线边框颜色
                    color0: '#2ec7c9'   // 阴线边框颜色
                }
            }
        }
    },
    
    // 散点图默认参数
    scatter: {
        symbol: 'circle',    // 图形类型
        symbolSize: 4        // 图形大小，半宽（半径）参数，当图形为方向或菱形则总宽度为symbolSize * 2
    },

    // 雷达图默认参数
    radar : {
        symbol: 'emptyCircle',    // 图形类型
        symbolSize:3
        //symbol: null,         // 拐点图形类型
        //symbolRotate : null,  // 图形旋转控制
    },

    map: {
        itemStyle: {
            normal: {
                areaStyle: {
                    color: '#ddd'
                },
                label: {
                    textStyle: {
                        color: '#d87a80'
                    }
                }
            },
            emphasis: {                 // 也是选中样式
                areaStyle: {
                    color: '#fe994e'
                }
            }
        }
    },
    
    force : {
        itemStyle: {
            normal: {
                linkStyle : {
                    color : '#1e90ff'
                }
            }
        }
    },

    chord : {
        itemStyle : {
            normal : {
                borderWidth: 1,
                borderColor: 'rgba(128, 128, 128, 0.5)',
                chordStyle : {
                    lineStyle : {
                        color : 'rgba(128, 128, 128, 0.5)'
                    }
                }
            },
            emphasis : {
                borderWidth: 1,
                borderColor: 'rgba(128, 128, 128, 0.5)',
                chordStyle : {
                    lineStyle : {
                        color : 'rgba(128, 128, 128, 0.5)'
                    }
                }
            }
        }
    },

    gauge : {
        axisLine: {            // 坐标轴线
            lineStyle: {       // 属性lineStyle控制线条样式
                color: [[0.2, '#2ec7c9'],[0.8, '#5ab1ef'],[1, '#d87a80']], 
                width: 10
            }
        },
        axisTick: {            // 坐标轴小标记
            splitNumber: 10,   // 每份split细分多少段
            length :15,        // 属性length控制线长
            lineStyle: {       // 属性lineStyle控制线条样式
                color: 'auto'
            }
        },
        splitLine: {           // 分隔线
            length :22,         // 属性length控制线长
            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                color: 'auto'
            }
        },
        pointer : {
            width : 5
        }
    },
    
    textStyle: {
        fontFamily: '微软雅黑, Arial, Verdana, sans-serif'
    }
};