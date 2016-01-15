<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div class="row">
	<div class="col-xs-12">
		<h3 class="header smaller lighter green">用户地理分布</h3>
		<div class="col-sm-10" style="height: 500px;" id="map"></div>
	</div>
</div>
<div id="main" style="height: 400px; width: 80%; border: 1px solid #ddd; margin: 20px"></div>
<script type="text/javascript">
	function showView(data) {
		option = {
			title : {
				text : '',
				x : 'center'
			},
			tooltip : {
				trigger : 'item'
			},
			dataRange : {
				x : 'right',
				y : 'bottom',
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
				mapLocation : {
					x : 60,
					width : "600",
					height : "500"
				},
				hoverable : false,
				roam : true,
				data : [],
				scaleLimit : {
					min : 0.9,
					max : 1.1
				},
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
		var myChart = echarts.init(document.getElementById('map'));
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
			showView(eval(data));
		});
	});
	
	function chars() {
		$.get("company!getCompanyNumEveryMonth", {}, function(result) {
			var xAxis = new Array(result.length);
			var yAxis = new Array(result.length);
		     var yAxisAdd = new Array(result.length);

			for (var i = 0; i < result.length; i++) {
				xAxis[i] =result[i].createDate;
				yAxis[i] = result[i].num;
			}
			
			  yAxisAdd[0]=0;
	          var count = yAxis[0];
	          for (var i = 1; i < yAxis.length; i++) {
	              yAxisAdd[i] = yAxis[i]-yAxis[i-1];
	          }
			var option = makeOptionScrollUnit(xAxis, yAxis, "月新增医院数量", barType , 100, 12);
			option = makeOptionAdd(option, yAxisAdd, "数据量变化曲线图", lineType, "阴影");

			// 基于准备好的dom，初始化echarts图表
			var myChart = echarts.init(document.getElementById('main'));
		     myChart.setOption(option);
		})
	}
	chars();
</script>